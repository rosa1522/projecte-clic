package cat.urv.clic.android.XMLobjects;

import java.util.HashMap;

public class Settings implements RecursiuXML {

	String[] descripcio;
	Integer index;
	protected HashMap<String, Object> atributsActivitat;
	
	public Settings() {
		this.index = 0;
		this.atributsActivitat = new HashMap<String, Object>();
	}

	public void afegirAtribut(String clau, Object valor) {
		this.atributsActivitat.put(clau, valor);
	}

	public void afegirDescripcioSetting(String str){
		this.descripcio[this.index] = str;
		this.index = this.index + 1;
	}
	
	public void midaDescripcio(Integer mida){
		this.descripcio = new String[mida];
	}
	
	public void afegirCell(Cell cell) {}
	public void afegirCells(CellList celllist) {}
}
