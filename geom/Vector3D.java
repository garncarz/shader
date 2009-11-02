package geom;

/**
 * Vektor v 3D prostoru
 */
public class Vector3D {

	/**
	 * Souradnice x
	 */
	private double vx;
	
	/**
	 * Souradnice y
	 */
	private double vy;
	
	/**
	 * Souradnice z
	 */
	private double vz;
	
	
	/**
	 * Konstruktor 
	 * @param x Souradnice x
	 * @param y Souradnice y
	 * @param z Souradnice z
	 */
	public Vector3D(double x, double y, double z) {
		set(x, y, z);
	}
	
	/**
	 * Konstruktor
	 * @param v Vektor, jehoz hodnoty budou dosazeny
	 */
	public Vector3D(Vector3D v) {
		set(v);
	}
	
	/**
	 * Konstruktor, nulove hodnoty souradnic
	 */
	public Vector3D() {
		set(0, 0, 0);
	}
	
	
	/**
	 * Nastavi souradnice podle zdroje
	 * @param source Zdrojovy vektor
	 */
	public void set(Vector3D source) {
		vx = source.getX();
		vy = source.getY();
		vz = source.getZ();
	}
	
	/**
	 * Nastavi souradnice
	 * @param x Nova hodnota souradnice x
	 * @param y Nova hodnota souradnice y
	 * @param z Nova hodnota souradnice z
	 */
	public void set(double x, double y, double z) {
		vx = x;
		vy = y;
		vz = z;
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
	 * Nastavi souradnici z
	 * @param z Nova hodnota
	 */
	public void setZ(double z) {
		vz = z;
	}
	
	/**
	 * Vrati souradnici z
	 * @return Souradnice z
	 */
	public double getZ() {
		return vz;
	}
	
	
	/**
	 * Pricte vektor
	 * @param a Vektor
	 */
	public void add(Vector3D a) {
		vx += a.getX();
		vy += a.getY();
		vz += a.getZ();
	}
	
	
	/**
	 * Odecte vektor
	 * @param a Vektor
	 */
	public void sub(Vector3D a) {
		vx -= a.getX();
		vy -= a.getY();
		vz -= a.getZ();
	}
	
	
	/**
	 * Vraci skalarni soucin s vektorem
	 * @param a Vektor
	 * @return Skalarni soucin
	 */
	public double dot(Vector3D a) {
		return a.getX() * vx + a.getY() * vy + a.getZ() * vz;
	}
	
	
	/**
	 * Vynasobi skalarem
	 * @param a Skalar
	 */
	public void mul(double a) {
		vx *= a;
		vy *= a;
		vz *= a;
	}
	
	
	/**
	 * Vektorove vynasobi
	 * @param a Vektor
	 */
	public void cross(Vector3D a) {
		double nx, ny, nz;
		nx = vy * a.getZ() - vz * a.getY();
		ny = vz * a.getX() - vx * a.getZ();
		nz = vx * a.getY() - vy * a.getX();
		set(nx, ny, nz);
	}
	
	
	/**
	 * Vraci delku vektoru
	 * @return Delka vektoru
	 */
	public double length() {
		return Math.sqrt(vx * vx + vy * vy + vz * vz);
	}
	
	
	/**
	 * Normalizuje souradnice vektoru
	 */
	public void normalize() {
		double len = length();
		if (len > 0) {
			vx /= len;
			vy /= len;
			vz /= len;
		}
	}
}
