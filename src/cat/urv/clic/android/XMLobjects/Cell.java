package cat.urv.clic.android.XMLobjects;

import java.util.HashMap;

public class Cell implements RecursiuXML {

	protected HashMap<String,Object> atributs;
	
	public Cell() {
		this.atributs = new HashMap<String,Object>();
		//this.fillsCells = new ArrayList<Cells>();
	}

	public void afegirAtribut(String clau, Object valor){
		this.atributs.put(clau, valor);
	}

	public void afegirCell(Cell cell) { return; }
	public void afegirCells(CellList celllist) { return; }
}
