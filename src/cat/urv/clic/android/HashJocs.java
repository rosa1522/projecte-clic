package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class HashJocs {

	//NOU: Mantenim dues taules, aixi que podem accedir als objectes pel seu nom o pel seu id
	private Hashtable<Integer,Joc> jocs;
	private Hashtable<String,Joc> jocsNom;

	public HashJocs() {
		jocs = new Hashtable<Integer, Joc>();
		jocsNom = new Hashtable<String, Joc>();
	}
	
	public void afegirJoc(Joc joc){
		jocs.put(joc.getIdentificador(), joc);
		jocsNom.put(joc.getNom(), joc);
	}
	
	//NOU: Ara totes les funcions les podem llençar pel nom o per l'id :)
	public void esborrarJoc(Integer id){
		String str = jocs.get(id).getNom();
		jocs.remove(id);
		jocsNom.remove(str);
		
	}

	public void esborrarJoc(String str){
		Integer id = jocs.get(str).getIdentificador();
		jocs.remove(id);
		jocsNom.remove(str);
		
	}

	public boolean existeixJoc(Integer id){
		return (jocs.containsKey(id));
	}

	public boolean existeixJoc(String str){
		return (jocsNom.containsKey(str));
	}
	
	public Joc cercarJoc(Integer id){
		return jocs.get(id);
	}
	
	public Joc cercarJoc(String str){
		return jocsNom.get(str);
	}
	
	//NOU: Li passem cert o fals i nomes ens tornara aquells que estiguin descarregats o no
	public List<String> construirLlistaJocs(boolean descarregats){
		List<String> llistaNomsJocs = new ArrayList<String>();
		//Per iterar els elements d'un hash es millor fer-ho aixi (Enumerator es molt lent)
		for(Joc j: jocs.values()){
			if (j.getDescarregat() == descarregats)
			{
				llistaNomsJocs.add(j.getNom());
			}
		}
		
		return llistaNomsJocs;
	}
	
	public List<Integer> llistaIdentificadorJocs(){
		List<Integer> llistaIdJocs = new ArrayList<Integer>();
		for(Joc j: jocs.values()){
			llistaIdJocs.add(j.getIdentificador());  
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
	
	public Integer midaHash(){
		return jocs.size();
	}
}
