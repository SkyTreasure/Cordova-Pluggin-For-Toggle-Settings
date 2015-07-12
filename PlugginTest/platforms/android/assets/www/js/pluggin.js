var TogglePlugin={
	WiFiToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'wifi',    //Type of action
			[{
				 
			}]
		);
	},

	DataPackToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'datapack',    //Type of action
			[{
				 
			}]
		);
	},

	SilentModeToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'silentmode',    //Type of action
			[{
				 
			}]
		);
	},

	BrightnessToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'brightness',    //Type of action
			[{
				 
			}]
		);
	},

	AirplaneModeToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'airplane',    //Type of action
			[{
				 
			}]
		);
	},

	BluetoothToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'bluetooth',    //Type of action
			[{
				 
			}]
		);
	},

	ScreenRotationToggle:function(successCallback, errorCallback){
		cordova.exec(
			successCallback,
			errorCallback,
			'Toggler', //Class Name
			'screenrotation',    //Type of action
			[{
				 
			}]
		);
	}

}
