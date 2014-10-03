/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
package es.ujaen.metaheuristicas.pr2a.algorithms;

import es.ujaen.metaheuristicas.pr2a.clustering.Chromosome;
import es.ujaen.metaheuristicas.pr2a.clustering.Functions;
import es.ujaen.metaheuristicas.pr2a.clustering.Pattern;
import java.util.Random;

/**
 * Algoritmo Genético (variante generacional estacionaria).
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class AGE {

    /**
     * Método para lanzar el algoritmo genético con solución inicial.
     *
     * @param fileName Nombre del archivo para realizar la carga de datos.
     * @param size
     * @param seed Semilla a utilizar para la generación de números aleatorios.
     * @param numberClusters Número de clusters a generar.
     * @param population Tamaño de la población.
     * @return Float con el coste de la solución.
     */
    public static Float init(String fileName, Integer size, Integer seed, Integer numberClusters, Integer population) {
        float mutationProbability = (float) 0.01;
        int childrenSize = 2;

        Random rand = new Random(seed);
        Pattern[] patterns = Functions.readData(fileName, size);
        Chromosome[] chromosomes = Functions.setInitial(patterns, numberClusters, population, rand);
        float initialSolution = Functions.bestSolution(chromosomes);;

        long time = System.currentTimeMillis();

        int generation = 0;
        int count = 0;
        while (count < 10000) {
            // selección
            int[] fathers = Functions.selection(chromosomes, 2, rand);
            // cruce
            Chromosome[] children = Functions.crossing(patterns, chromosomes, rand, fathers[0], fathers[1]);

            // mutación                              
            for (int i = 0; i < mutationProbability * patterns.length * childrenSize; i++) {
                Functions.mutation(chromosomes, patterns, rand);
            }
            // calcula el coste de cada hijo
            for (Chromosome child : children) {
                Functions.objectiveFunction(patterns, child);
                count++;
            }
            // sustituye
            for (Chromosome child : children) {
                int position = Functions.worstPosition(chromosomes);
                if (child.objective < chromosomes[position].objective) {
                    chromosomes[position] = child;
                }
            }
            generation++;
        }
        float best = Functions.bestSolution(chromosomes);

        time = System.currentTimeMillis() - time;
        Functions.print(fileName, patterns, chromosomes, seed, initialSolution, best, generation, time);
        return best;
    }
}
