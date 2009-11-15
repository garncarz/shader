package objects;

import shader.ShadingType;
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
	 * Jemnost deleni - horizontalne
	 */
	public int dh = 10;
	
	/**
	 * Jemnost deleni - vertikalne
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
	 * @param dh Jemnost deleni - horizontalne
	 * @param dv Jemnost deleni - vertikalne
	 */
	public Cylinder(double r, double h, int dh, int dv) {
		this();
		radius = r;
		height = h;
		this.dh = dh;
		this.dv = dv;
	}
	
	
	// TODO zrejme smazat
	/**
	 * Vraci souradnici bodu na plasti valce
	 * @param m Horizontalni jednotky (pocet casti kruhu)
	 * @param n Vertikalni jednotky
	 * @param steph Krok horizontalni stupnice (cast kruhu - uhel)
	 * @param stepv Krok vertikalni stupnice
	 * @return Souradnice bodu na plasti
	 */
	/*
	public Vector3D coordinate(int m, int n, double steph, double stepv) {
		double fi = m * steph;
		return new Vector3D(radius * Math.cos(fi), radius * Math.sin(fi),
				-height / 2 + n * stepv);
	}
	*/
	
	
	/**
	 * Trianguluje valec
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
				// pomocny vektor pro stred
				p0 = new Vector3D(0, 0, 0),
				// dalsi pomocne vektory pro pocitani normaly
				a = new Vector3D(),
				b = new Vector3D();
		// pocatecni barva
		ColorRGB c = new ColorRGB(0, 0, 0);
		// trojuhelnik
		Triangle t;
		// kroky pro triangulaci (horizontalni, vertikalni)
		double steph = 2 * Math.PI / dh,
				stepv = height / dv;
		// souradnice bodu
		double x1, y1, x2, y2, x3, y3, z1, z2;
		
		// triangulace podstav
		for (int i = 0; i < dh; i++) {
			x1 = y1 = 0;
			x2 = radius * Math.cos(i * steph);
			y2 = radius * Math.sin(i * steph);
			x3 = radius * Math.cos((i + 1) * steph);
			y3 = radius * Math.sin((i + 1) * steph);
			
			// dolni podstava
			p1.set(x1, y1, -height / 2);
			p2.set(x2, y2, -height / 2);
			p3.set(x3, y3, -height / 2);
			n.set(0, 0, -1);
			t = new Triangle(p1, p3, p2, n, n, n, c, c, c, n);
			t.diff.set(diff);
			t.spec.set(spec);
			t.shadingType = shadingType;
			triangles.add(t);
			
			// horni podstava
			p1.set(x1, y1, height / 2);
			p2.set(x2, y2, height / 2);
			p3.set(x3, y3, height / 2);
			n.set(0, 0, 1);
			t = new Triangle(p1, p2, p3, n, n, n, c, c, c, n);
			t.diff.set(diff);
			t.spec.set(spec);
			t.shadingType = shadingType;
			triangles.add(t);
		}
		
		// triangulace plaste
		for (int i = 0; i < dv; i++)
			for (int j = 0; j < dh; j++) {
				x1 = radius * Math.cos(j * steph);
				y1 = radius * Math.sin(j * steph);
				x2 = radius * Math.cos((j + 1) * steph);
				y2 = radius * Math.sin((j + 1) * steph);
				z1 = -height / 2 + i * stepv;
				z2 = -height / 2 + (i + 1) * stepv;
				
				// spodni trojuhelnik
				p1.set(x1, y1, z1);
				p2.set(x2, y2, z1);
				p3.set(x1, y1, z2);
				p0.setZ(z1);
				n1.set(p1); n1.sub(p0); n1.normalize();
				n2.set(p2); n2.sub(p0); n2.normalize();
				p0.setZ(z2);
				n3.set(p3); n3.sub(p0); n3.normalize();
				
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
				p2.set(x2, y2, z2);
				n1.set(n2);
				n2.set(p2); n2.sub(p0); n2.normalize();
				
				a.set(p2); a.sub(p1);
				b.set(p3); b.sub(p1);
				n.set(a); n.cross(b); n.normalize();
				
				t = new Triangle(p1, p2, p3, n1, n2, n3, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
			}
		
		// provedeni modelovacich transformaci
		transform();
	}
	
}
