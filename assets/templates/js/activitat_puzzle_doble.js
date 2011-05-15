/**
 * ACTIVITAT PUZZLE
 */
function PuzzleDoble(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	var myText = new Text();
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
	var colorfons, colorlinies, colorinactiu, colorfonsjoc, colorfonsalt, colorfonsbaix, gradiente;
	var control = "0x";
	var margin;
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
		
		//Inicialitzar la font
		myText.context = context;
		myText.face = vector_battle;
		
		//Inicialitzar les imatges
		var myImage = new Image();
		
		dist = activityData.atributsActivitat['layout-position'];
		
		colorfonsbaix = activityData.atributsActivitat['settings-container-gradient-dest'];
		colorfonsbaix = "#"+colorfonsbaix.replace(control,"");
		
		colorfonsalt = activityData.atributsActivitat['settings-container-gradient-source'];
		colorfonsalt = "#"+colorfonsalt.replace(control,"");
		
		gradiente = activityData.atributsActivitat['settings-container-gradient-angle'];
		
		margin = activityData.atributsActivitat['settings-margin'];
		
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
		
		arxiuSo = "./images/" + activityData.cell[0].atributs['media-file'];
		arxiuSoFi = "./images/" + activityData.cell[1].atributs['media-file'];
		
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
					gridAx=(canvasWidth-(showW+showW+12))/2; 
					gridAy=(canvasHeight-showH)/2; 
					gridBx=gridAx+showW+12; 
					gridBy=gridAy;
				}
			}
			else if (dist == "BA"){
				gridBx=(canvasWidth-(w+w+12))/2;
				gridBy=(canvasHeight-h)/2;
				gridAx=gridBx+w+12;
				gridAy=gridBy;
				if((w+w+24) > canvasWidth){
					showW=(canvasWidth-24)/2; 
					showH=h-(w-((canvasWidth-24)/2)); 
					gridBx=(canvasWidth-(showW+showW+12))/2; 
					gridBy=(canvasHeight-showH)/2; 
					gridAx=gridBx+showW+12; 
					gridAy=gridBy;
				}
			}
			else if (dist == "AUB"){
				//if((h+h+12) > 582){ hh=285; ww=w-(h-285);} 
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
				//if((h+h+12) > 582){ hh=285; ww=w-(h-285);}
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
			
			grid = new Grid(context, lines, cols, {width:showW,height:showH}, {x:gridBx,y:gridBy}, {x:gridAx,y:gridAy});
			
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
		
		myImage.src = "./images/" + activityData.celllist[0].atributs.image;
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
		
		//LLEGIR DADES USUARI
		if(DragData.active)
		{
			//myText.renderText('DRAG FROM: mm:'+margin+' . '+DragData.startPosX+' '+DragData.startPosY, 24, 10,30);  
			
			//Choose the selected image and activate it
			if(frontImage=='none'){	
				frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
				if(frontImage!='notfound') frontImage.setDraggable();
				
			}
			//Move the dragged image around
			if(frontImage!='notfound'){
				frontImage.drag(DragData.startPosX-DragData.currentPosX, DragData.startPosY-DragData.currentPosY);
			}
			
		//Disable the current active image
		}else{
			if (!frontImage.colocada){	//per col·locar-la no h a d'estar col·locada
				if (DragData.currentPosX>=(frontImage.colocaciox)&&DragData.currentPosX<=(frontImage.colocaciox+frontImage.w)&&
						DragData.currentPosY>=(frontImage.colocacioy)&&DragData.currentPosY<=(frontImage.colocacioy+frontImage.h)){
					frontImage.posx=frontImage.colocaciox;
					frontImage.posy=frontImage.colocacioy;
					colocades++;
					frontImage.colocada=true;
				}else{	//Si no es col·loca on toca torna a posició inicial
					frontImage.posx=frontImage.iniposx;
					frontImage.posy=frontImage.iniposy;
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
		
		//COMPROVAR ESTAT ACTIVITAT
		if(colocades==lines*cols){
			this.acabat=true;
			if (reprodSoFi == "PLAY_AUDIO") soundManager.play(arxiuSoFi);
		}
		
		//DRAW THE IMAGE
		grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
		grid.drawFonsJoc(colorfonsjoc, dist, margin);
		grid.drawFonsInactiu(colorinactiu);
		myImages.draw();
		grid.draw(colorlinies);
		
		contextControl.fillStyle = "black";
		contextControl.font = "14pt Arial";
		contextControl.fillText(aciertos, 890, 60);
		contextControl.fillText(intentos, 940, 60);
		
		tiempo = segons/20;
		tiempo = arrodonir(tiempo,0);
		contextControl.fillText(tiempo, 990, 60);
	
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

