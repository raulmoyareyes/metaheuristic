/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
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
     * Método para lanzar el algorimo de búsqueda local.
     *
     * @param fileName
     * @param seed
     * @param numberClusters
     * @return Float con el coste de la solución.
     */
    public static float run(String fileName, Integer seed, Integer numberClusters) {

        /* Conjunto de todos los patrones */
        List<Pattern> patterns = Functions.readData(fileName);
        
        /* Inicio de la cuenta del tiempo de ejecución */
        Long time = System.currentTimeMillis();
        /* Asignación de los patrones a cada cluster */
        List<Cluster> clusters = Functions.setRandom(patterns, seed, numberClusters);
        /* Cálculo del centroide de cada cluster */
        List<Pattern> centroids = Functions.calculateCentroids(clusters);
        /* Coste de la solución inicial */
        Float initialCost = Functions.objectiveFunction(clusters, centroids);

        /* Itera si hay mejora */
        boolean improvement = true;
        /* Itera como máximo 10000 veces */
        int iterations = 10000;

        /* Calcula el coste con la función objetivo inicial de los clusters */
        float cost = Functions.objectiveFunction(clusters, centroids);

        /* Repite hasta que supere el núemro de iteraciones o no mejore */
        for (int count = 0; improvement && count < iterations;) {
            improvement = false;
            /* Recorre los cluster */
            for (int i = 0; i < clusters.size(); i++) {
                /* Recorre los patrones */
                Cluster cluster = clusters.get(i);
                for (int j = 0; j < cluster.size(); j++) {
                    /* Repite comprobando el patrón en todos los clusters */
                    for (int assigned = 0; assigned < clusters.size(); assigned++) {
                        if (assigned != i && cluster.size() > 3) {
                            /* Obtiene el valor de mejora de la solución */
                            Float objective = Functions.factorize(clusters, centroids, assigned, i, j);
                            count++;
                            /* Si es negativo es porque mejora, entonces recalcula */
                            if (objective < 0) {
                                improvement = true;
                                cost += objective;
                                /* Recalcular los centroides */
                                centroids = Functions.calculateCentroids(clusters);
                                /* Cambio del patrón al nuevo cluster */
                                clusters.get(assigned).add(cluster.remove(j));
                                /* Pasa al siguiente cluster */
                                j--;
                                assigned = clusters.size();
                            }
                        }
                    }
                }
            }
        }

        /* Fin de la cuenta del tiempo de ejecución */
        time = System.currentTimeMillis() - time;
        /* Imprimir contenido en consola */
        Functions.print(fileName, patterns, clusters, centroids, seed,
                initialCost, cost, time);

        return cost;
    }

}
