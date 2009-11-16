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
	 * Vrati double hodnotu atributu 
	 * @param att Atribut
	 * @return Double hodnota obsazena v atributu
	 */
	private static double dblVal(Node att) {
		return Double.valueOf(att.getNodeValue()).doubleValue();
	}
	
	
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
				x = dblVal(att);
			else if (att.getNodeName() == "y")
				y = dblVal(att);
			else if (att.getNodeName() == "z")
				z = dblVal(att);
			
			else if (att.getNodeName() == "l")
				object.l = dblVal(att);
			else if (att.getNodeName() == "w")
				object.w = dblVal(att);
			else if (att.getNodeName() == "h")
				object.h = dblVal(att);
			
			else if (att.getNodeName() == "dl")
				object.dl = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dw")
				object.dw = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = dblVal(att);
			else if (att.getNodeName() == "diffG")
				diffG = dblVal(att);
			else if (att.getNodeName() == "diffB")
				diffB = dblVal(att);
			else if (att.getNodeName() == "diffS")
				diffS = dblVal(att);
			
			else if (att.getNodeName() == "specR")
				specR = dblVal(att);
			else if (att.getNodeName() == "specG")
				specG = dblVal(att);
			else if (att.getNodeName() == "specB")
				specB = dblVal(att);
			else if (att.getNodeName() == "specS")
				specS = dblVal(att);
			
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
				x = dblVal(att);
			else if (att.getNodeName() == "y")
				y = dblVal(att);
			else if (att.getNodeName() == "z")
				z = dblVal(att);
			
			else if (att.getNodeName() == "radius")
				object.radius = dblVal(att);
			
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dv")
				object.dv = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = dblVal(att);
			else if (att.getNodeName() == "diffG")
				diffG = dblVal(att);
			else if (att.getNodeName() == "diffB")
				diffB = dblVal(att);
			else if (att.getNodeName() == "diffS")
				diffS = dblVal(att);
			
			else if (att.getNodeName() == "specR")
				specR = dblVal(att);
			else if (att.getNodeName() == "specG")
				specG = dblVal(att);
			else if (att.getNodeName() == "specB")
				specB = dblVal(att);
			else if (att.getNodeName() == "specS")
				specS = dblVal(att);
			
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
				x = dblVal(att);
			else if (att.getNodeName() == "y")
				y = dblVal(att);
			else if (att.getNodeName() == "z")
				z = dblVal(att);
			
			else if (att.getNodeName() == "radius")
				object.radius = dblVal(att);
			else if (att.getNodeName() == "height")
				object.height = dblVal(att);
			
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dv")
				object.dv = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = dblVal(att);
			else if (att.getNodeName() == "diffG")
				diffG = dblVal(att);
			else if (att.getNodeName() == "diffB")
				diffB = dblVal(att);
			else if (att.getNodeName() == "diffS")
				diffS = dblVal(att);
			
			else if (att.getNodeName() == "specR")
				specR = dblVal(att);
			else if (att.getNodeName() == "specG")
				specG = dblVal(att);
			else if (att.getNodeName() == "specB")
				specB = dblVal(att);
			else if (att.getNodeName() == "specS")
				specS = dblVal(att);
			
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
				x = dblVal(att);
			else if (att.getNodeName() == "y")
				y = dblVal(att);
			else if (att.getNodeName() == "z")
				z = dblVal(att);
			
			else if (att.getNodeName() == "l")
				object.l = dblVal(att);
			else if (att.getNodeName() == "w")
				object.w = dblVal(att);
			else if (att.getNodeName() == "h")
				object.h = dblVal(att);
			
			else if (att.getNodeName() == "dl")
				object.dl = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dw")
				object.dw = Integer.valueOf(att.getNodeValue()).intValue();
			else if (att.getNodeName() == "dh")
				object.dh = Integer.valueOf(att.getNodeValue()).intValue();
			
			else if (att.getNodeName() == "diffR")
				diffR = dblVal(att);
			else if (att.getNodeName() == "diffG")
				diffG = dblVal(att);
			else if (att.getNodeName() == "diffB")
				diffB = dblVal(att);
			else if (att.getNodeName() == "diffS")
				diffS = dblVal(att);
			
			else if (att.getNodeName() == "specR")
				specR = dblVal(att);
			else if (att.getNodeName() == "specG")
				specG = dblVal(att);
			else if (att.getNodeName() == "specB")
				specB = dblVal(att);
			else if (att.getNodeName() == "specS")
				specS = dblVal(att);
			
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
	 * Zapracuje dany XML uzel obsahujici kameru do sceny
	 * @param node XML uzel obsahujici kameru
	 * @param scene Scena
	 */
	private static void processCameraNode(Node node, Scene scene) {
		Camera cam = scene.cam;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "vrpX")
				cam.VRP.setX(dblVal(att));
			else if (att.getNodeName() == "vrpY")
				cam.VRP.setY(dblVal(att));
			else if (att.getNodeName() == "vrpZ")
				cam.VRP.setZ(dblVal(att));
			
			else if (att.getNodeName() == "vpnX")
				cam.VPN.setX(dblVal(att));
			else if (att.getNodeName() == "vpnY")
				cam.VPN.setY(dblVal(att));
			else if (att.getNodeName() == "vpnZ")
				cam.VPN.setZ(dblVal(att));
			
			else if (att.getNodeName() == "vupX")
				cam.VUP.setX(dblVal(att));
			else if (att.getNodeName() == "vupY")
				cam.VUP.setY(dblVal(att));
			else if (att.getNodeName() == "vupZ")
				cam.VUP.setZ(dblVal(att));
			
			else if (att.getNodeName() == "prpX")
				cam.PRP.setX(dblVal(att));
			else if (att.getNodeName() == "prpY")
				cam.PRP.setY(dblVal(att));
			else if (att.getNodeName() == "prpZ")
				cam.PRP.setZ(dblVal(att));
			
			else if (att.getNodeName() == "Umin")
				cam.setUmin(dblVal(att));
			else if (att.getNodeName() == "Umax")
				cam.setUmax(dblVal(att));
			else if (att.getNodeName() == "Vmin")
				cam.setVmin(dblVal(att));
			else if (att.getNodeName() == "Vmax")
				cam.setVmax(dblVal(att));
			
			else if (att.getNodeName() == "front")
				cam.setF(dblVal(att));
			else if (att.getNodeName() == "back")
				cam.setB(dblVal(att));
		}
		
	}  // processCameraNode
	
	
	/**
	 * Zapracuje dany XML uzel obsahujici ambientni svetlo do sceny
	 * @param node XML uzel obsahujici ambientni svetlo
	 * @param scene Scena
	 */
	private static void processALightNode(Node node, Scene scene) {
		ALight light = new ALight();
		scene.lights.add(light);
		
		double r = 0, g = 0, b = 0, s = 1;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "r")
				r = dblVal(att);
			else if (att.getNodeName() == "g")
				g = dblVal(att);
			else if (att.getNodeName() == "b")
				b = dblVal(att);
			else if (att.getNodeName() == "s")
				s = dblVal(att);
		}
		
		ColorRGB c = new ColorRGB(r, g, b);
		c.mul(s);
		light.setC(c);
	}  // processALightNode
	
	
	/**
	 * Zapracuje dany XML uzel obsahujici bodove svetlo do sceny
	 * @param node XML uzel obsahujici bodove svetlo
	 * @param scene Scena
	 */
	private static void processPLightNode(Node node, Scene scene) {
		PLight light = new PLight();
		scene.lights.add(light);
		
		double x = 0, y = 0, z = 0;
		double r = 0, g = 0, b = 0, s = 1;
		double attX = 0, attY = 0, attZ = 0;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "x")
				x = dblVal(att);
			else if (att.getNodeName() == "y")
				y = dblVal(att);
			else if (att.getNodeName() == "z")
				z = dblVal(att);
			
			else if (att.getNodeName() == "r")
				r = dblVal(att);
			else if (att.getNodeName() == "g")
				g = dblVal(att);
			else if (att.getNodeName() == "b")
				b = dblVal(att);
			else if (att.getNodeName() == "s")
				s = dblVal(att);
			
			else if (att.getNodeName() == "attX")
				attX = dblVal(att);
			else if (att.getNodeName() == "attY")
				attY = dblVal(att);
			else if (att.getNodeName() == "attZ")
				attZ = dblVal(att);
		}
		
		Vector3D v = new Vector3D(x, y, z);
		light.setP(v);
		
		ColorRGB c = new ColorRGB(r, g, b);
		c.mul(s);
		light.setC(c);
		
		v = new Vector3D(attX, attY, attZ);
		light.setA(v);
	}  // processPLightNode
	
	
	/**
	 * Zapracuje dany XML uzel obsahujici reflektorove svetlo do sceny
	 * @param node XML uzel obsahujici reflektorove svetlo
	 * @param scene Scena
	 */
	private static void processRLightNode(Node node, Scene scene) {
		RLight light = new RLight();
		scene.lights.add(light);
		
		double x = 0, y = 0, z = 0;
		double x2 = 0, y2 = 0, z2 = 0;
		double angle = 0;
		double r = 0, g = 0, b = 0, s = 1;
		double attX = 0, attY = 0, attZ = 0;
		double n = 0;
		
		NamedNodeMap atts = node.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			
			if (att.getNodeName() == "x")
				x = dblVal(att);
			else if (att.getNodeName() == "y")
				y = dblVal(att);
			else if (att.getNodeName() == "z")
				z = dblVal(att);
			
			else if (att.getNodeName() == "x2")
				x2 = dblVal(att);
			else if (att.getNodeName() == "y2")
				y2 = dblVal(att);
			else if (att.getNodeName() == "z2")
				z2 = dblVal(att);
			
			else if (att.getNodeName() == "angle")
				angle = dblVal(att);
			
			else if (att.getNodeName() == "r")
				r = dblVal(att);
			else if (att.getNodeName() == "g")
				g = dblVal(att);
			else if (att.getNodeName() == "b")
				b = dblVal(att);
			else if (att.getNodeName() == "s")
				s = dblVal(att);
			
			else if (att.getNodeName() == "attX")
				attX = dblVal(att);
			else if (att.getNodeName() == "attY")
				attY = dblVal(att);
			else if (att.getNodeName() == "attZ")
				attZ = dblVal(att);
			
			else if (att.getNodeName() == "n")
				n = dblVal(att);
		}
		
		Vector3D v = new Vector3D(x, y, z);
		light.setP(v);
		
		v = new Vector3D(x2, y2, z2);
		light.setP2(v);
		
		light.setY(angle);
		
		ColorRGB c = new ColorRGB(r, g, b);
		c.mul(s);
		light.setC(c);
		
		v = new Vector3D(attX, attY, attZ);
		light.setA(v);
		
		light.setN(n);
	}  // processRLightNode
	
	
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
		
		else if (node.getNodeName() == "alight")
			processALightNode(node, scene);
		else if (node.getNodeName() == "plight")
			processPLightNode(node, scene);
		else if (node.getNodeName() == "rlight")
			processRLightNode(node, scene);
		
		else if (node.getNodeName() == "camera")
			processCameraNode(node, scene);
	}
	
	/**
	 * Doplni scenu daty ze souboru 
	 * @param file Soubor
	 * @param scene Scena
	 * @throws Exception Chyba nacitani ci parsovani
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
