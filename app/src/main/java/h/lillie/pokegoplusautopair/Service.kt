package h.lillie.pokegoplusautopair

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class Service: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        if (event.contentChangeTypes != AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED || event.packageName == null || event.className == null) return

        if (event.packageName == "com.android.settings" && event.className == "com.android.settings.bluetooth.BluetoothPairingDialog") return

        if (event.source != null && event.packageName == "com.android.settings" && Regex("[^A-Za-z0-9 ]").replace(event.text.toString(), "").contains("pair with pokemon go plus", true)) {

            // Android 13-15
            if (Build.VERSION.SDK_INT <= 35) {
                val pairButtonList = event.source!!.findAccessibilityNodeInfosByViewId("android:id/button1")
                if (pairButtonList.isNotEmpty()) {
                    pairButtonList[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
                return
            }

            // Android 16-17
            if (Build.VERSION.SDK_INT >= 36) {
                val pairButtonList = event.source!!.findAccessibilityNodeInfosByText("Pair")
                if (pairButtonList.isNotEmpty()) {
                    pairButtonList[1].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
                return
            }
        }
    }

    override fun onInterrupt() {
    }
}