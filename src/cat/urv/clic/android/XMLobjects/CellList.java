package cat.urv.clic.android.XMLobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CellList implements RecursiuXML {

	protected List<Cell> cell;
	protected HashMap<String,Object> atributs;
	
	public CellList() {
		this.atributs = new HashMap<String,Object>();
		this.cell = new ArrayList<Cell>();
	}

	public void afegirAtribut(String clau, Object valor){
		this.atributs.put(clau, valor);
	}

	public void afegirCell(Cell cell){
		this.cell.add(cell);
	}
	
	public void afegirCells(CellList celllist) { return; }
}
