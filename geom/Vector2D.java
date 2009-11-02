package geom;

/**
 * Vektor ve 2D prostoru
 */
public class Vector2D {

	/**
	 * Souradnice x
	 */
	private double vx;

	/**
	 * Souradnice y
	 */
	private double vy;
	
	
	/**
	 * Konstruktor 
	 * @param x Souradnice x
	 * @param y Souradnice y
	 */
	public Vector2D(double x, double y) {
		set(x, y);
	}
	
	/**
	 * Konstruktor
	 * @param v Vektor, jehoz hodnoty budou dosazeny
	 */
	public Vector2D(Vector2D v) {
		set(v);
	}
	
	/**
	 * Konstruktor, nulove hodnoty souradnic
	 */
	public Vector2D() {
		set(0, 0);
	}
	
	
	/**
	 * Nastavi souradnice podle zdroje
	 * @param source Zdrojovy vektor
	 */
	public void set(Vector2D source) {
		vx = source.getX();
		vy = source.getY();
	}
	
	/**
	 * Nastavi souradnice
	 * @param x Nova hodnota souradnice x
	 * @param y Nova hodnota souradnice y
	 */
	public void set(double x, double y) {
		vx = x;
		vy = y;
	}
	
	/**
	 * Nastavi souradnici x
	 * @param x Nova hodnota
	 */
	public void setX(double x) {
		vx = x;
	}
	
	/**
	 * Vrati souradnici x
	 * @return Souradnice x
	 */
	public double getX() {
		return vx;
	}
	
	/**
	 * Nastavi souradnici y
	 * @param y Nova hodnota
	 */
	public void setY(double y) {
		vy = y;
	}
	
	/**
	 * Vrati souradnici y
	 * @return Souradnice y
	 */
	public double getY() {
		return vy;
	}

	
	/**
	 * Pricte vektor
	 * @param a Vektor
	 */
	public void add(Vector2D a) {
		vx += a.getX();
		vy += a.getY();
	}
	
	
	/**
	 * Odecte vektor
	 * @param a Vektor
	 */
	public void sub(Vector2D a) {
		vx -= a.getX();
		vy -= a.getY();
	}
	
	
	/**
	 * Vraci skalarni soucin s vektorem
	 * @param a Vektor
	 * @return Skalarni soucin
	 */
	public double dot(Vector2D a) {
		return a.getX() * vx + a.getY() * vy;
	}
	
	
	/**
	 * Vynasobi skalarem
	 * @param a Skalar
	 */
	public void mul(double a) {
		vx *= a;
		vy *= a;
	}
	
	
	/**
	 * Vraci delku vektoru
	 * @return Delka vektoru
	 */
	public double length() {
		return Math.sqrt(vx * vx + vy * vy);
	}
	
	
	/**
	 * Normalizuje souradnice vektoru
	 */
	public void normalize() {
		double len = length();
		if (len > 0) {
			vx /= len;
			vy /= len;
		}
	}
	
}
