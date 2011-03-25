//Class to store touch positions
DragData = {
	active: false,
	identifier: 0,
	startPosX: 0,
	startPosY: 0,
	currentPosX: 0,
	currentPosY: 0
};


////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
// Work with the touch data in an iPad or an Android
if ( navigator.userAgent.match(/(iPad|Android)/i) != null) 
{
	//Add the new events
	document.getElementById('canvas').addEventListener('touchstart',function (e) {
		var changedTouches = e.changedTouches; //List only those that change
		if(!DragData.active)
		{
			DragData.active=true;
			DragData.identifier = changedTouches[0].identifier;
			DragData.startPosX = changedTouches[0].pageX;
			DragData.startPosY = changedTouches[0].pageY;
			DragData.currentPosX = changedTouches[0].pageX;
			DragData.currentPosY = changedTouches[0].pageY;	
		}
	}, false);
	
	document.getElementById('canvas').addEventListener('touchend',function (e) {
		var changedTouches = e.changedTouches; //List only those that change
		if(DragData.active)
		{
			for (var i = 0; i < changedTouches.length; i++) {
				if (changedTouches[i].identifier == DragData.identifier)
				{
					DragData.active = false;
					DragData.identifier=0;
				}
			}
		}
	}, false);
	
	document.getElementById('canvas').addEventListener('touchmove',function (e) {
		var changedTouches = e.changedTouches; //List only those that change
		if(DragData.active)
		{
		for (var i = 0; i < changedTouches.length; i++) {
				if (changedTouches[i].identifier == DragData.identifier)
				{
					DragData.currentPosX = changedTouches[i].pageX;
					DragData.currentPosY = changedTouches[i].pageY;	
				}
			}		
		}
	}, false);
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

