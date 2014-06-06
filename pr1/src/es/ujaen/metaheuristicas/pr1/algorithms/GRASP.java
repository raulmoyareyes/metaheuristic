/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
package es.ujaen.metaheuristicas.pr1.algorithms;

import es.ujaen.metaheuristicas.pr1.clustering.Cluster;
import es.ujaen.metaheuristicas.pr1.clustering.Functions;
import es.ujaen.metaheuristicas.pr1.clustering.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Algoritmo GRASP
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class GRASP {

    /**
     * Método para lanzar el algorimo de búsqueda tabú.
     *
     * @param clusters List de {@link Cluster} con los patrones asignados.
     * @param centroids List de {@link Pattern} representando los centroides de
     * los clusters.
     * @return Float con el coste de la solución.
     * @deprecated No ha sido implementado.
     */
    public static float run(List<Cluster> clusters, List<Pattern> centroids) {
        
        List<Pattern> restrictedList = new ArrayList();
        
        return 0f;
    }
}
