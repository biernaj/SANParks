package android.ndk.AdTest;

public class AdLoad {
    static {
        System.loadLibrary("FpCore"); // libFpCore.so
	}
    //public native String getMsgFromJni();
	public native int FPMatch(byte[] pbTmp1, byte[] pbTmp2, int nLevel, int[] pnSim);
}
