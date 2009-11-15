package objects;

import shader.ShadingType;
import geom.*;

/**
 * Trojuhelnik
 */
public class Triangle {
	
	/**
	 * Vrchol trojuhelniku
	 */
	public Vector3Dh p1 = new Vector3Dh(),
			p2 = new Vector3Dh(),
			p3 = new Vector3Dh();
	
	/**
	 * Normala ve vrcholu trojuhelniku
	 */
	public Vector3D n1 = new Vector3D(),
			n2 = new Vector3D(),
			n3 = new Vector3D();
	
	/**
	 * Normala plochy trojuhelniku
	 */
	public Vector3D n = new Vector3D();
	
	/**
	 * Barva ve vrcholu trojuhelniku
	 */
	public ColorRGB c1 = new ColorRGB(),
			c2 = new ColorRGB(),
			c3 = new ColorRGB();
	
	/**
	 * Difuzni koeficient odrazu
	 */
	public ColorRGB diff = new ColorRGB();
	
	/**
	 * Zrcadlovy koeficient odrazu
	 */
	public ColorRGB spec = new ColorRGB();
	
	/**
	 * Typ stinovani
	 */
	public ShadingType shadingType;

	
	/**
	 * Konstruktor
	 * @param p1 1. vrchol trojuhelniku
	 * @param p2 2. vrchol trojuhelniku
	 * @param p3 3. vrchol trojuhelniku
	 * @param n1 Normala v 1. vrcholu trojuhelniku
	 * @param n2 Normala v 2. vrcholu trojuhelniku
	 * @param n3 Normala v 3. vrcholu trojuhelniku
	 * @param c1 Barva v 1. vrcholu trojuhelniku
	 * @param c2 Barva v 2. vrcholu trojuhelniku
	 * @param c3 Barva v 3. vrcholu trojuhelniku
	 * @param n Normala plochy trojuhelniku
	 */
	public Triangle(Vector3D p1, Vector3D p2, Vector3D p3,
			Vector3D n1, Vector3D n2, Vector3D n3,
			ColorRGB c1, ColorRGB c2, ColorRGB c3,
			Vector3D n) {
		set(p1, p2, p3, n1, n2, n3, c1, c2, c3, n);
	}
	
	
	/**
	 * Dosadi nove hodnoty
	 * @param p1 1. vrchol trojuhelniku
	 * @param p2 2. vrchol trojuhelniku
	 * @param p3 3. vrchol trojuhelniku
	 * @param n1 Normala v 1. vrcholu trojuhelniku
	 * @param n2 Normala v 2. vrcholu trojuhelniku
	 * @param n3 Normala v 3. vrcholu trojuhelniku
	 * @param c1 Barva v 1. vrcholu trojuhelniku
	 * @param c2 Barva v 2. vrcholu trojuhelniku
	 * @param c3 Barva v 3. vrcholu trojuhelniku
	 * @param n Normala plochy trojuhelniku
	 */
	public void set(Vector3D p1, Vector3D p2, Vector3D p3,
			Vector3D n1, Vector3D n2, Vector3D n3,
			ColorRGB c1, ColorRGB c2, ColorRGB c3,
			Vector3D n) {
		this.p1.set(p1);
		this.p2.set(p2);
		this.p3.set(p3);
		this.n1.set(n1);
		this.n2.set(n2);
		this.n3.set(n3);
		this.c1.set(c1);
		this.c2.set(c2);
		this.c3.set(c3);
		this.n.set(n);
		this.shadingType = ShadingType.GOUARD;
		this.diff.set(0, 0, 0);
	}
	
	
	/**
	 * Dosadi hodnoty podle vzoru
	 * @param source Vzor
	 */
	public void set(Triangle source) {
		p1.set(source.p1);
		p2.set(source.p2);
		p3.set(source.p3);
		n1.set(source.n1);
		n2.set(source.n2);
		n3.set(source.n3);
		n.set(source.n);
		c1.set(source.c1);
		c2.set(source.c2);
		c3.set(source.c3);
		diff.set(source.diff);
		spec.set(source.spec);
	}
	
}
