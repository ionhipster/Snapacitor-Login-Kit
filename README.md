# Snapacitor Login Kit

Capacitor plugin for native Snapchat login.

## Installation

```bash
npm install snapacitor-login-kit
npx cap sync
```

### Android Configuration
In file ```android/app/src/main/java/**/**/MainActivity.java```, add the plugin to the initialization list:

```java
import com.triangles12.snapacitor.login.kit.SnapacitorLoginKitPlugin;
import android.os.Bundle; // required for onCreate parameter

public class MainActivity extends BridgeActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    registerPlugin(SnapacitorLoginKitPlugin.class);
  }
}
```
In file ```android/app/src/main/AndroidManifest.xml```, add the following XML elements under ```<manifest><application>``` :

```xml
<meta-data android:name="com.snap.kit.clientId" android:value="@string/snapchat_client_id" />
<meta-data android:name="com.snap.kit.redirectUrl" android:value="@string/redirect_url" />
<meta-data android:name="com.snap.kit.scopes" android:resource="@array/snap_connect_scopes" /> <!-- This should be a string array of scopes !-->

<activity
    android:name="com.snapchat.kit.sdk.SnapKitActivity"
    android:launchMode="singleTask"
    >
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <!-- update to match your redirect URI-->
        <data
            android:scheme="ionhipster"
            android:host="snap-kit"
            android:path="/oauth2"
            />
    </intent-filter>
</activity>

<uses-permission android:name="android.permission.INTERNET" />

<!-- If you're targeting API 30+ make sure you include -->
<queries>
    <package android:name="com.snapchat.android" />
</queries>
```
In file ```android/app/src/main/res/values/strings.xml``` add the following lines:
```xml
<string name="snapchat_client_id">your-public-oauth-client-id</string>
<string name="redirect_url">ionhipster://snap-kit/oauth2</string>
```
Don't forget to replace ```your-public-oauth-client-id``` and ```ionhipster://snap-kit/oauth2``` with your Snapchat **Public OAuth 2.0 Client ID** and **redirect URL**.

In file ```android/app/src/main/res/values/arrays.xml``` add the following lines:
```xml
<string-array name="snap_connect_scopes">
    <!-- Bitmoji avatar is not currently supported by this plugin -->
    <!-- <item>https://auth.snapchat.com/oauth2/api/user.bitmoji.avatar</item> -->
    <item>https://auth.snapchat.com/oauth2/api/user.display_name</item>
    <item>https://auth.snapchat.com/oauth2/api/user.external_id</item>
</string-array>
```
This provides Scope Permissions for a users display name and external id. Learn more about scopes [here](https://docs.snap.com/snap-kit/login-kit/Tutorials/web/).

**If you have trouble.**
Please restart Android Studio, and do clean build.

### iOS Configuration
In file ```ios/App/App/AppDelegate.swift``` add or replace the following:
```swift
import UIKit
import Capacitor
import SCSDKLoginKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
  
  ...
  
  func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
        // Called when the app was launched with a url. Feel free to add additional processing here,
        // but if you want the App API to support tracking app url opens, make sure to keep this call
        if SCSDKLoginClient.application(app, open: url, options: options) {
          return true
        }

        return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
    }
}
```
Add the following in the ```ios/App/App/info.plist``` file inside of the outermost ```<dict>```:
```xml
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>snapchat</string>
    <string>bitmoji-sdk</string>
    <string>itms-apps</string>
</array>
<key>SCSDKClientId</key>
<string>your-public-oauth-client-id</string>
<key>SCSDKRedirectUrl</key>
<string>ionhipster://snap-kit/oauth2</string>
<key>SCSDKScopes</key>
<array>
    <string>https://auth.snapchat.com/oauth2/api/user.display_name</string>
    <string>https://auth.snapchat.com/oauth2/api/user.bitmoji.avatar</string>
</array>
```
More info about iOS settings can be found [here](https://docs.snap.com/snap-kit/login-kit/Tutorials/ios#get-started).

### _Heads Up!_
<b>Apple will most likely reject your app</b> if you try to submit an app that only offers Snap LoginKit as an option for authentication <i><b>unless</b></i> you implement a <b>demoMode</b> app check workaround like below or an alternate way to log in.

### Web Configuration
Web is not currently supported.

## Example

### login
```typescript
import { Plugins } from '@capacitor/core'
const { SnapacitorLoginKitPlugin } = Plugins

await SnapacitorLoginKitPlugin.login().then(async (success) => {
    // success.loggedIn is a boolean
    if (success.loggedIn) {
      // Logged in successfully!
      ...
    } else if (response.demoMode) {
      // Handle demo mode for Apple review
      ...
    } else {
      // Handle if login fails
      ...
    }
})
```

### getUserInfo
```typescript
import { Plugins } from '@capacitor/core'
const { SnapacitorLoginKitPlugin } = Plugins

await SnapacitorLoginKitPlugin.getUserInfo().then((userInfo) => {
    // userInfo is an object featuring displayName and externalId
    console.log(`displayName:${userInfo.displayName}, externalId:${userInfo.externalId}`)
})


```


## API

<docgen-index>

* [`login()`](#login)
* [`getUserInfo()`](#getuserinfo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### login()

```typescript
login() => Promise<{ value: string; }>
```

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### getUserInfo()

```typescript
getUserInfo() => Promise<{ value: string; }>
```

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>
