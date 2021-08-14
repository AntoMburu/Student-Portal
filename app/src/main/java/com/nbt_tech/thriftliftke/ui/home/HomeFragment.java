package com.nbt_tech.thriftliftke.ui.home;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;


import static com.nbt_tech.thriftliftke.R.layout.fragment_home;

import com.nbt_tech.thriftliftke.MainActivity;
import com.nbt_tech.thriftliftke.R;
import com.nbt_tech.thriftliftke.splash;


public class HomeFragment extends Fragment {
    private ProgressBar progressBarr;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(fragment_home, container, false);

        progressBarr = (ProgressBar) rootview.findViewById(R.id.progressBarr);

        webView = (WebView) rootview.findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
     //   webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("http://portal.kafuco.ac.ke\n");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intentt = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentt);



                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressBarr.setVisibility(View.VISIBLE);

                } else {
                    progressBarr.setVisibility(View.GONE);

                }
                progressBarr.setProgress(progress);
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
        //back functionality
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
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

/*

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ///    webView.loadUrl("https://thriftlift.co.ke\n");

    }

    ///////////////
    public void onResume() {

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        super.onResume();


    }

    @Override
    public void onStop() {

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        super.onStop();

    }


 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
//Called when the user submits the query. This could be due to a key press on the keyboard or due to pressing a submit button.
                webView.loadUrl("http://portal.kafuco.ac.ke"+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//Called when the query text is changed by the user.
                return false;
            }
        });

        // Configure the search info and add any event listeners...
       super.onCreateOptionsMenu(menu,inflater);
    }
}