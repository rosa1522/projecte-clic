package cat.urv.clic.android;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HashJocs {

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

	public void modificarJocADescarregat(Integer id){
		// Abans de marcar el joc com a descarregat, ens assegurem que el codi del joc existeixi
		Joc joc = jocs.get(id);
		if (joc != null){
			joc.setDescarregat(true);
			jocsNom.get(joc.getNom()).setDescarregat(true);
		}
	}
	
	public void modificarJocANODescarregat(Integer id){
		// Abans de marcar el joc com a NO descarregat, ens assegurem que el codi del joc existeixi
		Joc joc = jocs.get(id);
		if (joc != null){
			joc.setDescarregat(false);
			jocsNom.get(joc.getNom()).setDescarregat(false);
		}
	}
	
	// Li passem cert o fals i nomes ens tornara aquells que estiguin descarregats o no
	public List<String> construirLlistaJocs(boolean descarregats){
		List<String> llistaNomsJocs = new ArrayList<String>();
		for(Joc j: jocs.values()){
			if (j.getDescarregat() == descarregats)
			{
				llistaNomsJocs.add(j.getNom());
			}
		}
		
		return llistaNomsJocs;
	}
	
	// Li passem totes les condicions de cerca i mirem els jocs que compleixen la cerca introduida
	public List<String> construirLlistaJocsCondicions(String nivell, String idioma, String area,
													  String titol, String autor){
		List<String> llistaNomsJocsCondicions = new ArrayList<String>();
		for(Joc j: jocs.values()){
			// Fem la comparació (nivell.compareTo("tot") != 0) perquè no tingui en compte cap filtre si han seleccionat "Tots els ..." 
			if (((nivell.compareTo("tot") == 0) || (j.getNivellJoc().contains(nivell))) && 
				((idioma.compareTo("tot") == 0) || (j.getLlengua().contains(idioma)))  && 
				((area.compareTo("tot") == 0) || (j.getAreaJoc().contains(area)))  && 
				((titol.trim().compareTo("") == 0) || (j.getNom().toUpperCase().contains(titol.trim().toUpperCase()))) && 
				((autor.trim().compareTo("") == 0) || (j.getAutors().toUpperCase().contains(autor.trim().toUpperCase()))) )
			{
				llistaNomsJocsCondicions.add(j.getNom());
			}
		}
		
		return llistaNomsJocsCondicions;
	}
	
	// Li passem cert o fals i nomes ens tornara aquells que estiguin descarregats o no
	public List<Integer> construirLlistaIdJocs(boolean descarregats){
		List<Integer> llistaIdJocs = new ArrayList<Integer>();
		for(Joc j: jocs.values()){
			if (j.getDescarregat() == descarregats)
			{
				llistaIdJocs.add(j.getIdentificador());
			}
		}
		
		return llistaIdJocs;
	}
		
	public Integer midaHash(){
		return jocs.size();
	}
}
