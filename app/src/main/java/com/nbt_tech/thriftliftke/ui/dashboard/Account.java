package com.nbt_tech.thriftliftke.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static com.nbt_tech.thriftliftke.R.layout.fragment_account;

import com.nbt_tech.thriftliftke.MainActivity;
import com.nbt_tech.thriftliftke.R;

public class Account extends Fragment {

    //Get webview
    private WebView webViewf;
    private ProgressBar progressBar;
    private TextView viewprogress;
    private int pStatus = 0;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(fragment_account, container, false);

        webViewf = (WebView) rootview.findViewById(R.id.webviewf);
        progressBar = (ProgressBar) rootview.findViewById(R.id.progressBar);
        viewprogress = (TextView) rootview.findViewById(R.id.viewprogress);


       /// webViewf.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webViewf.getSettings().setJavaScriptEnabled(true);
        webViewf.getSettings().setBuiltInZoomControls(false);
        webViewf.getSettings().setUseWideViewPort(true);
        webViewf.loadUrl("https://www.kafuco.ac.ke/home/index.php/news-centre/blog/\n");

        webViewf.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);


                return true;
            }
        });


        webViewf.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressBar.setVisibility(View.VISIBLE);

                } else {
                    progressBar.setVisibility(View.GONE);

                }
                progressBar.setProgress(progress);

            }
        });

        //initiaalize connectvitymanager
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//get acive network info
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//check network status
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            //for internet is connected

            ///initialize dialog
            Dialog dialog = new Dialog(getContext());
            //CONTENT VIEW
            dialog.setContentView(R.layout.alert_dialog);
            //CANCEL OUTSIDE TOUCH
            dialog.setCanceledOnTouchOutside(false);
//DIALOG W x H
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            //transparent bg
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//animation
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button bt_try = dialog.findViewById(R.id.btn_try);
            bt_try.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//call recreate method
                    getActivity().recreate();
                }
            });
            //show dialog when no internet
            dialog.show();
        } else {

        }//////
///back functionality

        webViewf.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View w, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webViewf = (WebView) w;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webViewf.canGoBack()) {
                                webViewf.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

        return rootview;


    }

  /*  public void onResume() {

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        super.onResume();


    }

    @Override
    public void onStop() {

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        super.onStop();

    }


   */

}

