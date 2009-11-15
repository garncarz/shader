package objects;

import shader.ShadingType;
import geom.*;

/**
 * Kvadr
 */
public class Block extends GeomObject {

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
	public Block() {
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
	public Block(double l, double w, double h,
			int dl, int dw, int dh) {
		this();
		this.l = l;
		this.w = w;
		this.h = h;
		this.dl = dl;
		this.dw = dw;
		this.dh = dh;
	}
	
	
	/**
	 * Trianguluje kvadr
	 */
	public void triangulate() {
		// body trojuhelniku, normala
		Vector3D p1 = new Vector3D(),
				p2 = new Vector3D(),
				p3 = new Vector3D(),
				n = new Vector3D();
		// pocatecni barva
		ColorRGB c = new ColorRGB(0, 0, 0);
		// trojuhelnik
		Triangle t;
		// kroky pro triangulaci (delka, sirka, vyska)
		double stepl, stepw, steph;
		
		// vypocet kroku pro triangulaci
		stepl = (dl > 0) ? l / dl : l;
		stepw = (dw > 0) ? w / dw : w;
		steph = (dh > 0) ? h / dh : h;
		
		// predni a zadni strana
		for (int i = 0; i < dl; i++)
			for (int j = 0; j < dh; j++) {
				// predni strana:
				
				// spodni trojuhelnik
				p1.set(-l / 2 + i * stepl, -w / 2, -h / 2 + j * steph);
				p2.set(-l / 2 + (i + 1) * stepl, -w / 2, -h / 2 + j * steph);
				p3.set(-l / 2 + i * stepl, -w / 2, -h / 2 + (j + 1) * steph);
				n.set(0, -1, 0);
				t = new Triangle(p1, p2, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(-l / 2 + (i + 1) * stepl, -w / 2,
						-h / 2 + (j + 1) * steph);
				t = new Triangle(p2, p1, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// zadni strana:
				
				// spodni trojuhelnik
				p1.set(-l / 2 + i * stepl, w / 2, -h / 2 + j * steph);
				p2.set(-l / 2 + (i + 1) * stepl, w / 2, -h / 2 + j * steph);
				p3.set(-l / 2 + i * stepl, w / 2, -h / 2 + (j + 1) * steph);
				n.set(0, 1, 0);
				t = new Triangle(p2, p1, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(-l / 2 + (i + 1) * stepl, w / 2,
						-h / 2 + (j + 1) * steph);
				t = new Triangle(p2, p3, p1, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
			}
		
		// prava a leva stena
		for (int i = 0; i < dw; i++)
			for (int j = 0; j < dh; j++) {
				// prava stena:
				
				// spodni trojuhelnik
				p1.set(l / 2, -w / 2 + i * stepw, -h / 2 + j * steph);
				p2.set(l / 2, -w / 2 + (i + 1) * stepw, -h / 2 + j * steph);
				p3.set(l / 2, -w / 2 + i * stepw, -h / 2 + (j + 1) * steph);
				n.set(1, 0, 0);
				t = new Triangle(p1, p2, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(l / 2, -w / 2 + (i + 1) * stepw,
						-h / 2 + (j + 1) * steph);
				t = new Triangle(p2, p1, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// leva stena:
				
				// spodni trojuhelnik
				p1.set(-l / 2, -w / 2 + i * stepw, -h / 2 + j * steph);
				p2.set(-l / 2, -w / 2 + (i + 1) * stepw, -h / 2 + j * steph);
				p3.set(-l / 2, -w / 2 + i * stepw, -h / 2 + (j + 1) * steph);
				n.set(-1, 0, 0);
				t = new Triangle(p2, p1, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(-l / 2, -w / 2 + (i + 1) * stepw,
						-h / 2 + (j + 1) * steph);
				t = new Triangle(p2, p3, p1, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
			}
		
		// horni a spodni stena
		for (int i = 0; i < dl; i++)
			for (int j = 0; j < dw; j++) {
				// horni stena:
				
				// spodni trojuhelnik
				p1.set(-l / 2 + i * stepl, -w / 2 + j * stepw, h / 2);
				p2.set(-l / 2 + (i + 1) * stepl, -w / 2 + j * stepw, h / 2);
				p3.set(-l / 2 + i * stepl, -w / 2 + (j + 1) * stepw, h / 2);
				n.set(0, 0, 1);
				t = new Triangle(p1, p2, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(-l / 2 + (i + 1) * stepl, -w / 2 + (j + 1) * stepw,
						h / 2);
				t = new Triangle(p2, p1, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// spodni stena:
				
				// spodni trojuhelnik
				p1.set(-l / 2 + i * stepl, -w / 2 + j * stepw, -h / 2);
				p2.set(-l / 2 + (i + 1) * stepl, -w / 2 + j * stepw, -h / 2);
				p3.set(-l / 2 + i * stepl, -w / 2 + (j + 1) * stepw, -h / 2);
				n.set(0, 0, -1);
				t = new Triangle(p2, p1, p3, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
				
				// horni trojuhelnik
				p1.set(-l / 2 + (i + 1) * stepl, -w / 2 + (j + 1) * stepw,
						-h / 2);
				t = new Triangle(p2, p3, p1, n, n, n, c, c, c, n);
				t.diff.set(diff);
				t.spec.set(spec);
				t.shadingType = shadingType;
				triangles.add(t);
			}
		
		// provedeni modelovacich transformaci
		transform();
	}
	
}
