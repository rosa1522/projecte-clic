package cat.urv.clic.android.XMLobjects;

import java.util.HashMap;

public class Style {
	
	//Atributs d'Style
	protected HashMap<String,Object> atributs;
	
	public Style() {
		this.atributs = new HashMap<String,Object>();
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributs.put(clau, valor);
	}
}
