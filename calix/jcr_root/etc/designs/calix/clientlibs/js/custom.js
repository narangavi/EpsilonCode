// JavaScript Document// A $( document ).ready() block.
$(window).load(function () {

    if ($('.eventsAndSpeakers')[0] || $('.pressAndNews')[0]) {

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

    $('.displayFilters .grid').on('click', function () {
        //event.preventDefault();
        if ($('.displayFilters .list').hasClass('active')) {
            $('.displayFilters .grid').addClass('active');
            $('.displayFilters .list').removeClass('active');
            
            $('.eventsAndSpeakers .row').addClass('gridView');
            $('.eventsAndSpeakers .row').removeClass('listView');

            $('.pressAndNews .row').addClass('gridView');
            $('.pressAndNews .row').removeClass('listView');
        }
        // reset masonry
        re_masonry();
    });


    //set same height for hero content in mobile display
    var heightM = 0;

    $('.fullContainer > div').each(function(){ var h = $(this).height(); if (heightM < h){heightM = h;}; });
    $('.fullContainer > div').each(function(){ $(this).css("height", heightM) });
});

$(document).ready(function () {
    /* mobile search button functionality */
    $(window).resize(function () {
        if ($(".menuSub").css("position") == "relative") {
            $(".navbar-nav .dropdown").removeClass("open");
        }
    });
    //show mobile search field
    $(".showSearch").on('click', function () {
        $(".mobileSearch.row").css("display", "block");
    });

    //hide mobile search field
    $(".closeMobileSearch").on('click', function () {
        $(".mobileSearch.row").css("display", "none");
    });

    //close mobile menu
    $(".closeMobileMenu").on('click', function () {
        $("#navbar-collapse-grid").removeClass("in").addClass("collapse");
        $(".showSearch").css("display", "block");
        $(".closeMobileMenu-main").css("display", "none");
    });
    //notification list
    if ($('.noteList')[0]){
    	$('body').on('click', '.noteContent', function() {
          $(this).next('.viewClose').toggleClass('showCtrl');
        });
    }
    // Check to see if the related products Carousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.relatedProductsWrap')[0]) {
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


    // Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.left-col .newsCarousel')[0]) {
        $('.left-col .newsCarousel .slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: false,
            speed: 500,
            slidesToShow: 2,
            slidesToScroll: 2,
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
                // You can unslick at a given breakpoint now by adding:
                // settings: "unslick"
                // instead of a settings object
            ]
        });
    }
    // Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.right-col .newsCarousel')[0]) {
        $('.right-col .newsCarousel .slider-news-loop').slick({
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
    if ($('.col-md-8 .largePar .newsCarousel')[0]) {
        $('.col-md-8 .largePar .newsCarousel .slider-news-loop').slick({
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
    if ($('.col-md-4 .smallPar .newsCarousel')[0]) {
        $('.col-md-4 .smallPar .newsCarousel .slider-news-loop').slick({
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
                // You can unslick at a given breakpoint now by adding:
                // settings: "unslick"
                // instead of a settings object
            ]
        });
    }

    // Check to see if the newsCarousel exists. If so, then create and initialize the Slick Slider carousel.
    if ($('.newsCarousel')[0]) {
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

    if ($('.newsCarouselTwoVids')[0]) {

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

    if ($('.newsCarouselThreeVids')[0] || $('.about')[0]) {

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
    if ($('.newsAndSocialSpotlight')[0]) {
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
    if ($('.newsAndSocialSpotlight')[0]) {

        $('.slider-photos-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            adaptiveHeight: true,
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
    if ($('.main-hero')[0]) {
        $('.main-hero .slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true
        });
    }

    if ($('.left-col .main-hero')[0]) {
        $('.left-col .main-hero .slider-news-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true
        });
    }
    if ($('.main-hero')[0]) {
        $('.main-hero .slider-photos-loop').slick({
            autoplay: true,
            autoplaySpeed: 7000,
            dots: true,
            speed: 500,
            slidesToShow: 1,
            slidesToScroll: 1,
            infinite: true
        });
    }
    $('[placeholder]').focus(function () {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
            input.val('');
            input.removeClass('placeholder');
        }
    }).blur(function () {
        var input = $(this);
        if (input.val() == '' || input.val() == input.attr('placeholder')) {
            input.addClass('placeholder');
            input.val(input.attr('placeholder'));
        }
    }).blur();


    $('.displayFilters .list').on('click', function () {
        //event.preventDefault();
        if ($('.displayFilters .grid').hasClass('active')) {
            $('.displayFilters .list').addClass('active');
            $('.displayFilters .grid').removeClass('active');
            
            $('.eventsAndSpeakers .row').removeClass('gridView');
            $('.eventsAndSpeakers .row').addClass('listView');

            $('.pressAndNews .row').removeClass('gridView');
            $('.pressAndNews .row').addClass('listView');

            $('div.grid').masonry('destroy');
        }
    });

    $(document).on('click', '.yamm .dropdown-menu', function (e) {
        e.stopPropagation()
    });

    $("[data-toggle=popover]").popover({
        html: true,
        content: function () {
            return $('#popover-content').html();
        }
    });

    $("[data-toggle=share-social]").popover({
        html: true,
        content: function () {
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
    if ($('.myTab')[0]) {
        $('.myTab').tabCollapse({
            tabsClass: 'hidden-xs',
            accordionClass: 'visible-xs'
        });
    }

    $(".navbar-toggle").click(function () {
        if ($(".navbar-collapse").hasClass("in")) {
            $('.mainHdrContainer .showSearch').css('display', 'block');
            $('.mainHdrContainer .closeMobileMenu-main').css('display', 'none');
        } else {
            $('.mainHdrContainer .showSearch').css('display', 'none');
            $('.mainHdrContainer .closeMobileMenu-main').css('display', 'block');
        }
    });

    $(".closeMobileMenu-main").on('click', function () {
        $("#navbar-collapse-grid").removeClass("in").addClass("collapse");
        $('.mainHdrContainer .showSearch').css('display', 'block');
        $('.mainHdrContainer .closeMobileMenu-main').css('display', 'none');
    });
});

//Function to the css rule
function checkSize() {
    if ($(".menuSub").css("position") == "absolute") {

        $(".pushIt").on('click', function () {
            // $('.navbar').css( "height", '100%' );
            $(this).siblings('.dropdown-menu').css("left", 0);
        });

        $(".subHdr").on('click', function () {
            $(this).hide();
            $(this).siblings('.menuSub').css("left", 0).css('display', 'block');
            $(this).parent().css("position", 'absolute');
        });

        $(".mobileBack").on('click', function () {
            $(this).parent().siblings('h3').show();
            $(this).parent().css("left", '100%');
            $(this).parent().parent().css("position", 'relative');
        });

        $(".mobileBackMain").on('click', function () {
            $(this).parent().css("left", '100%');
        });

        //show mobile search field
        $(".showSearch").on('click', function () {
            $(".mobileSearch.row").css("display", "block");
        });

        //hide mobile search field
        $(".closeMobileSearch").on('click', function () {
            $(".mobileSearch.row").css("display", "none");
            $(".showSearch").css("display", "block");
        });

        //close mobile menu
        $(".closeMobileMenu").on('click', function () {
            $("#navbar-collapse-grid").removeClass("in").addClass("collapse");
        });
    }
}
