package shader;

import java.util.ArrayList;

import objects.*;

/**
 * Trida graficke sceny, organizuje vypocty pro zobrazeni
 */
public class Scene {

	/**
	 * Kamera sceny
	 */
	public Camera cam;
	
	/**
	 * Seznam objektu sceny
	 */
	public ArrayList<GeomObject> objects;
	
	/**
	 * Seznam trojuhelniku sceny
	 */
	public ArrayList<Triangle> triangles;
	
}
