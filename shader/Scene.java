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
			
			// TODO je toto dobre?
			
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
	 */
	private void clipOnePoint(Vector3Dh plane,
			Vector3Dh p1, Vector3D n1, ColorRGB c1,
			Vector3Dh p2, Vector3D n2, ColorRGB c2,
			Vector3Dh p3, Vector3D n3, ColorRGB c3,
			ColorRGB diff, ColorRGB spec) {
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
		t1.diff.set(diff); t1.spec.set(spec);
		
		a.set(t1.p2); a.sub(t1.p1);
		b.set(t1.p3); b.sub(t1.p1);
		t1.n.set(a); t1.n.cross(b); t1.n.normalize();
		
		// pridani v pripade neporuseni roviny
		if (t1.p1.dot(plane) <= 0
				&& t1.p2.dot(plane) <= 0
				&& t1.p3.dot(plane) <= 0)
			triangles.add(t1);
		
		
		tmp.set(p1); tmp.sub(p3);
		part = plane.dot(p1) / plane.dot(tmp);
		p13.set(p3); p13.sub(p1); p13.mul(part); p13.add(p1);
		n13.set(n3); n13.sub(n1); n13.mul(part); n13.add(n1);
		c13.set(c3); c13.sub(c1); c13.mul(part); c13.add(c1);
		
		t2.p1.set(p12); t2.n1.set(n12); t2.c1.set(c12);
		t2.p2.set(p3); t2.n2.set(n3); t2.c2.set(c3);
		t2.p3.set(p13); t2.n3.set(n13); t2.c3.set(c13);
		t2.diff.set(diff); t2.spec.set(spec);
		
		a.set(t2.p2); a.sub(t2.p1);
		b.set(t2.p3); b.sub(t2.p1);
		t2.n.set(a); t2.n.cross(b); t2.n.normalize();
		
		// pridani v pripade neporuseni roviny
		if (t2.p1.dot(plane) <= 0
				&& t2.p2.dot(plane) <= 0
				&& t2.p3.dot(plane) <= 0)
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
	 */
	private void clipTwoPoints(Vector3Dh plane,
			Vector3Dh p1, Vector3D n1, ColorRGB c1,
			Vector3Dh p2, Vector3D n2, ColorRGB c2,
			Vector3Dh p3, Vector3D n3, ColorRGB c3,
			ColorRGB diff, ColorRGB spec) {
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
		
		t.p1.set(p13); t.n1.set(n13); t.c1.set(c13);
		t.p2.set(p1); t.n2.set(n1); t.c2.set(c1);
		t.p3.set(p12); t.n3.set(n12); t.c3.set(c12);
		t.diff.set(diff); t.spec.set(spec);
		
		a.set(t.p2); a.sub(t.p1);
		b.set(t.p3); b.sub(t.p1);
		t.n.set(a); t.n.cross(b); t.n.normalize();
		
		// pridani v pripade neporuseni roviny
		if (t.p1.dot(plane) <= 0
				&& t.p2.dot(plane) <= 0
				&& t.p3.dot(plane) <= 0)
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

				// vsechny body trojuhelniku uvnitr
				if (t.p1.dot(plane) <= 0
						&& t.p2.dot(plane) <= 0
						&& t.p3.dot(plane) <= 0) {
					it++;
					continue;
				}
				
				// vsechny tri body mimo
				else if (t.p1.dot(plane) > 0
						&& t.p2.dot(plane) > 0
						&& t.p3.dot(plane) > 0)
					;
				
				// dva body mimo
				else if (t.p1.dot(plane) > 0 && t.p2.dot(plane) > 0)
					clipTwoPoints(plane, t.p3, t.n3, t.c3, t.p1, t.n1, t.c1,
							t.p2, t.n2, t.c2, t.diff, t.spec);
				else if (t.p1.dot(plane) > 0 && t.p3.dot(plane) > 0)
					clipTwoPoints(plane, t.p2, t.n2, t.c2, t.p3, t.n3, t.c3,
							t.p1, t.n1, t.c1, t.diff, t.spec);
				else if (t.p2.dot(plane) > 0 && t.p3.dot(plane) > 0)
					clipTwoPoints(plane, t.p1, t.n1, t.c1, t.p2, t.n2, t.c2,
							t.p3, t.n3, t.c3, t.diff, t.spec);
				
				// jeden bod mimo
				else if (t.p1.dot(plane) < 0)
					clipOnePoint(plane, t.p1, t.n1, t.c1, t.p2, t.n2, t.c2,
							t.p3, t.n3, t.c3, t.diff, t.spec);
				else if (t.p2.dot(plane) < 0)
					clipOnePoint(plane, t.p2, t.n2, t.c2, t.p3, t.n3, t.c3,
							t.p1, t.n1, t.c1, t.diff, t.spec);
				else if (t.p3.dot(plane) < 0)
					clipOnePoint(plane, t.p3, t.n3, t.c3, t.p1, t.n1, t.c1,
							t.p2, t.n2, t.c2, t.diff, t.spec);
				
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
	 * @param Pxmin
	 * @param Pymin
	 * @param Pxmax
	 * @param Pymax
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
			else
				shadingAllPoints(map, t);
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
	 * Vrati barvu bodu trojuhelniku pro konstantni stinovani
	 * @param t Trojuhelnik
	 * @param x Souradnice x
	 * @param y Souradnice y
	 * @return Barva bodu
	 */
	private ColorRGBZ shadingConst(Triangle t, double x, double y) {
		Vector3D v1 = new Vector3D(),
				v2 = new Vector3D(),
				v3 = new Vector3D();
		ColorRGB c = new ColorRGB();
		double S1, S2, S3, S, z;
		
		c.set(t.c1); c.add(t.c2); c.add(t.c3); c.mul(1.0 / 3);
		
		S1 = Math.abs(x - t.p1.getX()) * Math.abs(y - t.p1.getY());
		S2 = Math.abs(x - t.p2.getX()) * Math.abs(y - t.p2.getY());
		S3 = Math.abs(x - t.p3.getX()) * Math.abs(y - t.p3.getY());
		
		v1.set(t.p2); v1.sub(t.p1);
		v2.set(t.p3); v1.sub(t.p1);  // ano, toto je preklep (v1.sub),
			// ale zaroven toto funguje prave takto
		v1.cross(v2);
		S = v1.length() / 2;
		
		v1.set(t.p1); v1.mul(S - S1);
		v2.set(t.p2); v2.mul(S - S2);
		v3.set(t.p3); v3.mul(S - S3);
		
		v1.add(v2); v1.add(v3);
		z = v1.getZ();
		
		return new ColorRGBZ(c, z);
	}  // shadingConst
	
	
	/**
	 * Vrati barvu bodu trojuhelniku pro Gouardovo stinovani
	 * @param t Trojuhelnik
	 * @param x Souradnice x
	 * @param y Souradnice y
	 * @return Barva bodu
	 */
	private ColorRGBZ shadingGouard(Triangle t, double x, double y) {
		return null;
	}
	
	
	/**
	 * Vrati barvu bodu trojuhelniku pro Phongovo stinovani
	 * @param t Trojuhelnik
	 * @param x Souradnice x
	 * @param y Souradnice y
	 * @return Barva bodu
	 */
	private ColorRGBZ shadingPhong(Triangle t, double x, double y) {
		// TODO dopsat vlastni
		return shadingGouard(t, x, y);
	}
	
	
	/**
	 * Vrati barvu bodu trojuhelniku pro jeho typ stinovani
	 * @param t Trojuhelnik
	 * @param x Souradnice x
	 * @param y Souradnice y
	 * @return Barva bodu
	 */
	private ColorRGBZ shadingPoint(Triangle t, double x, double y) {
		// TODO vratit
		if (true)
			return shadingConst(t, x, y);
		
		if (t.shadingType == ShadingType.GOUARD)
			return shadingGouard(t, x, y);
		if (t.shadingType == ShadingType.PHONG)
			return shadingPhong(t, x, y);
		if (t.shadingType == ShadingType.CONST)
			return shadingConst(t, x, y);
		return shadingGouard(t, x, y);
	}
	
	
	/**
	 * Projde vsechny body trojuhelniku a uplatni na ne urcene stinovani
	 * s vystupem do mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingAllPoints(PixelMap map, Triangle t) {
		Vector3D pA = new Vector3D(t.p1),
				pB = new Vector3D(t.p2),
				pC = new Vector3D(t.p3),
				pTmp = new Vector3D(),
				pMiddle = new Vector3D();
		ColorRGBZ cA = new ColorRGBZ(t.c1, t.p1.getZ()),
				cB = new ColorRGBZ(t.c2, t.p2.getZ()),
				cC = new ColorRGBZ(t.c3, t.p3.getZ()),
				cTmp = new ColorRGBZ();
		double xStartDir, xEndDir, d;
		
		// setrideni podle y:
		if (pA.getY() > pB.getY()) {
			pTmp.set(pA); pA.set(pB); pB.set(pTmp);
			cTmp.set(cA); cA.set(cB); cB.set(cTmp);
		}
		if (pA.getY() > pC.getY()) {
			pTmp.set(pA); pA.set(pC); pC.set(pTmp);
			cTmp.set(cA); cA.set(cC); cC.set(cTmp);
		}
		
		// setrideni podle x
		if (pC.getX() > pB.getX()) {
			pTmp.set(pB); pB.set(pC); pC.set(pTmp);
			cTmp.set(cB); cB.set(cC); cC.set(cTmp);
		}
		
		
		// bod mezi A a C delici radkovani, maximalne vsak ve vysce B
		pMiddle.set(pC); pMiddle.sub(pA);
		pMiddle.mul(Math.min(1, (pB.getY() - pA.getY()) /
				(pC.getY() - pA.getY())));
		pMiddle.add(pA);
		
		// spodni radkovani (odzdola nahoru):
		xStartDir = (pMiddle.getX() - pA.getX()) /
				(pMiddle.getY() - pA.getY());
		xEndDir = (pB.getX() - pA.getX()) /
				(pB.getY() - pA.getY());
		if (xEndDir < xStartDir) {  // x musi rust
			d = xStartDir; xStartDir = xEndDir; xEndDir = d;
		}
		for (double y = pA.getY(), xStart = pA.getX(), xEnd = pA.getX();
				y <= pMiddle.getY();
				y++, xStart += xStartDir, xEnd += xEndDir)
			for (int x = (int)xStart; x <= (int)Math.ceil(xEnd); x++)
				map.setPixel(x, (int)y, shadingPoint(t, x, y));
		
		
		// nyni setrideni podle y
		if (pC.getY() < pB.getY()) {
			pTmp.set(pB); pB.set(pC); pC.set(pTmp);
			cTmp.set(cB); cB.set(cC); cC.set(cTmp);
		}
		
		// prepocitani prostredniho bodu
		pMiddle.set(pC); pMiddle.sub(pA);
		pMiddle.mul(Math.min(1, (pB.getY() - pA.getY()) /
				(pC.getY() - pA.getY())));
		pMiddle.add(pA);
		
		// horni radkovani (odshora dolu):
		xStartDir = -(pC.getX() - pMiddle.getX()) /
				(pC.getY() - pMiddle.getY());
		xEndDir = -(pC.getX() - pB.getX()) /
				(pC.getY() - pB.getY());
		if (xEndDir < xStartDir) {  // x musi rust
			d = xStartDir; xStartDir = xEndDir; xEndDir = d;
		}
		for (double y = pC.getY(), xStart = pC.getX(), xEnd = pC.getX();
				y >= pMiddle.getY();
				y--, xStart += xStartDir, xEnd += xEndDir)
			for (int x = (int)xStart; x <= (int)Math.ceil(xEnd); x++)
				map.setPixel(x, (int)y, shadingPoint(t, x, y));
		
		
		// prostredni linka nakonec
		pTmp.set(pB); pTmp.sub(pMiddle);
		for (int part = 0; part < pTmp.length(); part++) {
			Vector3D v = new Vector3D(pTmp);
			v.mul(part / pTmp.length());
			v.add(pMiddle);
			map.setPixel((int)v.getX(), (int)v.getY(),
					shadingPoint(t, v.getX(), v.getY()));
		}
		
	}  // shadingAllPoints
	
}
