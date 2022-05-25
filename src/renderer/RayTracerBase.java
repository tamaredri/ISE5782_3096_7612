package renderer;

import primitives.*;
import scene.*;

/**
 *  represents a ray tracer that:<ul>
 *      <li>traces rays through a scene</li>
 *      <li>finding a color of an object that intersects closest to the ray</li>
 *  </ul>
 */
public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * tracing a ray through a scene and finding the color of the object closest to the head of the ray
     * @return the color of the object the ray 'sees' first
     */
    public abstract Color traceRay(Ray ray);
}
