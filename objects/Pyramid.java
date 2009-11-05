package objects;

import geom.*;

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
		Vector3D
				// body trojuhelniku
				p1 = new Vector3D(),
				p2 = new Vector3D(),
				p3 = new Vector3D(),
				// normala
				n = new Vector3D(),
				// pomocne vektory
				a = new Vector3D(),
				b = new Vector3D(),
				// referencni vektory
				vlevodole = new Vector3D(),
				vlevonahore = new Vector3D(),
				vpravodole = new Vector3D(),
				vpravonahore = new Vector3D(),
				// pomocne vektory odstupu
				vpravo = new Vector3D(),
				nahoru = new Vector3D();
		// pocatecni barva
		ColorRGB c = new ColorRGB(0, 0, 0);
		// trojuhelnik
		Triangle t;
		
		// triangulace podstavy:
		vlevodole.set(-l / 2, -w / 2, 0);
		vpravo.set(l / dl, 0, 0);
		nahoru.set(0, w / dw, 0);
		n.set(0, 0, -1);
		
		for (int i = 0; i < dl; i++)
			for (int j = 0; j < dw; j++) {
				// spodni trojuhelnik
				p1.set(vlevodole);
				a.set(vpravo); a.mul(i);
				b.set(nahoru); b.mul(j);
				p1.add(a); p1.add(b);
				p2.set(p1); p2.add(vpravo);
				p3.set(p1); p3.add(nahoru);
				
				t = new Triangle(p1, p3, p2, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(p2);
				p2.add(nahoru);
				
				t = new Triangle(p1, p3, p2, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
			}
		
		// triangulace sten:
		
		// vychozi body pro 4 steny
		Vector3D start[] = new Vector3D[4];
		start[0] = new Vector3D(-l / 2, -w / 2, 0);
		start[1] = new Vector3D(l / 2, -w / 2, 0);
		start[2] = new Vector3D(l / 2, w / 2, 0);
		start[3] = new Vector3D(-l / 2, w / 2, 0);
		
		// zpusob dilkovani pro 4 steny
		double dx[] = new double[]{dl, dw, dl, dw};
		
		for (int stena = 0; stena < 4; stena++) {
			// normala cele steny:
			// bod uprostred strany dole
			p1.set(start[(stena + 1) % 4]);
			p1.add(start[stena]);
			p1.mul(0.5);
			// vrchol
			p2.set(0, 0, h);
			// jeden vektor
			a.set(start[(stena + 1) % 4]); a.sub(p1);
			// druhy vektor - z vrcholu do bodu uprostred
			b.set(p2); b.sub(p1);
			// => normala
			n.set(a); n.cross(b); n.normalize();
			
			// postup nahoru po stene
			for (int i = 0; i < dl; i++) {
				// vypocet krajnich bodu zkriveneho pasu:
				// vlevo
				p1.set(start[stena]);  // v podstave
				p2.set(0, 0, h);  // vrchol
				a.set(p2); a.sub(p1);  // cesta k vrcholu
				a.mul(1.0 / dl);  // krucek k vrcholu
				vlevodole.set(p1);
				b.set(a); b.mul(i);
				vlevodole.add(b);
				vlevonahore.set(p1);
				vlevonahore.add(b);
				vlevonahore.add(a);
				
				// vpravo
				p1.set(start[(stena + 1) % 4]);  // v podstave
				p2.set(0, 0, h);  // vrchol
				a.set(p2); a.sub(p1);  // cesta k vrcholu
				a.mul(1.0 / dl);  // krucek k vrcholu
				vpravodole.set(p1);
				b.set(a); b.mul(i);
				vpravodole.add(b);
				vpravonahore.set(p1);
				vpravonahore.add(b);
				vpravonahore.add(a);
				
				// rozsekani pasu - horizontalni postup
				for (int j = 0; j < dx[stena]; j++) {
					// postup vpravo dole (a) a nahore (b)
					a.set(vpravodole); a.sub(vlevodole);
					a.mul(1.0 / dx[stena]);
					b.set(vpravonahore); b.sub(vlevonahore);
					b.mul(1.0 / dx[stena]);
					
					// spodni trojuhelnik
					p1.set(a); p1.mul(j); p1.add(vlevodole);
					p2.set(p1); p2.add(a);
					p3.set(b); p3.mul(j); p3.add(vlevonahore);
					
					t = new Triangle(p1, p2, p3, n, n, n, c, c, c, n);
					t.diff.set(diff);
					t.spec.set(spec);
					t.shadingType = shadingType;
					triangles.add(t);
					
					// horni trojuhelnik
					p1.set(p2);
					p2.set(p3); p2.add(b);
					
					t = new Triangle(p1, p2, p3, n, n, n, c, c, c, n);
					t.diff.set(diff);
					t.spec.set(spec);
					t.shadingType = shadingType;
					triangles.add(t);
				}  // pas na stene
			}  // stena
		}  // steny
		
		// provedeni modelovacich transformaci
		transform();
	}  // triangulate
	
}
