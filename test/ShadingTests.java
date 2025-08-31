package test;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

import shader.*;
import geom.*;

/**
 * Test class for verifying shading functionality
 */
public class ShadingTests {
    
    private static final double EPSILON = 0.001;
    
    /**
     * Test Gouraud shading color interpolation
     */
    public static boolean testGouraudInterpolation() {
        System.out.println("Testing Gouraud shading color interpolation...");
        
        try {
            // Create a simple scene with a gradient plane
            Scene scene = createGradientPlaneScene();
            
            // Render the scene
            ColorRGBZ c = new ColorRGBZ(0, 0, 0, -1 * Definitions.REAL_MAX);
            PixelMap map = new PixelMap(c, 
                Definitions.PXMAX - Definitions.PXMIN,
                Definitions.PYMAX - Definitions.PYMIN);
            
            scene.cam.create();
            scene.triangulate();
            scene.trivialReject();
            scene.computeLighting();
            scene.viewingTransform();
            scene.clipping();
            scene.normalizeW();
            scene.mapToDC(Definitions.PXMIN, Definitions.PYMIN,
                Definitions.PXMAX, Definitions.PYMAX);
            scene.rasterize(map);
            
            // Save test output
            map.writeToBmp("test/gouraud_test.bmp");
            
            // Analyze the result
            boolean hasGradient = analyzeColorGradient(map);
            System.out.println(hasGradient ? "PASS: Color gradient detected" : "FAIL: No color gradient found");
            
            return hasGradient;
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Test Phong shading per-pixel lighting
     */
    public static boolean testPhongShading() {
        System.out.println("Testing Phong shading per-pixel lighting...");
        
        try {
            // Create a scene with a sphere for proper Phong testing
            Scene scene = createSphereScene();
            
            // Render the scene
            ColorRGBZ c = new ColorRGBZ(0, 0, 0, -1 * Definitions.REAL_MAX);
            PixelMap map = new PixelMap(c, 
                Definitions.PXMAX - Definitions.PXMIN,
                Definitions.PYMAX - Definitions.PYMIN);
            
            scene.cam.create();
            scene.triangulate();
            scene.trivialReject();
            scene.computeLighting();
            scene.viewingTransform();
            scene.clipping();
            scene.normalizeW();
            scene.mapToDC(Definitions.PXMIN, Definitions.PYMIN,
                Definitions.PXMAX, Definitions.PYMAX);
            scene.rasterize(map);
            
            // Save test output
            map.writeToBmp("test/phong_test.bmp");
            
            // Check for smooth shading characteristics
            boolean hasSmoothing = analyzePhongSmoothing(map);
            System.out.println(hasSmoothing ? "PASS: Phong smoothing detected" : "FAIL: No Phong smoothing found");
            
            return hasSmoothing;
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Compare different shading methods
     */
    public static boolean testShadingComparison() {
        System.out.println("Testing shading method differences...");
        
        try {
            // Create identical scenes with different shading
            Scene wireScene = createComparisonScene(ShadingType.WIRE);
            Scene constScene = createComparisonScene(ShadingType.CONST);
            Scene gouraudScene = createComparisonScene(ShadingType.GOUARD);
            Scene phongScene = createComparisonScene(ShadingType.PHONG);
            
            // Render all scenes
            PixelMap[] maps = new PixelMap[4];
            Scene[] scenes = {wireScene, constScene, gouraudScene, phongScene};
            String[] names = {"wire", "const", "gouraud", "phong"};
            
            for (int i = 0; i < 4; i++) {
                ColorRGBZ c = new ColorRGBZ(0, 0, 0, -1 * Definitions.REAL_MAX);
                maps[i] = new PixelMap(c, 
                    Definitions.PXMAX - Definitions.PXMIN,
                    Definitions.PYMAX - Definitions.PYMIN);
                
                scenes[i].cam.create();
                scenes[i].triangulate();
                scenes[i].trivialReject();
                scenes[i].computeLighting();
                scenes[i].viewingTransform();
                scenes[i].clipping();
                scenes[i].normalizeW();
                scenes[i].mapToDC(Definitions.PXMIN, Definitions.PYMIN,
                    Definitions.PXMAX, Definitions.PYMAX);
                scenes[i].rasterize(maps[i]);
                
                maps[i].writeToBmp("test/" + names[i] + "_comparison.bmp");
            }
            
            // Verify they are different
            boolean allDifferent = verifyDifferentResults(maps);
            System.out.println(allDifferent ? "PASS: All shading methods produce different results" : 
                             "FAIL: Some shading methods produce identical results");
            
            return allDifferent;
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Create a simple plane scene for gradient testing
     */
    private static Scene createGradientPlaneScene() throws Exception {
        Scene scene = new Scene();
        
        // Use working camera setup from input.xml
        scene.cam.setVRP(new Vector3D(20, -5, 15));
        scene.cam.setVPN(new Vector3D(20, -5, 15));
        scene.cam.setVUP(new Vector3D(0, 0, 1));
        scene.cam.setPRP(new Vector3D(0, 0, 1));
        scene.cam.setUmin(-1);
        scene.cam.setVmin(-1);
        scene.cam.setUmax(1);
        scene.cam.setVmax(1);
        scene.cam.setF(0.5);
        scene.cam.setB(-60);
        
        // Add ambient light
        light.ALight alight = new light.ALight(new ColorRGB(0.3, 0.3, 0.3));
        scene.lights.add(alight);
        
        // Add point light
        light.PLight plight = new light.PLight();
        plight.setP(new Vector3D(10, -10, 6));
        plight.setC(new ColorRGB(1.0, 1.0, 1.0));
        plight.setA(new Vector3D(1, 0.1, 0.01));
        scene.lights.add(plight);
        
        // Create a large plane (block) that should show gradient
        objects.Block block = new objects.Block();
        block.l = 15;
        block.w = 15; 
        block.h = 1;
        block.dl = 1;  // Low division to see if it creates the "one-colored" issue
        block.dw = 1;
        block.dh = 1;
        block.diff.set(new ColorRGB(0.7, 0.5, 0.3));
        block.spec.set(new ColorRGB(0.8, 0.8, 0.8));
        block.shadingType = ShadingType.GOUARD;
        
        scene.objects.add(block);
        
        return scene;
    }
    
    /**
     * Create a sphere scene for Phong testing
     */
    private static Scene createSphereScene() throws Exception {
        Scene scene = new Scene();
        
        // Use working camera setup from input.xml
        scene.cam.setVRP(new Vector3D(20, -5, 15));
        scene.cam.setVPN(new Vector3D(20, -5, 15));
        scene.cam.setVUP(new Vector3D(0, 0, 1));
        scene.cam.setPRP(new Vector3D(0, 0, 1));
        scene.cam.setUmin(-1);
        scene.cam.setVmin(-1);
        scene.cam.setUmax(1);
        scene.cam.setVmax(1);
        scene.cam.setF(0.5);
        scene.cam.setB(-60);
        
        // Add lights similar to input.xml
        light.ALight alight = new light.ALight(new ColorRGB(0.4, 0.4, 0.4));
        scene.lights.add(alight);
        
        light.PLight plight = new light.PLight();
        plight.setP(new Vector3D(10, 10, 6));
        plight.setC(new ColorRGB(1.0, 1.0, 1.0));
        plight.setA(new Vector3D(1, 0.3, 0.45));
        scene.lights.add(plight);
        
        // Create sphere at origin to be visible with this camera
        objects.Sphere sphere = new objects.Sphere();
        sphere.radius = 5;
        sphere.dh = 30;
        sphere.dv = 30;
        sphere.diff.set(new ColorRGB(0.6, 0.3, 0.8));
        sphere.spec.set(new ColorRGB(0.9, 0.9, 0.9));
        sphere.shadingType = ShadingType.PHONG;
        
        scene.objects.add(sphere);
        
        return scene;
    }
    
    /**
     * Create scene for shading comparison
     */
    private static Scene createComparisonScene(ShadingType shading) throws Exception {
        Scene scene = new Scene();
        
        // Use working camera setup from input.xml
        scene.cam.setVRP(new Vector3D(20, -5, 15));
        scene.cam.setVPN(new Vector3D(20, -5, 15));
        scene.cam.setVUP(new Vector3D(0, 0, 1));
        scene.cam.setPRP(new Vector3D(0, 0, 1));
        scene.cam.setUmin(-1);
        scene.cam.setVmin(-1);
        scene.cam.setUmax(1);
        scene.cam.setVmax(1);
        scene.cam.setF(0.5);
        scene.cam.setB(-60);
        
        // Add lights similar to input.xml
        light.ALight alight = new light.ALight(new ColorRGB(0.4, 0.4, 0.4));
        scene.lights.add(alight);
        
        light.PLight plight = new light.PLight();
        plight.setP(new Vector3D(10, 10, 6));
        plight.setC(new ColorRGB(1.0, 1.0, 1.0));
        plight.setA(new Vector3D(1, 0.3, 0.45));
        scene.lights.add(plight);
        
        // Create sphere with specified shading
        objects.Sphere sphere = new objects.Sphere();
        sphere.radius = 5;
        sphere.dh = 15;
        sphere.dv = 15;
        sphere.diff.set(new ColorRGB(0.8, 0.4, 0.2));
        sphere.spec.set(new ColorRGB(0.9, 0.9, 0.9));
        sphere.shadingType = shading;
        
        scene.objects.add(sphere);
        
        return scene;
    }
    
    /**
     * Analyze if the pixel map contains a color gradient
     */
    private static boolean analyzeColorGradient(PixelMap map) {
        int width = map.getCountColumns();
        int height = map.getCountRows();
        
        // Sample colors along a diagonal line
        double[] intensities = new double[Math.min(width, height) / 2];
        
        for (int i = 0; i < intensities.length; i++) {
            int x = i * 2;
            int y = i * 2;
            ColorRGBZ pixel = map.getPixel(x, y);
            // Calculate brightness as approximation
            intensities[i] = 0.299 * pixel.getR() + 0.587 * pixel.getG() + 0.114 * pixel.getB();
        }
        
        // Check for monotonic change (gradient)
        boolean hasVariation = false;
        double minIntensity = intensities[0];
        double maxIntensity = intensities[0];
        
        for (int i = 1; i < intensities.length; i++) {
            minIntensity = Math.min(minIntensity, intensities[i]);
            maxIntensity = Math.max(maxIntensity, intensities[i]);
        }
        
        // Consider it a gradient if there's significant variation
        hasVariation = (maxIntensity - minIntensity) > 0.1;
        
        return hasVariation;
    }
    
    /**
     * Analyze if Phong shading produces smooth transitions
     */
    private static boolean analyzePhongSmoothing(PixelMap map) {
        int width = map.getCountColumns();
        int height = map.getCountRows();
        
        // Check center region for smoothness
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 6;
        
        double totalVariation = 0;
        int samples = 0;
        
        for (int y = centerY - radius; y <= centerY + radius; y += 2) {
            for (int x = centerX - radius; x <= centerX + radius; x += 2) {
                if (x >= 1 && x < width - 1 && y >= 1 && y < height - 1) {
                    ColorRGBZ center = map.getPixel(x, y);
                    ColorRGBZ right = map.getPixel(x + 1, y);
                    ColorRGBZ down = map.getPixel(x, y + 1);
                    
                    double centerBright = getBrightness(center);
                    double rightBright = getBrightness(right);
                    double downBright = getBrightness(down);
                    
                    totalVariation += Math.abs(centerBright - rightBright);
                    totalVariation += Math.abs(centerBright - downBright);
                    samples += 2;
                }
            }
        }
        
        double avgVariation = samples > 0 ? totalVariation / samples : 0;
        
        // Phong should have smooth gradients (low variation between adjacent pixels)
        // but still show some lighting variation overall
        return avgVariation > 0.001 && avgVariation < 0.2;
    }
    
    private static double getBrightness(ColorRGBZ color) {
        return 0.299 * color.getR() + 0.587 * color.getG() + 0.114 * color.getB();
    }
    
    /**
     * Verify that different shading methods produce different results
     */
    private static boolean verifyDifferentResults(PixelMap[] maps) {
        int width = maps[0].getCountColumns();
        int height = maps[0].getCountRows();
        
        // Compare each pair of maps
        for (int i = 0; i < maps.length; i++) {
            for (int j = i + 1; j < maps.length; j++) {
                boolean different = false;
                
                // Sample some pixels to see if they differ
                for (int y = height / 4; y < 3 * height / 4; y += 10) {
                    for (int x = width / 4; x < 3 * width / 4; x += 10) {
                        ColorRGBZ pixel1 = maps[i].getPixel(x, y);
                        ColorRGBZ pixel2 = maps[j].getPixel(x, y);
                        
                        double diff = Math.abs(getBrightness(pixel1) - getBrightness(pixel2));
                        if (diff > 0.01) {
                            different = true;
                            break;
                        }
                    }
                    if (different) break;
                }
                
                if (!different) {
                    System.out.println("Maps " + i + " and " + j + " are too similar");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        System.out.println("=== Shader Testing Suite ===");
        
        // Create test output directory
        File testDir = new File("test");
        if (!testDir.exists()) {
            testDir.mkdir();
        }
        
        boolean allPassed = true;
        
        // Run tests
        allPassed &= testGouraudInterpolation();
        allPassed &= testPhongShading();
        allPassed &= testShadingComparison();
        
        System.out.println("\n=== Test Results ===");
        System.out.println(allPassed ? "ALL TESTS PASSED" : "SOME TESTS FAILED");
        System.out.println("Test output images saved in test/ directory");
        
        System.exit(allPassed ? 0 : 1);
    }
}