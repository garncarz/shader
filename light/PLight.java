package light;

import geom.*;
import objects.*;

/**
 * Bodove svetlo
 */
public class PLight implements Light {

	/**
	 * Pozice svetla
	 */
	private Vector3D p = new Vector3D();
	
	/**
	 * Barva svetla
	 */
	private ColorRGB c = new ColorRGB();
	
	/**
	 * Koeficient utlumu svetla
	 */
	private Vector3D a = new Vector3D();
	
	
	/**
	 * Konstruktor
	 */
	public PLight() { }
	
	/**
	 * Konstruktor
	 * @param p Pozice svetla
	 * @param c Barva svetla
	 * @param a Koeficient utlumu svetla
	 */
	public PLight(Vector3D p, ColorRGB c, Vector3D a) {
		set(p, c, a);
	}
	
	
	/**
	 * Nastavi vlastnosti svetla 
	 * @param p Pozice svetla
	 * @param c Barva svetla
	 * @param a Koeficient utlumu svetla
	 */
	public void set(Vector3D p, ColorRGB c, Vector3D a) {
		this.p.set(p);
		this.c.set(c);
		this.a.set(a);
	}
	
	/**
	 * Nastavi pozici svetla
	 * @param p Pozice svetla
	 */
	public void setP(Vector3D p) {
		this.p.set(p);
	}
	
	/**
	 * Vrati pozici svetla
	 * @return Pozice svetla
	 */
	public Vector3D getP() {
		return new Vector3D(p);
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
	 * Nastavi koeficient utlumu svetla
	 * @param a Koeficient utlumu svetla
	 */
	public void setA(Vector3D a) {
		this.a.set(a);
	}
	
	/**
	 * Vrati koeficient utlumu svetla
	 * @return Koeficient utlumu svetla
	 */
	public Vector3D getA() {
		return new Vector3D(a);
	}
	
	
	/**
	 * Vypocte trojuhelniku prispevky svetla ve vrcholech,
	 * pro Gouraudovu metodu
	 * @param t Trojuhelnik
	 * @param prp Projection Reference Point
	 */
	public void myLight(Triangle t, Vector3D prp) {
		t.c1.add(myLight(new Vector3D(prp), new Vector3D(t.p1),
				t.n1, t.diff, t.spec));
		t.c2.add(myLight(new Vector3D(prp), new Vector3D(t.p2),
				t.n2, t.diff, t.spec));
		t.c3.add(myLight(new Vector3D(prp), new Vector3D(t.p3),
				t.n3, t.diff, t.spec));
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
		// osvetleni bodu - barva
		ColorRGB color = new ColorRGB(),
				tmp = new ColorRGB(); 
		Vector3D
				// vektor z vrcholu trojuhelniku do pozice svetla
				l = new Vector3D(),
				// smer odrazeneho paprsku
				r = new Vector3D(),
				// smer pozorovani
				v = new Vector3D(),
				// pomocny vektor
				u = new Vector3D();
		double
				// koeficient utlumu
				fatt,
				// vzdalenost svetla od vrcholu trojuhelniku
				d,
				// kosinus uhlu mezi normalou a paprskem svetla
				cosfi,
				// kosinus uhlu mezi odrazenym paprskem a smerem pozorovani
				cosalpha;
		
		l.set(p); l.sub(point);
		d = l.length();
		fatt = 1.0 / (a.getX() + a.getY() * d + a.getZ() * d * d);
		l.normalize(); nor.normalize();
		cosfi = l.dot(nor);
		color.set(c);
		color.mul(fatt);
		color.mul(cosfi);
		color.mul(diff);
		
		r.set(nor); r.mul(2); r.mul(cosfi); r.sub(l);
		v.set(prp); v.sub(point);
		r.normalize(); v.normalize();
		cosalpha = v.dot(r);
		
		cosalpha = Math.pow(cosalpha, 10);
		tmp.set(c);
		tmp.mul(fatt);
		tmp.mul(cosalpha);
		tmp.mul(spec);
		
		color.add(tmp);
		
		return color;
	}
	
}
