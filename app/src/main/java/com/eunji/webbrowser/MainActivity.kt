package com.eunji.webbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply{
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")

        urlText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(urlText.text.toString())
                true
            } else {
                false
            }
        }

        // 컨텍스트 메뉴 등록
        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // 화면 상단 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        // true 해줘야 있는 줄로 인식
        return true
        // return super.onCreateOptionsMenu(menu)
    }

    // 옵션 메뉴 상세 설정
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }

            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if(intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }

            R.id.action_send_msg -> {
                sendSMS("010-9310-7830", webView.url)
                Toast.makeText(this, "Send Message", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.action_email -> {
                email("zluoy5@gmail.com", "좋은 사이트", webView.url)
                Toast.makeText(this, "Send Email", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 컨텍스트 메뉴
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_share -> {
                share(webView.url)
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.action_browser -> {
                browse(webView.url)
                Toast.makeText(this, "Base Browser", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}