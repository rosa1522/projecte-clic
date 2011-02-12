package cat.urv.clic.android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;

public class WebView extends Activity {
	
	private WebView web;
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
    	
    	//web = (WebView) findViewById(R.id.webView);
        
        //web.getSettings().setJavaScriptEnabled(true);
        //web.loadUrl("http://www.google.com");
    }
}
