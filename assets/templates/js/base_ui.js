/**
 * @author Noelia Tuset
 */

function UserInterface()
{
	//Variables del canvas
	var context;
	var canvasWidth;
	var canvasHeight;
	var numActivitat;
	var i;
	
	//Imatges
	if (android) {
		var ctrlImages = [
			              { "src" : 'file:///android_asset/templates/images/orange_left.png', "posx" : 10, "posy" : 10, "w" : 60, "h" : 60},
			              { "src" : 'file:///android_asset/templates/images/orange_right.png', "posx" : 10, "posy" : 70, "w" : 60, "h" : 60},
			              { "src" : 'file:///android_asset/templates/images/orange_update.png', "posx" : 10, "posy" : 130, "w" : 60, "h" : 60},
			         ];		
	} else {
		var ctrlImages = [
			              { "src" : 'file:///android_asset/templates/images/orange_left.png', "posx" : 10, "posy" : 10, "w" : 80, "h" : 80},
			              { "src" : 'file:///android_asset/templates/images/orange_right.png', "posx" : 110, "posy" : 10, "w" : 80, "h" : 80},
			              { "src" : 'file:///android_asset/templates/images/orange_update.png', "posx" : 210, "posy" : 10, "w" : 80, "h" : 80},
			         ];		
	}

	var myimages = new ImageSet();
	var myimages2 = new ImageSet();
	var clicked;
	
	this.init = function(canvas,num) 
	{
		/** Inicialitzem el canvas de control  **/
		canvasWidth  = canvas.width;
		canvasHeight = canvas.height;
		canvasLeft = canvas.offsetLeft;
		canvasTop = canvas.offsetTop;
		context = canvas.getContext("2d");
		numActivitat = num;
		
		/** Inicialitzar les imatges  **/
		for (i=0;i<ctrlImages.length;i++){
			var img = new ImageData(i,context,ctrlImages[i].src);
			img.setPosition(ctrlImages[i].posx,ctrlImages[i].posy);
			img.setSize(ctrlImages[i].w,ctrlImages[i].h);
			myimages.add(img);
		}
	};
	
	this.draw = function(numCell,numActivitat,so) 
	{
		context.clearRect(0, 0, canvasWidth, canvasHeight);
		context.fillStyle = "orange";
		
		if(android) 
		{
			context.fillRect(0,0,80,375);
			context.fillStyle = "black";
			context.font = "9pt Arial";
			context.textAlign = "center";
			context.fillText("encerts", 40, 225);
			context.fillText("intents", 40, 275);
			context.fillText("temps", 40, 325);
			
		} else {
			
			context.fillRect(0,0,1024,100);
			
			var fontt = dadesActivitat.activitats[numActivitat].cell[numCell].atributs['style-font-family'];
			var x = 360+((800-360)/2);
			var y = canvasHeight/2;
			context.fillStyle = "black";
			context.font = "18pt " + fontt;
			context.textAlign = "center";
			
			var text = dadesActivitat.activitats[numActivitat].cell[numCell].atributs.p;
			var imsrc = dadesActivitat.activitats[numActivitat].cell[0].atributs.image;
			
			/** Quan el text es massa gran el tallem en dos files **/
			if (text)
			{
				var metric = context.measureText(text).width;
			   
				if(metric > 550){ 
					var name = text.substring(0,37);
			   		context.fillText(name,x, y-20);
			   		var name = text.substring(38,metric);
			   		context.fillText(name,x, y+20);
				}else{ 
				   	context.fillText(text,x, y);
				}
			}
			
			/** Si l'enunciat de l'activitat es una imatge **/
			if(imsrc){
				var img = new ImageData(i,context,imsrc);
				img.setPosition(x-150,y-40);
				img.setSize(300,80);
				myimages2.add(img);
				myimages2.draw(); 
			}
			
			context.font = "9pt Arial";
			context.fillText("encerts", 890, 30);
			context.fillText("intents", 940, 30);
			context.fillText("temps", 990, 30);
		}
		
		myimages.draw();  
	};
	
	this.checkClics =  function(){
		
		if(ControlData.active)
		{
			if(clicked=='none')
				clicked=myimages.getFrontImage(ControlData.startPosX-canvasLeft, ControlData.startPosY-canvasTop);

			/** Mirem si hem apretat algun boto **/
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

