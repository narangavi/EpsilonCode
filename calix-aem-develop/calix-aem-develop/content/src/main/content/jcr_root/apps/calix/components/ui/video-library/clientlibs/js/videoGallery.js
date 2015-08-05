$(document).ready(function() {
	if($('.myCarousel')[0]){
	  $('.myCarousel').carousel({
		pause: true,
		interval: false
	  });
	}

	// handles the carousel thumbnails
  $('[id^=carousel-selector-]').click( function(){
	var id_selector = $(this).attr("id");
    var id = id_selector.replace("carousel-selector-","");
	id = parseInt(id);
	$('.myCarousel').carousel(id);
	$('[id^=carousel-selector-]').removeClass('selected');
	$(this).addClass('selected');
  });
  
  $('.myCarousel').on('slid.bs.carousel', function () {
	var id = $('.item.active').data('slide-number');
	id = parseInt(id);
	$('[id^=carousel-selector-]').removeClass('selected');
	$('[id=carousel-selector-'+id+']').addClass('selected');
  });

});
