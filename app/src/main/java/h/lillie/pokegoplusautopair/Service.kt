package h.lillie.pokegoplusautopair

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

@SuppressLint("AccessibilityPolicy")
class Service: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.packageName == null || event.source == null || event.contentChangeTypes != AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED || event.className == null || event.className == "com.android.settings.bluetooth.BluetoothPairingDialog") return

        if (event.packageName == "com.android.settings" && Regex("[^A-Za-z0-9 ]").replace(event.text.toString(), "").contains("pair with pokemon go plus", true)) {

            // Android 13-15
            if (Build.VERSION.SDK_INT <= 35) {
                val pairNodeList = event.source!!.findAccessibilityNodeInfosByViewId("android:id/button1")
                if (pairNodeList.isNotEmpty() && pairNodeList[0].isClickable && pairNodeList[0].isEnabled) {
                    pairNodeList[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
                return
            }

            // Android 16-17
            if (Build.VERSION.SDK_INT >= 36) {
                val pairNodeList = event.source!!.findAccessibilityNodeInfosByText("pair")
                for (pairNode in pairNodeList) {
                    if (pairNode.text != null && pairNode.text.toString().equals("pair", true)) {
                        if (pairNode.isClickable && pairNode.isEnabled) {
                            pairNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            break
                        }
                        if (pairNode.parent != null && pairNode.parent.isClickable && pairNode.parent.isEnabled) {
                            pairNode.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            break
                        }
                    }
                }
                return
            }
        }
    }

    override fun onInterrupt() {
    }
}