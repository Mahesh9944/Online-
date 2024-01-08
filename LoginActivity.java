package com.Simmu.Mods;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner; 
import android.widget.ArrayAdapter;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.content.Intent;
import com.Simmu.Mods.drawing.Internal;
import android.net.Uri;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.os.Handler;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import com.Simmu.Mods.image.Prefs;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.view.MotionEvent;
import android.annotation.SuppressLint;
import android.widget.TextView;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.Manifest;
import android.util.Log;
import android.app.ProgressDialog;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import java.util.Locale;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.widget.RelativeLayout;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.content.ClipboardManager;
import android.content.ClipData;


public class LoginActivity extends Activity {

    private String TAG;
    
    static {
        System.loadLibrary("YadavXModer");
    }
    
    private void setLightStatusBar(Activity activity) {
        activity.getWindow().setStatusBarColor(Color.parseColor("#FF121212"));
        activity.getWindow().setNavigationBarColor(Color.parseColor("#FF121212"));
    }
    
    
    
    private static final String USER = "USER";
    public static String USERKEY;
    static boolean crackerdetect = false;
    CardView btnSignIn;
    private Prefs prefs;
    RelativeLayout ltrltr;
    
    String LoadLang(String a, String b) {
        SharedPreferences sp = this.getSharedPreferences("MyLang", Context.MODE_PRIVATE);
        return sp.getString(a,b);
    }
    void SetLang(String a, String b) {
        SharedPreferences sp = this.getSharedPreferences("MyLang", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(a, b);
        ed.apply();
    }
    public static String a[] = GetKey().split(" ");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLightStatusBar(this);
        setLocale(this,LoadLang("MyLang","en"));
        setContentView(R.layout.activity_login);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
		int linkOpenCount = preferences.getInt("linkOpenCount", 0);

		if (linkOpenCount < 2) {
			// Open the Facebook link
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+IFFyEjaG5M4xNWM9"));
			startActivity(intent);

			// Increment the link open count
			SharedPreferences.Editor editor = preferences.edit();
			editor.putInt("linkOpenCount", linkOpenCount + 1);
			editor.apply();
		}
		
		
        init();
        
    }
		

	
	
	
    private void setLocale(Activity act,String cd){
        Locale loc = new Locale(cd);
        loc.setDefault(loc);
        Resources ress = act.getResources();
        Configuration cfg = ress.getConfiguration();
        cfg.setLocale(loc);
        ress.updateConfiguration(cfg,ress.getDisplayMetrics());
    }
    private void init(){
        String[] arraySpinner = new String[] {getString(R.string.Choose_Language),getString(R.string.englis),getString(R.string.arab),getString(R.string.cina),};
        final Spinner spinlang = findViewById(R.id.spinbahasa);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinlang.setAdapter(adapter);
        spinlang.setSelection(0);
       
        spinlang.setOnItemSelectedListener(new OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
                    String aa = p1.getItemAtPosition(p3).toString();
                    
                    if(aa.equals(getString(R.string.englis))){
                        setLocale(LoginActivity.this,"en");
                        SetLang("MyLang","en");
                        spinlang.setSelection(0);
                        recreate();
                    } else if (aa.equals(getString(R.string.arab))){
                        setLocale(LoginActivity.this,"ar");
                        SetLang("MyLang","ar");
                        spinlang.setSelection(0);
                        recreate();
                    } else if (aa.equals(getString(R.string.cina))) {
                        setLocale(LoginActivity.this,"zh");
                        SetLang("MyLang","zh");
                        spinlang.setSelection(0);
                        recreate();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> p1) {
                }
            });
        prefs = Prefs.with(this);
        final Context m_Context = this;
        final EditText textUsername = findViewById(R.id.textUsername);
        textUsername.setText(prefs.read(USER, ""));
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textUsername = findViewById(R.id.textUsername);
                    if (!textUsername.getText().toString().isEmpty()) {
                        prefs.write(USER, textUsername.getText().toString());
                        String userKey = textUsername.getText().toString().trim();
                        Login(m_Context, userKey);
                        USERKEY = userKey;
                    }
                    if (textUsername.getText().toString().isEmpty()) {
                        textUsername.setError("Please enter Licence Keys");
                    }
                    if (textUsername.getText().toString().isEmpty()) {
                        textUsername.setError("Please enter Licence Keys");
                    }
                }
            });
        final ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        final ImageView pler = findViewById(R.id.vis_pwd);
        pler.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {
                    ClipData abc = myClipboard.getPrimaryClip();
                    textUsername.setText(abc.getItemAt(0).getText().toString());

                }
            });
        
        ImageView getKey = findViewById(R.id.telegram);
        getKey.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(a[0]));
                    startActivity(intent);
                }
            });
        
    }
    
    
    private static void Login(final Context m_Context, final String userKey) {
        
        final ProgressDialog progressDialog = new ProgressDialog(m_Context, 5);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(m_Context.getString(R.string.pleasewait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        

        final Handler loginHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Intent i = new Intent();
                    i = new Intent(m_Context.getApplicationContext(), MainActivity.class);
                    m_Context.startActivity(i);
                    
                } else if (msg.what == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(m_Context, 5);
                    builder.setTitle(m_Context.getString(R.string.erorserver));
                    if(msg.obj.toString().equals("USER OR GAME NOT REGISTERED")){
                        builder.setMessage(m_Context.getString(R.string.userorgamenotregis));
                    } else if(msg.obj.toString().equals("Expired Key...")){
                       builder.setMessage(m_Context.getString(R.string.expiredkey));
                    } else if (msg.obj.toString().equals("MAX DEVICE REACHED")) {
                        builder.setMessage(m_Context.getString(R.string.maxdevice));
                    } else if (msg.obj.toString().equals("Bad Parameter")) {
                        builder.setMessage(m_Context.getString(R.string.BadParameter));
                    } else if (msg.obj.toString().equals("MAINTENANCE")) {
                        builder.setMessage(m_Context.getString(R.string.MAINTENANCE));
                    } else if (msg.obj.toString().equals("INVALID PARAMETER")) {
                        builder.setMessage(m_Context.getString(R.string.INVALIDPARAMETER));
                    } else if (msg.obj.toString().equals("USER BLOCKED")){
                        builder.setMessage(m_Context.getString(R.string.userblock));
                    }
                    builder.setCancelable(false);
                    builder.setPositiveButton(m_Context.getString(R.string.okok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                    builder.show();
                }
                progressDialog.dismiss();
            }
        };

        new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = Check(m_Context, userKey);
                    if (result.equals("OK")) {
                        loginHandler.sendEmptyMessage(0);
                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        loginHandler.sendMessage(msg);
                    }
                }
            }).start();
    }
    
    
    private static native String Check(Context mContext, String userKey);


    static native String GetKey();
    
    
    
    
    
}
