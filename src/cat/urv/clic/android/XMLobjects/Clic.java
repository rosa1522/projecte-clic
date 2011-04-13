package cat.urv.clic.android.XMLobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clic implements RecursiuXML {

	protected List<Activitat> activitats;
	protected HashMap<String, Object> atributs;

	public Clic() {
		this.atributs = new HashMap<String, Object>();
		this.activitats = new ArrayList<Activitat>();
	}

	public void afegirAtribut(String clau, Object valor){
		this.atributs.put(clau, valor);
	}
	
	public void afegirActivitat(Activitat activitat){
		this.activitats.add(activitat);
	}

	public void afegirCell(Cell cell) { return; }
	public void afegirCells(CellList celllist) { return; }

}
