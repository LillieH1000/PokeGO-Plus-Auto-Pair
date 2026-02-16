package h.lillie.pokegoplusautopair

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class Service: AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }
}