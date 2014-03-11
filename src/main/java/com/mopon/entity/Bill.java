package com.mopon.entity;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;



import com.mopon.annotation.FieldType;
import com.mopon.annotation.PDF;
import com.mopon.annotation.PDFType;
import com.mopon.annotation.PaperType;
import com.mopon.annotation.Xls;

@PDF(type=PDFType.TABLE, paperType=PaperType.A4, tableRow=5, tableHeader="ID,NAME,DATE,INCOME,PAY")
@Xls(templateName="template.xls", fileName="t1.xls", startRow=2) 
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name="Bill")
public class Bill {
	
	@PDF(fieldType=FieldType.NUMBER)
	@Xls(cellType=FieldType.NUMBER)
	private int bid;
	
	@PDF(fieldType=FieldType.STRING)
	@Xls(cellType=FieldType.STRING)
	private String compnay;
	
	@PDF(fieldType=FieldType.DATE)
	@Xls(cellType=FieldType.DATE)
	private long dateline;
	
	@PDF(fieldType=FieldType.MONEY)
	@Xls(cellType=FieldType.MONEY)
	private double income;
	
	@PDF(fieldType=FieldType.MONEY)
	@Xls(cellType=FieldType.MONEY)
	private double pay;
	
	

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getCompnay() {
		return compnay;
	}

	public void setCompnay(String compnay) {
		this.compnay = compnay;
	}

	public long getDateline() {
		return dateline;
	}

	public void setDateline(long dateline) {
		this.dateline = dateline;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getPay() {
		return pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

}
