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

    /**
     * 
     * @param clusters
     * @param centroids
     * @return 
     * @deprecated Pendiente de terminar y comentar
     */
    public static float run(List<Cluster> clusters, List<Pattern> centroids) {

        /* Itera si hay mejora */
        boolean improvement = true;
        /* Itera como máximo 10000 veces */
        int iterations = 10000;

        /* Calcula el coste con la función objetivo inicial de los clusters */
        float cost = Functions.objectiveFunction(clusters, centroids);

        /* Repite hasta que supere el núemro de iteraciones o no mejore */
        for (int count = 0; count < iterations && improvement; count++) {
            improvement = false;
            /* Recorre los cluster */
            for (int i = 0; i < clusters.size(); i++) {
                /* Recorre los patrones */
                Cluster cluster = clusters.get(i);
                for (int j = 0; j < cluster.size(); j++) {
                    /* Repite comprobando el patrón en todos los clusters */
                    for (int assigned = 0; assigned < clusters.size(); assigned++) {
                        if(assigned != i && cluster.size() > 3){
                            //comprobar si mejora
                            //si mejora cambiar el patron y recalcular centroides
                        }
                    }
                }
            }
        }

        return cost;
    }

}
