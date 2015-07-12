# Cordova-Pluggin-For-Toggle-Settings
Settings like wifi, bluetooth, silent mode can be toggled.

pluggin.js has the javascript interface
PlugginTest/platforms/android/assets/www/js/pluggin.js

Toggler.java is the class which extends the CordovaPlugin, which has all the code implementation of toggling features.
PlugginTest/platforms/android/src/com/xist/pluggin/Toggler.java

index.html has the toggling buttons and error and success call back functions
PlugginTest/platforms/android/assets/www/index.html
