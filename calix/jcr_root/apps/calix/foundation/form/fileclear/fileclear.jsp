<%--
  ADOBE CONFIDENTIAL

  Copyright 2012 Adobe Systems Incorporated
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and may be covered by U.S. and Foreign Patents,
  patents in process, and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%
%><%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page session="false"
          import="java.util.regex.Matcher,
                  org.apache.commons.lang.StringUtils,
                  org.apache.sling.api.resource.ResourceUtil,
                  org.json.JSONArray,
                  com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.Tag" %><%
/**
 * FileUpload creates a file upload widget.
 *
 * Warning: The author SHOULD NOT depends on the details of underlying implementation. The output markup MAY change.
 *
 * @component
 * @name FileUpload
 * @location /libs/granite/ui/components/foundation/form/fileupload
 *
 * @property {String}  [id] value for the id attribute if any
 * @property {String}  [rel] class attr (this is to indicate the semantic relationship of the element)
 * @property {String}  [class] space separated list of class values
 * @property {String}  [title] title attr
 * @property {String}  [text] the text of the button
 * @property {String}  [icon] the icon of the button
 * @property {String[]} [variant] the variant of the button: "secondary", "quiet"
 * @property {String}  [name="file"] name for an underlying form field
 * @property {Boolean} [multiple=false] if <code>true</code>, multiple files can be uploaded.
 * @property {Boolean} [disabled=false] enables/disables the component
 * @property {String}  [placeholder] placeholder attr for the underlying form field
 * @property {String}  [emptyText] same as placeholder
 * @property {String}  [fileNameParameter] Name of File name's parameter
 * @property {String}  [uploadUrl] URL where to upload the file, you can use <code>${suffix.path}</code>
 * @property {String}  [uploadUrlBuilder] Upload URL builder
 * @property {Long}    [sizeLimit] File size limit (in Bytes ?)
 * @property {Boolean} [autoStart=false] If <code>true</code>, upload starts automatically once the file is selected
 * @property {Boolean} [useHTML5=true] Prefer HTML5 to upload files (if browser allows it)
 * @property {String}  [dropZone]  Drop zone selector to upload files from file system directly (if browser allows it)
 * @property {Object}  [events={}]   Event handlers
 * @property {String[]}  [mimeTypes=["*"] Browse and selection filter for file selection. Eg: [".png",".jpg"] or ["image/*]"
 * @property {String}  [&lt;other&gt;] data-&lt;other&gt; attr
 */

    if (!cmp.getRenderCondition().check()) {
       return;
	}
    Config cfg = cmp.getConfig();
    
    String fieldLabel = cfg.get("fieldLabel", String.class);
    String fieldDesc = cfg.get("fieldDescription", String.class);
    String name = cfg.get("name", String.class);
    String uploadUrl = cfg.get("uploadUrl", "");
    String[] mimeTypes =  cfg.get("mimeTypes", new String[] { "*" });
    String icon = cfg.get("icon", String.class);
    String[] variants = cfg.get("variant", new String[0]);

    
    Tag tag = cmp.consumeTag();
    
    AttrBuilder fieldAttrs = tag.getAttrs();
    
    fieldAttrs.add("id", cfg.get("id", String.class));
    fieldAttrs.addClass(cfg.get("class", String.class));
    fieldAttrs.addRel(cfg.get("rel", String.class));

    fieldAttrs.addClass("coral-FileUpload");
    fieldAttrs.add("data-init", "fileupload");

    AttrBuilder inputAttrs = new AttrBuilder(request, xssAPI);
    
    inputAttrs.add("type", "file");
    inputAttrs.add("name", name);
    inputAttrs.add("title", i18n.getVar(cfg.get("title", String.class)));
    inputAttrs.add("value", cmp.getValue().get("value", String.class));
    inputAttrs.add("placeholder", i18n.getVar(cfg.get("emptyText", String.class)));
    inputAttrs.addMultiple(cfg.get("multiple", false));
    inputAttrs.addDisabled(cfg.get("disabled", false));
    inputAttrs.add("accept", StringUtils.join(mimeTypes,','));

    String suffix = slingRequest.getRequestPathInfo().getSuffix();
    if (suffix != null) {
        // FIXME use EL to do this
        uploadUrl = uploadUrl.replaceAll("\\$\\{suffix.path\\}", Matcher.quoteReplacement(suffix));
    }

    inputAttrs.addClass("coral-FileUpload-input");
    inputAttrs.add("data-dropzone", cfg.get("dropZone", String.class));
    inputAttrs.add("data-usehtml5", cfg.get("useHTML5", true));
    inputAttrs.addHref("data-upload-url", uploadUrl);
    inputAttrs.add("data-upload-url-builder", cfg.get("uploadUrlBuilder", String.class));
    inputAttrs.add("data-size-limit", cfg.get("sizeLimit", String.class));
    inputAttrs.add("data-mime-types", new JSONArray(mimeTypes).toString());
    inputAttrs.add("data-auto-start", cfg.get("autoStart", false));
    inputAttrs.add("data-file-name-parameter", cfg.get("fileNameParameter", String.class));

    ValueMap eventVM = ResourceUtil.getValueMap(cfg.getChild("events"));
    for (String eventName : eventVM.keySet()) {
        if (!eventName.startsWith("jcr:")) {
            Object eventHandler = eventVM.get(eventName);
            if (eventHandler instanceof String) {
                inputAttrs.add("data-event-" + eventName, (String) eventHandler);
            }
        }
    }

    inputAttrs.addOthers(cfg.getProperties(), "id", "rel", "class", "title", "text", "icon", "variant", "name", "value", "multiple", "emptyText", "disabled", "uploadUrl", "sizeLimit", "autoStart", "fileNameParameter", "mimeTypes", "fieldLabel", "fieldDescription");
    
    
    if (cmp.getOptions().rootField() && (fieldLabel != null || fieldDesc != null)) {
        AttrBuilder fieldWrapperAttrs = new AttrBuilder(request, xssAPI);
        fieldWrapperAttrs.addClass("coral-Form-fieldwrapper");

        %><div <%= fieldWrapperAttrs.build() %>><%

        fieldAttrs.addClass("coral-Form-field");
        
        if (fieldLabel != null) {
            %><label class="coral-Form-fieldlabel"><%= outVar(xssAPI, i18n, fieldLabel) %></label><%
        }
    }

	String contentPath = slingRequest.getRequestPathInfo().getSuffix();
	boolean showDragMessage = false;
	if(resourceResolver.getResource(contentPath + "/image/sling:resourceType") == null) {
        showDragMessage = true;
    }

    %><span <%= fieldAttrs.build() %>><%
        AttrBuilder buttonAttrs = new AttrBuilder(request, xssAPI);
        buttonAttrs.addClass("coral-FileUpload-trigger coral-Button");
        
        for (String variant : variants) {
            buttonAttrs.addClass("coral-Button--" + variant);
        }
    
        %><span <%= buttonAttrs.build() %>><%
		    if (icon != null) {
		        %><i class="coral-Icon <%= cmp.getIconClass(icon) %>"></i> <%
		    }

            out.print(outVar(xssAPI, i18n, cfg.get("text", cfg.get("title", "")))); // Using title for button is for compatiblity, otherwise please avoid.
        
		    %><input <%= inputAttrs.build() %>>
		</span>
    </span><%
    
    if (cmp.getOptions().rootField() && (fieldLabel != null || fieldDesc != null)) {
        if (fieldDesc != null) {
            %><span class="coral-Form-fieldinfo coral-Icon coral-Icon--infoCircle coral-Icon--sizeS" data-init="quicktip" data-quicktip-type="info" data-quicktip-arrow="right" data-quicktip-content="<%= outAttrVar(xssAPI, i18n, fieldDesc) %>"></span><%
        }
        %></div><%
    }
%>
<style>
    #_drag-message {
		margin-top: 12px;
    }
</style>
<script>
    jQuery(function() {
        var showDragMessage = <%= showDragMessage %>;
        if(showDragMessage) {
			dragMessage();
        }
        jQuery("span.coral-FileUpload-trigger.coral-Button").unbind("click");
        jQuery("span.coral-FileUpload-trigger.coral-Button").off("click");
        jQuery('span.coral-FileUpload-trigger.coral-Button').on("click", clearImage);
    });

    function clearImage(e) {
        e.stopPropagation();
        e.preventDefault();
        var deleteImage = confirm("Warning: Pressing OK will clear the image immediately.");
        if(deleteImage) {
            var propsToPost = {};
            propsToPost["image@Delete"] = "";
            jQuery.ajax({
                url: "<%= contentPath %>",
                type: "POST",
                async: false,
                data: propsToPost
            });
            dragMessage();
        }
        jQuery("span.coral-FileUpload-trigger.coral-Button").unbind("click");
        jQuery("span.coral-FileUpload-trigger.coral-Button").off("click");
        jQuery('span.coral-FileUpload-trigger.coral-Button').on("click", clearImage);
    }

    function dragMessage() {
        jQuery("span.coral-FileUpload-trigger.coral-Button").hide();
        jQuery("span.coral-FileUpload.coral-Form-field").find("#_drag-message").remove();
        jQuery("span.coral-FileUpload.coral-Form-field").prepend("<div id='_drag-message'>Drag an image from the asset finder onto the component on the page to place an image here.</div>");
    }
</script>
