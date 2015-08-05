var Toogle = {};


Toogle.manageFields = function manageFields(field) {

    //Get the panel of the tab our drop down menu on
    var panel = field.findParentByType('panel');

    // Our text fields are stored in widgets of type dialogfieldset
    // and we get their reference 
    var fieldSets = panel.findByType('dialogfieldset');
    var fLength = fieldSets.length;

    // Get value of selected option in out select box
    var fieldValue = field.getValue();
    for (var i = 0; i < fLength; i++) {
        var fieldSet = fieldSets[i];

        // Values of options in our drop down menu correspond to
        // respective properties in dialogfieldsets we will set next,
        // so if value of selected options matches itemId we show this widget,
        // otherwise - hide.
        if (fieldValue === fieldSet.getItemId()) {
            fieldSet.show();
        } else {
            fieldSet.hide();
        }
    }
}

var groupFieldSet1 = ['image1', 'video1', 'videolibrary1', 'event1', 'news1', 'pRelease1', 'speaker1', 'image2', 'video2', 'videolibrary2', 'event2', 'news2', 'pRelease2', 'speaker2', 'eventS1', 'newsS1', 'pReleaseS1', 'speakerS1', 'twitterS1', 'eventS2', 'newsS2', 'pReleaseS2', 'speakerS2', 'twitterS2', 'eventS3', 'newsS3', 'pReleaseS3', 'speakerS3', 'twitterS3'];
var groupSelects1 = ['featureType1', 'featureType2', 'spotlight1', 'spotlight2', 'spotlight3'];

function init() {

    var elements = document.getElementsByClassName("coral-TabPanel-tab");
    for (var i = 0; i < elements.length; i++) {
        var tab = elements[i];

        var aria = tab.getAttribute("aria-controls");

        if (aria == "feature1Tab" || aria == "feature2Tab" || aria == "spotlight1Tab" || aria == "spotlight2Tab" || aria == "spotlight3Tab") {

            // trigger event value change for select box.
            fireEventSelectChange();
            // trigger event click for tab panel.
            tab.addEventListener("click", function() {
                for (var i = 0; i < groupSelects1.length; i++) {
                    var selects = document.getElementsByName("./" + groupSelects1[i]);
                    try{

                        var id = selects[0].value;

                    }catch(e){
                        console.log("can not found this item" + groupSelects1[i]);
                    }
                    // load field set with type of content which selected by user(images, video, event, news...etc)
                    toogleField(id);
                }
            });

        }

    }
}

/*
 * Function fire event select value change for drop down list.
 *
 */
function fireEventSelectChange() {
    var selects = document.getElementsByClassName("coral-SelectList-item");
    for (var i = 0; i < selects.length; i++) {

        var select = selects[i];
        select.addEventListener("click", function(select) {
            var value = this.getAttribute("data-value");
            toogleField(value);

        });
    }

}

function addEvent(element, eventType, eventName) {
    element.addEventListener(eventType, eventName);
}



function toogleField(id) {

    var index = groupFieldSet1.indexOf(id);
    var currentFieldSet = document.getElementById(id);
    currentFieldSet.style.display = '';

    // group field set for tab Feature1 0-6
    if (index >= 0 && index < 7) {
        for (var i = 0; i < 7; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet1[i]);
                element.style.display = "none";
            }
        }
        // group field set for tab Feature2 7-13
    } else if (index >= 7 && index < 14) {
        for (var i = 7; i < 14; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet1[i]);
                element.style.display = "none";
            }
        }
        // group field set for tab Spotlight1 14-18
    } else if (index >= 14 && index < 19) {
        for (var i = 14; i < 19; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet1[i]);
                element.style.display = "none";
            }
        }
        // group field set for tab Spotlight2
    } else if (index >= 19 && index < 25) {
        for (var i = 19; i < 25; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet1[i]);
                element.style.display = "none";
            }
        }
        // group field set for tab Spotlight3
    } else if (index >= 24 && index < 29) {
        for (var i = 24; i < 29; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet1[i]);
                element.style.display = "none";
            }
        }
    }


}

// Trigger event onload for cq dialog.
(function($, $document) {
    "use strict";
    $document.on("dialog-ready", function() {
        init();
    });
})($, $(document));