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
	
	
	// TODO zrejme smazat
	/**
	 * Vraci souradnici bodu na kouli
	 * @param m Horizontalni jednotky (pocet casti kruhu)
	 * @param n Vertikalni jednotky (pocet casti kruhu)
	 * @param steph Krok horizontalni stupnice (cast kruhu - uhel)
	 * @param stepv Krok vertikalni stupnice (cast kruhu - uhel)
	 * @return Souradnice bodu na kouli
	 */
	/**
	public Vector3D coordinate(int m, int n, double steph, double stepv) {
		double fi = m * steph;
		double psi = -Math.PI / 2 + n * stepv;
		return new Vector3D(radius * Math.cos(fi) * Math.cos(psi),
				radius * Math.sin(fi) * Math.cos(psi),
				radius * Math.sin(psi));
	}
	*/
	
	
	/**
	 * Trianguluje kouli
	 */
	public void triangulate() {
		Vector3D
				// body trojuhelniku
				p1 = new Vector3D(),
				p2 = new Vector3D(),
				p3 = new Vector3D(),
				// normaly bodu a pak celkova
				n1 = new Vector3D(),
				n2 = new Vector3D(),
				n3 = new Vector3D(),
				n = new Vector3D(),
				// pomocne vektory
				a = new Vector3D(),
				b = new Vector3D();
		// pocatecni barva
		ColorRGB c = new ColorRGB(0, 0, 0);
		// trojuhelnik
		Triangle t;
		double
				// vertikalni krok triangulace
				stepv = Math.PI / dv,
				// horizontalni krok triangulace
				steph = 2 * Math.PI / dh,
				// souradnice z a polomery pri vertikalnim kroku
				z1, r1, z2, r2,
				// uhly pri vertikalnim kroku
				beta1, beta2,
				// souradnice x a y pri horizontalnim kroku
				x11, y11, x12, y12, x21, y21, x22, y22,
				// uhly pri horizontalnim kroku
				alfa1, alfa2;
		
		// vertikalni postup - odzdola nahoru
		for (int i = 0; i < dv; i++) {
			beta1 = i * stepv - Math.PI / 2;
			z1 = Math.sin(beta1) * radius;
			r1 = Math.cos(beta1) * radius;
			
			beta2 = (i + 1) * stepv - Math.PI / 2;
			z2 = Math.sin(beta2) * radius;
			r2 = Math.cos(beta2) * radius;
			
			// horizontalni postup
			for (int j = 0; j < dh; j++) {
				alfa1 = j * steph;
				alfa2 = (j + 1) * steph;
				
				x11 = Math.cos(alfa1) * r1;
				y11 = Math.sin(alfa1) * r1;
				x12 = Math.cos(alfa2) * r1;
				y12 = Math.sin(alfa2) * r1;
				x21 = Math.cos(alfa1) * r2;
				y21 = Math.sin(alfa1) * r2;
				x22 = Math.cos(alfa2) * r2;
				y22 = Math.sin(alfa2) * r2;
				
				// spodni trojuhelnik
				p1.set(x11, y11, z1);
				p2.set(x12, y12, z1);
				p3.set(x21, y21, z2);
				
				n1.set(p1); n1.normalize();
				n2.set(p2); n2.normalize();
				n3.set(p3); n3.normalize();
				
				a.set(p2); a.sub(p1);
				b.set(p3); b.sub(p1);
				n.set(a); n.cross(b); n.normalize();
				
				t = new Triangle(p1, p2, p3, n1, n2, n3, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(p2);
				p2.set(x22, y22, z2);
				
				n1.set(n2);
				n2.set(p2); n2.normalize();
				
				a.set(p2); a.sub(p1);
				b.set(p3); b.sub(p1);
				n.set(a); n.cross(b); n.normalize();
				
				t = new Triangle(p1, p2, p3, n1, n2, n3, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
			}  // horizontalni postup
		}  // vertikalni postup
		
		// provedeni modelovacich transformaci
		transform();
	}  // triangulate
	
}
