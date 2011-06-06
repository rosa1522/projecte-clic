/**
 * @author Noelia Tuset
 */

/**
 * Funcio que inicia l'activitat si es una de les creades.
 * @param canvas
 * @param num
 * @returns activitat_iniciada
 */
function iniciaActivitat(canvas,num) {
	var act;
	if (dadesActivitat.activitats[num].atributsActivitat.classe == "@puzzles.DoublePuzzle" &&
			dadesActivitat.activitats[num].celllist[0].atributs['shaper-class'] == "@ClassicJigSaw") { act = new PuzzleDoble(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@puzzles.ExchangePuzzle") { act = new PuzzleIntercanvi(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@memory.MemoryGame") { act = new Memoria(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@panels.Identify") { act = new PanelIdentify(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@panels.InformationScreen") 
			{   if (dadesActivitat.activitats[num].celllist[0].cell.length != 0){
					var media = dadesActivitat.activitats[num].celllist[0].cell[0].atributs['media-type'];
					if(media == "RUN_CLIC_ACTIVITY" || media == "RUN_CLIC_PACKAGE") return "NO";
					else act = new PanelInformation();	
				}else{
					act = new PanelInformation();
				}
			}
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@associations.SimpleAssociation") {
		if (dadesActivitat.activitats[num].celllist[0].cell.length != 0){
			var media = dadesActivitat.activitats[num].celllist[0].cell[0].atributs['media-type'];
			if(media == "PLAY_AUDIO") return "NO";
			else act = new SimpleAssociation(); 
		}else{
			act = new SimpleAssociation(); 
		}	
	}
	else {
		return "NO";
	}
	/** Inicialitzar l'activitat si es valida **/
	act.init(canvas, dadesActivitat.activitats[num]);
	return act;
}


var canvas = document.getElementById('canvas');
var canvasControl = document.getElementById('canvasControl');
var numActivitat = 0;
var uiClic;
var inicial;

/** Inicialitzem l'interficie d'usuari **/
var UI = new UserInterface();
UI.init(canvasControl,numActivitat);

/** Inicalitzem la primera activitat **/
var activitatActual=iniciaActivitat(canvas, numActivitat);

/** Si no ha trobat una activitat valida cerca la seguent **/
while (activitatActual == "NO"){	
	numActivitat++;
	activitatActual=iniciaActivitat(canvas, numActivitat);
	inicial = numActivitat;
}

var mainLoop = function () {	
	var ultima, aux;
	
	/** Mirem si hi ha algun event a la interficie d'usuari **/
	uiClic = UI.checkClics();
	if (uiClic == "next")
	{	
		/** Mirem si podem avançar a la seguent activitat **/
		if (numActivitat < maxActivitats-1) { //0<-->8  MaxActivitats=10
			/** Tanquem l'activitat anterior **/
			activitatActual.end();
			
			/** I comencem la nova **/
			numActivitat++; //9
			activitatActual=iniciaActivitat(canvas, numActivitat);
			
			while (activitatActual == "NO" && numActivitat < maxActivitats-1){	//no entra
				numActivitat++;
				activitatActual=iniciaActivitat(canvas, numActivitat);
			}
			if (activitatActual != "NO") aux = numActivitat; 
		}	
		if (activitatActual == "NO"){ //la guardo
			numActivitat = aux; //a auxiliar
			//activitatActual=iniciaActivitat(canvas, numActivitat); //inicio la activitat
		}
		if(numActivitat == maxActivitats-1){ //9 --9
			activitatActual=iniciaActivitat(canvas, numActivitat);
			activitatActual.run(canvasControl);
			numActivitat = aux;
		}
	} 
	else if (uiClic == "previous") {
		/** Mirem si podem anar a l'activitat anterior **/
		if (numActivitat > 0) 
		{
			if (activitatActual == "NO") numActivitat = aux;
			/** Tanquem l'activitat anterior **/
			activitatActual.end();
			
			/** I comencem la nova **/
			numActivitat--;
			activitatActual=iniciaActivitat(canvas, numActivitat);
			var aux = numActivitat;
			
			while (activitatActual == "NO" && numActivitat >= inicial){	
				numActivitat--;
				activitatActual=iniciaActivitat(canvas, numActivitat);
			}
			
			if (activitatActual == "NO") numActivitat = aux;
			if (numActivitat <= inicial){
				activitatActual=iniciaActivitat(canvas, inicial);
				activitatActual.run(canvasControl);
				numActivitat = inicial;
			}
		}
	}
	else if (uiClic == "update") {
		activitatActual=iniciaActivitat(canvas, numActivitat);
	}

	if (activitatActual.acabat){
		UI.draw(1,numActivitat);
	}else{
		UI.draw(0,numActivitat);
	}
	
	/** Despres actualitzem l'activitat que tenim en progres **/
	if (activitatActual != "NO"){  
		ultima = numActivitat; 
		activitatActual.run(canvasControl);
	//}else{
		//numActivitat++;
	}
};

// START MAINLOOP
var mainLoopId = setInterval(mainLoop, 25);
