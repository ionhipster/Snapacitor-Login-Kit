import Foundation
import Capacitor
import SCSDKLoginKit

@objc(SnapacitorLoginKitPlugin)
public class SnapacitorLoginKitPlugin: CAPPlugin {

    @objc func login(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let app = UIApplication.shared
            let appScheme = "snapchat://app"
            if app.canOpenURL(URL(string: appScheme)!) {
                SCSDKLoginClient.login(from: self.bridge?.viewController) { (success : Bool, error : Error?) in
                    if (error == nil) {
                        call.resolve(["loggedIn":success])
                    } else {
                        call.reject(error?.localizedDescription ?? "Something went wrong.")
                    }
                }
            } else {
                call.resolve(["demoMode": true])
            }
        }
    }

    @objc func getUserInfo(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let builder = SCSDKUserDataQueryBuilder().withDisplayName().withExternalId()
            let userDataQuery = builder.build()

            // Call fetch API
            SCSDKLoginClient.fetchUserData(with:userDataQuery, success:{ (userData: SCSDKUserData?, partialError: Error?) in
              let displayName = userData?.displayName;
              let externalId = userData?.externalID;

              call.resolve(["displayName":displayName!, "externalId":externalId!])
            }, failure:{ (error: Error?, isUserLoggedOut: Bool) in
                // Handle error
            })
        }
    }
}
