function iniciaActivitat(canvas,num) {
	var act;
	//Posar aqui tots els tipus d'activitat que hi hagi
	
	if (dadesActivitat.activitats[num].atributsActivitat.classe == "@puzzles.DoublePuzzle" &&
			dadesActivitat.activitats[num].celllist[0].atributs['shaper-class'] == "@ClassicJigSaw") { act = new PuzzleDoble(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@puzzles.ExchangePuzzle") { act = new PuzzleIntercanvi(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@memory.MemoryGame") { act = new Memoria(); }
	else if (dadesActivitat.activitats[num].atributsActivitat.classe == "@panels.InformationScreen") 
			{   if (dadesActivitat.activitats[num].celllist[0].cell.length != 0){
					var media = dadesActivitat.activitats[num].celllist[0].cell[0].atributs['media-type'];
					if(media) return "NO";
					else act = new PanelInformation();	
				}else{
					act = new PanelInformation();
				}
			}
	else if (dadesActivitat.activitats[num].atributsActivitat.class == "@associations.SimpleAssociation") { act = new SimpleAssociation(); }
	else {
		return "NO";
	}
	//Inicialitzar l'activitat
	act.init(canvas, dadesActivitat.activitats[num]);
	return act;
}

////////////////////////////////////////////////
var canvas = document.getElementById('canvas');
var canvasControl = document.getElementById('canvasControl');
var numActivitat = 0;
	
//Inicialitzem l'interficie d'usuari
var UI = new UserInterface();
UI.init(canvasControl,numActivitat);
var uiClic;

//Triem la primera activitat i l'inicalitzem

var activitatActual=iniciaActivitat(canvas, numActivitat);
	
while (activitatActual == "NO"){	
	numActivitat++;
	activitatActual=iniciaActivitat(canvas, numActivitat);
}

// MAIN LOOP FUNCTION
var mainLoop = function () {	
	var ultima;
	
	//Mirem si hi ha algun event a la interficie d'usuari
	uiClic = UI.checkClics();
	if (uiClic == "next")
	{	
		//Mirem si podem avan�ar a la segŸent activitat
		if (numActivitat < maxActivitats-1) 
		{
			//Tanquem l'activitat anterior
			activitatActual.end();
			
			//I comencem la nova
			numActivitat++;
			activitatActual=iniciaActivitat(canvas, numActivitat);
			var aux = numActivitat;
			
			while (activitatActual == "NO" && numActivitat < maxActivitats-1){	
				numActivitat++;
				activitatActual=iniciaActivitat(canvas, numActivitat);
			}
			
			if (activitatActual == "NO") numActivitat = aux;
			else if(numActivitat == maxActivitats){ 
				activitatActual=iniciaActivitat(canvas, ultima);
				activitatActual.run(canvasControl);
			}
		}
	} 
	else if (uiClic == "previous") {
		//Mirem si podem anar a l'activitat anterior
		if (numActivitat > 0) 
		{
			//Tanquem l'activitat anterior
			activitatActual.end();
			
			//I comencem la nova
			numActivitat--;
			activitatActual=iniciaActivitat(canvas, numActivitat);
			var aux = numActivitat;
			
			while (activitatActual == "NO" && numActivitat > 0){	
				numActivitat--;
				activitatActual=iniciaActivitat(canvas, numActivitat);
			}
			if (activitatActual == "NO") numActivitat = aux;
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
	
	//Despres actualitzem l'activitat que tenim en progres
	if (activitatActual != "NO"){ activitatActual.run(canvasControl); ultima = numActivitat;}
	else{numActivitat++;}
	
};

// START MAINLOOP
var mainLoopId = setInterval(mainLoop, 25);
