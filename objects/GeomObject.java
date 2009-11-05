package objects;

import geom.*;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Zakladni geometricke teleso
 */
public class GeomObject {

	/**
	 * Difuzni koeficient odrazu
	 */
	ColorRGB diff;
	
	/**
	 * Zrcadlovy koeficient odrazu
	 */
	ColorRGB spec;
	
	/**
	 * Typ stinovani
	 */
	ShadingType shadingType;
	
	/**
	 * Modelovaci transformace
	 */
	Matrix44 modTransf;
	
	/**
	 * Seznam trojuhelniku telesa
	 */
	ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	
	
	/**
	 * Modelovaci transformace posunuti
	 * @param t Vektor o kolik
	 */
	void translate(Vector3D t) {
		Matrix44 tmp = new Matrix44();
		tmp.translateTransform(t);
		modTransf.mul(tmp);
	}
	
	
	/**
	 * Modelovaci transformace zmena meritka
	 * @param s Vektor zmeny meritka
	 */
	void scale(Vector3D s) {
		Matrix44 tmp = new Matrix44();
		tmp.scaleTransform(s);
		modTransf.mul(tmp);
	}
	
	
	/**
	 * Modelovaci transformace rotace
	 * @param r Vektor rotace
	 */
	void rotate(Vector3D r) {
		Matrix44 tmp = new Matrix44();
		tmp.rotateTransform(r);
		modTransf.mul(tmp);
	}
	
	
	/**
	 * Realizace modelovaci transformace
	 */
	void transform() {
		Triangle t;
		Vector3Dh newPoint = new Vector3Dh();
		Vector3D newNormal = new Vector3D();
		Vector3D p1 = new Vector3D();
		Vector3D p2 = new Vector3D();
		Vector3D p3 = new Vector3D();
		Vector3D n = new Vector3D();
		Vector3D a = new Vector3D();
		Vector3D b = new Vector3D();
		
		Matrix44 normTransf = new Matrix44(modTransf);
		normTransf.translateTransform(new Vector3D(0, 0, 0));
		
		Iterator<Triangle> iterator = triangles.iterator();
		while (iterator.hasNext()) {
			t = iterator.next();
			
			newPoint.set(t.p1);
			newPoint.mul(modTransf);
			newPoint.normalizeW();
			t.p1.set(newPoint);
			
			newPoint.set(t.p2);
			newPoint.mul(modTransf);
			newPoint.normalizeW();
			t.p2.set(newPoint);
			
			newPoint.set(t.p3);
			newPoint.mul(modTransf);
			newPoint.normalizeW();
			t.p3.set(newPoint);
			
			newNormal.set(t.n1);
			newNormal.mul(normTransf);
			newNormal.normalize();
			t.n1.set(newNormal);
			
			newNormal.set(t.n2);
			newNormal.mul(normTransf);
			newNormal.normalize();
			t.n2.set(newNormal);
			
			newNormal.set(t.n3);
			newNormal.mul(normTransf);
			newNormal.normalize();
			t.n3.set(newNormal);
			
			// prepocet normaly plochy trojuhelniku
			p1.set(t.p1);
			p2.set(t.p2);
			p3.set(t.p3);
			a.set(p2);
			a.sub(p1);
			b.set(p3);
			b.sub(p1);
			n.set(a);
			n.cross(b);
			n.normalize();
			t.n.set(n);
		}
	}	// transform
	
	
	/**
	 * Pripoji svuj seznam trojuhelniku k odkazovanemu seznamu 
	 * @param list Cizi seznam trojuhelniku
	 */
	public void concatenate(ArrayList<Triangle> list) {
		list.addAll(triangles);
	}
	
}
