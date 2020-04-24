# humanID Android SDK
Meet humanID - An anonymous online identity, enabling platforms to offer the speed and comfort of social logins, while guaranteeing absolute privacy and protecting our communities by permanently blocking bots, spams, and trolls.

**humanID Android SDK**
Meet humanID - An anonymous online identity, enabling platforms to offer the speed and comfort of social logins, while guaranteeing absolute privacy and protecting our communities by permanently blocking bots, spams, and trolls.


![](https://lh3.googleusercontent.com/umzCnjYdKctZ1oiZS4rmOCyrZtpP9KTKQ2f1t6c9mUbs8TKuZ51zmPwIe-06fS1Xbna_FJbQCyN2pMSzLg4daik2boNdPnTXdFCAYh_GYRPU6hnu-K1ZtVcIPUv-OBuPbo7uzuGn)
## Download

    allprojects {
	    repositories {
	    ...
		    maven { url 'https://jitpack.io' }
	    }
	}
    

## Get the credentials access

Get the appId and appSecret by filling up this form or drop us an email [bastian@human-id.org](mailto:bastian@human-id.org).

## Configuration

Add these codes into your AndroidManifest.xml and make sure all meta-data is fulfilled.

    <meta-data  
      android:name="com.humanid.sdk.applicationIcon"  
      android:resource="@drawable/ic_app_icon"/>  
    <meta-data
      android:name="com.humanid.sdk.applicationName"
      android:value="@string/app_name"/>
    <meta-data  
      android:name="com.humanid.sdk.applicationId"
      android:value="YOUR_APP_ID"/>
    <meta-data
      android:name="com.humanid.sdk.applicationSecret" 
      android:value="YOUR_APP_SECRET"/>
      

## How Do I Use humanID Android SDK in Kotlin

Add these codes into your Activity or Fragment file, we recommend you wrap this in a function that handles the login button.

    LoginManager.getInstance(this).registerCallback(object : LoginCallback {  
	      override fun onCancel() {  }
	    
        override fun onSuccess(exchangeToken: String) {  
    		//todo: send the exchangeToken to your server  
	      }  
	    
        override fun onError(errorMessage: String) {  } 
	    })  
	    ...  
	    ..  
	    .  
	    override
	    fun onActivityResult(requestCode: Int, resultCode:Int, data: Intent?) { 
		  LoginManager.getInstance(this).onActivityResult(requestCode, resultCode, data) 
		  super.onActivityResult(requestCode, resultCode, data)  
	    }

## How Do I Use humanID Android SDK in Java

Add these codes into your Activity or Fragment file, we recommend you wrap this in a function that handles the login button.

    LoginManager.INSTANCE.getInstance(this).registerCallback(new LoginCallback() {
	    @Override
	    public void onCancel() {}
	    
      @Override
	    public void onSuccess(@NotNull String exchangeToken) {
	      //todo: send the exchangeToken to your server
	    }
	    
      @Override
	    public void onError(@NotNull String errorMessage) {}
    
    });
    ...  
    ..  
    .  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		 LoginManager.INSTANCE.getInstance(this).onActivityResult(requestCode, resultCode, data);
		 super.onActivityResult(requestCode, resultCode, data);
	 }
    
## You are set!

Now you can integrate your Android app to humanID. See the full sample here to learn more.


## License

Copyright 2019-2020 Bluenumber Foundation
Licensed under the GNU General Public License v3.0 [(LICENSE)](client/LICENSE)

