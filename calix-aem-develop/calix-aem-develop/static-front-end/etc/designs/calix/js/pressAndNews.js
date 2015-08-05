$(document).ready(function() {
    //  initial load
	$('.pressAndNews .subNav .categories li:first-child').addClass('active').children().addClass('active');
    $($('.pressAndNews .subNav .categories li:first-child').children().attr('href')).addClass('active in');

    // 
    $('.pressAndNews .subNav .categories li a:not(.active)').each(function() {
		$($(this).attr('href')).hide();
	});
    $('.pressAndNews .subNav .categories li a').click(function() {
        $($('li a.active').attr('href')).hide();
		$('li a.active').removeClass('active');

        $(this).addClass('active');
        $($(this).attr('href')).show();
        if ($('.pressAndNews .displayFilters .grid').hasClass('active')) {
           $('.pressAndNews .displayFilters .grid').click();
        }
    });
});