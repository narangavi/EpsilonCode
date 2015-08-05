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

var groupFieldSet = ['text1', 'image1', 'video1', 'videos1', 'text2', 'image2', 'video2', 'videos2'];
var groupSelects =  ['type1', 'type2'];

function genericInit() {
    
    var elements = document.getElementsByClassName("coral-TabPanel-tab");
    for (var i = 0; i < elements.length; i++) {
        var tab = elements[i];

        var aria = tab.getAttribute("aria-controls");
        console.log(aria);

        if (aria == "genericTab1" || aria == "genericTab2") {
            
            // trigger event value change for select box.
            genericFireEventSelectChange();
            // trigger event click for tab panel. 
            tab.addEventListener("click", function() {

                for (var i = 0; i < groupSelects.length; i++) {
                    var selects = document.getElementsByName("./" + groupSelects[i]);
                    var id = selects[0].value;
                    // load field set with type of content which selected by user(images, video, event, news...etc)
                    genericToogleField(id);
                }
            });

        }

    }
}

/*
* Function fire event select value change for drop down list.
*
*/
function genericFireEventSelectChange() {
    var selects = document.getElementsByClassName("coral-SelectList-item");
    for (var i = 0; i < selects.length; i++) {

        var select = selects[i];
        select.addEventListener("click", function(select) {
            var value = this.getAttribute("data-value");
            genericToogleField(value);

        });
    }

}

function addEvent(element, eventType, eventName) {
    element.addEventListener(eventType, eventName);
}



function genericToogleField(id) {
    
    var index = groupFieldSet.indexOf(id);
    var currentFieldSet = document.getElementById(id);
    currentFieldSet.style.display = '';
     // group field set for tab Column1
	if (index >= 0 && index < 4) {
	    for (var i = 0; i < 4; i++) {
	        if (i != index) {
	            var element = document.getElementById(groupFieldSet[i]);
	            element.style.display = "none";
	        }
	    }
	// group field set for tab Cloumn2
	} else if (index >= 4 && index < 8) {
	    for (var i = 4; i < 8; i++) {
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
        genericInit();
    });
})($, $(document));