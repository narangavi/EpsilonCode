package com.calix.model;

import java.util.List;

/**
 * The Class Table.
 */
public class Table {

	/** The column headers. */
	private List<String> columnHeaders;
	
	/** The rows. */
	private List<Row> rows;
	
	/** The title. */
	private String title;
	
	/** The caption. */
	private String caption;

	/**
	 * Gets the column headers.
	 *
	 * @return the column headers
	 */
	public List<String> getColumnHeaders() {
		return columnHeaders;
	}

	/**
	 * Sets the column headers.
	 *
	 * @param columnHeaders the new column headers
	 */
	public void setColumnHeaders(List<String> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public List<Row> getRows() {
		return rows;
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}
