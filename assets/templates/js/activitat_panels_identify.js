/**
 * ACTIVITAT PUZZLE
 */
function PanelIdentify(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	
	//Variables especifiques d'aquesta activitat
	var frontImage='none';
	var colocades=0;
	this.acabat=false;
	var lines,cols;
	var w,h;
	var myImages = new ImageSetMemory();
	var grid, jugar;
	var dist;
	var x = new Array();
	var y = new Array();
	var ordArray = new Array();
	var xy = new Array();
	var numPrimer, numSegon;
	var primerClic = false;
	var segonClic = false;
	var tercerClic = false;
	var passat = false;
	var idPrimer = 'none';
	var idSegon = 'none';
	var numPeca=1;
	var colorlinies, colorhidden, colorfons, background;
	var theX, theY, incrShowX, incrShowY, aux;
	var control = "0x";
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var encerts = 0;
	var colocades = 0;
	var arxiuSoFi, reprodSoFi, reprodSo;
	
	//Funcio per a inicialitzar l'activitat a partir de les seves dades
	this.init = function(canvas, activityData)
	{		
		/** S'inicialitza el canvas **/
		
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		context = canvas.getContext("2d");
		context.canvas.style.cursor = "pointer";
		
		/** S'agafen les dades necessaries del fitxer data.js **/
		
		w=arrodonir(activityData.celllist[0].atributs.cellWidth,0);
		h=arrodonir(activityData.celllist[0].atributs.cellHeight,0);
		
		dist = activityData.atributsActivitat['layout-position'];
		
		colorhidden = activityData.celllist[0].atributs['style-color-inactive'];
		if (colorhidden) colorhidden = "#"+colorhidden.replace(control,"");
		
		colorfonsbaix = activityData.atributsActivitat['settings-container-gradient-dest'];
		if (colorfonsbaix) colorfonsbaix = "#"+colorfonsbaix.replace(control,"");
		
		colorfonsalt = activityData.atributsActivitat['settings-container-gradient-source'];
		if (colorfonsalt) colorfonsalt = "#"+colorfonsalt.replace(control,"");
		
		gradiente = activityData.atributsActivitat['settings-container-gradient-angle'];
		
		margin = activityData.atributsActivitat['settings-margin'];
		
		background = activityData.atributsActivitat['settings-container-bgColor'];
		if (!background) background="#FFFFFF"; 
		background = "#"+background.replace(control,"");
		
		colorfonsjoc = activityData.atributsActivitat['settings-window-bgColor'];
		if (!colorfonsjoc) colorfonsjoc ="#FFFFFF";
		colorfonsjoc = "#"+colorfonsjoc.replace(control,"");
		
		colorlinies = activityData.celllist[0].atributs['style-color-border'];
		if (!colorlinies) colorlinies = "#00000";
		colorlinies = "#"+colorlinies.replace(control,"");
		
		/*reprodSo = activityData.cell[0].atributs['media-type'];
		reprodSoFi = activityData.cell[1].atributs['media-type'];
		
		arxiuSo = activityData.cell[0].atributs['media-file'];
		arxiuSoFi = activityData.cell[1].atributs['media-file'];*/
		
		colorFonsNoms = "FFFFFF";
		colorFonsNomsSota = "AAFFAA";
		
		/**
		 * El tauler de joc depenent de la distribucio que tingui
		 * s'adapta a unes mides que es puguin mostrar les dades
		 * adaptades a la pantalla correctament.
		 */
		
		lines=activityData.celllist[0].atributs.rows;
		cols=activityData.celllist[0].atributs.cols;
		
		xy=adaptarGrid(canvasWidth,canvasHeight,w,h,lines,cols);
		incrShowX = xy[0];
		incrShowY = xy[1];
		h=incrShowY*lines;
		w=incrShowX*cols;
		
		gridAx=(canvasWidth-w)/2;
		gridAy=(canvasHeight-h)/2;

		/**
		 * Carreguem les imatges o textos a la array Peces.
		 */
		
		var peces = new Array();
		var id_img=0;
		var numPrimer = activityData.celllist[0].cell.length; //noms
		
		for(var i=0; i<numPrimer; i++)
		{
			var hihaText = activityData.celllist[0].cell[i].atributs.p;
			
			if (hihaText)
			{
				var nomImage = activityData.celllist[0].cell[i].atributs.p;
				var novaPeca = new ImageAssociation(context, id_img, nomImage,incrShowX,incrShowY);
				novaPeca.setNumPeca(activityData.celllist[0].cell[i].atributs.id);
				novaPeca.setHidden(false);
				novaPeca.setColor(colorFonsNoms);
				peces.push(novaPeca);
				id_img++;
			}else{
				var myImage = new Image();
				
				myImage.onload = function() {
					imageLoaded = true;
				};
	
				myImage.src = activityData.celllist[0].cell[i].atributs.image;
				var novaPecaImatges = new ImageMemory(context, id_img, myImage,incrShowX,incrShowY);
				novaPecaImatges.setNumPeca(activityData.celllist[0].cell[i].atributs.id);
				novaPecaImatges.setHidden(false);
				peces.push(novaPecaImatges);
				id_img++;
			}
		}

		/**
		 * Desordenem les peces.
		 */
	
		for (var o=0;o<lines*cols;o++){
			ordArray[o]=o;
		}
		
		theX = gridAx;
		theY = gridAy;
		var o = 0;
	    
		for(var i=0;i<lines;i++) { 
	        for(var j=0;j<cols;j++) { 
	        	x[o]=theX;
	        	y[o]=theY;
	        	theX += incrShowX;
	            o++;
	        }
	        theX = gridAx;
	        theY +=  incrShowY;
	    }
		
		jugar = activityData.celllist[0].atributs.id;
		
		if(jugar){	
			ordArray.sort( randOrd );
		
			for (var o=0;o<peces.length;o++){
				peces[o].setPosx(x[ordArray[o]]);
				peces[o].setPosy(y[ordArray[o]]);
				peces[o].llocPeca=ordArray[o];
				if (peces[o].numPeca == "1"){
					encerts++;
				}
			}	
		}else{
			for (var o=0;o<numPrimer;o++){
				peces[o].setPosx(x[o]);
				peces[o].setPosy(y[o]);
			}
		}
		
		/**
		 * Pintem el tauler de peces.
		 */
		
		for (var o=0;o<peces.length;o++){	
			myImages.add(peces[o]);
		}

		/*
		if (reprodSo == "PLAY_AUDIO")
		{	
			soundManager.url = "./sound/swf/";
			soundManager.flashVersion = 9;
			soundManager.useFlashBlock = false;
			soundManager.onready(function() {
				soundManager.createSound(arxiuSo,arxiuSo);
				soundManager.createSound(arxiuSoFi,arxiuSoFi);
				soundManager.play(arxiuSo);
			});
		}*/
	};
	
	//Aqui dins va el codi de l'activitat
	this.run = function() {
		contextControl = canvasControl.getContext("2d");
		context.clearRect(0, 0, canvasWidth, canvasHeight);
		context.strokeRect(gridAx,gridAy,w,h);
		
		grid = new Grid(context, lines/1, cols/1, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
		
		//LLEGIR DADES USUARI
		if(DragData.active)
		{
			if(frontImage=='none'){	
				frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
				if(frontImage!='notfound') frontImage.setDraggable();
			}

			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && 
					DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				
				if (frontImage!='none' && myImages.images[frontImage.id].numPeca == "1" && jugar){
					idSegon = frontImage.id;
					primerClic = true;	
				}
			}
			
		//Disable the current active image
		}else{

			if(primerClic==true && myImages.images[idSegon].colocada==false && jugar){
				myImages.images[idSegon].setHidden(true);
				colocades++;
				aciertos++;
				primerClic = false;
			}

			if(frontImage!='none' && jugar){
				if(frontImage!='notfound') frontImage.unsetDraggable();
				frontImage='none';
				intentos++;
			}
		}
	
		//COMPROVAR ESTAT ACTIVITAT
		if(colocades == encerts && colocades != 0 && jugar){
			this.acabat=true;
			context.canvas.style.cursor = 'url(./images/ok.cur), wait';
			var im = new Image();
			im.src=fitxeracabar;
			context.drawImage(im,(canvasWidth/2)-64,(canvasHeight/2)-64,128,128);
			/*if (reprodSoFi == "PLAY_AUDIO"){
				soundManager.play(arxiuSoFi);
				reprodSoFi = "false";
			}*/
		}else{
			segons++;
			
			//DRAW THE IMAGE
			if (!gradiente){
				grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
			}else{
				grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
			}
			grid.drawFonsJoc(colorfonsjoc, "0", margin);
			myImages.draw(colorhidden);
			grid.draw(colorlinies);
		}
	
		contextControl.fillStyle = "black";
		contextControl.font = "14pt Arial";
		contextControl.textAlign = "center";
		tiempo = segons/20;
		tiempo = arrodonir(tiempo,0);
		
		
		if (android){
			contextControl.fillText(aciertos, 40, 240);
			contextControl.fillText(intentos, 40, 290);
			contextControl.fillText(tiempo, 40, 340);
		}else{
			contextControl.fillText(aciertos, 890, 50);
			contextControl.fillText(intentos, 940, 50);
			contextControl.fillText(tiempo, 990, 50);
		}
		
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

