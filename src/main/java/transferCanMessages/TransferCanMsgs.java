package transferCanMessages;

import detectionModules.DBintf;
import transferCanMessages.UсanLibrary.Msg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TransferCanMsgs extends UsbCanInterface  {

	private static TransferCanMsgs transferCanMsg;// = new TransferCanMsgs(null);
	private Queue<Msg> transmitCanMsgs 	= new LinkedList<>();
	private Queue<Msg> receiveCanMsgs 	= new LinkedList<>();
	private int countTrCanMsg;
	private int countRecCanMsg;

	public TransferCanMsgs() {
		super();
	}

	DBintf db;
	public void setDB(DBintf db){this.db = db;} //TODO temp

	public static TransferCanMsgs getInstance() {
		return transferCanMsg;
	}

	//------receive
	@Override
	protected void onNewCanMsgs(Msg[] msgs, int size){
		addToReceiveCanMsgs(msgs, size);
	}

	synchronized private void addToReceiveCanMsgs(Msg[] msgs, int size) {
		for(int i = 0; i < size; i++) {
			receiveCanMsgs.add(msgs[i]);
			System.out.println("New receive msg:" + msgs[i]);
		}
		countRecCanMsg += size;

		if(db != null) db.readMsgs();//TODO temp
	}

	synchronized public List<Msg> subFromReceiveCanMsgs() {
		List<Msg> msgs = new ArrayList<>(receiveCanMsgs.size());
		for (int i = 0; i < receiveCanMsgs.size(); i++)
			msgs.add(receiveCanMsgs.poll());
		return msgs;
	}

	//------transmit
	synchronized public void addToTransmitMsgs(List<Msg> msg) {
		for(int i = 0; i < msg.size(); i++)
			transmitCanMsgs.add(msg.get(i));

		subFromTransmitCanMsgsAndWrite();
	}

	private void subFromTransmitCanMsgsAndWrite() {
		Msg[] msg = subFromTransmitCanMsgs();
		writeMsg(msg);
		countTrCanMsg += msg.length; //TODO temp
	}

	synchronized private Msg[] subFromTransmitCanMsgs() {
		Msg[] msgs = new Msg[transmitCanMsgs.size()];
		for (int i = 0; i < msgs.length; i++) {
			msgs[i] = transmitCanMsgs.poll();
			System.out.println("New transmit msg:" + msgs[i]);
		}

		return msgs;
	}

	private void writeMsg(Msg[] msgs) {
		writeUsbCanMsgs(msgs);
	}

	//------
	public int getCountTrCanMsg() {
		return countTrCanMsg;
	}

	public int getCountRecCanMsg() {
		return countRecCanMsg;
	}
}





