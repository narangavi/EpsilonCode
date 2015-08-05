$(document).ready(function(){
    //your code

    $('#pressNewsPDF').click(function () {
        var title = $('h2').text( );
        var description = $(".pressReleaseNewsDescription").text();
        var image = $(".img-responsive").attr("src");
        var content = $(".pressReleaseNewsContent").text();

        callServer_saveAsPDF(title,description,image,content);
    });

    function callServer_saveAsPDF(title,description,image,content){
        $.ajax({
            url: "/bin/calixservice/pdfservlet",
            data:{
                title : title,
                description : description,
                image : image,
                content   :  content
            },
            type : "POST",
            async: false,
            success : function(data) {
                var hostname = window.location.host;
                window.open("http://"+hostname + "/content/dam/calix/pdf/pressNewRelease");
                location.reload();
            }
        });
    }

});