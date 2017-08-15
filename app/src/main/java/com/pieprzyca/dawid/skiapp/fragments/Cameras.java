package com.pieprzyca.dawid.skiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pieprzyca.dawid.skiapp.R;

/**
 * Created by Dawid on 29.05.2016.
 * Klasa odpowiedzialna za wyświetlanie stron internetowych z kamerkami dla stoków narciarskich.
 */
public class Cameras extends Fragment {
    public Cameras() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cameras, container, false);
        String link = "<iframe src=\"http://imageserver.webcamera.pl/umiesc/slotwiny2\" width=\"800\" height=\"450\" border=\"0\" frameborder=\"0\" scrolling=\"no\"></iframe>";
        String video = "<video id=\"video\" poster=\"//imageserver.webcamera.pl/miniaturki/um_wejherowo_cam_47c4b7.jpg\" style=\"z-index:2;position:absolute;width:100%;height:100%;top:0px;\" src=\"blob:http://player.webcamera.pl/01c7ea8d-eb9f-46c6-9901-edd74be6517b\"></video>";
        WebView camera = (WebView) view.findViewById(R.id.webView);
        camera.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings webSettings = camera.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        camera.loadData(link, "text/html", null);
        return view;
    }
}