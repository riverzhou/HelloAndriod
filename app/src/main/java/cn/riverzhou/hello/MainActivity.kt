package cn.riverzhou.hello

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient


class MainActivity : Activity() {

    private var webview: WebView? = null
    //private var weburl = "http://192.168.3.36:5000"
    //private var weburl = "https://riverzhou.github.io"
    //private var weburl = "https://ie.icoa.cn"
    private var weburl = "http://127.0.0.1:9524"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main)

        initActivity()
        initWebView()
    }

    private fun initActivity(){
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun initWebView(){
        webview = findViewById(R.id.webSite)

        var webSettings = webview!!.settings

        //webSettings.setAppCacheEnabled(true) // 启用或禁用缓存
        //webSettings.setAppCachePath(cacheDir.path) // 设置应用缓存路径

        webSettings.javaScriptEnabled = true  // 开启 JavaScript 交互
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT // 只要缓存可用就加载缓存, 哪怕已经过期失效 如果缓存不可用就从网络上加载数据

        webSettings.setSupportZoom(true) // 支持缩放 默认为true 是下面那个的前提
        webSettings.builtInZoomControls = true  // 设置内置的缩放控件 若为false 则该WebView不可缩放
        webSettings.displayZoomControls = false // 隐藏原生的缩放控件

        webSettings.blockNetworkImage = false // 禁止或允许WebView从网络上加载图片
        webSettings.loadsImagesAutomatically = true // 支持自动加载图片

        webSettings.safeBrowsingEnabled = true // 是否开启安全模式
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW // http和https混合调用

        webSettings.javaScriptCanOpenWindowsAutomatically = false // 支持通过JS打开新窗口
        webSettings.domStorageEnabled = true // 启用或禁用DOM缓存
        webSettings.setSupportMultipleWindows(false) // 设置WebView是否支持多窗口

        webSettings.useWideViewPort = true  // 将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true  // 缩放至屏幕的大小
        webSettings.allowFileAccess = true // 设置可以访问文件

        webview?.fitsSystemWindows = true
        webview?.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webview?.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        // webSettings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"

        val metrics: DisplayMetrics? = resources.displayMetrics
        val w = metrics?.widthPixels
        val h = metrics?.heightPixels

        webview?.loadUrl("$weburl/?h=$h&w=$w")
    }

    override fun onBackPressed() {
        if (webview!!.canGoBack()){
            webview!!.goBack()
            return
        }
        return super.onBackPressed()
    }
}
