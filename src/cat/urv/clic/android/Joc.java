package cat.urv.clic.android;

import java.util.Date;
import java.util.List;

public class Joc {

	enum idioma{ alemany, angles, arauca, basc, calo, catala, espanyol, esperanto, frances, gallec, grec, italia,
		llati, occita, portugues, rumanes, suec, xines }
	enum nivell { infantil, primaria, secundaria, batxillerat }
	enum area { llengues, matematiques, cienciesSocials, cienciesExperimentals, musica, visualIplastica, educacioFisica,
		tecnologies, diversos }
	
	private Integer identificador;
	private String nom;
	private Date dataPublicacio;
	private List<String> llengua;
	private List<String> nivellJoc;
	private List<String> areaJoc;
	private Boolean descarregat;
	private String ruta;
	
	public Joc (Integer identificador, String nom, Date dataPublicacio,List<String> llengua, List<String> nivellJoc, 
			List<String> areaJoc, String ruta, Boolean descarregat) {
		this.identificador = identificador;
		this.nom = nom;
		this.dataPublicacio = dataPublicacio;	// Format de la data ANY-MES-DIA i separat amb guions
		this.llengua = llengua;
		this.nivellJoc = nivellJoc;
		this.areaJoc = areaJoc;
		this.descarregat = false;    // Es cert si el joc esta descarregat al mobil.
		this.ruta = ruta;		 // Path d'on es trobara l'arxiu del joc comprimit.
		this.descarregat = descarregat;
	}
	
	public Boolean getDescarregat() {
		return descarregat;
	}
	public Integer getIdentificador() {
		return identificador;
	}
	public String getNom() {
		return nom;
	}
	public Date getDataPublicacio() {
		return dataPublicacio;
	}
	public List<String> getLlengua() {
		return llengua;
	}
	public List<String> getNivellJoc() {
		return nivellJoc;
	}
	public List<String> getAreaJoc() {
		return areaJoc;
	}
	public String getRuta() {
		return ruta;
	}
	
}
