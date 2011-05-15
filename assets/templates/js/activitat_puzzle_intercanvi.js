/**
 * ACTIVITAT PUZZLE
 */
function PuzzleIntercanvi(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	var myText = new Text();
	
	//Variables especifiques d'aquesta activitat
	this.frontImage='none';
	var colocades=0;
	this.acabat=false;
	var lines,cols;
	var w,h;
	var myImages = new ImageSet();
	var grid;
	var peces = new Array();
	var dist;
	var x = new Array();
	var y = new Array();
	var ordArray = new Array();
	var showW, showH, gridAx, gridAy;
	var primerClic = false;
	var segonClic = false;
	var tercerClic = false;
	this.idPrimer = 'none';
	this.idSegon = 'none';
	var my_gradient;
	this.colorlinies;
	var colorfons, colorinactiu, colorfonsjoc, colorfonsalt, colorfonsbaix, gradiente;
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
		
		//Inicialitzar la font
		myText.context = context;
		myText.face = vector_battle;
		
		//Inicialitzar les imatges
		var myImage = new Image();
		
		colorfonsbaix = activityData.atributsActivitat['settings-container-gradient-dest'];
		colorfonsbaix = "#"+colorfonsbaix.replace(control,"");
		
		colorfonsalt = activityData.atributsActivitat['settings-container-gradient-source'];
		colorfonsalt = "#"+colorfonsalt.replace(control,"");
		
		gradiente = activityData.atributsActivitat['settings-container-gradient-angle'];
		
		margin = activityData.atributsActivitat['settings-margin'];
		
		colorfonsjoc = activityData.atributsActivitat['settings-window-bgColor'];
		colorfonsjoc = "#"+colorfonsjoc.replace(control,"");
		
		this.colorlinies = activityData.celllist[0].atributs['style-color-border'];
		if (!this.colorlinies) this.colorlinies = "0x00000";
		this.colorlinies = "#"+this.colorlinies.replace(control,"");
		
		reprodSo = activityData.cell[0].atributs['media-type'];
		reprodSoFi = activityData.cell[1].atributs['media-type'];
		
		arxiuSo = "./images/" + activityData.cell[0].atributs['media-file'];
		arxiuSoFi = "./images/" + activityData.cell[1].atributs['media-file'];
		
		myImage.onload = function() {
			
			imageLoaded = true;
			w=myImage.width;
			h=myImage.height;
			showW=myImage.width;
			showH=myImage.height;
			
			gridAx=(canvasWidth-w-50)/2;
			gridAy=(canvasHeight-h-50)/2;
			
			if(w > (canvasWidth-50)){
				showW=w-(w-(canvasWidth-50));
				showH=h;
				if(h > (canvasHeight-50)) showH=h-(w-(canvasWidth-50));
				gridAx=25; 
				gridAy=(canvasHeight-showH)/2; 
			}
			else if(h > (canvasHeight-50)){
				showH=h-(h-(canvasHeight-50));
				showW = w;
				if(w > (canvasWidth-50)) showW=w-(h-(canvasHeight-50));
				gridAx=(canvasWidth-showW)/2; 
				gridAy=25; 
			}
			
			lines=activityData.celllist[0].atributs.rows;
			cols=activityData.celllist[0].atributs.cols;
				
			peces = createPeca(context, myImage, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy}, {w:showW,h:showH});
			
			grid = new Grid(context, lines, cols, {width:showW,height:showH}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
			
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
				peces[o].llocPeca=ordArray[o];
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
	this.run = function(canvasControl) {
		contextControl = canvasControl.getContext("2d");
		context.clearRect(0, 0, canvasWidth, canvasHeight);
		segons++;

		if(DragData.active)
		{  
			if(this.frontImage=='none'){	
				this.frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
				if(this.frontImage!='notfound') this.frontImage.setDraggable();
			}
			
			//MIRAR SI S'HA CLICAT FORA DEL PANELL
			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				primerClic = true;
				if (segonClic==true){
					this.idSegon = this.frontImage.id;
					tercerClic=true;
				}else{
					this.idPrimer=this.frontImage.id;
				}
			}
		}else{

			colocades=0;
			
			//MIRAR SI S'HA CLICAT FORA DEL PANELL
			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				if(tercerClic==true)
				{
					var auxX = myImages.images[this.idPrimer].posx;
					var auxY = myImages.images[this.idPrimer].posy;
					
					myImages.images[this.idPrimer].setPosx(myImages.images[this.idSegon].posx);
					myImages.images[this.idPrimer].setPosy(myImages.images[this.idSegon].posy);
					
					myImages.images[this.idSegon].setPosx(auxX);
					myImages.images[this.idSegon].setPosy(auxY);
	
					segonClic = false;
					primerClic = false;
					tercerClic = false;
					this.idPrimer='none';
					this.idSegon='none';
					
					intentos++;
					
					//COMPROVAR SI ESTAN TOTES COL·LOCADES
					for (var i=0; i<lines*cols;i++){
						if (myImages.images[i].posx==myImages.images[i].colocaciox 
								&& myImages.images[i].posy==myImages.images[i].colocacioy){
							colocades++;	
						}
					}
					aciertos = colocades;
				}
				if(primerClic==true){
					segonClic = true;
				}
			}
			
			if(this.frontImage!='none'){
				if(this.frontImage!='notfound') this.frontImage.unsetDraggable();
				this.frontImage='none';
			}	
		}		
		
		//COMPROVAR ESTAT ACTIVITAT
		if(colocades==lines*cols){
			this.acabat=true;
			if (reprodSoFi == "PLAY_AUDIO") soundManager.play(arxiuSoFi);
		}
		
		//DRAW THE IMAGE
		grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
		grid.drawFonsJoc(colorfonsjoc, "0", margin);
		myImages.draw();
		grid.draw(this.colorlinies);
		
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

