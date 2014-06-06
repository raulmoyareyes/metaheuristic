package es.ujaen.metaheuristicas.pr1;

import es.ujaen.metaheuristicas.pr1.algorithms.BL;
import es.ujaen.metaheuristicas.pr1.algorithms.GRASP;
import es.ujaen.metaheuristicas.pr1.clustering.Cluster;
import es.ujaen.metaheuristicas.pr1.clustering.Functions;
import es.ujaen.metaheuristicas.pr1.clustering.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pr1 {

    public static void main(String[] args) {

        /*
         * Semillas - 77358477, 73584777, 35847777, 58477773, 84777735
         */
        List<Integer> seeds = new ArrayList(
                Arrays.asList(new Integer[]{77367799, 73584777, 35847777, 58477773, 84777735})
        );

        Scanner in = new Scanner(System.in);
        System.out.println("Selecciona el dataset: \n 1 - Breast \n 2 - R15 \n 3 - Yeast");
        int dataset = in.nextInt();
        System.out.println("");
        System.out.println("Selecciona el algoritmo: \n 1 - BL \n 2 - BT \n 3 - GRASP");
        int algorithm = in.nextInt();
        System.out.println("\n");

        switch (dataset) {
            case 1:
                String breast = "Breast - 699 patrones con 9 dimensiones en 2 clusters.";
                run("breast.txt", breast, seeds, 2, algorithm);
                break;
            case 2:
                String r15 = "R15 - 600 Patrones con 2 dimensiones en 15 clusters.";
                run("R15.txt", r15, seeds, 15, algorithm);
                break;
            case 3:
                String yeast = "Yeast - 1484 Patrones con 8 dimensiones en 10 clusters.";
                run("yeast.txt", yeast, seeds, 10, algorithm);
                break;
        }
    }

    private static void print(String dataset, String fileName, List<Pattern> patterns,
            List<Cluster> clusters, List<Pattern> centroids, Integer seed,
            Float initialCost, Float finalCost, Long time) {
        System.out.println("======================================================================\n"
                + "    " + dataset + "\n"
                + "======================================================================");
        System.out.println("Lectura del dataset " + fileName);
        System.out.println("Número de patrones: " + patterns.size());
        System.out.println("Dimensión de los patrones: " + patterns.get(0).size());
        System.out.println("Número de clusters: " + clusters.size());
        System.out.println("Semilla: " + seed);
        System.out.println("Tiempo de ejecución: " + time + " milisegundos");
//        System.out.println("Número de centroides: " + centroids.size());
//        System.out.println("    Centroide inicial 1: " + centroids.get(0).toString());
//        System.out.println("    Centroide inicial 2: " + centroids.get(1).toString());
//        System.out.println("    Coste inicial: " + initialCost);
        System.out.println("===================== Coste final: " + finalCost + " ======================");
        System.out.println("\n");

    }

    private static void run(String fileName, String head, List<Integer> seeds, Integer numberClusters, Integer algorithm) {
        List<Pattern> patterns = Functions.readData(fileName);
        for (Integer seed : seeds) {
            List<Cluster> clusters = null;
            List<Pattern> centroids = null;
            Float initialCost = null;
            float finalCost = 0;
            long time = 0;
            switch (algorithm) {
                case 1:
                    clusters = Functions.setRandom(patterns, seed, numberClusters);
                    centroids = Functions.calculateCentroids(clusters);
                    initialCost = Functions.objectiveFunction(clusters, centroids);
                    time = System.currentTimeMillis();
                    finalCost = BL.run(clusters, centroids);
                    time = System.currentTimeMillis() - time;
                    break;
                case 2:
//                            Float finalCost = BT.run(clusters, centroids);
                    break;
                case 3:
                    centroids = Functions.calculateCentroids(clusters, seed);
                    clusters = Functions.setGreedy(patterns, numberClusters);
                    initialCost = Functions.objectiveFunction(clusters, centroids);
                    time = System.currentTimeMillis();
                    finalCost = GRASP.run(clusters, centroids);
                    time = System.currentTimeMillis() - time;
                    break;
            }

            /* Imprimir contenido en consola */
            print(head, fileName, patterns, clusters, centroids, seed,
                    initialCost, finalCost, time);
        }
    }
}
