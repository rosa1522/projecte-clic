/**
 * ACTIVITAT PUZZLE
 * @author Noelia Tuset
 */
function SimpleAssociation()
{
	var context;
	var canvasWidth;
	var canvasHeight;
	
	/** Variables especifiques d'aquesta activitat **/
	var frontImage='none';
	this.acabat=false;
	var idPrimer = 'none';
	var idSegon = 'none';
	
	var myImages = new ImageSetMemory();
	var x = new Array();
	var y = new Array();
	var ordArray = new Array();
	var ordArrayImatges = new Array();
	var primerClic = false;
	var segonClic = false;
	var tercerClic = false;
	var control = "0x";
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var colocades=0;
	var numPeca=1;
	var lines, cols, w, h, w2, h2, grid, peces, dist;
	var colorlinies, colorhidden, colorfons, colorFonsNoms, background;
	var theX, theY, incrShowX, incrShowY, capes, numPrimer, numSegon;
	var fet=false;
	
	/**
	 * Funcio per a inicialitzar l'activitat a partir de les seves dades
	 */
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
		
		w2=arrodonir(activityData.celllist[1].atributs.cellWidth,0); if(w2 > w) w = w2;
		h2=arrodonir(activityData.celllist[1].atributs.cellHeight,0); if(h2 > h) h = h2;

		dist = activityData.atributsActivitat['layout-position'];
		
		colorhidden = activityData.celllist[0].cell[0].atributs['style-color-inactive'];
		if (!colorhidden) colorhidden = "#00000";
		colorhidden = "#"+colorhidden.replace(control,"");
		
		colorfonsbaix = activityData.atributsActivitat['settings-container-gradient-dest'];
		if (!colorfonsbaix) colorfonsbaix = "#00000";
		colorfonsbaix = "#"+colorfonsbaix.replace(control,"");
		
		colorfonsalt = activityData.atributsActivitat['settings-container-gradient-source'];
		if (!colorfonsalt) colorfonsalt = "#00000";
		colorfonsalt = "#"+colorfonsalt.replace(control,"");
		
		gradiente = activityData.atributsActivitat['settings-container-gradient-angle'];
		
		margin = activityData.atributsActivitat['settings-margin'];
		
		background = activityData.atributsActivitat['settings-container-bgColor'];
		if (!background) background="#FFFFFF"; 
		background = "#"+background.replace(control,"");
		
		colorfonsjoc = activityData.atributsActivitat['settings-window-bgColor'];
		if (!colorfonsjoc) colorfonsjoc = "#00000";
		colorfonsjoc = "#"+colorfonsjoc.replace(control,"");
		
		colorlinies = activityData.celllist[0].atributs['style-color-border'];
		if (!colorlinies) colorlinies = "#00000";
		colorlinies = "#"+colorlinies.replace(control,"");
		
		colorFonsNoms = "FFFFFF";
		colorFonsNomsSota = "AAFFAA";
		
		/**
		 * El tauler de joc depenent de la distribucio que tingui
		 * s'adapta a unes mides que es puguin mostrar les dades
		 * adaptades a la pantalla correctament.
		 */
		if ((dist == "AB")||(dist == "BA")){
			lines=activityData.celllist[0].atributs.rows;
			cols=activityData.celllist[0].atributs.cols;
			cols = cols*2;
		}
		
		if ((dist == "AUB")||(dist == "BUA")){
			lines=activityData.celllist[0].atributs.rows;
			lines = lines*2;
			cols=activityData.celllist[0].atributs.cols;
		}
		
		xy=adaptarGrid(canvasWidth,canvasHeight,w,h,lines,cols);
		incrShowX = xy[0];
		incrShowY = xy[1];
		h=incrShowY*lines;
		w=incrShowX*cols;
		
		gridAx=(canvasWidth-w)/2;
		gridAy=(canvasHeight-h)/2;

		/**
		 * Carreguem les imatges a la array Peces.
		 */
		
		var id_img=0;
		var pecesPrimer = new Array();
		var numPrimer = activityData.celllist[0].cell.length; //noms
		var hihaImatge = activityData.celllist[0].cell[0].atributs.image;
		
		if (hihaImatge)
		{
			for(var i=0; i<numPrimer; i++)
			{
				var myImage = new Image();
				
				myImage.onload = function() {
					imageLoaded = true;
				};
	
				myImage.src = activityData.celllist[0].cell[i].atributs.image;
				var novaPecaImatges = new ImageMemory(context, id_img, myImage,incrShowX,incrShowY);
				novaPecaImatges.setNumPeca(numPeca);
				pecesPrimer.push(novaPecaImatges);
				id_img++;
				numPeca++;
			}
		}else{
			for(var i=0; i<numPrimer; i++)
			{
				var nomImage = activityData.celllist[0].cell[i].atributs.p;
				var novaPeca = new ImageAssociation(context, id_img, nomImage,incrShowX,incrShowY);
				novaPeca.setNumPeca(numPeca);
				novaPeca.setColor(colorFonsNoms);
				pecesPrimer.push(novaPeca);
				id_img++;
				numPeca++;	
			}
		}
		
		numPeca=1;
		var pecesSegon = new Array();
		var numSegon = activityData.celllist[1].cell.length; //fotos
		var hihaImatge = activityData.celllist[1].cell[0].atributs.image;
		
		if (hihaImatge)
		{
			for(var i=0; i<numSegon; i++)
			{
				var myImage = new Image();
				
				myImage.onload = function() {
					imageLoaded = true;
				};
	
				myImage.src = activityData.celllist[1].cell[i].atributs.image;
				var novaPecaImatges = new ImageMemory(context, id_img, myImage,incrShowX,incrShowY);
				novaPecaImatges.setNumPeca(numPeca);
				pecesSegon.push(novaPecaImatges);
				id_img++;
				numPeca++;
			}
		}else{
			for(var i=0; i<numSegon; i++)
			{
				var nomImage = activityData.celllist[1].cell[i].atributs.p;
				var novaPeca = new ImageAssociation(context, id_img, nomImage,incrShowX,incrShowY);
				novaPeca.setNumPeca(numPeca);
				novaPeca.setColor(colorFonsNomsSota);
				pecesSegon.push(novaPeca);
				id_img++;
				numPeca++;	
			}
		}
		
		/**
		 * Desordenem les peces.
		 */
		for (var o=0;o<numPrimer;o++){
			ordArray[o]=o;
		}
		for (var o=0;o<numSegon;o++){
			ordArrayImatges[o]=o;
		}
		
		theX = gridAx;
		theY = gridAy;
		var o = 0;
	    
		if ((dist == "AUB")||(dist == "BUA")){
			for(var c=2; c<=lines; c++){
				for(var i=0;i<cols;i++){ 
		        	x[o]=theX;
		        	y[o]=theY;
		        	theX += incrShowX;
		            o++;
			    }
				theX = gridAx;
				theY += incrShowY;
			}
		}else{
			for(var c=2; c<=cols; c++){
				for(var i=0;i<lines;i++){ 
		        	x[o]=theX;
		        	y[o]=theY;
		        	theY += incrShowY;
		            o++;
			    }
				theY = gridAy;
				theX += incrShowX;
			}
		}
		ordArray.sort( randOrd );
		for (var o=0;o<pecesPrimer.length;o++){
			pecesPrimer[o].setPosx(x[ordArray[o]]);
			pecesPrimer[o].setPosy(y[ordArray[o]]);
		}
		
		if ((dist == "AUB")||(dist == "BUA")){
			theX = gridAx;
			theY = gridAy + (incrShowY*(lines/2));
			o=0;
			for(var c=2; c<=lines; c++){	
				for(var i=0;i<cols;i++){ 
		        	x[o]=theX;
		        	y[o]=theY;
		        	theX += incrShowX;
		            o++;
			    }
				theX = gridAx;
				theY += incrShowY;
			}
		}else{
			theX = gridAx + (incrShowX*(cols/2));
			theY = gridAy;
			o=0;
			for(var c=2; c<=cols; c++){
				for(var i=0;i<lines;i++){ 
		        	x[o]=theX;
		        	y[o]=theY;
		        	theY += incrShowY;
		            o++;
			    }
				theY = gridAy;
				theX += incrShowX;
			}
		}
		ordArrayImatges.sort( randOrd );
		for (var o=0;o<pecesSegon.length;o++){
			pecesSegon[o].setPosx(x[ordArrayImatges[o]]);
			pecesSegon[o].setPosy(y[ordArrayImatges[o]]);
		}
		
		/**
		 * Pintem el tauler de peces.
		 */
		grid = new Grid(context, lines/1, cols/1, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
		
		for (var o=0;o<pecesPrimer.length;o++){
			pecesPrimer[o].setHidden(false);
			myImages.add(pecesPrimer[o]);
		}
		for (var o=0;o<pecesSegon.length;o++){
			pecesSegon[o].setHidden(false);
			myImages.add(pecesSegon[o]);
		}

	};
	
	//Aqui dins va el codi de l'activitat
	this.run = function(){
		contextControl = canvasControl.getContext("2d");
		context.clearRect(0, 0, canvasWidth, canvasHeight);
		//context.strokeRect(gridAx,gridAy,w,h);
		
		if(DragData.active){
			if(frontImage=='none'){	
				frontImage=myImages.getFrontImage(DragData.startPosX, DragData.startPosY);
				if(frontImage!='notfound') frontImage.setDraggable();
			}
			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && 
					DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				primerClic = true;
				if (segonClic==true && myImages.images[frontImage.id].colocada==false){
					idSegon = frontImage.id;
					numPrimer = myImages.images[idPrimer].numPeca;
					numSegon = myImages.images[idSegon].numPeca;
					
					tercerClic=true;
				}
			}
		}else{
			if (DragData.currentPosX >= gridAx && DragData.currentPosX < gridAx+w && 
					DragData.currentPosY >= gridAy && DragData.currentPosY < gridAy+h){
				if(tercerClic==true){
					if(numPrimer == numSegon &&  idPrimer != idSegon){
						myImages.images[idPrimer].setHidden(true);
						myImages.images[idSegon].setHidden(true);
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
		if(colocades==(numPeca-1))
		{
			this.acabat=true;
			var im = new Image();
			im.src=fitxeracabar;
			context.drawImage(im,(canvasWidth/2)-64,(canvasHeight/2)-64,128,128);
		}else{
			//DRAW THE IMAGE
			if (!gradiente){
				grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
			}else{
				grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
			}
			grid.drawFonsJoc(colorfonsjoc, "0", margin);
			myImages.draw(colorFonsNoms);
			grid.draw(colorlinies);
			segons++;
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
			context.strokeRect(myImages.images[idPrimer].posx,myImages.images[idPrimer].posy,incrShowX,incrShowY);
			context.lineWidth = "1.0";
		}
		
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		delete(myImages);
		
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

