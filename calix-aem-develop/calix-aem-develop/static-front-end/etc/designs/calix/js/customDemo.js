$(document).ready(function() {
  var totalCount = $(this).find('.tablesaw-advance-dots > li').length - 1;
    var startIndex = 1;
    var endIndex = 4;
$('.tablesaw-nav-btn').on('click', function(){
  $(this).parent().next('.minimap').find('.tablesaw-advance-dots > li').each(function(i,item) {
    if(i !== 0){
      if(item.className !== 'tablesaw-advance-dots-hide') {
        startIndex = i;
        if(startIndex < totalCount - 3){
          endIndex = startIndex + 3;
        } else {
          endIndex = totalCount;
        }
        return false;
      }
  };

  });
  var $countDiv = $(this).parent().parent().prev('.count');
  $countDiv.find('.numFirst').html(startIndex);
  $countDiv.find('.numSecond').html(endIndex);
});

$('.tablesaw-bar').each(function(){
  var totalCount = $(this).find('.tablesaw-advance-dots > li').length - 1;
  var startIndex = 1;
  var endIndex = 4;
  var $countDiv = $(this).prev('.count');
  $(this).find('.tablesaw-advance-dots > li').each(function(i,item) {
    if(i !== 0){
      // if(item.className === '') {
      if(item.className !== 'tablesaw-advance-dots-hide') {
        startIndex = i;
        if(startIndex < totalCount - 3){
          endIndex = startIndex + 3;
        } else {
          endIndex = totalCount;
        }
        return false;
      }
    }
    $countDiv.find('.numFirst').html(startIndex);
    $countDiv.find('.numSecond').html(endIndex);
    $countDiv.find('.numTotal').html(totalCount);
  });

  });

  // $('.tablesaw').each(function(){
  //   console.log( $(this).data('tablesaw-mode') );
  //   if ($(this).data('tablesaw-mode') === 'stack') {
  //     $(this).prev().prev().hide();
  //     console.log('yo');
  //   }
  // });
  //
  // function checkSize(){
  //
  //   // if ($(".count").css("display") == "none" ){
  //   //   console.log('mobile table');
  //   //   $('.tablesaw').attr('data-tablesaw-mode', 'stack');
  //   //   $('.tablesaw').removeClass('tablesaw-swipe');
  //   //   $('.tablesaw').addClass('tablesaw-stack');
  //   //   $( ".tablesaw" ).table( "refresh" );
  //   //   $( ".tablesaw" ).prev().removeClass('mode-swipe');
  //   //   $( ".tablesaw" ).prev().addClass('mode-stack');
  //   //
  //   //   $(".tablesaw").removeData(); // remove any data tablesaw has associated with the table
  //   //   $(".tablesaw").table(); //call the plugin again
  //   //
  //   //
  //   // }
  //
  //   //refresh page on browser resize -- edge case but will need to cover bases here
  //   // $(window).bind('resize', function(e)
  //   // {
  //   //   if (window.RT) clearTimeout(window.RT);
  //   //   window.RT = setTimeout(function()
  //   //   {
  //   //     this.location.reload(false);
  //   //   }, 300);
  //   // });
  //
  //
  //
  //
  //
  //
  // };

  //run test on initial page load
  // checkSize();

  //run test on resize of the window
  // $(window).resize(checkSize);



});
