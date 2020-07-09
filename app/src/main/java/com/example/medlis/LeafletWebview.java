package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.zxing.pdf417.PDF417Reader;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

                    textToSpeech(url);

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

    private void textToSpeech(String text) {
String link = "https://drive.google.com/viewerng/viewer?embedded=true&url="+text;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            String parsedText="";
            PdfReader reader = new PdfReader(text);
            int n = reader.getNumberOfPages();
            for (int i = 0; i <n ; i++) {
                parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n"; //Extracting the content from the different pages
            }
            System.out.println(parsedText);
            Log.d("RESULLTT", parsedText);
            mTTs.speak(parsedText, TextToSpeech.QUEUE_FLUSH, null);
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
