/**
 * ACTIVITAT PANEL INFORMATION SCREEN
 */
function PanelInformation(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	//var myText = new Text();
	
	//Variables especifiques d'aquesta activitat
	this.frontImage='none';
	var colocades=0;
	this.acabat=false;
	var lines,cols;
	var w,h;
	var myImages = new ImageSet();
	var grid, gridFons;
	var peces = new Array();
	var dist;
	var x,y;
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
	var colorbaix, coloralt, grad, marg, imgw, imgh, cellWidth, cellHeight;
	var control = "0x";
	var intentos = 0;
	var segons = 0;
	var aciertos = 0;
	var myImage = new Image();
	var reprodSo, arxiuSo;
	var image;
	var img = new Image();
	
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
			w=myImage.width;
			h=myImage.height;
			
			if(h > canvasHeight-50){
				w = w - (h - (canvasHeight-50));
				h = canvasHeight-50;
			}else if (w > canvasWidth-50){
				h = h - (w - (canvasWidth-50));
				w = canvasWidth-50;
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
		
		if (imatgefons){
			img.onload = function(){
				imgw=img.width;
				imgh=img.height;	
			};
			
			img.src = imatgefons;
		}
		/*
		if (reprodSo == "PLAY_AUDIO")
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
	
	//Aqui dins va el codi de l'activitat
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
			/*if (!gradiente){
				grid.drawFons(background, 0, canvasWidth, canvasHeight, 0);
			}else{
				grid.drawFons(colorfonsalt, colorfonsbaix, canvasWidth, canvasHeight, gradiente);
			}*/
		}

		if (grad){
			if (colorfonsjoc!="") grid.drawFonsJoc(colorfonsjoc, "0", margin);
			if (colorlinies!="") grid.drawFonsJoc(colorlinies, "0", marg);
			if (coloralt!="") grid.drawFonsGrid(coloralt, colorbaix, w, h, h, gridAx, gridAy);
		}else{
			if (colorfonsjoc!="") grid.drawFonsJoc(colorfonsjoc, "0", margin);
			if (colorlinies!="") grid.drawFonsJoc(colorlinies, "0", 2);
		}
		
		image = document.getElementById('image');
		context.drawImage(image,gridAx,gridAy,w,h);
		
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

