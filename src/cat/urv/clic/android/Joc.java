package cat.urv.clic.android;

import java.util.Date;
import java.util.List;

public class Joc {

	enum idioma{ alemany, angles, araucà, basc, caló, català, espanyol, esperanto, francès, gallec, grec, italià,
		llatí, occità, portuguès, rumanès, suec, xinès }
	enum nivell { infantil, primaria, secundaria, batxillerat }
	enum area { llengues, matematiques, cienciesSocials, cienciesExperimentals, musica, visualIplastica, educacioFisica,
		tecnologies, diversos }
	
	private Integer identificador;
	private String nom;
	private Date dataPublicacio;
	private List<idioma> llengua;
	private nivell nivellJoc;
	private List<area> areaJoc;
	private Boolean descarregat;
	private String enllaç;
	
	public Joc (Integer identificador, String nom, Date dataPublicacio,List<idioma> llengua, nivell nivellJoc, 
			List<area> areaJoc, String enllaç) {
		this.identificador = identificador;
		this.nom = nom;
		this.dataPublicacio = dataPublicacio;
		this.llengua = llengua;
		this.nivellJoc = nivellJoc;
		this.areaJoc = areaJoc;
		this.descarregat = false;    // És cert si el joc està descarregat al mòbil.
		this.enllaç = enllaç;		 // Path d'on es trobarà l'arxiu del joc comprimit.
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
	public List<idioma> getLlengua() {
		return llengua;
	}
	public nivell getNivellJoc() {
		return nivellJoc;
	}
	public List<area> getAreaJoc() {
		return areaJoc;
	}
	public String getEnllaç() {
		return enllaç;
	}
	
}
