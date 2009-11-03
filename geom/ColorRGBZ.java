package geom;

/**
 * Barva v modelu RGB + souradnice z
 */
public class ColorRGBZ {

	/**
	 * Hodnoty barevnych slozek + souradnice:
	 * [0] red, [1] green, [2] blue, [3] z
	 */
	private double color[] = new double[3];
	
	
	/**
	 * Konstruktor
	 * @param r Cervena slozka barvy
	 * @param g Zelena slozka barvy
	 * @param b Modra slozka barvy
	 * @param z Souradnice z
	 */
	public ColorRGBZ(double r, double g, double b, double z) {
		set(r, g, b, z);
	}
	
	/**
	 * Konstruktor
	 * @param source Zdrojova barva, jejiz hodnoty budou dosazeny
	 */
	public ColorRGBZ(ColorRGBZ source) {
		set(source);
	}
	
	/**
	 * Konstruktor, nulove hodnoty slozek barvy a souradnice
	 */
	public ColorRGBZ() {
		set(0, 0, 0, 0);
	}
	
	
	/**
	 * Nastavi barvu a souradnici podle zdroje
	 * @param source Zdroj
	 */
	public void set(ColorRGBZ source) {
		color[0] = source.getR();
		color[1] = source.getG();
		color[2] = source.getB();
		color[3] = source.getZ();
	}
	
	/**
	 * Nastavi hodnoty barevnych slozek a souradnici
	 * @param r Cervena slozka barvy
	 * @param g Zelena slozka barvy
	 * @param b Modra slozka barvy
	 * @param z Souradnice z
	 */
	public void set(double r, double g, double b, double z) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = z;
	}
	
	/**
	 * Nastavi cervenou slozku barvy
	 * @param r Nova hodnota
	 */
	public void setR(double r) {
		color[0] = r;
	}
	
	/**
	 * Vraci cervenou slozku barvy
	 * @return Cervena slozka barvy
	 */
	public double getR() {
		return color[0];
	}
	
	/**
	 * Nastavi zelenou slozku barvy
	 * @param g Nova hodnota
	 */
	public void setG(double g) {
		color[1] = g;
	}
	
	/**
	 * Vraci zelenou slozku barvy
	 * @return Zelena slozka barvy
	 */
	public double getG() {
		return color[1];
	}
	
	/**
	 * Nastavi modrou slozku barvy
	 * @param b Nova hodnota
	 */
	public void setB(double b) {
		color[2] = b;
	}
	
	/**
	 * Vraci modrou slozku barvy
	 * @return Modra slozka barvy
	 */
	public double getB() {
		return color[2];
	}
	
	/**
	 * Nastavi souradnici z
	 * @param z Nova hodnota
	 */
	public void setZ(double z) {
		color[3] = z;
	}
	
	/**
	 * Vraci souradnici z
	 * @return Souradnice z
	 */
	public double getZ() {
		return color[3];
	}
	
	
	/**
	 * Pricte barvu a souradnici
	 * @param a Barva se souradnici
	 */
	public void add(ColorRGBZ a) {
		color[0] += a.getR();
		color[1] += a.getG();
		color[2] += a.getB();
		color[3] += a.getZ();
	}
	
	
	/**
	 * Pricte ke vsem slozkam barvy a souradnici konstantu
	 * @param a Konstanta
	 */
	public void add(double a) {
		color[0] += a;
		color[1] += a;
		color[2] += a;
		color[3] += a;
	}
	
	
	/**
	 * Odecte barvu a souradnici
	 * @param a Barva se souradnici
	 */
	public void sub(ColorRGBZ a) {
		color[0] -= a.getR();
		color[1] -= a.getG();
		color[2] -= a.getB();
		color[3] -= a.getZ();
	}
	
	
	/**
	 * Odecte od vsech slozek barvy a souradnice konstantu 
	 * @param a Konstanta
	 */
	public void sub(double a) {
		color[0] -= a;
		color[1] -= a;
		color[2] -= a;
		color[3] -= a;
	}
	
	
	/**
	 * Vynasobi kazdou slozku barvy a souradnici konstantou
	 * @param a Konstanta
	 */
	public void mul(double a) {
		color[0] *= a;
		color[1] *= a;
		color[2] *= a;
		color[3] *= a;
	}
	
}
