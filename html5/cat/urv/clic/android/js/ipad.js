//Class to store touch positions
TouchData = {
	posX: new Array(10),
	posY: new Array(10),
	numFingers: 0
};


if ( navigator.userAgent.match(/iPad/i) != null ) 
{
  $(function () {

    $(document).bind('gesturestart gesturechange gestureend touchstart touchmove touchend', function (e) {
      e.preventDefault();
    });

	$(document).bind('touchstart touchmove touchend', function (e) {
		//Save the actual ones
		var touches = (e.type == 'touchend') ? e.originalEvent.changedTouches : e.originalEvent.touches;
		TouchData.numFingers=touches.length;
		for (var i = 0; i < touches.length; i++) {
			posX[i]=touches[i].pageX;
			posY[i]=touches[i].pageY;
		}
	});
  });
}

