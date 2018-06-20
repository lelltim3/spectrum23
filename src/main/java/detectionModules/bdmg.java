package detectionModules;

public class bdmg {

    private byte logicNumber;
    private int serialNubmer;

    public bdmg(int serialNubmer) {
        this.serialNubmer = serialNubmer;
    }

    public byte getLogicNumber() {
        return logicNumber;
    }

    public void setLogicNumber(byte logicNumber) {
        this.logicNumber = logicNumber;
    }
}
