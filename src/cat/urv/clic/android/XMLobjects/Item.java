package cat.urv.clic.android.XMLobjects;

import java.util.HashMap;

public class Item implements RecursiuXML {
	
	//Atributs d'Item
	protected HashMap<String,Object> atributs;
	
	public Item() {
		this.atributs = new HashMap<String,Object>();
	}
	
	public void afegirAtribut(String clau, Object valor){
		this.atributs.put(clau, valor);
	}

	public void afegirCell(Cell cell) {}
	public void afegirCells(CellList celllist) {}
}
