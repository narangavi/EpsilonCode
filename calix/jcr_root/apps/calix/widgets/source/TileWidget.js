/**
 * @class CQ.Ext.form.TileWidget
 * @extends CQ.Ext.form.TriggerField Provides some fields for Tile Widget
 * @constructor Create a new Tile
 * @param {Object} config
 * @xtype TileWidget
 */
CQ.Ext.form.TileWidget = CQ.Ext.extend(CQ.Ext.form.TriggerField, {
	TileWidgetDialog : null,
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
		CQ.Ext.form.TileWidget.superclass.constructor.call(this,config);
		this.clearInvalid();
	},
	initComponent : function() {
		CQ.Ext.form.TileWidget.superclass.initComponent.call(this);
	},
	// private
	onDestroy : function() {
		if (this.TileWidgetDialog) {
			this.TileWidgetDialog.destroy();
		}
		CQ.Ext.form.TileWidget.superclass.onDestroy.call(this);
	},

	/**
	 * @method onTriggerClick
	 * @hideheadline
	 */
	onTriggerClick : function() {
		var parentDialog = this.findParentByType("dialog");
		var imageName = "image";
		var titleName= "title";
		var anchorName = "anchor";
		var tabName = "mainTab";

		var imageValue;
		var titleValue;
		var anchorValue;

		var fieldValues = this.getValue();

		if (fieldValues.length != 0) {
			var tool = jQuery.parseJSON(fieldValues);
			imageValue = tool.image;
			titleValue = tool.title;
			anchorValue = tool.anchor;
		}
		// lazy creation of browse dialog
		if (this.keyItemDialog == null) {
			function okHandler() {
				imageValue = this.getField(imageName).getValue();
				titleValue = this.getField(titleName).getValue();
				anchorValue = this.getField(anchorName).getValue();

				this.TileWidgetField.setValue('{"' + imageName + '":"' + imageValue
					+ '","' + titleName + '":"' + titleValue
					+ '","' + anchorName + '":"' + anchorValue
					+ '"}');

				this.TileWidgetField.fireEvent("change");
				this.hide();
			}

			var typeSelectionchanged = function(box,value){
				box.findParentByType('tabpanel').manageTabs(value);
			};

			var TileWidgetDialogCfg = {
				'width' : 550,
				'height' : 550,
				'ok' : okHandler,
				'title' : 'Tile Widget',
				'TileWidgetField' : this,
				'modal' : 'true',
				'items' : {
					'xtype' : 'tabpanel',
					'id' : tabName,
					'items' : [ {
						'title' : "Tiles",
						'xtype' : 'panel',
						'items' : [ {
							'fieldLabel' : 'Tile Image',
							'labelStyle' : 'font-weight:bold;',
							'rootPath' : '/content/dam',
							'name' : imageName,
							'value' : imageValue,
							'xtype' : 'pathfield'
						},{
							'fieldLabel' : 'Tile Title',
							'labelStyle' : 'font-weight:bold;',
							'name' : titleName,
							'value' : titleValue,
							'xtype' : 'textfield'
						},{
							'fieldLabel' : 'Tile URL/Anchor',
							'labelStyle' : 'font-weight:bold;',
							'name' : anchorName,
							'value' : anchorValue,
							'xtype' : 'textfield'
						}]
					} ]
				}
			};
			TileWidgetDialogCfg.buttons = CQ.Dialog.OKCANCEL;
			this.TileWidgetDialog = new CQ.Dialog(TileWidgetDialogCfg);
		} else {
			var imageLabel = this.TileWidgetDialog.getField(titleName);
			imageLabel.setValue(imageValue);
			var titleLabel = this.TileWidgetDialog.getField(leadinName);
			titleLabel.setValue(titleValue);
			var anchorLabel = this.TileWidgetDialog.getField(anchorName);
			anchorLabel.setValue(anchorValue);
		}
		this.TileWidgetDialog.show();
	}
});
CQ.Ext.reg('tilewidget', CQ.Ext.form.TileWidget);
