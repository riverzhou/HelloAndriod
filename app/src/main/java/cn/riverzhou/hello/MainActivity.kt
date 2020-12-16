package cn.riverzhou.hello

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var webView: WebView? = null
    private var WEBURL = "https://riverzhou.github.io"
    //private var WEBURL = "https://ie.icoa.cn/"
    //private var WEBURL = "http://127.0.0.1/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActivity()
        initWebView()
    }

    private fun initActivity(){
        //屏幕常亮 在 setContentView  上写
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //全屏
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LOW_PROFILE //状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
                        or View.SYSTEM_UI_FLAG_FULLSCREEN //Activity全屏显示，且状态栏被隐藏覆盖掉。
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //全屏沉浸模式，
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
                )

        //隐藏标题栏
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.hide()
    }

    private fun initWebView(){
        webView = findViewById(R.id.webSite)

        var webSettings = webView!!.settings

        webSettings.setAppCacheEnabled(true) // 启用或禁用缓存
        webSettings.setAppCachePath(cacheDir.path) // 设置应用缓存路径

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

        webView?.fitsSystemWindows = true
        webView?.setLayerType(View.LAYER_TYPE_HARDWARE,null)

        webView?.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        // webSettings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"

        webView?.loadUrl(WEBURL)
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()){
            webView!!.goBack()
            return
        }
        return super.onBackPressed()
    }
}
