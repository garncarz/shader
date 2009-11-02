package objects;

import javax.vecmath.*;

/**
 * Trida reprezentujici trojuhelnik
 */
public class Triangle {
	
	/**
	 * Vrchol trojuhelniku
	 */
	Vector4d p1, p2, p3;
	
	/**
	 * Normala ve vrcholu trojuhelniku
	 */
	Vector3d n1, n2, n3;
	
	/**
	 * Normala plochy trojuhelniku
	 */
	Vector3d n;
	
	/**
	 * Barva ve vrcholu trojuhelniku
	 */
	Color3f c1, c2, c3;
	
	/**
	 * Difuzni koeficient odrazu
	 */
	Color3f diff;
	
	/**
	 * Zrcadlovy koeficient odrazu
	 */
	Color3f spec;
	
	/**
	 * Typ stinovani
	 */
	ShadingType shadingType;

	
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
	public Triangle(Vector3d p1, Vector3d p2, Vector3d p3,
			Vector3d n1, Vector3d n2, Vector3d n3,
			Color3f c1, Color3f c2, Color3f c3,
			Vector3d n) {
		this.p1 = new Vector4d(p1);
		this.p2 = new Vector4d(p2);
		this.p3 = new Vector4d(p3);
		this.n1 = new Vector3d(n1);
		this.n2 = new Vector3d(n2);
		this.n3 = new Vector3d(n3);
		this.c1 = new Color3f(c1);
		this.c2 = new Color3f(c2);
		this.c3 = new Color3f(c3);
		this.n = new Vector3d(n);
		this.shadingType = ShadingType.ST_GOUARD;
		this.diff = new Color3f(0, 0, 0);
	}
}
