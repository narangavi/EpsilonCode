CQ.Ext.ns("Calix");
Calix.MultiFieldPanel = CQ.Ext.extend(CQ.Ext.Panel, {
    panelValue: '',

    constructor: function(config){
        config = config || {};
        Calix.MultiFieldPanel.superclass.constructor.call(this, config);
    },

    initComponent: function () {
        Calix.MultiFieldPanel.superclass.initComponent.call(this);

        this.panelValue = new CQ.Ext.form.Hidden({
            name: this.name
        });

        this.add(this.panelValue);

        var dialog = this.findParentByType('dialog');

        dialog.on('beforesubmit', function(){
            var value = this.getValue();
            value = value.replace(/\\/g,"");
            value = value.replace(/}",/g,"},");
            value = value.replace(/,"{/g,",{");
            value = value.replace(/\["{/g,"[{");
            value = value.replace(/}"]/g,"}]");
            if(value){
                this.panelValue.setValue(value);
            }
        },this);

    },

    afterRender : function(){
        Calix.MultiFieldPanel.superclass.afterRender.call(this);

        this.items.each(function(){
            if(!this.contentBasedOptionsURL
                    || this.contentBasedOptionsURL.indexOf(CQ.form.Selection.PATH_PLACEHOLDER) < 0){
                return;
            }

            this.processPath(this.findParentByType('dialog').path);
        })
    },

    getValue: function () {
        var pData = {};

        this.items.each(function(i){
            if(i.xtype == "label" || i.xtype == "hidden" || !i.hasOwnProperty("dName")){
                return;
            }

            pData[i.dName] = i.getValue();
        });


        return $.isEmptyObject(pData) ? "" : JSON.stringify(pData);
    },

    setValue: function (value) {
		
         var from = value.indexOf("[") + 1;
         var to = value.indexOf("]");

        if(from>0){   
        var value1 = (value.substring(from, to));      
        var value2 =  JSON.stringify(value1);	
        value2 = value2.replace(/},/g,"}\",");
        value2 = value2.replace(/,{/g,",\"{");   
		value = value.replace(value1, value2);
		
        }
       

        this.panelValue.setValue(value);
		var temp = JSON.parse(value);
		temp.cellValues


        var pData = JSON.parse(value);

        this.items.each(function(i){
            if(i.xtype == "label" || i.xtype == "hidden" || !i.hasOwnProperty("dName")){
                return;
            }

            i.setValue(pData[i.dName]);
        });
    },

    validate: function(){
        return true;
    },

    getName: function(){
        return this.name;
    }
});

CQ.Ext.reg("multi-field-panel", Calix.MultiFieldPanel);