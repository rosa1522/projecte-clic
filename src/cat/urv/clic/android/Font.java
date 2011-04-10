package cat.urv.clic.android;

import java.util.HashMap;

public class Font extends Style {
	protected HashMap<String,Object> atributsFont;
	
	public Font(){
		this.atributsFont = new HashMap<String,Object>();
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributsFont.put(clau, valor);
	}
	
}
