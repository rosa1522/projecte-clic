package cat.urv.clic.android.XMLobjects;

import java.util.HashMap;

public class Clic implements RecursiuXML {

	protected HashMap<String, Object> atributs;
	protected Settings settings;
	protected Sequence sequence;
	protected HashMap<String,Activitat> activitats;

	
	public Clic() {
		this.atributs = new HashMap<String, Object>();
		this.activitats = new HashMap<String,Activitat>();
	}

	public void afegirAtribut(String clau, Object valor){
		this.atributs.put(clau, valor);
	}
	
	public void afegirActivitat(String nom, Activitat activitat){
		this.activitats.put(nom, activitat);
	}

	public void afegirSettings(Settings set){
		this.settings = set;
	}	
	
	public Activitat retornaActivitat(String nom){
		return this.activitats.get(nom);
	}
	
	public void afegirSequence(Sequence seq){
		this.sequence = seq;
	}
	
	public Settings getSettings(){
		return this.settings;
	}
	
	public Sequence getSequence(){
		return this.sequence;
	}
	
	public void afegirCell(Cell cell) { return; }
	public void afegirCells(CellList celllist) { return; }

}
