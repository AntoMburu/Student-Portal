package com.nbt_tech.thriftliftke.ui.notifications;

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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nbt_tech.thriftliftke.R;

import static com.nbt_tech.thriftliftke.R.layout.fragment_account;

public class NotificationsFragment extends Fragment {



        //Get webview
        private WebView webViews;
        private ProgressBar progressBar;
        private TextView viewprogress;
        private int pStatus = 0;
        private Handler handler = new Handler();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootview = inflater.inflate(fragment_account, container, false);

            webViews = (WebView) rootview.findViewById(R.id.webviewf);
            progressBar = (ProgressBar) rootview.findViewById(R.id.progressBar);
            viewprogress = (TextView) rootview.findViewById(R.id.viewprogress);


            webViews.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webViews.getSettings().setJavaScriptEnabled(true);
            webViews.getSettings().setBuiltInZoomControls(false);
            webViews.getSettings().setUseWideViewPort(true);
            webViews.loadUrl("https://driplift.co.ke/store//\n");

            webViews.setWebViewClient(new WebViewClient() {
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


            webViews.setWebChromeClient(new WebChromeClient() {
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

            webViews.setOnKeyListener(new View.OnKeyListener() {
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

