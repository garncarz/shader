# 3D Shader - Java-based Software Renderer

A 3D rendering engine written in Java that creates BMP images from XML scene descriptions. This project implements a complete software rendering pipeline with multiple shading techniques including wireframe, constant, Gouraud, and Phong shading.

![Example render](examples/best_practices.bmp)

## Features

- **Multiple 3D Primitives**: Spheres, cylinders, blocks (boxes), and pyramids
- **Advanced Lighting**: Ambient lighting and point lights with attenuation
- **Four Shading Models**:
  - **WIRE**: Wireframe outline rendering
  - **CONST**: Constant (flat) shading - uniform color per triangle
  - **GOUARD**: Gouraud shading - smooth color interpolation from vertex colors
  - **PHONG**: Phong shading - per-pixel lighting calculations for maximum realism
- **Camera Controls**: Full 3D camera positioning and viewing frustum setup
- **Material Properties**: Configurable diffuse and specular material properties
- **High-Quality Output**: Generates high-resolution BMP images (700x700 by default)

## Project Structure

```
shader/
├── shader/          # Core rendering pipeline
│   ├── Scene.java   # Main scene management and rendering
│   ├── Camera.java  # 3D camera and viewing transformations
│   ├── PixelMap.java # Framebuffer and image output
│   └── ShadingType.java # Shading method definitions
├── objects/         # 3D geometric objects
│   ├── Sphere.java
│   ├── Cylinder.java
│   ├── Block.java
│   └── Pyramid.java
├── light/           # Lighting system
│   ├── ALight.java  # Ambient lighting
│   └── PLight.java  # Point lighting
├── geom/            # Geometry utilities
│   ├── Vector3D.java
│   ├── Matrix44.java
│   └── ColorRGB.java
├── examples/        # Example scene files
└── test/           # Automated testing suite
```

## Rendering Pipeline

The shader implements a standard 3D rendering pipeline:

1. **Scene Loading**: Parse XML scene description
2. **Triangulation**: Convert 3D objects into triangular meshes
3. **Back-face Culling**: Remove triangles facing away from camera
4. **Lighting Calculation**: Compute colors at triangle vertices
5. **Viewing Transform**: Transform to camera coordinate system
6. **Clipping**: Remove geometry outside the viewing frustum  
7. **Perspective Projection**: Project 3D coordinates to 2D screen space
8. **Device Coordinate Mapping**: Map to final image resolution
9. **Rasterization**: Fill triangles with appropriate shading algorithm

## Usage

### Basic Usage

```bash
# Compile the project
ant compile

# Run with default scene
ant run

# Run with custom scene
java -jar shader.jar input_scene.xml output_image.bmp
```

### Scene File Format

Scene files use XML format to define the 3D scene:

```xml
<?xml version="1.0"?>
<scene>
    <!-- Camera setup -->
    <camera
        vrpX="20" vrpY="-5" vrpZ="15"
        vpnX="20" vpnY="-5" vpnZ="15"
        vupX="0" vupY="0" vupZ="1"
        prpX="0" prpY="0" prpZ="1"
        Umin="-1" Vmin="-1" Umax="1" Vmax="1"
        front="0.5" back="-60"
    />
    
    <!-- Ambient lighting -->
    <alight r="1" g="1" b="1" s="0.4" />
    
    <!-- Point light -->
    <plight
        x="10" y="10" z="6"
        r="1" g="1" b="1" s="15"
        attX="1" attY="0.3" attZ="0.45"
    />
    
    <!-- 3D Objects -->
    <sphere
        x="0" y="0" z="0" radius="5"
        dh="30" dv="30"
        diffR="1" diffG="0.5" diffB="0.2" diffS="0.8"
        specR="1" specG="1" specB="1" specS="1"
        shading="PHONG"
    />
</scene>
```

## Shading Techniques Comparison

The renderer supports four different shading techniques, each with distinct visual characteristics:

### Wireframe (WIRE)
- Renders only the triangle edges
- Fastest rendering method
- Useful for debugging geometry

### Constant Shading (CONST)  
- Each triangle has uniform color
- Faceted appearance
- Simple lighting calculation per triangle

### Gouraud Shading (GOUARD)
- Smooth color interpolation between triangle vertices
- Lighting calculated at vertices, colors interpolated across surface
- Good balance of quality and performance

### Phong Shading (PHONG)
- Per-pixel lighting calculations
- Highest quality and most realistic results
- Smooth specular highlights and gradients

## Testing

The project includes automated tests to verify shading quality:

```bash
# Run the test suite
ant test

# Individual debugging
java -cp classes test.ShadingTests
java -cp classes test.ShadingDebugger
```

The tests verify:
- Color gradient generation in Gouraud shading
- Smooth lighting transitions in Phong shading  
- Visual differences between shading methods
- Proper lighting calculations

## Examples

### Basic Scene
The default `input.xml` demonstrates all features with multiple objects and shading types.

### Shading Issue Demonstration
The `examples/shading_issue_demo.xml` shows the difference between low and high tessellation:
- Low tessellation can create "flat" or "one-colored" appearance
- Higher tessellation provides smoother gradients

### Known Issues and Solutions

**Issue**: Some planes appear almost one-colored when they should show gradients.

**Cause**: This typically occurs with low tessellation (subdivision) settings. Gouraud shading interpolates colors between triangle vertices, so large triangles with few vertices cannot show smooth gradients.

**Solution**: Increase tessellation parameters (dl, dw, dh) for geometric objects to create more triangles and smoother color transitions.

## Building and Development

### Requirements
- Java 8 or higher
- Apache Ant for building

### Build Commands
```bash
ant compile    # Compile source code
ant dist       # Create JAR file  
ant doc        # Generate documentation
ant test       # Run test suite
ant clean      # Clean build artifacts
```

## Technical Details

- **Language**: Java
- **Build System**: Apache Ant
- **Output Format**: BMP images
- **Default Resolution**: 700x700 pixels
- **Coordinate System**: Right-handed 3D coordinates
- **Lighting Model**: Phong reflection model with ambient and diffuse/specular components

## Contributing

The codebase is well-structured for extension:
- Add new 3D primitives by extending `GeomObject`
- Implement new lighting models by implementing `Light` interface
- Extend shading techniques by modifying `Scene.rasterize()`

## License

This is an educational project originally developed for a computer graphics course at VŠB-TUO, converted from C++ to Java implementation.

<!-- ❄️ Hello to the GitHub Archive! ❄️ -->
