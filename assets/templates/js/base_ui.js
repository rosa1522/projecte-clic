function UserInterface()
{
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	var textControl = new Text();
	var textInicial = new Text();
	var numActivitat;
	
	//Imatges
	//var ctrlImages = [
	//              { "src" : '_left.png', "posx" : 10, "posy" : 10, "w" : 80, "h" : 80},
	//              { "src" : '_right.png', "posx" : 110, "posy" : 10, "w" : 80, "h" : 80},
	//              { "src" : '_update.png', "posx" : 210, "posy" : 10, "w" : 80, "h" : 80},
	//         ];
	var ctrlImages = [
		              { "src" : 'file:///android_asset/templates/images/blue_left.png', "posx" : 10, "posy" : 10, "w" : 60, "h" : 60},
		              { "src" : 'file:///android_asset/templates/images/blue_right.png', "posx" : 10, "posy" : 90, "w" : 60, "h" : 60},
		              { "src" : 'file:///android_asset/templates/images/blue_update.png', "posx" : 10, "posy" : 170, "w" : 60, "h" : 60},
		       ];
	var myimages = new ImageSet();
	var clicked; 
	
	var control = "@";
	var control2 = ".xml";
	var color;
	
	this.init = function(canvas,num) {
		////////////////////////////////////////////////
		//Inicialitzem el canvas de control
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		canvasLeft = canvas.offsetLeft;
		canvasTop = canvas.offsetTop;
		context = canvas.getContext("2d");
		numActivitat = num;
		
		textControl.context = context;
		textControl.face = vector_battle;
		
		//color = dadesActivitat.activitats[num].atributsActivitat['settings-skin-file'];
		//color = color.replace(control,"");
		//color = color.replace(control2,"");
		
		//Inicialitzar les imatges
		var i;
		for (i=0;i<ctrlImages.length;i++){
			//var img = new ImageData(i,context,"../images/"+color+ctrlImages[i].src);
			var img = new ImageData(i,context,ctrlImages[i].src);
			img.setPosition(ctrlImages[i].posx,ctrlImages[i].posy);
			img.setSize(ctrlImages[i].w,ctrlImages[i].h);
			myimages.add(img);
		}
	};
	
	this.draw = function(numCell,numActivitat,so) {
		//CLEAR SCREEN
		context.clearRect(0, 0, canvasWidth, canvasHeight);

		//context.fillStyle = color;
		//context.fillRect(0,0,1024,100);
		 
		//DRAW THE IMAGES
		myimages.draw();
		
		//var fontt = dadesActivitat.activitats[numActivitat].cell[numCell].atributs['style-font-family'];
		//var pxfont = dadesActivitat.activitats[numActivitat].cell[numCell].atributs['style-font-size'];
		
		var x = 360+((800-360)/2);
		var y = canvasHeight/2;
		context.fillStyle = "black";
		//context.font = "22pt " + fontt;
		context.font = "22pt Arial";
		context.textAlign = "center";
		context.textBaseline = "middle";
		//context.fillText(dadesActivitat.activitats[numActivitat].cell[numCell].atributs.p, x, y);
		context.fillText("Prova", 0, 0);
		
		//context.font = "9pt Arial";
		//context.fillText("aciertos   intentos   tiempo", 940, 30);
		

		
		//context.fillText(dadesActivitat.activitats[numActivitat].cell[numCell].atributs.p, 24, 180,30);
		if(ControlData.active)
			textControl.renderText('TOUCH POS: '+(ControlData.startPosX-canvasLeft)+' '+(ControlData.startPosY-canvasTop)+' '+ControlData.active, 24, 10,30);
	};
	
	this.checkClics =  function(){
		
		if(ControlData.active)
		{
			if(clicked=='none')
				clicked=myimages.getFrontImage(ControlData.startPosX-canvasLeft, ControlData.startPosY-canvasTop);

			//Mirem si hem apretat algun boto
			if(clicked!='notfound' && clicked!='none') {
				if( clicked.id == 'image0'){ControlData.active=false; return "previous";}
				if( clicked.id == 'image1'){ControlData.active=false; return "next";}
				if( clicked.id == 'image2'){ControlData.active=false; return "update";}
			}	
		} else {
			clicked='none';
		}
		return "none";
	};
}

