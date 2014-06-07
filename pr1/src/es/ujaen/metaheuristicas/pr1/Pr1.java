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
                Arrays.asList(new Integer[]{77358477, 73584777, 35847777, 58477773, 84777735})
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

    private static void run(String fileName, String head, List<Integer> seeds, Integer numberClusters, Integer algorithm) {
        
        for (Integer seed : seeds) {
            switch (algorithm) {
                case 1:
                    float finalCost = BL.run(fileName, seed, numberClusters);
                    break;
                case 2:
//                            Float finalCost = BT.run(clusters, centroids);
                    break;
                case 3:
//                    centroids = Functions.calculateCentroids(clusters, seed);
//                    clusters = Functions.setGreedy(patterns, numberClusters);
//                    initialCost = Functions.objectiveFunction(clusters, centroids);
//                    time = System.currentTimeMillis();
//                    finalCost = GRASP.run(clusters, centroids);
//                    time = System.currentTimeMillis() - time;
                    break;
            }
        }
    }
}
