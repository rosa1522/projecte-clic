package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activitat {

	protected List<Cells> cells;
	protected HashMap<String, Object> atributsActivitat;

	public Activitat() {
		this.atributsActivitat = new HashMap<String, Object>();
		//this.cells = new ArrayList<Cells>();
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributsActivitat.put(clau, valor);
	}
	
	public void afegirCellsActivitat(Cells cells){
		this.cells.add(cells);
	}
}
