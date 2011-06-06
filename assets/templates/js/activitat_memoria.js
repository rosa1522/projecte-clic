/**
 * ACTIVITAT PUZZLE
 */
function Memoria(){
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
	var grid;
	var peces;
	var dist;
	var x = new Array();
	var y = new Array();
	var ordArray = new Array();
	var numPrimer, numSegon;
	var primerClic = false;
	var segonClic = false;
	var tercerClic = false;
	var idPrimer = 'none';
	var idSegon = 'none';
	var numPeca=1;
	var colorlinies, colorhidden, colorfons, background;
	var theX, theY, incrShowX, incrShowY;
	var control = "0x";
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var arxiuSoFi, reprodSoFi, reprodSo;
	
	//Funcio per a inicialitzar l'activitat a partir de les seves dades
	this.init = function(canvas, activityData){
		//Inicialitzar el canvas
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		context = canvas.getContext("2d");
		
		/**
		 * Agafem les dades del fitxer data.js
		 */

		//w=activityData.celllist[0].atributs.cellWidth;
		//h=activityData.celllist[0].atributs.cellHeight;
		w=100;
		h=70;
		
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
		
		reprodSo = activityData.cell[0].atributs['media-type'];
		reprodSoFi = activityData.cell[1].atributs['media-type'];
		
		arxiuSo = activityData.cell[0].atributs['media-file'];
		arxiuSoFi = activityData.cell[1].atributs['media-file'];
		
		/**
		 * A partir del layout-position calculem:
		 *	 w: amplada general
		 *	 h: alçada general
		 *	 incrShowX: amplada peça
		 *	 incrShowY: alçada peça
		 */
		
		if ((dist == "AB")||(dist == "BA")){
			lines=activityData.celllist[0].atributs.rows*4;
			cols=activityData.celllist[0].atributs.cols/2;
				
			incrShowY=(canvasHeight-24)/lines;
			aux=h-incrShowY;
			h=(canvasHeight-24);
			incrShowX=w-aux;
			w=incrShowX*cols;
		}
		if ((dist == "AUB")||(dist == "BUA")){
			lines=activityData.celllist[0].atributs.rows*2;
			cols=activityData.celllist[0].atributs.cols;
 
			incrShowX=(canvasWidth-24)/cols; 
			aux=w-incrShowX;
			w=(canvasWidth-24);
			incrShowY=h-aux;
			h=incrShowY*lines;
		}
		
		gridAx=(canvasWidth-w)/2;
		gridAy=(canvasHeight-h)/2;

		/**
		 * Carreguem les imatges a la array Peces.
		 */
		
		var id_img=0;
		var peces = new Array();
		var ll = activityData.celllist[0].cell.length;
		
		for(var i=0; i<ll; i++){
			
			var myImage = new Image();
			
			myImage.onload = function() {
				imageLoaded = true;
			};

			myImage.src = activityData.celllist[0].cell[i].atributs.image;
			var novaPeca = new ImageMemory(context, id_img, myImage,incrShowX,incrShowY);
			novaPeca.setNumPeca(numPeca);
			peces.push(novaPeca);
			id_img++;
			
			var novaPeca2 = new ImageMemory(context, id_img, myImage,incrShowX,incrShowY);
			novaPeca2.setNumPeca(numPeca);
			peces.push(novaPeca2);
			id_img++;
			
			numPeca++;
			
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
		
		ordArray.sort( randOrd );
		
		for (var o=0;o<peces.length;o++){
			peces[o].setPosx(x[ordArray[o]]);
			peces[o].setPosy(y[ordArray[o]]);
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
		context.clearRect(0, 0, canvasWidth, canvasHeight);
		segons++;
		context.strokeRect(gridAx,gridAy,w,h);

		grid = new Grid(context, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
		
		//LLEGIR DADES USUARI
		if(DragData.active)
		{
			if(frontImage=='none'){	
				frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
				if(frontImage!='notfound') frontImage.setDraggable();
			}

			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && 
					DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				primerClic = true;
				if (segonClic==true && myImages.images[frontImage.id].colocada==false){
					idSegon = frontImage.id;
					myImages.images[idSegon].setHidden(false);
					
					numPrimer = myImages.images[idPrimer].numPeca;
					numSegon = myImages.images[idSegon].numPeca;
					
					tercerClic=true;
				}
			}
			
		//Disable the current active image
		}else{

			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && 
					DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				if(tercerClic==true){
					if(numPrimer != numSegon || idPrimer == idSegon){
						myImages.images[idPrimer].setHidden(true);
						myImages.images[idSegon].setHidden(true);
					}else{
						myImages.images[idPrimer].setColocada(true);
						myImages.images[idSegon].setColocada(true);
						colocades++;
						aciertos++;
					}
					
					segonClic = false;
					primerClic = false;
					tercerClic = false;
					idPrimer='none';
					idSegon='none';
					intentos++;
				}
				
				if(primerClic==true && myImages.images[frontImage.id].colocada==false){
					idPrimer = frontImage.id;
					myImages.images[idPrimer].setHidden(false);
					segonClic = true;
				}
			}
			
			if(frontImage!='none'){
				if(frontImage!='notfound') frontImage.unsetDraggable();
				frontImage='none';
			}
			primerClic = false;
		}	
	
		//COMPROVAR ESTAT ACTIVITAT
		if(colocades==(numPeca-1)){
			this.acabat=true;
			/*if (reprodSoFi == "PLAY_AUDIO"){
				soundManager.play(arxiuSoFi);
				reprodSoFi = "false";
			}*/
		}
		
		//DRAW THE IMAGE
		if (!gradiente){
			grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
		}else{
			grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
		}
		grid.drawFonsJoc(colorfonsjoc, "0", margin);
		myImages.draw(colorhidden);
		grid.draw(colorlinies);
		
		contextControl.fillStyle = "black";
		contextControl.font = "14pt Arial";
		tiempo = segons/20;
		tiempo = arrodonir(tiempo,0);
		
		if (android){
			contextControl.fillText(aciertos, 35, 250);
			contextControl.fillText(intentos, 35, 300);
			contextControl.fillText(tiempo, 30, 350);
		}else{
			contextControl.fillText(aciertos, 890, 60);
			contextControl.fillText(intentos, 940, 60);
			contextControl.fillText(tiempo, 990, 60);
		}
		
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}
