package nl.sogeti.android.gpstracker.ng.util

import android.os.Handler
import android.os.Looper
import android.support.test.espresso.IdlingResource
import android.webkit.WebChromeClient
import android.webkit.WebView

class WebViewIdlingResource(private val webView: WebView) : WebChromeClient(), IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    init {
        Handler(Looper.getMainLooper()).post {
            webView.setWebChromeClient(this@WebViewIdlingResource)
        }
    }

    override fun isIdleNow(): Boolean {
        val isIdle = webView.progress == 100
        if (isIdle) {
            callback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun getName() = "WebViewResource_${webView.id}"

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (webView.progress == 100) {
            callback?.onTransitionToIdle()
        }
    }
}
