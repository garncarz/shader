package shader;

import java.util.ArrayList;

import objects.*;
import light.*;

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
	 * Seznam svetel
	 */
	public ArrayList<Light> lights;
	
	/**
	 * Seznam trojuhelniku sceny
	 */
	public ArrayList<Triangle> triangles;
	
}
