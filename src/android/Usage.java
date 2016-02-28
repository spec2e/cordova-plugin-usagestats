package net.speconsult.android.usage;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class Usage extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("usage")) {

            String name = data.getString(0);
            String message = "Usage, " + name;
            callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
}
