package com.BRMicro;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException; 	
import android.content.Context;
import android.util.Log;

public class NETLH_E {

	private final String TAG = "=NETLH_E=";
	public NETLH_E()
	{
		String sCurrentPath = "/mnt/sdcard/";
		char[] pCurrentPath = sCurrentPath.toCharArray();
		//SetAppDirectoryPath(pCurrentPath, 14);
		
		char[] ppCurrentPath = new char[1024];
		GetAppDirectoryPath(ppCurrentPath, 1024);
		String ssCurrentPath = ppCurrentPath.toString();
	}
	public native int  CmdDeviceInitGetPath(byte[] path);
	public int CmdDeviceGetChmod(int ErrCode)
	{
	    int ret = 1;
		byte[]  path = new byte[128];
		
		CmdDeviceInitGetPath(path);
		String spath = new String(path);
		String sspath = spath.substring(0, spath.indexOf('\0'));
		Process process = null;
		DataOutputStream os = null;
		String command = "chmod 777 " + sspath;
		Log.d(TAG, " exec " + command);
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command+"\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		}
		catch (Exception e) 
		{
			ret = 0;
		}

        return ret;
	}
	
	public int CmdDeviceGetChmod(String path)
	{
		String command = "chmod 777 " + path;
		Log.d(TAG, " exec " + command);
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command+"\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		}
		catch (Exception e) 
		{
		}
		return 0;
	}

	public native int  GetComList(char[] ComList); ///
	public native int  AsciiToHex(char[] _pInData, int _nInLength, char[] _pOutData, int[] _nOutLength);///
	public native int  GetCurrentDirectoryPath(char[] _pCurrentPath, int _pLenth);///
	public native int  GetAppDirectoryPath(char[] _pCurrentPath, int _pLenth); ///
	
	public native int  SetAppDirectoryPath(char[] _pCurrentPath, int _pLenth); ///

	public native int  ConfigCommParameterUDisk(int _DeviceAdd,       //设备地址
									        int _Password);       //联机密码
	public native int  ConfigCommParameterCom(String _COM,                 //串口号  
								          int  _BaudRate,    //波特率  
								          int  _DataBit,     //数据位  
								          int  _StopBit,     //停止位  
								          int  _CheckMode,   //校验方式
								          int  _DeviceAdd,   //设备地址
								          int  _Password);   //联机密码 


	public native int  CmdDeviceReset(int _ErrFlag[]); //
	public native int  CmdDetectFinger(int _ErrFlag[]);//
	public native int  CmdGetRawImage(int _ErrFlag[]);//
	public native int  CmdGetRedressImage(int _DetectDn, int _ErrFlag[]);///
	public native int  CmdUpLoadRawImage(byte[] _ImageBuf);//
	public native int  CmdUpLoadRedressImage(byte[] _ImageBuf);//
	public native int  CmdDownLoadImage(byte[] _ImagePath);//
	public native int  CmdGenChar(int iBuffer,int  _ErrFlag[]);//
	public native int  CmdMatchChar(int  _RetScore[], int  _ErrFlag[]);//
	public native int  CmdStoreChar(int m_Addr,int _RetMbIndex[], int _RetScore[], int _ErrFlag[]); //
	public native int  CmdSearchChar(int iBuffer,int _RetMbIndex[], int _RetScore[], int _ErrFlag[]); //
	public native int  CmdGetChar_eAlg(int iBuffer, byte[] CharBuf, int[] _ErrFlag);///
	public native int  CmdGetChar_xAlg(int iBuffer, byte[] CharBuf, int[] _ErrFlag);///
	public native int  CmdPutChar_eAlg(int iBuffer, byte[] CharBuf, int[] _ErrFlag);///
	public native int  CmdPutChar_xAlg(int iBuffer, byte[] CharBuf, int[] _ErrFlag);///
	public native int  CmdGetMBIndex(byte[] gMBIndex , int gMBIndexStart, int gMBIndexNum, int _ErrFlag[]); //
	public native int  CmdEmptyChar(int _ErrFlag[]);//
	public native int  CmdDelChar(int m_Addr, int _ErrFlag[]);//
	public native int  CmdVerifyChar(int iBuffer, int m_Addr,int  _RetScor[], int  _ErrFlag[]); //
	public native int  CmdReadNoteBook(int _PageID, byte[] _NoteText ,int  _ErrFlag[]); //
	public native int  CmdWriteNoteBook(int _PageID, byte[] _NoteText ,int  _ErrFlag[]); //

	public native int  CmdReadParaTable(PARA_TABLE _ParaTable ,int  _ErrFlag[]); //
	/*
		PARA_TABLE  ParaTable = new PARA_TABLE();
		CmdReadParaTable(ParaTable, );
	*/
	public native int  CmdSetBaudRate(int _BaudRate ,int  _ErrFlag[]);  //
	public native int  CmdSetSecurLevel(int _SecurLevel ,int  _ErrFlag[]); //
	public native int  CmdSetCmosPara(int _ExposeTimer ,int DetectSensitive,int  _ErrFlag[]); //
	public native int  CmdGetRawImageBuf(byte[] _ImageBuf);
	public native int  CmdEraseProgram(int  _ErrFlag[]); //
	public native int  CmdResumeFactory(int  _ErrFlag[]);//



	public native 	void  CommClose ();   //鍏抽棴鎵�湁閫氫俊鏂瑰紡


	public native int   GetLastCommErr();
	public native int   GetLastCommSystemErr();
	public native void  SetTimeOutValue(int  _TimeOutValue);
	public native int   GetTimeOutValue();

	
	public native int CmdUpLoadRedressImage256x360(byte[] _ImageBuf);
	public native int CmdMergeChar(int[] _RetScore, int[] _ErrFlag);
	public native int CmdStoreCharDirect_eAlg(int m_Addr, byte[] _FingerChar, int[] _ErrFlag);
	public native int CmdStoreCharDirect_xAlg(int m_Addr, byte[] _FingerChar, int[] _ErrFlag);
	public native int CmdReadCharDirect_eAlg(int m_Addr, byte[] _FingerChar, int[] _ErrFlag);
	public native int CmdReadCharDirect_xAlg(int m_Addr, byte[] _FingerChar, int[] _ErrFlag);
	
	public native int CmdSetPsw(int _NewPsw, int[] _ErrFlag);
	public native int CmdSetDeviceAddress(int _NewAddress, int[] _ErrFlag);
	public native int CmdGetRandom(int[] _Random, int[] _ErrFlag);
	public native int CmdSendDemon(byte[] data, int[] ErrFlag);

 	//IC CARD
 	
	
	public native int    CmdICRequest(int _Mode, byte _CardType[],/*2 bytes*/ int _ErrFlag[]) ;
	public native int    CmdICAnticoll(int _Bcnt, byte _CardNum[],/*4 bytes*/ int _ErrFlag[]);
	public native int    CmdICSelect(byte _Size[],/*1 bytes*/int _CardNum, int _ErrFlag[]) ;
	public native int    CmdICHalt(int _ErrFlag[]);
	public native int    CmdICLoadKey(byte _LoadKey[], /*6 bytes*/int _ErrFlag[]);
	public native int    CmdICAuthentication(int _Sector,int _AuthMode, int _CardNum ,int _ErrFlag[]);
	public native int    CmdICReadBlock(int _SectorIndex,int _BlockIndex,byte  _BlockBuf[], int _ErrFlag[]);
	public native int    CmdICWriteBlock(int _SectorIndex,int _BlockIndex,byte _BlockBuf[], /* IC_BLOCK_SIZE 16 bytes*/int _ErrFlag[]);

	public native int    CmdICInitMoney(int _SectorIndex,int _BlockIndex,int _Value, int _ErrFlag[]);
	public native int    CmdICIncrementMoney(int _SectorIndex,int _BlockIndex,int _Value, int _ErrFlag[]);
	public native int    CmdICDecrementMoney(int _SectorIndex,int _BlockIndex,int _Value,int _ErrFlag[]);
	public native int    CmdICTransferMoney(int _SectorIndex,int _BlockIndex,int _ErrFlag[]);
	
	public native int    CmdGetCardIdTypeB(byte _Card_ID[], /* 8 bytes*/int _ErrFlag[]);
	public native int    CmdGetCardIdTypeA(byte _Card_ID[],  /* 4 bytes*/int _ErrFlag[]);
	public native int    CmdSetCardType(int _CardType,int _ErrFlag[]);
	
    static {
        System.loadLibrary("NETLH_E");
    }

}
