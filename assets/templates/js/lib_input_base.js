var ipad=false;
var android=false;

//Save if we have an android or an ios device
if ( navigator.userAgent.match(/iPad/i) != null) { ipad = true; }
else if ( navigator.userAgent.match(/Android/i) != null) { 
	android = true; 
//	document.getElementById('canvas').width=650;
//	document.getElementById('canvas').height=320;			
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

