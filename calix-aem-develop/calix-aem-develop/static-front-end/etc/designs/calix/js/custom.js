// A $( document ).ready() block.
$(document).ready(function() {

    var windowWidthDesktop = true;

  if ($('.gridder')[0]){

    function getQueryVariable(variable) {
      var query = window.location.search.substring(1);
      //console.log('query: '+window.location.search);
      var vars = query.split("&");
      for (var i=0; i<vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
          return pair[1];
        }
      }
      return false;
    }

    var getBio = unescape(getQueryVariable("bio"));
    var getRole = unescape(getQueryVariable("role"));


    //console.log('getBio: '+getBio+'\n'+'getRole: '+getRole);

    var listItem = $('.employees > h3:contains('+getBio+')');
    var getIndex = $( '.employees > h3' ).index( listItem);

    //console.log('index: ' + getIndex);

    var test = $('.employees').eq(getIndex).parent();
    //console.log('test', test);


    // Call Gridder
    $(".gridder").gridderExpander({
        scroll: true,
        scrollOffset: 320,
        showNav: false,
        scrollTo: "panel", // "panel" or "listitem"
        animationSpeed: 500,
        animationEasing: "easeInOutExpo",
        defaultIndex: getIndex,
        defaultTarget: test,
        onStart: function () {
            //console.log("Gridder Inititialized");
        },
        onExpanded: function (object) {
            //console.log("Gridder Expanded");
            $(".carousel").carousel();
        },
        onChanged: function (object) {
            //console.log("Gridder Changed");
        },
        onClosed: function () {
            //console.log("Gridder Closed");
        }
    });
  }

  // Check to see if the related products Carousel exists. If so, then create and initialize the Slick Slider carousel.
if ($('.relatedProductsWrap')[0]){
  $('.relatedProductsList').slick({
    autoplay: true,
    autoplaySpeed: 7000,
    dots: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 3,
    infinite: true,
    responsive: [
  // {
  //   breakpoint: 1024,
  //   settings: {
  //     slidesToShow: 3,
  //     slidesToScroll: 3
  //   }
  // },
  // {
  //   breakpoint: 768,
  //   settings: {
  //     slidesToShow: 2,
  //     slidesToScroll: 1
  //   }
  // },
  {
    breakpoint: 768,
    settings: {
      slidesToShow: 1,
      slidesToScroll: 1
    }
  }
  // You can unslick at a given breakpoint now by adding:
  // settings: "unslick"
  // instead of a settings object
  ]
});
}

  // Masonry jQuery http://masonry.desandro.com/

  if ($('.eventsAndSpeakers')[0] || $('.pressAndNews')[0]){

    var re_masonry = function () {
      $('div.grid').masonry(
          {
            itemSelector: '.grid-item',
            columnWidth: 500
          }
      )
    }

    // page init: set masonry
     re_masonry();


    }
    // Adjust header logo based on desktop or mobile view
    function adjustLogo() {
        var x = $("a.navbar-brand");
        if ( $(window).width() < 768) {
            x.insertAfter("button[data-target='#navbar-collapse-grid']");
            x.css("background-size","105px");
            if (executiveTeamOpen && !directorsOpen) {
                $(".executiveHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
                $(".directorsHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
                $(".directorsDesktopNav").removeClass('active');
                $(".executiveDesktopNav").addClass('active');
                $(".executiveTeam").show();
            } else {
                $(".executiveHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
                $(".directorsDesktopNav").removeClass('active');
                $(".executiveDesktopNav").addClass('active');
                $(".executiveTeam").hide();
            }
            if (directorsOpen && !executiveTeamOpen) {
                $(".directorsHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
                $(".executiveDesktopNav").removeClass('active');
                $(".directorsDesktopNav").addClass('active');
                $(".directors").show();
            }

        } else if( $(window).width() > 768 ) {

            $("a.navbar-brand").appendTo(".mainHdrContainer .logo");
            $('.directors').hide();
            if (executiveTeamOpen && directorsOpen) {
                $(".executiveTeam").show();
                executiveTeamOpen = true;
                directorsOpen = false;
            }
            if (executiveTeamOpen) {
                $(".executiveTeam").show();
                executiveTeamOpen = true;
                directorsOpen = false;
            }
            if (!executiveTeamOpen && !directorsOpen) {
                $(".executiveTeam").show();
                executiveTeamOpen = true;
                directorsOpen = false;
            }
            if (!executiveTeamOpen && directorsOpen) {
                $(".executiveTeam").hide();
                $(".directors").show();
                executiveTeamOpen = false;
                directorsOpen = true;
            }
        }
    }

    // run on initial page load
    adjustLogo();

    // run on window resize
    $(window).resize(adjustLogo);

    /* mobile search button functionality */

    //show mobile search field
    $(".showSearch").on('click', function() {
        $(".mobileSearch.row").css("display", "block");
    });

    //hide mobile search field
    $(".closeMobileSearch").on('click', function() {
        $(".mobileSearch.row").css("display", "none");
    });

    //close mobile menu
    $(".closeMobileMenu").on('click', function() {
        $("#navbar-collapse-grid").removeClass("in").addClass("collapse");
    });



  // Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.col-md-8 .largePar .newsCarousel')[0]){
        $('.slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: false,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true,
            responsive: [
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }

                }
                // You can unslick at a given breakpoint now by adding:
                // settings: "unslick"
                // instead of a settings object
            ]
        });
    }

	// Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.col-md-4 .smallPar .newsCarousel')[0]){
        $('.slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: false,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true,
            responsive: [
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }

                }
                // You can unslick at a given breakpoint now by adding:
                // settings: "unslick"
                // instead of a settings object
            ]
        });
    }



    // Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.parsys  .newsCarousel')[0]){

        $('.slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: false,
            speed: 500,
            slidesToShow: 3,
            slidesToScroll: 3,
            infinite: true,
            responsive: [
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 3,
                        slidesToScroll: 3
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }

                }
                // You can unslick at a given breakpoint now by adding:
                // settings: "unslick"
                // instead of a settings object
            ]
        });
    }

    if ($('.newsCarouselTwoVids')[0]){

        $('.slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            slidesToShow: 2,
            slidesToScroll: 2,
            dots: false,
            speed: 500,
            infinite: true,
            responsive: [
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 2
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }
                }
            ]
        });
    }

    if ($('.newsCarouselThreeVids')[0] || $('.about')[0]){

        $('.slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            slidesToShow: 3,
            slidesToScroll: 3,
            dots: false,
            speed: 500,
            infinite: true,
            responsive: [
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 3,
                        slidesToScroll: 3
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 1,
                        slidesToScroll: 1
                    }
                }
            ]
        });
    }


// Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.newsAndSocialSpotlight')[0]){
        $('.slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true
        });
    }




// Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.newsAndSocialSpotlight')[0]){

        $('.slider-photos-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true,
            responsive: [
                {
                    breakpoint: 320,
                    settings: {
                        arrows: false
                    }
                },
                {
                    breakpoint: 1024,
                    settings: {
                        arrows: true
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        arrows: true
                    }
                }
            ]
        });
    }
$('[placeholder]').focus(function() {
  var input = $(this);
  if (input.val() == input.attr('placeholder')) {
    input.val('');
    input.removeClass('placeholder');
  }
}).blur(function() {
  var input = $(this);
  if (input.val() == '' || input.val() == input.attr('placeholder')) {
    input.addClass('placeholder');
    input.val(input.attr('placeholder'));
  }
}).blur();


    $('.displayFilters .grid').on('click', function() {
      event.preventDefault();
      if ($('.displayFilters .list').hasClass('active')) {
        $('.displayFilters .grid').addClass('active');
        $('.displayFilters .list').removeClass('active');
        //console.log('Grid View');
        $('.eventsAndSpeakers .row').addClass('gridView');
        $('.eventsAndSpeakers .row').removeClass('listView');

        $('.pressAndNews .row').addClass('gridView');
        $('.pressAndNews .row').removeClass('listView');
      }
      // reset masonry
      re_masonry();
    });

    $('.displayFilters .list').on('click', function() {
      event.preventDefault();
      if ($('.displayFilters .grid').hasClass('active')) {
        $('.displayFilters .list').addClass('active');
        $('.displayFilters .grid').removeClass('active');
        //console.log('List View');
        $('.eventsAndSpeakers .row').removeClass('gridView');
        $('.eventsAndSpeakers .row').addClass('listView');

        $('.pressAndNews .row').removeClass('gridView');
        $('.pressAndNews .row').addClass('listView');

        $('div.grid').masonry( 'destroy' );
      }
    });

    $(document).on('click', '.yamm .dropdown-menu', function(e) {
        e.stopPropagation()
    });

    $("[data-toggle=popover]").popover({
        html: true,
        content: function() {
            return $(this).next('#popover-content').html();
        }
    });

    $("[data-toggle=share-social]").popover({
        html: true,
        content: function() {
            return $('#social-content').html();
        },
        template: '<div class="popover popover-social" style="  margin-left: -95px;"><div class="arrow arrow-popover" style="margin-left: 90px;"></div><h3 class="popover-title"></h3><div class="popover-content content-social-pop"></div></div>'
    });

    //run test on initial page load
    checkSize();

    //run test on resize of the window
    $(window).resize(checkSize);

    $(document).on('click', function (e) {
        $('[data-toggle="popover"]').each(function () {
            //the 'is' for buttons that trigger popups
            //the 'has' for icons within a button that triggers a popup
            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                $(this).popover('hide');
            }
        });
    });


    //init tabs
    if ($('#myTab')[0]){
        $('#myTab').tabCollapse({
            tabsClass: 'hidden-sm hidden-xs',
            accordionClass: 'visible-sm visible-xs'
        });
    }

    $( ".navbar-toggle" ).click(function() {
        if($(".navbar-collapse").hasClass("in")){
            $('.mainHdrContainer .showSearch').css('display','block');
            $('.mainHdrContainer .closeMobileMenu-main').css('display','none');
        }else{
            $('.mainHdrContainer .showSearch').css('display','none');
            $('.mainHdrContainer .closeMobileMenu-main').css('display','block');
        }
    });

    $(".closeMobileMenu-main").on('click', function() {
        $("#navbar-collapse-grid").removeClass("in").addClass("collapse");
        $('.mainHdrContainer .showSearch').css('display','block');
        $('.mainHdrContainer .closeMobileMenu-main').css('display','none');
    });

});

function tablesawChangeMode(selector, mode){
        //selector = id of the table
        //mode = columntoggle, swipe or stack

        var $switcher = $(selector).closest('.tablesaw-modeswitch');
        $switcher.remove();
        //$(selector).data( 'table' ).destroy();

        $(selector).attr( 'data-tablesaw-mode', mode );
        $(selector).table();
}

//Function to the css rule
function checkSize(){
    if ($(".menuSub").css("position") == "absolute" ){

        $(".pushIt").on('click', function() {
            // $('.navbar').css( "height", '100%' );
            $(this).siblings('.dropdown-menu').css( "left", 0 );
        });

        $(".subHdr").on('click', function() {
            $(this).hide();
            $(this).siblings('.menuSub').css( "left", 0 ).css('display', 'block');
            $(this).parent().css( "position", 'absolute' );
        });

        $(".mobileBack").on('click', function() {
            $(this).parent().siblings('h3').show();
            $(this).parent().css( "left", '100%' );
            $(this).parent().parent().css( "position", 'relative' );
        });

        $(".mobileBackMain").on('click', function() {
            $(this).parent().css( "left", '100%' );
        });

        //show mobile search field
        $(".showSearch").on('click', function() {
            $(".mobileSearch.row").css("display", "block");
        });

        //hide mobile search field
        $(".closeMobileSearch").on('click', function() {
            $(".mobileSearch.row").css("display", "none");
        });

        //close mobile menu
        $(".closeMobileMenu").on('click', function() {
            $("#navbar-collapse-grid").removeClass("in").addClass("collapse");
        });
    }
    else if ($(".menuSub").css("position") == "relative" ){
        $(".navbar-nav .dropdown").removeClass("open");
    }

    else if ($("#vidGallery").css("width") == "100%" ){
      //Remove modal styles to display videos inline on mobile

      $('#vidGallery #slider-thumbs').css('display', 'none');
      $('#vidGallery .videoGalleryWrap .contentArea').css('height', 'auto');
      $('#vidGallery #carousel-bounding-box').removeAttr('id');
      $('#vidGallery #myCarousel').removeAttr('class');
      $('#vidGallery #myCarousel').removeAttr('id');
      $('#vidGallery #slider').removeAttr('id');
      $('#vidGallery .carousel-inner').removeAttr('class');

      //refresh page on browser resize -- edge case but will need to cover bases here
      $(window).bind('resize', function(e)
      {
        if (window.RT) clearTimeout(window.RT);
        window.RT = setTimeout(function()
        {
          this.location.reload(false);
        }, 300);
      });

    }
    //Restore modal on window resize should that happen
    else if ($("#vidGallery").css("width") == "900px" ){

      $('.videoGalleryWrap .contentArea').css('height', '945px');
      $('.featureImage').children().attr('id', 'slider');
      $('#slider').children().attr('id', 'carousel-bounding-box');
      $('#carousel-bounding-box').children().attr('id', 'myCarousel');
      $('#carousel-bounding-box').children().addClass('carousel');
      $('#myCarousel').children().addClass('carousel-inner');

    }
    //remove show more plugin functionality by removing class
    else if ($(".checkWrap").css("position") == "relative" ){
      $('ul.expandable').removeAttr('class');
    }
}

//Initial width check
checkWidth();

$(window).resize(checkWidth);

var popUpClicked = false;

var formValue = '';

$(".showingMostRecentNav").on("click", function(){
    if ( $(window).width() > 768) {
        $('.popover').popover('show');
        if (!popUpClicked) {
            checkWidth ();
            popUpClicked = true;
        } else {
            popUpClicked = false;
        }
    }
    if ( $(window).width() < 768) {
        $('.popover').popover('destroy');
        console.log('destroyed');
        $('.monthYearPicker').show();
        $('.monthYearPicker').focus();
        $('.monthYearPicker').val('2015-07');
        console.log('formValue: '+formValue);
    }
});

var d = new Date();
var month = new Array();
month[0] = "January";
month[1] = "February";
month[2] = "March";
month[3] = "April";
month[4] = "May";
month[5] = "June";
month[6] = "July";
month[7] = "August";
month[8] = "September";
month[9] = "October";
month[10] = "November";
month[11] = "December";
var n = month[d.getMonth()]+' blah '+d.getFullYear();

var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    for (i = new Date().getFullYear() ; i > 2008; i--) {
        $.each(months, function (index, value) {
            $('#yearMonthInput').append($('<option />').val(index + "_" + i).html(value + " " + i));
            //console.log(value + " " + i);
        });                
    }


$(".monthYearPicker").change( function() {
    formValue = $('.monthYearPicker').val();
    
    var convertDate = formValue;
    var getMonth = formValue.substring(7,5);
    var getYear = formValue.substring(0, 4);
    if (getMonth === '01') {
        getMonth = 'January';
    } else if (getMonth === '02') {
        getMonth = 'February';
    } else if (getMonth === '03') {
        getMonth = 'March';
    } else if (getMonth === '04') {
        getMonth = 'April';
    } else if (getMonth === '05') {
        getMonth = 'May';
    } else if (getMonth === '06') {
        getMonth = 'June';
    } else if (getMonth === '07') {
        getMonth = 'July';
    } else if (getMonth === '08') {
        getMonth = 'August';
    } else if (getMonth === '09') {
        getMonth = 'September';
    } else if (getMonth === '10') {
        getMonth = 'October';
    } else if (getMonth === '11') {
        getMonth = 'November';
    } else if (getMonth === '12') {
        getMonth = 'December';
    }
    $('.dateRange').html('<span class="caret"></span> '+getMonth+' '+getYear);
    //console.log('formValue was selected: '+getMonth+' '+getYear);
});

$( ".monthYearPicker" ).blur(function() {
  //alert( "Handler for .blur() called." );
  $('.monthYearPicker').hide();
});



function checkWidth () {
    if ( $(window).width() < 768) {
        
        console.log('mobile');
        /*
         if ($('.tableTest')[0]){
            tablesawChangeMode('tableTest', 'stack');
            console.log('stack');
        }*/
    } else {
        console.log('non-mobile');
        /*
        $('.legend').css('position','relative');
            $('.legend').css('top','0px');
            $('.legend ul').css('float','none');
            $('.legend ul').css('text-align','left');
            $('.legend li').css('display','block');
            $('.legend li div').show();
        if ($('.tablesaw')[0]){
            tablesawChangeMode('tableTest', 'columntoggle');
            console.log('columntoggle');
        }
        */
    }
    console.log ('width: '+ $(window).width());
}

var legendSticky = false,
    scrollThreshold = 300;

if ($('.nonTable')[0]){
    legendFixMobile();
}

var legendHeight = $('.legend').offset();

function legendFixMobile () {



    $(window).scroll(function(){
    console.log('scrollTop(): '+$(this).scrollTop());
    //
    if ($(this).scrollTop()>675) {
        //$(this).scrollTo(0);
        $('.info').text('fixed');
        //$('.legend').css('position','fixed');
    } else {
        $('.info').text($(this).scrollTop());

        //$('.legend').css('position','relative');
    }
    if(!legendSticky) {
        legendSticky = true;
    }

/*
    if($(this).scrollTop()>376){
        if (legendSticky === false) {
            legendSticky = true;
            $('legend').removeClass('open');
            $('.legend').css('top','0px');
            $('.legend').css('margin','0 0 20px 0');
            $('.legend').css('position','fixed');
            $('.legend h3').css('float','left');
            $('.legend .title').css('width','25%');
            $('.legend ul').css('float','right');
            $('.legend ul').css('text-align','right');
            $('.legend li').css('display','inline');
            $('.legend li div').hide();
            $('.legend').css('border-bottom','1px solid #ccc');
            
        } 
    }
    if($(this).scrollTop()<=160){ 
        $('legend').addClass('open');
        $('.legend').css('top','0px');
        $('.legend').css('left','0px');
        $('.legend .title').css('width','100%');
        $('.legend').css('position','relative');
        $('.legend').css('padding','10px 15px 15px 15px');
        $('.legend').css('margin','20px 0 20px 0');
        $('.legend ul').css('text-align','left');
        $('.legend li').css('display','block');
        $('.legend li div').show();
        $('.legend .title i').removeClass('glyphicon glyphicon-menu-down').addClass('glyphicon glyphicon-menu-up');
        legendOpen = true;
    }
    */
});


}

var legendOpen = false;

$(".legend").on("click", function(){
    if (!legendOpen) {
        $('legend').addClass('open');
        $('.legend').css('top','0px');
        $('.legend').css('left','0px');
        $('.legend .title').css('width','100%');
        $('.legend').css('position','fixed');
        $('.legend').css('padding','10px 15px 15px 15px');
        $('.legend').css('margin','0');
        $('.legend ul').css('text-align','left');
        $('.legend li').css('display','block');
        $('.legend li div').show();
        $('.legend .title i').removeClass('glyphicon glyphicon-menu-down').addClass('glyphicon glyphicon-menu-up');
        legendOpen = true;
    } else {
            //$('legend').removeClass('open');
            $('.legend').css('top','0px');
            $('.legend').css('margin','0 0 20px 0');
            $('.legend').css('position','fixed');
            $('.legend h3').css('float','left');
            $('.legend .title').css('width','25%');
            $('.legend ul').css('float','right');
            $('.legend ul').css('text-align','right');
            $('.legend li').css('display','inline');
            $('.legend li div').hide();
        $('.legend .title i').removeClass('glyphicon glyphicon-menu-up').addClass('glyphicon glyphicon-menu-down');
        legendOpen = false;
    }
    console.log('legendOpen: '+legendOpen);
});

    $('#accordion1').find('.collapse').on('shown.bs.collapse', function(){
        $(this).parent().find(".openCloseIconBlue").removeClass("openCloseIconBlue").addClass("openCloseIconBlueMinus");
        }).on('hidden.bs.collapse', function(){
        $(this).parent().find(".openCloseIconBlueMinus").removeClass("openCloseIconBlueMinus").addClass("openCloseIconBlue");
    });

    var executiveTeamOpen = true,
            directorsOpen = false;

    // Toggle desktop menu nav for Leadership page:

    $(".directorsDesktopNav").on('click', function() {
        event.preventDefault();
        $(".executiveDesktopNav").removeClass('active');
        $(".directorsDesktopNav").addClass('active');
        $(".directorsHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
        $(".executiveHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
        $(".executiveTeam").fadeOut('slow', function() {
            $(".directors").fadeIn('slow');
        });
        //console.log('directorsOpen: '+directorsOpen);
        directorsOpen = true;
        executiveTeamOpen = false;
        return false;
    });

    $(".executiveDesktopNav").on('click', function() {
        event.preventDefault();
        $(".directorsDesktopNav").removeClass('active');
        $(".executiveDesktopNav").addClass('active');
        $(".executiveHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
        $(".directorsHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
        $(".directors").fadeOut('slow', function() {
            $(".executiveTeam").fadeIn('slow');
        });
        //console.log('executiveTeamOpen: '+executiveTeamOpen);
        executiveTeamOpen = true;
        directorsOpen = false;
        return false;
    });


    // Toggle mobile menu nav for Leadership page:

    $(".executiveHeader").on('click', function(event) {
        $('.gridder-show').hide();
        if (!executiveTeamOpen) {
            executiveTeamOpen = true;
            $(this).find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
            $(".directorsDesktopNav").removeClass('active');
            $(".executiveDesktopNav").addClass('active');
            $(".executiveTeam").slideDown('slow');
        } else {
            $(this).find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
            $(".executiveTeam").slideUp('slow');
            executiveTeamOpen = false;
        }

        return false;
    });

    $(".directorsHeader").on('click', function(event) {
        if (!directorsOpen) {
            directorsOpen = true;
            $(this).find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
            $(".executiveDesktopNav").removeClass('active');
            $(".directorsDesktopNav").addClass('active');
            $(".directors").slideDown('slow');
        } else {
            $(this).find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
            $(".directors").slideUp('slow');
            directorsOpen = false;
        }
        //console.log('directorsOpen: '+directorsOpen);
        return false;
    });

    if (executiveTeamOpen && directorsOpen) {
        $('.directors').hide();
        $(".executiveTeam").show();
        executiveTeamOpen = true;
        directorsOpen = false;
    };

    $(".editAccountInfo").on('click', function() {
        event.preventDefault();
        $('.accountInformation').fadeOut('fast', function() {
            $('.accountInformationEdit').fadeIn('fast');
        });
        return false;
    });

    $(".saveBtn").on('click', function() {
        event.preventDefault();
        $('.accountInformationEdit').fadeOut('fast', function() {
            $('.accountInformation').fadeIn('fast');
        });
        return false;
    });

    $(".cancelBtn").on('click', function() {
        event.preventDefault();
        $('.accountInformationEdit').fadeOut('fast', function() {
            $('.accountInformation').fadeIn('fast');
        });
        return false;
    });

    // Saved text confirmation for the checkboxes on the Account Settings page.

    $('.accntIconLinks').click(function() {
        $(this).parent().find('.saved').hide();
        if($('.accntIconLinks input').prop("checked")){
            $(this).find('input').addClass('checked');
        }else{
            $(this).find('input').removeClass('checked');
        }
        $(this).parent().find('.saved').stop().fadeIn(100, function() {
            $(this).parent().find('.saved').delay(1500).fadeOut(200);
        });
    });
