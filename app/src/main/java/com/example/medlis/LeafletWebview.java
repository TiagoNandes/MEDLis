package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class LeafletWebview extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaflet_webview);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //webView.loadUrl("http://www.africau.edu/images/default/sample.pdf");0
        Bundle extras = getIntent().getExtras();

            String url =extras.getString("url");


            Log.d("TAG", "https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);

    }

    public void onUrlClick(final View view) {
      /*  TextView textView = (TextView)view;
        String sUrl = String.valueOf(textView.getText());
        webView.loadUrl(sUrl);*/

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
