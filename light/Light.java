package light;

import geom.*;
import objects.*;

/**
 * Objekt svetla
 */
public interface Light {
	
	/**
	 * Vypocte trojuhelniku prispevky svetla ve vrcholech,
	 * pro Gouraudovu metodu
	 * @param t Trojuhelnik
	 * @param prp Projection Reference Point
	 */
	public void myLight(Triangle t, Vector3D prp);
	
	/**
	 * Vrati prispevek svetla pro dany bod
	 * @param prp Projection Reference Point
	 * @param point Bod
	 * @param nor Normala bodu
	 * @param diff Difuzni koeficient odrazu
	 * @param spec Zrcadlovy koeficient odrazu
	 * @return Prispevek svetla pro bod
	 */
	public ColorRGB myLight(Vector3D prp, Vector3D point,
			Vector3D nor, ColorRGB diff, ColorRGB spec);

}
