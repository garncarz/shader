package shader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import geom.*;

// TODO zrejme promazat nepouzivane veci (maxColor, set, get,
// getCountRows, getCountColumns)

/**
 * 2D plocha pixelu
 */
public class PixelMap {

	/**
	 * Sirka mapy (pocet sloupcu)
	 */
	private int width;
	
	/**
	 * Vyska mapy (pocet radku)
	 */
	private int height;
	
	/**
	 * Dvourozmerne pole pixelu;
	 * index: nejdriv sloupce, pak radky
	 */
	private ColorRGBZ map[][];
	
	
	/**
	 * Konstruktor
	 * @param initColor Pocatecni barva vsech pixelu
	 * @param width Pocet sloupcu mapy
	 * @param height Pocet radku mapy
	 */
	public PixelMap(ColorRGBZ initColor, int width, int height) {
		this.width = width;
		this.height = height;
		
		map = new ColorRGBZ[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				map[x][y] = new ColorRGBZ();
		init(initColor);
	}
	
	/**
	 * Konstruktor
	 * @param initColor Pocatecni barva vsech pixelu
	 */
	public PixelMap(ColorRGBZ initColor) {
		this(initColor, 500, 500);
	}
	
	
	/**
	 * Nastavi hodnotu barvy vsech pixelu
	 * @param c Nova barva
	 */
	public void init(ColorRGBZ c) {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				map[x][y].set(c);
	}
	
	
	/**
	 * Nastavi barvu pixelu na dane pozici,
	 * pokud nova barva patri blizsimu bodu
	 * @param x Sloupec mapy
	 * @param y Radek mapy
	 * @param c Nova barva
	 */
	public void setPixel(int x, int y, ColorRGBZ c) {
		if (x >= 0 && x < width && y >= 0 && y < height
				&& c.getZ() > map[x][y].getZ())
			map[x][y].set(c);
	}
	
	/**
	 * Vrati barvu pixelu na dane pozici
	 * @param x Sloupec mapy
	 * @param y Radek mapy
	 * @return Barva
	 */
	public ColorRGBZ getPixel(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			return new ColorRGBZ(map[x][y]);
		return new ColorRGBZ();
	}
	
	
	/**
	 * Vrati pocet radku mapy
	 * @return Pocet radku mapy
	 */
	public int getCountRows() {
		return height;
	}
	
	/**
	 * Vrati pocet sloupcu mapy
	 * @return Pocet sloupcu mapy
	 */
	public int getCountColumns() {
		return width;
	}
	
	
	/**
	 * Ulozi obrazek mapy do souboru
	 * @param name Nazev souboru
	 * @throws Exception Chyba ukladani obrazku
	 */
	public void writeToBmp(String name) throws Exception {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				ColorRGB c = new ColorRGB(map[x][y]);
				float r = (float)Math.min(Math.max(c.getR(), 0), 1),
						g = (float)Math.min(Math.max(c.getG(), 0), 1),
						b = (float)Math.min(Math.max(c.getB(), 0), 1);
				Color color = new Color(r, g, b);
				image.setRGB(x, y, color.getRGB());
			}
		try {
			ImageIO.write((RenderedImage)image, "BMP", new File(name));
		} catch (IOException e) {
			throw new Exception("Chyba pri ukladani obrazku!");
		}
	}
	
}
