package com.cgy.mycollections.functions.tts;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cgy.mycollections.R;
import com.cgy.mycollections.utils.LogUtils;

import java.util.List;

public class TTSDemo extends AppCompatActivity {
    Spinner mLanguageSpinner;
    EditText mSpeakEt;
    TextView mCurrentEngineName;

    TTSService.TTSBinder mBinder;

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d("onServiceConnected");
            mBinder = (TTSService.TTSBinder) service;
            mCurrentEngineName.setText(mBinder.getCurrentEngine());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        mCurrentEngineName = (TextView) findViewById(R.id.current_engine_name);
        mSpeakEt = (EditText) findViewById(R.id.speak_tx);
        mLanguageSpinner = (Spinner) findViewById(R.id.language_spinner);
        mLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languages = getResources().getStringArray(R.array.languages);
//                Toast.makeText(TTSDemo.this, "你选择了:" + languages[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent serviceIntent = new Intent(TTSDemo.this, TTSService.class);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mConnection);
    }

    public void onClick(View v) {
        String speakTx = mSpeakEt.getText().toString();
        if (TextUtils.isEmpty(speakTx)) {
            Toast.makeText(this, "请输入播报内容", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.save_btn://保存
                Intent checkIntent = new Intent();
                checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                PackageManager pm = getPackageManager();

                List<ResolveInfo> list = pm.queryIntentActivities(checkIntent,
                        PackageManager.GET_META_DATA);

                String currentEngine = mBinder.getCurrentEngine();
                for (ResolveInfo appInfo : list) {
                    LogUtils.d("appinfo--->:" + appInfo.activityInfo.applicationInfo.packageName);
                    if (appInfo.activityInfo.applicationInfo.packageName.matches(currentEngine)) {
                        checkIntent.setPackage(appInfo.activityInfo.applicationInfo.packageName);
                    }
                }

                startActivityForResult(checkIntent, 2);
                break;
            case R.id.speak_btn://播报
                mBinder.say(speakTx);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                LogUtils.d("CHECK_VOICE_DATA_PASS");
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
}
