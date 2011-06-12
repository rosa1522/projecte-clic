//Class to store touch positions
ControlData = {
	active: false,
	identifier: 0,
	startPosX: 0,
	startPosY: 0
};

// Work with the touch data in an iPad or an Android
if ( navigator.userAgent.match(/(iPad|Android)/i) != null) 
{
	//Add the new events
	document.getElementById('canvasControl').addEventListener('touchstart',function (e) {
		var changedTouches = e.changedTouches; //List only those that change
		if(!ControlData.active)
		{
			ControlData.active=true;
			ControlData.identifier = changedTouches[0].identifier;
			ControlData.startPosX = changedTouches[0].pageX;
			ControlData.startPosY = changedTouches[0].pageY;
		}
	}, false);
	
	document.getElementById('canvasControl').addEventListener('touchend',function (e) {
		var changedTouches = e.changedTouches; //List only those that change
		if(ControlData.active)
		{
			for (var i = 0; i < changedTouches.length; i++) {
				if (changedTouches[i].identifier == ControlData.identifier)
				{
					ControlData.active = false;
					ControlData.identifier=0;
				}
			}
		}
	}, false);
}
// put the mouse info in the DragData class
else
{
	document.getElementById('canvasControl').addEventListener('mousedown',function (e) {
		ControlData.active=true;
		ControlData.identifier = 1;
		ControlData.startPosX = e.pageX;
		ControlData.startPosY = e.pageY;
	}, false);
	
	document.getElementById('canvasControl').addEventListener('mouseup',function (e) {	
		if(ControlData.active)
		{
			ControlData.active = false;
			ControlData.identifier=0;	
		}
	}, false);
}

