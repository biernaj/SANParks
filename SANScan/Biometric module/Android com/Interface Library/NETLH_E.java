package com.BRMicro;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException; 	
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.BRMicro.PARA_TABLE;

import android.util.Log;


public class NETLH_E {
	//1.初始化串口采集public native int  ConfigCommParameterCom(String _COM,                 //串口号  
								          int  _BaudRate,    //波特率  
								          int  _DataBit,     //数据位  
								          int  _StopBit,     //停止位  
								          int  _CheckMode,   //校验方式
								          int  _DeviceAdd,   //设备地址
								          int  _Password);   //联机密码 


	//1.初始化USB采集�?
		public native int  ConfigCommParameterUDisk(int  _DeviceAdd, int  _Password);   
	 	//2.读取参数列表
		public native int  CmdReadParaTable(PARA_TABLE _ParaTable ,int  _ErrFlag[]);
		//3.读取指纹模板索引	
		public native int  CmdGetMBIndex(byte[] gMBIndex , int gMBIndexStart, int gMBIndexNum, int _ErrFlag[]);
		//4.探测指纹
		public native int  CmdDetectFinger(int _ErrFlag[]);
		//5.获取指纹图像
		public native int  CmdGetRedressImage(int _ErrFlag[]);
		//6.上传指纹图像
		public native int  CmdUpLoadRedressImage(byte[] _ImageBuf);	
		//7.获取原始指纹图像 
		public native int  CmdGetRawImage(int _ErrFlag[]);	
		//8.上传原始指纹图像
		public native int  CmdUpLoadRawImage(int _ErrFlag[]);
		public native int  CmdUpLoadRawImage(byte[] _ImageBuf);	
		//9.生成指纹特征
		public native int  CmdGenChar(int iBuffer,int  _ErrFlag[]);
		//10.存储指纹模板
		public native int  CmdStoreChar(int m_Addr,int _RetMbIndex[], int _RetScore[], int _ErrFlag[]);
		//11.搜索指纹模板
		public native int  CmdSearchChar(int iBuffer,int _RetMbIndex[], int _RetScore[], int _ErrFlag[]);
		//12.单一比对指纹模板
		public native int  CmdVerifyChar(int iBuffer, int m_Addr,int  _RetScor[], int  _ErrFlag[]);
		//13.上传模板
		public native int  CmdGetChar(int iBuffer,byte[] CharBuf, int _ErrFlag[]);
		//14.下载模板
		public native int  CmdPutChar(int iBuffer,byte[] CharBuf, int  _ErrFlag[]);
		//15.删除�?��指纹模板
		public native int  CmdEmptyChar(int _ErrFlag[]);
		//16.删除指定地址的指纹模�?
		public native int  CmdDelChar(int m_Addr, int _ErrFlag[]);
		//17.读记事本
		public native int  CmdReadNoteBook(int _PageID, byte[] _NoteText ,int  _ErrFlag[]);
		//18.写记事本
		public native int  CmdWriteNoteBook(int _PageID, byte[] _NoteText ,int  _ErrFlag[]);
		//19.设置指纹安全级别
		public native int  CmdSetSecurLevel(int _SecurLevel ,int  _ErrFlag[]);
		//20.恢复出厂设置
		public native int  CmdResumeFactory(int  _ErrFlag[]);
		//21.复位模块
		public native int  CmdDeviceReset(int _ErrFlag[]);

		public native int  CmdGetCharExt(int iBuffer,byte[] CharBuf, int _ErrFlag[]); 
		public native int  CmdPutCharExt(int iBuffer,byte[] CharBuf, int  _ErrFlag[]);		
		
	//Below is FingerModel.cpp
	    public native int  CreatBmp(String pFilePath, byte[] pImage,int  xLen , int yLen);
		public native int  CmdDownLoadImage(byte[] _ImagePath);
		public native int  CmdMatchChar(int  _RetScore[], int  _ErrFlag[]);
		public native int  CmdSetBaudRate(int _BaudRate ,int  _ErrFlag[]);
		public native int  CmdSetCmosPara(int _ExposeTimer ,int DetectSensitive,int  _ErrFlag[]);
		public native int  CmdGetRawImageBuf(byte[] _ImageBuf);
		public native int  CmdEraseProgram(int  _ErrFlag[]);
	// Below is command.cpp

		public native 	void  CommClose ();    
		public native int   GetLastCommErr();
		public native int   GetLastCommSystemErr();
		public native void  SetTimeOutValue(int  _TimeOutValue);
		public native int   GetTimeOutValue();
		public native int   CmdTestPro();
		public native int   CmdDeviceConnect();
		public native int   CmdDeviceDeConnect();
		public native int   CmdHandDevice();
		public native int   CmdOnBeep();
		public native int   CmdSetBeepTime(int  _DelayMs);
		public native int   CmdGetBeepTime(int _DelayMs[]);
	    static {
	        System.loadLibrary("ZAZFinger_BR");
	        
	    }


}
