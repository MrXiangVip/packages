package com.example.settings.wifi;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Switch;

import androidx.preference.PreferenceCategory;

import com.example.settings.R;
import com.example.settings.core.InstrumentedPreferenceFragment;
import com.example.settings.custom.CustomPreference;
import com.example.settings.settingslib.LongPressWifiEntryPreference;
import com.example.settings.settingslib.WifiEntryPreference;

import java.util.List;

public class WifiSettings extends InstrumentedPreferenceFragment {

    //    WifiPickerTracker mWifiPickerTracker;
    private static final int MSG_SCAN_WIFI =0;
    private static final int MSG_UPDATE_WIFI =1;
    private static final String PREF_KEY_ACCESS_POINTS = "access_points";
    private static final String PREF_KEY_AVAILABLE_WIFI_LIST = "wifi_available_list";

    protected WifiManager mWifiManager;
    private PreferenceCategory mWifiEntryPreferenceCategory;

    Handler handler = new Handler( ){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SCAN_WIFI:
                    mScanWifiRunnable.run();
                case MSG_UPDATE_WIFI:
                    mUpdateWifiEntryPreferencesRunnable.run();
                    break;
                default:
                    break;
            }
            return;
        }
    };

    private final Runnable mScanWifiRunnable=()->{
      startScanWifi();
    };
    private final Runnable mUpdateWifiEntryPreferencesRunnable = () -> {
        updateWifiEntryPreferences();
    };

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferences();

    }

    private void addPreferences() {
        addPreferencesFromResource(R.xml.wifi_settings);
        mWifiEntryPreferenceCategory = findPreference(PREF_KEY_ACCESS_POINTS);

    }

    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        mWifiManager = getContext().getSystemService(WifiManager.class);
        handler.sendEmptyMessage( MSG_SCAN_WIFI );
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onStart() {
        super.onStart();
    }

//    public void onActivityResult(int requestCode, int resultCodstartScane, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void startScanWifi( ){
        int status =mWifiManager.getWifiState();
        if( status != WifiManager.WIFI_STATE_ENABLED){
            mWifiManager.setWifiEnabled( true );
        }
        mWifiManager.startScan();
        mWifiEntryPreferenceCategory.setVisible(true);

        handler.sendEmptyMessageDelayed( MSG_UPDATE_WIFI,1000);
    }
    private void updateWifiEntryPreferences() {
        List<ScanResult> scanResults = mWifiManager.getScanResults();
        int index = 0;

        if( scanResults.size()>0 ){
            CustomPreference availablePref = new CustomPreference(getPrefContext());
            availablePref.setSelectable(false);
            availablePref.setTitle(R.string.available_wifi_list);
            availablePref.setOrder(index++);
            availablePref.setKey(PREF_KEY_AVAILABLE_WIFI_LIST);
            mWifiEntryPreferenceCategory.addPreference(availablePref);
        }

        for( ScanResult result:scanResults){
            LongPressWifiEntryPreference pref = createLongPressWifiEntryPreference(result);
            pref.setKey(result.SSID);
            pref.setTitle( result.SSID);
            pref.setOrder(index++);
            mWifiEntryPreferenceCategory.addPreference(pref);

        }
    }

    public LongPressWifiEntryPreference createLongPressWifiEntryPreference(ScanResult wifiEntry) {
        return new LongPressWifiEntryPreference(getPrefContext(), wifiEntry, this);
    }

}