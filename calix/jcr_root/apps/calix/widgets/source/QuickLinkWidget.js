/**
 * @class CQ.Ext.form.TileWidget
 * @extends CQ.Ext.form.TriggerField Provides some fields for Tile Widget
 * @constructor Create a new Tile
 * @param {Object} config
 * @xtype TileWidget
 */
CQ.Ext.form.QuickLinkWidget = CQ.Ext.extend(CQ.Ext.form.TriggerField, {
	QuickLinkWidgetDialog : null,
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
		CQ.Ext.form.QuickLinkWidget.superclass.constructor.call(this,config);
		this.clearInvalid();
	},
	initComponent : function() {
		CQ.Ext.form.QuickLinkWidget.superclass.initComponent.call(this);
	},
	// private
	onDestroy : function() {
		if (this.QuickLinkWidgetDialog) {
			this.QuickLinkWidgetDialog.destroy();
		}
		CQ.Ext.form.QuickLinkWidget.superclass.onDestroy.call(this);
	},

	/**
	 * @method onTriggerClick
	 * @hideheadline
	 */
	onTriggerClick : function() {
		var parentDialog = this.findParentByType("dialog");
		var titleName= "title";
		var linkName = "link";

		var tabName = "tilesTab";

		var titleValue;
		var linkValue;

		var fieldValues = this.getValue();

		if (fieldValues.length != 0) {
			var tool = jQuery.parseJSON(fieldValues);
			titleValue = tool.title;
			linkValue = tool.link;
		}
		// lazy creation of browse dialog
		if (this.keyItemDialog == null) {
			function okHandler() {
				titleValue = this.getField(titleName).getValue();
				linkValue = this.getField(linkName).getValue();

				if(titleValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Title field is not empty');
					return false;
				}
				if(linkValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Link field is not empty');
					return false;
				}

				this.QuickLinkWidgetField.setValue('{"' + titleName + '":"' + titleValue
					+ '","' + linkName + '":"' + linkValue
					+ '"}');

				this.QuickLinkWidgetField.fireEvent("change");
				this.hide();
			}

			var typeSelectionchanged = function(box,value){
				box.findParentByType('tabpanel').manageTabs(value);
			};

			var QuickLinkWidgetDialogCfg = {
				'width' : 550,
				'height' : 550,
				'ok' : okHandler,
				'title' : 'Quick Link Widget',
				'QuickLinkWidgetField' : this,
				'modal' : 'true',
				'items' : {
					'xtype' : 'tabpanel',
					'id' : tabName,
					'items' : [ {
						'title' : "Link",
						'xtype' : 'panel',
						'items' : [ {
							'fieldLabel' : 'Title',
							'labelStyle' : 'font-weight:bold;',
							'name' : titleName,
							'value' : titleValue,
							'allowBlank' : false,
							'xtype' : 'textfield'
						},{
							'fieldLabel' : 'Link',
							'labelStyle' : 'font-weight:bold;',
							'rootPath' : '/content/calix',
							'name' : linkName,
							'value' : linkValue,
							'allowBlank' : false,
							'xtype' : 'pathfield'
						}]
					} ]
				}
			};
			QuickLinkWidgetDialogCfg.buttons = CQ.Dialog.OKCANCEL;
			this.QuickLinkWidgetDialog = new CQ.Dialog(QuickLinkWidgetDialogCfg);
		} else {
			var titleLabel = this.QuickLinkWidgetDialog.getField(titleName);
			titleLabel.setValue(titleValue);
			var linkLabel = this.QuickLinkWidgetDialog.getField(linkName);
			linkLabel.setValue(linkValue);
		}
		this.QuickLinkWidgetDialog.show();
	}
});
CQ.Ext.reg('quicklinkwidget', CQ.Ext.form.QuickLinkWidget);
