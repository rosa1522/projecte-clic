package cat.urv.clic.android;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class VistaWeb extends Activity {
	
	/** Called when the activity is first created. */
	/* @Override
   	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llista);
    }*/
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.webview);
    	
    	WebView web = (WebView) findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        //web.loadUrl("http://www.google.com");
        web.loadUrl("file:///data/data/cat.urv.clic.android/files/pagina_web/index.html");
        /*
        final WebView webview = (WebView)findViewById(R.id.webView);  
        // JavaScript must be enabled if you want it to work, obviously 
        webview.getSettings().setJavaScriptEnabled(true);  
          
        // WebViewClient must be set BEFORE calling loadUrl! 
        webview.setWebViewClient(new WebViewClient() {  
            @Override  
            public void onPageFinished(WebView view, String url)  
            {  
                webview.loadUrl("javascript:(function() { " +  
                        "document.getElementsByTagName('body')[0].style.color = 'red'; " +  
                        "})()");  
            }  
        });  
          
        webview.loadUrl("http://code.google.com/android");  
        */
    }
}
