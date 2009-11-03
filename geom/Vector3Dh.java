package geom;

/**
 * 3D vektor v homogennich souradnicich
 */
public class Vector3Dh {
	
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
	 * Souradnice w
	 */
	private double vw;
	
	
	/**
	 * Konstruktor 
	 * @param x Souradnice x
	 * @param y Souradnice y
	 * @param z Souradnice z
	 * @param w Souradnice w
	 */
	public Vector3Dh(double x, double y, double z, double w) {
		set(x, y, z, w);
	}
	
	/**
	 * Konstruktor
	 * @param v Vektor, jehoz hodnoty budou dosazeny
	 */
	public Vector3Dh(Vector3Dh v) {
		set(v);
	}
	
	/**
	 * Konstruktor
	 * @param v Vektor, jehoz hodnoty budou dosazeny
	 */
	public Vector3Dh(Vector3D v) {
		set(v);
	}
	
	/**
	 * Konstruktor, nulove hodnoty souradnic
	 */
	public Vector3Dh() {
		set(0, 0, 0, 1);
	}
	
	
	/**
	 * Nastavi souradnice podle zdroje
	 * @param source Zdrojovy vektor
	 */
	public void set(Vector3Dh source) {
		vx = source.getX();
		vy = source.getY();
		vz = source.getZ();
		vw = source.getW();
	}
	
	/**
	 * Nastavi souradnice podle zdroje, w = 1
	 * @param source Zdrojovy vektor
	 */
	public void set(Vector3D source) {
		vx = source.getX();
		vy = source.getY();
		vz = source.getZ();
		vw = 1;
	}
	
	/**
	 * Nastavi souradnice
	 * @param x Nova hodnota souradnice x
	 * @param y Nova hodnota souradnice y
	 * @param z Nova hodnota souradnice z
	 * @param w Nova hodnota souradnice w
	 */
	public void set(double x, double y, double z, double w) {
		vx = x;
		vy = y;
		vz = z;
		vw = w;
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
	 * Nastavi souradnici w
	 * @param w Nova hodnota
	 */
	public void setW(double w) {
		vw = w;
	}
	
	/**
	 * Vrati souradnici w
	 * @return Souradnice w
	 */
	public double getW() {
		return vw;
	}
	
	
	/**
	 * Pricte vektor
	 * @param a Vektor
	 */
	public void add(Vector3Dh a) {
		vx += a.getX();
		vy += a.getY();
		vz += a.getZ();
		vw += a.getW();
	}
	
	
	/**
	 * Odecte vektor
	 * @param a Vektor
	 */
	public void sub(Vector3Dh a) {
		vx -= a.getX();
		vy -= a.getY();
		vz -= a.getZ();
		vw -= a.getW();
	}
	
	
	/**
	 * Vraci skalarni soucin s vektorem
	 * @param a Vektor
	 * @return Skalarni soucin
	 */
	public double dot(Vector3Dh a) {
		return a.getX() * vx + a.getY() * vy + a.getZ() * vz + a.getW() * vw;
	}
	
	
	/**
	 * Vynasobi skalarem
	 * @param a Skalar
	 */
	public void mul(double a) {
		vx *= a;
		vy *= a;
		vz *= a;
		vw *= a;
	}
	
	
	/**
	 * Vynasobi matici
	 * @param m Matice
	 */
	public void mul(Matrix44 m) {
		double r[] = new double[4];
		for (int i = 0; i < 4; i++) {
			r[i] = vx * m.getElement(0, i) +
					vy * m.getElement(1, i) +
					vz * m.getElement(2, i) +
					vw * m.getElement(3, i);
		}
		set(r[0], r[1], r[2], r[3]);
	}
	
	
	/**
	 * Vraci delku vektoru
	 * @return Delka vektoru
	 */
	public double length() {
		return Math.sqrt(vx * vx + vy * vy + vz * vz + vw * vw);
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
			vw /= len;
		}
	}
	
	
	/**
	 * Normalizuje souradnice tak, aby w = 1
	 */
	public void normalizeW() {
		if (vw != 0) {
			vx /= vw;
			vy /= vw;
			vz /= vw;
			vw = 1;
		}
	}
	
}
