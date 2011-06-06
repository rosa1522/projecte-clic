/**
 * ACTIVITAT PANEL INFORMATION SCREEN
 * @author Noelia Tuset
 */
function PanelIdentify()
{
	var context;
	var canvasWidth;
	var canvasHeight;
	
	/** Variables especifiques d'aquesta activitat **/
	this.frontImage = 'none';
	this.acabat = false;
	this.idPrimer = 'none';
	this.idSegon = 'none';
	
	var x = new Array();
	var y = new Array();
	var myImages = new ImageSetMemory();
	var peces = new Array();
	var ordArray = new Array();
	var myImage = new Image();
	var img = new Image();
	var primerClic = false;
	var segonClic = false;
	var tercerClic = false;
	var control = "0x";
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var colocades = 0;
	var lines, cols, w, h, x, y, incrShowX, incrShowY, grid, showW, showH, gridAx, gridAy, reprodSo, arxiuSo;
	var colorFonsNoms, colorlinies, colorfonsjoc, colorfonsalt, colorfonsbaix, gradiente, background, colorbaix, coloralt, grad, marg, image;
	
	/**
	 * Funcio per a inicialitzar l'activitat a partir de les seves dades
	 */
	this.init = function(canvas, activityData)
	{
		/** Inicialitzar el canvas **/
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		context = canvas.getContext("2d");

		/** Agafem les dades del fitxer data.js **/
		colorfonsbaix = activityData.atributsActivitat['settings-container-gradient-dest'];
		if (colorfonsbaix) colorfonsbaix = "#"+colorfonsbaix.replace(control,"");
		
		colorfonsalt = activityData.atributsActivitat['settings-container-gradient-source'];
		if (colorfonsalt) colorfonsalt = "#"+colorfonsalt.replace(control,"");
		
		imatgefons = activityData.atributsActivitat['settings-container-image-name'];

		gradiente = activityData.atributsActivitat['settings-container-gradient-angle'];
		
		margin = activityData.atributsActivitat['settings-margin'];
		
		background = activityData.atributsActivitat['settings-container-bgColor'];
		if (!background) background="#FFFFFF"; 
		background = "#"+background.replace(control,"");
		
		colorfonsjoc = activityData.atributsActivitat['settings-window-bgColor'];
		if (!colorfonsjoc) colorfonsjoc = "0xFFFFFF";
		colorfonsjoc = "#"+colorfonsjoc.replace(control,"");
		
		reprodSo = activityData.cell[0].atributs['media-type'];
		
		arxiuSo = activityData.cell[0].atributs['media-file'];
		
		colorlinies = activityData.celllist[0].atributs['style-color-border'];
		if (!colorlinies) colorlinies = "0x111111";
		colorlinies = "#"+colorlinies.replace(control,"");
		
		colorbaix = activityData.celllist[0].atributs['style-gradient-dest'];
		if (colorbaix) colorbaix = "#"+colorbaix.replace(control,"");
		
		coloralt = activityData.celllist[0].atributs['style-gradient-source'];
		if (coloralt) coloralt = "#"+coloralt.replace(control,"");

		grad = activityData.celllist[0].atributs['style-gradient-angle'];
		
		marg = activityData.celllist[0].atributs['style-borderStroke'];
		
		colorFonsNoms = "FFFFFF";
			
		lines=activityData.celllist[0].atributs.rows;
		cols=activityData.celllist[0].atributs.cols;

		//gridFons = new Grid(context, 1, 1, {width:canvasWidth,height:canvasHeight}, {x:0,y:0}, {x:0,y:0});
		
		/** ocupació **/
		w = arrodonir (activityData.celllist[0].atributs.cellWidth,0);
		h = arrodonir (activityData.celllist[0].atributs.cellHeight,0);
		
		if((h*lines) > (canvasHeight-24)){
			w = w - (h - (canvasHeight-24));
			h = canvasHeight-24;
		}else if (w*cols > canvasWidth-24){
			h = h - (w - (canvasWidth-24));
			w = canvasWidth-24;
		}
		incrShowX = w;
		incrShowY = h;
		
		h = h*lines;
		w = w*cols;
		
		gridAx=(canvasWidth-w)/2;
		gridAy=(canvasHeight-h)/2;
		/****/
		
		//grid = new Grid(context, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});
		
		var id_img=0;
		var pecesPrimer = new Array();
		var quantitat = activityData.celllist[0].cell.length; //noms
		
		for(var i=0; i<quantitat; i++){
			var hihaText = activityData.celllist[0].cell[i].atributs.p;
			if(hihaText){
				var novaPeca = new ImageAssociation(context, id_img, hihaText,incrShowX,incrShowY);
				novaPeca.setColor(colorFonsNoms);
				pecesPrimer.push(novaPeca);
			}else{
				var myImage = new Image();
				
				myImage.onload = function() {
					imageLoaded = true;
				};
	
				myImage.src = activityData.celllist[0].cell[i].atributs.image;
				var novaPecaImatges = new ImageMemory(context, id_img, myImage,incrShowX,incrShowY);
				pecesPrimer.push(novaPecaImatges);
			}
			id_img++;
		}
		
		theX = gridAx;
		theY = gridAy;
		o=0;
		if (lines > 1){
			for(var i=0; i<lines; i++){ 
	        	x[o]=theX;
	        	y[o]=theY;
	        	theY += incrShowY;
	            o++;
		    }
		}else{
			for(var i=0; i<cols; i++){ 
	        	x[o]=theX;
	        	y[o]=theY;
	        	theX += incrShowX;
	            o++;
		    }
		}
		
		for (var o=0;o<pecesPrimer.length;o++){
			pecesPrimer[o].setPosx(x[o]);
			pecesPrimer[o].setPosy(y[o]);
		}
		
		for (var o=0;o<pecesPrimer.length;o++){
			pecesPrimer[o].setHidden(false);
			myImages.add(pecesPrimer[o]);
		}		

		/*
		var rutaImatgeSenseCela = activityData.celllist[0].atributs.image;
		
		if (rutaImatgeSenseCela){
			myImage.src = rutaImatgeSenseCela;
			document.getElementById('image').src = rutaImatgeSenseCela;
		}else{
			var rutaImatgeCela = activityData.celllist[0].cell[1].atributs.image;
			myImage.src = rutaImatgeCela;
			document.getElementById('image').src = rutaImatgeCela;	
		}
		
		// Carrega imatge del fons del joc
		if (imatgefons){
			img.onload = function(){
				imgw=img.width;
				imgh=img.height;	
			};
			img.src = imatgefons;
		}
		*/
		/** Reprodueix el so de l'activitat **/
		/*if (reprodSo == "PLAY_AUDIO")
		{
			soundManager.url = "./sound/swf/";
			soundManager.flashVersion = 9;
			soundManager.useFlashBlock = false;
			
			soundManager.onready(function() {
				soundManager.createSound(arxiuSo,arxiuSo);
				soundManager.play(arxiuSo);
			});
		}*/
	};
	
	this.run = function(canvasControl) 
	{
		contextControl = canvasControl.getContext("2d");
		context.clearRect(0, 0, canvasWidth, canvasHeight);
		segons++;
	
		grid = new Grid(context, lines, cols, {width:w,height:h}, {x:gridAx,y:gridAy}, {x:gridAx,y:gridAy});

		if (imatgefons){
			for (var i=0;i<8;i++){
				for (var j=0;j<8;j++){
					context.drawImage(img,j*imgw,i*imgh,imgw,imgh);
				}
			}
		}else{
			if (colorfonsalt) gridFons.drawFonsGrid(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, canvasHeight, 0, 0);
			if (!gradiente){
				grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
			}else{
				grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
			}
		}
/*	
		if (grad){
			//if (colorfonsjoc!="") grid.drawFonsJoc(colorfonsjoc, "0", margin);
			if (colorlinies!="") grid.drawFonsJoc(colorlinies, "0", marg);
			//if (coloralt!="") grid.drawFonsGrid(coloralt, colorbaix, w, h, h, gridAx, gridAy);
		}else{
			//if (colorfonsjoc!="") grid.drawFonsJoc(colorfonsjoc, "0", margin);
			if (colorlinies!="") grid.drawFonsJoc(colorlinies, "0", 2);
		}
		*/

		myImages.draw(colorFonsNoms);
		
		contextControl.fillStyle = "black";
		contextControl.font = "14pt Arial";
		tiempo = segons/20;
		tiempo = arrodonir(tiempo,0);
		
		/*
		if (android){
			contextControl.fillText(aciertos, 35, 250);
			contextControl.fillText(intentos, 35, 300);
			contextControl.fillText(tiempo, 30, 350);
			context.drawImage(image,gridAx,gridAy,w,h);
		}else{
			contextControl.fillText(aciertos, 890, 60);
			contextControl.fillText(intentos, 940, 60);
			contextControl.fillText(tiempo, 990, 60);
			context.drawImage(image,gridAx,gridAy,w,h);
		}*/
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

