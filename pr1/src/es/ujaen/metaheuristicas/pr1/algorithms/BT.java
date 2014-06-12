/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
package es.ujaen.metaheuristicas.pr1.algorithms;

import es.ujaen.metaheuristicas.pr1.clustering.Cluster;
import es.ujaen.metaheuristicas.pr1.clustering.Functions;
import es.ujaen.metaheuristicas.pr1.clustering.Pattern;
import es.ujaen.metaheuristicas.pr1.utils.Matrix;
import es.ujaen.metaheuristicas.pr1.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Algoritmo Búsqueda Tabú
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class BT {

    /**
     * Método para lanzar el algorimo de búsqueda tabú.
     *
     * @param fileName Nombre del archivo para realizar la carga de datos.
     * @param seed Semilla a utilizar para la generación de la solución inicial.
     * @param numberClusters Número de clusters a generar.
     * @return Float con el coste de la solución.
     */
    public static float init(String fileName, Integer seed, Integer numberClusters) {

        /* Conjunto de todos los patrones */
        List<Pattern> patterns = Functions.readData(fileName);
        /* Números aleatorios a partir de una semilla */
        Random rand = new Random(seed);

        /* Inicio de la cuenta del tiempo de ejecución */
        Long time = System.currentTimeMillis();
        /* Asignación de los patrones a cada cluster */
        List<Cluster> clusters = Functions.setRandom(patterns, rand, numberClusters);
        /* Cálculo del centroide de cada cluster */
        List<Pattern> centroids = Functions.calculateCentroids(clusters);
        /* Coste de la solución inicial */
        float initialCost = Functions.objectiveFunction(clusters, centroids);

        /* Coste de la solución final */
        float cost = run(clusters, centroids, initialCost, rand, patterns, numberClusters);

        /* Fin de la cuenta del tiempo de ejecución */
        time = System.currentTimeMillis() - time;
        /* Imprimir contenido en consola */
        Functions.print(fileName, patterns, clusters, centroids, seed,
                initialCost, cost, time);

        return cost;
    }

    /**
     * Método con el algoritmo de búsqueda local sin utilizar ningún método de
     * generación de solución inicial.
     *
     * @param clusters Clusters con los patrones asignados.
     * @param centroids Centroides de cada cluster.
     * @param initialCost Coste de la solución inicial.
     * @param rand Números aleatorios a partir de una semilla.
     * @param patterns Todos los patrones para asignarlos a los clusters.
     * @param numberClusters Número de clusters que se van a crear.
     * @return Float con el coste de la solución generada mediante BL.
     * @deprecated Falta terminar de comentar.
     */
    public static Float run(List<Cluster> clusters, List<Pattern> centroids,
            float initialCost, Random rand, List<Pattern> patterns, Integer numberClusters) {
        /* Número de repeticiones del algoritmo*/
        int iterations = 10000;
        /* Iteraciones para reinicialización */
        int reset = 2000;
        /* Número de soluciones generadas en cada iteración */
        int solutionsGenerated = 20;
        /* Probabilidad de reinicializar a una solución aleatoria */
        double probabilityResetInitial = 0.25;
        /* Probabilidad de reinicializar a la mejor solución */
        double probabilityResetSolution = 0.25;
        /* Probabilidad de reinicializar a una solución menos frecuente */
        double probabilityResetMemory = 0.50;
        /* Probabilidad de asignar el cluster a un patrón */
        double probabilityCluster = 0.05;
        /* Calculo de tamaño de la lista tabú */
        int size = 0;
        for (Cluster c : clusters) {
            size += c.size();
        }
        int sizeTabuList = size / 3;
        /* Inicialización de la lista tabú */
        List<Pair<Integer, Integer>> tabuList = new ArrayList();
        /* Coste inicial de la solución */
        float cost = initialCost;

        /* Matriz de incidencia de cada patrón en los clusters */
        Matrix<Integer> incidences = new Matrix(patterns.size(), clusters.size());

        /* Repite 10000 veces */
        for (int i = 0; i < iterations; i++) {
            float best = 0;
            int cluster = 0;
            Pair bestMove = null;
            /* Genera 20 soluciones en cada iteración */
            for (int j = 0; j < solutionsGenerated; j++) {
                /* Recorre cada cluster */
                for (int k = 0; k < clusters.size(); k++) {
                    float probability = rand.nextFloat();
                    /* Probabilidad de asignar el patrón al cluster */
                    if (probability < probabilityCluster) {
                        int assigned;
                        do {
                            assigned = rand.nextInt(clusters.size());
                        } while (assigned == k);
                        /* Posición aleatoria de un patrón para intercambiar */
                        int patternPosition = rand.nextInt(clusters.get(k).size());
                        /* Mejora del coste tras el intercambio */
                        float actualCost = Functions.factorize(clusters, centroids, assigned, k, patternPosition);
                        /* Almacena el movimiento */
                        Pair<Integer, Integer> actualMove = new Pair(patternPosition, assigned);
                        /* Almacena el mejor coste y el mejor movimiento si no está en la lista tabú */
                        if ((actualCost < best) && !tabuList.contains(actualMove)) {
                            best = actualCost;
                            bestMove = actualMove;
                            cluster = k;
                        }
                    }
                }
            }
            /* Si mejora recalculamos */
            if (best < 0 && bestMove != null) {
                /* Coste actual de la solución */
                cost += best;
                /* Cambio del patrón al nuevo cluster */
                clusters.get((int) bestMove.second).add(clusters.get(cluster).remove((int) bestMove.first));
                /* Recalcular los centroides */
                centroids = Functions.calculateCentroids(clusters);
                // actualizar lista tabú
                tabuList.add(bestMove);
                if (tabuList.size() > sizeTabuList) {
                    tabuList.remove(0);
                }
                /* Actualiza la matriz de incidencia */
                Integer count = incidences.get((int) bestMove.first, (int) bestMove.second);
                incidences.add((int) bestMove.first, (int) bestMove.second, count + 1);

            }

            /* Cada 2000 iteraciones se produce un reinicio */
            if (i % reset == 0 && i != 0) {
                /* Probabilidad de ampliar o reducir el tamaño de la lista tabú */
                float probability = rand.nextFloat();
                if (probability < 0.5) {
                    /* Reduce la lista tabú un 25% */
                    sizeTabuList = (int) (sizeTabuList - sizeTabuList * 0.25);
                    tabuList = new ArrayList();
                } else {
                    /* Amplia la lista tabú un 25% */
                    sizeTabuList = (int) (sizeTabuList + sizeTabuList * 0.25);
                    tabuList = new ArrayList();
                }
                /* Probabilidad de realizar uno de los tipos de reinicialización */
                probability = rand.nextFloat();
                if (probability < probabilityResetInitial) {
                    /* Reinicio a una solución aleatoria inicial */
                    clusters = Functions.setRandom(patterns, rand, numberClusters);
                    centroids = Functions.calculateCentroids(clusters);
                    cost = Functions.objectiveFunction(clusters, centroids);
                } else if (probability < probabilityResetInitial + probabilityResetSolution) {
                    /* Reinicio a la mejor solución actual */
                    /* No hay que hacer nada porque ya trabajo con la mejor solución inicial */
                } else if (probability < probabilityResetInitial + probabilityResetSolution + probabilityResetMemory) {
                    /* Reinicio a una solución proco frecuente */
                    clusters = Functions.setLessFrecuency(patterns, numberClusters, incidences);
                    centroids = Functions.calculateCentroids(clusters);
                    cost = Functions.objectiveFunction(clusters, centroids);
                }
            }
        }

        return cost;
    }
}
