/**
 * ACTIVITAT PUZZLE
 */
function PuzzleIntercanvi(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	//var myText = new Text();
	
	//Variables especifiques d'aquesta activitat
	this.frontImage='none';
	var colocades=0;
	var colocades2=0;
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
	var colorlinies;
	var colorfons, colorinactiu, colorfonsjoc, colorfonsalt, colorfonsbaix, gradiente, background;
	var control = "0x";
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var arxiuSoFi, reprodSoFi, reprodSo, imageAtallar;
	var grid;
	
	//Funcio per a inicialitzar l'activitat a partir de les seves dades
	this.init = function(canvas, activityData){
		//Inicialitzar el canvas
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		context = canvas.getContext("2d");
		context.canvas.style.cursor = "pointer";
		
		//Inicialitzar les imatges
		var myImage = new Image();
		
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
		if (!colorfonsjoc) colorfonsjoc="#FFFFFF"; 
		colorfonsjoc = "#"+colorfonsjoc.replace(control,"");
		
		colorlinies = activityData.celllist[0].atributs['style-color-border'];
		if (!colorlinies) colorlinies = "#FFFF66";
		colorlinies = "#"+colorlinies.replace(control,"");
		
		/*reprodSo = activityData.cell[0].atributs['media-type'];
		reprodSoFi = activityData.cell[1].atributs['media-type'];
		
		arxiuSo = activityData.cell[0].atributs['media-file'];
		arxiuSoFi = activityData.cell[1].atributs['media-file'];*/
		
		imageAtallar = activityData.celllist[0].atributs.image;
		
		lines=arrodonir (activityData.celllist[0].atributs.rows,0);
		cols=arrodonir (activityData.celllist[0].atributs.cols,0);
		
		if (imageAtallar)
		{
			myImage.onload = function() {
				
				imageLoaded = true;
				w=myImage.width;
				h=myImage.height;
				showW=arrodonir(w,0);
				showH=arrodonir(h,0);

				if(w > (canvasWidth-50)){
					showW=w-(w-canvasWidth-50);
					showH=h-(w-canvasWidth-50);
					//if(h > (canvasHeight-50)) showH=h-(w-(canvasWidth));
					gridAx=((canvasWidth-showW-50)/2)+25; 
					gridAy=((canvasHeight-showH-50)/2)+25;
				}
				else if(h > (canvasHeight-50)){
					showH=h-(h-(canvasHeight-50));
					showW=canvasWidth-50;
					gridAx=((canvasWidth-showW-50)/2)+25; 
					gridAy=((canvasHeight-showH-50)/2)+25; 
				}else{
					gridAx=((canvasWidth-w)/2);
					gridAy=((canvasHeight-h)/2);
				}
	
				peces = createPeca(context, myImage, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy}, {w:showW,h:showH});
				
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
		
			myImage.src = imageAtallar;
			
		}else{
			
			/** mirar si la serie va ordenada amb files o columnes **/
			if(lines > 1) quantitat = lines;
			else quantitat = cols;
			
			showW = arrodonir (activityData.celllist[0].atributs.cellWidth,0);
			showH = arrodonir (activityData.celllist[0].atributs.cellHeight,0);
			
			w = showW*cols;
			h = showH*lines;
			
			gridAx = (canvasWidth-24-w)/2;
			gridAy = (canvasHeight-24-h)/2;
			
			//i si es passa de mides???
			if (lines > 1){
				if(w+24 > canvasWidth){
					gridAx = (canvasWidth-24-w)/2;
				}
			}else{
				if(h+24 > canvasWidth){
					
				} 
			}
			
			var id_img=0;
			var numPeca=0;
			var pecesSegon = new Array();
			
			for(var i=0; i<quantitat; i++)
			{
				var myImage = new Image();
				
				myImage.onload = function() {
					imageLoaded = true;
				};
	
				myImage.src = activityData.celllist[0].cell[i].atributs.image;
				var novaPecaImatges = new ImageMemory(context, id_img, myImage,showW,showH);
				novaPecaImatges.setNumPeca(numPeca);
				novaPecaImatges.llocPeca=i;
				pecesSegon.push(novaPecaImatges);
				id_img++;
				numPeca++;
			}
			
			/**
			 * Desordenem les peces.
			 */
			
			for (var o=0;o<quantitat;o++){
				ordArray[o]=o;
			}
			
			theX = gridAx;
			theY = gridAy;
			var o = 0;
			var quantitat;
		    
			if (lines > 1){
				for(var i=0; i<lines; i++){ 
		        	x[o]=theX;
		        	y[o]=theY;
		        	theY += showH;
		            o++;
			    }
			}else{
				for(var i=0; i<cols; i++){ 
		        	x[o]=theX;
		        	y[o]=theY;
		        	theX += showW;
		            o++;
			    }
			}
			
			ordArray.sort( randOrd );
			for (var o=0; o<quantitat; o++){
				pecesSegon[o].setPosx(x[ordArray[o]]);
				pecesSegon[o].setPosy(y[ordArray[o]]);
			}
			
			for (var o=0; o<quantitat; o++){
				pecesSegon[o].setHidden(false);
				myImages.add(pecesSegon[o]);
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
	this.run = function(canvasControl) {
		contextControl = canvasControl.getContext("2d");
		context.clearRect(0, 0, canvasWidth, canvasHeight);

		if (imageAtallar)
		{
			grid = new Grid(context, lines, cols, {width:showW,height:showH}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
			w=showW;
			h=showH;
		}else{
			grid = new Grid(context, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
		}
		
		if(colocades < lines*cols)
		{
			if(DragData.active)
			{  
				if(this.frontImage=='none'){	
					this.frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
					if(this.frontImage!='notfound') this.frontImage.setDraggable();
				}
				
				//MIRAR SI S'HA CLICAT FORA DEL PANELL
				if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w 
						&& DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
					primerClic = true;				
					if (segonClic==true){
						this.idSegon = this.frontImage.id;
						tercerClic=true;
					}else{
						this.idPrimer=this.frontImage.id;
					}
				}
			}else{
				//MIRAR SI S'HA CLICAT FORA DEL PANELL
				if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w 
						&& DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
					if(tercerClic==true)
					{
						var auxX = myImages.images[this.idPrimer].posx;
						var auxY = myImages.images[this.idPrimer].posy;
						
						myImages.images[this.idPrimer].setPosx(myImages.images[this.idSegon].posx);
						myImages.images[this.idPrimer].setPosy(myImages.images[this.idSegon].posy);
						
						var p = myImages.images[this.idSegon].llocPeca;
						myImages.images[this.idSegon].llocPeca = myImages.images[this.idPrimer].llocPeca;
						myImages.images[this.idPrimer].llocPeca = p;
						
						myImages.images[this.idSegon].setPosx(auxX);
						myImages.images[this.idSegon].setPosy(auxY);
		
						segonClic = false;
						primerClic = false;
						tercerClic = false;
						this.idPrimer='none';
						this.idSegon='none';
						
						intentos++;
						
						//Si és una imatge està tallada comptar quantes peces estan al seu lloc
						if(imageAtallar){
							colocades2=0;
							for (var i=0; i<lines*cols;i++){
								if(myImages.images[i].llocPeca == i){
									colocades2++;	
								}
							}
							aciertos = colocades2;
						}
						
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
			
			colocades=0;
			//COMPROVAR SI ESTAN TOTES COL·LOCADES
			if(!imageAtallar){
				for (var i=0; i<lines*cols;i++){
					if (myImages.images[i].posx==x[i] && myImages.images[i].posy==y[i]){
						colocades++;	
					}
				}
				aciertos = colocades;
			}
			
		}

		//COMPROVAR ESTAT ACTIVITAT
		if(colocades==lines*cols || colocades2 == lines*cols){
			this.acabat=true;
			context.canvas.style.cursor = 'url(./images/ok.cur), wait';
			var im = new Image();
			im.src=fitxeracabar;
			context.drawImage(im,(canvasWidth/2)-64,(canvasHeight/2)-64,128,128);
			//if (reprodSoFi == "PLAY_AUDIO") soundManager.play(arxiuSoFi);
		}else{
			segons++;
			//DRAW THE IMAGE
			if (!gradiente){
				grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
			}else{
				grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
			}
			grid.drawFonsJoc(colorfonsjoc, "0", margin);
			myImages.draw();
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
		
		if(segonClic){
			context.lineWidth = "5.0";
			if (imageAtallar) context.strokeRect(myImages.images[this.idPrimer].posx,myImages.images[this.idPrimer].posy,(showW/cols),(showH/lines));
			else context.strokeRect(myImages.images[this.idPrimer].posx,myImages.images[this.idPrimer].posy,showW,showH);
			context.lineWidth = "1.0";
		}
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

