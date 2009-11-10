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
	 * Pocet radku mapy
	 */
	private int m;
	
	/**
	 * Pocet sloupcu mapy
	 */
	private int n;
	
	/**
	 * Maximalni hodnota barvy
	 */
	private double maxColor;
	
	/**
	 * Dvourozmerne pole pixelu
	 */
	private ColorRGBZ map[][];
	
	
	/**
	 * Konstruktor
	 * @param initColor Pocatecni barva vsech pixelu
	 * @param maxColor Maximalni hodnota barvy
	 * @param m Pocet radku mapy
	 * @param n Pocet sloupcu mapy
	 */
	public PixelMap(ColorRGBZ initColor, double maxColor, int m, int n) {
		this.m = m;
		this.n = n;
		this.maxColor = maxColor;
		
		map = new ColorRGBZ[m][n];
		init(initColor);
	}
	
	/**
	 * Konstruktor
	 * @param initColor Pocatecni barva vsech pixelu
	 */
	public PixelMap(ColorRGBZ initColor) {
		this(initColor, 0, 1, 1);
	}
	
	
	/**
	 * Nastavi hodnotu barvy vsech pixelu
	 * @param c Nova barva
	 */
	public void init(ColorRGBZ c) {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				map[i][j].set(c);
	}
	
	
	/**
	 * Nastavi barvu pixelu na dane pozici
	 * @param i Radek mapy
	 * @param j Sloupec mapy
	 * @param c Nova barva
	 */
	public void setPixel(int i, int j, ColorRGBZ c) {
		if (i >= 0 && i < m && j >= 0 && j < n)
			map[i][j].set(c);
	}
	
	/**
	 * Vrati barvu pixelu na dane pozici
	 * @param i Radek mapy
	 * @param j Sloupec mapy
	 * @return Barva
	 */
	public ColorRGBZ getPixel(int i, int j) {
		if (i >= 0 && i < m && j >= 0 && j < n)
			return new ColorRGBZ(map[i][j]);
		return new ColorRGBZ();
	}
	
	
	/**
	 * Nastavi maximalni barvu
	 * @param maxColor Nova hodnota
	 */
	public void setMaxColor(double maxColor) {
		this.maxColor = maxColor;
	}
	
	/**
	 * Vrati maximalni barvu
	 * @return Maximalni barva
	 */
	public double getMaxColor() {
		return maxColor;
	}
	
	
	/**
	 * Vrati pocet radku mapy
	 * @return Pocet radku mapy
	 */
	public int getCountRows() {
		return m;
	}
	
	/**
	 * Vrati pocet sloupcu mapy
	 * @return Pocet sloupcu mapy
	 */
	public int getCountColumns() {
		return n;
	}
	
	
	/**
	 * Ulozi obrazek mapy do souboru
	 * @param name Nazev souboru
	 */
	public void writeToBmp(String name) {
		BufferedImage image = new BufferedImage(n, m,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				Color color = new Color((float)map[j][i].getR(),
						(float)map[j][i].getG(), (float)map[j][i].getZ());
				image.setRGB(i, j, color.getRGB());
			}
		try {
			ImageIO.write((RenderedImage)image, "BMP", new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
