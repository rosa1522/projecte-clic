/**
 * ACTIVITAT PUZZLE
 */
function PuzzleDoble(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	//var myText = new Text();
	var grid;
	
	//Variables especifiques d'aquesta activitat
	var frontImage='none';
	var colocades=0;
	this.acabat=false;
	var lines,cols;
	var myImages = new ImageSet();
	var peces;
	var dist;
	var x = new Array();
	var y = new Array();
	var ordArray = new Array();
	var w, h, showW, showH, gridBx, gridBy, gridAx, gridAy;
	var colorfons, colorlinies, colorinactiu, colorfonsjoc, colorfonsalt, colorfonsbaix, gradiente, background;
	var control = "0x";
	var margin;
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var arxiuSoFi, reprodSoFi, reprodSo, joc;
	var theX, theY;
	var myImagesMem = new ImageSetMemory();
	
	//Funcio per a inicialitzar l'activitat a partir de les seves dades
	this.init = function(canvas, activityData){
		//Inicialitzar el canvas
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		context = canvas.getContext("2d");
		
		//Inicialitzar la font
		//myText.context = context;
		//myText.face = vector_battle;
		
		//Inicialitzar les imatges
		var myImage = new Image();
		
		dist = activityData.atributsActivitat['layout-position'];
		
		colorfonsbaix = activityData.atributsActivitat['settings-container-gradient-dest'];
		colorfonsbaix = "#"+colorfonsbaix.replace(control,"");
		
		colorfonsalt = activityData.atributsActivitat['settings-container-gradient-source'];
		colorfonsalt = "#"+colorfonsalt.replace(control,"");
		
		gradiente = activityData.atributsActivitat['settings-container-gradient-angle'];
		
		margin = activityData.atributsActivitat['settings-margin'];
		
		background = activityData.atributsActivitat['settings-container-bgColor'];
		if (!background) background="#FFFFFF"; 
		background = "#"+background.replace(control,"");
		
		colorfonsjoc = activityData.atributsActivitat['settings-window-bgColor'];
		colorfonsjoc = "#"+colorfonsjoc.replace(control,"");
		
		colorlinies = activityData.celllist[0].atributs['style-color-border'];
		if (!colorlinies) colorlinies = "0x00000";
		colorlinies = "#"+colorlinies.replace(control,"");
		
		colorinactiu = activityData.celllist[0].atributs['style-color-inactive'];
		if (!colorinactiu) colorinactiu = "0x00000";
		colorinactiu = "#"+colorinactiu.replace(control,"");
		
		reprodSo = activityData.cell[0].atributs['media-type'];
		reprodSoFi = activityData.cell[1].atributs['media-type'];
		
		arxiuSo = activityData.cell[0].atributs['media-file'];
		arxiuSoFi = activityData.cell[1].atributs['media-file'];
		
		joc = activityData.celllist[0].atributs['shaper-class'];
		
		if (joc == "@ClassicJigSaw"){
		
			myImage.onload = function() {
				imageLoaded = true;
				var w=myImage.width;
				var h=myImage.height;
				var showW=myImage.width;
				var showH=myImage.height;
				var gridAx, gridAy, gridBx, gridBy;
	
				if (dist == "AB"){
					gridAx=(canvasWidth-(w+w+12))/2;
					gridAy=(canvasHeight-h)/2;
					gridBx=gridAx+w+12;
					gridBy=gridAy;
					if((w+w+24) > canvasWidth){
						showW=(canvasWidth-24)/2; 
						showH=h-(w-((canvasWidth-24)/2));
					}
					/*if(showH > canvasHeight){
						showH=(canvasHeight-showH);
						showW=showW-(showH-((canvasHeight-24)/2)); 
					}*/
					gridAx=(canvasWidth-(showW+showW+12))/2; 
					gridAy=(canvasHeight-showH)/2; 
					gridBx=gridAx+showW+12; 
					gridBy=gridAy;
				}
				else if (dist == "BA"){
					gridBx=(canvasWidth-(w+w+12))/2;
					gridBy=(canvasHeight-h)/2;
					gridAx=gridBx+w+12;
					gridAy=gridBy;
					if((w+w+24) > canvasWidth){
						showW=(canvasWidth-24)/2; 
						showH=h-(w-((canvasWidth-24)/2)); 
					}
					if(showH > canvasHeight){
						showH=(canvasHeight-24)/2;
						showW=showW-(showH-((canvasHeight-24)/2)); 
					}
					gridBx=(canvasWidth-(showW+showW+12))/2; 
					gridBy=(canvasHeight-showH)/2; 
					gridAx=gridBx+showW+12; 
					gridAy=gridBy;
				}
				else if (dist == "AUB"){
					gridAx=(canvasWidth-w)/2;
					gridAy=(canvasHeight-(h+h+12))/2;
					gridBx=gridAx;
					gridBy=gridAy+h+12;
					if((h+h+24) > canvasHeight){
						showH=(canvasHeight-24)/2;
						showW=w-(h-((canvasHeight-24)/2));
						gridAx=(canvasWidth-showW)/2; 
						gridAy=(canvasHeight-(showH+showH+12))/2;
						gridBx=gridAx;
						gridBy=gridAy+showH+12;
					}
				}
				else if (dist == "BUA"){
					gridBx=(canvasWidth-w)/2;
					gridBy=(canvasHeight-(h+h+12))/2;
					gridAx=gridBx;
					gridAy=gridBy+h+12;
					if((h+h+24) > canvasHeight){
						showH=(canvasHeight-24)/2;
						showW=w-(h-((canvasHeight-24)/2)); 
						gridBx=(canvasWidth-showW)/2; 
						gridBy=(canvasHeight-(showH+showH+12))/2;
						gridAx=gridBx;
						gridAy=gridBy+showH+12;
					}
				}
				
				lines=activityData.celllist[0].atributs.rows;
				cols=activityData.celllist[0].atributs.cols;
					
				peces = createPeca(context, myImage, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridBx,y:gridBy}, {w:showW,h:showH});
				
				/*****************DESORDENAR PECES*************************/ 
				for (var o=0;o<lines*cols;o++){
					ordArray[o]=o;
				}
				
				ordArray.sort( randOrd );
				
				for (var o=0;o<peces.length;o++){
					x[o]=peces[o].posx;
					y[o]=peces[o].posy;
				}
				
				for (var o=0;o<peces.length;o++){
					peces[o].setPosx(x[ordArray[o]]);
					peces[o].setPosy(y[ordArray[o]]);
				}	
				/************************************************************/
			
				for (var o=0;o<peces.length;o++){	
					myImages.add(peces[o]);
				}
			};
			
			myImage.src = activityData.celllist[0].atributs.image;

		}else {
			
			w=activityData.celllist[0].atributs.cellWidth;
			h=activityData.celllist[0].atributs.cellHeight;
			
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
				novaPeca.setNumPeca(myImage.src);
				peces.push(novaPeca);
				id_img++;
			}

			/**
			 * Desordenem les peces.
			 */
			
			for (var o=0;o<ll;o++){
				ordArray[o]=o;
			}
			
			//Si es columnes varies
			theX = gridAx;
			theY = gridAy;
		
			for(var i=0;i<ll;i++) { 
		        x[i]=theX;
		        y[i]=theY;
		        theX += incrShowX;
		    }
			
			ordArray.sort( randOrd );
			
			for (var o=0;o<ll;o++){
				peces[o].setPosx(x[ordArray[o]]);
				peces[o].setPosy(y[ordArray[o]]);
			}	

			/**
			 * Pintem el tauler de peces.
			 */
			
			for (var o=0;o<ll;o++){	
				myImagesMem.add(peces[o]);
				myImagesMem.images[o].setHidden(false);
			}
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
		segons++;
		
		if (joc == "@ClassicJigSaw"){
			grid = new Grid(context, lines, cols, {width:showW,height:showH}, {x:gridBx,y:gridBy}, {x:gridAx,y:gridAy});
		}else{
			grid = new Grid(context, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
		}
		
		//LLEGIR DADES USUARI
		if(DragData.active)
		{ 
			if (joc == "@ClassicJigSaw"){
				//Choose the selected image and activate it
				if(frontImage=='none'){	
					frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
					if(frontImage!='notfound') frontImage.setDraggable();
					
				}
				//Move the dragged image around
				if(frontImage!='notfound' && !frontImage.colocada){
					frontImage.drag(DragData.startPosX-DragData.currentPosX, DragData.startPosY-DragData.currentPosY);
				}
			}
			
		//Disable the current active image
		}else{
			
			if (joc == "@ClassicJigSaw"){
				if (!frontImage.colocada){	//per col·locar-la no h a d'estar col·locada
					if (DragData.currentPosX>=(frontImage.colocaciox)&&DragData.currentPosX<=(frontImage.colocaciox+frontImage.w)&&
							DragData.currentPosY>=(frontImage.colocacioy)&&DragData.currentPosY<=(frontImage.colocacioy+frontImage.h)){
						frontImage.posx=frontImage.colocaciox;
						frontImage.posy=frontImage.colocacioy;
						colocades++;
						frontImage.colocada=true;
						intentos++;
					}else if(frontImage != 'none'){	//Si no es col·loca on toca torna a posició inicial
						frontImage.posx=frontImage.iniposx;
						frontImage.posy=frontImage.iniposy;
						intentos++;
					}
				}else{
					//si ja estava col·locada torna a col·locar-se bé!!
					frontImage.posx=frontImage.colocaciox;
					frontImage.posy=frontImage.colocacioy;
				}
					
				if(frontImage!='none'){
					if(frontImage!='notfound') frontImage.unsetDraggable();
					frontImage='none';
				}
			}
		}
		
		//COMPROVAR ESTAT ACTIVITAT
		if(colocades==lines*cols){
			this.acabat=true;
			if (reprodSoFi == "PLAY_AUDIO"){
				soundManager.play(arxiuSoFi);
				reprodSoFi = "false";
			}
		}
		
		//DRAW THE IMAGE
		
		if (!gradiente){
			grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
		}else{
			grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
		}
		//if (joc == "@ClassicJigSaw"){
			grid.drawFonsJoc(colorfonsjoc, dist, margin);
			grid.drawFonsInactiu(colorinactiu);
			myImages.draw();
			grid.draw(colorlinies);
		/*}else{
			grid.drawFonsJoc(colorfonsjoc, "0", margin);
			grid.drawFonsInactiu(colorinactiu);
			myImagesMem.draw();
			grid.draw(colorlinies);
		}*/
			
		contextControl.fillStyle = "black";
		contextControl.font = "14pt Arial";
		tiempo = segons/20;
		tiempo = arrodonir(tiempo,0);
			
		//if (android){
			contextControl.fillText(colocades, 35, 250);
			contextControl.fillText(intentos, 35, 300);
			contextControl.fillText(tiempo, 30, 350);
		/*}else{
			contextControl.fillText(colocades, 890, 60);
			contextControl.fillText(intentos, 940, 60);
			contextControl.fillText(tiempo, 990, 60);
		}*/
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}
