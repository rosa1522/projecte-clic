package cat.urv.clic.android.XMLobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clic implements RecursiuXML {

	protected HashMap<String, Object> atributs;
	protected Settings settings;
	protected Sequence sequence;
	protected List<Activitat> activitats;

	
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

	public void afegirSettings(Settings set){
		this.settings = set;
	}
	
	public void afegirSequence(Sequence seq){
		this.sequence = seq;
	}
	
	public void afegirCell(Cell cell) { return; }
	public void afegirCells(CellList celllist) { return; }

}
