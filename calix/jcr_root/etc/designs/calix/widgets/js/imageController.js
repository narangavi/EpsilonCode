/*
 * This will register the drag / drop images component for the dialog boxes. and leaves original drag/drop for images.
 * 
 */
(function ($, ns, channel, window, undefined) {

    var self = {},
        name = 'Images';
        
    self.handleDragStart = function (event) {
        if (ns.device.isIpad) {
            event.preventDefault();
        }

        event.setDragObject($(event.currentTarget));

        // only slide back to content panel on mobile
        if (!ns.device.isDesktop()) {
            setTimeout(function () {
                ns.SidePanel.close();
            }, 100);
        }
    };

    self.handleDrag = function (event) {

    };

    self.handleDragEnd = function (event) {
        if(CQ.target) {
            CQ.target.val(event.path);
            jQuery('.' + CQ.target.randomclass).change();
            CQ.target = undefined;
        }
    };

    self.handleDragEnter = function (event) {

    };

    self.handleDragOver = function (event) {

    };

    self.handleDragLeave = function (event) {

    };

    self.handleDrop = function (event) {
        var editable = event.currentDropTarget.targetEditable,
            properties = {};

        for (var i = 0; i < editable.dropTargets.length; i++) {
            var dropTarget = editable.dropTargets[i],
                fileProperty = dropTarget.name,
                params = dropTarget.params,
                j;

            properties[fileProperty] = event.path;

            for (j in params) {
                if (params.hasOwnProperty(j)) {
                    properties[j] = params[j];
                }
            }

            for (j in event.param) {
                if (event.param.hasOwnProperty(j)) {
                    properties[j] = event.param[j];
                }
            }

            // set lastModified and lastModifiedBy
            // hack: this might be done maybe in a better way: properties should be given by the dialog
            var imgNode = fileProperty.substring(0, fileProperty.lastIndexOf("/"));
            properties[imgNode + "/jcr:lastModified"] = null;
            properties[imgNode + "/jcr:lastModifiedBy"] = null;

            // reset file
            properties[imgNode + "/fileName"] = null;
            properties[imgNode + "/file@Delete"] = "true";
        }

        ns.edit.actions.doUpdate(editable, properties).done(function () {
            ns.selection.select(editable);
        });
    };

    // register the dropcontroller at the dispatcher
    ns.dropController.register(name, self);
    
}(jQuery, Granite.author, jQuery(document), this));
