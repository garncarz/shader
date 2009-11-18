package light;

import geom.*;
import objects.*;

/**
 * Ambientni svetlo
 */
public class ALight implements Light {

	/**
	 * Barva svetla
	 */	
	private ColorRGB c = new ColorRGB();
	
	
	/**
	 * Konstruktor
	 */
	public ALight() { }
	
	/**
	 * Konstruktor
	 * @param c Barva svetla
	 */
	public ALight(ColorRGB c) {
		this.c.set(c);
	}
	
	
	/**
	 * Nastavi barvu svetla
	 * @param c Barva svetla
	 */
	public void setC(ColorRGB c) {
		this.c.set(c);
	}
	
	/**
	 * Vrati barvu svetla
	 * @return Barva svetla
	 */
	public ColorRGB getC() {
		return new ColorRGB(c);
	}
	
	
	/**
	 * Vypocte trojuhelniku prispevky svetla ve vrcholech,
	 * pro Gouraudovu metodu
	 * @param t Trojuhelnik
	 * @param prp Projection Reference Point
	 */
	public void myLight(Triangle t, Vector3D prp) {
		ColorRGB ctmp = new ColorRGB(c);
		ctmp.mul(t.diff);
		
		t.c1.add(ctmp);
		t.c2.add(ctmp);
		t.c3.add(ctmp);
	}
	

	/**
	 * Vrati prispevek svetla pro dany bod
	 * @param prp Projection Reference Point
	 * @param point Bod
	 * @param nor Normala bodu
	 * @param diff Difuzni koeficient odrazu
	 * @param spec Zrcadlovy koeficient odrazu
	 * @return Prispevek svetla pro bod
	 */
	public ColorRGB myLight(Vector3D prp, Vector3D point,
			Vector3D nor, ColorRGB diff, ColorRGB spec) {
		ColorRGB color = new ColorRGB(diff);
		color.mul(c);
		return color;
	}

}
