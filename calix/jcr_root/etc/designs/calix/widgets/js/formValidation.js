(function (document, $, ns) {
	"use strict";

	$(document).on("click", ".cq-dialog-submit", function (e) {
		e.stopPropagation();
		e.preventDefault();
		var $form = $(this).closest("form.foundation-form");
		var isFormValid = isFormValid($form);

		if(!isFormValid){
			showRequiredMessage();
		}

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
				$ele.addClass("is-invalid");
				return false;
			}else{
				return isFormValid;
			}
		}

		function showRequiredMessage() {
			ns.ui.helpers.prompt({
				title: Granite.I18n.get("Invalid Input"),
				message: "Please check error fields",
				actions: [{
					id: "CANCEL",
					text: "CANCEL",
					className: "coral-Button"
				}],
				callback: function (actionId) {
					if (actionId === "CANCEL") {
					}
				}
			});
		}
	});
})(document, Granite.$, Granite.author);
