function iniciaActivitat(canvas,num) {
	var act;
	//Posar aqui tots els tipus d'activitat que hi hagi
	if (tipusActivitat[num] == "PuzzleJordi") { act = new PuzzleJordi(); }
	if (tipusActivitat[num] == "Puzzle") { act = new Puzzle(); }
	
	//Inicialitzar l'activitat
	act.init(canvas, dadesActivitat[num]);
	return act;
}


////////////////////////////////////////////////
var canvas = document.getElementById('canvas');
var canvasControl = document.getElementById('canvasControl');

	
//Inicialitzem l'interficie d'usuari
var UI = new UserInterface();
UI.init(canvasControl);
var uiClic;

//Triem la primera activitat i l'inicalitzem
var numActivitat = 1;
var activitatActual=iniciaActivitat(canvas, numActivitat);


// MAIN LOOP FUNCTION
var mainLoop = function () {	

	//Dibuixem l'interficie d'usuari
	UI.draw();
	
	//Mirem si hi ha algun event a la interficie d'usuari
	uiClic = UI.checkClics();
	if (uiClic == "next")
	{
		//Mirem si podem avançar a la següent activitat
		if (numActivitat < maxActivitats) 
		{
			//Tanquem l'activitat anterior
			activitatActual.end();
			
			//I comencem la nova
			numActivitat++;
			activitatActual=iniciaActivitat(canvas, numActivitat);
		}
	} 
	else if (uiClic == "previous") {
		//Mirem si podem anar a l'activitat anterior
		if (numActivitat > 1) 
		{
			//Tanquem l'activitat anterior
			activitatActual.end();
			
			//I comencem la nova
			numActivitat--;
			activitatActual=iniciaActivitat(canvas, numActivitat);
		}
	}
	
	//Despres actualitzem l'activitat que tenim en progres
	activitatActual.run();
};

// START MAINLOOP
var mainLoopId = setInterval(mainLoop, 25);
