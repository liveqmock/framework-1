package com.mopon.views;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.mopon.annotation.PDF;
import com.mopon.annotation.PaperType;
import com.mopon.util.TypeFormat;

/**
 * PDF 视图生成类
 * <p>PDF视图生成类根据实体对象的注解,解析实体对象所要生成的PDF类型,分为文档,表格,图片3种类型</p>
 * @author reaganjava
 * @version v1.0
 */
public class ViewPDF extends AbstractPdfView {
	
	@Autowired
	private TypeFormat typeFormat;

	/**
	 * 构建PDF文档方法
	 * <p>从控制器中读取对象生成PDF文件,该方法继承之AbstractPdfView的buildPdfDocument</p>
	 * @param model 试图模型对象,以K/V的方式存储传输数据
	 * @param document PDF文档对象由基类创建
	 * @param writer PDF流对象用于写PDF文件
	 * @param request HTTP请求
	 * @param response HTTP响应
	 * @exception Exception
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object obj = model.get("PDFData");
		buildPDF(obj, document, writer);
	}
	
	/**
	 * 构建PDF基础内容方法
	 * <p>从控制器中读取对象生成PDF文件,该方法继承之AbstractPdfView的buildPdfDocument</p>
	 * @param obj 需要转换的对象根据对象类型自动判断是集合还是单独对像
	 * @param document PDF文档对象
	 * @param writer PDF流对象用于写PDF文件
	 * @exception Exception
	 */
	public void buildPDF(Object obj, Document document, PdfWriter writer) throws Exception {
		Object o = null;
		//判断是否为集合对象
		if(obj.getClass().getName().indexOf("ArrayList") != -1) {
			List<Object> objectList = (List<Object>) obj;
			o = objectList.get(0);
		} else {
			o = obj;
		}
		Class<?> clazz = o.getClass();
		PDF pdf = clazz.getAnnotation(PDF.class);
		PaperType paperType = pdf.paperType();
		//设置页面
		Rectangle rectPageSize = new Rectangle(paperType.getRectangle());
		document.setPageSize(rectPageSize);
		//设置边距
		int top = 10;
		int bottom = 10;
		int left = 10;
		int right = 10;
		if(pdf.borderTop() != 0) {
			top = pdf.borderTop();
		}
		if(pdf.borderBottom() != 0) {
			bottom = pdf.borderBottom();
		}
		if(pdf.borderLeft() != 0) {
			left = pdf.borderLeft();
		}
		if(pdf.borderRight() != 0) {
			right = pdf.borderRight();
		}
		document.setMargins(left, right, top, bottom);
		//文档名称
		String filename = pdf.filename();
		if(filename.equals("")) {
			filename = System.currentTimeMillis() + ".pdf";
		}
		//标题
		String title = pdf.title();
		if(!title.equals("")) {
			document.addTitle(title);
		}
		//主题
		String subject = pdf.subject();
		if(!subject.equals("")) {
			document.addSubject(subject);
		}
		//作者
		String author = pdf.author();
		if(!author.equals("")) {
			document.addAuthor(author);
		}
		//关键字
		String keywords = pdf.keywords();
		if(!author.equals("")) {
			document.addKeywords(keywords);
		}	
		//设置文档头与低
		buildDocHeaderFooter(document, pdf);
		//判断文档类型
		switch(pdf.type()) {
			case TABLE:  { 
				Table table = buildTable((List<Object>) obj, pdf); 
				document.add(table);
				break;
			}
			case WORD: {
				document = buildText(document, obj); 
				break;
			}
		}
	}
	
	
	/**
	 * 构建文档类PDF
	 * <p>构建文档类的PDF文件</p>
	 * @param document PDF文档对象
	 * @param obj 转换的实体对象
	 * @return Document 返回包装好的文档对象
	 * @exception Exception
	 */
	private Document buildText(Document document, Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		//处理每个字段
		for(Field field : clazz.getDeclaredFields()) {
			PDF pdf = field.getAnnotation(PDF.class);
			int size = pdf.fontSize();
			int style = pdf.fontStyle().getFontStyleValue();
			Color color = pdf.fontColor().getRGB();
			switch(pdf.type()) {
				case TABLE: {
					List<Object> objList = (List<Object>) invokeMethod(obj, field.getName(), null);
					Object o = objList.get(0);
					PDF cellPdf = o.getClass().getAnnotation(PDF.class);
					Table table = buildTable(objList, cellPdf);
					document.add(table);
					break;
				}
				case WORD: {
					Paragraph par = new Paragraph(getFieldValue(obj, field), getFont(size, style, color));
					document.add(par);
					break;
				}
				case PICTURE: {
					String path = pdf.srcPath();
					int w = pdf.width();
					int h = pdf.height();
					Image jpeg = Image.getInstance(path);
		            jpeg.scaleAbsolute(w, h);
		            jpeg.setAlignment(Image.ALIGN_CENTER); 
		            document.add(jpeg);
					break;
				}
			}
		}
		return document;
	}
	
	/**
	 * 字段格式化
	 * <p>根据字段的注解类型格式化成指定的字符串类型</p>
	 * @param obj 转换的实体对象
	 * @param field 字段对象
	 * @return String 返回转换好的字符串
	 * 
	 */
	private String getFieldValue(Object obj, Field field) {
		PDF fieldPdf = field.getAnnotation(PDF.class);
		Object value = invokeMethod(obj, field.getName(), null);
		String formatValue = "";
		switch(fieldPdf.fieldType()) {
			case STRING: {
				formatValue = value.toString();
				break;
			}
			case MONEY: {
				formatValue = typeFormat.formatMoney((double)value);
				break;
			}
			case DATE: {
				formatValue = typeFormat.formatCNSimple(new Date((long)value));
				break;
			}
			case DOUBLE: {
				formatValue = typeFormat.formatDouble((double)value);
				break;
			}
			case NUMBER: {
				formatValue = value.toString();
				break;
			}
		}
		return formatValue;
	}
	
	
	/**
	 * 构建表格
	 * <p>构建表格类型的文档或者是文档类型嵌套的表格</p>
	 * @param objList 对象集合
	 * @param PDF 注解对象
	 * @return Table 返回构建好的表格对象
	 * @exception Exception
	 */
	private Table buildTable(List<Object> objList, PDF pdf) throws Exception {
		
		int row = pdf.tableRow();
		Table table = new Table(row);
		String[] rowNames = pdf.tableHeader().split(",");
		int size = pdf.fontSize();
		int style = pdf.fontStyle().getFontStyleValue();
		Color color = pdf.fontColor().getRGB();
		//设置表格头
		for(String rowName : rowNames) {
			System.out.println(rowName);
			table.addCell(buildCell(rowName, getFont(size, style, color)));
		}
		//填充表格内容
		for(Object obj : objList) {
			for(Field field : obj.getClass().getDeclaredFields()) {
				table.addCell(buildCell(getFieldValue(obj, field), getFont(size, style, color)));
			}
		}
		return table;
	}

	/**
	 * 构建PDF的头部和底部
	 * <p>根据注解构建PDF文档的头部信息与底部信息</p>
	 * @param document PDF文档对象
	 * @param PDF 注解对象
	 * @return Document 返回包装好的文档对象
	 * @exception Exception
	 */
	private void buildDocHeaderFooter(Document document, PDF pdf) throws Exception {
		String headerMsg = pdf.header();
		String footerMsg = pdf.footer();
		int size = pdf.fontSize();
		int style = pdf.fontStyle().getFontStyleValue();
		Color color = pdf.fontColor().getRGB();
		if(!headerMsg.equals("")) {
			HeaderFooter header = new HeaderFooter(new Paragraph(headerMsg, getFont(size, style, color)), false);
			header.setAlignment(Element.ALIGN_CENTER);
			document.setHeader(header);
		}
		if(!footerMsg.equals("")) {
			HeaderFooter footer = new HeaderFooter(new Paragraph(footerMsg, getFont(size, style, color)), false);
			footer.setAlignment(Element.ALIGN_CENTER);
			document.setFooter(footer);
		}
	}
	
	/**
	 * 返回字体
	 * <p>根据注解构建PDF文档的子体大小,风格,颜色</p>
	 * @param size 字体大小
	 * @param style 字体风格
	 * @param color 字体颜色
	 * @return Font 返回构建好的字体对象
	 * @exception Exception
	 */
	private Font getFont(int size, int style, Color color) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",  
                "UniGB-UCS2-H", false);  
        Font bold_fontChinese = new Font(bfChinese, size, style, color); 
        return bold_fontChinese;
	}
	
	/**
	 * 构建单元格
	 * <p>根据注解构建PDF表格的单元格</p>
	 * @param value 单元格值
	 * @param font 字体
	 * @return Cell 返回构建好的单元格
	 * @exception Exception
	 */
	private Cell buildCell(String value, Font font) throws Exception {
		return new Cell(new Phrase(value, font));
	}
	
	/**
	 * 执行某个Field的getField方法
	 * @param owner类
	 * @param fieldName 类的属性名称
	 * @param args参数，默认为null
	 * @return
	 */
	private Object invokeMethod(Object owner, String fieldName, Object[] args) {
		Class<? extends Object> ownerClass = owner.getClass();

		// fieldName -> FieldName
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);

		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {

			// e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			return "";
		}

		// invoke getMethod
		try {
			return method.invoke(owner);
		} catch (Exception e) {
			return "";
		}
	}

}
