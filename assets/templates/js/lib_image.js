function randOrd(){
	return (Math.round(Math.random())-0.5); 
}

function adaptarGrid(canvasWidth,canvasHeight,w,h,lines,cols){
	var xy = new Array();
	var aux, incrShowX, incrShowY;
	var passat = false;
	
	if(android){
		incrShowY = (canvasHeight-24)/lines; 
		incrShowX = (canvasWidth-24)/cols;
	}else{
		if((h*lines) > (canvasHeight-24)){
			incrShowY = (canvasHeight-24)/lines; 
			aux = h - incrShowY;
			incrShowX = w - aux;
			passat = true;
		}
		if(passat){
			h=incrShowY;
			w=incrShowX;
		}
		if((w*cols) > (canvasWidth-24)){
			incrShowX = (canvasWidth-24)/cols;
			aux = w - incrShowX;
			incrShowY = h - aux;
			passat = true;
		}
		if(!passat){
			incrShowY=h;
			incrShowX=w;
		}
	}
	
	xy.push(incrShowX);
	xy.push(incrShowY);
	
	return xy;
}

function arrodonir(quantitat, decimals) {
	var quantitat = parseFloat(quantitat);
	var decimals = parseFloat(decimals);
	decimals = (!decimals ? decimals : decimals);
	return Math.round(quantitat * Math.pow(10, decimals)) / Math.pow(10, decimals);
}

function Peca(ctxt,id,img,posx,posy,w,h,insidePointx,insidePointy,insideSizew,insideSizeh,colocaciox,colocacioy,showH,showW)
{
   this.ctxt = ctxt;
   this.id = id;
   this.img = img;
   this.posx = posx;
   this.posy = posy;
   this.w = w;
   this.h = h;
   this.insidePointx = insidePointx;
   this.insidePointy = insidePointy;
   this.insideSizew = insideSizew;
   this.insideSizeh = insideSizeh;
   this.colocaciox = colocaciox;
   this.colocacioy = colocacioy;
   this.depth = 0;
   this.llocPeca;
   this.iniposx=posx;
   this.iniposy=posy;
   this.colocada=false;
   this.showH=showH;
   this.showW=showW;
   this.numPeca;

   this.setPosx = function(x){
	   this.posx = x;
	   this.iniposx = x;
   };
   
   this.getPosx = function(){
	   return this.posx;
   };
   
   this.setPosy = function(y){
	   this.posy = y;
	   this.iniposy = y;
   };
   
   this.getPosy = function(){
	   return this.posy;
   };
   
   this.setNumPeca = function(numPeca){
	   this.numPeca = numPeca;
   };
   
   //Renders the image in the screen
   this.draw2 = function() {
	   this.ctxt.drawImage(this.img,this.posx,this.posy,this.showW,this.showH);
	};
	
	this.draw = function() {
		this.ctxt.drawImage(this.img,this.insidePointx,this.insidePointy,this.insideSizew,this.insideSizeh,this.posx,this.posy,this.showW,this.showH);
	};
	
	//Checks if the point x,y is inside the image
	this.isInside = function(x,y){
		if (x>=this.posx && x<=(this.posx+this.w) 
				&& y>=this.posy && y<=(this.posy+this.h)) return true;
		return false;
	};

	//Activates the image as a draggable element
	this.setDraggable = function(){
		//Save some data
		this.previousDepth= this.depth;
		this.depth=20;
		this.startX = this.posx;
		this.startY = this.posy;
		//this.colocaciox = posx;
		//this.colocacioy = posy;
	};
	
	//Sets the depth of the image
	//Depth must be an integer value between 0 (back) and 20 (front)
	this.setDepth = function(depth){
		this.depth = depth;
	};
	
	//Deactivates the image as a draggable element
	this.unsetDraggable = function(){
		this.depth= this.previousDepth;
	};
	
	//Drags the image according to a relative increment
	this.drag = function(incX,incY){
		this.posx =  this.startX-incX;
		///this.colocaciox = posx;
		this.posy =  this.startY-incY;
		//this.colocacioy = posy;
	};

}

function ImageAssociation(ctxt,id,nom,showW,showH)
{
   this.ctxt = ctxt;
   this.id = id;
   this.nom = nom;
   this.posx;
   this.posy;
   this.depth = 0;
   this.showH=showH;
   this.showW=showW;
   this.colocada=false;
   this.hidden=false;
   this.numPeca;
   this.llocPeca;
   this.color;
   
   this.setPosx = function(x){
	   this.posx = x;
   };
   
   this.setPosy = function(y){
	   this.posy = y;
   };
   
   this.setNumPeca = function(numPeca){
	   this.numPeca = numPeca;
   };
   
   this.setColocada = function(colocada){
	   this.colocada = colocada;
   };
   
   this.setColor = function(color){
	   this.color = color;
   };
   
   //A l'invers per poder utilitzar el memoryImage
   this.draw = function(colorFonsNoms) {
	   var line, dibline, aa;
	   if (!this.hidden){
		   this.ctxt.fillStyle = this.color;
		   this.ctxt.fillRect(this.posx, this.posy, this.showW, this.showH);   
		   this.ctxt.fillStyle = "black";
		   if (android){ 
			   this.ctxt.font = "7pt Arial";
		   }else{ 
			   this.ctxt.font = "11pt Arial"; 
		   }
		   this.ctxt.font = "11pt Arial";
		   this.ctxt.textAlign = "center";
		   this.ctxt.textBaseline = "middle";

		   ////////////////////////////////////////////////////
		   var metric = this.ctxt.measureText(this.nom).width;
		   
		   if(metric > this.showW){ 
			   	aa = arrodonir(metric/this.showW,0);
			   	line = aa;
		   		var measure = (this.nom.length/aa);
		   		var o = (this.showH/aa)/2;
		   		var incH = (this.showH/aa);
		   }else{ 
			   line = 1; 
			   aa = 2;
			   var measure = this.nom.length;
			   var incH = (this.showH/aa);
			   var o = incH;
		   }

		   var inc = measure;
		   var name = this.nom.substring(0,measure);

		   for (var i=1; i<=line; i++){
			   image = this.ctxt.fillText(name,this.posx+(this.showW/2), this.posy+o);
			   name = this.nom.substring(measure,measure+inc);
			   measure+=measure;
			   o+=incH;
		   }
	   }else{
		   this.ctxt.fillStyle = this.color;
		   this.ctxt.fillRect(this.posx,this.posy,this.showW-0.01,this.showH-0.01);
	   }
   };
	
   this.setHidden = function(hidden){
	   this.hidden = hidden;
   };
   
	//Checks if the point x,y is inside the image
	this.isInside = function(x,y){
		if (x>=this.posx && x<=(this.posx+this.showW) 
				&& y>=this.posy && y<=(this.posy+this.showH)) return true;
		return false;
	};

	//Activates the image as a draggable element
	this.setDraggable = function(){
		//Save some data
		this.previousDepth= this.depth;
		this.depth=20;
		this.startX = this.posx;
		this.startY = this.posy;
	};
	
	//Sets the depth of the image
	//Depth must be an integer value between 0 (back) and 20 (front)
	this.setDepth = function(depth){
		this.depth = depth;
	};
	
	//Deactivates the image as a draggable element
	this.unsetDraggable = function(){
		this.depth= this.previousDepth;
	};
	
	//Drags the image according to a relative increment
	this.drag = function(incX,incY){
		this.posx =  this.startX-incX;
		this.posy =  this.startY-incY;
	};
}

function ImageMemory(ctxt,id,img,showW,showH)
{
   this.ctxt = ctxt;
   this.id = id;
   this.img = img;
   this.posx;
   this.posy;
   this.depth = 0;
   this.showH=showH;
   this.showW=showW;
   this.colocada=false;
   this.numPeca;
   this.hidden=true;
   this.llocPeca;
   
   this.setPosx = function(x){
	   this.posx = x;
   };
   
   this.setPosy = function(y){
	   this.posy = y;
   };
   
   this.setNumPeca = function(numPeca){
	   this.numPeca = numPeca;
   };
   
   this.setColocada = function(colocada){
	   this.colocada = colocada;
   };
   
   this.setHidden = function(hidden){
	   this.hidden = hidden;
   };
   
   //Renders the image in the screen
   this.draw = function(colorhidden) {
	   if (!this.hidden){
		   this.ctxt.drawImage(this.img,this.posx,this.posy,this.showW,this.showH);
	   }else{
		   this.ctxt.fillStyle = colorhidden;
		   this.ctxt.fillRect(this.posx,this.posy,this.showW-0.01,this.showH-0.01);
	   }
	};
	
	//Checks if the point x,y is inside the image
	this.isInside = function(x,y){
		if (x>=this.posx && x<=(this.posx+this.showW) 
				&& y>=this.posy && y<=(this.posy+this.showH)) return true;
		return false;
	};

	//Activates the image as a draggable element
	this.setDraggable = function(){
		//Save some data
		this.previousDepth= this.depth;
		this.depth=20;
		this.startX = this.posx;
		this.startY = this.posy;
	};
	
	//Sets the depth of the image
	//Depth must be an integer value between 0 (back) and 20 (front)
	this.setDepth = function(depth){
		this.depth = depth;
	};
	
	//Deactivates the image as a draggable element
	this.unsetDraggable = function(){
		this.depth= this.previousDepth;
	};
	
	//Drags the image according to a relative increment
	this.drag = function(incX,incY){
		this.posx =  this.startX-incX;
		this.posy =  this.startY-incY;
	};
}

function createPeca(ctxt,myImage,lines,cols,imageSize,basePosition,imageColocation,imageShow)
{
    var incrY = imageSize.height/lines;
    var incrX = imageSize.width/cols;
    var incrShowY = imageShow.h/lines;
    var incrShowX = imageShow.w/cols;
    var theX = basePosition.x;
    var theY = basePosition.y;
    var peces = new Array();
    var id_img=0;
    for(var i=0;i<lines;i++) { 
        for(var j=0;j<cols;j++) { 
        	peces.push(new Peca(ctxt, id_img, myImage,theX,theY,incrShowX,incrShowY,(j*incrX),(i*incrY),incrX,incrY,imageColocation.x+(j*incrShowX),imageColocation.y+(i*incrShowY),incrShowY,incrShowX));
        	theX += incrShowX;
            id_img++;
        }
        theX = basePosition.x;
        theY +=  incrShowY;
    }
    return peces;
}

function Grid(ctxt,lines,cols,imageSize,basePosition,iniPosition)
{	
	this.ctxt = ctxt;
	this.lines = lines;
	this.cols = cols;
	this.imageW = imageSize.width;
	this.imageH = imageSize.height;
    this.incrY2 = imageSize.height/lines;//220/3 = 73.333
    this.incrX2 = imageSize.width/cols;//220/3 = 73.333
    this.incrY=arrodonir(this.incrY2,4);	
    this.incrX=arrodonir(this.incrX2,4);
    this.theX = basePosition.x;//518
    this.theY = basePosition.y;//185
    this.iniX = iniPosition.x;
    this.iniY = iniPosition.y;
    this.ww;
    this.hh;
	
	/** Renders the board in the screen **/
	this.draw = function(colorlinies) {
		this.ctxt.beginPath(0,0,0,0);

		var x = this.theX;
		for (var y = 0; y < (this.cols+1); y++) {
			this.ctxt.moveTo(x, this.theY);
			this.ctxt.lineTo(x, this.theY+this.imageH);
			x+=this.incrX;
		}
		
		var y = this.theY;
		for (var x = 0; x < (this.lines+1); x++ ) {
			this.ctxt.moveTo(this.theX, y);
			this.ctxt.lineTo(this.theX+this.imageW, y);
			y+=this.incrY;
		} 
		
		this.ctxt.strokeStyle = colorlinies;
		this.ctxt.stroke();	
	};
	
	this.drawFonsInactiu = function(colorinactiu) {
		 this.ctxt.fillStyle = colorinactiu;
		 this.ctxt.fillRect(this.theX,this.theY,this.imageW,this.imageH);
	};
	
	this.drawFons = function(colorfonsalt,colorfonsbaix,canvasWidth,canvasHeight,gradiente){
		var my_gradient = this.ctxt.createLinearGradient(0, 0, 0, gradiente);
		if (gradiente == "0"){
			my_gradient=colorfonsalt;
		}else{
			my_gradient.addColorStop(1, colorfonsalt);
			my_gradient.addColorStop(0, colorfonsbaix);
		}
		this.ctxt.fillStyle = my_gradient;
		this.ctxt.fillRect(0, 0, canvasWidth, canvasHeight);
	};
	
	this.drawFonsGrid = function(colorfonsalt,colorfonsbaix,canvasWidth,canvasHeight,gradiente,x,y){
		var my_gradient = this.ctxt.createLinearGradient(y, x, gradiente, 0);
		my_gradient.addColorStop(1, colorfonsalt);
		my_gradient.addColorStop(0, colorfonsbaix);
		this.ctxt.fillStyle = my_gradient;
		this.ctxt.fillRect(x, y, canvasWidth, canvasHeight);
	};
	
	this.drawFonsJoc = function(colorfonsjoc,dist,margin) {
		 this.ctxt.fillStyle = colorfonsjoc;
		 
		 if (dist == "AB"){
			 this.ww = (this.imageW*2)+(margin*2)+12;
			 this.hh = this.imageH+(margin*2);
			 this.ctxt.fillRect(this.iniX-margin,this.iniY-margin,this.ww,this.hh);
		 }else if(dist == "BA"){
			 this.ww = (this.imageW*2)+(margin*2)+12;
			 this.hh = this.imageH+(margin*2);
			 this.ctxt.fillRect(this.theX-margin,this.theY-margin,this.ww,this.hh);
		 }else if(dist == "0"){
			 this.ww = this.imageW+(margin*2);
			 this.hh = this.imageH+(margin*2);
			 this.ctxt.fillRect(this.iniX-margin,this.iniY-margin,this.ww,this.hh);
		 }else if(dist == "BUA"){
			 this.ww = this.imageW+(margin*2);
			 this.hh = (this.imageH*2)+(margin*2)+12;
			 this.ctxt.fillRect(this.theX-margin,this.theY-margin,this.ww,this.hh);
		 }else{
			 this.ww = this.imageW+(margin*2);
			 this.hh = (this.imageH*2)+(margin*2)+12;
			 this.ctxt.fillRect(this.iniX-margin,this.iniY-margin,this.ww,this.hh);
		 }

	};
}

function ImageData(id,context,src)
{
	this.context=context;
	this.id = 'image'+id;
	this.posX = 0;
	this.posY = 0;
	this.depth = 0;

	this.img = new Image();
	this.img.src = src;
	
	//Sets the x,y position of the image
	this.setPosition = function(posX,posY){
		this.posX = posX;
		this.posY = posY;
	};
	
	//Sets the dimensions of the image
	this.setSize = function(width,height){
		this.img.width = width;
		this.img.height = height;	
	};
	
	//Sets the depth of the image
	//Depth must be an integer value between 0 (back) and 20 (front)
	this.setDepth = function(depth){
		this.depth = depth;
	};
	
	//Renders the image in the screen
	this.draw = function(){
		this.context.drawImage(this.img, this.posX, this.posY, this.img.width, this.img.height);
	};
	
	//Checks if the point x,y is inside the image
	this.isInside = function(x,y){
		if (x>=this.posX && x<=(this.posX+this.img.width) 
				&& y>=this.posY && y<=(this.posY+this.img.height)) return true;
		return false;
	};
	
	//Activates the image as a draggable element
	this.setDraggable = function(){
		//Save some data
		this.previousDepth= this.depth;
		this.depth=20;
		this.startX = this.posX;
		this.startY = this.posY;
	};
	
	
	//Deactivates the image as a draggable element
	this.unsetDraggable = function(){
		this.depth= this.previousDepth;
	};
	
	//Drags the image according to a relative increment
	this.drag = function(incX,incY){
		this.posX =  this.startX-incX;
		this.posY =  this.startY-incY;
	};
} 

function ImageSet()
{
	this.images = new Array(120);
	this.num_images = 0;
	
	//Adds an image to the collection
	this.add = function(image){
		this.images[this.num_images]=image;
		this.num_images++;
	};
	
	// Draws all the images (maintaining the order)
	this.draw = function(){
		var i,depth;
		for(depth=0;depth<21;depth++){
			for (i=0;i<this.num_images;i++){
				if(this.images[i].depth==depth){
					this.images[i].draw();
				}
			}
		}
	};
	
	//Returns the most frontal image at position x,y
	this.getFrontImage = function(x, y){
		var img = 'notfound';
		var currentdepth = 0;
	 	
		for (i=0;i<this.num_images;i++){
			if(this.images[i].isInside(x,y) && 
			   this.images[i].depth>=currentdepth) {
				img = this.images[i];
				currentdepth = this.images[i].depth;
			}
		}	
		return img;
	};
}

function ImageSetMemory()
{
	this.images = new Array(20);
	this.num_images = 0;
	
	//Adds an image to the collection
	this.add = function(image){
		this.images[this.num_images]=image;
		this.num_images++;
	};
	
	// Draws all the images (maintaining the order)
	this.draw = function(colorhidden){
		var i,depth;
		for(depth=0;depth<21;depth++){
			for (i=0;i<this.num_images;i++){
				if(this.images[i].depth==depth){
					this.images[i].draw(colorhidden);
				}
			}
		}
	};
	
	//Returns the most frontal image at position x,y
	this.getFrontImage = function(x, y){
		var img = 'notfound';
		var currentdepth = 0;
		
		for (i=0;i<this.num_images;i++){
			if(this.images[i].isInside(x,y) && 
			   this.images[i].depth>=currentdepth) {
				img = this.images[i];
				currentdepth = this.images[i].depth;
			}
		}	
		return img;
	};
}
