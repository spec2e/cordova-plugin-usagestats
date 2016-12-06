# cordova-plugin-usagestats

A plugin to access the usage stats from the Android UsageStatsManager

Sample is developed using Ionicframework.

Import into index.html:

<script src="cordova.js"></script>
<script src="usage.js"></script>

Create a function in the controller, that checks if the plugin is available :

   function registerUsagePlugin() {
      try {
        if (window.usageStats) {
          dash.usageAvailable = true;
        } else {
          console.log('cordova plugin usageStats not available ...');
        }
      } catch(err) {
        console.error('cordova plugin is not available...');
      }
    }


Use it like this from the template

      <ion-item ng-repeat="stat in dash.statistics">
        <h2>{{stat.app}}</h2>
        <p>Usage: {{stat.minutes}} minutes</p>
      </ion-item>

You need to ensure that this permission is registered in the AndroidManifest.xml

<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" tools:ignore="ProtectedPermissions" />

That should be it :-)
