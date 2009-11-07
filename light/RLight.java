package light;

import geom.*;
import objects.*;

/**
 * Svetlo reflektoru
 */
public class RLight implements Light {

	/**
	 * Pozice svetla
	 */
	private Vector3D p = new Vector3D();
	
	/**
	 * Osvetlovany bod
	 */
	private Vector3D p2 = new Vector3D();
	
	/**
	 * Uhel rozevreni kuzele
	 */
	private double y;
	
	/**
	 * Barva svetla
	 */
	private ColorRGB c = new ColorRGB();
	
	/**
	 * Koeficient utlumu svetla
	 */
	private Vector3D a = new Vector3D();
	
	/**
	 * Exponent utlumu uvnitr kuzele
	 */
	private double n;
	
	
	/**
	 * Konstruktor
	 * @param p Pozice svetla
	 * @param p2 Osvetlovany bod
	 * @param y Uhel rozevreni kuzele
	 * @param c Barva svetla
	 * @param a Koeficient utlumu svetla
	 * @param n Exponent utlumu uvnitr kuzele
	 */
	public RLight(Vector3D p, Vector3D p2, double y,
			ColorRGB c, Vector3D a, double n) {
		set(p, p2, y, c, a, n);
	}
	
	
	/**
	 * Nastavi vlastnosti svetla
	 * @param p Pozice svetla
	 * @param p2 Osvetlovany bod
	 * @param y Uhel rozevreni kuzele
	 * @param c Barva svetla
	 * @param a Koeficient utlumu svetla
	 * @param n Exponent utlumu uvnitr kuzele
	 */
	public void set(Vector3D p, Vector3D p2, double y,
			ColorRGB c, Vector3D a, double n) {
		this.p.set(p);
		this.p2.set(p2);
		this.y = y * Math.PI / 180;
		this.c.set(c);
		this.a.set(a);
		this.n = n;
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
	 * Nastavi osvetlovany bod
	 * @param p2 Osvetlovany bod
	 */
	public void setP2(Vector3D p2) {
		this.p2.set(p2);
	}
	
	/**
	 * Vrati osvetlovany bod
	 * @return Osvetlovany bod
	 */
	public Vector3D getP2() {
		return new Vector3D(p2);
	}
	
	/**
	 * Nastavi uhel rozevreni kuzele
	 * @param y Uhel rozevreni kuzele
	 */
	public void setY(double y) {
		this.y = y * Math.PI / 180;
	}
	
	/**
	 * Vrati uhel rozevreni kuzele
	 * @return Uhel rozevreni kuzele
	 */
	public double getY() {
		return y * 180 / Math.PI;
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
	 * Nastavi exponent utlumu uvnitr kuzele
	 * @param n Exponent utlumu uvnitr kuzele
	 */
	public void setN(double n) {
		this.n = n;
	}
	
	/**
	 * Vrati exponent utlumu uvnitr kuzele
	 * @return Exponent utlumu uvnitr kuzele
	 */
	public double getN() {
		return n;
	}
	
	
	/**
	 * Vypocte trojuhelniku prispevky svetla ve vrcholech,
	 * pro Gouraudovu metodu
	 * @param t Trojuhelnik
	 * @param prp Projection Reference Point
	 */
	public void myLight(Triangle t, Vector3D prp) {
		myLightPoint(new Vector3D(t.p1), t.n1, t.c1, t.diff);		
		myLightPoint(new Vector3D(t.p2), t.n2, t.c2, t.diff);
		myLightPoint(new Vector3D(t.p3), t.n3, t.c3, t.diff);
	}
	
	/**
	 * Vypocte prispevek svetla v jednom vrcholu trojuhelniku,
	 * pro Gouraudovu metodu
	 * @param pt Vrchol trojuhelniku
	 * @param nor Normala vrcholu
	 * @param color Barva vrcholu
	 * @param diff Difuzni koeficient odrazu trojuhelniku
	 */
	private void myLightPoint(Vector3D pt, Vector3D nor, ColorRGB color,
			ColorRGB diff) {
		Vector3D
				// vektor z vrcholu trojuhelniku do bodu svetla
				l = new Vector3D(),
				// osa reflektoru
				axis = new Vector3D();
		// pomocna barva
		ColorRGB ctmp = new ColorRGB();
		double
				// koeficient utlumu
				fatt,
				// vzdalenost svetla od vrcholu trojuhelniku
				d,
				// kosinus uhlu mezi normalou a paprskem svetla
				cosfi,
				// kosinus gama na n
				cosny,
				// uhel sevreni mezi osou reflektoru a paprskem
				gama;
		
		// vypocet vektoru paprsku
		l.set(p); l.sub(pt);
		// vypocet vzdalenosti svetla od vrcholu trojuhelniku
		d = l.length();
		l.normalize();
		// vypocet osy reflektoru
		axis.set(p); axis.sub(p2); axis.normalize();
		// vypocet uhlu gama
		gama = Math.acos(l.dot(axis));
		// vypocet kosinu gama na n
		cosny = Math.pow(l.dot(axis), n);
		
		if (gama < y && gama >= 0) {
			// vypocet kosinu uhlu dopadu
			cosfi = nor.dot(l);
			
			if (cosfi < Definitions.EPSILON)
				cosfi = 0;
			
			// vypocet koeficientu utlumu
			if (a.length() != 0) {
				fatt = 1.0 / (a.getX() + a.getY() * d + a.getZ() * d * d);
				if (fatt > 1)
					fatt = 1;
			}
			else fatt = 1;
			
			ctmp.set(c);
			ctmp.mul(cosny); ctmp.mul(fatt);
			ctmp.mul(diff); ctmp.mul(cosfi);
			color.add(ctmp);
		}
	}  // myLightPoint
	
	
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
		ColorRGB
				// vysledna barva
				color = new ColorRGB(),
				// pomocne barvy
				ctmp1 = new ColorRGB(),
				ctmp2 = new ColorRGB();
		Vector3D
				// vektor z vrcholu trojuhelniku do bodu svetla
				l = new Vector3D(),
				// smer odrazeneho paprsku
				r = new Vector3D(),
				// smer pozorovani
				v = new Vector3D(),
				// osa reflektoru
				axis = new Vector3D(),
				// pomocny 3D vektor
				pt = new Vector3D();
		double
				// koeficient utlumu
				fatt,
				// vzdalenost svetla od vrcholu trojuhelniku
				d,
				// kosinus uhlu mezi normalou a paprskem svetla
				cosfi,
				// kosinus gama na n
				cosny,
				// uhel sevreni mezi osou reflektoru a paprskem
				gama,
				// kosinus uhlu mezi odrazenym paprskem a smerem pozorovani
				cosalpha;
		
		pt.set(point);
		// vypocet vektoru paprsku
		l.set(p); l.sub(pt);
		// vypocet vzdalenosti svetla od bodu
		d = l.length();
		l.normalize();
		// vypocet osy reflektoru
		axis.set(p); axis.sub(p2); axis.normalize();
		// vypocet uhlu gama
		gama = Math.acos(l.dot(axis));
		// vypocet kosinu gama na n
		cosny = Math.pow(l.dot(axis), n);
		
		if (gama < y && gama >= 0) {
			// vypocet koeficientu utlumu
			if (a.length() != 0) {
				fatt = 1.0 / (a.getX() + a.getY() * d + a.getZ() * d * d);
				if (fatt > 1)
					fatt = 1;
			}
			else fatt = 1;
			
			// vypocet kosinu uhlu dopadu
			cosfi = nor.dot(l);
			
			// vypocet kosinu uhlu mezi odrazenym paprskem a smerem pozorovani
			v.set(prp); v.sub(point);
			v.normalize();
			r.set(nor); r.mul(nor.dot(l) * 2); r.sub(l);
			r.normalize();
			cosalpha = v.dot(r);
			
			if (cosfi < Definitions.EPSILON)
				cosfi = 0;
			if (cosalpha < Definitions.EPSILON)
				cosalpha = 0;
			cosalpha = Math.pow(cosalpha, 10);

			color.set(c);
			color.mul(cosny); color.mul(fatt);
			
			ctmp1.set(diff); ctmp1.mul(cosfi);
			ctmp2.set(spec); ctmp2.mul(cosalpha);
			ctmp1.add(ctmp2);
			
			color.mul(ctmp1);
		}
		
		return color;
	}  // myLight
	
}
