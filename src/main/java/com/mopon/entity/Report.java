package com.mopon.entity;

import java.util.ArrayList;
import java.util.List;

import com.mopon.annotation.FieldType;
import com.mopon.annotation.PDF;
import com.mopon.annotation.PDFType;
import com.mopon.annotation.PaperType;

@PDF(type=PDFType.WORD, paperType=PaperType.A4)
public class Report {

	@PDF(type=PDFType.WORD, fieldType=FieldType.STRING)
	private String msg = "";
	
	@PDF(type=PDFType.TABLE)
	private List<Bill> bills = new ArrayList<Bill>();

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
}
