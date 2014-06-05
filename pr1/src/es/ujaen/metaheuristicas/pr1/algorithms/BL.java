package es.ujaen.metaheuristicas.pr1.algorithms;

import es.ujaen.metaheuristicas.pr1.clustering.Cluster;
import es.ujaen.metaheuristicas.pr1.clustering.Functions;
import es.ujaen.metaheuristicas.pr1.clustering.Pattern;
import java.util.List;

/**
 * Algoritmo Búsqueda Local
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class BL {

    public static Float run(List<Cluster> clusters, List<Pattern> centroids) {
        
        /* Se calcula el coste con la función objetivo inicial de los clusters */
        Float coste = Functions.objectiveFunction(clusters, centroids);
        System.out.println("Coste inicial: " + coste);
        
        return coste;
    }
    
    

}
