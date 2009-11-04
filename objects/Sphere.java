package objects;

import geom.*;

/**
 * Koule
 */
public class Sphere extends GeomObject {

	/**
	 * Polomer
	 */
	public double radius = 1;
	
	/**
	 * Jemnost deleni - horizontalne
	 */
	public int dh = 10;
	
	/**
	 * Jemnost deleni - vertikalne
	 */
	public int dv = 10;
	
	
	/**
	 * Konstruktor
	 */
	public Sphere() {
		shadingType = ShadingType.ST_GOUARD;
		modTransf.setDiagonal(1);
	}
	
	/**
	 * Konstruktor
	 * @param r Polomer
	 * @param dh Jemnost deleni - horizontalne
	 * @param dv Jemnost deleni - vertikalne
	 */
	public Sphere(double r, int dh, int dv) {
		this();
		this.radius = r;
		this.dh = dh;
		this.dv = dv;
	}
	
	
	/**
	 * Vraci souradnici bodu na kouli
	 * @param m Horizontalni jednotky (pocet casti kruhu)
	 * @param n Vertikalni jednotky (pocet casti kruhu)
	 * @param steph Krok horizontalni stupnice (cast kruhu - uhel)
	 * @param stepv Krok vertikalni stupnice (cast kruhu - uhel)
	 * @return Souradnice bodu na kouli
	 */
	public Vector3D coordinate(int m, int n, double steph, double stepv) {
		double fi = m * steph;
		double psi = -Math.PI / 2 + n * stepv;
		return new Vector3D(radius * Math.cos(fi) * Math.cos(psi),
				radius * Math.sin(fi) * Math.cos(psi),
				radius * Math.sin(psi));
	}
	
	
	/**
	 * Trianguluje kouli
	 */
	public void triangulate() {
		// TODO doplnit
	}
	
}
