package net.speconsult.android.usage;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class Usage extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

/*
        Context context = this.cordova.getActivity();

        PackageManager pm = context.getPackageManager();

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, -24);
        long startTime = calendar.getTimeInMillis();
*/
        //List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        JSONObject jo = new JSONObject();
        jo.put("firstName", "John");
        jo.put("lastName", "Doe");

        JSONArray ja = new JSONArray();
        ja.put(jo);

        JSONObject mainObj = new JSONObject();
        mainObj.put("employees", ja);

        callbackContext.success(mainObj);

        return true;
    }
}
