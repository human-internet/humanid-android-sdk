[![](https://jitpack.io/v/bluenumberfoundation/humanid-android-sdk.svg)](https://jitpack.io/#bluenumberfoundation/humanid-android-sdk)

# humanID Android SDK



<p align="center">
<img src="https://github.com/bluenumberfoundation/humanid-android-sdk/blob/master/human-id-logo.png" width="200" height="200">
</p>


<p align="center">
<a href="https://github.com/bluenumberfoundation/humanid-documentation/edit/master/README.md">General Documentation</a> •
<a href="https://github.com/bluenumberfoundation/humanid-android-sdk/wiki">Wiki</a> • 
<a href="https://github.com/bluenumberfoundation/humanid-android-sdk/wiki/integration">Integration</a> •
<a href="https://github.com/bluenumberfoundation/humanid-documentation/blob/master/contributing.md">Contributing</a> • 
<a href="https://github.com/bluenumberfoundation/humanid-documentation/blob/master/gallery.md">Gallery</a> • 
<a href="https://github.com/bluenumberfoundation/humanid-documentation/blob/master/faq.md">FAQ</a>


<p align="center">
Meet humanID - An anonymous online identity, enabling platforms to offer the speed and comfort of social logins, while guaranteeing absolute privacy and protecting our communities by permanently blocking bots, spams, and trolls.
</p>



## Requirements

<ul>
	<li>API level 21 or higher</li>
	<li>Build with Kotlin</li>
</ul>


Please update to the latest SDK!

## Downloads

    allprojects {
        repositories {
        ...
    	    maven { url 'https://jitpack.io' }
        }
    }
    .
    .
    .
    dependencies {
    	//todo change with the new one
    	implementation 'com.github.bluenumberfoundation:humanid-android-sdk:0.0.5’
    }


## Credentials Access

Get the appId and appSecret by dropping us an email [developers@human-id.org](mailto:developers@human-id.org).

## Configuration

Build the object easier just with this

```kotlin
val humanIdSDK: HumanIdSDK? by lazy {
  HumanIdSDK.Builder()
    .withActivity(this)
    .addClientId(getString(R.string.client_id))
    .addClientSecret(getString(R.string.client_secret))
    .setDefaultLanguage(SupportedLanguage.FRENCH)
    .setPriorityCountryCodes(arrayOf(
      CountryCode.UNITED_STATES,
      CountryCode.FRANCE,
      CountryCode.JAPAN,
      CountryCode.INDONESIA
    )).build()
}
```


## Use in project

Add these codes into your Activity, we recommend you wrap this in a function that handles the login button.

```kotlin
humanIdSDK?.login()

//And in onActivityResult()
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    val exchangeToken = humanIdSDK?.getHumanIdExchangeToken(requestCode, resultCode, data)
}
```

## You're set!

Now you can integrate your Android app to humanID. See the full [sample](https://github.com/bluenumberfoundation/humanid-android-sdk/tree/master/sample) here to learn more.


## License

Copyright 2019-2020 Bluenumber Foundation
Licensed under the GNU General Public License v3.0 [(LICENSE)](
