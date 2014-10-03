package es.ujaen.metaheuristicas.pr3.clustering;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pair {

    public int pattern;
    public int cluster;

    Pair(Pair orig) {
        this.pattern = orig.pattern;
        this.cluster = orig.cluster;
    }

    Pair() {
    }
}
