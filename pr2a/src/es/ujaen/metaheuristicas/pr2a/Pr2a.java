package es.ujaen.metaheuristicas.pr2a;

import es.ujaen.metaheuristicas.pr2a.algorithms.AGE;
import es.ujaen.metaheuristicas.pr2a.algorithms.AGG;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pr2a {

    public static void main(String[] args) {

        /* Semillas - 77358477, 73584777, 35847777, 58477773, 84777735 */
        List<Integer> seeds = new ArrayList(
                Arrays.asList(new Integer[]{77358477, 73584777, 35847777, 58477773, 84777735})
        );

        /* Menú */
        Scanner in = new Scanner(System.in);
        System.out.println("Selecciona el dataset: \n 1 - Breast \n 2 - R15 \n 3 - Yeast");
        int dataset = in.nextInt();
        System.out.println("");
        System.out.println("Selecciona el algoritmo: \n 1 - AGE \n 2 - AGG");
        int algorithm = in.nextInt();
        System.out.println("\n");

        switch (dataset) {
            case 1:
                run("breast.txt", seeds, 2, algorithm);
                break;
            case 2:
                run("R15.txt", seeds, 15, algorithm);
                break;
            case 3:
                run("yeast.txt", seeds, 10, algorithm);
                break;
        }

    }

    private static Float run(String fileName, List<Integer> seeds, Integer numberClusters, Integer algorithm) {

        float finalCost = 0;
        for (Integer seed : seeds) {
            switch (algorithm) {
                case 1:
                    finalCost = AGE.init(fileName, seed, numberClusters, 50);
                    break;
                case 2:
                    finalCost = AGG.init(fileName, seed, numberClusters, 50);
                    break;
            }
        }
        return finalCost;
    }

}
