package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.util.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class LeafletWebview extends AppCompatActivity {
    private WebView webView;

    private TextToSpeech mTTs;
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

        final ImageView textSpeech = (ImageView) findViewById(R.id.btnListenningPDF); //botão

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(new Locale("pt", "pt"));
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");

                    } else {
                        textSpeech.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        textSpeech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    textToSpeech(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
    private String GetString(String filepath) throws IOException {
        InputStream inputStream = new FileInputStream(filepath);
        byte[] byteArray = IOUtils.toByteArray(inputStream);
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }
    private void textToSpeech(String text) throws IOException {

        mTTs.speak( GetString(text), TextToSpeech.QUEUE_FLUSH, null);
    }
}
