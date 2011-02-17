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
    	WebView.enablePlatformNotifications();
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://www.google.com");
    }
}
