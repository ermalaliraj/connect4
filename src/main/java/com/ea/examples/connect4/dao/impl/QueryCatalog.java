package com.ea.examples.connect4.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryCatalog {
	
	protected static transient Log log = LogFactory.getLog(QueryCatalog.class);

	public static String getQuery_getCanvasByUserId(String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select e from CanvasEntity e ");
		sb.append("where e.userId = :userId ");
		return sb.toString();
	}

	public static String getQuery_getAllCanvas() {
		StringBuffer sb = new StringBuffer();
		sb.append("select e from CanvasEntity e ");
		return sb.toString();
	}

	public static String getQuery_deleteCanvasByUserId(String userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from CanvasEntity e ");
		sb.append("where e.userId = :userId ");
		return sb.toString();
	}

}
