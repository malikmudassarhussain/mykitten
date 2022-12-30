import SwiftUI
import mobile

@UIApplicationMain
class iOSApp : NSObject, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool
    {
        KoinKt.koinIos(onSuspendApp:exitApp, apiKey:apiKey())
        let controller = AvoidDispose (RootKt.RootView())
        controller.view.backgroundColor = . white
                let window = UIWindow (frame: UIScreen.main.bounds)
        window.backgroundColor = . white
                window.rootViewController = controller
        window.makeKeyAndVisible()
        self.window = window
        return true
    }

    func exitApp() ->Void
    {
        DispatchQueue.main.asyncAfter(deadline:.now()) {
        UIApplication.shared.perform(#selector (NSXPCConnection.suspend))
        }
    }
    
    func apiKey()->String{
        return Bundle.main.infoDictionary?["Api_Key"]! as! String
    }
}
