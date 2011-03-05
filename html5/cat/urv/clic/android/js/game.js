var imagelist = [
     { "src" : './images/grass.jpg', "posx" : 200, "posy" : 200, "w" : 128, "h" : 128},
     { "src" : './images/roof.jpg', "posx" : 300, "posy" : 300, "w" : 128, "h" : 128},
     { "src" : './images/brick.png', "posx" : 400, "posy" : 400, "w" : 128, "h" : 128}
];


////////////////////////////////////////////////
var canvas = $("#canvas");
canvasWidth  = canvas.width();
canvasHeight = canvas.height();

var context = canvas[0].getContext("2d");

Text.context = context;
Text.face = vector_battle;

var myimages = new ImageSet();
  
//LOAD THE IMAGES FROM THE DATA
var i;
for (i=0;i<imagelist.length;i++){
	var img = new ImageData(i,context,imagelist[i].src);
	img.setPosition(imagelist[i].posx,imagelist[i].posy);
	img.setSize(imagelist[i].w,imagelist[i].h);
	myimages.add(img);
}

var frontImage='none';
  
// MAIN LOOP FUNCTION
var mainLoop = function () {
	  
	//CLEAR SCREEN
	context.clearRect(0, 0, canvasWidth, canvasHeight);

	//DRAW THE IMAGE
	myimages.draw();
	
	if(DragData.active)
	{
		Text.renderText('DRAG FROM: '+DragData.startPosX+' '+DragData.startPosY, 24, 10,30);  
		
		//Choose the selected image and activate it
		if(frontImage=='none'){
			frontImage=myimages.getFrontImage(DragData.startPosX, DragData.startPosY);
			if(frontImage!='notfound') frontImage.setDraggable();
		}
		//Move the dragged image around
		if(frontImage!='notfound'){
			Text.renderText('IMAGE: '+frontImage.id, 24, 10,60);  
			frontImage.drag(DragData.startPosX-DragData.currentPosX, DragData.startPosY-DragData.currentPosY);
		}
		
	//Disable the current active image
	}else if(frontImage!='none'){
		if(frontImage!='notfound') frontImage.unsetDraggable();
		frontImage='none';
	}
};

// START MAINLOOP
var mainLoopId = setInterval(mainLoop, 25);

