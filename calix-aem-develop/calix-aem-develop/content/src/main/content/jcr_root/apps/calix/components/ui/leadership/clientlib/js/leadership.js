var executiveTeamOpen = true,
	directorsOpen = false,
	isMobileView = false;

$(document).ready(function() {
    // run on initial page load
    adjustLeadership();

    // run on window resize
    $(window).resize(adjustLeadership);

    if ($('.gridder')[0]) {
        function getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return false;
        }

        var getBio = unescape(getQueryVariable("bio"));
        var getRole = unescape(getQueryVariable("role"));
        
        var listItem = $('.employees > h3:contains(' + getBio + ')');
        var getIndex = $('.employees > h3').index(listItem);
        var test = $('.employees').eq(getIndex).parent();

        // Call Gridder
        $('.gridder').gridderExpander({
            scroll: false,
            scrollOffset: 320,
            showNav: false,
            scrollTo: "panel", // "panel" or "listitem"
            animationSpeed: 500,
            animationEasing: "easeInOutExpo",
            defaultIndex: getIndex,
            defaultTarget: test,
            multiExpanded: isMobileView,
            onStart: function () {
            },
            onExpanded: function (object) {
            },
            onChanged: function (object) {
            },
            onClosed: function () {
            }
        });

        var parent = test.parent();
        if (getBio !== "false" && !parent.is(":visible")) {
            if (parent.hasClass("executiveTeam")) {
                $(".directorsDesktopNav").removeClass('active');
                $(".executiveDesktopNav").addClass('active');
                $(".executiveHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
                $(".directorsHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
                $(".directorsDesc").hide();
                $(".executiveDesc").show();
                $(".directors").hide();
                $(".executiveTeam").show();
                directorsOpen = fase;
                executiveTeamOpen = true;
            } else if (parent.hasClass("directors")) {
                $(".executiveDesktopNav").removeClass('active');
                $(".directorsDesktopNav").addClass('active');
                $(".directorsHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
                $(".executiveHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
                $(".executiveDesc").hide();
                $(".directorsDesc").show();
                $(".executiveTeam").hide();
                $(".directors").show();
                executiveTeamOpen = false;
                directorsOpen = true;
            }

            parent.gridderExpander({
                scroll: false,
                scrollOffset: 320,
                showNav: false,
                scrollTo: "panel", // "panel" or "listitem"
                animationSpeed: 500,
                animationEasing: "easeInOutExpo",
                defaultIndex: getIndex,
                defaultTarget: test,
                multiExpanded: isMobileView,
                onStart: function () {
                },
                onExpanded: function (object) {
                },
                onChanged: function (object) {
                },
                onClosed: function () {
                }
            });
        }
    }

    // Toggle desktop menu nav for Leadership page:

    $(".directorsDesktopNav").on('click', function() {
        $(".executiveDesktopNav").removeClass('active');
        $(".directorsDesktopNav").addClass('active');
        $(".directorsHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
        $(".executiveHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
        $(".executiveDesc").hide();
        $(".directorsDesc").show();
        $(".executiveTeam").fadeOut('slow', function() {
            $(".directors").fadeIn('slow');
        });
        directorsOpen = true;
        executiveTeamOpen = false;
        return false;
    });

    $(".executiveDesktopNav").on('click', function() {
        $(".directorsDesktopNav").removeClass('active');
        $(".executiveDesktopNav").addClass('active');
        $(".executiveHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
        $(".directorsHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
        $(".directorsDesc").hide();
        $(".executiveDesc").show();
        $(".directors").fadeOut('slow', function() {
            $(".executiveTeam").fadeIn('slow');
        });
        executiveTeamOpen = true;
        directorsOpen = false;
        return false;
    });


    // Toggle mobile menu nav for Leadership page:

    $(".executiveHeader").on('click', function(event) {
        //$('.gridder-show').hide();
        if (!executiveTeamOpen) {
            executiveTeamOpen = true;
            $(this).find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
            $(".directorsDesktopNav").removeClass('active');
            $(".executiveDesktopNav").addClass('active');
            $(".directorsDesc").hide();
            $(".executiveDesc").show();
            $(".executiveTeam").slideDown('slow');
        } else {
            $(this).find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
            if (!directorsOpen) {
                $(".directorsDesc").hide();
                $(".executiveDesc").show();   
            } else {
                $(".executiveDesc").hide();
                $(".directorsDesc").show();   
            }
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
            $(".executiveDesc").hide();
            $(".directorsDesc").show();
            $(".directors").slideDown('slow');
        } else {
            $(this).find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down'); 
            $(".directorsDesc").hide();
            $(".executiveDesc").show();
            $(".directors").slideUp('slow'); 
            directorsOpen = false;  
        }
        return false;
    });

    if (executiveTeamOpen && directorsOpen) {
        $(".directorsDesc").hide();
        $(".executiveDesc").show();
        $('.directors').hide();
        $(".executiveTeam").show();
        executiveTeamOpen = true;
        directorsOpen = false; 
    };
});

// Adjust header logo based on desktop or mobile view
    function adjustLeadership() {
        if ( $(window).width() < 768) {
        	isMobileView = true;
            if (executiveTeamOpen && !directorsOpen) {
                $(".executiveHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
                $(".directorsHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
                $(".directorsDesktopNav").removeClass('active');
                $(".executiveDesktopNav").addClass('active');
                $(".directorsDesc").hide();
                $(".executiveDesc").show();
                $(".executiveTeam").show();
            } else {
                $(".executiveHeader").find('i').removeClass('glyphicon-menu-up').addClass('glyphicon-menu-down');
                $(".directorsDesktopNav").removeClass('active');
                $(".executiveDesktopNav").addClass('active');
                $(".directorsDesc").hide();
                $(".executiveDesc").show();
                $(".executiveTeam").hide();
            }
            if (directorsOpen && !executiveTeamOpen) {
                $(".directorsHeader").find('i').removeClass('glyphicon-menu-down').addClass('glyphicon-menu-up');
                $(".executiveDesktopNav").removeClass('active');
                $(".directorsDesktopNav").addClass('active'); 
                $(".executiveDesc").hide();
                $(".directorsDesc").show();
                $(".directors").show();
            }

        } else if( $(window).width() > 768 ) {
        	isMobileView = false;
            $(".directorsDesktopNav").removeClass('active');
            $(".executiveDesktopNav").addClass('active');
            $('.directorsDesc').hide();
            $('.directors').hide();
            if (executiveTeamOpen && directorsOpen) {
                $(".executiveDesc").show();
                $(".executiveTeam").show();
                executiveTeamOpen = true;
                directorsOpen = false;
            }
            if (executiveTeamOpen) {
                $(".executiveDesc").show();
                $(".executiveTeam").show();
                executiveTeamOpen = true;
                directorsOpen = false;
            }
            if (!executiveTeamOpen && !directorsOpen) {
                $(".executiveDesc").show();
                $(".executiveTeam").show();
                executiveTeamOpen = true;
                directorsOpen = false;
            }
            if (!executiveTeamOpen && directorsOpen) {
                $(".executiveDesktopNav").removeClass('active');
                $(".directorsDesktopNav").addClass('active');
                $(".executiveDesc").hide();
                $(".directorsDesc").show();
                $(".executiveTeam").hide();
                $(".directors").show();
                executiveTeamOpen = false;
                directorsOpen = true;
            }
        }
    }