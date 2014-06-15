/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
package es.ujaen.metaheuristicas.pr2a.algorithms;

import es.ujaen.metaheuristicas.pr2a.clustering.Chromosome;
import es.ujaen.metaheuristicas.pr2a.clustering.Cluster;
import es.ujaen.metaheuristicas.pr2a.clustering.Functions;
import es.ujaen.metaheuristicas.pr2a.clustering.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Algoritmo Genético (variante generacional estacionaria),
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class AGE {

    /**
     * Método para
     *
     * @param fileName
     * @param seed
     * @param numberClusters
     * @param population
     * @return Float
     * @deprecated
     */
    public static Float init(String fileName, Integer seed, Integer numberClusters, Integer population) {
        /* Conjunto de todos los patrones */
        List<Pattern> patterns = Functions.readData(fileName);
        /* Números aleatorios a partir de una semilla */
        Random rand = new Random(seed);
        /* Inicio de la cuenta del tiempo de ejecución */
        Long time = System.currentTimeMillis();

        /* Clusters de la población */
        List<List<Cluster>> populationClusters = new ArrayList();
        /* Centroides de cada cluster de la población */
        List<List<Pattern>> populationCentroids = new ArrayList();
        /* Distancia de cada cluster de la población a su centroide */
        List<Float> populationDistances = new ArrayList();
        /* Chromosoma de cada cluster de la población */
        List<Chromosome> populationChromosomes = new ArrayList();

        /* Crea 50 soluciones y las almacena para trabajar con ellas */
        for (int i = 0; i < population; i++) {
//            populationClusters.add(Functions.setRandom(patterns, rand, numberClusters));
//            populationCentroids.add(Functions.calculateCentroids(populationClusters.get(i)));
//            populationDistances.add(Functions.objectiveFunction(populationClusters.get(i), populationCentroids.get(i)));
//            populationChromosomes.add(Functions.setChromosome(populationClusters.get(i), patterns, rand));
            populationChromosomes.add(Functions.setChromosome(patterns, rand, numberClusters));
            populationClusters.add(Functions.getClusterChromosome(populationChromosomes.get(i), numberClusters));
            populationCentroids.add(Functions.calculateCentroids(populationClusters.get(i)));
            populationDistances.add(Functions.objectiveFunction(populationClusters.get(i), populationCentroids.get(i)));

        }

        /* Mejor solución inicial */
        float initialCost = Functions.bestDistance(populationDistances);

        /* Posición de la mejor solución */
        int positionBetter = run(populationClusters, populationCentroids, populationDistances, populationChromosomes, patterns, numberClusters, rand);

        /* Coste de la solución final */
        float cost = populationDistances.get(positionBetter);

        /* Fin de la cuenta del tiempo de ejecución */
        time = System.currentTimeMillis() - time;
        /* Imprimir contenido en consola */
        Functions.print(fileName, patterns, populationClusters.get(positionBetter), populationCentroids.get(positionBetter), seed,
                initialCost, cost, time);

        return cost;
    }

    /**
     *
     * @param populationClusters
     * @param populationCentroids
     * @param populationDistances
     * @param populationChromosomes
     * @param patterns
     * @param numberClusters
     * @param rand
     * @return int
     * @deprecated No comentado y sin terminar de implementar
     */
    public static Integer run(List<List<Cluster>> populationClusters, List<List<Pattern>> populationCentroids,
            List<Float> populationDistances, List<Chromosome> populationChromosomes, List<Pattern> patterns, Integer numberClusters, Random rand) {

        float crossingProbability = 1;
        float mutationProbability = (float) 0.01;

        for (int i = 0; i < 10000; i++) {
            /* Selecciona dos padres aleatorios */
            int father = rand.nextInt(populationChromosomes.size());
            int mother = rand.nextInt(populationChromosomes.size());
            List<Chromosome> children = new ArrayList();
            /* Probabilidad de cruce es 1 por tanto siempre cruza */
            float probability = rand.nextFloat();
            if (probability < crossingProbability) {
                children = Functions.crossing(populationClusters, populationChromosomes, father, mother, rand);
            }
            /* Probabilidad de mutación es 0.01 */
            // parece que hay que mutar un número de veces 
            probability = rand.nextFloat();
            if (probability > mutationProbability) {
                Functions.mutation();
            }

            // comprobar si son mejores.
//            for(Chromosome c : children){
//                
//            }
//            List<Cluster> childrenClusters = Functions.getClusterChromosome(children.get(0), numberClusters);
//            List<Pattern> childrenCentroid = Functions.calculateCentroids(childrenClusters);
//            Float cost = Functions.objectiveFunction(childrenClusters, childrenCentroid);
//
//            if (cost < populationDistances.get(father)) {
//                populationChromosomes.set(father, children.get(0));
//                populationCentroids.set(father, childrenCentroid);
//                populationClusters.set(father, childrenClusters);
//                populationDistances.set(father, cost);
//            }
//            System.out.println(i);

        }

        return 0;
    }
}
