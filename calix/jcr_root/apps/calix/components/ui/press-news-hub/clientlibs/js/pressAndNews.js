var tempScrollTop,
    month = 0,
    year = 0,
    months = [ "January", "February", "March", "April", "May", "June", 
               "July", "August", "September", "October", "November", "December" ],
    isHideTab1,
    isHideTab2,
    isHideTab3;

$(document).ready(function() {
    hideNoDataText_pressAndNews();
    if ( $('.gridView > .grid-item').length == 0 ) {
        showNoDataText_pressAndNews();
        $(".pressAndNews #loadmore").hide();
    }

    $(".pressAndNews #loadmore").click(function() {
        $('.iconLoadingTab').css('display','block');
        loadMore_pressAndNews();
    });   

    $(".pressAndNews li[id^='tab']").each(function(index) {
        // trigger event click on tab
        $(this).click(function() {
            var contentId = $(this).attr('content-id');
            var tabId = $(this).attr('id');
            showContent_pressAndNews(contentId, tabId);
            $(".subNavMobile.dropdown button").html($(this).find("a").text() + '<span class="caret"></span>');
        });

    });
    refeshGrid_pressAndNews();
    eventTabAction_pressAndNews();
});

$(document).on("click", ".dateFilter", function(e) {
    month = $(".popover-content .form-inline .starting-month-dl").val();
    year = $(".popover-content .form-inline .starting-year-dl").val();
    $('.starting-month-dl > option:selected').attr("selected",null);
    $('.starting-month-dl > option[value="'+ month +'"]').attr('selected', true);
    $('.starting-year-dl > option:selected').attr("selected",null);
    $('.starting-year-dl > option[value="'+ year +'"]').attr('selected', true);
    $("[data-toggle=popover]").popover('hide');
    $(".showingMostRecentNav > a").html('Showing From ' + months[month-1] + " " + year + '<div class="arrow-down"></div>');
    dateFilter_pressAndNews();
});

$(document).on("click", ".mostRecent", function() {
    month = 0;
    year = 0;
    $('.starting-month-dl > option:selected').attr("selected",null);
    $('.starting-month-dl > option[value="'+ currentMonth +'"]').attr('selected', true);
    $('.starting-year-dl > option:selected').attr("selected",null);
    $('.starting-year-dl > option[value="'+ currentYear +'"]').attr('selected', true);
    $("[data-toggle=popover]").popover('hide');
    $(".showingMostRecentNav > a").html('Showing Most Recent<div class="arrow-down"></div>');
    dateFilter_pressAndNews();
});

function eventTabAction_pressAndNews() {
    $(".pressAndNews div .eventTab").each(function(index) {
        // trigger event click on label
        $(this).on('click', function() {
            var contentId = $(this).attr('content-id');
            var tabId = $(this).attr('tab-id');
            var num = tabId.split("tab")[1];
            if(num != "-1"){
                showContent_pressAndNews(contentId, tabId);
                $(".subNavMobile.dropdown button").html($(this).text() + '<span class="caret"></span>');
            }
        });
        
    });
}

function dateFilter_pressAndNews(){
    tempScrollTop = $(window).scrollTop();
    var count = 0;
    var to = parseInt(limitItems);
    $(".pressAndNews li[id^='tab'] a").each(function(index) {
        var currentTab= $(this).parent().attr('content-id');
        if (currentTab === "tabContent_1") {
            callServerByDate_pressAndNews(pressPath, newsPath, count, to, pressTagName, newsTagName, currentTab, month, year);
        } else if (currentTab === "tabContent_2") {
            callServerByDate_pressAndNews(pressPath, "", count, to, pressTagName, "", currentTab, month, year);
        } else if (currentTab === "tabContent_3") {
            callServerByDate_pressAndNews("", newsPath, count, to, "", newsTagName, currentTab, month, year);
        }
    });
}

function loadMore_pressAndNews(){
    tempScrollTop = $(window).scrollTop();
    var currentTab = getTabcontentId_pressAndNews();
    var count = 0;
    var to = parseInt(limitItems);
    $(".pressAndNews div[id^='"+currentTab+"'] .grid-item").each(function(index) {
        count++;
    });
    if (currentTab === "tabContent_1") {
        callServer_pressAndNews(pressPath, newsPath, count, to, pressTagName, newsTagName, currentTab);
    } else if (currentTab === "tabContent_2") {
        callServer_pressAndNews(pressPath, "", count, to, pressTagName, "", currentTab);
    } else if (currentTab === "tabContent_3") {
        callServer_pressAndNews("", newsPath, count, to, "", newsTagName, currentTab);
    }
    $('.iconLoadingTab').delay(800).hide(0);
}

function callServerByDate_pressAndNews(path1, path2, from, to, type1, type2, id, month, year){
    $.ajax({
        url: "/bin/calix/servlets/loadmore",
        data:{
            path1 : path1,
            path2 : path2,
            from : from,
            to   :  to,
            type1 : type1,
            type2 : type2,
            isPressAndNews : "true",
            month : month,
            year : year
        },
        type : "POST",
        async: false,
        success : function(data) {
        	if (data.articles != undefined) {
                generateHTML_dateFilter_pressAndNews(data, id);
                if (data.articles.length == 0) {
                    showNoDataText_pressAndNews();
                } else {
                    hideNoDataText_pressAndNews();
                }
                if (data.articles.length < to) {
                    $(".pressAndNews #loadmore").hide();
                    if (id === "tabContent_1") {
                        isHideTab1 = true;
                    } else if (id === "tabContent_2") {
                        isHideTab2 = true;
                    } else if (id === "tabContent_3") {
                        isHideTab3 = true;
                    }
                } else {
                    $(".pressAndNews #loadmore").show();
                    isHideTab1 = false;
                    isHideTab2 = false;
                    isHideTab3 = false;
                    
                }
        	}
        }
    });
}

function callServer_pressAndNews(path1, path2, from, to, type1, type2, id){
    $.ajax({
        url: "/bin/calix/servlets/loadmore",
        data:{
            path1 : path1,
            path2 : path2,
            from : from,
            to   :  to,
            type1 : type1,
            type2 : type2,
            isPressAndNews : "true",
            month : month,
            year : year
        },
        type : "POST",
        async: false,
        success : function(data) {
            generateHTML_pressAndNews(data,id);
            if (data.articles.length < to) {
                $(".pressAndNews #loadmore").hide();
                if (id === "tabContent_1") {
                    isHideTab1 = true;
                } else if (id === "tabContent_2") {
                    isHideTab2 = true;
                } else if (id === "tabContent_3") {
                    isHideTab3 = true;
                }
            }
        }
    });
}
    
function generateHTML_pressAndNews(data, id){
    var html = generateHTML(data);

    $(".pressAndNews #" + id).append(html);
    eventTabAction_pressAndNews();
    refeshGrid_pressAndNews();
    $(window).scrollTop(tempScrollTop);
}
    
function generateHTML_dateFilter_pressAndNews(data, id){
    var html = generateHTML(data);

    $(".pressAndNews #" + id).html(html);
    eventTabAction_pressAndNews();
    refeshGrid_pressAndNews();
    $(window).scrollTop(tempScrollTop);
}

function generateHTML(data) {
    var html ="";
    for (var i=0; i< data.articles.length; i++) {
        var article = data.articles[i];

        html += "<div class='col-sm-6 col-xs-12 grid-item";
        if (article.image != undefined && article.image != "") {
            html += " videoModule ";
        } else {
            html += " eventModule ";
        }
        html += "'>";
        html += "<div class='eventTab' tab-id='tab"+ article.TabId +"' content-id='tabContent_" + article.TabId + "' style='cursor:pointer;'>";
        html += "<span>"+ article.type + "</span>";
        html += "</div>";
        html += "<a href='"+ article.link+ "'>";
        if (article.image != undefined && article.image != ""){
            html += "<img src='"+ article.image+"' width='100%' height='auto' alt='' />";
        }
        html += "<div class='description'>";
        html += "<h3>"+ article.date;
        if (article.source != undefined && article.source != "") {
            html += " | "+ article.source;
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
    return html;
}

function getTabcontentId_pressAndNews(){
    var id = -1;
    $(".pressAndNews li[id^='tab'] a").each(function(index) {
        // trigger event click on tab
        if($(this).hasClass('active')){
            id= $(this).parent().attr('content-id');
        }
    });
    return id;
}

function showContent_pressAndNews(contentId, tabId) {
    var hideByNoData = false;
    // show content for current tab
    $(".pressAndNews div[id^='tabContent_']").each(function(index) {
        var id = $(this).attr('id');
        if (id == contentId) {
            $(this).show();
            if ( $(this).children().length == 0 ) {
                showNoDataText_pressAndNews();
                $(".pressAndNews #loadmore").hide();
                hideByNoData = true;
            } else {
                hideNoDataText_pressAndNews();
            }
        } else {
            $(this).hide();
        }

    });
    
    //remove all active class
    $(".pressAndNews ul li a").each(function(index) {
        $(this).removeClass('active');
    });

    // set active class for current tab
    $("#" + tabId + " a").attr('class', 'active');

    // show hide load more button
    if (!hideByNoData) {
        if ((!isHideTab1 && contentId === "tabContent_1") ||
                (!isHideTab2 && contentId === "tabContent_2") ||
                (!isHideTab3 && contentId === "tabContent_3")) {
            $(".pressAndNews #loadmore").show();
        } else {
            $(".pressAndNews #loadmore").hide();
        }
    }
    
    // check grid view mode
    refeshGrid_pressAndNews();
    
}

function refeshGrid_pressAndNews(){
    $(".pressAndNews").imagesLoaded( function() {
        $('div.grid').masonry().masonry( 'destroy' );
        $('div.grid').masonry().masonry(
            {
                itemSelector: '.grid-item',
                columnWidth: 500
            }
        )
    });
}

function showNoDataText_pressAndNews(){
    $(".pressAndNews #noDataText").show();
}

function hideNoDataText_pressAndNews(){
    $(".pressAndNews #noDataText").hide();
}