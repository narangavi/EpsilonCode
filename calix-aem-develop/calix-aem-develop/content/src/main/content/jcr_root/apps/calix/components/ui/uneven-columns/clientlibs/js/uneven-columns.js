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

var unevenGroupFieldSet = ['image', 'video', 'videolibrary'];
var unevenGroupSelects =  ['type'];

function unevenInit() {
    
    var elements = document.getElementsByClassName("coral-TabPanel-tab");
    for (var i = 0; i < elements.length; i++) {
        var tab = elements[i];

        var aria = tab.getAttribute("aria-controls");
        console.log(aria);

        if (aria == "imageVideoTab") {
            
            // trigger event value change for select box.
            unevenFireEventSelectChange();
            // trigger event click for tab panel. 
            tab.addEventListener("click", function() {

                for (var i = 0; i < unevenGroupSelects.length; i++) {
                    var selects = document.getElementsByName("./" + unevenGroupSelects[i]);
                    var id = selects[0].value;
                    // load field set with type of content which selected by user(images, video, event, news...etc)
                    unevenToogleField(id);
                }
            });

        }

    }
}

/*
* Function fire event select value change for drop down list.
*
*/
function unevenFireEventSelectChange() {
    var selects = document.getElementsByClassName("coral-SelectList-item");
    for (var i = 0; i < selects.length; i++) {

        var select = selects[i];
        select.addEventListener("click", function(select) {
            var value = this.getAttribute("data-value");
            unevenToogleField(value);

        });
    }

}

function addEvent(element, eventType, eventName) {
    element.addEventListener(eventType, eventName);
}



function unevenToogleField(id) {
    
    var index = unevenGroupFieldSet.indexOf(id);
    var currentFieldSet = document.getElementById(id);
    currentFieldSet.style.display = '';
     // group field set for tab Column1
    if (index >= 0 && index < 3) {
        for (var i = 0; i < 3; i++) {
            if (i != index) {
                var element = document.getElementById(unevenGroupFieldSet[i]);
                element.style.display = "none";
            }
        }
    // group field set for tab Cloumn2
    } else if (index >= 3 && index < 6) {
        for (var i = 3; i < 6; i++) {
            if (i != index) {
                var element = document.getElementById(unevenGroupFieldSet[i]);
                element.style.display = "none";
            }
        }
    } 
}

// Trigger event onload for cq dialog.
(function($, $document) {
    "use strict";
    $document.on("dialog-ready", function() {
        unevenInit();
    });
})($, $(document));
