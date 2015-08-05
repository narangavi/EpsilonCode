var tempScrollTop,
    isHideTab1,
    isHideTab4,
    isHideTab5;

$(document).ready(function() {
    hideNoDataText_eventsAndSpeakers();
    if ( $('.gridView > .grid-item').length == 0 ) {
        showNoDataText_eventsAndSpeakers();
        $(".eventsAndSpeakers #loadmore").hide();
    }

    $(".eventsAndSpeakers #loadmore").click(function() {
        $('.iconLoadingTab').css('display','block');
        loadMore_eventsAndSpeakers();
    });

    $(".eventsAndSpeakers li[id^='tab']").each(function(index) {
        // trigger event click on tab
        $(this).click(function() {
            var contentId = $(this).attr('content-id');
            var tabId = $(this).attr('id');
            showContent_eventsAndSpeakers(contentId, tabId);
            $(".subNavMobile.dropdown button").html($(this).find("a").text() + '<span class="caret"></span>');
        });

    });
    refeshGrid_eventsAndSpeakers();
    eventTabAction_eventsAndSpeakers();
});

function eventTabAction_eventsAndSpeakers() {
    $(".eventsAndSpeakers div .eventTab").each(function(index) {
        // trigger event click on label
        $(this).on('click', function() {
            var contentId = $(this).attr('content-id');
            var tabId = $(this).attr('tab-id');
            var num = tabId.split("tab")[1];
            if(num != "-1"){
                showContent_eventsAndSpeakers(contentId, tabId);
                $(".subNavMobile.dropdown button").html($(this).text() + '<span class="caret"></span>');
            }
        });
        
    });
}

function loadMore_eventsAndSpeakers(){
    tempScrollTop = $(window).scrollTop();
    var currentTab = getTabcontentId_eventsAndSpeakers();
    var count = 0;
    var to = parseInt(limitItems);
    $(".eventsAndSpeakers div[id^='"+currentTab+"'] .grid-item").each(function(index) {
        count++;
    });
    if (currentTab === "tabContent_1") {
        callServer_eventsAndSpeakers(eventsPath, speakersPath, count, to, calixEvent, calixSpeaker, currentTab);
    } else if (currentTab === "tabContent_4") {
        callServer_eventsAndSpeakers(eventsPath, "", count, to, calixEvent, "", currentTab);
    } else if (currentTab === "tabContent_5") {
        callServer_eventsAndSpeakers("", speakersPath, count, to, "", calixSpeaker, currentTab);
    }
    $('.iconLoadingTab').delay(800).hide(0);
}

function callServer_eventsAndSpeakers(path1, path2, from, to, type1, type2, id){
    $.ajax({
        url: "/bin/calix/servlets/loadmore",
        data:{
            path1 : path1,
            path2 : path2,
            from : from,
            to   :  to,
            type1 : type1,
            type2 : type2,
            isPressAndNews : "false"
        },
        type : "POST",
        async: false,
        success : function(data) {
            generateHTML_eventsAndSpeakers(data,id);
            if (data.articles.length < to) {
                $(".eventsAndSpeakers #loadmore").hide();
                if (id === "tabContent_1") {
                    isHideTab1 = true;
                } else if (id === "tabContent_4") {
                    isHideTab4 = true;
                } else if (id === "tabContent_5") {
                    isHideTab5 = true;
                }
            }
        }
    });
}
    
function generateHTML_eventsAndSpeakers(data, id){
    var html ="";
    for (var i=0; i< data.articles.length; i++) {
        var article = data.articles[i];

        html += "<div class='col-sm-6 col-xs-12 grid-item";
        if (article.headshot != undefined && article.headshot != "") {
            html += " speakerModule ";
        } else if (article.image != undefined && article.image != "") {
            html += " videoModule ";
        } else {
            html += " eventModule ";
        }
        html += "'>";
        html += "<div class='eventTab' tab-id='tab"+ article.TabId +"' content-id='tabContent_" + article.TabId + "' style='cursor:pointer;'>";
        html += "<span>"+ article.type + "</span>";
        html += "</div>";
        html += "<a href='"+ article.link+ "'>";
        if(article.image != undefined && article.image != ""){
            html += "<img src='"+ article.image+"' width='100%' height='auto' alt='' />";
        }
        html += "<div class='description'>";
        if (article.headshot != undefined && article.headshot != "") {
            html += "<img class='bio' src='"+ article.headshot +"'/>";
        }
        html += "<h3>"+ article.date;
        if (article.speakerName != undefined && article.speakerName != "") {
            html += " | "+ article.speakerName;
        }
        html += "</h3>";
        html += "<h2>" + article.headLine +"</h2>";
        if (article.subHead != undefined && article.subHead != ""){
            html += "<p>" + article.subHead + "</p>";
        }
        if (article.eventLocation != undefined && article.eventLocation != ""){
            html += "<p class='location'>" + article.eventLocation + "</p>";
        }
        html += "</div></a></div>";
    }
     
    $(".eventsAndSpeakers #" + id).append(html);
    eventTabAction_eventsAndSpeakers();
    refeshGrid_eventsAndSpeakers();
    $(window).scrollTop(tempScrollTop);
}

function getTabcontentId_eventsAndSpeakers(){
    var id = -1;
    $(".eventsAndSpeakers li[id^='tab'] a").each(function(index) {
        // trigger event click on tab
        if($(this).hasClass('active')){
            id= $(this).parent().attr('content-id');
        }
    });
    return id;
}

function showContent_eventsAndSpeakers(contentId, tabId) {
    var hideByNoData = false;
    // show content for current tab
    $(".eventsAndSpeakers div[id^='tabContent_']").each(function(index) {
        var id = $(this).attr('id');
        if (id == contentId) {
            $(this).show();
            if ( $(this).children().length == 0 ) {
                showNoDataText_eventsAndSpeakers();
                $(".eventsAndSpeakers #loadmore").hide();
                hideByNoData = true;
            } else {
                hideNoDataText_eventsAndSpeakers();
            }
        } else {
            $(this).hide();
        }
        
    });
    
    //remove all active class
    $(".eventsAndSpeakers ul li a").each(function(index) {
        $(this).removeClass('active');
    });
    
    // set active class for current tab
    $("#" + tabId + " a").attr('class', 'active');
    
    // show hide load more button
    if (!hideByNoData) {
        if ((!isHideTab1 && contentId === "tabContent_1") ||
                (!isHideTab4 && contentId === "tabContent_4") ||
                (!isHideTab5 && contentId === "tabContent_5")) {
            $(".eventsAndSpeakers #loadmore").show();
        } else {
            $(".eventsAndSpeakers #loadmore").hide();
        }
    }

    // check grid view mode
    refeshGrid_eventsAndSpeakers();
    
}

function refeshGrid_eventsAndSpeakers(){
    $(".eventsAndSpeakers").imagesLoaded( function() {
        $('div.grid').masonry().masonry( 'destroy' );
        $('div.grid').masonry().masonry(
            {
                itemSelector: '.grid-item',
                columnWidth: 500
            }
        )
    });
}

function showNoDataText_eventsAndSpeakers(){
    $(".eventsAndSpeakers #noDataText").show();
}

function hideNoDataText_eventsAndSpeakers(){
    $(".eventsAndSpeakers #noDataText").hide();
}