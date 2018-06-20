package transferCanMessages;

import detectionModules.DBintf.Parameter;
import detectionModules.DBintf.OperatingMode;
import detectionModules.DBintf.State;
import detectionModules.DBintf.Command;
import detectionModules.DBintf.TypeMsg;


import transferCanMessages.UсanLibrary.Msg;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static detectionModules.DBintf.*;
import static detectionModules.DBintf.Command.*;
import static detectionModules.DBintf.Command.CALIBRATION;
import static detectionModules.DBintf.Command.MEASURE;
import static detectionModules.DBintf.TypeMsg.*;


public class Parsing {

    private final int idDanger;
    private int idManagement;
    private int idReply;


    private StringBuilder log = new StringBuilder();

    private byte commandCode;
    private TypeMsg typeMsg;

    private byte logicNumber;
    private int serialNumber;

    private byte operatingModeCode;
    private byte stateCode;
    private byte parameterCode;
    private int parameterValue;

    private byte stabilization;


    public Parsing(int idDanger, int idManagement, int idReply) {
        this.idDanger = idDanger;
        this.idManagement = idManagement;
        this.idReply = idReply;
    }

    public void parsingMsg(Msg msg) {
        typeMsg = getTypeMsgById(msg.getIdCanMsg());
        if(typeMsg == UNKNOWN)
            throw new IllegalArgumentException("Ошибка парсинга can сообщения. Не известный тип сообщения. " +  msg);
        commandCode = getCommandCode(msg.getDataCanMsg());
        if(commandCode == commandsCodes.get(SET_LOGIC_NUMBER)) {
            parsingMsgBySetLogicNumber(typeMsg, msg);
        }
        else if(commandCode == commandsCodes.get(SET_STATE)) {
            parsingMsgBySetState(typeMsg, msg);
        }
        else if(commandCode == commandsCodes.get(SET_PARAMETER)) {
            parsingMsgBySetParameter(typeMsg, msg);
        }
        else if(commandCode == commandsCodes.get(GET_PARAMETER)) {
            parsingMsgByGetParameter(typeMsg, msg);
        }
        else if(commandCode == commandsCodes.get(CALIBRATION)) {
            parsingMsgByCalibration(typeMsg, msg);
        }
        else if(commandCode == commandsCodes.get(MEASURE)) {
            parsingMsgByMeasure(typeMsg, msg);
        }
        else
            throw new IllegalArgumentException("Ошибка парсинга can сообщения. Не известная команда " + commandCode + " " + msg);

        log.append(msg.getIdCanMsg() + "\t");
        log.append(Arrays.toString(msg.getDataCanMsg()) + "\t");
    }

    private void parsingMsgBySetLogicNumber(TypeMsg typeMsg, Msg msg) {
        byte[] dataCanMsg = msg.getDataCanMsg();
        if(typeMsg == REPLY) {
            logicNumber = getLogicNumberFromDataCanData(dataCanMsg);
        }
        addLogBySetLogicNumber(typeMsg, dataCanMsg);
    }

    private void parsingMsgBySetState(TypeMsg typeMsg, Msg msg) {
        byte[] dataCanMsg = msg.getDataCanMsg();
        if(typeMsg == REPLY) {
            operatingModeCode = getOperatingModeCodeFromDataCanMsg(dataCanMsg);
        }
        else if(typeMsg == DANGER) {
            serialNumber = getSerialNumberFromDataCanData(dataCanMsg);
        }
        addLogBySetState(typeMsg, dataCanMsg);
    }

    private void parsingMsgBySetParameter(TypeMsg typeMsg, Msg msg) {
        byte[] dataCanMsg = msg.getDataCanMsg();
        if( typeMsg == REPLY) {
            parameterCode = getParameterCodeFromDataCanMsg(dataCanMsg);
            parameterValue = getParameterValueFromDataCanMsg(dataCanMsg);
        }
        else if(typeMsg == DANGER) {
            stabilization = getStabilizationFromDataCanMsg(dataCanMsg);
        }
        addLogBySetParameter(typeMsg, dataCanMsg);
    }

    private void parsingMsgByGetParameter(TypeMsg typeMsg, Msg msg) {
        byte[] dataCanMsg = msg.getDataCanMsg();
        addLogByGetParameter(typeMsg, dataCanMsg);
    }

    private void parsingMsgByCalibration(TypeMsg typeMsg, Msg msg) {
        if(typeMsg == MANAGEMENT) {
        }
        else if(typeMsg == REPLY) {
        }
    }

    private void parsingMsgByMeasure(TypeMsg typeMsg, Msg msg) {
        if(typeMsg == MANAGEMENT) {
        }
        else if(typeMsg == REPLY) {
        }
    }


    private TypeMsg getTypeMsgById(int id) { //TODO алгоритм!
        if(id >= idDanger && id < idDanger + 0x100) {
            log.append("<---\t");
            return DANGER;
        }
        else if(id >= idManagement && id < idManagement + 0x100) {
            log.append("--->\t");
            return MANAGEMENT;
        }
        else if(id >= idReply && id < idReply + 0x100) {
            log.append("<---\t");
            return REPLY;
        }
        else {
            log.append("?\t");
            return UNKNOWN;
        }
    }

    private byte getCommandCode(byte[] dataCanMsg) {
        byte commandCode = dataCanMsg[0];
        log.append(commandsNames.get(commandCode) + "\t");
        return commandCode;
    }

    private byte getLogicNumberFromDataCanData(byte[] dataCanMsg) {
        return dataCanMsg[1];
    }
    private int getSerialNumberFromDataCanData(byte[] dataCanMsg) {
        return ByteBuffer.wrap(new byte[] {dataCanMsg[4], dataCanMsg[5], dataCanMsg[6], dataCanMsg[7]}).getInt();
    }
    private void addLogBySetLogicNumber(TypeMsg typeMsg, byte[] dataCanMsg) {
        byte logicNumber = getLogicNumberFromDataCanData(dataCanMsg);
        int serialNumber = getSerialNumberFromDataCanData(dataCanMsg);
        if(typeMsg == MANAGEMENT) {
            log.append("Логический номер: " + logicNumber);
        }
        else if(typeMsg == REPLY) {
            log.append("Логический номер: " + logicNumber + " задан");
        }
        else if(typeMsg == DANGER) {
        }
        log.append(" (серийный номер: " + serialNumber +")\t");
    }

    private byte getStateCodeFromDataCanMsg(byte[] dataCanMsg) {
        return dataCanMsg[1];
    }
    private byte getOperatingModeCodeFromDataCanMsg(byte[] dataCanMsg) {
        return dataCanMsg[2];
    }
    private void addLogBySetState(TypeMsg typeMsg, byte[] dataCanMsg) {
        if(typeMsg == MANAGEMENT) {
            log.append("Состояние: " + statesNames.get(getStateCodeFromDataCanMsg(dataCanMsg)) + "\t");
        }
        else if(typeMsg == REPLY) {
            log.append("Режим: " + operatingModesNames.get(getOperatingModeCodeFromDataCanMsg(dataCanMsg)) + "\t");
        }
        else if(typeMsg == DANGER) {
            log .append("Включение в сеть БД (серийный номер: ")
                .append(getSerialNumberFromDataCanData(dataCanMsg) + ")\t");
        }
    }

    private byte getParameterCodeFromDataCanMsg(byte[] dataCanMsg) {
        return dataCanMsg[1];
    }
    private int getParameterValueFromDataCanMsg(byte[] dataCanMsg) {
        return ByteBuffer.wrap(new byte[] {dataCanMsg[4], dataCanMsg[5]}).getInt();
    }
    private byte getStabilizationFromDataCanMsg(byte[] dataCanMsg) {
        return dataCanMsg[1];
    }
    private void addLogBySetParameter(TypeMsg typeMsg, byte[] dataCanMsg) {
        if(typeMsg == MANAGEMENT) {
            log.append("Параметр: " + parameterNames.get(getParameterCodeFromDataCanMsg(dataCanMsg)) + " ");
            log.append("Значение: " + getParameterValueFromDataCanMsg(dataCanMsg));
        }
        else if(typeMsg == REPLY) {
            log.append("Параметр: " + parameterNames.get(getParameterCodeFromDataCanMsg(dataCanMsg)) + " задан. ");
            log.append("Значение: " + getParameterValueFromDataCanMsg(dataCanMsg));
        }
        else if(typeMsg == DANGER) {
            if(getStabilizationFromDataCanMsg(dataCanMsg) == 1)
                log.append("Стабилизирован!");
            else
                log.append("Не стабилизации!");
        }
    }
    private void addLogByGetParameter(TypeMsg typeMsg, byte[] dataCanMsg) {
        if(typeMsg == MANAGEMENT) {
            log.append("Параметр: " + parameterNames.get(getParameterCodeFromDataCanMsg(dataCanMsg)) + " ");
            log.append("Значение: " + getParameterValueFromDataCanMsg(dataCanMsg));
        }
    }



    public String getLog() {
        String str = log.toString();
        log.setLength(0);
        return str;
    }

    public byte getCommandCode() {
        return commandCode;
    }

    public TypeMsg getTypeMsg() {
        return typeMsg;
    }

    public byte getLogicNumber() {
        return logicNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public byte getOperatingModeCode() {
        return operatingModeCode;
    }

    public byte getStateCode() {
        return stateCode;
    }

    public byte getParameterCode() {
        return parameterCode;
    }

    public int getParameterValue() {
        return parameterValue;
    }

    public byte getStabilization() {
        return stabilization;
    }
}
