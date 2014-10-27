package com.haiya.fingerprintcontroller;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException; 	
import android.os.Message;
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.util.Log;
import android.os.Handler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException; 	
import android.text.InputType;
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import java.io.UnsupportedEncodingException;

import android.os.HandlerThread;  
import android.os.Looper;  


import java.util.ArrayList; 
import java.util.List; 
import java.io.IOException;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;   
import android.content.DialogInterface; 

import org.apache.http.util.EncodingUtils;
import android.widget.ProgressBar;
import android.view.View;

import com.BRMicro.NETLH_E;
import com.BRMicro.PARA_TABLE;

import android.ndk.AdTest.*;

public class ControllerAct extends Activity {
 
	private final String TAG = "=Fingerprint=";
	private ImageView mFingerprintIv ;
	private Button mContinueSearchBtn ;

	private Button mClearDatabaseBtn ;
	private Button mInputFingerContinueBtn ;
	private Button mObtainImgContinueBtn ;
	private TextView mUSBInfoTv ;

	private Button mOpenDeviceBtn ;
	private Button mCloseDeviceBtn ;
	private Button mCancelCurOperateBtn ;
	private Button mQuitBtn ;
	
	private Button match_android_Btn;
	private Button report_android_Btn;
	
	private EditText editText_1;
	private TextView zhengshiyong_usb_tv;
	private ProgressBar processFPmatchBar;

	private boolean mOriginImgCbFlag = false;
	private Handler objHandler ;
	private Handler objHandler_2 ;
	private HandlerThread thread;
	
	private int mscGetImage = 1;
	
	private final int CHAR_BUFFER_A   =       0x01;
	private final int CHAR_BUFFER_B     =     0x02;
	private final int CHAR_BUFFER_C     =     0x03;
	
	private final int CMD_RT_OK = 0;
	private int[] FingerAddress = new int[1000];
	private int nextAddress = 0;
	private boolean mUploadImgCb_fg = true;
	private String publicString = "";
	public Handler mHandlerUI=new Handler();  
	boolean gbCancel = false;
	boolean isFinish = false;
	private int currIndexAddress = 0;
	PARA_TABLE g_ptab = new PARA_TABLE();
	
	private final boolean bPreviewImg = true;
	NETLH_E netlh = new NETLH_E();
	
	private boolean showImage = false;
	
	// v4.x
	private Spinner usb_com_spinner;
	private Spinner com_baud_spinner;
	private Spinner images_type_spinner;
	private Spinner algorithm_version_spinner;
	private final int FINGER_SIZE_256_360 = 1;
	private final int FINGER_SIZE_256_288 = 0;
	private final int FINGER_XALG_TYPE = 1;
	private final int FINGER_EALG_TYPE = 0;
	private int m_finger_size = FINGER_SIZE_256_288;
	
	private int m_alg_type = FINGER_EALG_TYPE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);
		
		int ret = 0;
		meminit();
		
		findViews();
		findViews2();
		initSpinners();
		setListener();
		
		thread = new HandlerThread("MyHandlerThread");
		thread.start();
		objHandler = new Handler(thread.getLooper());//
		objHandler_2 = new Handler();
		mHandlerUI.postDelayed(updateUI, 0);
		mFingerprintIv.setFocusable(true);
		mFingerprintIv.setFocusableInTouchMode(true);
		
		processFPmatchBar.setVisibility(View.GONE);
		checkFileAndDir(appDirr);
		checkFileAndDir(fexDir);
		
		setEnableBt(false);
		mCancelCurOperateBtn.setEnabled(false);
		mCloseDeviceBtn.setEnabled(false);
		mOpenDeviceBtn.setEnabled(true);

		match_android_Btn.setVisibility(View.INVISIBLE);
		report_android_Btn.setVisibility(View.INVISIBLE);
		
		usb_com_spinner.setSelection(1);
	}
	private Runnable updateUI = new Runnable()   
	{  
		public void run()//运行该服务执行此函数  
		{  
			mUSBInfoTv.setText(publicString); 
			if (showImage)
			{
				displayImage();
				showImage = false;
			}
			
			if (isFinish)
			{
				setEnableBt(true);
				isFinish = false;
				addressPageInit();
				
			}
			
			mHandlerUI.postDelayed(updateUI, 1);//每3000毫秒执行一次
		}
	};	
	public int LongDunD8800_CheckEuq()
	{
        Process process = null;
        DataOutputStream os = null;
        	        
        for (int i = 0; i < 10; i++)
        {
        	String path = "/dev/ttySAC3" + i;
	        File fpath = new File(path);
	        Log.d("*** NETLH_E ***", " check path:" + path);
	        if (fpath.exists()) 
	        {
	        	String command = "chmod 777 " + path;
	        	Log.d("*** NETLH_E ***", " exec command:" + command);
	            try 
	            {
		            process = Runtime.getRuntime().exec("su");
		            os = new DataOutputStream(process.getOutputStream());
		            os.writeBytes(command+"\n");
		            os.writeBytes("exit\n");
		            os.flush();
		            process.waitFor();
		            return 1;
		        }
		        catch (Exception e) 
		        {
                    Log.d("*** NETLH_E ***", "Unexpected error - Here is what I know: "+e.getMessage());
		        }
	        }	        	
        }
        return 0;
	}	
    
	private void findViews()
	{
		mFingerprintIv = (ImageView)findViewById(R.id.Fingerprint_iv);
		mContinueSearchBtn = (Button)findViewById(R.id.continue_search_btn);
		mClearDatabaseBtn = (Button)findViewById(R.id.db_clear_btn);
		mInputFingerContinueBtn = (Button)findViewById(R.id.input_finger_continue_btn);
		mObtainImgContinueBtn = (Button)findViewById(R.id.lianxu_huoqu_iamge_btn);
		
		mUSBInfoTv = (TextView)findViewById(R.id.usb_shuchu_tv) ;

		mOpenDeviceBtn = (Button)findViewById(R.id.dakai_shebei_btn);
		mCloseDeviceBtn = (Button)findViewById(R.id.guanbi_shebei_btn);
		mCancelCurOperateBtn = (Button)findViewById(R.id.quxiao_dangqian_btn);
		mQuitBtn = (Button)findViewById(R.id.tuichu_chengxu_btn);
		match_android_Btn = (Button)findViewById(R.id.match_android_btn);
		report_android_Btn = (Button)findViewById(R.id.report_android_btn);
		processFPmatchBar = (ProgressBar)findViewById(R.id.progressBar1);  
		
		editText_1 = (EditText)findViewById(R.id.editText_id_1);
		zhengshiyong_usb_tv = (TextView )findViewById(R.id.zhengshiyong_usb_tv_id);
		//
	}
	
	private final String xAlg = "xAlg";
	private final String eAlg = "eAlg";
	private final String m_360 = "360";
	private void setPandText(PARA_TABLE ptab)
	{

		Log.d("*** NETLH_E ***", " setPandText cproductModel:" + ptab.cproductModel);
		Log.d("*** NETLH_E ***", " setPandText cSWVersion:" + ptab.cSWVersion);
		Log.d("*** NETLH_E ***", " setPandText dwFingerNum:" + ptab.dwFingerNum);
		Log.d("*** NETLH_E ***", " setPandText FingerAddress.length:" + FingerAddress.length);
		
		if (ptab.dwFingerNum != FingerAddress.length)
		{
			FingerAddress = new int[ptab.dwFingerNum + 10];
		}

		Log.d("*** NETLH_E ***", " setPandText new FingerAddress.length:" + FingerAddress.length);
		//if (null != ptab.cproductModel && ptab.cproductModel.equals(xAlg))
		if (null != ptab.cSWVersion && ptab.cSWVersion.contains(xAlg))
		{
			//xAlg
			m_alg_type = FINGER_XALG_TYPE;
			algorithm_version_spinner.setSelection(1, false);
			Log.d("*** NETLH_E ***", " setPandText dwFingerNum:" );
		}
		else
		{
			//eAlg
			m_alg_type = FINGER_EALG_TYPE;
			algorithm_version_spinner.setSelection(0, false);
		}
		
		if (null != ptab.cproductModel && ptab.cproductModel.contains(m_360))
		{
			//256_360
			images_type_spinner.setSelection(1, true);
			m_finger_size = FINGER_SIZE_256_360;
		}
		else
		{
			//256_288
			m_finger_size = FINGER_SIZE_256_288;
			images_type_spinner.setSelection(0, true);
		}
	}
	private static  String[] ADDRESS_LIST={"0", "1", "2"}; 
	private static final String[] TEMPLATE_LIST={"Buf_A", "Buf_B"};  
	private static final String[] SAFE_LEVEL_LIST={"0","1", "2", "3", "4", "5"}; 
	private static final String[] BAUD_LIST={"115200", "14400","38400", "57600",  "9600"};  
	
	
	private void initSpinners()
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, usb_com_list);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        usb_com_spinner.setAdapter(adapter);  
        usb_com_spinner.setVisibility(View.VISIBLE); 
        usb_com_spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (1 == arg2)
				{
					com_baud_spinner.setEnabled(true);
					editText_1.setEnabled(true);
					zhengshiyong_usb_tv.setText(
							getResources().getString(R.string.zhengshiyong_serial));
				}
				else
				{
					com_baud_spinner.setEnabled(false);
					editText_1.setEnabled(false);
					zhengshiyong_usb_tv.setText(
							getResources().getString(R.string.zhengshiyong_usb));
				}
				netlh.CommClose();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
        
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, BAUD_LIST);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        com_baud_spinner.setAdapter(adapter);  
        com_baud_spinner.setVisibility(View.VISIBLE); 
        com_baud_spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
    		@Override
    		public void onItemSelected(AdapterView<?> arg0, View arg1,
    				int arg2, long arg3) {
    		}

    		@Override
    		public void onNothingSelected(AdapterView<?> arg0) {
    			// TODO Auto-generated method stub
    		}
    	});
        
        
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, IMAGES_TYPE_LIST);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        images_type_spinner.setAdapter(adapter);  
        images_type_spinner.setVisibility(View.VISIBLE); 
        images_type_spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
    		@Override
    		public void onItemSelected(AdapterView<?> arg0, View arg1,
    				int arg2, long arg3) {
    		}

    		@Override
    		public void onNothingSelected(AdapterView<?> arg0) {
    			// TODO Auto-generated method stub
    		}
    	});
    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ALG_VERSION_LIST);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        algorithm_version_spinner.setAdapter(adapter);  
        algorithm_version_spinner.setVisibility(View.VISIBLE); 
        algorithm_version_spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
    		@Override
    		public void onItemSelected(AdapterView<?> arg0, View arg1,
    				int arg2, long arg3) {
    		}

    		@Override
    		public void onNothingSelected(AdapterView<?> arg0) {
    			// TODO Auto-generated method stub
    		}
    	});
	}
	
	private void setListener()
	{
		mQuitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				netlh.CommClose();
				System.exit(0);
				//Toast.makeText(ControllerAct.this,  "点击按钮：" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
			}
		}) ;		
		match_android_Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				objHandler.postDelayed(mFPmatchTasks, 1);
				//Toast.makeText(ControllerAct.this,  "????????????锟斤拷?锟斤拷o" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
			}
		}) ;
		report_android_Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				objHandler.postDelayed(mReportTasks, 1);
				//Toast.makeText(ControllerAct.this,  "????????????锟斤拷?锟斤拷o" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
			}
		}) ;		
		mOpenDeviceBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goonGetImage = false;
				gbCancel = true;

				isFinish = false;

				Log.d("*** NETLH_E ***", " NOW open device BRMicro...");
				Toast.makeText(ControllerAct.this,  "NOW open device ... " , Toast.LENGTH_SHORT).show();
				
				int ret = 0;
				String str = "Success"; 
				int usb_com_index = 0;
				
				usb_com_index = usb_com_spinner.getSelectedItemPosition();
				if (0 == usb_com_index)
				{
					ret = netlh.ConfigCommParameterUDisk(0xffffffff, 0xffffffff);
					Log.d("*** NETLH_E ***", " NOW open device BRMicro...ret :" + ret);
					if (0 == ret)
					{
						netlh.CmdDeviceGetChmod(0);
						Log.d("*** NETLH_E ***", " exec open device again ");
						Toast.makeText(ControllerAct.this,  "exec open device again " , Toast.LENGTH_SHORT).show();
						ret = netlh.ConfigCommParameterUDisk(0xffffffff, 0xffffffff);
					}
					mUploadImgCb_fg = true;
				}
				else if (1 == usb_com_index)
				{
					
					Log.d("*** NETLH_E ***", " NOW open device BRMicro via serial...");
					//String ComPath = "/dev/ttyMT0";
					//String ComPath = "/dev/ttySAC0";
					
					String ComPath = "/dev/s3c2410_serial2";
					String getPath = editText_1.getText().toString();
					
					if (!getPath.isEmpty())
					{
						ComPath = getPath;
					}
					int  BaudRate = 115200;    //波特率  9216000   57600
					BaudRate = Integer.parseInt(com_baud_spinner.getSelectedItem().toString(), 10);
					int  DataBit;    //数据位  
					int  StopBit;     //停止位  
					int  CheckMode;   //校验方式
					int  DeviceAdd;   //设备地址
					int  Password;   //联机密码 
					ret = netlh.ConfigCommParameterCom(
								ComPath, 
								BaudRate, 
								8, 
								2, 
								0, //0 NOPARITY，1 EVENPARITY，2 ODDPARITY
								0xffffffff, 
								0xffffffff);
					Log.d("*** NETLH_E ***", " NOW open device BRMicro via serial...ret :" + ret);
					if (0 == ret)
					{
						netlh.CmdDeviceGetChmod(ComPath);
						Log.d("*** NETLH_E ***", " exec open device via serial again ");
						Toast.makeText(ControllerAct.this,  "exec open device via serial again " , Toast.LENGTH_SHORT).show();
						ret = netlh.ConfigCommParameterCom(
								ComPath, 
								BaudRate, 
								8, 
								2, 
								0, //0 NOPARITY，1 EVENPARITY，2 ODDPARITY
								0xffffffff, 
								0xffffffff);
					}
					
					mUploadImgCb_fg = false;
				}
				else
				{
					return ;
				}
				
				if (0 == ret)
				{
					str = "failed.";
					Toast.makeText(ControllerAct.this,  "open device " + ((Button)v).getText() + str, Toast.LENGTH_SHORT).show();
					return ;
				}
				Toast.makeText(ControllerAct.this,  "open device " + ((Button)v).getText() + str, Toast.LENGTH_SHORT).show();
				
				setEnableBt(true);
				mCloseDeviceBtn.setEnabled(true);
				mOpenDeviceBtn.setEnabled(false);
				mCancelCurOperateBtn.setEnabled(true);
				
				
				PARA_TABLE ptab = new PARA_TABLE();
				int[] _ErrFlag = new int[10];
				ret = netlh.CmdReadParaTable(ptab, _ErrFlag);
				Log.d("*** NETLH_E ***", " exec CmdReadParaTable:"+ ret);
				if (0 == ret)
				{
					//Toast.makeText(ControllerAct.this,  "CmdReadParaTable failed ErrFlag: " +  _ErrFlag, Toast.LENGTH_SHORT).show();
				}

				ptab.dwDeviceAddress = 0XFFFFFFFF;
				g_ptab = ptab;
				setPandText(ptab);
				addressPageInit();
				
			}
		}) ;
		mCloseDeviceBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ControllerAct.this,  "" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
				goonGetImage = false;
				gbCancel = true;
				setEnableBt(false);
				mCancelCurOperateBtn.setEnabled(false);
				mCloseDeviceBtn.setEnabled(false);
				mOpenDeviceBtn.setEnabled(true);
				mCancelCurOperateBtn.setEnabled(false);
				
				isFinish = false;

				netlh.CommClose();
				PARA_TABLE ptab = new PARA_TABLE();
				setPandText(ptab);
				Log.d("*** NETLH_E ***", " exec CommClose ");
				setDefaultImage(R.drawable.ic_launcher);
			}
		}) ;	
		
		mObtainImgContinueBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(ControllerAct.this,  "开始" + ((Button)v).getText(), Toast.LENGTH_SHORT).show();
				goonGetImage = true;
				mscGetImage = 1;

				mObtainImgContinueBtn.setEnabled(false);
				setEnableBt(false);
				objHandler_2.postDelayed(mFlowTasks, 0);
			}
		}) ;

		mContinueSearchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goonGetImage = true;
				gbCancel = false;
				setEnableBt(false);
				objHandler.postDelayed(OnSearchTasks, 0);
				
			}
		}) ;
		mCancelCurOperateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				publicString = " ";
				goonGetImage = false;
				gbCancel = true;
				setEnableBt(true);
				objHandler_2.removeCallbacks(mFlowTasks); 
				objHandler.removeCallbacks(mEnrollTasks); 
				objHandler.removeCallbacks(OnSearchTasks);
				isFinish = false;
				//addressPageInit();
				setDefaultImage(R.drawable.ic_launcher);
			}
		}) ;	
		
		mClearDatabaseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int ret = 0;
				int[] _ErrFlag = new int[10];

				ret = netlh.CmdEmptyChar( _ErrFlag);        
				String result= "";
				if (0 == ret)
				{
					//failed
					result = "Connect Failed.";
				}
				else if (0 != _ErrFlag[0])
				{
					result = " failed";
					
				}
				else
				{
					result = " success" ;
				}

				addressPageInit();
				Toast.makeText(ControllerAct.this,  ((Button)v).getText() + result, Toast.LENGTH_SHORT).show();
			}
		}) ;

		mInputFingerContinueBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goonGetImage = true;
				onEnroll();
				gbCancel = false;

				mInputFingerContinueBtn.setEnabled(false);	
				setEnableBt(false);
				objHandler.postDelayed(mEnrollTasks, 1);
			}
		}) ;
		
	}
	
	private Runnable mFlowTasks = new Runnable()   
	{  
		public void run()//运行该服务执行此函数  
		{  
			//Log.d("*** NETLH_E ***", " exec getImage:" + goonGetImage);
			getImage();

			if (goonGetImage)
			{
				objHandler_2.postDelayed(mFlowTasks, mscGetImage);//每3000毫秒执行一次
			}
		}
	}; 
    @Override 
    public void onDestroy() { 
            super.onDestroy(); 
            // TODO Auto-generated method stub  
            objHandler_2.removeCallbacks(mFlowTasks);  
            objHandler.removeCallbacks(mEnrollTasks);  
    }  
    public void getImage() {
    	Log.d("=NETLH_E=", "getImage  in");
		int[] _ErrFlag = new int[10];
		int ret = 0;
		ret = netlh.CmdDetectFinger(_ErrFlag);

		Log.d("*** NETLH_E ***", " exec CmdDetectFinger: " + ret + ",ErrFlag:" +_ErrFlag[0]);
		if(1 != ret)
		{
			return ;
		}
		if(0 != _ErrFlag[0])
		{
			return;
		}
		if (mOriginImgCbFlag)
		{
			ret = netlh.CmdGetRawImage(_ErrFlag);
		}
		else
		{
			//Log.d("=NETLH_E=", "CmdGetRedressImage begin");
			ret = netlh.CmdGetRedressImage(0, _ErrFlag);
			//Log.d("=NETLH_E=", "CmdGetRedressImage end");
		}
		if (1 != ret)
		{
			//Log.d("*** NETLH_E ***", " exec CmdGetRawImage/Redress: " + ret + ",ErrFlag:" +_ErrFlag[0]);
			return;
		}
		if(0 != _ErrFlag[0])
		{
			return ;
		}

		//upload image
		ret = uploadImage();
		Log.d("=NETLH_E=", "getImage  out");
		return;
			
	}
    private final int IMAGE_SIZE = (256*288); //73728
    private boolean goonGetImage;

    private final String mFingerImagePath= "/mnt/sdcard/Finger.bmp";
    public int uploadImage() {
    	int ret = 0;
    	byte[] pRawData = new byte[IMAGE_SIZE];
    	String path= "";
    	if (mOriginImgCbFlag)
    	{
    		ret = netlh.CmdUpLoadRawImage(null);
    		//path = "/mnt/sdcard/RawImage.bmp";
    	}
    	else
    	{
    		if (FINGER_SIZE_256_360 == m_finger_size)
    		{
    			ret = netlh.CmdUpLoadRedressImage256x360(null);
    		}
    		else
    		{
    			ret = netlh.CmdUpLoadRedressImage(null);
    		}
    		
    		//path = "/mnt/sdcard/RedressImage.bmp";
    	}
    	Log.d("*** NETLH_E ***", " exec CmdUpLoadRawImage/Redress: " + ret);

        Bitmap bmpDefaultPic;
        bmpDefaultPic = BitmapFactory.decodeFile(mFingerImagePath, null);
        mFingerprintIv.setImageBitmap(bmpDefaultPic);	

    	return ret;
    }
    public void writeFile(String path, byte[] pRawData)
    {
        try
        {
            
	        File file;  
	        FileOutputStream out;                     
	        file = new File(path);  
	        file.createNewFile();  

	        out = new FileOutputStream(file);  
	        out.write(pRawData);                     
	        out.close();  
        }
        catch (IOException e)
        {  
        	Log.d("*** NETLH_E ***", " write " + path + " error.");
        }
        Log.d("*** NETLH_E ***", " write " + path + " finish");	        		  
    }
    public int readFileData(String fileName, byte [] buffer)
    {  
    	try{  
    		FileInputStream fin = openFileInput(fileName);//获得FileInputStream对象  
    		int length = fin.available();//获取文件长度  
    		
    		fin.read(buffer);//将文件内容读入到byte数组中                    
    		fin.close();                    //关闭文件输入流
    		return 1;
    	}  
    	catch(Exception e){  
    		e.printStackTrace(); //捕获异常并打印  
    	}  
    	return 0;//返回读到的数据字符串       
	}  

    public int getNextFreeAddress()
    {
    	int i = 0;
    	int len = FingerAddress.length;
    	for(i = 0; i < len; i++)
    	{
    		if (0 == FingerAddress[i])
    		{
    			return i;
    		}
    	}
    	return 0;
    }
	private Runnable mEnrollTasks = new Runnable()   
	{  
		public void run()//运行该服务执行此函数  
		{  
			int ret = 0;
			ret = Enroll();
			
			if (goonGetImage)
			{
				if (!gbCancel)
					objHandler.postDelayed(mEnrollTasks, 1);//每3000毫秒执行一次
				
			}
			else
				isFinish = true;
		}
	};
	
    public void onEnroll()
    {
    	nextAddress = getNextFreeAddress();
    	
    }
	public int Enroll()
	{ 
		int  ret=0;
		int[]  _ErrFlag = new int[10];
		int[]  _RetMbIndex = new int[10];
		int[] _RetScore = new int[10];
		boolean bReplace = false;
		byte[] gImgData = new byte[IMAGE_SIZE];
		Message message=new Message();  
		message.what=1;  

		//Log.d(TAG, " Enroll 01 "); 
		if (FingerAddress[nextAddress] == 1)
		{
			publicString = "用户 " + nextAddress + "已经存在，将覆盖。";
			bReplace = true;
		}
		else 	 
		{
			bReplace = false;
		}
		
		
		int iFingerNum = 0;
		int iBuffer = 0;
		
		int iFingerCount = 2;
		if (FINGER_XALG_TYPE == m_alg_type)
		{
			iFingerCount = 3;
		}
	
		//Log.d(TAG, " Enroll 02 ");
		while (iFingerNum < iFingerCount)
		{

			publicString  = "请将手指平放在传感器上..." ;

			do
			{
				if (gbCancel)
					return 0;
				ret = netlh.CmdDetectFinger(_ErrFlag);
				if(1 != ret)
				{
		           return 1;
				}
				if(_ErrFlag[0]== 0)
				{
				ret = netlh.CmdGetRedressImage(0, _ErrFlag);
		    	if(1 != ret)
				{

					publicString  = "通信失败!" ;
	                return 1;
					}
				}
		
			}while( _ErrFlag[0] != 0);
			publicString  = "读取指纹成功..." ;
			
		if (mUploadImgCb_fg)
		{
			publicString  = "图像录入成功！正在上传..." ;

			if (FINGER_SIZE_256_360 == m_finger_size)
			{
				ret = netlh.CmdUpLoadRedressImage256x360(null);
			}
			else
			{
				ret = netlh.CmdUpLoadRedressImage(null);  //上传图象
			}
			
			if( 1 != ret)
			{
				publicString  = "图像上传错误" ;

				return 1;
			}
			publicString  = "图像上传成功。" ;

		}
		Log.d(TAG, " Enroll 03 ");
		showImage = true;
		mHandlerUI.postDelayed(updateUI, 0);
		
		iBuffer = CHAR_BUFFER_A;
		if (iFingerNum == 1)
			iBuffer = CHAR_BUFFER_B;
		if (iFingerNum == 2)
			iBuffer = CHAR_BUFFER_C;

		publicString  = "生成模板.." ;
 		
		ret = netlh.CmdGenChar(iBuffer, _ErrFlag);
		if(1 != ret)  //生成模板
		{
			publicString  = "通信失败" ;

	        return 1;
		}
	
		if ( _ErrFlag[0] != CMD_RT_OK)
		{


			publicString  = "生成模板失败！请重新录入！" ;

			continue;			 
		}
		
	    iFingerNum++; 

		publicString  = "请拿开手指..." ;

		do
		{
			if (gbCancel)
				return 0;			
			ret = netlh.CmdDetectFinger(_ErrFlag);
			if(1 != ret)
			{

	            return 1;
			}
			showImage = true;
			mHandlerUI.postDelayed(updateUI, 0);
			
			mySleep(100);
		    
		}while( _ErrFlag[0] == CMD_RT_OK);   
		mySleep(500);
		
	}
	publicString = "合并特征...";

	ret=netlh.CmdMergeChar(_RetScore, _ErrFlag);  //合并特征
	
	if(1 != ret)
	{
		Log.d(TAG, " CmdMergeChar api failed");
		return 1;
	}
	
	if(_ErrFlag[0] !=CMD_RT_OK)
	{

		publicString  = "特征合并失败!" ;
		mySleep(sleepms);
		return 1;
	}

	publicString  = "合并特征成功, 存放模板..." ;
	mHandlerUI.postDelayed(updateUI, 0);
	ret=netlh.CmdStoreChar( nextAddress, _RetMbIndex, _RetScore, _ErrFlag);    //存放模板
	
	Log.d(TAG, " Enroll 1 ");
	if(1 != ret)
	{  
		Log.d(TAG, " CmdStoreChar api failed");
		return 1;
	}
	Log.d(TAG, " Enroll 2 ");
	
	if(_ErrFlag[0] !=CMD_RT_OK)
	{
		publicString  = "指纹重复,用户添加取消！" ;
		mySleep(sleepms);
		Log.d(TAG, " Enroll 3 ");
		return 1;
	}
	Log.d(TAG, " Enroll 4 ");
	if (bReplace)
	{		
	}
	else
	{
		FingerAddress[nextAddress] = 1;
	}	
	Log.d(TAG, " Enroll 5 ");
	String  ss = "用户 " + nextAddress  +"添加成功!";

	publicString  = ss ;
	
	nextAddress = getNextFreeAddress();
	mySleep(sleepms);
	mHandlerUI.postDelayed(updateUI, 0);
	Log.d(TAG, " Enroll 6 ");
	return 0;
	}
	private int sleepms = 800;
	private void addressPageInit()
	{
		int ret = 0;
		int[]  _ErrFlag = new int[10];
		byte[] gMBIndex = new byte[17 + 820 + 2 + 10];
		//FingerAddress
		for(int i = 0; i < FingerAddress.length; i += 250)
		{
			ret = netlh.CmdGetMBIndex(gMBIndex, i, 250, _ErrFlag);
			Log.d("*** NETLH_E ***", "Exec CmdGetMBIndex ret =" + ret);
			
			if(1 == ret && _ErrFlag[0] == CMD_RT_OK)
			{
				for (int y = 0; y < 250; y++)
				{
					Log.d("*** NETLH_E ***", "Exec CmdGetMBIndex gMBIndex[" + (y+i) + "]=" + gMBIndex[y]);
					if (0 != gMBIndex[y])
						FingerAddress[i + y] = 1;
					else
						FingerAddress[i + y] = 0;
				}
			}
			mySleep(400);
		}
		addressPageDisplay();
	}
	private void addressPageDisplay()
	{
		List<String> allContent; 
		allContent = new ArrayList<String>(); 
		
		for (int i = 0; i < FingerAddress.length; i++)
		{
			if (0 != FingerAddress[i])
			{
				allContent.add("" + i);
				nextAddress = i + 1;
			}
		}

	}
	
    private void setEnableBt(boolean en)
    {

    	mContinueSearchBtn.setEnabled(en) ;

    	mClearDatabaseBtn.setEnabled(en) ;

    	mInputFingerContinueBtn.setEnabled(en) ;

    	mObtainImgContinueBtn.setEnabled(en) ;    	    	
    }
	private Runnable OnVerifyTasks = new Runnable()   
	{  
		public void run()//运行该服务执行此函数  
		{  
			int ret = 0;
			ret = Verify();
			if (0 == ret)
			{
				if (!gbCancel)
					objHandler_2.postDelayed(OnVerifyTasks, 1);//每3000毫秒执行一次
			}
			else 
			{
				setEnableBt(true);
			}
		}
	};
	


	private int Verify()
	{ 
		int[]  _ErrFlag = new int[10];
		int[] _RetScore = new int[10];
		int  ret = 1;

		publicString ="请将手指平放在传感器上...";
		ret = netlh.CmdDetectFinger(_ErrFlag);
		 if(1 != ret)
		 {
			 publicString = "通信失败!";
		   	// pMyDlg->EnableBtn(true);
		     return 0;
		 }
	
		 if(_ErrFlag[0] != CMD_RT_OK)
		 {
		   return 0;
		 }
		 ret=netlh.CmdGetRedressImage(0, _ErrFlag); //获取图象  
		 
		 if(_ErrFlag[0] !=CMD_RT_OK)
		 {
		    return 0;
		 }

		 	
		 
		if (mUploadImgCb_fg)
		{
			if (FINGER_SIZE_256_360 == m_finger_size)
			{
				ret = netlh.CmdUpLoadRedressImage256x360(null);
			}
			else
			{
				ret = netlh.CmdUpLoadRedressImage(null);  //上传图象
			} 
		
			if( 1 != ret)
			{
				publicString = "图像上传错误";
				return 0;
			}
			if (bPreviewImg)
			{
				//String path = "/mnt/sdcard/RedressImage.bmp";
		
				Bitmap bmpDefaultPic;
				bmpDefaultPic = BitmapFactory.decodeFile(mFingerImagePath,null);
				mFingerprintIv.setImageBitmap(bmpDefaultPic);	
			}
		}

		publicString ="图像录入成功,比对中……" ;
		ret = netlh.CmdGenChar(CHAR_BUFFER_A, _ErrFlag);
		if(1 != ret)  //生成模板
		{
			publicString = "通信失败!" ;
		     return 0;
		}
		if (_ErrFlag[0] != CMD_RT_OK)
		{
			publicString = "特征提取失败!" ;
			return 0;
		}
		publicString = "特征提取..." ;
		ret = netlh.CmdVerifyChar(CHAR_BUFFER_A, currIndexAddress, _RetScore, _ErrFlag);
		if(1 != ret)
		{
			publicString = "特征提取失败!" ;
			return 0;
		}
	
		if (_ErrFlag[0] != CMD_RT_OK)
		{
			publicString = "指纹比对失败!" ;
			return -1;
		}

		publicString = "比对成功！" ;

	  return 1;
		
	}
	private Runnable OnSearchTasks = new Runnable()   
	{  
		public void run()//运行该服务执行此函数  
		{  
			int ret = 0;
			ret = onSearch();

			if(!goonGetImage)
			{
				if (0 == ret)
				{
					if (!gbCancel)
						objHandler.postDelayed(OnSearchTasks, 1);//每3000毫秒执行一次
				}
				else 
				{
					//setEnableBt(true);
				}
			}
			else
			{
				if (!gbCancel)
				{
					objHandler.postDelayed(OnSearchTasks, 1);//每3000毫秒执行一次

				}
						
			}
		}
	};
	private int onSearch()
	{
		int[]  _ErrFlag = new int[10];
		int[] _RetScore = new int[10];
		int  ret = 1;

		publicString ="请将手指平放在传感器上...";

		ret = netlh.CmdDetectFinger(_ErrFlag);
		 if(1 != ret)
		 {
			 publicString = "通信失败!";
		   	// pMyDlg->EnableBtn(true);
		     return 0;
		 }
	
		 if(_ErrFlag[0] != CMD_RT_OK)
		 {
		   return -2;
		 }
		 ret=netlh.CmdGetRedressImage(0, _ErrFlag); //获取图象  
		 
		 if(1 != ret)
		 {
			 publicString = "通信失败!";
		     return 0;
		 }
		 
		 if(_ErrFlag[0] != CMD_RT_OK)
		 {
		    return -2;
		 }
		 if (mUploadImgCb_fg)
		 {
			 if (FINGER_SIZE_256_360 == m_finger_size)
			 {
			 	ret = netlh.CmdUpLoadRedressImage256x360(null);
			 }
			 else
			 {
			 	ret = netlh.CmdUpLoadRedressImage(null);  //上传图象
			 }
					
			if( 1 != ret)
			{
				publicString = "图像上传错误";
				//pMyDlg->EnableBtn(true);
				return 0;
			}
			
			if (bPreviewImg)
			{
				showImage = true;
				mHandlerUI.postDelayed(updateUI, 0);
			}	
		 }
		 publicString ="图像录入成功,比对中……" ;
		ret = netlh.CmdGenChar(CHAR_BUFFER_A, _ErrFlag);
		if(1 != ret)  //生成模板
		{
			publicString = "通信失败!" ;
		     return 0;
		}
		if (_ErrFlag[0] != CMD_RT_OK)
		{
			publicString = "特征提取失败!" ;
			return 0;
		}
		publicString = "特征提取..." ;
		
		int[] _RetMbIndex = new int[10];
		ret = netlh.CmdSearchChar(CHAR_BUFFER_A, _RetMbIndex,_RetScore,_ErrFlag);
		if(1 != ret)
		{
			publicString = "通信失败!" ;
			return 0;
		}
	
		if (_ErrFlag[0] != CMD_RT_OK)
		{
			publicString = "没有找到相同的手指!" ;
			mySleep(sleepms);
			return -1;
		}

		publicString = "找到相同手指！ID为" +  _RetMbIndex[0];
		mySleep(sleepms);
		return 1;		
	}
	private void mySleep(int sec)
	{
	    try {
			thread.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
	}
	public void setDefaultImage(int id)
	{
		mFingerprintIv.setImageResource(id);		
	}
	public void setDefaultImage(String path)
	{

		Bitmap bmpDefaultPic;
		bmpDefaultPic = BitmapFactory.decodeFile(path,null);
		mFingerprintIv.setImageResource(R.drawable.ic_launcher);			
	}	
	public void displayImage()
	{
		//String path = "/mnt/sdcard/RedressImage.bmp";
		
		Bitmap bmpDefaultPic;
		bmpDefaultPic = BitmapFactory.decodeFile(mFingerImagePath,null);
		mFingerprintIv.setImageBitmap(bmpDefaultPic);	
	}
	
	
	/* For FPmatch demon :allFileContents */
	
	private List<File> mFileList = new ArrayList<File>(); 
	public List<String> allFileContents = new ArrayList<String>();
	public int allFileNum = 0;
	//AdLoad	m_fpCore = new AdLoad();
	
	public boolean getAllFileNameByPath(String dir, String end1, String end2)
	{
		boolean bg = false;
		
		return bg;
	}
	public boolean readFileData()
	{
		return true;
	}
	public void FPmatch_process()
	{
		Log.d("=FPmatch=", " FPmatch_process in " );
		
		String fexDir = "mnt/sdcard/FPmatch/fex_char";
		String ExtensionERR = ".err";
		String ExtensionOK = "OK";
		String ExtensionERRs = "ERR";
		String ExtensionCHR = ".chr";
		String ExtensionBK = "/";
		//File f = new File(fexDir);
		
		fileNum = 0;
		GetFiles(fexDir, ExtensionCHR, true);
		Log.d("=FPmatch=", " FPmatch_process GetFiles fileNum = " + fileNum );
		//List<String> lstFile
		int len = 0;
		/*
		 * for (String filename : lstFile)
		{
			len++;
			Log.d("=FPmatch=", " List<String> lstFile [" + len + "] : [" + filename + "]");
		}
		*/
		byte [] bufferA;
		byte [] bufferB;
		int MAX = 810;
		int m_nRet = 0;
		int[] m_nSim =  new int[1];

		AdLoad	m_fpCore = new AdLoad();
		
		
		int length = lstFile.size();
		Log.d("=FPmatch=", " FPmatch_process start match : "  + length);
		for (int i = 0; i < fileNum; i++)
		//for (int i = 0; i < 5; i++)
		{
			try {
				bufferA = readFileSdcardFile(lstFile.get(i), MAX);
			} catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				continue;
			}
			for(int j = i + 1; j < fileNum; j++)
			//for(int j = i + 1; j < 5; j++)
			{
				Log.d("=FPmatch=", " List<String> lstFile [" + i + "] : [" + lstFile.get(i) + "]");
				Log.d("=FPmatch=", " List<String> lstFile [" + j + "] : [" + lstFile.get(j) + "]");
				try {
					bufferB = readFileSdcardFile(lstFile.get(j), MAX);
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				m_nRet = m_fpCore.FPMatch(bufferA, bufferB, 3, m_nSim);
				//Log.d("=FPmatch=", " FPMatch :m_nRet=" + m_nRet + ",m_nSim= " + m_nSim[0] );
				if (0 != m_nRet)
				{
					return ;
				}

				String output = "";
				output = lstFile.get(i).substring(lstFile.get(i).lastIndexOf(ExtensionBK) + 1)
						+ "_" + lstFile.get(j).substring(lstFile.get(j).lastIndexOf(ExtensionBK) + 1);	
				if (1 == m_nSim[0])
				{
					// match 
					output += ExtensionOK;
					matchFiles.add(output);
					Log.d("=FPmatch=", " matchFiles.add : " + output );
				}
				else
				{
					// not match 
					output += ExtensionERRs;
					unMatchFiles.add(output);	
					Log.d("=FPmatch=", " unMatchFiles.add : " + output );
				}
			}
			
		}
		Log.d("=FPmatch=", " FPmatch_process end match  " );
	}

	public byte []  readFileSdcardFile(String fileName, int max) throws IOException
	{ 
		String res=""; 
		byte [] buffer =  new byte[max];
		try
		{
			FileInputStream fin = new FileInputStream(fileName); 
			int length = fin.available(); 
			if (length < max)
			{
				Log.d("=FPmatch=", "[ " + fileName + "] is small,size: " + length + ",max is " + max);
				return buffer;
			}
			fin.read(buffer);     
			//res = EncodingUtils.getString(buffer, "UTF-8"); 
			fin.close();     
		} 
		catch(Exception e)
		{ 
			e.printStackTrace(); 
		} 
		return buffer;
	}

	public String  readFileSdcardFile(String fileName)
	{ 
		byte [] buffer;
		String str = "";
		try
		{
			FileInputStream fin = new FileInputStream(fileName); 
			int length = fin.available(); 
			buffer =  new byte[length];
			fin.read(buffer);     
			str = EncodingUtils.getString(buffer, "UTF-8"); 
			fin.close();   
		} 
		catch(Exception e)
		{ 
			e.printStackTrace(); 
		} 
		return str;
	}
	
	private final int CLEAR_MESSAGE = 0;
	private final int DISABLE_FPMATCH_BT = 1;
	private final int ENABLE_FPMATCH_BT = 2;
	private final int DISABLE_REPORT_BT = 3;
	private final int ENABLE_REPORT_BT = 4;
	private final int UPDATE_PROCESS_BAR = 5;
	private final int INIT_PROCESS_BAR = 6;
	private final int END_PROCESS_BAR = 7;
	private final int MESSAGE_INFO = 8;
	private final int FULL_PROCESS_BAR = 9;
	
	private Runnable mFPmatchTasks = new Runnable()   
	{  
		public void run()
		{
			checkFileAndDir(appDirr);
			checkFileAndDir(fexDir);
			sendMessage2Ui(DISABLE_FPMATCH_BT);
			getCompareCount();
			
			sendMessage2Ui(INIT_PROCESS_BAR);
			//sendCmdId(DISABLE_FPMATCH_BT);
			//sendCmdId(INIT_PROCESS_BAR);
			
			deleteFile(appDirr, reportAdPath);

			//FPmatch_process();
			FPmatch_process2();
			
			sendMessage2Ui(ENABLE_FPMATCH_BT);
			sendMessage2Ui(END_PROCESS_BAR);
			//sendCmdId(ENABLE_FPMATCH_BT);
			//sendCmdId(END_PROCESS_BAR);
			//objHandler.postDelayed(mFPmatchTasks, 1);
		}
	};
	
	public void FPmatch_process2()
	{
		ERRChar_NUM = 0;
		ERRMatch_NUM = 0;
		OKMatch_NUM = 0;
		processBarCount = 0;
		GetFiles(fexDir, ExtensionCHR, ExtensionERR, true) ;
		Log.d("=FPmatch=", " ERRChar_NUM:" + ERRChar_NUM + ", ERRMatch_NUM:" + ERRMatch_NUM + 
				",OKMatch_NUM:" + OKMatch_NUM + ",processBarCount=" + processBarCount + " ,chrCpmpareNum=" + chrCpmpareNum);
	}
	public List<File> getFile(File file)
	{
		File[] fileArray = file.listFiles();
		for (File f : fileArray) 
		{
			if(f.isFile())
			{
				mFileList.add(f);
			}else
			{
				getFile(f);
			}
		}
		return mFileList;

	}
		
	
	private List<String> lstFile = new ArrayList<String>(); 
	private List<String> errLstFile = new ArrayList<String>(); 
	private List<String> matchFiles = new ArrayList<String>(); 
	private List<String> unMatchFiles = new ArrayList<String>(); 
	
	private int ERRChar_NUM = 0;
	private int ERRMatch_NUM = 0;
	private int OKMatch_NUM = 0;
	
	private int chrCpmpareNum = 0;
	
	int fileNum = 0;
	String appDirr = "mnt/sdcard/FPmatch";
	String fexDir = "mnt/sdcard/FPmatch/fex_char";
	String ExtensionERR = ".err";
	String ExtensionOK = "OK";
	String ExtensionERRs = "ERR";
	String ExtensionCHR = ".chr";
	String ExtensionBK = "/";
	String reportAdPath = "ReportAd.txt";
	String matchReportAdPath = "MatchReportAd.log";
	public void GetFiles(String Path, String Extension,String Extension2, boolean IsIterative)  
	{

		fileNum = 0;
		lstFile.clear();
	    File[] files = new File(Path).listFiles();
	    for (int i = 0; i < files.length; i++)
	    {
	        File f = files[i];
	        
	        if (f.isDirectory() && f.getPath().indexOf("/.") == -1) 
	        {
	        	writefile("", f.getPath() + "/" +  reportAdPath);
	            GetFiles(f.getPath(), Extension, Extension2, IsIterative);
	        }
	        else if(f.isFile())
	        {
	            if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension))
	            {
	            	fileNum++;
	            	Log.d("=FPmatch=", " lstFile.add [" + fileNum + "] : [" + f.getPath() + "]");
	                lstFile.add(f.getPath());
	            }
	            else
	            if (f.getPath().substring(f.getPath().length() - Extension2.length()).equals(Extension2))
	            {
	            	//Log.d("=FPmatch=", " lstFile.add [" + fileNum + "] : [" + f.getPath() + "]");
	            	//errLstFile.add(f.getPath());
	            	String currdir = f.getPath().substring(0, f.getPath().lastIndexOf(ExtensionBK));
	            	String currfile = f.getPath().substring(f.getPath().lastIndexOf(ExtensionBK) + 1);
	            	Log.d("=FPmatch=", ".err: [" + currfile + "] : [" + currdir + "]");
	            	writefile(currfile +"\n", currdir + "/" +  reportAdPath);
	            	ERRChar_NUM++;
	            }   
	            if (!IsIterative)
	                break;
	        }
	        
	    }

		byte [] bufferA;
		byte [] bufferB;
		int MAX = 810;
		int m_nRet = 0;
		int[] m_nSim =  new int[1];

		if(2 > fileNum)
		{
			return ;
		}
		AdLoad	m_fpCore = new AdLoad();
		
		for (int i = 0; i < fileNum; i++)
		{
			try {
				bufferA = readFileSdcardFile(lstFile.get(i), MAX);
			} catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				continue;
			}
			for(int j = i + 1; j < fileNum; j++)
			{
				//Log.d("=FPmatch=", " List<String> lstFile [" + i + "] : [" + lstFile.get(i) + "]");
				//Log.d("=FPmatch=", " List<String> lstFile [" + j + "] : [" + lstFile.get(j) + "]");
				try {
					bufferB = readFileSdcardFile(lstFile.get(j), MAX);
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				m_nRet = m_fpCore.FPMatch(bufferA, bufferB, 1, m_nSim);
				//Log.d("=FPmatch=", " FPMatch :m_nRet=" + m_nRet + ",m_nSim= " + m_nSim[0] );
				if (0 != m_nRet)
				{
					continue;
					//return ;
				}

				String output = "";
				output = lstFile.get(i).substring(lstFile.get(i).lastIndexOf(ExtensionBK) + 1)
						+ "_" + lstFile.get(j).substring(lstFile.get(j).lastIndexOf(ExtensionBK) + 1);	
				if (1 == m_nSim[0])
				{
					// match 
					output += ExtensionOK;
					//matchFiles.add(output);
					//Log.d("=FPmatch=", " matchFiles.add : " + output );	
					OKMatch_NUM++;
				}
				else
				{
					// not match 
					output += ExtensionERRs;
					//unMatchFiles.add(output);	
					//Log.d("=FPmatch=", " unMatchFiles.add : " + output );	
					ERRMatch_NUM++;
				}

            	String currdir = lstFile.get(i).substring(0, lstFile.get(i).lastIndexOf(ExtensionBK));

            	Log.d("=FPmatch=", "[" + processBarCount +"]un/matchFiles reportad.txt: [" + output + "] -> [" + currdir + "/" + reportAdPath + "]");
            	writefile(output +"\n", currdir + "/" +  reportAdPath);		
            	if (processBarCount >= chrCpmpareNum)
            	{
            		sendMessage2Ui(CLEAR_MESSAGE);
            	}
            	sendMessage2Ui(UPDATE_PROCESS_BAR);
			}
			
		}
	}	
	public void GetFiles(String Path, String Extension, boolean IsIterative)  
	{
	    File[] files = new File(Path).listFiles();
	    for (int i = 0; i < files.length; i++)
	    {
	        File f = files[i];
	        
	        if (f.isDirectory() && f.getPath().indexOf("/.") == -1) 
	        {
	            GetFiles(f.getPath(), Extension, IsIterative);
	        }
	        else if(f.isFile())
	        {
	            if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension))
	            {
	            	fileNum++;
	            	//Log.d("=FPmatch=", " lstFile.add [" + fileNum + "] : [" + f.getPath() + "]");
	                lstFile.add(f.getPath());
	            }
	            
	            if (!IsIterative)
	                break;
	        }
	        
	    }
	
	}	
	  public void writefile(String str,String path )  
	  {  
		//Log.d("=FPmatch=", " write [" + str + "] -->[" + path + "]");
		File file;  
		FileOutputStream out;  
		 try {  
			 	file = new File(path);  
			 	if(!file.exists())
			 		file.createNewFile();  
				out = new FileOutputStream(file, true);  
				String infoToWrite = str;  
				out.write(infoToWrite.getBytes());  
				out.close();   
		   } catch (IOException e) {  
			  Log.d("=FPmatch=",e.toString());  
				 
		   }  
	  } 
	public void deleteFile(String path, String fileName)
	{
		File file = new File(path);

		File[] fileArray = file.listFiles();
		for (File f : fileArray) 
		{
			if(f.isFile())
			{
				if (f.getPath().substring(f.getPath().length() - fileName.length()).equals(fileName))
				{
					f.delete();
					sendMessage2Ui(UPDATE_PROCESS_BAR);
				}
			}else
			{
				deleteFile(f.getPath(), fileName);
			}
		}
		return ;
	}
	public int getCompareCount()
	{
		chrCpmpareNum = 0;
		getExtersionFileNum(fexDir, ExtensionCHR);
		return  chrCpmpareNum;
	}
	public void getExtersionFileNum(String path, String fileName)
	{
		fileNum = 0;
		File file = new File(path);

		File[] fileArray = file.listFiles();
		for (File f : fileArray) 
		{
			if(f.isFile())
			{
				if (f.getPath().substring(f.getPath().length() - fileName.length()).equals(fileName))
				{
					fileNum++;
				}
			}else
			{
				getExtersionFileNum(f.getPath(), fileName);
			}
		}

		if(fileNum > 1)
		{
			chrCpmpareNum += (fileNum * (fileNum - 1)) / 2;
			Log.d("=FPmatch=", "fileNum :" + fileNum + ", chrCpmpareNum=" + chrCpmpareNum);  
		}

		return ;
	}
	public void seatchApendFile2File(String des, String sour, String path)
	{
		File file = new File(path);

		File[] fileArray = file.listFiles();
		for (File f : fileArray) 
		{
			if(f.isFile())
			{
				if (f.getPath().substring(f.getPath().length() - sour.length()).equals(sour))
				{
					writefile(readFileSdcardFile(f.getPath()) , des);
					sendMessage2Ui(UPDATE_PROCESS_BAR);
				}
			}else
			{
				seatchApendFile2File(des, sour, f.getPath());
			}
		}
		return ;

	}
	private Runnable mReportTasks = new Runnable()   
	{  
		public void run()
		{  
			checkFileAndDir(appDirr);
			checkFileAndDir(fexDir);
			
			sendMessage2Ui(DISABLE_REPORT_BT);
			chrCpmpareNum = 1000;
			sendMessage2Ui(INIT_PROCESS_BAR);
			//sendMessage2Ui(UPDATE_PROCESS_BAR);
			deleteFile(appDirr, matchReportAdPath);

			seatchApendFile2File(fexDir + "/" + matchReportAdPath, reportAdPath, fexDir);
			String allInfo = "\n";
			allInfo += "ERRChar_NUM=" + ERRChar_NUM + "\n";
			allInfo += "ERRMatch_NUM=" + ERRMatch_NUM + "\n";
			allInfo += "OKMatch_NUM=" + OKMatch_NUM  + "\n";
			writefile(allInfo, fexDir + "/" + matchReportAdPath);
			//sendMessage2Ui(UPDATE_PROCESS_BAR);
			sendMessage2Ui(FULL_PROCESS_BAR);
			sendMessage2Ui(ENABLE_REPORT_BT);
			//sendMessage2Ui(END_PROCESS_BAR);
			
			//objHandler.postDelayed(mReportTasks, 1);
		}
	};
	private int cmdid = 0;
	private int processBarCount = 1;
	
	
	private void sendMessage2Ui(int id)
	{
		if (CLEAR_MESSAGE == id)
		{
			mUiHandler.removeMessages(CLEAR_MESSAGE);
			return ;
		}
		Message msg = new Message();  
		msg.what = id;  
		mUiHandler.sendMessage(msg);  

		Log.d("=FPmatch=", "sendMessage2Ui :" + id);  

	}
	private Handler mUiHandler = new Handler()
	{  
		public void handleMessage(Message msg)
		{  
			switch (msg.what) 
			{  
				case DISABLE_FPMATCH_BT:  
					match_android_Btn.setEnabled(false);
					break;  
				case ENABLE_FPMATCH_BT:  
					match_android_Btn.setEnabled(true);
					Toast.makeText(ControllerAct.this, "特征比对完毕！", Toast.LENGTH_SHORT).show();
					//mUSBInfoTv.setText("ENABLE_FPMATCH_BT"); 
					break;  		
				case DISABLE_REPORT_BT:  
					report_android_Btn.setEnabled(false);
					break;  
				case ENABLE_REPORT_BT:  
					report_android_Btn.setEnabled(true);
					Toast.makeText(ControllerAct.this, "报表生成完毕。", Toast.LENGTH_SHORT).show();
					break;  
				case UPDATE_PROCESS_BAR:  
					processFPmatchBar.setProgress(++processBarCount);
					Log.d("=FPmatch=", "setProgress :" + processBarCount);
					//mUSBInfoTv.setText("UPDATE_PROCESS_BAR " + processBarCount ); 	
					break;  
					
				case INIT_PROCESS_BAR:
					processBarCount = 0;
					processFPmatchBar.setMax(chrCpmpareNum);
					Log.d("=FPmatch=", "processFPmatchBar.setMax :" + chrCpmpareNum);
					processFPmatchBar.setVisibility(View.VISIBLE);
					processFPmatchBar.setProgress(0);
					//mUSBInfoTv.setText("INIT_PROCESS_BAR"); 				
					break;  	
				case END_PROCESS_BAR: 
					processBarCount = 0;
					processFPmatchBar.setMax(0);
					//mUSBInfoTv.setText("END_PROCESS_BAR"); 
					processFPmatchBar.setProgress(0);
					processFPmatchBar.setVisibility(View.GONE);
					
					break; 	
				case FULL_PROCESS_BAR:
					processFPmatchBar.setProgress(1000);
					sendMessage2Ui(END_PROCESS_BAR);
					break;
				case MESSAGE_INFO:
					break;
			}  
		}  
	}; 
    private void checkFileAndDir(String dir)
    {
		File ondir = new File(dir);
		//判断文件夹是否存在,如果不存在则创建文件夹
		if (!ondir.exists()) 
		{
		    ondir.mkdir();
		    Log.d ("=FPmatch=",  "mkdir " + dir);
		    String result = "Dir[" + dir + "] is not exist,please check.";
		    
		    Toast.makeText(ControllerAct.this, result, Toast.LENGTH_SHORT).show();
		}
    }
    
    // v4.x
    private static  String[] usb_com_list = new String[2]; 
    private static  String[] IMAGES_TYPE_LIST = {"256*288", "256*360"};  
    private static  String[] ALG_VERSION_LIST = {"eAlg","xAlg"};
    public void meminit()
    {
    	usb_com_list[0] = getResources().getString(R.string.usb_communication_type);
    	usb_com_list[1] = getResources().getString(R.string.com_communication_type);
    	IMAGES_TYPE_LIST[0] = "256*288" + getResources().getString(R.string.image_type);
    	IMAGES_TYPE_LIST[1] = "256*360" + getResources().getString(R.string.image_type);
    	ALG_VERSION_LIST[0] = "eAlg" + getResources().getString(R.string.alg_type);
    	ALG_VERSION_LIST[1] = "xAlg" + getResources().getString(R.string.alg_type);  
    	
    }
    public void findViews2()
    {
    	usb_com_spinner = (Spinner)findViewById(R.id.usb_com_spinner_id);
    	com_baud_spinner = (Spinner)findViewById(R.id.com_baud_spinner_id);
    	images_type_spinner = (Spinner)findViewById(R.id.images_type_spinner_id); 
    	algorithm_version_spinner = (Spinner)findViewById(R.id.algorithm_version_spinner_id); 
    }
}
