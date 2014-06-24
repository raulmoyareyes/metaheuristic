/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
package es.ujaen.metaheuristicas.pr3.algorithms;

import es.ujaen.metaheuristicas.pr3.clustering.Chromosome;
import es.ujaen.metaheuristicas.pr3.clustering.Functions;
import es.ujaen.metaheuristicas.pr3.clustering.Pair;
import es.ujaen.metaheuristicas.pr3.clustering.Pattern;
import java.util.Random;

/**
 * Algoritmo Genético (variante generacional elitista).
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class AGG {

    /**
     * Método para lanzar el algoritmo genético con solución inicial.
     *
     * @param fileName Nombre del archivo para realizar la carga de datos.
     * @param size
     * @param seed Semilla a utilizar para la generación de números aleatorios.
     * @param numberClusters Número de clusters a generar.
     * @param population Tamaño de la población.
     * @param frequency
     * @return Float con el coste de la solución.
     */
    public static Float init(String fileName, Integer size, Integer seed, Integer numberClusters, Integer population, float frequency) {
        float mutationProbability = (float) 0.01;
        float crossingProbability = (float) 0.7;
        int childrenSize = 2;

        Random rand = new Random(seed);
        Pattern[] patterns = Functions.readData(fileName, size);
        Chromosome[] chromosomes = Functions.setInitial(patterns, numberClusters, population, rand);
        float initialSolution = Functions.bestSolution(chromosomes);

        long time = System.currentTimeMillis();

        int generation = 0;
        int count = 0;
        while (count < 10000) {
            // guardar el mejor
            int bestPosition = Functions.bestPosition(chromosomes);
            Chromosome bestChromosome = chromosomes[bestPosition];

            // selección
            int[] fathers = Functions.selection(chromosomes, chromosomes.length, rand);
            for (int i = 0; i < crossingProbability * (chromosomes.length / 2); i++) {
                int f1 = 0;
                while (fathers[f1] == -1) {
                    f1 = rand.nextInt(fathers.length);
                }
                int f2 = 0;
                while (fathers[f2] == -1 || f1 == f2) {
                    f2 = rand.nextInt(fathers.length);
                }

                // cruce
                Chromosome[] children = Functions.crossing(patterns, chromosomes, rand, fathers[f1], fathers[f2]);
                // sustituye
                chromosomes[f1] = children[0];
                chromosomes[f2] = children[1];
                fathers[f1] = -1;
                fathers[f2] = -1;

            }

            // mutación                              
            for (int i = 0; i < mutationProbability * patterns.length * childrenSize; i++) {
                Functions.mutation(chromosomes, patterns, rand);
            }

            if (fathers[bestPosition] == -1) {
                fathers[bestPosition] = bestPosition;
                chromosomes[bestPosition] = bestChromosome;
            }

            for (int i = 0; i < chromosomes.length; i++) {
                if (fathers[i] == -1) {
                    Functions.objectiveFunction(patterns, chromosomes[i]);
                    count++;
                }
            }
            if (count % 10 == 0 && count > 0) {
                for (int i = 0; i < chromosomes.length * frequency; i++) {
                    int position = rand.nextInt(chromosomes.length);
                    localSearch(chromosomes[position], patterns);
                    count++;
                }
            }
            generation++;
        }

        float best = Functions.bestSolution(chromosomes);

        time = System.currentTimeMillis() - time;
        Functions.print(fileName, patterns, chromosomes, seed, initialSolution, best, generation, time);
        return best;
    }

    private static void localSearch(Chromosome chromosome, Pattern[] patterns) {

        for (int i = 0; i < chromosome.clusters.length; i++) {
            for (Pair pair : chromosome.pairs) {
                int cluster = pair.cluster;
                int pattern = pair.pattern;
                if (i != cluster) {
                    // factorizar
                    float objective = Functions.factorize(patterns[pattern], chromosome.clusters[cluster].centroid, chromosome.clusters[i].centroid);
                    if (objective < 0) {
                        //cambiar patron y calcular centroides
                        int m1 = chromosome.clusters[cluster].size;
                        int m2 = chromosome.clusters[i].size;

                        for (int k = 0; k < patterns[pattern].dimensions.length; k++) {

                            float centroid = chromosome.clusters[cluster].centroid[k];
                            chromosome.clusters[cluster].centroid[k] = ((m1 * centroid - patterns[pattern].dimensions[k]) / (m1 - 1));

                            centroid = chromosome.clusters[i].centroid[k];
                            chromosome.clusters[i].centroid[k] = ((m2 * centroid + patterns[pattern].dimensions[k]) / (m2 + 1));
                        }
                        chromosome.pairs[cluster].cluster = i;
                    }
                }
            }
        }
    }
}
