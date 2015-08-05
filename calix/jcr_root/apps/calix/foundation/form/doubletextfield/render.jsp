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
--%><%@page session="false" %><%
%><%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page import="com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.ComponentHelper.Options,
                  com.adobe.granite.ui.components.Tag,
                  com.adobe.granite.ui.components.Field,
                  com.adobe.granite.ui.components.Value,
                  org.apache.sling.commons.json.JSONObject,
                  java.util.Random,
                  org.apache.commons.lang3.StringEscapeUtils" %><%

    Config cfg = cmp.getConfig();
    ValueMap vm = (ValueMap) request.getAttribute(Field.class.getName());

    String value = vm.get("value", String.class);

    JSONObject obj = new JSONObject();
    try{
        obj = new JSONObject(value);
    } catch(Exception ex){
        log.error("unable to parse "+value+" into json object",ex);
    }
    String fieldOneValue = obj.has("fieldOne") ? obj.getString("fieldOne") : "";
    String fieldTwoValue = obj.has("fieldTwo") ? obj.getString("fieldTwo") : "";

    String fieldLabel = cfg.get("fieldLabel", String.class);
    
    Tag tag = cmp.consumeTag();
    
    AttrBuilder attrs = tag.getAttrs();

    attrs.add("id", cfg.get("id", String.class));
    //attrs.addClass("coral-Form-field ");
    attrs.addClass(cfg.get("class", String.class));
    attrs.addRel(cfg.get("rel", String.class));
    attrs.add("title", i18n.getVar(cfg.get("title", String.class)));
    
    attrs.addOthers(cfg.getProperties(), "id", "class", "rel", "title", "fieldLabel");

    Random rand=new Random();
    int nextInt = rand.nextInt();
    String uniqueId=""+nextInt;
    String updateJavascript="doubleField.updateHiddenField("+uniqueId+");";

    %><div <%= attrs.build() %>><%
        AttrBuilder fieldOneAttrs = new AttrBuilder(request, xssAPI);

        fieldOneAttrs.add("id", "fieldOne"+uniqueId);
        fieldOneAttrs.add("type", "text");
        fieldOneAttrs.addDisabled(cfg.get("disabled", false));
        fieldOneAttrs.add("value", fieldOneValue);
        fieldOneAttrs.addClass("coral-Textfield");
        fieldOneAttrs.add("onkeyup",updateJavascript);

        %><input <%= fieldOneAttrs.build() %> /><%
    %><%
        AttrBuilder fieldTwoAttrs = new AttrBuilder(request, xssAPI);

        fieldTwoAttrs.add("id", "fieldTwo"+uniqueId);
        fieldTwoAttrs.add("type", "text");
        fieldTwoAttrs.addDisabled(cfg.get("disabled", false));
        fieldTwoAttrs.add("value", fieldTwoValue);
        fieldTwoAttrs.addClass("coral-Textfield");
        fieldTwoAttrs.add("onkeyup",updateJavascript);
        
        %><input <%= fieldTwoAttrs.build() %> /> <%
    %>
    <%
        String hiddenValue=value;
    %>
    <input id="doubleFieldValue<%=uniqueId%>" type="hidden" name="<%=cfg.get("name", String.class)%>" value="<%=StringEscapeUtils.escapeXml(value)%>"/>
    <script>
        if(!doubleField){
            var doubleField={};
            doubleField.updateHiddenField = function (uniqueId){
                var doubleFieldValues={};
                doubleFieldValues.fieldOne = jQuery("#fieldOne"+uniqueId).val();
                doubleFieldValues.fieldTwo = jQuery("#fieldTwo"+uniqueId).val();
                jQuery('#doubleFieldValue'+uniqueId).val(JSON.stringify(doubleFieldValues));
            };
        }
    </script>
    </div>
    
    