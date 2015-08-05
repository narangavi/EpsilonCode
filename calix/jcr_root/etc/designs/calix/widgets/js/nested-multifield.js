(function () {
    var DATA_EAEM_NESTED = "data-eaem-nested";
    var CFFW = ".coral-Form-fieldwrapper";
    var nestedfieldname;
    //reads multifield data from server, creates the nested composite multifields and fills them
    var addDataInFields = function () {
        $(document).on("dialog-ready", function() {
            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");

            if(_.isEmpty($fieldSets)){
                return;
            }

            var mNames = [];

            $fieldSets.each(function (i, fieldSet) {
                mNames.push($(fieldSet).data("name"));
            });

            mNames = _.uniq(mNames);

            var actionUrl = $fieldSets.closest("form.foundation-form").attr("action") + ".json";

            $.ajax(actionUrl).done(postProcess);

            function postProcess(data){
                _.each(mNames, function(mName){
                    buildMultiField(data, mName);
                });
            }

            //creates & fills the nested multifield with data
            function fillNestedFields($multifield, valueArr){
                _.each(valueArr, function(record, index){
                    $multifield.find(".js-coral-Multifield-add").click();

                    //a setTimeout may be needed
                    _.each(record, function(value, key){
                        var $field = $($multifield.find("[name='./" + key + "']")[index]);
                        //fix for checkbox, selectbox
                        var currentSelectText = '';
                        if($field.is(':checkbox')){
                            if(value=='true'){
                                $field.prop('checked', true);
                            }else{
                                $field.prop('checked', false);
                            }
                        }else if($field.is('select')){
                            $field.val(value);
                            $field.parent().find(".coral-SelectList .coral-SelectList-item").each(function() {
                                if(rValue==$( this ).attr("data-value")){
                                    currentSelectText = $(this).text();
                                    $field.parent().find(".coral-Select-button .coral-Select-button-text").text(currentSelectText);
                                    return false;
                                }
                            });
                        }else{
                            $field.val(value);
                        }
                    })
                })
            }

            function buildMultiField(data, mName){
                if(_.isEmpty(mName)){
                    return;
                }
                var fieldSets = document.getElementsByClassName("coral-Form-fieldset");
                
              
                	if (!!fieldSets && fieldSets.length > 0){
                		var fieldSet = fieldSets[1];
                    	nestedfieldname = fieldSet.getAttribute("data-name");
                	}
                	
                
                
                $fieldSets = $("[data-name='" + mName + "']");

                //strip ./
                mName = mName.substring(2);

                var mValues = data[mName], $field, name;

                if(_.isString(mValues)){
                    mValues = [ JSON.parse(mValues) ];
                }

                _.each(mValues, function (record, i) {
                    if (!record) {
                        return;
                    }

                    if(_.isString(record)){
                        record = JSON.parse(record);
                    }

                    _.each(record, function(rValue, rKey){
                        $field = $($fieldSets[i]).find("[name='./" + rKey + "']");
                        if(_.isArray(rValue) && !_.isEmpty(rValue)){
                            fillNestedFields( $($fieldSets[i]).find("[data-init='multifield']"), rValue);
                        }else{
                            //fix for checkbox, selectbox
                            var currentSelectText = '';
                            if($field.is(':checkbox')){
                                if(rValue=='true'){
                                    $field.prop('checked', true);
                                }else{
                                    $field.prop('checked', false);
                                }
                            }else if($field.is('select')){
                                $field.val(rValue);
                                $field.parent().find(".coral-SelectList .coral-SelectList-item").each(function() {
                                    if(rValue==$( this ).attr("data-value")){
                                        currentSelectText = $(this).text();
                                        $field.parent().find(".coral-Select-button .coral-Select-button-text").text(currentSelectText);
                                        return false;
                                    }
                                });
                            }else{
                                $field.val(rValue);
                            }
                        }
                    });
                });
            }
        });
    };
    var fillValue = function($field, record, isSubmit){
        var name = $field.attr("name");

        if (!name) {
            return;
        }

        //strip ./
        if (name.indexOf("./") == 0) {
            name = name.substring(2);
        }

        //fix for checkbox
        if($field.is(':checkbox')){
            if($field.is(":checked")){
                $field.val(true);
            }else{
                $field.val(false);
            }
        }

        record[name] = $field.val();
        //remove the field, so that individual values are not POSTed
        if(isSubmit){
            $field.remove();
        }
    };

    //for getting the nested multifield data as js objects
    var getRecordFromMultiField = function($multifield, isSubmit){
        var $fieldSets = $multifield.find("[class='coral-Form-fieldset']");

        var records = [], record, $fields, name;

        $fieldSets.each(function (i, fieldSet) {
            $fields = $(fieldSet).find("[name]");
            record = {};

            $fields.each(function (j, field) {
                fillValue($(field), record, isSubmit);
            });
            if(!$.isEmptyObject(record)){
                records.push(record);
            }
        });
        return records;
    };

    //collect data from widgets in multifield and POST them to CRX as JSON
    var collectDataFromFields = function(){
        $(document).on("click", ".cq-dialog-submit", function () {


            function isFormValid($form) {
                var requiredFields = $form.find("[data-isrequired='true']");
                var maxlengthFields = $(".character-count");
                var isFormValid = true;
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
            var clear = false;
            var $form = $(this).closest("form.foundation-form");
            if(!isFormValid($form)){
                isSubmit = false;
            }

            var mName = $("[" + DATA_EAEM_NESTED + "]");
            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");

            var record, $fields, $field, name, $nestedMultiField;
            if($fieldSets.length <= 0){
            	$("<section class='coral-Form-fieldset' data-eaem-nested></section>").appendTo($form);
            	  $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");
            	  clear = true;
            }
            $fieldSets.each(function (i, fieldSet) {
                $fields = $(fieldSet).children().children(CFFW);

                record = {};
                $fields.each(function (j, field) {
                    $field = $(field);

                    //may be a nested multifield
                    $nestedMultiField = $field.find("[data-init='multifield']");
                    if($nestedMultiField.length == 0){
                        fillValue($field.find("[name]"), record, isSubmit);
                    }else{
                        name = $nestedMultiField.find("[class='coral-Form-fieldset']").data("name");
                        if(!name){
                            return;
                        }

                        //strip ./
                        name = name.substring(2);

                        record[name] = getRecordFromMultiField($nestedMultiField, isSubmit);
                    }
                });

                if ($.isEmptyObject(record)) {
                	if (!clear){
                        return;
                	}
                	
                }
                if(isSubmit){
               	 //add the record JSON in a hidden field as string
                	if (!!clear){
                		$("input[type='hidden']").remove();
                		$('<input />').attr('type', 'hidden')
                        .attr('name', nestedfieldname)
                        .attr('value', '')
                        .appendTo($form);
                	} else {
                		$('<input />').attr('type', 'hidden')
                        .attr('name', $(mName[i]).data("name"))
                        .attr('value', JSON.stringify(record))
                        .appendTo($form);
                	}
                }
               
            });
            if(isSubmit){
                $form.submit();
            }
        });
    };

    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });

    //extend otb multifield for adjusting event propagation when there are nested multifields
    //for working around the nested multifield add and reorder
    CUI.Multifield = new Class({
        toString: "Multifield",
        extend: CUI.Multifield,

        construct: function (options) {
            this.script = this.$element.find(".js-coral-Multifield-input-template:last");
        },

        _addListeners: function () {
            this.superClass._addListeners.call(this);

            //otb coral event handler is added on selector .js-coral-Multifield-add
            //any nested multifield add click events are propagated to the parent multifield
            //to prevent adding a new composite field in both nested multifield and parent multifield
            //when user clicks on add of nested multifield, stop the event propagation to parent multifield
            this.$element.on("click", ".js-coral-Multifield-add", function (e) {
                e.stopPropagation();
            });

            this.$element.on("drop", function (e) {
                e.stopPropagation();
            });
        }
    });

    CUI.Widget.registry.register("multifield", CUI.Multifield);
})();