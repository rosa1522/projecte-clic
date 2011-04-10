package cat.urv.clic.android;

import java.util.HashMap;

public class Color extends Style {
	protected HashMap<String,Object> atributsColor;
	
	public Color() {
		this.atributsColor = new HashMap<String,Object>();
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributsColor.put(clau, valor);
	}
}
