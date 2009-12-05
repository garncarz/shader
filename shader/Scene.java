package shader;

import java.util.*;

import geom.*;
import objects.*;
import light.*;

/**
 * Trida graficke sceny, organizuje vypocty pro zobrazeni
 */
public class Scene {

	/**
	 * Kamera sceny
	 */
	public Camera cam = new Camera();
	
	/**
	 * Seznam objektu sceny
	 */
	public ArrayList<GeomObject> objects = new ArrayList<GeomObject>();
	
	/**
	 * Seznam svetel
	 */
	public ArrayList<Light> lights = new ArrayList<Light>();
	
	/**
	 * Seznam trojuhelniku sceny
	 */
	public ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	
		
	/**
	 * Konstruktor
	 */
	public Scene() { }
	
	
	/**
	 * Trianguluje telesa
	 * (aproximuje povrchy vsech teles ve scene pomoci trojuhelniku)
	 */
	public void triangulate() {
		GeomObject object;
		Iterator<GeomObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			object = iterator.next();
			object.triangulate();
			object.concatenate(triangles);
		}
	}
	
	
	/**
	 * Odstrani odvracene trojuhelniky
	 */
	public void trivialReject() {
		// trojuhelnik
		Triangle t;
		// bod promitani
		Vector3D p = new Vector3D(cam.getPRPinWRC());
		// bod trojuhelniku v homogennich souradnicich
		Vector3Dh pth = new Vector3Dh();
		// bod trojuhelniku v 3D souradnicich
		Vector3D pt = new Vector3D();
		// pomocny vektor
		Vector3D v = new Vector3D();
		// pomocna promenna
		double s;
		
		Iterator<Triangle> iterator = triangles.iterator();
		while (iterator.hasNext()) {
			t = iterator.next();
			
			// podle skalarniho soucinu vektoru, jak se divam, a normaly:
			// kladny - nevidim
			// zaporny - vidim
			
			pth.set(t.p1);
			pth.normalizeW();
			pt.set(pth);
			v.set(pt); v.sub(p);
			s = v.dot(t.n);
			
			if (s > 0)
				iterator.remove();
		}
	}  // trivialReject
	
	
	/**
	 * Vypocte barvy ve vrcholech trojuhelniku
	 */
	public void computeLighting() {
		Triangle t;
		Light l;
		
		Iterator<Triangle> tIter = triangles.iterator();
		while (tIter.hasNext()) {
			t = tIter.next();
			
			Iterator<Light> lIter = lights.iterator();
			while (lIter.hasNext()) {
				l = lIter.next();
				l.myLight(t, cam.getPRPinWRC());
			}
		}
	}
	
	
	/**
	 * Pouzije zobrazovaci transformace
	 */
	public void viewingTransform() {
		Triangle t;
		Iterator<Triangle> iterator = triangles.iterator();
		while(iterator.hasNext()) {
			t = iterator.next();
			
			t.p1.mul(cam.getM());
			t.p2.mul(cam.getM());
			t.p3.mul(cam.getM());
		}
	}
	
	
	/**
	 * Prida do seznamu trojuhelniku dva nove trojuhelniky vznikle
	 * orezanim prvniho vrcholu trojuhelniku daneho argumenty danou rovinou
	 * @param plane Orezavaci rovina
	 * @param p1 Prvni vrchol
	 * @param n1 Normala prvniho vrcholu
	 * @param c1 Barva prvniho vrcholu
	 * @param p2 Druhy vrchol
	 * @param n2 Normala druheho vrcholu
	 * @param c2 Barva druheho vrcholu
	 * @param p3 Treti vrchol
	 * @param n3 Normala tretiho vrcholu
	 * @param c3 Barva tretiho vrcholu
	 * @param diff Difuzni koeficient odrazu
	 * @param spec Zrcadlovy koeficient odrazu
	 * @param shading Typ stinovani
	 */
	private void clipOnePoint(Vector3Dh plane,
			Vector3Dh p1, Vector3D n1, ColorRGB c1,
			Vector3Dh p2, Vector3D n2, ColorRGB c2,
			Vector3Dh p3, Vector3D n3, ColorRGB c3,
			ColorRGB diff, ColorRGB spec, ShadingType shading) {
		Vector3Dh p12 = new Vector3Dh(),
				p13 = new Vector3Dh(),
				tmp = new Vector3Dh();
		Vector3D n12 = new Vector3D(),
				n13 = new Vector3D(),
				a = new Vector3D(),
				b = new Vector3D();
		ColorRGB c12 = new ColorRGB(),
				c13 = new ColorRGB();
		Triangle t1 = new Triangle(),
				t2 = new Triangle();
		double part;
		
		tmp.set(p1); tmp.sub(p2);
		part = plane.dot(p1) / plane.dot(tmp);
		p12.set(p2); p12.sub(p1); p12.mul(part); p12.add(p1);
		n12.set(n2); n12.sub(n1); n12.mul(part); n12.add(n1);
		c12.set(c2); c12.sub(c1); c12.mul(part); c12.add(c1);
		
		t1.p1.set(p12); t1.n1.set(n12); t1.c1.set(c12);
		t1.p2.set(p2); t1.n2.set(n2); t1.c2.set(c2);
		t1.p3.set(p3); t1.n3.set(n3); t1.c3.set(c3);
		t1.diff.set(diff); t1.spec.set(spec); t1.shadingType = shading;
		
		a.set(t1.p2); a.sub(t1.p1);
		b.set(t1.p3); b.sub(t1.p1);
		t1.n.set(a); t1.n.cross(b); t1.n.normalize();
		
		triangles.add(t1);
		
		
		tmp.set(p1); tmp.sub(p3);
		part = plane.dot(p1) / plane.dot(tmp);
		p13.set(p3); p13.sub(p1); p13.mul(part); p13.add(p1);
		n13.set(n3); n13.sub(n1); n13.mul(part); n13.add(n1);
		c13.set(c3); c13.sub(c1); c13.mul(part); c13.add(c1);
		
		t2.p1.set(p12); t2.n1.set(n12); t2.c1.set(c12);
		t2.p2.set(p3); t2.n2.set(n3); t2.c2.set(c3);
		t2.p3.set(p13); t2.n3.set(n13); t2.c3.set(c13);
		t2.diff.set(diff); t2.spec.set(spec); t2.shadingType = shading;
		
		a.set(t2.p2); a.sub(t2.p1);
		b.set(t2.p3); b.sub(t2.p1);
		t2.n.set(a); t2.n.cross(b); t2.n.normalize();
		
		triangles.add(t2);
	}  // clipOnePoint
	
	
	/**
	 * Prida do seznamu trojuhelniku novy trojuhelnik vznikly orezanim
	 * druheho a tretiho vrcholu trojuhelniku daneho argumenty danou rovinou
	 * @param plane Orezavaci rovina
	 * @param p1 Prvni vrchol
	 * @param n1 Normala prvniho vrcholu
	 * @param c1 Barva prvniho vrcholu
	 * @param p2 Druhy vrchol
	 * @param n2 Normala druheho vrcholu
	 * @param c2 Barva druheho vrcholu
	 * @param p3 Treti vrchol
	 * @param n3 Normala tretiho vrcholu
	 * @param c3 Barva tretiho vrcholu
	 * @param diff Difuzni koeficient odrazu
	 * @param spec Zrcadlovy koeficient odrazu
	 * @param shading Typ stinovani
	 */
	private void clipTwoPoints(Vector3Dh plane,
			Vector3Dh p1, Vector3D n1, ColorRGB c1,
			Vector3Dh p2, Vector3D n2, ColorRGB c2,
			Vector3Dh p3, Vector3D n3, ColorRGB c3,
			ColorRGB diff, ColorRGB spec, ShadingType shading) {
		Vector3Dh p12 = new Vector3Dh(),
				p13 = new Vector3Dh(),
				tmp = new Vector3Dh();
		Vector3D n12 = new Vector3D(),
				n13 = new Vector3D(),
				a = new Vector3D(),
				b = new Vector3D();
		ColorRGB c12 = new ColorRGB(),
				c13 = new ColorRGB();
		Triangle t = new Triangle();
		double part;
		
		tmp.set(p1); tmp.sub(p2);
		part = plane.dot(p1) / plane.dot(tmp);
		p12.set(p2); p12.sub(p1); p12.mul(part); p12.add(p1);
		n12.set(n2); n12.sub(n1); n12.mul(part); n12.add(n1);
		c12.set(c2); c12.sub(c1); c12.mul(part); c12.add(c1);
		
		tmp.set(p1); tmp.sub(p3);
		part = plane.dot(p1) / plane.dot(tmp);
		p13.set(p3); p13.sub(p1); p13.mul(part); p13.add(p1);
		n13.set(n3); n13.sub(n1); n13.mul(part); n13.add(n1);
		c13.set(c3); c13.sub(c1); c13.mul(part); c13.add(c1);
		
		t.p1.set(p1); t.n1.set(n1); t.c1.set(c1);
		t.p2.set(p12); t.n2.set(n12); t.c2.set(c12);
		t.p3.set(p13); t.n3.set(n13); t.c3.set(c13);
		t.diff.set(diff); t.spec.set(spec); t.shadingType = shading;
		
		a.set(t.p2); a.sub(t.p1);
		b.set(t.p3); b.sub(t.p1);
		t.n.set(a); t.n.cross(b); t.n.normalize();
		
		triangles.add(t);
	}  // clipTwoPoints
	
	
	/**
	 * Oreze zornym hranolem
	 */
	public void clipping() {
		// orezavaci roviny
		double planes[][] = new double[][]{
				{ 1, 0, 0, -1},
				{-1, 0, 0, -1},
				{0, -1, 0, -1},
				{0,  1, 0, -1},
				{0, 0,  1, 0},
				{0, 0, -1, -1}
		};
		
		Triangle t;
		for (int iplane = 0; iplane < 6; iplane++) {
			Vector3Dh plane = new Vector3Dh(planes[iplane][0],
					planes[iplane][1],
					planes[iplane][2],
					planes[iplane][3]);
			
			for (int it = 0; it < triangles.size();) {
				t = triangles.get(it);

				boolean b1 = t.p1.dot(plane) > 0,
						b2 = t.p2.dot(plane) > 0,
						b3 = t.p3.dot(plane) > 0;
				
				// vsechny body trojuhelniku uvnitr
				if (!b1 && !b2 && !b3) {
					it++;
					continue;
				}
				
				// vsechny tri body mimo
				else if (b1 && b2 && b3)
					;
				
				// dva body mimo
				else if (b1 && b2)
					clipTwoPoints(plane, t.p3, t.n3, t.c3, t.p1, t.n1, t.c1,
							t.p2, t.n2, t.c2, t.diff, t.spec, t.shadingType);
				else if (b1 && b3)
					clipTwoPoints(plane, t.p2, t.n2, t.c2, t.p3, t.n3, t.c3,
							t.p1, t.n1, t.c1, t.diff, t.spec, t.shadingType);
				else if (b2 && b3)
					clipTwoPoints(plane, t.p1, t.n1, t.c1, t.p2, t.n2, t.c2,
							t.p3, t.n3, t.c3, t.diff, t.spec, t.shadingType);
				
				// jeden bod mimo
				else if (b1)
					clipOnePoint(plane, t.p1, t.n1, t.c1, t.p2, t.n2, t.c2,
							t.p3, t.n3, t.c3, t.diff, t.spec, t.shadingType);
				else if (b2)
					clipOnePoint(plane, t.p2, t.n2, t.c2, t.p3, t.n3, t.c3,
							t.p1, t.n1, t.c1, t.diff, t.spec, t.shadingType);
				else if (b3)
					clipOnePoint(plane, t.p3, t.n3, t.c3, t.p1, t.n1, t.c1,
							t.p2, t.n2, t.c2, t.diff, t.spec, t.shadingType);
				
				// odstraneni puvodniho trojuhelniku, ktery vycuhoval
				triangles.remove(it);
			}
		}
	}  // clipping
	
	
	/**
	 * Znormalizuje vrcholy trojuhelniku podle W
	 */
	public void normalizeW() {
		Triangle t;
		Iterator<Triangle> iterator = triangles.iterator();
		while(iterator.hasNext()) {
			t = iterator.next();
			t.p1.normalizeW();
			t.p2.normalizeW();
			t.p3.normalizeW();
		}
	}
	
	
	/**
	 * Zmapuje na vystupni zarizeni
	 * @param Pxmin Minimalni x-ova souradnice vystupniho zarizeni
	 * @param Pymin Minimalni y-ova souradnice vystupniho zarizeni
	 * @param Pxmax Maximalni x-ova souradnice vystupniho zarizeni
	 * @param Pymax Maximalni y-ova souradnice vystupniho zarizeni
	 */
	public void mapToDC(double Pxmin, double Pymin,
			double Pxmax, double Pymax) {
		double scaleX = (Pxmax - Pxmin) / (cam.getUmax() - cam.getUmin());
		double scaleY = (Pymax - Pymin) / (cam.getVmax() - cam.getVmin());
		
		Triangle t;
		Iterator<Triangle> iterator = triangles.iterator();
		while(iterator.hasNext()) {
			t = iterator.next();
			
			t.p1.setX((t.p1.getX() - cam.getUmin()) * scaleX + Pxmin);
			t.p1.setY((t.p1.getY() - cam.getVmin()) * scaleY + Pymin);
			
			t.p2.setX((t.p2.getX() - cam.getUmin()) * scaleX + Pxmin);
			t.p2.setY((t.p2.getY() - cam.getVmin()) * scaleY + Pymin);
			
			t.p3.setX((t.p3.getX() - cam.getUmin()) * scaleX + Pxmin);
			t.p3.setY((t.p3.getY() - cam.getVmin()) * scaleY + Pymin);
		}
	}
	
	
	/**
	 * Vrati odmapovany bod z vystupniho zarizeni,
	 * vhodne pro Phongovo osvetleni
	 * @param from Namapovany bod
	 * @param Pxmin Minimalni x-ova souradnice vystupniho zarizeni
	 * @param Pymin Minimalni y-ova souradnice vystupniho zarizeni
	 * @param Pxmax Maximalni x-ova souradnice vystupniho zarizeni
	 * @param Pymax Maximalni y-ova souradnice vystupniho zarizeni
	 * @return Odmapovany bod
	 */
	private Vector3D unmapFromDC(Vector3D from, double Pxmin, double Pymin,
			double Pxmax, double Pymax) {
		Vector3D p = new Vector3D();
		
		double scaleX = (Pxmax - Pxmin) / (cam.getUmax() - cam.getUmin());
		double scaleY = (Pymax - Pymin) / (cam.getVmax() - cam.getVmin());
		
		p.setX((from.getX() - Pxmin) / scaleX + cam.getUmin());
		p.setY((from.getY() - Pymin) / scaleY + cam.getVmin());
		p.setZ(from.getZ());

		return p;
	}
	
	
	/**
	 * Rasterizuje scenu do mapy pixelu
	 * - rozsviti jednotlive pixely u vsech trojuhelniku sceny
	 * @param map Mapa pixelu
	 */
	public void rasterize(PixelMap map) {
		Triangle t;
		Iterator<Triangle> iterator = triangles.iterator();
		while (iterator.hasNext()) {
			t = iterator.next();
			if (t.shadingType == ShadingType.WIRE)
				shadingWire(map, t);
			else if (t.shadingType == ShadingType.CONST)
				shadingConst(map, t);
			else if (t.shadingType == ShadingType.GOUARD)
				shadingGouard(map, t);
			else if (t.shadingType == ShadingType.PHONG)
				shadingPhong(map, t);
			else
				shadingWire(map, t);
		}
	}
	
	
	/**
	 * Obrysove vystinuje dany trojuhelnik do dane mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingWire(PixelMap map, Triangle t) {
		Vector3D pA = new Vector3D(t.p1),
				pB = new Vector3D(t.p2),
				pC = new Vector3D(t.p3),
				dir = new Vector3D(),
				v = new Vector3D();
		ColorRGBZ cA = new ColorRGBZ(t.c1, t.p1.getZ()),
				cB = new ColorRGBZ(t.c2, t.p2.getZ()),
				cC = new ColorRGBZ(t.c3, t.p3.getZ()),
				cDir = new ColorRGBZ(),
				c = new ColorRGBZ();
		int parts;
		
		// od A k B:
		v.set(pB); v.sub(pA); parts = (int)v.length();
		dir.set(pB); dir.sub(pA); dir.mul(1.0 / parts);
		v.set(pA);
		
		cDir.set(cB); cDir.sub(cA); cDir.mul(1.0 / parts);
		c.set(cA);
		
		for (int i = 0; i < parts; i++) {
			map.setPixel((int)v.getX(), (int)v.getY(), c);
			v.add(dir); c.add(cDir);
		}
		
		// od B k C:
		v.set(pC); v.sub(pB); parts = (int)v.length();
		dir.set(pC); dir.sub(pB); dir.mul(1.0 / parts);
		v.set(pB);
		
		cDir.set(cC); cDir.sub(cB); cDir.mul(1.0 / parts);
		c.set(cB);
		
		for (int i = 0; i < parts; i++) {
			map.setPixel((int)v.getX(), (int)v.getY(), c);
			v.add(dir); c.add(cDir);
		}
		
		// od A k C:
		v.set(pC); v.sub(pA); parts = (int)v.length();
		dir.set(pC); dir.sub(pA); dir.mul(1.0 / parts);
		v.set(pA);
		
		cDir.set(cC); cDir.sub(cA); cDir.mul(1.0 / parts);
		c.set(cA);
		
		for (int i = 0; i < parts; i++) {
			map.setPixel((int)v.getX(), (int)v.getY(), c);
			v.add(dir); c.add(cDir);
		}
	}  // shadingWire
	
	
	/**
	 * Vystinuje trojuhelnik konstantnim zpusobem do mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingConst(PixelMap map, Triangle t) {
		Vector3D pA = new Vector3D(t.p1),
				pB = new Vector3D(t.p2),
				pC = new Vector3D(t.p3),
				dirAB = new Vector3D(),
				dir = new Vector3D(),
				vAB = new Vector3D(),
				v = new Vector3D();
		ColorRGBZ cA = new ColorRGBZ(t.c1, t.p1.getZ()),
				cB = new ColorRGBZ(t.c2, t.p2.getZ()),
				cC = new ColorRGBZ(t.c3, t.p3.getZ()),
				cMiddle = new ColorRGBZ();  // prumerna barva trojuhelniku
		int partsAB, parts;
		
		cMiddle.set(cA); cMiddle.add(cB); cMiddle.add(cC);
		cMiddle.mul(1.0 / 3);
		
		// od A k B:
		vAB.set(pB); vAB.sub(pA);
		partsAB = (int)Math.ceil(vAB.length()) * 2;
			// * 2 => ciste stinovani, bez "cerneho antialiasingu"
		dirAB.set(pB); dirAB.sub(pA); dirAB.mul(1.0 / partsAB);
		vAB.set(pA);
		
		for (int i = 0; i <= partsAB; i++) {
			// od C k AB:
			v.set(vAB); v.sub(pC);
			parts = (int)Math.ceil(v.length());
			dir.set(vAB); dir.sub(pC); dir.mul(1.0 / parts);
			v.set(pC);
			
			for (int j = 0; j <= parts; j++) {
				cMiddle.setZ(v.getZ());
				map.setPixel((int)v.getX(), (int)v.getY(), cMiddle);
				v.add(dir);
			}
			
			vAB.add(dirAB);
		}
		
	}  // shadingConst
	
	
	/**
	 * Vystinuje trojuhelnik Gouardovou metodou do mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingGouard(PixelMap map, Triangle t) {
		Vector3D pA = new Vector3D(t.p1),
				pB = new Vector3D(t.p2),
				pC = new Vector3D(t.p3),
				dirAB = new Vector3D(),
				dir = new Vector3D(),
				vAB = new Vector3D(),
				v = new Vector3D();
		ColorRGBZ cA = new ColorRGBZ(t.c1, t.p1.getZ()),
				cB = new ColorRGBZ(t.c2, t.p2.getZ()),
				cC = new ColorRGBZ(t.c3, t.p3.getZ()),
				cDirAB = new ColorRGBZ(),
				cDir = new ColorRGBZ(),
				cAB = new ColorRGBZ(),
				c = new ColorRGBZ();
		int partsAB, parts;
		
		// od A k B:
		vAB.set(pB); vAB.sub(pA);
		partsAB = (int)Math.ceil(vAB.length()) * 2;
			// * 2 => ciste stinovani, bez "cerneho antialiasingu"
		dirAB.set(pB); dirAB.sub(pA); dirAB.mul(1.0 / partsAB);
		vAB.set(pA);
		
		cDirAB.set(cB); cDirAB.sub(cA); cDirAB.mul(1.0 / partsAB);
		cAB.set(cA);
		
		for (int i = 0; i <= partsAB; i++) {
			// od C k AB:
			v.set(vAB); v.sub(pC);
			parts = (int)Math.ceil(v.length());
			dir.set(vAB); dir.sub(pC); dir.mul(1.0 / parts);
			v.set(pC);
			
			cDir.set(cAB); cDir.sub(cC); cDir.mul(1.0 / parts);
			c.set(cC);
			
			for (int j = 0; j <= parts; j++) {
				map.setPixel((int)v.getX(), (int)v.getY(), c);
				v.add(dir); c.add(cDir);
			}
			
			vAB.add(dirAB); cAB.add(cDirAB);
		}
		
	}  // shadingGouard
	
	
	/**
	 * Vystinuje trojuhelnik Phongovou metodou do mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingPhong(PixelMap map, Triangle t) {
		Vector3D pA = new Vector3D(t.p1),  // body
				pB = new Vector3D(t.p2),
				pC = new Vector3D(t.p3),
				
				dirAB = new Vector3D(),  // vektory pro polohu uvnitr
				dir = new Vector3D(),
				vAB = new Vector3D(),
				v = new Vector3D(),
				
				nA = new Vector3D(t.n1),  // normaly
				nB = new Vector3D(t.n2),
				nC = new Vector3D(t.n3),
				
				nDirAB = new Vector3D(),  // vektory pro zmenu normaly
				nDir = new Vector3D(),
				nAB = new Vector3D(),
				n = new Vector3D();
		int partsAB, parts;
		
		// od A k B:
		vAB.set(pB); vAB.sub(pA);
		partsAB = (int)Math.ceil(vAB.length()) * 2;
			// * 2 => ciste stinovani, bez "cerneho antialiasingu"
		dirAB.set(pB); dirAB.sub(pA); dirAB.mul(1.0 / partsAB);
		vAB.set(pA);
		
		nDirAB.set(nB); nDirAB.sub(nA); nDirAB.mul(1.0 / partsAB);
		nAB.set(nA);
		
		for (int i = 0; i <= partsAB; i++) {
			// od C k AB:
			v.set(vAB); v.sub(pC);
			parts = (int)Math.ceil(v.length());
			dir.set(vAB); dir.sub(pC); dir.mul(1.0 / parts);
			v.set(pC);
			
			nDir.set(nAB); nDir.sub(nC); nDir.mul(1.0 / parts);
			n.set(nC);
			
			for (int j = 0; j <= parts; j++) {
				ColorRGB c = new ColorRGB();
				
				Vector3Dh ph = new Vector3Dh(unmapFromDC(v, Definitions.PXMIN,
						Definitions.PYMIN, Definitions.PXMAX,
						Definitions.PYMAX));
				ph.setW(1);
				ph.mul(cam.getIM());
				ph.normalizeW();
				Vector3D p = new Vector3D(ph);
				
				Iterator<Light> it = lights.iterator();
				while (it.hasNext()) {
					Light light = it.next();
					c.add(light.myLight(cam.PRP, p, n, t.diff, t.spec));
				}
				
				map.setPixel((int)v.getX(), (int)v.getY(),
						new ColorRGBZ(c, v.getZ()));
				v.add(dir); n.add(nDir);
			}
			
			vAB.add(dirAB); nAB.add(nDirAB);
		}
		
	}  // shadingPhong
	
}
