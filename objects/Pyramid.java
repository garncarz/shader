package objects;

/**
 * Jehlan
 */
public class Pyramid extends GeomObject {

	/**
	 * Delka
	 */
	public double l = 1;
	
	/**
	 * Sirka
	 */
	public double w = 1;
	
	/**
	 * Vyska
	 */
	public double h = 1;
	
	/**
	 * Jemnost deleni delky
	 */
	public int dl = 10;
	
	/**
	 * Jemnost deleni sirky
	 */
	public int dw = 10;
	
	/**
	 * Jemnost deleni vysky
	 */
	public int dh = 10;
	
	
	/**
	 * Konstruktor
	 */
	public Pyramid() {
		shadingType = ShadingType.ST_GOUARD;
		modTransf.setDiagonal(1);
	}
	
	/**
	 * Konstruktor
	 * @param l Delka
	 * @param w Sirka
	 * @param h Vyska
	 * @param dl Jemnost deleni delky
	 * @param dw Jemnost deleni sirky
	 * @param dh Jemnost deleni vysky
	 */
	public Pyramid(double l, double w, double h, int dl, int dw, int dh) {
		this();
		this.l = l;
		this.w = w;
		this.h = h;
		this.dl = dl;
		this.dw = dw;
		this.dh = dh;
	}
	
	
	/**
	 * Trianguluje jehlan
	 */
	public void triangulate() {
		// TODO doplnit
	}
	
}
