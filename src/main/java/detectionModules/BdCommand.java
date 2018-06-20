package detectionModules;

import detectionModules.DBintf.Parameter;

@FunctionalInterface
public interface BdCommand {
    void execute();
}

class GetParametr implements BdCommand {

    private DBintf handler;
    private byte logicNumber;
    private Parameter parameter;

    public GetParametr(byte logicNumber, Parameter parameter) {
        this.logicNumber = logicNumber;
        this.parameter = parameter;
    }

    @Override
    public void execute() {
        handler.getParameter(logicNumber, parameter);
    }
}

