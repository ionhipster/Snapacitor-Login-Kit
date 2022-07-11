package com.triangles12.snapacitor.login.kit;

import android.util.Log;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.login.models.MeData;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

@NativePlugin
public class SnapacitorLoginKitPlugin extends Plugin {
    private LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener;
    private MeData meData;

    @Override
    public void load() {}

    @PluginMethod
    public void login(PluginCall call) {
        // the LoginStateListener tells us when a user has signed in/out
        mLoginStateChangedListener =
            new LoginStateController.OnLoginStateChangedListener() {
                final JSObject ret = new JSObject();

                @Override
                public void onLoginSucceeded() {
                    Log.d("SnapkitLogin", "Login was successful");
                    ret.put("loggedIn", true);

                    call.resolve(ret);
                }

                @Override
                public void onLoginFailed() {
                    Log.d("SnapkitLogin", "Login was unsuccessful");
                    ret.put("loggedIn", false);

                    call.resolve(ret);
                }

                @Override
                public void onLogout() {
                    // when the user unlinks their account we reset the fields and make the login button visible
                    Log.d("SnapkitLogin", "User logged out");
                }
            };

        SnapLogin.getLoginStateController(this.getContext()).addOnLoginStateChangedListener(mLoginStateChangedListener);

        SnapLogin.getAuthTokenManager(this.getContext()).startTokenGrant();
    }

    @PluginMethod
    public void getUserInfo(PluginCall call) {
        JSObject ret = new JSObject();

        // set a list of the data the app wants to use - these need to mirror the snap_connect_scopes set in arrays.xml
        String query = "{me{bitmoji{avatar},displayName,externalId}}";

        SnapLogin.fetchUserData(
            this.getContext(),
            query,
            null,
            new FetchUserDataCallback() {

                @Override
                public void onSuccess(@Nullable UserDataResponse userDataResponse) {
                    if (userDataResponse == null || userDataResponse.getData() == null) {
                        return;
                    }

                    meData = userDataResponse.getData().getMe();
                    if (meData == null) {
                        return;
                    }

                    // set the value of the display name
                    ret.put("displayName", userDataResponse.getData().getMe().getDisplayName());

                    // set the value of the external id
                    ret.put("externalId", userDataResponse.getData().getMe().getExternalId());

                    // not all users have a bitmoji connected, if the account has bitmoji connected we load the bitmoji avatar
                    // if (meData.getBitmojiData() != null) {
                    //     Glide.with(getBaseContext()).load(meData.getBitmojiData().getAvatar()).into(mAvatarImageView);
                    //     ret.put("bitmojiURL", meData.getBitmojiData().getAvatar());
                    // }

                    call.resolve(ret);
                }

                @Override
                public void onFailure(boolean isNetworkError, int statusCode) {
                    Log.d("SnapkitLogin", "No user data fetched " + statusCode);
                }
            }
        );
    }
}
