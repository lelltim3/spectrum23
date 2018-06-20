package transferCanMessages;

import java.util.Arrays;
import java.util.List;

import org.omg.CORBA.IntHolder;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.BYTE;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.WORD;
import com.sun.jna.platform.win32.WinDef.DWORDByReference;
import com.sun.jna.platform.win32.WinDef.WORDByReference;

public interface UсanLibrary extends Library {

	class Init 			extends Structure {

		public DWORD	m_dwSize;
		public BYTE   	m_bMode;
		public BYTE  	m_bBTR0;
		public BYTE   	m_bBTR1;
		public BYTE   	m_bOCR;
		public DWORD	m_dwAMR;
		public DWORD  	m_dwACR;
		public DWORD  	m_dwBaudrate;
		public WORD   	m_wNrOfRxBufferEntries;
		public WORD   	m_wNrOfTxBufferEntries;

		public Init(int size) {
			super(new Memory(size));
			setAlignType(ALIGN_NONE);
		}

		public Init() {
			super();
			setAlignType(ALIGN_NONE);
		}

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("m_dwSize",
					"m_bMode",
					"m_bBTR0",
					"m_bBTR1",
					"m_bOCR",
					"m_dwAMR",
					"m_dwACR",
					"m_dwBaudrate",
					"m_wNrOfRxBufferEntries",
					"m_wNrOfTxBufferEntries");
		}

		public static class ByRef extends Init implements ByReference{
		}
	}
	class Msg 			extends Structure {

		private static int sizeStructure = 18;

		public DWORD   m_dwID;
		public BYTE    m_bFF;
		public BYTE    m_bDLC;
		public BYTE    m_bData0;
		public BYTE    m_bData1;
		public BYTE    m_bData2;
		public BYTE    m_bData3;
		public BYTE    m_bData4;
		public BYTE    m_bData5;
		public BYTE    m_bData6;
		public BYTE    m_bData7;
		public DWORD   m_dwTime;

		public Msg(int size) {
			super(new Memory(sizeStructure*size));
			setAlignType(ALIGN_NONE);
		}

		public Msg() {
			setAlignType(ALIGN_NONE);
		}

		public Msg(int id, byte[] data) {
			super();
			setAlignType(ALIGN_NONE);

			if(data.length != 8 ) {
				throw new IllegalArgumentException("Length data != 8");
			}

			m_dwID.setValue(id);
			m_bDLC.setValue(8);
			m_bData0.setValue(data[0]);
			m_bData1.setValue(data[1]);
			m_bData2.setValue(data[2]);
			m_bData3.setValue(data[3]);
			m_bData4.setValue(data[4]);
			m_bData5.setValue(data[5]);
			m_bData6.setValue(data[6]);
			m_bData7.setValue(data[7]);
		}

		public void copyMe(Msg instance) {
			instance.m_dwID = this.m_dwID;
			instance.m_bDLC = this.m_bDLC;
			instance.m_bData0 = this.m_bData0;
			instance.m_bData1 = this.m_bData1;
			instance.m_bData2 = this.m_bData2;
			instance.m_bData3 = this.m_bData3;
			instance.m_bData4 = this.m_bData4;
			instance.m_bData5 = this.m_bData5;
			instance.m_bData6 = this.m_bData6;
			instance.m_bData7 = this.m_bData7;
		}

		public void getAllDataCanMsg(IntHolder id, byte[] data) {
			id.value = m_dwID.intValue();

			if(data.length != 8 ) {
				throw new IllegalArgumentException("Length data != 8");
			}

			data[0] = m_bData0.byteValue();
			data[1] = m_bData1.byteValue();
			data[2] = m_bData2.byteValue();
			data[3] = m_bData3.byteValue();
			data[4] = m_bData4.byteValue();
			data[5] = m_bData5.byteValue();
			data[6] = m_bData6.byteValue();
			data[7] = m_bData7.byteValue();
		}

		public int getIdCanMsg() {
			return m_dwID.intValue();
		}

		public byte[] getDataCanMsg () {
			byte[] data = new byte[8];
			data[0] = m_bData0.byteValue();
			data[1] = m_bData1.byteValue();
			data[2] = m_bData2.byteValue();
			data[3] = m_bData3.byteValue();
			data[4] = m_bData4.byteValue();
			data[5] = m_bData5.byteValue();
			data[6] = m_bData6.byteValue();
			data[7] = m_bData7.byteValue();
			return data;
		}

		@Override
		public String toString()
		{
			return "Msg{" +
					"id: " + m_dwID +
					", data: " + m_bData0 +
					" " + m_bData1 +
					" " + m_bData2 +
					" " + m_bData3 +
					" " + m_bData4 +
					" " + m_bData5 +
					" " + m_bData6 +
					" " + m_bData7 +
					'}';
		}

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("m_dwID",
					"m_bFF",
					"m_bDLC",
					"m_bData0",
					"m_bData1",
					"m_bData2",
					"m_bData3",
					"m_bData4",
					"m_bData5",
					"m_bData6",
					"m_bData7",
					"m_dwTime");
		}

		public static class ByRef extends Msg implements ByReference {

			public ByRef(int size) {
				super(sizeStructure*size);
			}

			public ByRef() {
				super();
			}

			public ByRef(int id, byte[] data) {
				super(id, data);
			}
		}
	}
	class MsgCountInfo 	extends Structure {

		public WORD m_wSentMsgCount;
		public WORD m_wRecvdMsgCount;

		public MsgCountInfo(int size) {
			super(new Memory(size));
			setAlignType(ALIGN_NONE);
		}

		public MsgCountInfo() {
			super();
			setAlignType(ALIGN_NONE);
		}

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("m_wSentMsgCount",
					"m_wRecvdMsgCount");
		}

		public static class ByRef extends MsgCountInfo implements ByReference {
			public ByRef(int size)
			{
				super(size);
			}

			public ByRef()
			{
				super();
			}
		}
	}
	class Status 		extends Structure {

		public WORD m_wCanStatus;
		public WORD m_wUsbStatus;

		public Status(int size) {
			super(new Memory(size));
			setAlignType(ALIGN_NONE);
		}

		public Status() {
			super();
			setAlignType(ALIGN_NONE);
		}

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("m_wCanStatus",
					"m_wUsbStatus");
		}

		public static class ByRef extends Status implements ByReference {

			public ByRef(int size) {
				super(size);
			}

			public ByRef() {
				super();
			}
		}
	}

    //BYTE UcanInitHwConnectControlEx (tConnectControlFktEx fpConnectControlFktEx_p, void* pCallbackArg_p);
	BYTE UcanInitHwConnectControlEx(Callback callback, Pointer arg);
    
    //BYTE UcanDeinitHwConnectControl(void);
	BYTE UcanDeinitHwConnectControl();
    
    //BYTE UcanInitHardwareEx(tUcanHandle* pUcanHandle_p, BYTE bDeviceNr_p, tCallbackFktEx fpCallbackFktEx_p, void* pCallbackArg_p);
	BYTE UcanInitHardwareEx(Pointer pUsbCanHandle, BYTE bDeviceNr_p, Callback eventHandler, Pointer arg);

	//BYTE UcanDeinitHardware(BYTE UcanHandle_p);
	BYTE UcanDeinitHardware(BYTE usbCanHandle);

    //BYTE UcanInitCanEx2(BYTE UcanHandle_p, BYTE bChannel_p, tUcanInitCanParam* pInitCanParam_p);
	BYTE UcanInitCanEx2(BYTE usbCanHandle, BYTE channel, Init.ByRef init);

	//BYTE UcanDeinitCan(tUcanHandle UcanHandle_p, BYTE bChannel_p);
	BYTE UcanDeinitCanEx(BYTE usbCanHandle, BYTE channel);

	//BYTE UcanGetStatusEx(BYTE UcanHandle_p,  BYTE bChannel_p, tStatusStruct* pStatus_p);
	BYTE UcanGetStatusEx(BYTE usbCanHandle, BYTE channel, Status.ByRef status);

	//BYTE UcanGetMsgCountInfoEx(BYTE UcanHandle_p, BYTE bChannel_p, tUcanMsgCountInfo* pMsgCountInfo_p);
	BYTE UcanGetMsgCountInfoEx(BYTE usbCanHandle, BYTE channel, MsgCountInfo.ByRef msgCountInfo);

	//BYTE UcanGetCanErrorCounter(BYTE UcanHandle_p, BYTE bChannel_p, DWORD* pdwTxErrorCounter_p, DWORD* pdwRxErrorCounter_p);
	BYTE UcanGetCanErrorCounter(BYTE usbCanHandle, BYTE channel, DWORDByReference txErrorCounter, DWORDByReference rxErrorCounter);

	//BYTE UcanResetCanEx(BYTE UcanHandle_p, BYTE bChannel_p, DWORD dwResetFlags_p);
	BYTE UcanResetCanEx(BYTE usbCanHandle, BYTE channel, DWORD resetFlags);

    //BYTE UcanWriteCanMsgEx(tUcanHandle UcanHandle_p, BYTE bChannel_p, tCanMsgStruct* pCanMsg_p, DWORD* pdwCount_p);
	BYTE UcanWriteCanMsgEx(BYTE usbCanHandle, BYTE channel, Msg.ByRef canMsg, DWORDByReference count);
	
    //BYTE UcanReadCanMsgEx(BYTE UcanHandle_p, BYTE* pbChannel_p, tCanMsgStruct* pCanMsg_p, DWORD* pdwCount_p);
	BYTE UcanReadCanMsgEx(BYTE usbCanHandle, WORDByReference channel, Msg.ByRef canMsg, DWORDByReference count);

	//DWORD PUBLIC UcanGetVersionEx (tUcanVersionType VerType_p);
	DWORD UcanGetVersionEx(DWORD verType);
}
