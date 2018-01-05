package com.cgy.mycollections.functions.tts;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.widget.Toast;

import com.cgy.mycollections.MyApplication;
import com.cgy.mycollections.utils.LogUtils;

import java.util.Locale;

/**
 * TTS Service is for speak the text
 *
 * @Author: Guichun Chen
 * Date:   2013/3/10
 */
public class TTSService extends Service {
    private TextToSpeech tts;

    private TTSBinder mBinder = new TTSBinder();

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d("TTS onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(this, ttsInitListener);
        LogUtils.d("TTSService onCreate");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tts.shutdown();
        LogUtils.d("TTSService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("TTSService onStartCommand");
//        if (intent == null) return START_STICKY;


        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }


    private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            LogUtils.d("TextToSpeech onInit");
            switch (status) {
                case TextToSpeech.SUCCESS:

                    break;
                default:
                    break;
            }
            Locale loc = Locale.getDefault();
            if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(loc);
                tts.setSpeechRate(1f);
            }
        }
    };


    class TTSBinder extends Binder {

        /**
         * 获取当前的语音引擎
         *
         * @return
         */
        public String getCurrentEngine() {
            return tts.getDefaultEngine();
        }

        /**
         * 设置语言
         *
         * @param loc
         */
        public void setLanguage(Locale loc) {
            if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(loc);
                tts.setSpeechRate(1f);
            }
        }

        public void say(String strSpeak) {
            if (!TextUtils.isEmpty(strSpeak)) {
//                tts.speak(strSpeak, TextToSpeech.QUEUE_FLUSH,
//                        null);
                tts.speak(strSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            }

        }
    }
}