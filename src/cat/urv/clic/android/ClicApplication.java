package cat.urv.clic.android;

import android.app.Application;

public class ClicApplication extends Application {
	// Posem la llista de jocs en aquesta classe i sera accessible per totes les activities
	public static HashJocs llistaJocs;
	
	public static Nivell llistaNivells;
	public static Idioma llistaIdiomes;
	public static Area llistaArees;

	@Override
	public void onCreate() {
		// Omplim les llistes amb els nivells, les arees i les llengües
		llistaNivells = new Nivell();
		llistaNivells.afegirTotsNivells();
		llistaIdiomes = new Idioma();
		llistaIdiomes.afegirTotsIdiomes();
		llistaArees = new Area();
		llistaArees.afegirTotesArees();
        
		// Llegim tots els jocs existents que hi ha al fitxer jocs.xml i els posem com no descarregats
        llistaJocs = Utils.llegirFitxerJocsXML(getApplicationContext(), "jocs.xml");
        
        // Llegim el fitxer descarregats.xml i marquem els jocs que hi hagi aqui com a descarregats
        Utils.marcarJocsDescarregats(getApplicationContext());

        super.onCreate();
	}

}
