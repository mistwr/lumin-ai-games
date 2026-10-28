package pt.luminai.games.nativeapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import java.io.ByteArrayInputStream

class EmulatorActivity : Activity() {
    private lateinit var web: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent.getStringExtra("rom_uri")?.let(Uri::parse) ?: run { finish(); return }
        val name = uri.lastPathSegment ?: "game.gba"
        val extension = name.substringAfterLast('.', "gba").lowercase()
        val core = mapOf("gb" to "gb", "gbc" to "gb", "gba" to "gba", "nes" to "nes", "sfc" to "snes", "smc" to "snes", "md" to "segaMD", "gen" to "segaMD")[extension] ?: run { finish(); return }
        val rom = contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: run { finish(); return }
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.webChromeClient = WebChromeClient()
        web.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                if (request?.url?.toString() == "https://lumin.local/game.rom") return WebResourceResponse("application/octet-stream", null, ByteArrayInputStream(rom))
                return null
            }
        }
        setContentView(web)
        val html = """<!doctype html><html><head><meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"><style>html,body,#game{margin:0;width:100%;height:100%;background:#0d0b16;color:#e6bc63}</style></head><body><div id="game"></div><script>window.EJS_player="#game";window.EJS_core="$core";window.EJS_gameUrl="https://lumin.local/game.rom";window.EJS_pathtodata="https://cdn.emulatorjs.org/stable/data/";window.EJS_startOnLoaded=true;</script><script src="https://cdn.emulatorjs.org/stable/data/loader.js"></script></body></html>"""
        web.loadDataWithBaseURL("https://lumin.local/", html, "text/html", "UTF-8", null)
    }
    override fun onDestroy() { web.destroy(); super.onDestroy() }
}
