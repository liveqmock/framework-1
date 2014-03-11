package com.mopon.entity.sys;

import java.awt.Color;

/**
 * <p>Description: </p>
 * @date 2013年10月15日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum ResultType {

	XMLDATA("xmlData"),
	
	JSONDATA("jsonData"),
	
	XLSDATA("xlsData"),
	
	PROTODATA("protoDate"),
	
	PDFDATA("pdfData");
	
	
    private ResultType(String type) {  
       this.type = type;
    }  
    
   
    public String getType() {
    	return type;
    }
    
    
    public String toString() {
    	return super.toString() + type;
    }
    
	private String type;
}
