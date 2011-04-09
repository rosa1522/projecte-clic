package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activitat {

/*	protected String classe;
	protected String name;
	protected String missatgeInicial;
	protected String missatgeFinal;*/
	protected List<Cells> llistaCells;
	protected HashMap<String, Object> atributsActivitat;

	public Activitat() {
		this.atributsActivitat = new HashMap<String, Object>();
		this.llistaCells = new ArrayList<Cells>();
/*		this.classe = "";
		this.name = "";
		this.missatgeInicial = "";
		this.missatgeFinal = "";
		this.llistaCells = null;*/
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributsActivitat.put(clau, valor);
	}
	
	public void afegirCellsActivitat(Cells cells){
		this.llistaCells.add(cells);
	}
	

/*	public void setClasse(String classe) {
		this.classe = classe;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMissatgeInicial(String missatgeInicial) {
		this.missatgeInicial = missatgeInicial;
	}

	public void setMissatgeFinal(String missatgeFinal) {
		this.missatgeFinal = missatgeFinal;
	}

	public void setLlistaCells(List<Cells> llistaCells) {
		this.llistaCells = llistaCells;
	}*/
}
