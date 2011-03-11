package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HashJocs {

	private Hashtable<Integer,Joc> jocs;

	public HashJocs() {
		this.jocs = new Hashtable<Integer, Joc>();
	}
	
	public void afegirJoc(Integer id, Joc joc){
		this.jocs.put(id, joc);
	}
	
	public void esborrarJoc(Integer id){
		this.jocs.remove(id);
	}
	
	public List<String> construirLlistaJocs(){
    	System.out.println("PASSA3 JOCS " +jocs.size());
		List<String> llistaNomsJocs = new ArrayList<String>();
		Enumeration<Joc> j = jocs.elements();
		while (j.hasMoreElements()){
			llistaNomsJocs.add(j.nextElement().getNom());  
		}
		
		return llistaNomsJocs;
	}
	
	public List<Integer> llistaIdentificadorJocs(){
    	System.out.println("PASSA3 JOCS " +jocs.size());
		List<Integer> llistaIdJocs = new ArrayList<Integer>();
		Enumeration<Joc> j = jocs.elements();
		while (j.hasMoreElements()){
			llistaIdJocs.add(j.nextElement().getIdentificador());  
		}
		
		return llistaIdJocs;
	}
	
	
	public Joc cercarJoc(Integer id){
		return this.jocs.get(id);
	}
	
}
