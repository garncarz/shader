package test;

import shader.*;
import geom.*;

/**
 * Debug utility to check color values during shading
 */
public class ShadingDebugger {
    
    public static void debugColorValues() {
        System.out.println("=== Debugging color values ===");
        
        try {
            // Create a simple scene
            Scene scene = new Scene();
            
            // Set up camera - matching input.xml exactly
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
            
            // Add lights
            light.ALight alight = new light.ALight(new ColorRGB(0.5, 0.5, 0.5));
            scene.lights.add(alight);
            
            light.PLight plight = new light.PLight();
            plight.setP(new Vector3D(-2, -3, 2));
            plight.setC(new ColorRGB(1.0, 1.0, 1.0));
            plight.setA(new Vector3D(1, 0.1, 0.01));
            scene.lights.add(plight);
            
            // Create a sphere
            objects.Sphere sphere = new objects.Sphere();
            sphere.radius = 1.5;
            sphere.dh = 8;
            sphere.dv = 8;
            sphere.diff.set(new ColorRGB(0.8, 0.4, 0.2));
            sphere.spec.set(new ColorRGB(0.9, 0.9, 0.9));
            sphere.shadingType = ShadingType.GOUARD;
            
            scene.objects.add(sphere);
            
            scene.cam.create();
            scene.triangulate();
            
            System.out.println("Number of triangles: " + scene.triangles.size());
            
            // Check colors before lighting
            objects.Triangle t = scene.triangles.get(0);
            System.out.println("Before lighting:");
            System.out.printf("  c1: (%.3f, %.3f, %.3f)%n", t.c1.getR(), t.c1.getG(), t.c1.getB());
            System.out.printf("  c2: (%.3f, %.3f, %.3f)%n", t.c2.getR(), t.c2.getG(), t.c2.getB());
            System.out.printf("  c3: (%.3f, %.3f, %.3f)%n", t.c3.getR(), t.c3.getG(), t.c3.getB());
            System.out.printf("  diff: (%.3f, %.3f, %.3f)%n", t.diff.getR(), t.diff.getG(), t.diff.getB());
            
            scene.trivialReject();
            scene.computeLighting();
            
            System.out.println("Number of triangles after reject: " + scene.triangles.size());
            
            // Check colors after lighting
            if (scene.triangles.size() > 0) {
                t = scene.triangles.get(0);
                System.out.println("After lighting:");
                System.out.printf("  c1: (%.3f, %.3f, %.3f)%n", t.c1.getR(), t.c1.getG(), t.c1.getB());
                System.out.printf("  c2: (%.3f, %.3f, %.3f)%n", t.c2.getR(), t.c2.getG(), t.c2.getB());
                System.out.printf("  c3: (%.3f, %.3f, %.3f)%n", t.c3.getR(), t.c3.getG(), t.c3.getB());
                
                // Check a few more triangles to see variation
                if (scene.triangles.size() > 5) {
                    objects.Triangle t2 = scene.triangles.get(5);
                    System.out.println("Triangle 5 after lighting:");
                    System.out.printf("  c1: (%.3f, %.3f, %.3f)%n", t2.c1.getR(), t2.c1.getG(), t2.c1.getB());
                    System.out.printf("  c2: (%.3f, %.3f, %.3f)%n", t2.c2.getR(), t2.c2.getG(), t2.c2.getB());
                    System.out.printf("  c3: (%.3f, %.3f, %.3f)%n", t2.c3.getR(), t2.c3.getG(), t2.c3.getB());
                }
                
                // Test pixel map values
                ColorRGBZ c = new ColorRGBZ(0, 0, 0, -1 * Definitions.REAL_MAX);
                PixelMap map = new PixelMap(c, 200, 200);
                
                scene.viewingTransform();
                System.out.println("Number of triangles after viewing transform: " + scene.triangles.size());
                scene.clipping();
                System.out.println("Number of triangles after clipping: " + scene.triangles.size());
                scene.normalizeW();
                System.out.println("Number of triangles after normalizeW: " + scene.triangles.size());
                
                // Check coordinates after transformation
                if (scene.triangles.size() > 0) {
                    objects.Triangle t_coord = scene.triangles.get(0);
                    System.out.println("After transformation:");
                    System.out.printf("  p1: (%.3f, %.3f, %.3f)%n", t_coord.p1.getX(), t_coord.p1.getY(), t_coord.p1.getZ());
                    System.out.printf("  p2: (%.3f, %.3f, %.3f)%n", t_coord.p2.getX(), t_coord.p2.getY(), t_coord.p2.getZ());
                    System.out.printf("  p3: (%.3f, %.3f, %.3f)%n", t_coord.p3.getX(), t_coord.p3.getY(), t_coord.p3.getZ());
                }
                
                scene.mapToDC(0, 0, 200, 200);
                
                // Check coordinates after DC mapping  
                if (scene.triangles.size() > 0) {
                    objects.Triangle t_dc = scene.triangles.get(0);
                    System.out.println("After DC mapping:");
                    System.out.printf("  p1: (%.3f, %.3f, %.3f)%n", t_dc.p1.getX(), t_dc.p1.getY(), t_dc.p1.getZ());
                    System.out.printf("  p2: (%.3f, %.3f, %.3f)%n", t_dc.p2.getX(), t_dc.p2.getY(), t_dc.p2.getZ());
                    System.out.printf("  p3: (%.3f, %.3f, %.3f)%n", t_dc.p3.getX(), t_dc.p3.getY(), t_dc.p3.getZ());
                }
                scene.rasterize(map);
                
                // Sample some pixels
                System.out.println("Sample pixel values:");
                for (int y = 50; y < 150; y += 25) {
                    for (int x = 50; x < 150; x += 25) {
                        ColorRGBZ pixel = map.getPixel(x, y);
                        System.out.printf("  (%d,%d): (%.3f, %.3f, %.3f)%n", 
                            x, y, pixel.getR(), pixel.getG(), pixel.getB());
                    }
                }
                
                map.writeToBmp("test/debug_output.bmp");
                System.out.println("Debug image saved as test/debug_output.bmp");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        debugColorValues();
    }
}