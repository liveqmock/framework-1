package com.mopon.util;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;


/**
 * <p>Description: XML文档创建工具，用于创建XML文档</p>
 * @date 2013年8月28日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("xmlDom")
public class XmlDom {
	
	/**
	 * XML文档基础对象
	 */
	private Document document = DocumentHelper.createDocument();
	
	/**
	 * 根节点对象
	 */
	private Element rootNode;
	
	/**
	 * 方法用途:创建XML根节点 <br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @return 返回根节点的节点对象
	 */
	public Element createRoot(String nodeName) {
		rootNode = document.addElement(nodeName);
		return rootNode;
	}
	
	/**
	 * 方法用途:创建XML根节点 ，并可以为根节点加入文本<br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @param  text 文本内容
	 * @return 返回根节点的节点对象
	 */
	public Element createRoot(String nodeName, String text) {
		rootNode = createRoot(nodeName);
		rootNode.setText(text);
		return rootNode;
	}
	
	/**
	 * 方法用途:创建XML根节点 ，并可以为根节点加入文本<br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @param  attrbutes 属性集合
	 * @return 返回根节点的节点对象
	 */
	public Element createRoot(String nodeName, Map<String, String> attrbutes) {
		rootNode = createRoot(nodeName);
		addAttrbutes(rootNode, attrbutes);
		return rootNode;
	}
	
	/**
	 * 方法用途:创建XML根节点 ，并可以为根节点加入文本<br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @param  text 文本内容
	 * @param  attrbutes 属性集合
	 * @return 返回根节点的节点对象
	 */
	public Element createRoot(String nodeName, String text, Map<String, String> attrbutes) {
		rootNode = createRoot(nodeName, text);
		addAttrbutes(rootNode, attrbutes);
		return rootNode;
	}
	
	/**
	 * 方法用途:创建XML节点 <br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @return 返回根节点的节点对象
	 */
	public Element createNode(Element node, String nodeName) {
		Element childNode = node.addElement(nodeName);
		return childNode;
	}
	
	/**
	 * 方法用途:创建XML节点<br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @param  text 文本内容
	 * @return 返回根节点的节点对象
	 */
	public Element createNode(Element node, String nodeName, String text) {
		Element childNode = createNode(node, nodeName);
		childNode.setText(text);
		return childNode;
	}
	
	/**
	 * 方法用途:创建XML节点<br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @param  attrbutes 属性集合
	 * @return 返回根节点的节点对象
	 */
	public Element createNode(Element node, String nodeName, Map<String, String> attrbutes) {
		Element childNode = createNode(node, nodeName);
		addAttrbutes(childNode, attrbutes);
		return childNode;
	}

	/**
	 * 方法用途:创建XML节点<br>
	 * 实现步骤: <br>
	 * @param  nodeName跟节点的名称
	 * @param  text 文本内容
	 * @param  attrbutes 属性集合
	 * @return 返回根节点的节点对象
	 */
	public Element createNode(Element node, String nodeName, String text, Map<String, String> attrbutes) {
		Element childNode = createNode(node, nodeName);
		childNode.setText(text);
		addAttrbutes(childNode, attrbutes);
		return childNode;
	}
	
	/**
	 * 方法用途:添加节点属性<br>
	 * 实现步骤: <br>
	 * @param  node节点对象
	 * @param  attrbutes 属性集合
	 */
	private void addAttrbutes(Element node, Map<String, String> attrbutes) {
		if(attrbutes != null) {
			for(String key : attrbutes.keySet()) {
				node.addAttribute(key, attrbutes.get(key));
			}
		}
	}
	
	/**
	 * 方法用途:返回XML的字符串<br>
	 * 实现步骤: <br>
	 */
	public String getDocumnet() {
		return document.asXML();
	}

}
