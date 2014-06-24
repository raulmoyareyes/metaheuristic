
package es.ujaen.metaheuristicas.pr2a.clustering;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pattern {

    float dimensions[];
    int size;

    Pattern(int size) {
        this.size = size;
        this.dimensions = new float[this.size];
    }
}
