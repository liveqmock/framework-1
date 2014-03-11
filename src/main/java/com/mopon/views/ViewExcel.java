package com.mopon.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mopon.annotation.Xls;


/**
 * 
 * <p>Description: EXCEL视图转换类</p>
 * @date 2013年9月5日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ViewExcel extends AbstractExcelView {

	/**
	 * excel对象
	 */
	private HSSFWorkbook workbook;
	
	/**
	 * 输入流
	 */
	private FileInputStream is;
	
	/**
	 * 模板开始插入位置
	 */
	private int startRow;

	/**
	 * 方法用途: 构建EXCEL<br>
	 * 实现步骤: <br>
	 * @param model 包含数据的模型类
	 * @param workbook excel BOOK对象
	 * @param request 请求
	 * @param response 响应
	 * @throws Exception
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object obj = model.get("xlsData");
		
		String webRoot = request.getServletContext().getRealPath("/");
		if(obj.getClass().getName().indexOf("ArrayList") != -1) {
			List<Object> objectList = (List<Object>) obj;
			readTemplate(webRoot, objectList.get(0), response);
			for(int rowNum = 2; rowNum < objectList.size(); rowNum++) {
				writeRowList(objectList, response.getOutputStream());
			}
		} else {
			readTemplate(webRoot, obj, response);
			writeRowObject(obj, response.getOutputStream());
		} 
		
	}
	
	/**
	 * 
	 * 方法用途: 读取模板<br>
	 * 实现步骤: <br>
	 * @param webRoot 应用更目录
	 * @param obj 包装了注解的对象
	 * @param response 响应
	 * @throws Exception
	 */
	public void readTemplate(String webRoot, Object obj, HttpServletResponse response) throws Exception {
		if(obj == null) {
			throw new NullPointerException("对象为空不能解析模板");
		}
		
		Class<?> clazz = obj.getClass();
		Xls xls = clazz.getAnnotation(Xls.class);
		System.out.println(xls);
		String templateName = xls.templateName();
		String filename = xls.fileName();
		System.out.println(templateName + ":" + filename);
		String tempPath = webRoot + "/template/"
				+ templateName;
		startRow = xls.startRow();
		if (filename.equals("")) {
			filename = System.currentTimeMillis() + ".xls";
		}
		response.setHeader("Content-disposition", "attachment;filename=" + filename);  
		//String writePath = "e:/framework/src/main/webapp/template/" + filename;
		File xlsFile = new File(tempPath);
		if (!xlsFile.exists()) {
			throw new Exception("模板文件不存在");
		}
		is = new FileInputStream(xlsFile);
		workbook = new HSSFWorkbook(is);
	}
	
	/**
	 * 方法用途:写行集合数据 <br>
	 * 实现步骤: <br>
	 * @param objectList 集合对象
	 * @param out 输出对象
	 * @throws Exception
	 */
	public void writeRowList(List<Object> objectList, OutputStream out) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(int rowNum = 0; rowNum < objectList.size(); rowNum++) {
			Object obj = objectList.get(rowNum); 
			Class<?> clazz = obj.getClass();
			HSSFRow row = sheet.createRow(startRow);
			for (int cellNum = 0; cellNum < clazz.getDeclaredFields().length; cellNum++) {
				HSSFCell cell = row.createCell(cellNum);
				setCell(cell, clazz.getDeclaredFields()[cellNum], obj);
			}
		}
		workbook.write(out);
		workbook = null;
		objectList.clear();
		objectList = null;
		out.flush();
		out.close();
		is.close();
	}


	/**
	 * 方法用途:写行单个数据 <br>
	 * 实现步骤: <br>
	 * @param obj 对象
	 * @param out 输出对象
	 * @throws Exception
	 */
	public void writeRowObject( Object obj, OutputStream out) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		Class<?> clazz = obj.getClass();
		HSSFRow row = sheet.createRow(startRow);
		System.out.println(clazz.getDeclaredFields().length);
		for (int cellNum = 0; cellNum < clazz.getDeclaredFields().length; cellNum++) {
			HSSFCell cell = row.createCell(cellNum);
			setCell(cell, clazz.getDeclaredFields()[cellNum], obj);
		}
		workbook.write(out);
		workbook = null;
		obj = null;
		out.flush();
		out.close();
		is.close();
	}

	/**
	 * 
	 * 方法用途: 设置单元格<br>
	 * 实现步骤: <br>
	 * @param cell 单元格对象
	 * @param field 字段对象
	 * @param obj 放入单元格的值
	 */
	public void setCell(HSSFCell cell, Field field, Object obj) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		Xls fieldXls = field.getAnnotation(Xls.class);
		switch (fieldXls.cellType()) {
		case STRING: {
			if(fieldXls.cellBorder()) {
				setBorder(cellStyle);
			}
			cell.setCellStyle(cellStyle);
			Object value = invokeMethod(obj, field.getName(), null);
			cell.setCellValue((String) value);
			break;
		}
		case DATE: {
			if(fieldXls.cellBorder()) {
				setBorder(cellStyle);
			}
			cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));
			Object value = invokeMethod(obj, field.getName(), null);
			Date date = new Date((Long)value);
			cell.setCellValue(date);
			cell.setCellStyle(cellStyle);
			break;
		}
		case NUMBER: {
			if(fieldXls.cellBorder()) {
				setBorder(cellStyle);
			}
			Object value = invokeMethod(obj, field.getName(), null);
			cell.setCellValue((Integer) value);
			cell.setCellStyle(cellStyle);
			break;
		}
		case DOUBLE: {
			if(fieldXls.cellBorder()) {
				setBorder(cellStyle);
			}
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			Object value = invokeMethod(obj, field.getName(), null);
			cell.setCellValue((Double) value);
			cell.setCellStyle(cellStyle);
			break;
		}
		case MONEY: {
			if(fieldXls.cellBorder()) {
				setBorder(cellStyle);
			}
			cellStyle.setDataFormat(format.getFormat("¥#,##0.00"));
			Object value = invokeMethod(obj, field.getName(), null);
			cell.setCellValue((Double) value);
			cell.setCellStyle(cellStyle);
			break;
		}
		}
	}
	/**
	 * 
	 * 方法用途: 设置单元格边框<br>
	 * 实现步骤: <br>
	 * @param cellStyle
	 */
	public void setBorder(HSSFCellStyle cellStyle) {
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	}

	/**
	 * 
	 * 执行某个Field的getField方法
	 * 
	 * @param owner
	 *            类
	 * @param fieldName
	 *            类的属性名称
	 * @param args
	 *            参数，默认为null
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
