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

var groupFieldSet = ['image1', 'video1', 'event1', 'news1', 'pRelease1', 'speaker1', 'image2', 'video2', 'event2', 'news2', 'pRelease2', 'speaker2', 'eventS1', 'newsS1', 'pReleaseS1', 'speakerS1', 'twitterS1', 'eventS2', 'newsS2', 'pReleaseS2', 'speakerS2', 'twitterS2', 'eventS3', 'newsS3', 'pReleaseS3', 'speakerS3', 'twitterS3'];
var groupSelects = ['featureType1', 'featureType2', 'spotlight1', 'spotlight2', 'spotlight3'];

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
                for (var i = 0; i < groupSelects.length; i++) {
                    var selects = document.getElementsByName("./" + groupSelects[i]);
                    var id = selects[0].value;
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
	
    var index = groupFieldSet.indexOf(id);
    var currentFieldSet = document.getElementById(id);
    currentFieldSet.style.display = '';
	// group field set for tab Feature1
    if (index >= 0 && index < 6) {
        for (var i = 0; i < 6; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet[i]);
                element.style.display = "none";
            }
        }
	// group field set for tab Feature2
    } else if (index >= 6 && index < 12) {
        for (var i = 6; i < 12; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet[i]);
                element.style.display = "none";
            }
        }
	// group field set for tab Spotlight1
    } else if (index >= 12 && index < 17) {
        for (var i = 12; i < 17; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet[i]);
                element.style.display = "none";
            }
        }
	// group field set for tab Spotlight2
    } else if (index >= 17 && index < 22) {
        for (var i = 17; i < 22; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet[i]);
                element.style.display = "none";
            }
        }
	// group field set for tab Spotlight3	
    } else if (index >= 22 && index < 27) {
        for (var i = 22; i < 27; i++) {
            if (i != index) {
                var element = document.getElementById(groupFieldSet[i]);
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