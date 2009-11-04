package objects;

import geom.*;

/**
 * Valec
 */
public class Cylinder extends GeomObject {

	/**
	 * Polomer podstavy
	 */
	public double radius = 1;
	
	/**
	 * Vyska valce
	 */
	public double height = 1;
	
	/**
	 * Jemnost deleni strany kvadru
	 */
	public int dh = 10;
	
	/**
	 * Jemnost deleni strany kvadru
	 */
	public int dv= 10;
	
	
	/**
	 * Konstruktor
	 */
	public Cylinder() {
		shadingType = ShadingType.ST_GOUARD;
		modTransf.setDiagonal(1);
	}
	
	/**
	 * Konstruktor
	 * @param r Polomer podstavy
	 * @param h Vyska valce
	 * @param dh Jemnost deleni strany kvadru
	 * @param dv Jemnost deleni strany kvadru
	 */
	public Cylinder(double r, double h, int dh, int dv) {
		this();
		radius = r;
		height = h;
		this.dh = dh;
		this.dv = dv;
	}
	
	
	/**
	 * Vraci souradnici bodu na plasti valce
	 * @param m Horizontalni jednotky (pocet casti kruhu)
	 * @param n Vertikalni jednotky
	 * @param steph Krok horizontalni stupnice (cast kruhu - uhel)
	 * @param stepv Krok vertikalni stupnice
	 * @return Souradnice bodu na plasti
	 */
	public Vector3D coordinate(int m, int n, double steph, double stepv) {
		double fi = m * steph;
		return new Vector3D(radius * Math.cos(fi), radius * Math.sin(fi),
				-height / 2 + n * stepv);
	}
	
	
	/**
	 * Trianguluje valec
	 */
	public void triangulate() {
		// TODO doplnit
	}
	
}
