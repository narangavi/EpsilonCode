package com.calix.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.calix.model.Column;
import com.calix.model.Row;
import com.calix.model.Table;
import com.calix.utils.JcrUtils;
import com.calix.utils.JsonUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class Chart.
 */
public class Chart extends WCMUse {

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Chart.class);

    /**
     * The Constant CHART_TITLE.
     */
    private static final String CHART_TITLE = "chartTitle";

    /**
     * The Constant CHART_CAPTION.
     */
    private static final String CHART_CAPTION = "text";

    /**
     * The Constant DATACELL.
     */
    private static final String DATACELL = "dataCell";

    /**
     * The Constant CELL_VALUES.
     */
    private static final String CELL_VALUES = "cellValues";

    /**
     * The Constant CELL_VALUE.
     */
    private static final String CELL_VALUE = "cellValue";

    /**
     * The Constant COLUMN_HEADERS.
     */
    private static final String COLUMN_HEADERS = "columnHeaders";

    /**
     * The Constant COLUMN_HEADER.
     */
    private static final String COLUMN_HEADER = "columnHeader";

    /**
     * The Constant TOTALROW.
     */
    private static final String TOTALROW = "totalRow";

    /**
     * The chart table.
     */
    private Table table;

    /**
     * Gets the table.
     *
     * @return the table
     */
    public Table getTable() {
        return table;
    }

    /* (non-Javadoc)
     * @see com.adobe.cq.sightly.WCMUse#activate()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void activate() throws Exception {
        SlingHttpServletRequest slingRequest = this.getRequest();
        Node currentNode = slingRequest.getResource().adaptTo(Node.class);
        String title = getProperties().get(CHART_TITLE, StringUtils.EMPTY);
        String caption = getProperties().get(CHART_CAPTION, StringUtils.EMPTY);
        String[] jsonArray = JcrUtils.getStringArrayValue(currentNode, DATACELL);
        // get parameters from JSON array.
        Map<String, Object> params = getParams(jsonArray);
        List<String> columnHeaders = (List<String>) params.get(COLUMN_HEADERS);
        int totalRow = (Integer) params.get(TOTALROW);
        int totalColumn = columnHeaders.size();
        table = convertJsonToTable(jsonArray, totalColumn, totalRow);
        table.setTitle(title);
        table.setCaption(caption);
        table.setColumnHeaders(columnHeaders);
    }

    /**
     * Convert json to table.
     *
     * @param jsonArray   the json array
     * @param totalColumn the total column
     * @param totalRow    the total row
     * @return the table
     */
    private Table convertJsonToTable(String[] jsonArray, int totalColumn, int totalRow) {
        Table chartTable = new Table();

        if (ArrayUtils.isNotEmpty(jsonArray)) {
            List<Row> rows = new ArrayList<Row>();
            try {
                int rowIndex = 0;
                Row row = null;
                List<Column> columns = null;
                Column column = null;
                String cellValue = StringUtils.EMPTY;
                while (rowIndex < totalRow) {
                    // begin new row
                    row = new Row();
                    columns = new ArrayList<Column>();
                    for (int columnIndex = 0; columnIndex < totalColumn; columnIndex++) {
                        JSONObject obj = new JSONObject(jsonArray[columnIndex]);
                        JSONArray values = JsonUtils.getJsonArray(CELL_VALUES, obj);
                        JSONObject cell;
                        // begin new column
                        column = new Column();
                        if (rowIndex < values.length()) {
                            cell = values.getJSONObject(rowIndex);
                            cellValue = JsonUtils.getJsonValue(CELL_VALUE, cell);
                        } else {
                            cellValue = "-";
                        }
                        // end new column
                        column.setCellValue(cellValue);
                        columns.add(column);
                    }
                    // end new row
                    row.setColumns(columns);
                    rows.add(row);
                    rowIndex++;
                }
                chartTable.setRows(rows);
            } catch (JSONException e) {
                LOGGER.error("Error when convert JSON to table object {}", e);
            }
        }

        return chartTable;
    }


    /**
     * Gets parameters.
     *
     * @param jsonArray the JSON array
     * @return the map of parameters
     */
    private Map<String, Object> getParams(String[] jsonArray) {
        Map<String, Object> maps = new HashMap<String, Object>();
        List<String> columnHeaders = new ArrayList<String>();
        int totalRow = 0;
        if (ArrayUtils.isNotEmpty(jsonArray)) {
            String jsonString = StringUtils.EMPTY;
            JSONObject obj;
            String header = StringUtils.EMPTY;
            try {
                for (int i = 0; i < jsonArray.length; i++) {
                    jsonString = jsonArray[i];
                    if (StringUtils.isNotBlank(jsonString)) {
                        // get column headers
                        obj = new JSONObject(jsonString);
                        header = JsonUtils.getJsonValue(COLUMN_HEADER, obj);
                        columnHeaders.add(header);
                        //get max row
                        JSONArray values = JsonUtils.getJsonArray(CELL_VALUES, obj);
                        if (values.length() > totalRow) {
                            totalRow = values.length();
                        }
                    }
                }
            } catch (JSONException e) {
                LOGGER.error("Error when get parameters {}", e);
            }
        }
        maps.put(COLUMN_HEADERS, columnHeaders);
        maps.put(TOTALROW, totalRow);
        return maps;
    }
}
