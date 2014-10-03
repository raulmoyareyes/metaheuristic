
package es.ujaen.metaheuristicas.pr3.clustering;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pattern {

    public float dimensions[];
    public int size;

    Pattern(int size) {
        this.size = size;
        this.dimensions = new float[this.size];
    }
}
