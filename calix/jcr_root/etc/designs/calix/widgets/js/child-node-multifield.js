(function () {
    var DATA_EAEM_NESTED = "data-eaem-nested";
    var CFFW = ".coral-Form-fieldwrapper";

    //reads multifield data from server, creates the nested composite multifields and fills them
    function addDataInFields() {
        function getMultiFieldNames($multifields){
            var mNames = {}, mName;

            $multifields.each(function (i, multifield) {
                mName = $($(multifield).find(".js-coral-Multifield-input-template").html()).data("name");

                if(_.isEmpty(mName)){
                    return;
                }

                mName = mName.substring(2);

                mNames[mName] = $(multifield);
            });

            return mNames;
        }

        function buildMultiField(data, $multifield, mName){
            if(_.isEmpty(mName) || _.isEmpty(data)){
                return;
            }

            _.each(data, function(value, key){
                if(key == "jcr:primaryType"){
                    return;
                }

                $multifield.find(".js-coral-Multifield-add").click();

                _.each(value, function(fValue, fKey){
                    if(fKey == "jcr:primaryType"){
                        return;
                    }

                    var $field = $multifield.find("[name='./" + fKey + "']").last();

                    if(_.isEmpty($field)){
                        return;
                    }

                    $field.val(fValue);
                });
            });
        }

        $(document).on("dialog-ready", function() {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($multifields)){
                return;
            }

            var mNames = getMultiFieldNames($multifields),
                $form = $(".cq-dialog"),
                actionUrl = $form.attr("action") + ".infinity.json";

            $.ajax(actionUrl).done(postProcess);

            function postProcess(data){
                _.each(mNames, function($multifield, mName){
                    buildMultiField(data[mName], $multifield, mName);
                });
            }
        });
    }
    //collect data from widgets in multifield and POST them to CRX
    function collectDataFromFields(){
        function fillValue($form, fieldSetName, $field, counter, isSubmit){
            var name = $field.attr("name");

            if (!name) {
                return;
            }

            //strip ./
            if (name.indexOf("./") == 0) {
                name = name.substring(2);
            }

            //remove the field, so that individual values are not POSTed
            if(isSubmit){
                $field.remove();
                 $('<input />').attr('type', 'hidden')
                .attr('name', fieldSetName + "/" + counter + "/" + name)
                .attr('value', $field.val())
                .appendTo($form);
            }
        }

        $(document).on("click", ".cq-dialog-submit", function () {
        	 function isFormValid($form) {
                 var requiredFields = $form.find("[data-isrequired='true']");
                 var maxlengthFields = $(".character-count");
                 var isFormValid = false;
                 // required field
                 if(requiredFields != null){
                     $(requiredFields).each(function() {
                         if($(this).is('input') || $(this).is('textarea')){
                             isFormValid = isEmpty(isFormValid, $(this));
                         }else{
                             // other type
                             // 1. pathbrowser
                             var dataInit = $(this).attr("data-init");
                             if(dataInit != null && dataInit=='pathbrowser'){
                                 isFormValid = isEmpty(isFormValid, $(this).find('input'));
                             }
                         }
                     });
                 }
                 
                 // maxLength
     			if(maxlengthFields != null){
     				$(maxlengthFields).each(function() {
     					var eleMaxLength = $(this).parent().find('[data-maxlength]');
     					var textLength = $(this).text();
                         var maxLimit = eleMaxLength.attr('data-maxlength');
                         var result = eleMaxLength.val().length + textLength;
                         if(textLength < 0){
     							$(eleMaxLength).addClass("is-invalid");
     							$(this).val(result);
     							isFormValid = false;
                         } else {
                             if(eleMaxLength.val().length>maxLimit){
                                 $(this).val(result);
                                 $(eleMaxLength).addClass("is-invalid");
                                 isFormValid = false;
     						}
                         } 

     				});
     			}

                 return isFormValid;
             }
             function isEmpty(isFormValid, $ele) {
                 if($ele == null){
                     return isFormValid;
                 }
                 if(!$ele.val()){
                     return false;
                 }else{
                     return isFormValid;
                 }
             }

             var isSubmit = true;
             var $form = $(this).closest("form.foundation-form");
             if(!isFormValid($form)){
                 isSubmit = false;
             }

            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($multifields)){
                return;
            }

            var $form = $(this).closest("form.foundation-form"),
                $fieldSets, $fields;

            $multifields.each(function(i, multifield){
                $fieldSets = $(multifield).find("[class='coral-Form-fieldset']");

                $fieldSets.each(function (counter, fieldSet) {
                    $fields = $(fieldSet).children().children(CFFW);

                    $fields.each(function (j, field) {
                        fillValue($form, $(fieldSet).data("name"), $(field).find("[name]"), (counter + 1), isSubmit);
                    });
                    if(isSubmit){
                    $('<input />').attr('type', 'hidden')
                        .attr('name', $(fieldSet).data("name") + "@Delete")
                        .attr('value', "true")
                        .appendTo($form);
                    }
                });
            });
        });
    }

    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });
})();