# JAVA-Raytracer

## Overview
A Java-based ray tracing engine supporting basic shapes (spheres, planes, triangles), lighting (directional and point lights), materials (diffuse and specular), and shadow computation.

## Building the Project

### Prerequisites
- JDK 17 or later
- Maven 3.8.9 or later

### Compile
```bash
mvn clean compile
```

## Running Tests

### Unit and Integration Tests
The project includes a comprehensive test suite covering:
- **Math operations**: Vector, Point, Color (add, subtract, normalize, dot, cross, etc.)
- **Shapes**: Sphere, Plane, Triangle intersection tests
- **Lighting**: Lambert diffuse and Blinn-Phong specular shading
- **Parsing**: Scene file parser validation (required keywords, error handling)
- **Integration**: Full render pipeline with camera and ambient lighting

Run all tests:
```bash
mvn test
```

Run a specific test class:
```bash
mvn test -Dtest=SphereTest
mvn test -Dtest=PlaneTest
mvn test -Dtest=RendererIntegrationTest
```

View test coverage report:
```bash
mvn clean test jacoco:report
```
Coverage report will be available at `target/site/jacoco/index.html`

## Test Summary

**Total Tests**: 20 (all passing)

| Component | Tests | Notes |
|-----------|-------|-------|
| Color | 3 | Clamping, add, scale, Schur product, toRGB |
| Point | 2 | Subtraction, vector operations |
| Vector | 3 | Add/sub/scale, dot/cross, normalize |
| Sphere | 2 | Center hit, miss detection |
| Plane | 2 | Face hit, parallel miss |
| Triangle | 2 | Center hit, outside miss |
| Intersection | 2 | Lambert diffuse, Blinn-Phong specular |
| SceneFileParser | 3 | Minimal parse, missing required keyword, invalid radius |
| Renderer Integration | 1 | Image dimensions and pixel initialization |

## Project Structure

```
src/
  main/java/raytracer/
    - RayTracer.java         (core ray tracing engine)
    - Renderer.java          (renders scene to image)
    - Scene.java             (scene management, shading)
    - Ray.java               (ray definition)
    - Intersection.java      (intersection data and shading)
    - Camera.java            (camera definition)
    - Orthonormal.java       (coordinate frame)
    math/
      - Point.java, Vector.java, Color.java, AbstractVec3.java
    shape/
      - Shape.java, Sphere.java, Plane.java, Triangle.java
    light/
      - AbstractLight.java, DirectionalLight.java, PointLight.java
    parsing/
      - SceneFileParser.java, SceneParseException.java
  test/java/
    - ColorTest.java, PointTest.java, VectorTest.java
    - SphereTest.java, PlaneTest.java, TriangleTest.java
    - IntersectionTest.java, SceneFileParserTest.java
    - RendererIntegrationTest.java
```

## Example Scene File

```
size 800 600
camera 0 1 3 0 0 -1 0 1 0 60
ambient 0.2 0.2 0.2

diffuse 0.8 0.2 0.2
specular 0.5 0.5 0.5
shininess 32
sphere 0 0 -5 1.0

directional -1 -1 -1 0.7 0.7 0.7
output result.png
```

## Notes

- The parser enforces validation: positive dimensions/radii, color sum constraints for lights
- All vector/point operations are immutable
- Ray tracing uses epsilon-based epsilon (1e-6) to avoid self-intersection
- Shadow rays are offset along surface normal to prevent self-shadowing artifacts