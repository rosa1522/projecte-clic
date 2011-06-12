package cat.urv.clic.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class VistaWeb extends Activity {
	private Bundle bundle;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.webview);
    	        
    	WebView web = (WebView) findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        
        web.setWebChromeClient(new WebChromeClient() {
        	  public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        	    Log.d("MyApplication", message + " -- From line "
        	                         + lineNumber + " of "
        	                         + sourceID);
        	  }
        });
        
        bundle = getIntent().getExtras();
        bundle.getInt("idJoc");
    	web.loadUrl("file://" + getFilesDir() + "/" +  bundle.getInt("idJoc") + "/index.html");    
    }
}
