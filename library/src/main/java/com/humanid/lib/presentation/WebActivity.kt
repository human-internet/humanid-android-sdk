package com.humanid.lib.presentation

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.humanid.lib.R
import java.net.URI

class WebActivity : AppCompatActivity() {
    companion object{
        const val KEY_LOGIN_URL = "key_login_url"
        const val KEY_CREDENTIAL = "key_credential"
        const val REQUEST_CODE = 100
        const val RESULT_CODE = 101
        fun start(activity: AppCompatActivity, url: String){
            val intent = Intent(activity, WebActivity::class.java)
                .apply {
                    putExtra(KEY_LOGIN_URL, url)
                }
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private val webView: WebView by lazy {
        findViewById(R.id.webView)
    }

    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_circular)
    }

    private val loginUrl: String? by lazy {
        intent.getStringExtra(KEY_LOGIN_URL)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        supportActionBar?.hide()
        initWebView()
    }

    private fun initWebView() {
        webView.apply {
            webView.settings.loadsImagesAutomatically = true
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.databaseEnabled = true
            webView.settings.allowFileAccess = true
            webView.settings.setAppCacheEnabled(false)

            webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

            webView.loadUrl(loginUrl.orEmpty())

            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    progressBar.visibility = View.VISIBLE
                    val currentUrl: String? = request?.url?.toString()
                    return if (currentUrl?.startsWith("https://", true)!!){
                        view?.loadUrl(currentUrl)
                        false
                    }else{
                        val exchangeToken = request.url?.getQueryParameter("et")
                        if (!exchangeToken.isNullOrEmpty()){
                            val intent = Intent()
                                .apply {
                                    putExtra(KEY_CREDENTIAL, Credential(exchangeToken))
                                }
                            setResult(RESULT_CODE, intent)
                            finish()
                            true
                        }else{
                            false
                        }
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    progressBar.visibility = View.GONE
                }

                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    return super.shouldInterceptRequest(view, request)
                }
            }
        }
    }
}