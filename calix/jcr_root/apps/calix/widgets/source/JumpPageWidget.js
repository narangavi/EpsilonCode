/**
 * @class CQ.Ext.form.JumpPageWidget
 * @extends CQ.Ext.form.TriggerField Provides some fields for JumpPage Widget
 * @constructor Create a new Page
 * @param {Object} config
 * @xtype JumpPageWidget
 */
CQ.Ext.form.JumpPageWidget = CQ.Ext.extend(CQ.Ext.form.TriggerField, {
	JumpPageWidgetDialog : null,
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
		CQ.Ext.form.JumpPageWidget.superclass.constructor.call(this,config);
		this.clearInvalid();
	},
	initComponent : function() {
		CQ.Ext.form.JumpPageWidget.superclass.initComponent.call(this);
	},
	// private
	onDestroy : function() {
		if (this.JumpPageWidgetDialog) {
			this.JumpPageWidgetDialog.destroy();
		}
		CQ.Ext.form.JumpPageWidget.superclass.onDestroy.call(this);
	},

	/**
	 * @method onTriggerClick
	 * @hideheadline
	 */
	onTriggerClick : function() {
		var parentDialog = this.findParentByType("dialog");

		var titleName = "title";
		var pathName= "path";
		var tabName = "mainTab";

		var titleValue, pathValue;
		var fieldValues = this.getValue();

		if (fieldValues.length != 0) {
			var tool = jQuery.parseJSON(fieldValues);
			titleName = tool.title;
			pathValue = tool.path;
		}
		// lazy creation of browse dialog
		if (this.keyItemDialog == null) {
			function okHandler() {
				titleValue = this.getField(titleName).getValue();
				pathValue = this.getField(pathName).getValue();

				if(titleValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Title fields is empty');
					return false;
				}
				this.JumpPageWidgetField.setValue('{"' + titleName + '":"' + titleValue
					+ '","' + pathName + '":"' + pathValue
					+ '"}');

				this.JumpPageWidgetField.fireEvent("change");
				this.hide();
			}

			var typeSelectionchanged = function(box,value){
				box.findParentByType('tabpanel').manageTabs(value);
			};

			var JumpPageWidgetDialogCfg = {
				'width' : 550,
				'height' : 550,
				'ok' : okHandler,
				'title' : 'JumpPage Widget',
				'JumpPageWidgetField' : this,
				'modal' : 'true',
				'items' : {
					'xtype' : 'tabpanel',
					'id' : tabName,
					'items' : [ {
						'title' : "Jump Pages",
						'xtype' : 'panel',
						'items' : [ {
							'fieldLabel' : 'Title',
							'labelStyle' : 'font-weight:bold;',
							'name' : titleName,
							'value' : titleValue,
							'xtype' : 'textfield'
						},{
							'fieldLabel' : 'Path',
							'labelStyle' : 'font-weight:bold;',
							'name' : pathName,
							'value' : pathValue,
							'rootPath' : '/content/calix',
							'xtype' : 'pathfield'
						}]
					} ]
				}
			};
			JumpPageWidgetDialogCfg.buttons = CQ.Dialog.OKCANCEL;
			this.JumpPageWidgetDialog = new CQ.Dialog(JumpPageWidgetDialogCfg);
		} else {
			var titleLabel = this.JumpPageWidgetDialog.getField(titleName);
			titleLabel.setValue(titleValue);
			var pathLabel = this.JumpPageWidgetDialog.getField(pathName);
			pathLabel.setValue(pathValue);
		}
		this.JumpPageWidgetDialog.show();
	}
});
CQ.Ext.reg('jumppagewidget', CQ.Ext.form.JumpPageWidget);
