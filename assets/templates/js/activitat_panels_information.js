/**
 * ACTIVITAT PANEL INFORMATION SCREEN
 * @author Noelia Tuset
 */
function PanelInformation()
{
	var context;
	var canvasWidth;
	var canvasHeight;
	
	/** Variables especifiques d'aquesta activitat **/
	this.frontImage = 'none';
	this.acabat = false;
	this.idPrimer = 'none';
	this.idSegon = 'none';
	
	var myImages = new ImageSet();
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
	var w = 0;
	var h = 0;
	var gridAx = 0;
	var gridAy = 0;
	var lines, cols, x, y, imgw, imgh, grid, showW, showH, reprodSo, arxiuSo, gridFons, imatgefons;
	var colorlinies, colorfonsjoc, colorfonsalt, colorfonsbaix, gradiente, background, colorbaix, coloralt, grad, marg, image;
	
	/**
	 * Funcio per a inicialitzar l'activitat a partir de les seves dades
	 */
	this.init = function(canvas, activityData)
	{
		/** Inicialitzar el canvas **/
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		context = canvas.getContext("2d");
		context.canvas.style.cursor = "pointer";

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
			
		lines=activityData.celllist[0].atributs.rows;
		cols=activityData.celllist[0].atributs.cols;
		
		gridFons = new Grid(context, 1, 1, {width:canvasWidth,height:canvasHeight}, {x:0,y:0}, {x:0,y:0});
		
		myImage.onload = function() {
			imageLoaded = true;
			ww=myImage.width;
			hh=myImage.height;
			w = ww;
			h = hh;
			
			if(hh > canvasHeight-50){
				w = ww - (hh - (canvasHeight-50));
				h = canvasHeight-50;
				if(android) w = ww;
			}else if (ww > canvasWidth-50){
				h = hh - (ww - (canvasWidth-50));
				w = canvasWidth-50;
				if(android) h = hh;
			}

			gridAx=(canvasWidth-w)/2;
			gridAy=(canvasHeight-h)/2;
		};
		
		var rutaImatgeSenseCela = activityData.celllist[0].atributs.image;
		
		if (rutaImatgeSenseCela){
			myImage.src = rutaImatgeSenseCela;
			document.getElementById('image').src = rutaImatgeSenseCela;
		}else{
			var rutaImatgeCela = activityData.celllist[0].cell[0].atributs.image;
			myImage.src = rutaImatgeCela;
			document.getElementById('image').src = rutaImatgeCela;	
		}
		
		/** Carrega imatge del fons del joc **/
		if (imatgefons){
			img.onload = function(){
				imgw=img.width;
				imgh=img.height;	
			};
			img.src = imatgefons;
		}
		
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
			if (colorfonsalt){
				gridFons.drawFonsGrid(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, canvasHeight, 0, 0);
			}
			if (!gradiente){
				grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
			}else{
				grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
			}
		}

		if (grad){
			if (colorfonsjoc) grid.drawFonsJoc(colorfonsjoc, "0", margin);
			if (colorlinies) grid.drawFonsJoc(colorlinies, "0", marg);
			if (coloralt) grid.drawFonsGrid(coloralt, colorbaix, w, h, grad, gridAx, gridAy);
		}else{
			if (colorfonsjoc) grid.drawFonsJoc(colorfonsjoc, "0", margin);
			if (colorlinies) grid.drawFonsJoc(colorlinies, "0", 2);
		}
		
		image = document.getElementById('image');
		contextControl.fillStyle = "black";
		contextControl.font = "14pt Arial";
		contextControl.textAlign = "center";
		tiempo = segons/20;
		tiempo = arrodonir(tiempo,0);
		
		if (android){
			contextControl.fillText(aciertos, 40, 240);
			contextControl.fillText(intentos, 40, 290);
			contextControl.fillText(tiempo, 40, 340);
			context.drawImage(image,gridAx,gridAy,w,h);
		}else{
			contextControl.fillText(aciertos, 890, 50);
			contextControl.fillText(intentos, 940, 50);
			contextControl.fillText(tiempo, 990, 50);
			context.drawImage(image,gridAx,gridAy,w,h);
		}
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		delete(grid);
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

