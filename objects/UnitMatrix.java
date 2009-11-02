package objects;

import javax.vecmath.*;

/**
 * Typove matice
 */
public class UnitMatrix {

	/**
	 * Jednotkova matice 4 x 4
	 */
	public static final Matrix4d ONE4 = new Matrix4d(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1);

	
	/**
	 * Nulova matice 4 x 4
	 */
	public static final Matrix4d ZERO4 = new Matrix4d(
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0);
}
