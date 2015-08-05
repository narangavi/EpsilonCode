<%--
  ADOBE CONFIDENTIAL

  Copyright 2013 Adobe Systems Incorporated
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
          import="com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.Field,
                  com.adobe.granite.ui.components.Tag,
                  org.apache.commons.lang3.RandomStringUtils" %><%

    Config cfg = cmp.getConfig();
    ValueMap vm = (ValueMap) request.getAttribute(Field.class.getName());
    
    boolean disabled = cfg.get("disabled", false);
    
    String defaultOptionLoader =
            "function(path, callback) {\n" +
            "    jQuery.get(path + \".pages.json\", {\n" +
            "        predicate: \"hierarchyNotFile\"\n" +
            "    },\n" +
            "    function(data) {\n" +
            "        var pages = data.pages;\n" +
            "        var result = [];\n" +
            "        for(var i = 0; i < pages.length; i++) {\n" +
            "            result.push(pages[i].label);\n" +
            "        }\n" +
            "        if (callback) callback(result);\n" +
            "    }, \"json\");\n" +
            "    return false;\n" +
            "}";
    
    Tag tag = cmp.consumeTag();
    
    AttrBuilder attrs = tag.getAttrs();

    attrs.add("id", cfg.get("id", String.class));
    attrs.addClass(cfg.get("class", String.class));
    attrs.addRel(cfg.get("rel", String.class));
    attrs.add("title", i18n.getVar(cfg.get("title", String.class)));

    attrs.addClass("coral-InputGroup");
    attrs.add("data-init", "pathbrowser");
    attrs.add("data-root-path", cfg.get("rootPath", "/"));
    attrs.add("data-option-loader", cfg.get("optionLoader", defaultOptionLoader));
    attrs.add("data-option-loader-root", cfg.get("optionLoaderRoot", String.class));
    attrs.add("data-option-value-reader", cfg.get("optionValueReader", String.class));
    attrs.add("data-option-title-reader", cfg.get("optionTitleReader", String.class));
    
    if (disabled) {
        attrs.add("data-disabled", disabled);
    }
    
    attrs.addOthers(cfg.getProperties(), "id", "class", "rel", "title", "name", "value", "emptyText", "disabled", "rootPath", "optionLoader", "optionLoaderRoot", "optionValueReader", "optionTitleReader", "renderReadOnly", "fieldLabel", "fieldDescription", "required");

    AttrBuilder inputAttrs = new AttrBuilder(request, xssAPI);
    inputAttrs.addClass("coral-InputGroup-input coral-Textfield");
    inputAttrs.addClass("coral-Textfield");
    inputAttrs.addClass("js-coral-pathbrowser-input");
	inputAttrs.addClass("cq-droptarget");
    inputAttrs.addClass("cq-subdroptarget");
    inputAttrs.addClass("cq-Overlay-subdroptarget"); 
    inputAttrs.addClass("cq-dd-image");
    inputAttrs.addClass("cq-image-placeholder");
    inputAttrs.addClass("info");
    String randomClass = RandomStringUtils.randomAlphabetic(5);
    inputAttrs.addClass(randomClass);
    inputAttrs.add("type", "text");
	inputAttrs.add("draggable", "true");
	inputAttrs.add("data-asset-accept", "['.*']");
	//inputAttrs.add("data-asset-groups", "['imagepath']");
	inputAttrs.add("data-asset-groups", "['media']");
	String contentPath = slingRequest.getRequestPathInfo().getSuffix();
	inputAttrs.add("data-path", contentPath);
    inputAttrs.add("name", cfg.get("name", String.class));
    inputAttrs.add("value", vm.get("value", String.class));
    inputAttrs.add("placeholder", i18n.getVar(cfg.get("emptyText", String.class)));
    inputAttrs.add("autocomplete", "off");
    inputAttrs.addDisabled(disabled);

    if (cfg.get("required", false)) {
        inputAttrs.add("aria-required", true);
    }

%>
<script>
    jQuery(".<%= randomClass %>").on("dragenter", function(e) {
        jQuery(".<%= randomClass %>").addClass("cq-dragover");
        jQuery(".<%= randomClass %>").addClass("cq-Overlay--insertBefore");
        CQ.target = $(e.currentTarget);
        CQ.target.randomclass = "<%= randomClass %>";
    });
        
    jQuery(".<%= randomClass %>").on("dragleave", function(e) {
        jQuery(".<%= randomClass %>").removeClass("cq-dragover");
        jQuery(".<%= randomClass %>").removeClass("cq-Overlay--insertBefore");
        CQ.target = undefined;
    }); 
    
    jQuery(".<%= randomClass %>" ).on('change',function(e) {
        e.preventDefault();
        jQuery(".<%= randomClass %>thumb").attr("src",e.target.value);
    });    
</script>
<span <%= attrs.build() %>><input <%= inputAttrs.build() %> style="position: relative;"></span>
<img class="<%=randomClass%>thumb" src="<%= vm.get("value") %>" style="height: 38px; margin-left: 5px; max-width: 110px;">