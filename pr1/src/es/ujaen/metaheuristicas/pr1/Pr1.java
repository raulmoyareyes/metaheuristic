package es.ujaen.metaheuristicas.pr1;

import es.ujaen.metaheuristicas.pr1.algorithms.BL;
import es.ujaen.metaheuristicas.pr1.clustering.Cluster;
import es.ujaen.metaheuristicas.pr1.clustering.Functions;
import es.ujaen.metaheuristicas.pr1.clustering.Pattern;
import java.util.List;

/**
 * Programa principal.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pr1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /*
         * Semillas - 77358477, 73584777, 35847777, 58477773, 84777735
         */
        Long seed = (long) 77358477; // Debe ser dinamico
        String fileName = "yeast.txt"; // Debe ser dinamico

        /* *********************************************************************
         * Breast - 699 patrones con 9 dimensiones en 2 clusters.
         ******************************************************************** */
        Integer numberClusters = 2;
        List<Pattern> patterns = Functions.readData(fileName);
        List<Cluster> clusters = Functions.setRandom(patterns, seed, numberClusters);
        List<Pattern> centroids = Functions.calculateCentroids(clusters);
        Float initialCost = Functions.objectiveFunction(clusters, centroids);
        Float finalCost = BL.run(clusters, centroids); // 50

        /* Imprimir contenido en consola */
        print(fileName, patterns, clusters, centroids, initialCost, finalCost);

        /* *********************************************************************
         * R15 - 600 patrones con 2 dimensiones en 15 clusters.
         ******************************************************************** */
        /* *********************************************************************
         * Yeast - 1484 Patrones con 8 dimensiones en 10 clusters.
         ******************************************************************** */
    }

    private static void print(String fileName, List<Pattern> patterns,
            List<Cluster> clusters, List<Pattern> centroids, Float initialCost, Float finalCost) {

        System.out.println("Lectura del dataset " + fileName);
        System.out.println("Número de patrones: " + patterns.size());
        System.out.println("Dimensión de los patrones: " + patterns.get(0).size());
        System.out.println("Número de clusters: " + clusters.size());
        System.out.println("Número de centroides: " + centroids.size());
        System.out.println("    Centroide inicial 1: " + centroids.get(0).toString());
        System.out.println("    Centroide inicial 2: " + centroids.get(1).toString());
        System.out.println("    Coste inicial: " + initialCost);
        System.out.println("    Coste final: " + finalCost);
        System.out.println("\n");

    }
}
