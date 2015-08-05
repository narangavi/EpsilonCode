/**
 * @class CQ.Ext.form.SlideWidget
 * @extends CQ.Ext.form.TriggerField Provides some fields for Slide Widget
 * @constructor Create a new Slide
 * @param {Object} config
 * @xtype SlideWidget
 */
CQ.Ext.form.SlideWidget = CQ.Ext.extend(CQ.Ext.form.TriggerField, {
	SlideWidgetDialog : null,
	"triggerClass" : "x-form-search-trigger",
	"readOnly" : false,
	updateEditState : function() {
		if (this.rendered) {
			if (this.readOnly) {
				this.el.dom.readOnly = true;
			} else {
				if (!this.editable) {
					this.el.dom.readOnly = true;
					this.el.addClass('x-trigger-noedit');
					this.mon(this.el, 'click', this.onTriggerClick, this);
				} else {
					this.el.dom.readOnly = false;
					this.el.removeClass('x-trigger-noedit');
					this.mun(this.el, 'click', this.onTriggerClick, this);
				}
				this.trigger.setDisplayed(!this.hideTrigger);
			}
			this.onResize(this.width || this.wrap.getWidth());
		}
	},
	constructor : function(config) {
		this.editorKernel = new CQ.form.rte.IFrameKernel(config);
		CQ.Ext.form.SlideWidget.superclass.constructor.call(this,config);
		this.clearInvalid();
	},
	initComponent : function() {
		CQ.Ext.form.SlideWidget.superclass.initComponent.call(this);
	},
	// private
	onDestroy : function() {
		if (this.SlideWidgetDialog) {
			this.SlideWidgetDialog.destroy();
		}
		CQ.Ext.form.SlideWidget.superclass.onDestroy.call(this);
	},

	/**
	 * @method onTriggerClick
	 * @hideheadline
	 */
	onTriggerClick : function() {
		var parentDialog = this.findParentByType("dialog");
		var pathImageName = "pathImage";
		var leadinName= "leadin";
		var headlineName = "headline";
		var subheadName = "subhead";
		var titleCTAName = "titleCTA";
		var linkCTAName = "linkCTA";
		var alignTypeName = "alignType";
		var buttonTypeName = "buttonType";
		var captionName = "caption";
		var tabName = "mainTab";

		var pathImageValue, leadinValue, headlineValue, subheadValue;
		var titleCTAValue, linkCTAValue;
		var alignTypeValue, buttonTypeValue, captionValue;

		var fieldValues = this.getValue();

		if (fieldValues.length != 0) {
			var tool = jQuery.parseJSON(fieldValues);
			pathImageValue = tool.pathImage;
			leadinValue = tool.leadin;
			headlineValue = tool.headline;
			subheadValue = tool.subhead;
			titleCTAValue = tool.titleCTA;
			linkCTAValue = tool.linkCTA;
			alignTypeValue = tool.alignType;
			buttonTypeValue = tool.buttonType;
			captionValue = tool.caption;
		}
		// lazy creation of browse dialog
		if (this.keyItemDialog == null) {
			function okHandler() {
				pathImageValue = this.getField(pathImageName).getValue();
				leadinValue = this.getField(leadinName).getValue();
				pathImageValue = this.getField(pathImageName).getValue();
				headlineValue = this.getField(headlineName).getValue();
				subheadValue = this.getField(subheadName).getValue();
				captionValue = this.getField(captionName).getValue();
				titleCTAValue = this.getField(titleCTAName).getValue();
				linkCTAValue = this.getField(linkCTAName).getValue();
				buttonTypeValue = this.getField(buttonTypeName).getValue();
				alignTypeValue = this.getField(alignTypeName).getValue();

				if(headlineValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Headline Text fields is empty');
					return false;
				}
				if("" != titleCTAValue && titleCTAValue.length > 10) {
					CQ.Ext.Msg.alert('Validation Failed', 'A maximum of 10 character is allowed for the text');
					return false;
				}
				this.SlideWidgetField.setValue('{"' + pathImageName + '":"' + pathImageValue
					+ '","' + leadinName + '":"' + leadinValue
					+ '","' + headlineName + '":"' + headlineValue
					+ '","' + subheadName + '":"' + subheadValue
					+ '","' + titleCTAName + '":"' + titleCTAValue
					+ '","' + linkCTAName + '":"' + linkCTAValue
					+ '","' + alignTypeName + '":"' + alignTypeValue
					+ '","' + buttonTypeName + '":"' + buttonTypeValue
					+ '","' + captionName + '":"' + captionValue
					+ '"}');

				this.SlideWidgetField.fireEvent("change");
				this.hide();
			}

			var typeSelectionchanged = function(box,value){
				box.findParentByType('tabpanel').manageTabs(value);
			};

			var SlideWidgetDialogCfg = {
				'width' : 550,
				'height' : 550,
				'ok' : okHandler,
				'title' : 'Slide Widget',
				'SlideWidgetField' : this,
				'modal' : 'true',
				'items' : {
					'xtype' : 'tabpanel',
					'id' : tabName,
					'items' : [ {
						'title' : "Hero Slides",
						'xtype' : 'panel',
						'items' : [ {
							'fieldLabel' : 'Image path',
							'labelStyle' : 'font-weight:bold;',
							'rootPath' : '/content/dam',
							'fieldDescription' : 'Provide path for Hero Image',
							'name' : pathImageName,
							'value' : pathImageValue,
							'xtype' : 'pathfield'
						},{
							'fieldLabel' : 'Hero Lead in Text',
							'labelStyle' : 'font-weight:bold;',
							'height' : 45,
							'name' : leadinName,
							'value' : leadinValue,
							'xtype' : 'textarea'
						},{
							'fieldLabel' : 'Headline Text',
							'labelStyle' : 'font-weight:bold;',
							'height' : 45,
							'name' : headlineName,
							'value' : headlineValue,
							'allowBlank' : false,
							'xtype' : 'textarea'
						},{
							'fieldLabel' : 'Subhead Text',
							'labelStyle' : 'font-weight:bold;',
							'height' : 45,
							'name' : subheadName,
							'value' : subheadValue,
							'xtype' : 'textarea'
						},{
							'fieldLabel' : 'CTA Button Text',
							'labelStyle' : 'font-weight:bold;',							
							'maxLength' : 20,
                            'fieldDescription' : 'A maximum of 20 character is allowed for the text',
							'name' : titleCTAName,
							'value' : titleCTAValue,
							'xtype' : 'textfield'
						},{
							'fieldLabel' : 'CTA Button URL',
							'labelStyle' : 'font-weight:bold;',
							'fieldDescription' : 'Can be internal link or external URL',
							'name' : linkCTAName,
							'value' : linkCTAValue,
							'xtype' : 'pathfield'
						},{
							'fieldLabel' : 'Text Alignment',
							'labelStyle' : 'font-weight:bold;',
							'fieldDescription' : 'Text alignment (for leadin, headline, subhead, and cta)',
							'name' : alignTypeName ,
							'value' : alignTypeValue,
							'xtype' : 'selection',
							'type' : 'select',
							'defaultValue' : 'center',
							'options' : [{
								'text' : 'Center',
								'value' : 'center'
							},{
								'text' : 'Left',
								'value' : 'Left'
							},{
								'text' : 'Right',
								'value' : 'right'}]
						},{
							'fieldLabel' : 'CTA Button Type',
							'labelStyle' : 'font-weight:bold;',
							'name' : buttonTypeName ,
							'value' : buttonTypeValue,
							'xtype' : 'selection',
							'type' : 'select',
							'defaultValue' : 'green',
							'options' : [{
								'text' : 'Primary Green',
								'value' : 'green'
							},{
								'text' : 'Primary White',
								'value' : 'white'
							},{
								'text' : 'Primary Outline',
								'value' : 'outline'
							},{
								'text' : 'Text Link',
								'value' : 'text'}]
						},{
							'fieldLabel' : 'Caption Text',
							'labelStyle' : 'font-weight:bold;',
							'height' : 45,
							'name' : captionName,
							'value' : captionValue,
							'xtype' : 'textarea'
						}]
					} ]
				}
			};
			SlideWidgetDialogCfg.buttons = CQ.Dialog.OKCANCEL;
			this.SlideWidgetDialog = new CQ.Dialog(SlideWidgetDialogCfg);
		} else {
			var pathImageLabel = this.SlideWidgetDialog.getField(pathImageName);
			pathImageLabel.setValue(pathImageValue);
			var leadinLabel = this.SlideWidgetDialog.getField(leadinName);
			leadinLabel.setValue(leadinValue);
			var headlineLabel = this.SlideWidgetDialog.getField(headlineName);
			headlineLabel.setValue(headlineValue);
			var subheadLabel = this.SlideWidgetDialog.getField(subheadName);
			subheadLabel.setValue(subheadValue);
			var titleCTALabel = this.SlideWidgetDialog.getField(titleCTAName);
			titleCTALabel.setValue(titleCTAValue);
			var linkCTALabel = this.SlideWidgetDialog.getField(linkCTAName);
			linkCTALabel.setValue(linkCTAValue);
			var alignTypeLabel = this.SlideWidgetDialog.getField(alignTypeName);
			alignTypeLabel.setValue(alignTypeValue);
			var buttonTypeLabel = this.SlideWidgetDialog.getField(buttonTypeName);
			buttonTypeLabel.setValue(buttonTypeValue);
			var captionLabel = this.SlideWidgetDialog.getField(captionName);
			captionLabel.setValue(captionValue);
		}
		this.SlideWidgetDialog.show();
	}
});
CQ.Ext.reg('slidewidget', CQ.Ext.form.SlideWidget);
