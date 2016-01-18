package webview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import net.dev4life.baovietyet.R;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DEV4LIFE on 1/14/16.
 */
public class WebViewActivity extends AppCompatActivity {
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    private ProgressDialog pd = null;
//    private Toolbar toolbar;
    private String url;
    private ImageButton backBtn, shareBtn;
    private ImageButton goBtn;
    private EditText edAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

//        toolbar = (Toolbar) findViewById(R.id.toolbar_webview);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        backBtn = (ImageButton) findViewById(R.id.btn_webview_exit);
//        backBtn.setOnClickListener(this);
//        shareBtn = (ImageButton) findViewById(R.id.btn_webview_share);
//        shareBtn.setOnClickListener(this);
//        goBtn = (ImageButton) findViewById(R.id.btn_go);
//        goBtn.setOnClickListener(this);
//        edAddress = (EditText) findViewById(R.id.ed_address);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        edAddress.setOnKeyListener(this);

//        initCircularFloatingMenu();
        // Save the web view
        webView = (VideoEnabledWebView) findViewById(R.id.webView);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
        //noinspection all
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        displayProgress(webView);

        url = getIntent().getStringExtra("link");
        // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
        webView.loadUrl(url);

    }

    private void initCircularFloatingMenu() {
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.mipmap.ic_launcher);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        final ImageView share = new ImageView(this);
        share.setImageResource(R.drawable.ic_share_white_24dp);

        ImageView back = new ImageView(this);
        back.setImageResource(R.drawable.ic_backspace_white_24dp);


        SubActionButton btnShare = itemBuilder.setContentView(share).build();
        SubActionButton btnBack = itemBuilder.setContentView(back).build();

        //attach the sub buttons to the main button
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnShare)
                .addSubActionView(btnBack)
                .attachTo(actionButton)
                .build();

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void displayProgress(final VideoEnabledWebView mWebview) {
        pd = ProgressDialog.show(this, "", "Web đang được xử lý! Bạn vui lòng chờ để xem được đầy đủ...Nhanh hay chậm phụ thuộc mạng của bạn.", true);
        pd.setCancelable(true);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setDisplayZoomControls(false);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebview.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = mWebview.getUrl();

            }

        });

    }

    @Override
    public void onBackPressed() {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (!webChromeClient.onBackPressed()) {
            if (webView.canGoBack()) {
                pd.dismiss();
                webView.goBack();
            } else {
                pd.dismiss();
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_webview, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("url", url);
        super.onSaveInstanceState(outState);

    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.btn_webview_exit:
//                finish();
//                break;
//            case R.id.btn_webview_share:
//                share();
//                break;
//            case R.id.btn_go:
//                url = checkUrl(edAddress.getText().toString()).toString();
//                webView.loadUrl(url);
//                hideKeyboard();
//                break;
//        }
//    }

    private URL checkUrl(String inputUrl){
        if (!inputUrl.contains("http://"))
            inputUrl = "http://" + inputUrl;

        URL url = null;
        try {
            url = new URL(inputUrl);
        } catch (MalformedURLException e) {
            Log.v("myApp", "bad url entered");
        }
        if (url == null){
            Toast.makeText(this,"Đây không phải đường link đúng",Toast.LENGTH_LONG).show();
        }
        return url;
    }

    private void share() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(intent, "Share"));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        url = savedInstanceState.getString("url");
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//        if (event.getAction() == KeyEvent.ACTION_DOWN)
//        {
//            switch (keyCode)
//            {
//                case KeyEvent.KEYCODE_DPAD_CENTER:
//                case KeyEvent.KEYCODE_ENTER:
//                    url = checkUrl(edAddress.getText().toString()).toString();
//                    webView.loadUrl(url);
//                    hideKeyboard();
//                    return true;
//                default:
//                    break;
//            }
//        }
//        return false;
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_webview_exit) {
            finish();
            return true;
        }else if(id==R.id.action_webview_share){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
