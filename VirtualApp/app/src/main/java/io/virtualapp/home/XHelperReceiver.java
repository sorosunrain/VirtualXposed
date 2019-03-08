package io.virtualapp.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lody.virtual.client.core.VirtualCore;

import io.virtualapp.R;
import io.virtualapp.sys.Installd;


/**
 * @author WeiPeng
 * @version 1.0
 * @title XHelperReceiver.java
 * @description need_desc
 * @created 2019/03/08 15:33
 */
public class XHelperReceiver extends BroadcastReceiver {

    private static final String TAG = "[-XHelperReceiver-]";

    public static final String ACTION_COMMAND = "com.sunrain.action.COMMAND";

    public void register(Context ctx){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_COMMAND);
        ctx.registerReceiver(this, filter);
        Log.i(TAG, "register");
    }

    public void unRegister(Context ctx){
        ctx.unregisterReceiver(this);
        Log.i(TAG, "un register");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null || TextUtils.isEmpty(intent.getAction())){
            return;
        }
        switch (intent.getAction()){
            case ACTION_COMMAND:
                String cmd = intent.getStringExtra("cmd");
                Log.d(TAG, "onReceive: " + cmd);
                if(!TextUtils.isEmpty(cmd)){
                    try {
                        executeCommand(context, cmd, intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private void executeCommand(Context ctx, String cmd, Intent intent){
        switch (cmd){

            case "install":
                String path = intent.getStringExtra("path");
                Installd.handleRequestFromFile(ctx, path);
                break;
            case "reboot":
                VirtualCore.get().killAllApps();
                Toast.makeText(ctx, R.string.reboot_tips_1, Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
