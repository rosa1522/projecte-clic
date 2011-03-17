//Class to store touch positions
TouchData = {
	posX: new Array(20),
	posY: new Array(20),
	target: new Array(20),
	identifier: new Array(20),
	numFingers: 0
};

DragData = {
	active: false,
	identifier: 0,
	startPosX: 0,
	startPosY: 0,
	currentPosX: 0,
	currentPosY: 0
};

var ipad=false;
var android=false;


////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
var fprevent=function(e) { e.preventDefault(); };
var fcapture=function(e) {
	//Save the actual ones
	var changedTouches = e.changedTouches; //List only those that change
	var touches = e.touches; //List all touches
	TouchData.numFingers=touches.length;
	for (var i = 0; i < touches.length; i++) {
		TouchData.posX[i]=touches[i].pageX;
		TouchData.posY[i]=touches[i].pageY;
		TouchData.target[i]=touches[i].target;
		TouchData.identifier[i]=touches[i].identifier;
	}
	
	//Track the drag & drop of the first finger	
	//Check if it is the first finger touching the screen
	if (e.type == 'touchstart' && !DragData.active) {
		DragData.active=true;
		DragData.identifier = changedTouches[0].identifier;
		DragData.startPosX = changedTouches[0].pageX;
		DragData.startPosY = changedTouches[0].pageY;
		DragData.currentPosX = changedTouches[0].pageX;
		DragData.currentPosY = changedTouches[0].pageY;		
	//Check if we are removing the first finger
	} else if (e.type == 'touchend' && DragData.active) {
		for (var i = 0; i < changedTouches.length; i++) {
			if (changedTouches[i].identifier == DragData.identifier)
			{
				DragData.active = false;
				DragData.identifier=0;
			}
		}
	//Check if we are moving our finger around the screen
	} else if (e.type == 'touchmove' && DragData.active) {
		for (var i = 0; i < changedTouches.length; i++) {
			if (changedTouches[i].identifier == DragData.identifier)
			{
				DragData.currentPosX = changedTouches[i].pageX;
				DragData.currentPosY = changedTouches[i].pageY;	
			}
		}
	}	



	//if (TouchData.numFingers>0) 
	//	for(i=0; i<TouchData.numFingers; i++)
	 //   	Text.renderText('FINGER ('+TouchData.identifier[i]+'):'
	    //                  +TouchData.posX[i]+' '+TouchData.posY[i], 24, 10,60+30*i);    	
	//if(DragData.active) 
	//	Text.renderText('DRAG FROM: '+DragData.startPosX+' '+DragData.startPosY+' TO: '
	//                  +DragData.currentPosX+' '+DragData.currentPosY, 24, 10,30);   

};

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// Work with the touch data in an iPAD
if ( navigator.userAgent.match(/(iPad|Android)/i) != null) 
{
	if ( navigator.userAgent.match(/iPad/i) != null) { ipad = true; }
	else { android = true; }
	
	//Prevent the standard ones	
	document.addEventListener('gesturestart',fprevent, false);
	document.addEventListener('gesturechange', fprevent, false);
	document.addEventListener('gestureend', fprevent, false);
	document.addEventListener('touchstart', fprevent, false);
	document.addEventListener('touchmove', fprevent, false);
	document.addEventListener('touchend', fprevent, false);
	//Add the new events
	document.getElementById('canvas').addEventListener('touchstart', fcapture, false);
	document.getElementById('canvas').addEventListener('touchmove', fcapture, false);
	document.getElementById('canvas').addEventListener('touchend', fcapture, false);	
}

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// put the mouse info in the DragData class
else
{
	document.getElementById('canvas').addEventListener('mousedown',function (e) {
		DragData.active=true;
		DragData.identifier = 1;
		DragData.startPosX = e.pageX;
		DragData.startPosY = e.pageY;
		DragData.currentPosX = e.pageX;
		DragData.currentPosY = e.pageY;		
	}, false);
	
	document.getElementById('canvas').addEventListener('mouseup',function (e) {	
		if(DragData.active)
		{
			DragData.active = false;
			DragData.identifier=0;	
		}
	}, false);
	
	document.getElementById('canvas').addEventListener('mousemove',function (e) {
		if(DragData.active)
		{
			DragData.currentPosX = e.pageX;
			DragData.currentPosY = e.pageY;	
		}
	}, false);
}

