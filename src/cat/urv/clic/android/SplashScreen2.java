package cat.urv.clic.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen2 extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		// Carreguem la imatge del logotip al layout
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		img.setImageResource(R.drawable.clic50);
	      
		// Programem una animacio perque la imatge aparegui gradualment.
		// L'animacio esta definida al fitxer res/anim/fadein
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		img.startAnimation(myFadeInAnimation);  
		
		// Programem una tasca per tal que s'esperi una mica abans de passar a la seg�ent activitat
		// Ho fem aixi pq sino el telefon es bloquejaria si fem un sleep al thread principal
		// Passat el temps es canvia cap a l'activity IniciAplicacio
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 5000) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
				} finally {
					finish();
					Intent i = new Intent();
					i.setClassName("cat.urv.clic.android", "cat.urv.clic.android.IniciAplicacio");
					startActivity(i);
				}
			}
		};
		splashThread.start();
	}
}
