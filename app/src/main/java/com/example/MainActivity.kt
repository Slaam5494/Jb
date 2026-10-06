package com.example

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.LuminaBackground
import com.example.ui.theme.LuminaCyan
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private var webView: WebView? = null
    private var lastBackPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LuminaBackground
                ) {
                    LuminaWebViewScreen(
                        onWebViewCreated = { webView = it },
                        onBackPressed = {
                            if (webView?.canGoBack() == true) {
                                webView?.goBack()
                            } else {
                                val currentTime = System.currentTimeMillis()
                                if (currentTime - lastBackPressTime < 2000L) {
                                    finish()
                                } else {
                                    lastBackPressTime = currentTime
                                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        webView?.let {
            it.stopLoading()
            it.clearHistory()
            it.removeAllViews()
            it.destroy()
        }
        webView = null
        super.onDestroy()
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LuminaWebViewScreen(
    onWebViewCreated: (WebView) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    var loadProgress by remember { mutableFloatStateOf(0f) }
    var isLoading by remember { mutableStateOf(true) }

    BackHandler(onBack = onBackPressed)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LuminaBackground)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).apply {
                    setBackgroundColor(android.graphics.Color.parseColor("#010308"))

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        allowFileAccess = true
                        allowContentAccess = true
                        mediaPlaybackRequiresUserGesture = false
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        builtInZoomControls = false
                        displayZoomControls = false
                        cacheMode = WebSettings.LOAD_DEFAULT
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }

                    // Native-to-JS bridge
                    addJavascriptInterface(LuminaNativeBridge(ctx, this), "AndroidBridge")

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            loadProgress = newProgress / 100f
                            isLoading = newProgress < 100
                        }

                        override fun onJsAlert(
                            view: WebView?,
                            url: String?,
                            message: String?,
                            result: JsResult?
                        ): Boolean {
                            AlertDialog.Builder(ctx)
                                .setTitle("LUMINA AI")
                                .setMessage(message.orEmpty())
                                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                                    dialog.dismiss()
                                    result?.confirm()
                                }
                                .setOnCancelListener {
                                    result?.cancel()
                                }
                                .show()
                            return true
                        }

                        override fun onJsConfirm(
                            view: WebView?,
                            url: String?,
                            message: String?,
                            result: JsResult?
                        ): Boolean {
                            AlertDialog.Builder(ctx)
                                .setTitle("LUMINA AI")
                                .setMessage(message.orEmpty())
                                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                                    dialog.dismiss()
                                    result?.confirm()
                                }
                                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                                    dialog.dismiss()
                                    result?.cancel()
                                }
                                .setOnCancelListener {
                                    result?.cancel()
                                }
                                .show()
                            return true
                        }
                    }

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val url = request?.url?.toString().orEmpty()
                            return handleUrlNavigation(ctx, url)
                        }

                        @Deprecated("Deprecated in Java")
                        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                            return handleUrlNavigation(ctx, url.orEmpty())
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }

                    loadUrl("file:///android_asset/index.html")
                    onWebViewCreated(this)
                }
            }
        )

        // Loading indicator
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            LinearProgressIndicator(
                progress = { loadProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = LuminaCyan,
                trackColor = Color(0xFF0A1930)
            )
        }
    }
}

private fun handleUrlNavigation(context: Context, url: String): Boolean {
    // If it's a local asset file, load inside WebView
    if (url.startsWith("file:///android_asset/")) {
        return false
    }

    // WhatsApp, Tel, and External URLs should open through Android Intents
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
        true
    } catch (e: Exception) {
        Toast.makeText(context, "Cannot open: $url", Toast.LENGTH_SHORT).show()
        false
    }
}

class LuminaNativeBridge(private val context: Context, private val webView: WebView) {

    @JavascriptInterface
    fun showToast(message: String) {
        webView.post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    @JavascriptInterface
    fun openExternal(url: String) {
        webView.post {
            handleUrlNavigation(context, url)
        }
    }

    @JavascriptInterface
    fun triggerRewardedAd() {
        webView.post {
            // Simulated AdMob Rewarded Video
            Toast.makeText(context, "▶ [AdMob Rewarded Video Completed]", Toast.LENGTH_SHORT).show()
            webView.evaluateJavascript("if (window.LuminaApp) { LuminaApp.watchAdForCoin(); }", null)
        }
    }
}
