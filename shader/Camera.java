package shader;

import geom.*;

/**
 * Kamera - reprezentuje zorny jehlan pro stredove promitani
 */
public class Camera {

	/**
	 * View Reference Point
	 */
	public Vector3D VRP = new Vector3D();
	
	/**
	 * View Plane Normal
	 */
	public Vector3D VPN = new Vector3D();
	
	/**
	 * View Up Vector
	 */
	public Vector3D VUP = new Vector3D();
	
	/**
	 * Projection Reference Point
	 */
	public Vector3D PRP = new Vector3D();
	
	public double
			/** Minimalni horizontalni souradnice zorneho jehlanu */
			Umin,
			
			/** Maximalni horizontalni souradnice zorneho jehlanu */
			Umax,
			
			/** Minimalni vertikalni souradnice zorneho jehlanu */
			Vmin,
			
			/** Maximalni vertikalni souradnice zorneho jehlanu */
			Vmax;
	
	/**
	 * Predni orezavaci plocha
	 */
	private double F;
	
	/**
	 * Zadni orezavaci plocha
	 */
	private double B;
	
	private Matrix44
			/** Transformacni matice - celkova */
			M = new Matrix44(),
			
			/** Transformacni matice - prvni */
			M1 = new Matrix44(),
			
			/** Transformacni matice - druha */
			M2 = new Matrix44(),
			
			/** Transformacni matice - treti */
			M3 = new Matrix44(),
			
			/** Transformacni matice - ctvrta */
			M4 = new Matrix44(),
			
			/** Transformacni matice - pata */
			M5 = new Matrix44(),
			
			/** Transformacni matice - sesta */
			M6 = new Matrix44();
	
	private Matrix44
			/** Inverzni transformacni matice - celkova */
			IM = new Matrix44(),
			
			/** Inverzni transformacni matice - prvni */
			IM1 = new Matrix44(),
			
			/** Inverzni transformacni matice - druha */
			IM2 = new Matrix44(),
			
			/** Inverzni transformacni matice - treti */
			IM3 = new Matrix44(),
			
			/** Inverzni transformacni matice - ctvrta */
			IM4 = new Matrix44(),
			
			/** Inverzni transformacni matice - pata */
			IM5 = new Matrix44(),
			
			/** Inverzni transformacni matice - sesta */
			IM6 = new Matrix44();
	
	
	/**
	 * Konstruktor
	 */
	public Camera() { }
	
	/**
	 * Konstruktor
	 * @param VRP View Reference Point
	 * @param VPN View Plane Normal
	 * @param VUP View Up Vector
	 * @param PRP Projection Reference Point
	 */
	public Camera(Vector3D VRP, Vector3D VPN, Vector3D VUP, Vector3D PRP) {
		set(VRP, VPN, VUP, PRP);
	}
	
	/**
	 * Konstruktor
	 * @param VRP View Reference Point
	 * @param VPN View Plane Normal
	 * @param VUP View Up Vector
	 * @param PRP Projection Reference Point
	 * @param Umin Definice zorneho jehlanu - Umin
	 * @param Umax Definice zorneho jehlanu - Umax
	 * @param Vmin Definice zorneho jehlanu - Vmin
	 * @param Vmax Definice zorneho jehlanu - Vmax
	 * @param F Predni orezavaci plocha
	 * @param B Zadni orezavaci plocha
	 */
	public Camera(Vector3D VRP, Vector3D VPN, Vector3D VUP, Vector3D PRP,
			double Umin, double Umax, double Vmin, double Vmax,
			double F, double B) {
		set(VRP, VPN, VUP, PRP, Umin, Umax, Vmin, Vmax, F, B);
	}
	
	
	/**
	 * Nastavi parametry kamery
	 * @param VRP View Reference Point
	 * @param VPN View Plane Normal
	 * @param VUP View Up Vector
	 * @param PRP Projection Reference Point
	 * @param Umin Definice zorneho jehlanu - Umin
	 * @param Umax Definice zorneho jehlanu - Umax
	 * @param Vmin Definice zorneho jehlanu - Vmin
	 * @param Vmax Definice zorneho jehlanu - Vmax
	 * @param F Predni orezavaci plocha
	 * @param B Zadni orezavaci plocha
	 */
	public void set(Vector3D VRP, Vector3D VPN, Vector3D VUP, Vector3D PRP,
			double Umin, double Umax, double Vmin, double Vmax,
			double F, double B) {
		this.VRP.set(VRP);
		this.VPN.set(VPN);
		this.VUP.set(VUP);
		this.PRP.set(PRP);
		this.Umin = Umin;
		this.Umax = Umax;
		this.Vmin = Vmin;
		this.Vmax = Vmax;
		this.F = F;
		this.B = B;
	}
	
	/**
	 * Nastavi parametry kamery
	 * @param VRP View Reference Point
	 * @param VPN View Plane Normal
	 * @param VUP View Up Vector
	 * @param PRP Projection Reference Point
	 */
	public void set(Vector3D VRP, Vector3D VPN, Vector3D VUP, Vector3D PRP) {
		set(VRP, VPN, VUP, PRP, -1, 1, -1, 1, 1, -1);
	}
	
	/**
	 * Nastavi View Reference Point
	 * @param VRP View Reference Point
	 */
	public void setVRP(Vector3D VRP) {
		this.VRP.set(VRP);
	}
	
	/**
	 * Vrati View Reference Point
	 * @return View Reference Point
	 */
	public Vector3D getVRP() {
		return new Vector3D(VRP);
	}
	
	/**
	 * Nastavi View Plane Normal
	 * @param VPN View Plane Normal
	 */
	public void setVPN(Vector3D VPN) {
		this.VPN.set(VPN);
	}
	
	/**
	 * Vrati View Plane Normal
	 * @return View Plane Normal
	 */
	public Vector3D getVPN() {
		return new Vector3D(VPN);
	}
	
	/**
	 * Nastavi View Up Vector
	 * @param VUP View Up Vector
	 */
	public void setVUP(Vector3D VUP) {
		this.VUP.set(VUP);
	}
	
	/**
	 * Vrati View Up Vector
	 * @return View Up Vector
	 */
	public Vector3D getVUP() {
		return new Vector3D(VUP);
	}
	
	/**
	 * Nastavi Projection Reference Point
	 * @param PRP Projection Reference Point
	 */
	public void setPRP(Vector3D PRP) {
		this.PRP.set(PRP);
	}
	
	/**
	 * Vrati Projection Reference Point
	 * @return Projection Reference Point
	 */
	public Vector3D getPRP() {
		return new Vector3D(PRP);
	}
	
	/**
	 * Nastavi Umax
	 * @param Umax Umax
	 */
	public void setUmax(double Umax) {
		this.Umax = Umax;
	}
	
	/**
	 * Vrati Umax
	 * @return Umax
	 */
	public double getUmax() {
		return Umax;
	}

	/**
	 * Nastavi Umin
	 * @param Umin Umin
	 */
	public void setUmin(double Umin) {
		this.Umin = Umin;
	}
	
	/**
	 * Vrati Umin
	 * @return Umin
	 */
	public double getUmin() {
		return Umin;
	}
	
	/**
	 * Nastavi Vmax
	 * @param Vmax Vmax
	 */
	public void setVmax(double Vmax) {
		this.Vmax = Vmax;
	}
	
	/**
	 * Vrati Vmax
	 * @return Vmax
	 */
	public double getVmax() {
		return Vmax;
	}

	/**
	 * Nastavi Vmin
	 * @param Vmin Vmin
	 */
	public void setVmin(double Vmin) {
		this.Vmin = Vmin;
	}
	
	/**
	 * Vrati Vmin
	 * @return Vmin
	 */
	public double getVmin() {
		return Vmin;
	}
	
	/**
	 * Nastavi predni orezavaci rovinu
	 * @param F Predni orezavaci rovina
	 */
	public void setF(double F) {
		this.F = F;
	}
	
	/**
	 * Vrati predni orezavaci rovinu
	 * @return Predni orezavaci rovina
	 */
	public double getF() {
		return F;
	}
	
	/**
	 * Nastavi zadni orezavaci rovinu
	 * @param B Zadni orezavaci rovina
	 */
	public void setB(double B) {
		this.B = B;
	}
	
	/**
	 * Vrati zadni orezavaci rovinu
	 * @return Zadni orezavaci rovina
	 */
	public double getB() {
		return B;
	}
	
	/**
	 * Vrati transformacni matici
	 * @return Transformacni matice
	 */
	public Matrix44 getM() {
		return new Matrix44(M);
	}
	
	/**
	 * Vrati inverzni transformacni matici
	 * @return Inverzni transformacni matice
	 */
	public Matrix44 getIM() {
		return new Matrix44(IM);
	}
	
	
	/**
	 * Vrati polohu PRP ve WRC
	 * @return Poloha PRP ve WRC
	 */
	public Vector3D getPRPinWRC() {
		Vector3Dh v = new Vector3Dh(PRP);
		v.mul(IM2);
		v.mul(IM1);
		return new Vector3D(v);
	}
	
	
	/**
	 * Vypocet projekcni matice
	 */
	public void create() {
		// M1 - posunuti pocatku souradne soustavy do VRP
		M1.setDiagonal(1);
		M1.setElement(3, 0, -VRP.getX());
		M1.setElement(3, 1, -VRP.getY());
		M1.setElement(3, 2, -VRP.getZ());
		
		IM1.setDiagonal(1);
		IM1.setElement(3, 0, VRP.getX());
		IM1.setElement(3, 1, VRP.getY());
		IM1.setElement(3, 2, VRP.getZ());
		
		
		// M2 - natoceni tak, "aby z smerovalo do tabule"
		// => potom lehka transformace x, y do 2D
		Vector3D z2 = new Vector3D(VPN);
		z2.normalize();
		Vector3D x2 = new Vector3D(VUP);
		x2.cross(VPN);
		x2.normalize();
		Vector3D y2 = new Vector3D(z2);
		y2.cross(x2);
		
		M2.setElement(0, 0, x2.getX());
		M2.setElement(1, 0, x2.getY());
		M2.setElement(2, 0, x2.getZ());
		M2.setElement(0, 1, y2.getX());
		M2.setElement(1, 1, y2.getY());
		M2.setElement(2, 1, y2.getZ());
		M2.setElement(0, 2, z2.getX());
		M2.setElement(1, 2, z2.getY());
		M2.setElement(2, 2, z2.getZ());
		M2.setElement(3, 3, 1);
		
		// rotace => inverzni matice = prehozene radky a sloupce
		IM2.setElement(0, 0, x2.getX());
		IM2.setElement(0, 1, x2.getY());
		IM2.setElement(0, 2, x2.getZ());
		IM2.setElement(1, 0, y2.getX());
		IM2.setElement(1, 1, y2.getY());
		IM2.setElement(1, 2, y2.getZ());
		IM2.setElement(2, 0, z2.getX());
		IM2.setElement(2, 1, z2.getY());
		IM2.setElement(2, 2, z2.getZ());
		IM2.setElement(3, 3, 1);
		
		
		// M3 - ulehcuje nasledne pocitani vzdalenosti;
		// pohled vychazi ze stredu
		M3.setDiagonal(1);
		M3.setElement(3, 0, -PRP.getX());
		M3.setElement(3, 1, -PRP.getY());
		M3.setElement(3, 2, -PRP.getZ());
		
		// posun => inverzni matice = naopak posun
		IM3.setDiagonal(1);
		IM3.setElement(3, 0, PRP.getX());
		IM3.setElement(3, 1, PRP.getY());
		IM3.setElement(3, 2, PRP.getZ());
		
		
		// M4
		Vector3D cv = new Vector3D();
		cv.setX((Umax + Umin) / 2);
		cv.setY((Vmax + Vmin) / 2);
		cv.setZ(0);
		Vector3D dop = new Vector3D(cv);
		dop.sub(PRP);

		M4.setDiagonal(1);
		M4.setElement(2, 0, -dop.getX() / dop.getZ());
		M4.setElement(2, 1, -dop.getY() / dop.getZ());

		IM4.setDiagonal(1);
		IM4.setElement(0, 2, dop.getX() / dop.getZ());
		IM4.setElement(1, 2, dop.getY() / dop.getZ());
		
		
		// M5 - scale => lepsi pocitani
		Vector2D q = new Vector2D();
		q.setX((Umax - Umin) / dop.getZ() * (dop.getZ() + B));
		q.setY((Vmax - Vmin) / dop.getZ() * (dop.getZ() + B));
		Vector3D s = new Vector3D();
		s.setZ(-1 / (dop.getZ() + B));
		s.setX(2 * dop.getZ() / ((Umax - Umin) * (dop.getZ() + B)));
		s.setY(2 * dop.getZ() / ((Vmax - Vmin) * (dop.getZ() + B)));

		M5.setElement(0, 0, s.getX());
		M5.setElement(1, 1, s.getY());
		M5.setElement(2, 2, s.getZ());
		M5.setElement(3, 3, 1);

		IM5.setElement(0, 0, 1 / s.getX());
		IM5.setElement(1, 1, 1 / s.getY());
		IM5.setElement(2, 2, 1 / s.getZ());
		IM5.setElement(3, 3, 1);

		
		// M6
		double zf = s.getZ() * (dop.getZ() + F);

		M6.setElement(0, 0, 1);
		M6.setElement(1, 1, 1);
		M6.setElement(2, 2, 1 / (1 + zf));
		M6.setElement(2, 3, -1);
		M6.setElement(3, 2, -zf / (1 + zf));

		IM6.setElement(0, 0, 1);
		IM6.setElement(1, 1, 1);
		IM6.setElement(3, 3, -1 / zf);
		IM6.setElement(3, 2, -1);
		IM6.setElement(2, 3, -(1 + zf) / zf);
		
		
		// M, IM
		M.set(M1); M.mul(M2); M.mul(M3); M.mul(M4); M.mul(M5); M.mul(M6);
		IM.set(IM1); IM.mul(IM2); IM.mul(IM3); IM.mul(IM4); IM.mul(IM5);
		IM.mul(IM6);
	}  // create
	
}
