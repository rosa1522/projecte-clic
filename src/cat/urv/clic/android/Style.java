package cat.urv.clic.android;

import java.util.HashMap;
import java.util.List;

public class Style extends Cells {
	
	//Atributs d'Style
	protected List<Cells> fillsStyle;
	protected HashMap<String,Object> atributsStyle;
	
	public Style() {
		this.atributsStyle = new HashMap<String,Object>();
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributsStyle.put(clau, valor);
	}
	
	public void afegirFillStyle(Style style){
		this.fillsStyle.add(style);
	}
}
