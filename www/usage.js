/*global cordova, module*/

module.exports = {
    usage: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Usage", "usage", [name]);
    }
};
