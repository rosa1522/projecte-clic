package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cells extends Activitat{

	protected List<Cells> fillsCells;
	protected HashMap<String,Object> atributsCells;
	
	
	public Cells() {
		this.atributsCells = new HashMap<String,Object>();
		//this.fillsCells = new ArrayList<Cells>();
	}

	public void afegirAtributActivitat(String clau, Object valor){
		this.atributsCells.put(clau, valor);
	}
	
	public String retornaRows(){
		return (String)this.atributsCells.get("rows");
	}
	
	public void afegirFillCells(Cells cell){
		this.fillsCells.add(cell);
	}
}
