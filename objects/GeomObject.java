package objects;

import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.*;

/**
 * Zakladni geometricke teleso
 */
public class GeomObject {

	/**
	 * Difuzni koeficient odrazu
	 */
	Color3f diff;
	
	/**
	 * Zrcadlovy koeficient odrazu
	 */
	Color3f spec;
	
	/**
	 * Typ stinovani
	 */
	ShadingType shadingType;
	
	/**
	 * Modelovaci transformace
	 */
	Matrix4d modTransf;
	
	/**
	 * Seznam trojuhelniku telesa
	 */
	ArrayList<Triangle> triangles;
	
	
	/**
	 * Modelovaci transformace posunuti
	 * @param t Vektor o kolik
	 */
	void translate(Vector3d t) {
		Matrix4d tmp = new Matrix4d(UnitMatrix.ONE4);
		tmp.setTranslation(t);
		modTransf.mul(tmp);
	}
	
	
	/**
	 * Modelovaci transformace zmena meritka
	 * @param s Vektor zmeny meritka
	 */
	void scale(Vector3d s) {
		Matrix4d tmp = new Matrix4d(UnitMatrix.ZERO4);
		tmp.setColumn(3, new Vector4d(s));
		tmp.setElement(3, 3, 1);
		modTransf.mul(tmp);
	}
	
	
	/**
	 * Modelovaci transformace rotace
	 * @param r Vektor rotace
	 */
	void rotate(Vector3d r) {
		double cosAlpha, sinAlpha;
		Matrix4d rotate;
		Matrix4d result = new Matrix4d(UnitMatrix.ONE4);
		Vector3d radian = new Vector3d(r);
		radian.scale(Math.PI / 2, radian);
		
		// rotace kolem osy x
		rotate = new Matrix4d(UnitMatrix.ONE4);
		cosAlpha = Math.cos(radian.getX());
		sinAlpha = Math.sin(radian.getX());
		if (Math.abs(cosAlpha) < Definitions.EPSILON)
			cosAlpha = 0;
		if (Math.abs(sinAlpha) < Definitions.EPSILON)
			sinAlpha = 0;
		rotate.setElement(2, 2, cosAlpha);
		rotate.setElement(2, 3, sinAlpha);
		rotate.setElement(3, 2, -sinAlpha);
		rotate.setElement(3, 3, cosAlpha);
		result.mul(rotate);
		
		// rotace kolem osy y
		rotate = new Matrix4d(UnitMatrix.ONE4);
		cosAlpha = Math.cos(radian.getY());
		sinAlpha = Math.sin(radian.getY());
		if (Math.abs(cosAlpha) < Definitions.EPSILON)
			cosAlpha = 0;
		if (Math.abs(sinAlpha) < Definitions.EPSILON)
			sinAlpha = 0;
		rotate.setElement(1, 1, cosAlpha);
		rotate.setElement(1, 3, sinAlpha);
		rotate.setElement(3, 1, -sinAlpha);
		rotate.setElement(3, 3, cosAlpha);
		result.mul(rotate);
		
		// rotace kolem osy z
		rotate = new Matrix4d(UnitMatrix.ONE4);
		cosAlpha = Math.cos(radian.getZ());
		sinAlpha = Math.sin(radian.getZ());
		if (Math.abs(cosAlpha) < Definitions.EPSILON)
			cosAlpha = 0;
		if (Math.abs(sinAlpha) < Definitions.EPSILON)
			sinAlpha = 0;
		rotate.setElement(1, 1, cosAlpha);
		rotate.setElement(1, 2, sinAlpha);
		rotate.setElement(2, 1, -sinAlpha);
		rotate.setElement(2, 2, cosAlpha);
		result.mul(rotate);
		
		// vysledek
		modTransf.mul(result);
	}
	
	
	/**
	 * Realizace modelovaci transformace
	 */
	void transform() {
		Triangle t;
		Vector4d newPoint;
		Vector3d newNormal;
		Vector3d p1, p2, p3, n, a, b;
		
		Matrix4d normTransf = new Matrix4d(UnitMatrix.ONE4);
		
		Iterator<Triangle> iterator = triangles.iterator();
		while (iterator.hasNext()) {
			t = iterator.next();
			
			newPoint = new Vector4d(t.p1);
		}
		
	}
	
}
