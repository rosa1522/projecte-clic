var ipad=false;
var android=false;

//Save if we have an android or an ios device
if ( navigator.userAgent.match(/iPad/i) != null) { ipad = true; }
else if ( navigator.userAgent.match(/Android/i) != null) { android = true; }
	
if (android == true) {
	document.getElementById('canvas').width=603;
	document.getElementById('canvas').height=375;
	document.getElementById('canvasControl').width=80;
	document.getElementById('canvasControl').height=375;
	document.getElementById('canvasControl').style.top='0px';
	document.getElementById('canvasControl').style.left='603px';
}

//Prevent the standard multitouch events
if ( navigator.userAgent.match(/(iPad|Android)/i) != null)  {
	var fprevent=function(e) { e.preventDefault(); };
	document.addEventListener('gesturestart',fprevent, false);
	document.addEventListener('gesturechange', fprevent, false);
	document.addEventListener('gestureend', fprevent, false);
	document.addEventListener('touchstart', fprevent, false);
	document.addEventListener('touchmove', fprevent, false);
	document.addEventListener('touchend', fprevent, false);		
}
