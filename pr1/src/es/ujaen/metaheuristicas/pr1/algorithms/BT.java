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
     * @deprecated No está implementado.
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
     * @deprecated no implementada.
     */
    public static Float run(List<Cluster> clusters, List<Pattern> centroids,
            float initialCost, Random rand, List<Pattern> patterns, Integer numberClusters) {

        int iterations = 10000;
        int reset = 2000;
        int solutionsGenerated = 20;
        double probabilityResetInitial = 0.25;
        double probabilityResetSolution = 0.25;
        double probabilityResetMemory = 0.50;
        double probabilityCluster = 0.05;
        int size = 0;
        for (Cluster c : clusters) {
            size += c.size();
        }
        int sizeTabuList = size / 3;
        List<Pair<Integer, Integer>> tabuList = new ArrayList();
        float cost = initialCost;

        Matrix<Integer> incidences = new Matrix(patterns.size(), clusters.size());

        // se repite 10000 veces
        for (int i = 0; i < iterations; i++) {
            // en cada iteracion se generan 20 soluciones y nos quedamos con la mejor
            float best = 0;
            int cluster = 0;
            Pair bestMove = null;
            // sacar 20 soluciones
            for (int j = 0; j < solutionsGenerated; j++) {
                for (int k = 0; k < clusters.size(); k++) {
                    float probability = rand.nextFloat();
                    if (probability < probabilityCluster) {
                        int assigned;
                        do {
                            assigned = rand.nextInt(clusters.size());
                        } while (assigned == k);
                        int patternPosition = rand.nextInt(clusters.get(k).size());
                        float actualCost = Functions.factorize(clusters, centroids, assigned, k, patternPosition);
                        Pair<Integer, Integer> actualMove = new Pair(patternPosition, assigned);
                        if ((actualCost < best) && !tabuList.contains(actualMove)) {
                            best = actualCost;
                            bestMove = actualMove;
                            cluster = k;
                        }
                    }
                }
            }
            if (best < 0 && bestMove != null) {
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
                Integer count = incidences.get((int) bestMove.first, (int) bestMove.second);
                incidences.add((int) bestMove.first, (int) bestMove.second, count + 1);

            }

            //reinicilizar cada 2000
            if (i % reset == 0 && i != 0) {
                // ampliamos o reducimos la lista tabú en un 25%
                float probability = rand.nextFloat();
                if (probability < 0.5) {
                    //recudir tamaño 25%
                    sizeTabuList = (int) (sizeTabuList - sizeTabuList * 0.25);
                    tabuList = new ArrayList();
                } else {
                    //ampliar tamaño 25%
                    sizeTabuList = (int) (sizeTabuList + sizeTabuList * 0.25);
                    tabuList = new ArrayList();
                }
                probability = rand.nextFloat();
                if (probability < probabilityResetInitial) {
                    //reinicio a una solucion inicial
                    clusters = Functions.setRandom(patterns, rand, numberClusters);
                    centroids = Functions.calculateCentroids(clusters);
                    cost = Functions.objectiveFunction(clusters, centroids);
                } else if (probability < probabilityResetInitial + probabilityResetSolution) {
                    //reinicio a una solucion actual

                } else if (probability < probabilityResetInitial + probabilityResetSolution + probabilityResetMemory) {
                    //crear una solucion inicial menos frecuente.
                    clusters = Functions.setLessFrecuency(patterns, rand, numberClusters, incidences);
                    centroids = Functions.calculateCentroids(clusters);
                    cost = Functions.objectiveFunction(clusters, centroids);
                }
            }
        }

        return cost;
    }
}
