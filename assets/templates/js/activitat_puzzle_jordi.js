/**
 * ACTIVITAT PUZZLE
 */
function PuzzleJordi(){
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	var myText = new Text();
	
	//Variables especifiques d'aquesta activitat
	var myimages = new ImageSet();
	var frontImage='none';
	
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
		var i;
		for (i=0;i<activityData.llistaImatges.length;i++){
			var img = new ImageData(i,context,activityData.llistaImatges[i].src);
			img.setPosition(activityData.llistaImatges[i].posx,activityData.llistaImatges[i].posy);
			img.setSize(activityData.llistaImatges[i].w,activityData.llistaImatges[i].h);
			myimages.add(img);
		}
	};
	
	//Aqui dins va el codi de l'activitat
	this.run = function() {
		//CLEAR SCREEN
		context.clearRect(0, 0, canvasWidth, canvasHeight);

		//DRAW THE IMAGE
		myimages.draw();
		
		if(DragData.active)
		{
			myText.renderText('DRAG FROM: '+DragData.startPosX+' '+DragData.startPosY, 24, 10,30);  
			
			//Choose the selected image and activate it
			if(frontImage=='none'){
				frontImage=myimages.getFrontImage(DragData.startPosX, DragData.startPosY);
				if(frontImage!='notfound') frontImage.setDraggable();
			}
			//Move the dragged image around
			if(frontImage!='notfound'){
				myText.renderText('IMAGE: '+frontImage.id, 24, 10,60);  
				frontImage.drag(DragData.startPosX-DragData.currentPosX, DragData.startPosY-DragData.currentPosY);
			}
			
		//Disable the current active image
		}else if(frontImage!='none'){
			if(frontImage!='notfound') frontImage.unsetDraggable();
			frontImage='none';
		}
	};
	
	//Aquest funcio s'ha de cridar un cop s'ha acabat l'activitat i es canvia a una altra
	this.end = function() {
		//Aqui hauriem d'alliberar la memoria de les imatges (si es pot)
		return;
	};
}

  

