package com.mopon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.mopon.annotation.Fields;
import com.mopon.annotation.Proto;
import com.mopon.util.TypeHandler;


/**
 * google buffer 通讯类生成工具
 * @author reaganjava
 * @version v1.0
 */
public class JavaTransformProtos {
	
	/**
	 * 源代码路径
	 */
	private String srcClassPath = "";
	
	/**
	 * proto文件路径
	 */
	private String protoFilePath = "";
	
	/**
	 * proto文件名
	 */
	private String protoFileName = "";
	
	/**
	 * 项目根目录
	 */
	private String projectDir = "";
	
	/**
	 * 需要转换的类包
	 */
	private String classPackage = "";
	
	
	/**
	 * 默认构造方法
	 * <p>传入需要转换的类包和生产那个proto文件的目标目录来初始化转换器</p>
	 * @param classPackage 转换包路径 列：com.pojo
	 * @param protoFileDir 转换后proto文件存放的位置，proto文件是对生成buffer类的基本描述
	 * @exception Exception
	 */
	public JavaTransformProtos(String classPackage, String protoFileDir) throws Exception {
		File currentFile = new File("");
		projectDir = currentFile.getCanonicalPath();
		this.classPackage = classPackage;
		System.out.println(classPackage);
		this.srcClassPath = projectDir + "/src/main/java/" + classPackage.replace(".", "/");;
		System.out.println(srcClassPath);
		this.protoFilePath = projectDir + "/" + protoFileDir;
		File protoDir = new File(this.protoFilePath);
		if(!protoDir.isDirectory()) {
			protoDir.mkdirs();
		}
	}
	
	/**
	 * 转换方法
	 * <p>调用该方法进行转换，先将类转换成proto文件，在调用proto.exe生成buffer类</p>
	 *
	 * @exception Exception
	 */
	public void startTransform() throws Exception {
		File pojoPackage = new File(srcClassPath);
		
		System.out.println(pojoPackage.getPath());
		for(String classFile : pojoPackage.list()) {
			String className = classFile.substring(0, classFile.indexOf("."));
			System.out.println(className);
			Class<?> clazz = Class.forName(classPackage + "." + className);
			System.out.println(clazz.getName());
			Proto proto = clazz.getAnnotation(Proto.class);
			if(proto != null && !proto.subClass()) {
				String protoFile = transform(clazz);
				if(protoFile != null) {
					System.out.println(protoFile);
					protoFileName = clazz.getSimpleName() + "Proto.proto";
					System.out.println(protoFilePath + "/" + protoFileName);
					File writeProtoFile = new File(protoFilePath + "/" + protoFileName);
					FileOutputStream out = new FileOutputStream(writeProtoFile);
					out.write(protoFile.getBytes());
					out.flush();
					out.close();
					
				}
			}
		}
		
		buildJava();
		
	}
	
	/**
	 * 编译方法
	 * <p>该方法调用proto.exe将proto目录下所有生成的proto文件转换成为buffer类</p>
	 *
	 * @exception Exception
	 */
	public void buildJava() throws Exception {
		Runtime run = Runtime.getRuntime();
		BufferedReader reader = null;
		File file = new File(protoFilePath);
		for(String protoName : file.list()) {
			System.out.println(protoName);
			String commend = projectDir + "/protoc.exe -I=" + protoFilePath + " --java_out=" + projectDir + "/src/main/java/" + " " +  protoFilePath + "/" + protoName;
			System.out.println(commend);
			Process process = run.exec(commend);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
		
	
	}

	/**
	 * 类转换方法
	 * <p>通过注解标识将类转换成为proto文件</p>
	 * @see Proto
	 * @see Fields
	 * @param clazz 需要转换的类
	 * @return protoFile 返回包装好的proto文件信息
	 * @exception Exception
	 */
	private String transform(Class<?> clazz) throws Exception {
		Proto proto = clazz.getAnnotation(Proto.class);
		if(proto != null) {
			String protoFile = "";
			String protoPackage = "";
			String packageName = "";
			String className = "";
			if(!proto.subClass()) {
				if(!proto.protoPackage().equals("")) {
					protoPackage = "package " + proto.protoPackage() + ";\n";
				}
				if(!proto.packageName().equals("")) {
					packageName = "option java_package = \"" + proto.packageName() + "\";\n";
				} else {
					System.out.println("default:" + clazz.getPackage());
					packageName = "option java_package = \"" + clazz.getPackage() + "\";\n";
				} 
				if(!proto.className().equals("")) {
					className = "option java_outer_classname = \"" + proto.className() + "\";\n";
				} else {
					System.out.println("default:" + clazz.getSimpleName());
					className = "option java_outer_classname = \"" + clazz.getSimpleName() + "Buffer\";\n";
				}
			}
			String messageName = "message " + clazz.getSimpleName() + " {\n";
			String protoFields = "";
			String subProto = "";
			String enumType = "";
			int defaultIndex = 1;
			for(Field field : clazz.getDeclaredFields()) {
				String protoField = "";
				Fields fieldAnno = field.getAnnotation(Fields.class);
				if(fieldAnno != null) {
					
				String fieldType = fieldAnno.fieldType();
				String fieldName = fieldAnno.fieldName();
				String protoType = fieldAnno.protoType();
				int index = fieldAnno.fieldIndex();
				String mapping = fieldAnno.mapping();
				String defValue = fieldAnno.defValue();
				String enums = fieldAnno.enums();
				
					if(!fieldType.equals("")) {
						protoField += fieldType;
					}
					
					if(!protoType.equals("")) {
						protoField += " " + protoType;
					} else if(enums.equals("") && mapping.equals("")){
						protoType = TypeHandler.getProtoType(field.getType().getName());
						if(protoType == null) {
							throw new Exception("@Fields mapping error Class can not find");
						}
						protoField += " " + protoType;
					} else if(!enums.equals("")){
						Class<?> enumClass = Class.forName(enums);
						int enumIndex = 1;
						String enumName = enumClass.getSimpleName();
						enumType = "enum " + enumName + " {\n";
						
						for(Field enumField : enumClass.getFields()) {
							enumType += enumField.getName() + "=" + enumIndex + ";\n";
							enumIndex++;
						}
						enumType += "}\n";
						protoFields += enumType;
						protoField += " " + enumName;
					}
					
					if(!mapping.equals("")) {
						Class<?> mappingClazz = Class.forName(mapping);
						subProto = transform(mappingClazz);
						protoFields += subProto;
						protoField += " " + mappingClazz.getSimpleName();
					}
					
					if(!fieldName.equals("")) {
						protoField += " " + fieldName;
					} else {
						protoField += " " + field.getName(); 
					}
					
					if(index != -1) {
						protoField += " = " + index;
					} else {
						protoField += " = " + defaultIndex; 
					}
					
					if(!defValue.equals("")) {
						switch(protoType) {
							case "string" : {
								defValue = "\"" + defValue + "\"";
								break;
							}
							case "float" : {
								defValue = "" + Float.parseFloat(defValue);
								break;
							}
							case "double" : {
								defValue = "" + Double.parseDouble(defValue);
								break;
							}
							case "int32":
							case "int64": {
								defValue = "" + Integer.parseInt(defValue);
								break;
							}
						}
						protoField += " [default = " + defValue + "]";
					}
	
					protoFields += protoField +";\n";
					protoField = "";
					defaultIndex++;
				}
			}
			protoFile = protoPackage + packageName + className + messageName + protoFields;
			protoFile += "}\n";
			return protoFile;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		JavaTransformProtos jtp = new JavaTransformProtos("com.mopon.entity", "proto");
		jtp.startTransform();
	}
	
}
