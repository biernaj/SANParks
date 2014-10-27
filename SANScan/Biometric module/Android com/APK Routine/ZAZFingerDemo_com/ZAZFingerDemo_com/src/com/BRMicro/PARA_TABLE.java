package com.BRMicro;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException; 	

public class  PARA_TABLE
{
	public  int dwProductSN;              //娴溠冩惂鎼村繐鍨崣锟�
	public  int dwFingerNum;              //閹稿洨姹楅弫浼村櫤
	public  int dwDeviceAddress;          //鐠佹儳顦搁崷鏉挎絻
	public  int dwCommPassword;           //闁矮淇婄�鍡欑垳
	public  int dwComBaudRate;            //娑撴彃褰涘▔銏㈠閻滐拷
	public  int wCmosExposeTimer;         //CMOS閺囨繂鍘滈弮鍫曟？
	public  int  cDetectSensitive;         //閹恒垺绁撮幍瀣瘹閻忓灚鏅辨惔锟�閸掞拷00閸欘垵鐨�
	public  int  cSecurLevel;              //閹稿洨姹楅幖婊呭偍鐎瑰鍙忕痪褍鍩�
	public  String  cManuFacture;         //閻㈢喍楠囬崢鍌氭櫌
	public  String  cproductModel;        //娴溠冩惂閸ㄥ褰�
	public  String  cSWVersion;           //鏉烆垯娆㈤悧鍫熸拱
	public  String  cReserve;
}