////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
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


////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
//A collection of images
function ImageSet()
{
	this.images = new Array(20);
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
				if(this.images[i].depth==depth) this.images[i].draw();
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
