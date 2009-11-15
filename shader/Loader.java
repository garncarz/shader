package shader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import geom.*;
import light.*;
import objects.*;

/**
 * Trida starajici se o nacteni definice sceny z XML souboru
 */
public class Loader {
	
	/**
	 * Zapracuje dany XML uzel obsahujici kvadr do sceny
	 * @param node XML uzel obsahujici kvadr
	 * @param scene Scena
	 */
	private static void processBlockNode(Node node, Scene scene) {
		Block object = new Block();
		scene.objects.add(object);
		
		double x = 0, y = 0, z = 0;
		double diffR = 0, diffG = 0, diffB = 0, diffS = 1;
		double specR = 0, specG = 0, specB = 0, specS = 1;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "x")
				x = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "y")
				y = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "z")
				z = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "l")
				object.l = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "w")
				object.w = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "h")
				object.h = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "dl")
				object.dl = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dw")
				object.dw = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffG")
				diffG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffB")
				diffB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffS")
				diffS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "specR")
				specR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specG")
				specG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specB")
				specB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specS")
				specS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "shading")
				object.shadingType = ShadingType.valueOf(att.getNodeValue());
		}
		
		object.translate(new Vector3D(x, y, z));
		
		ColorRGB c = new ColorRGB(diffR, diffG, diffB);
		c.mul(diffS);
		object.diff.set(c);
		
		c.set(specR, specG, specB);
		c.mul(specS);
		object.spec.set(c);
	}  // processBlockNode
	
	
	/**
	 * Zapracuje dany XML uzel obsahujici kouli do sceny
	 * @param node XML uzel obsahujici kouli
	 * @param scene Scena
	 */
	private static void processSphereNode(Node node, Scene scene) {
		Sphere object = new Sphere();
		scene.objects.add(object);
		
		double x = 0, y = 0, z = 0;
		double diffR = 0, diffG = 0, diffB = 0, diffS = 1;
		double specR = 0, specG = 0, specB = 0, specS = 1;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "x")
				x = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "y")
				y = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "z")
				z = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "radius")
				object.radius = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dv")
				object.dv = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffG")
				diffG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffB")
				diffB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffS")
				diffS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "specR")
				specR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specG")
				specG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specB")
				specB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specS")
				specS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "shading")
				object.shadingType = ShadingType.valueOf(att.getNodeValue());
		}
		
		object.translate(new Vector3D(x, y, z));
		
		ColorRGB c = new ColorRGB(diffR, diffG, diffB);
		c.mul(diffS);
		object.diff.set(c);
		
		c.set(specR, specG, specB);
		c.mul(specS);
		object.spec.set(c);
	}  // processSphereNode
	
	
	/**
	 * Zapracuje dany XML uzel obsahujici valec do sceny
	 * @param node XML uzel obsahujici valec
	 * @param scene Scena
	 */
	private static void processCylinderNode(Node node, Scene scene) {
		Cylinder object = new Cylinder();
		scene.objects.add(object);
		
		double x = 0, y = 0, z = 0;
		double diffR = 0, diffG = 0, diffB = 0, diffS = 1;
		double specR = 0, specG = 0, specB = 0, specS = 1;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "x")
				x = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "y")
				y = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "z")
				z = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "radius")
				object.radius = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "height")
				object.height = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dv")
				object.dv = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffG")
				diffG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffB")
				diffB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffS")
				diffS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "specR")
				specR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specG")
				specG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specB")
				specB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specS")
				specS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "shading")
				object.shadingType = ShadingType.valueOf(att.getNodeValue());
		}
		
		object.translate(new Vector3D(x, y, z));
		
		ColorRGB c = new ColorRGB(diffR, diffG, diffB);
		c.mul(diffS);
		object.diff.set(c);
		
		c.set(specR, specG, specB);
		c.mul(specS);
		object.spec.set(c);
	}  // processCylinderNode
	
	
	/**
	 * Zapracuje dany XML uzel obsahujici jehlan do sceny
	 * @param node XML uzel obsahujici jehlan
	 * @param scene Scena
	 */
	private static void processPyramidNode(Node node, Scene scene) {
		Pyramid object = new Pyramid();
		scene.objects.add(object);
		
		double x = 0, y = 0, z = 0;
		double diffR = 0, diffG = 0, diffB = 0, diffS = 1;
		double specR = 0, specG = 0, specB = 0, specS = 1;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "x")
				x = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "y")
				y = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "z")
				z = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "l")
				object.l = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "w")
				object.w = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "h")
				object.h = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "dl")
				object.dl = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dw")
				object.dw = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffG")
				diffG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffB")
				diffB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "diffS")
				diffS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "specR")
				specR = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specG")
				specG = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specB")
				specB = Double.valueOf(att.getNodeValue()).doubleValue();
			else if (att.getNodeName() == "specS")
				specS = Double.valueOf(att.getNodeValue()).doubleValue();
			
			else if (att.getNodeName() == "shading")
				object.shadingType = ShadingType.valueOf(att.getNodeValue());
		}
		
		object.translate(new Vector3D(x, y, z));
		
		ColorRGB c = new ColorRGB(diffR, diffG, diffB);
		c.mul(diffS);
		object.diff.set(c);
		
		c.set(specR, specG, specB);
		c.mul(specS);
		object.spec.set(c);
	}  // processPyramidNode
	
	
	/**
	 * Zapracuje dany XML uzel do sceny
	 * @param node XML uzel
	 * @param scene Scena
	 */
	private static void processNode(Node node, Scene scene) {
		if (node.getNodeName() == "block")
			processBlockNode(node, scene);
		else if (node.getNodeName() == "sphere")
			processSphereNode(node, scene);
		else if (node.getNodeName() == "cylinder")
			processCylinderNode(node, scene);
		else if (node.getNodeName() == "pyramid")
			processPyramidNode(node, scene);
	}
	
	/**
	 * Doplni scenu daty ze souboru 
	 * @param file Soubor
	 * @param scene Scena
	 */
	public static void load(String filename, Scene scene) throws Exception {
		try {
			Document doc = DocumentBuilderFactory.newInstance().
					newDocumentBuilder().parse(new File(filename));
			Element root = doc.getDocumentElement();
			
			NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
				processNode(children.item(i), scene);
			
		} catch (SAXException e) {
			throw new Exception("Chyba XML!");
		} catch (IOException e) {
			throw new Exception("Chyba pri otevirani souboru!");
		} catch (ParserConfigurationException e) {
			throw new Exception("Chyba XML!");
		}
		
	}
	
}
