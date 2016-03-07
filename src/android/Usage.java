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
import java.util.HashMap;
import java.util.Map;

public class Usage extends CordovaPlugin {

    public static final String TAG = "stats";

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {


        Context context = this.cordova.getActivity();

        PackageManager pm = context.getPackageManager();

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR, -24);
        long startTime = calendar.getTimeInMillis();

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        Log.d(TAG, "Count of usage stats list is: " + usageStatsList.size());

        JSONArray ja = new JSONArray();

        for (UsageStats usage : usageStatsList) {

            Long totalTimeInForeground = usage.getTotalTimeInForeground();

            if(totalTimeInForeground > 60000) {

                ApplicationInfo applicationInfo = getApplicationInfo(pm, usage);
                String appName = (String) applicationInfo.loadLabel(pm);

                Long lastTimeUsed = usage.getLastTimeUsed();

                JSONObject jo = new JSONObject();
                jo.put("appName", appName);
                jo.put("timeInForeground", totalTimeInForeground);
                jo.put("lastTimeUsed", lastTimeUsed);
                ja.put(jo);
            }

        }

        JSONObject mainObj = new JSONObject();
        mainObj.put("applications", ja);

        callbackContext.success(mainObj);

        return true;
    }

    private Map<String, Long> buildConsolidatedStats(List<UsageStats> usageStatsList, PackageManager pm) {
        Map<String, Long> consolidated = new HashMap<String, Long>();
        for (UsageStats usage : usageStatsList) {
            ApplicationInfo applicationInfo = getApplicationInfo(pm, usage);
            String appName = (String) applicationInfo.loadLabel(pm);
            if(consolidated.containsKey(appName)) {
                long usageMs = consolidated.get(appName);
                usageMs += usage.getTotalTimeInForeground();
                consolidated.put(appName, usageMs);
            } else {
                consolidated.put(appName, usage.getTotalTimeInForeground());
            }
        }
        return consolidated;
    }

    private ApplicationInfo getApplicationInfo(PackageManager pm, UsageStats usage) {
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(usage.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private long calcMinutes(long millis) {
        return (millis / 1000) / 60;
    }
}
