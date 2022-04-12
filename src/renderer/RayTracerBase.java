package renderer;

import primitives.*;
import scene.*;

/**
 *  represents a ray tracer that:<ui>
 *      <li>traces rays through a scene</li>
 *      <li>finding a color of an object that intersects closest to the ray</li>
 *  </ui>
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor accepting the scene to trace the rays trough
     * @param scene the scene to trace
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * tracing a ray through a scene and finding the color of the object closest to the head of the ray
     * @param ray the ray to trace the scene with
     * @return the color of the object the ray 'sees' first
     */
    public abstract Color traceRay(Ray ray);
}
