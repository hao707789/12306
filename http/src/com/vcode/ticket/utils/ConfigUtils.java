package com.vcode.ticket.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigUtils {
	
	public Map<String, String> map;
	
	private File file;
	
	public static ConfigUtils configUtils;
	
	public static ConfigUtils getInstace() throws Exception{
		if (configUtils==null) {
			configUtils = new ConfigUtils();
		}
		return configUtils;
	}

	private ConfigUtils() throws Exception{
		map = new HashMap<String, String>();
		file = new File(this.getClass().getResource("../").getPath()+"conf/xconf.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder = factory.newDocumentBuilder();   
		Document doc = builder.parse(file);   
		NodeList nodeList = doc.getElementsByTagName("stor");
		for (int i=0;i<nodeList.getLength();i++) {
			Node node = nodeList.item(i);
			String content = node.getTextContent();
			String [] data = content.split("\\|");
			map.put(new String(Base64.decode(data[0])), new String(Base64.decode(data[1])));
		}
	}
	
	public void rememberPass(String[] str) throws Exception, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder = factory.newDocumentBuilder();   
		Document doc = builder.parse(file); 
		NodeList nodeList = doc.getElementsByTagName("stor");
		Node node = doc.getElementsByTagName("storkey").item(0);
		String stor = Base64.encode(str[0].getBytes())+"|"+Base64.encode(str[1].getBytes());
		
		for (int i=0;i<nodeList.getLength();i++) {
			Node node1 = nodeList.item(i);
			String content = node1.getTextContent();
			if (stor.split("\\|")[0].equals(content.split("\\|")[0])) {
				node.removeChild(node1);
			}
		}
		Element el = doc.createElement("stor");
		el.setTextContent(stor);
		node.appendChild(el);
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);
	}
	
	public void removePass(String[] str) throws Exception, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		DocumentBuilder builder = factory.newDocumentBuilder();   
		Document doc = builder.parse(file); 
		NodeList nodeList = doc.getElementsByTagName("stor");
		Node storkey = doc.getElementsByTagName("storkey").item(0);
		String stor = Base64.encode(str[0].getBytes())+"|"+Base64.encode(str[1].getBytes());
		for (int i=0;i<nodeList.getLength();i++) {
			Node node = nodeList.item(i);
			String content = node.getTextContent();
			if (stor.equals(content)) {
				storkey.removeChild(node);
			}
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);
	}
	
	public static void main(String[] args) throws IOException, Exception {
		String [] str = new String[]{"hao707789","loveXIAO707789"};
		ConfigUtils.getInstace().removePass(str);
	}
}
