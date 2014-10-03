
package es.ujaen.metaheuristicas.pr2a.clustering;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Chromosome {

    public Pair[] pairs;
    public Cluster[] clusters;
    public float objective;
    public int patternsSize;
    public int clustersSize;

    Chromosome() {
        this.objective = Float.MAX_VALUE;
        this.clusters = null;
        this.clustersSize = -1;
        this.patternsSize = -1;
        this.pairs = null;
    }

    Chromosome(int numP, int numClus) {
        this.objective = Float.MAX_VALUE;
        this.clustersSize = numClus;
        this.patternsSize = numP;
        this.clusters = new Cluster[numClus];
        this.pairs = new Pair[numP];
    }

    Chromosome(Chromosome orig) {
        this.clustersSize = orig.clustersSize;
        this.patternsSize = orig.patternsSize;
        this.objective = orig.objective;
        this.clusters = new Cluster[clustersSize];
        this.pairs = new Pair[patternsSize];
        for (int i = 0; i < pairs.length; i++) {
            Pair aux = new Pair();

            aux.cluster = orig.pairs[i].cluster;
            aux.pattern = orig.pairs[i].pattern;
            this.pairs[i] = aux;
        }
        for (int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster();
            clusters[i].centroid = new float[orig.clusters[i].centroid.length];
            System.arraycopy(orig.clusters[i].centroid, 0, clusters[i].centroid, 0, clusters[i].centroid.length);
            clusters[i].size = orig.clusters[i].size;
        }
    }
}
