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
		// TODO doplnit (my_viewing_transf.cpp)
	}
	
	
	/**
	 * Oreze zornym hranolem
	 */
	public void clipping() {
		// TODO doplnit (my_clipping.cpp)
	}
	
	
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
		// TODO doplnit (my_map_to_DC.cpp)
	}
	
	
	/**
	 * Rasterizuje do mapy pixelu
	 * - rozsviti jednotlive pixely u vsech trojuhelniku sceny
	 * @param map Mapa pixelu
	 */
	public void rasterize(PixelMap map) {
		Triangle t;
		Iterator<Triangle> iterator = triangles.iterator();
		while (iterator.hasNext()) {
			t = iterator.next();
			if (t.shadingType == ShadingType.CONST)
				shadingConst(map, t);
			else if (t.shadingType == ShadingType.PHONG)
				shadingPhong(map, t);
			else
				shadingGouard(map, t);
		}
	}
	
	
	/**
	 * Konstantne vystinuje dany trojuhelnik do dane mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingConst(PixelMap map, Triangle t) {
		// TODO doplnit
	}
	
	
	/**
	 * Vystinuje Gouardovou metodou dany trojuhelnik do dane mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingGouard(PixelMap map, Triangle t) {
		// TODO doplnit
	}
	
	
	/**
	 * Vystinuje Phongovou metodou dany trojuhelnik do dane mapy pixelu
	 * @param map Mapa pixelu
	 * @param t Trojuhelnik
	 */
	private void shadingPhong(PixelMap map, Triangle t) {
		// TODO doplnit
	}
	
}
