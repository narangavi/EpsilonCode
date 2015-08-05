/**
 * @class CQ.Ext.form.TileWidget
 * @extends CQ.Ext.form.TriggerField Provides some fields for Tile Widget
 * @constructor Create a new Tile
 * @param {Object} config
 * @xtype TileWidget
 */
CQ.Ext.form.LinkTileWidget = CQ.Ext.extend(CQ.Ext.form.TriggerField, {
	LinkTileWidgetDialog : null,
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
		CQ.Ext.form.LinkTileWidget.superclass.constructor.call(this,config);
		this.clearInvalid();
	},
	initComponent : function() {
		CQ.Ext.form.LinkTileWidget.superclass.initComponent.call(this);
	},
	// private
	onDestroy : function() {
		if (this.LinkTileWidgetDialog) {
			this.LinkTileWidgetDialog.destroy();
		}
		CQ.Ext.form.LinkTileWidget.superclass.onDestroy.call(this);
	},

	/**
	 * @method onTriggerClick
	 * @hideheadline
	 */
	onTriggerClick : function() {
		var parentDialog = this.findParentByType("dialog");
		var imageName = "image";
		var titleName= "title";
		var descName = "desc";
		var linkName = "link";

		var tabName = "tilesTab";

		var imageValue;
		var titleValue;
		var descValue;
		var linkValue;

		var fieldValues = this.getValue();

		if (fieldValues.length != 0) {
			var tool = jQuery.parseJSON(fieldValues);
			imageValue = tool.image;
			titleValue = tool.title;
			descValue = tool.desc;
			linkValue = tool.link;
		}
		// lazy creation of browse dialog
		if (this.keyItemDialog == null) {
			function okHandler() {
				imageValue = this.getField(imageName).getValue();
				titleValue = this.getField(titleName).getValue();
				descValue = this.getField(descName).getValue();
				linkValue = this.getField(linkName).getValue();

				if(imageValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Image field is empty');
					return false;
				}
				if(titleValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Tile title field is empty');
					return false;
				}
				if(descValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'Tile description field is empty');
					return false;
				}
				if(linkValue == "") {
					CQ.Ext.Msg.alert('Validation Failed', 'External link field is empty');
					return false;
				}

				this.LinkTileWidgetField.setValue('{"' + imageName + '":"' + imageValue
					+ '","' + titleName + '":"' + titleValue
					+ '","' + descName + '":"' + descValue
					+ '","' + linkName + '":"' + linkValue
					+ '"}');

				this.LinkTileWidgetField.fireEvent("change");
				this.hide();
			}

			var typeSelectionchanged = function(box,value){
				box.findParentByType('tabpanel').manageTabs(value);
			};

			var LinkTileWidgetDialogCfg = {
				'width' : 550,
				'height' : 550,
				'ok' : okHandler,
				'title' : 'Link Tile Widget',
				'LinkTileWidgetField' : this,
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
							'allowBlank' : false,
							'xtype' : 'pathfield'
						},{
							'fieldLabel' : 'Tile Title',
							'labelStyle' : 'font-weight:bold;',
							'name' : titleName,
							'value' : titleValue,
							'allowBlank' : false,
							'xtype' : 'textfield'
						},{
							'fieldLabel' : 'Tile Description',
							'labelStyle' : 'font-weight:bold;',
							'name' : descName,
							'value' : descValue,
							'allowBlank' : false,
							'xtype' : 'textarea'
						},{
							'fieldLabel' : 'External Link',
							'labelStyle' : 'font-weight:bold;',
							'name' : linkName,
							'value' : linkValue,
							'allowBlank' : false,
							'xtype' : 'textarea'
						}]
					} ]
				}
			};
			LinkTileWidgetDialogCfg.buttons = CQ.Dialog.OKCANCEL;
			this.LinkTileWidgetDialog = new CQ.Dialog(LinkTileWidgetDialogCfg);
		} else {
			var imageLabel = this.LinkTileWidgetDialog.getField(imageName);
			imageLabel.setValue(imageValue);
			var titleLabel = this.LinkTileWidgetDialog.getField(titleName);
			titleLabel.setValue(titleValue);
			var descLabel = this.LinkTileWidgetDialog.getField(descName);
			descLabel.setValue(descValue);
			var linkLabel = this.LinkTileWidgetDialog.getField(linkName);
			linkLabel.setValue(linkValue);
		}
		this.LinkTileWidgetDialog.show();
	}
});
CQ.Ext.reg('linktilewidget', CQ.Ext.form.LinkTileWidget);
