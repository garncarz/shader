/**
 * Balik trid starajicich se predevsim o uchovani sceny a jeji zpracovani
 */
package shader;

/**
 * @mainpage
 * 
 * <center>
 * <p>Toto je "Shader", standardní zobrazovací řetězec.</p>
 * 
 * Vznikl jako projekt do předmětu
 * <a href="http://mrl.cs.vsb.cz/people/sojka/pg1_course.html">
 * Počítačová grafika</a>
 * na <a href="http://vsb.cz">VŠB-TUO</a>
 * podle již
 * <a href="http://www.cs.vsb.cz/gajdos/soubory/pg/shader2%20-%20VS2008.zip">
 * předpřipravené C++ šablony</a>
 * od Jana Plačka z roku 2003 pro MS VS (??).
 * 
 * Fungováním vychází ze skript
 * <a href="http://mrl.cs.vsb.cz/people/sojka/pocitacova_grafikaII.pdf">
 * Počítačová grafika II</a>
 * od <a href="http://www.cs.vsb.cz/sojka">doc. Dr. Ing. Edy Sojky</a>.
 * 
 * Pod Eclipsem v jazyku Java sestavil
 * <a href="mailto:gar.o@post.cz">Ondřej Garncarz</a> na podzim 2009.
 * 
 * Zbytek dokumentace i komentáře jsou raději již bez diakritiky, pardon.
 * 
 * @image html http://i32.photobucket.com/albums/d31/AwesomeWelles/columbo2.jpg
 * </center>
 */

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
		try {
			map.writeToBmp(outputFilename);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("OK");
		
	}  // main
	
}
