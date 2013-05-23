package ltg.commons.phenomena_handler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class PhenomenaXMLUtils {
	
	public static String removeXMLDeclaration(Document doc) {
		StringWriter w = new StringWriter();
		OutputFormat f =  OutputFormat.createPrettyPrint();
		f.setSuppressDeclaration(true);
		XMLWriter xw = new XMLWriter(w, f);
		try {
			xw.write(doc);
		} catch (IOException e1) {
			System.err.println("Unable to print to a string? Really dom4j?");
		}
		return w.toString();
	}
	
	
	public static int parseIntElement(Element root, String element) throws DocumentException {
		try {
			return Integer.parseInt(root.elementTextTrim(element));
		} catch(NumberFormatException e) {
			throw new DocumentException();
		}
	}
	
	
	public static long parseLongElement(Element root, String element) throws DocumentException{
		try {
			return Long.parseLong(root.elementTextTrim(element));
		} catch(NumberFormatException e) {
			throw new DocumentException();
		}
	}
	
	
	public static double parseDoubleElement(Element root, String element) throws DocumentException{
		try {
			return Double.parseDouble(root.elementTextTrim(element));
		} catch(NumberFormatException e) {
			throw new DocumentException();
		}
	}
	
	
	public static String parseStringElement(Element root, String element) throws DocumentException{
		String el = root.elementTextTrim(element);
		if (el!=null && !el.isEmpty())
			return el;
		throw new DocumentException();
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Element> parseListElement(Element root, String element) throws DocumentException{
		Element list = root.element(element);
		if (list!=null)
			return list.elements();
		throw new DocumentException();
	}
	
	
	

}
