/**
 * ACTIVITAT PUZZLE
 */
function Puzzle(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	var myText = new Text();
	
	//Variables especifiques d'aquesta activitat
	var frontImage='none';
	var colocades=0;
	var acabat=false;
	var lines,cols;
	var w,h;
	var myImages = new ImageSet();
	var grid;
	var peces;
	
	
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
		myImage.onload = function() {
			imageLoaded = true;
		};
		myImage.src = activityData.imatge.src;

		lines=activityData.imatge.lines;
		cols=activityData.imatge.cols;
		w=activityData.imatge.w;
		h=activityData.imatge.h;
		
		peces = createPeca(context, myImage, lines,cols,{width:w,height:h},{x:100,y:100},600,100);

		grid = new Grid(context, lines,cols,{width:w,height:h},{x:600,y:100});

		for (var u=0;u<peces.length;u++){
			myImages.add(peces[u]);
		} 

	};
	
	//Aqui dins va el codi de l'activitat
	this.run = function() {
		context.clearRect(0, 0, canvasWidth, canvasHeight);

		//LLEGIR DADES USUARI
		if(DragData.active)
		{
			myText.renderText('DRAG FROM: '+DragData.startPosX+' '+DragData.startPosY, 24, 10,30);  
			
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
			if (DragData.currentPosX>=(frontImage.colocaciox)&&DragData.currentPosX<=(frontImage.colocaciox+frontImage.w)&&
					DragData.currentPosY>=(frontImage.colocacioy)&&DragData.currentPosY<=(frontImage.colocacioy+frontImage.h)){
				frontImage.posx=frontImage.colocaciox;
				frontImage.posy=frontImage.colocacioy;
				colocades++;
			}
				
			if(frontImage!='none'){
				if(frontImage!='notfound') frontImage.unsetDraggable();
				frontImage='none';
			}
		}		
		
		//COMPROVAR ESTAT ACTIVITAT
		if(colocades==lines*cols){
			acabat=true;
		}
		
		if(acabat){
			myText.renderText('FINALITZAT:', 24, 30,60);
		}
		
		
		//DRAW THE IMAGE
		myImages.draw();
		grid.draw();
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

