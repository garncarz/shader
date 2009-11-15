package shader;

import geom.*;

/**
 * Hlavni trida zobrazovace, organizuje praci
 */
public class Shader {

	/**
	 * Spousteci metoda
	 * @param args Argumenty
	 */
	public static void main(String[] args) {
		Scene scene = new Scene();
		ColorRGBZ c = new ColorRGBZ(0, 0, 0, -1 * Definitions.REAL_MAX);
		PixelMap map = new PixelMap(c, Definitions.MAX_COLOR,
				Definitions.PXMAX - Definitions.PXMIN,
				Definitions.PYMAX - Definitions.PYMIN);
		
		System.out.print("Nacitam informace o scene... ");
		// TODO doplnit nacitani ze souboru
		System.out.println();
		
		System.out.print("Vytvarim kameru... ");
		scene.cam.create();
		System.out.println("OK");
		
		System.out.print("Trianguluji scenu... ");
		scene.triangulate();
		System.out.println("OK");
		System.out.println("\tScena obsahuje " + scene.triangles.size() +
				" trojuhelniku");
		
		System.out.print("Odstranuji odvracene trojuhelniky... ");
		scene.trivialReject();
		System.out.println("OK");
		System.out.println("\tScena obsahuje " + scene.triangles.size() +
				" trojuhelniku");
		
		System.out.print("Pocitam barvy ve vrcholech trojuhelniku... ");
		scene.computeLighting();
		System.out.println("OK");
		
		System.out.print("Provadim zobrazovaci transformace... ");
		scene.viewingTransform();
		System.out.println("OK");
		
		System.out.print("Orezavam zornym hranolem... ");
		scene.clipping();
		System.out.println("OK");
		System.out.println("\tScena obsahuje " + scene.triangles.size() +
				" trojuhelniku");
		
		System.out.print("Prevadim do afinnich souradnic... ");
		scene.normalizeW();
		System.out.println("OK");
		
		System.out.print("Mapuji na vystupni zarizeni... ");
		scene.mapToDC(Definitions.PXMIN, Definitions.PYMIN,
				Definitions.PXMAX, Definitions.PYMAX);
		System.out.println("OK");
		
		System.out.print("Rasterizuji... ");
		scene.rasterize(map);
		System.out.println("OK");
		
		System.out.print("Ukladam mapu do souboru... ");
		map.writeToBmp("vystup.bmp");
		System.out.println("OK");
	}
	
}
