package cat.urv.clic.android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class VistaWeb extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.webview);
    	
    	WebView web = (WebView) findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("file:///android_asset/templates/index.html");
    }
}
