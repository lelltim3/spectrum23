package transferCanMessages;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Suite;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.LongHolder;

import transferCanMessages.UsbCanInterface.CanStatus;
import transferCanMessages.UsbCanInterface.UsbStatus;
import transferCanMessages.UsbCanInterface.VersionType;


import java.nio.ByteBuffer;

public class testModulUsbCan {

    private TransferCanMsgs transferCanMsg;

    @Before
    public void setUI() {
        transferCanMsg = TransferCanMsgs.getInstance();
    }

    @Test
    public void checkVersionDLL(){
        IntHolder verMajor = new IntHolder();
        IntHolder verMinor = new IntHolder();
        IntHolder verRelease = new IntHolder();
        transferCanMsg.getVersion(verMajor, verMinor, verRelease, VersionType.K_VER_TYPE_USER_DLL);

        System.out.println("Version userDll: " + verMajor.value + " " + verMinor.value + " Release = " + verRelease.value);

        Assert.assertEquals(6, verMajor.value);
        Assert.assertEquals(2, verMinor.value);
        Assert.assertEquals(153, verRelease.value);

    }

    @Test
    public void checkErrorCounter() {
        LongHolder trErrorCounter = new LongHolder();
        LongHolder recErrorCounter = new LongHolder();
        transferCanMsg.getErrorCounter(trErrorCounter, recErrorCounter);

        Assert.assertEquals(0, trErrorCounter.value);
        Assert.assertEquals(0, recErrorCounter.value);
    }

    @Test
    public void checkStatus() {
        IntHolder statusCan = new IntHolder();
        IntHolder statusUsb = new IntHolder();

        transferCanMsg.getStatus(statusCan, statusUsb);

        Assert.assertEquals(transferCanMsg.canStatuses.get(CanStatus.USBCAN_CANERR_OK).intValue(), statusCan.value);
        Assert.assertEquals(transferCanMsg.usbStatuses.get(UsbStatus.USBCAN_USBERR_OK).intValue(), statusUsb.value);
    }

    @Test
    public void checkMsgCount() {
        IntHolder trMsgCount = new IntHolder();
        IntHolder recMsgCount = new IntHolder();
        transferCanMsg.getMsgCount(trMsgCount, recMsgCount);

        Assert.assertEquals(0, trMsgCount.value);
        Assert.assertEquals(0, recMsgCount.value);
    }

    @Test
    public void checkReset() {
        transferCanMsg.reset();
    }

    @Test
    public void convertByteInt() {
        byte[] arr = { 0x05 , 0x47, 0x08, 0x55 };
        int num = ByteBuffer.wrap(arr).getInt();
        Assert.assertEquals(88541269, num );

        byte[] bytes = ByteBuffer.allocate(4).putInt(num).array();

        System.out.println(Short.BYTES);
    }


    public  void tearDown() {
        transferCanMsg.close();
    }


}
