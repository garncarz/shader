package geom;

import objects.Definitions;

/**
 * Matice rozmeru 4 x 4
 */
public class Matrix44 {

	/**
	 * Prvky matice
	 */
	private double[][] matrix = new double[4][4];
	
	
	/**
	 * Konstruktor
	 * @param a Hodnota, jez bude dosazena do vsech prvku matice
	 */
	public Matrix44(double a) {
		init(a);
	}
	
	/**
	 * Konstruktor
	 * @param m Matice, jejiz obsah bude dosazen
	 */
	public Matrix44(Matrix44 m) {
		set(m);
	}
	
	/**
	 * Konstruktor, nulove hodnoty prvku matice
	 */
	public Matrix44() {
		init(0);
	}
	
	
	/**
	 * Inicializace matice danou hodnotou
	 * @param a Hodnota
	 */
	public void init(double a) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				matrix[i][j] = a;
	}
	
	
	/**
	 * Nastavi hodnoty prvku podle zdroje
	 * @param a Zdrojova matice
	 */
	public void set(Matrix44 a) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				matrix[i][j] = a.getElement(i, j);
	}
	
	
	/**
	 * Vrati prvek matice na dane pozici
	 * @param r Radek
	 * @param s Sloupec
	 * @return Hodnota prvku
	 */
	public double getElement(int r, int s) {
		if (r >= 0 && r < 4 && s >= 0 && s < 4)
			return matrix[r][s];
		return 0;
	}
	
	
	/**
	 * Nastavi hodnotu prvku matice
	 * @param r Radek
	 * @param s Sloupec
	 * @param value Nova hodnota
	 */
	public void setElement(int r, int s, double value) {
		if (r >= 0 && r < 4 && s >= 0 && s < 4)
			matrix[r][s] = value;
	}
	
	
	/**
	 * Vynuluje matici a na diagonale nastavi danou hodnotu
	 * @param a Hodnota diagonaly
	 */
	public void setDiagonal(double a) {
		init(0);
		for (int i = 0; i < 4; i++)
			matrix[i][i] = a;
	}
	
	
	/**
	 * Pricte matici
	 * @param a Matice
	 */
	public void add(Matrix44 a) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				matrix[i][j] += a.getElement(i, j);
	}
	
	
	/**
	 * Vynasobi matici
	 * @param a Matice
	 */
	public void mul(Matrix44 a) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 4; k++)
					matrix[i][j] += matrix[i][k] * a.getElement(k, j);
	}
	
	
	/**
	 * Transponuje matici
	 */
	public void transpose() {
		Matrix44 tmp = new Matrix44();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tmp.setElement(i, j, matrix[j][i]);
		set(tmp);
	}
	
	
	/**
	 * Dosadi do matice matici provadejici posunuti o vektor
	 * @param t Vektor
	 */
	public void translateTransform(Vector3D t) {
		setDiagonal(1);
		matrix[3][0] = t.getX();
		matrix[3][1] = t.getY();
		matrix[3][2] = t.getZ();
	}
	
	
	/**
	 * Dosadi do matice matici provadejici zmenu meritka o vektor
	 * @param s Vektor
	 */
	public void scaleTransform(Vector3D s) {
		init(0);
		matrix[0][0] = s.getX();
		matrix[1][1] = s.getY();
		matrix[2][2] = s.getZ();
		matrix[3][3] = 1;
	}
	
	
	/**
	 * Dosadi do matice matici provadejici rotaci o vektor
	 * @param r Vektor
	 */
	public void rotateTransform(Vector3D r) {
		double cosAlpha, sinAlpha;
		Matrix44 rotate = new Matrix44();
		Matrix44 result = new Matrix44();
		Vector3D radian = new Vector3D(r);
		
		radian.mul(Math.PI / 180);
		result.setDiagonal(1);
		
		// rotace kolem osy x
		rotate.setDiagonal(1);
		cosAlpha = Math.cos(radian.getX());
		sinAlpha = Math.sin(radian.getX());
		if (Math.abs(cosAlpha) < Definitions.EPSILON)
			cosAlpha = 0;
		if (Math.abs(sinAlpha) < Definitions.EPSILON)
			sinAlpha = 0;
		rotate.setElement(1, 1, cosAlpha);
		rotate.setElement(1, 2, sinAlpha);
		rotate.setElement(2, 1, -sinAlpha);
		rotate.setElement(2, 2, cosAlpha);
		result.mul(rotate);
		
		// rotace kolem osy y
		rotate.setDiagonal(1);
		cosAlpha = Math.cos(radian.getY());
		sinAlpha = Math.sin(radian.getY());
		if (Math.abs(cosAlpha) < Definitions.EPSILON)
			cosAlpha = 0;
		if (Math.abs(sinAlpha) < Definitions.EPSILON)
			sinAlpha = 0;
		rotate.setElement(0, 0, cosAlpha);
		rotate.setElement(0, 2, sinAlpha);
		rotate.setElement(2, 0, -sinAlpha);
		rotate.setElement(2, 2, cosAlpha);
		result.mul(rotate);
		
		// rotace kolem osy z
		rotate.setDiagonal(1);
		cosAlpha = Math.cos(radian.getZ());
		sinAlpha = Math.sin(radian.getZ());
		if (Math.abs(cosAlpha) < Definitions.EPSILON)
			cosAlpha = 0;
		if (Math.abs(sinAlpha) < Definitions.EPSILON)
			sinAlpha = 0;
		rotate.setElement(0, 0, cosAlpha);
		rotate.setElement(0, 1, sinAlpha);
		rotate.setElement(1, 0, -sinAlpha);
		rotate.setElement(1, 1, cosAlpha);
		result.mul(rotate);
		
		set(result);
	}
	
}
