
<%--
    Engineered by Vagner Polund

    This JSP is responsible for rendering out all necessary fields out to a granite 
    UI dialog box.
--%><%@page session="false" %><%
%><%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page import="com.adobe.granite.ui.components.AttrBuilder,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.Field,
                  com.adobe.granite.ui.components.ComponentHelper.Options,
                  com.adobe.granite.ui.components.Tag,
                  com.adobe.granite.ui.components.Value,
                  org.apache.sling.api.wrappers.ValueMapDecorator,
                  java.util.HashMap,
                  java.util.ArrayList,
                  java.util.Arrays,
                  java.util.Iterator" %><%
    Config cfg = cmp.getConfig();

    ValueMap vm = (ValueMap)request.getAttribute(Field.class.getName());

    String compositeFieldName=cfg.get("name", String.class);
    String value = vm.get("value", String.class);

    Tag tag = cmp.consumeTag();
    
    AttrBuilder wrappingOuterDivAttributes = tag.getAttrs();

    wrappingOuterDivAttributes.add("id", cfg.get("id", String.class));
    wrappingOuterDivAttributes.addClass(cfg.get("class", String.class));
    wrappingOuterDivAttributes.addRel(cfg.get("rel", String.class));
    wrappingOuterDivAttributes.add("pagetitle", i18n.getVar(cfg.get("pagetitle", String.class)));
    
    wrappingOuterDivAttributes.addOthers(cfg.getProperties(), "id", "class", "rel", "pagetitle", "fieldLabel");
    wrappingOuterDivAttributes.addClass("compositeFieldContainer");

%><div style="clear: both; margin-bottom: 20px; border-bottom: 1px solid gray;" <%= wrappingOuterDivAttributes.build
() %>>
<%
    Boolean showFieldLabels = cfg.get("showFieldLabels", Boolean.class);

    if (showFieldLabels == null) {
        showFieldLabels = false;
    }

    Boolean inMultiField =isInMultiField(resource);

    Integer i = (Integer)request.getAttribute("currentMultiIndex");
    
    if(inMultiField!= null && inMultiField){

        i= i == null ? new Integer(0) : i+1;
        request.setAttribute("currentMultiIndex", i);
        %><input class="serializedFieldHiddenValue" type="hidden" name="<%=compositeFieldName%>" value="<%=i%>"/><%
    }

    Iterator<Resource> subFields = cfg.getItems();

    while (subFields.hasNext()) {
        Resource subField = subFields.next();

        AttrBuilder wrappingInnerDivAttributes = new AttrBuilder(request, xssAPI);

        Config subFieldConfig = new Config(subField);
        String compositeName = subFieldConfig.get("name");

        wrappingInnerDivAttributes.add("data-compositeName", compositeName);
        wrappingInnerDivAttributes.addClass("compositeField");
        Object currentFieldValue = cmp.getValue().getContentValue(compositeName);
        if(inMultiField!= null && inMultiField){
            String contentPath = (String)request.getAttribute(Value.CONTENTPATH_ATTRIBUTE);
            Config contentResource = new Config(resourceResolver.getResource(contentPath));
            Object rawValue = contentResource.getProperties().get(compositeName);
            if(rawValue != null){
                if(rawValue instanceof Object[]){
                    Object[] contentvalues = (Object[])contentResource.getProperties().get(compositeName);
                    ArrayList<Object> subComponentValues = new ArrayList<Object>(Arrays.asList(contentvalues));
                    currentFieldValue = subComponentValues.get(i);
                } else{
                    currentFieldValue = rawValue;
                }
            }
            
        }

%>    <div <%= wrappingInnerDivAttributes.build() %>>
        <% include(subField, compositeName, currentFieldValue, cmp, request, showFieldLabels); %>
    </div>
<%
    }
%>
</div>
<%!
    private void include(Resource field, String name, Object value, ComponentHelper cmp, HttpServletRequest request, boolean setRootField) throws Exception {
        ValueMap map = new ValueMapDecorator(new HashMap<String, Object>());
        map.put(name, value);

        ValueMap existingValuessMap = (ValueMap)request.getAttribute(Value.FORM_VALUESS_ATTRIBUTE);
        request.setAttribute(Value.FORM_VALUESS_ATTRIBUTE, map);

        cmp.include(field, new Options().rootField(setRootField));

        request.setAttribute(Value.FORM_VALUESS_ATTRIBUTE, existingValuessMap);
    }

    private boolean isInMultiField(Resource currentResource){
        boolean isInMultifield=false;
        String parentResourcePath = currentResource.getParent().getResourceType();
        if(parentResourcePath.contains("/")){
            String parentResourceEnding=parentResourcePath.substring(parentResourcePath.lastIndexOf("/")+1, parentResourcePath.length());
            String parentResourceBeginning=parentResourcePath.substring(0,parentResourcePath.indexOf("/"));
            isInMultifield = ("multifield".equals(parentResourceEnding) && 
                                      "granite".equals(parentResourceBeginning) && 
                                      "field".equals(currentResource.getName()));
        }
        return isInMultifield;
    }
%>