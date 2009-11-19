package shader;

import objects.Triangle;
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
		String inputFilename = "input.xml";
		if (args.length > 0)
			inputFilename = args[0];
		String outputFilename = "output.bmp";
		if (args.length > 1)
			outputFilename = args[1];
		
		Scene scene = new Scene();
		ColorRGBZ c = new ColorRGBZ(0, 0, 0, -1 * Definitions.REAL_MAX);
		PixelMap map = new PixelMap(c,
				Definitions.PXMAX - Definitions.PXMIN,
				Definitions.PYMAX - Definitions.PYMIN);
		
		System.out.print("Nacitam informace o scene... ");
		try {
			Loader.load(inputFilename, scene);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("OK");
		System.out.println("\tPocet nactenych teles: " + scene.objects.size());
		System.out.println("\tPocet nactenych svetel: " + scene.lights.size());
		
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
		
		/*
		System.out.print("Orezavam zornym hranolem... ");
		scene.clipping();
		System.out.println("OK");
		System.out.println("\tScena obsahuje " + scene.triangles.size() +
				" trojuhelniku");
		*/
		
		System.out.print("Prevadim do afinnich souradnic... ");
		scene.normalizeW();
		System.out.println("OK");
		
		System.out.print("Mapuji na vystupni zarizeni... ");
		scene.mapToDC(Definitions.PXMIN, Definitions.PYMIN,
				Definitions.PXMAX, Definitions.PYMAX);
		System.out.println("OK");
		
		// TODO smazat
		/*
		{
			scene.triangles.clear();
			Triangle t = new Triangle(
					new Vector3D(100, 200, -0.5),
					new Vector3D(200, 200, -0.5),
					new Vector3D(200, 100, -0.5),
					new Vector3D(0, 0, -1),
					new Vector3D(0, 0, -1),
					new Vector3D(0, 0, -1),
					new ColorRGB(0, 0, 1),
					new ColorRGB(1, 0, 0),
					new ColorRGB(0, 1, 0),
					new Vector3D(0, 0, -1));
			scene.triangles.add(t);
		}
		*/
		
		System.out.print("Rasterizuji... ");
		scene.rasterize(map);
		System.out.println("OK");
		
		System.out.print("Ukladam mapu do souboru... ");
		try {
			map.writeToBmp(outputFilename);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("OK");
		
	}  // main
	
}
