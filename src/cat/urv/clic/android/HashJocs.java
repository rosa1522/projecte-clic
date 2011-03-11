package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
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
		List<String> llistaNomsJocs = new ArrayList<String>();
		Enumeration<Joc> j = jocs.elements();
		while (j.hasMoreElements()){
			llistaNomsJocs.add(j.nextElement().getNom());  
		}
		
		return llistaNomsJocs;
	}
	
	public List<Integer> llistaIdentificadorJocs(){
		List<Integer> llistaIdJocs = new ArrayList<Integer>();
		Enumeration<Joc> j = jocs.elements();
		while (j.hasMoreElements()){
			llistaIdJocs.add(j.nextElement().getIdentificador());  
		}
		
		return llistaIdJocs;
	}
	
	public List<String> canviLlistaIdNom(List<Integer> llistaId){
		List<String> llistaNoms = new ArrayList<String>();
		Iterator<Integer> it = llistaId.iterator();
		while(it.hasNext()){
			llistaNoms.add(this.jocs.get(it.next()).getNom());
		}
		return llistaNoms;
	}

	public boolean existeixJoc(Integer id){
		return (this.jocs.containsKey(id));
	}
	
	public Joc cercarJoc(Integer id){
		return this.jocs.get(id);
	}
	
	public Integer midaHash(){
		return this.jocs.size();
	}
	
}
