package geom;

/**
 * Barva v modelu RGB
 */
public class ColorRGB {

	/**
	 * Hodnoty barevnych slozek:
	 * [0] red, [1] green, [2] blue
	 */
	private double color[] = new double[3];
	
	
	/**
	 * Konstruktor
	 * @param r Cervena slozka barvy
	 * @param g Zelena slozka barvy
	 * @param b Modra slozka barvy
	 */
	public ColorRGB(double r, double g, double b) {
		set(r, g, b);
	}
	
	/**
	 * Konstruktor
	 * @param source Zdrojova barva, jejiz hodnoty budou dosazeny
	 */
	public ColorRGB(ColorRGB source) {
		set(source);
	}
	
	/**
	 * Konstruktor
	 * @param source Zdrojova barva, jejiz hodnoty budou dosazeny
	 */
	public ColorRGB(ColorRGBZ source) {
		set(source.getR(), source.getB(), source.getB());
	}
	
	/**
	 * Konstruktor, nulove hodnoty slozek barvy
	 */
	public ColorRGB() {
		set(0, 0, 0);
	}
	
	
	/**
	 * Nastavi barvu podle zdroje
	 * @param source Zdroj
	 */
	public void set(ColorRGB source) {
		color[0] = source.getR();
		color[1] = source.getG();
		color[2] = source.getB();
	}
	
	/**
	 * Nastavi hodnoty barevnych slozek
	 * @param r Cervena slozka barvy
	 * @param g Zelena slozka barvy
	 * @param b Modra slozka barvy
	 */
	public void set(double r, double g, double b) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
	}
	
	/**
	 * Nastavi cervenou slozku barvy
	 * @param r Nova hodnota
	 */
	public void setR(double r) {
		color[0] = r;
	}
	
	/**
	 * Vrati cervenou slozku barvy
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
	 * Vrati zelenou slozku barvy
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
	 * Vrati modrou slozku barvy
	 * @return Modra slozka barvy
	 */
	public double getB() {
		return color[2];
	}
	
	
	/**
	 * Pricte barvu
	 * @param a Barva
	 */
	public void add(ColorRGB a) {
		color[0] += a.getR();
		color[1] += a.getG();
		color[2] += a.getB();
	}
	
	
	/**
	 * Pricte ke vsem slozkam barvy konstantu
	 * @param a Konstanta
	 */
	public void add(double a) {
		color[0] += a;
		color[1] += a;
		color[2] += a;
	}
	
	
	/**
	 * Odecte barvu
	 * @param a Barva
	 */
	public void sub(ColorRGB a) {
		color[0] -= a.getR();
		color[1] -= a.getG();
		color[2] -= a.getB();
	}
	
	
	/**
	 * Odecte od vsech slozek barvy konstantu 
	 * @param a Konstanta
	 */
	public void sub(double a) {
		color[0] -= a;
		color[1] -= a;
		color[2] -= a;
	}
	
	
	/**
	 * Vynasobi kazdou slozku barvy konstantou
	 * @param a Konstanta
	 */
	public void mul(double a) {
		color[0] *= a;
		color[1] *= a;
		color[2] *= a;
	}
	
	
	/**
	 * Vynasobi kazdou slozku barvy odpovidajici slozkou druhe barvy
	 * @param a Druha barva
	 */
	public void mul(ColorRGB a) {
		color[0] *= a.getR();
		color[1] *= a.getG();
		color[2] *= a.getB();
	}
	
}
