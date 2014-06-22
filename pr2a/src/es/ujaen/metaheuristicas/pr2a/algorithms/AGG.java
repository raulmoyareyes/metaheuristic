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
 * Algoritmo Genético (variante generacional elitista).
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class AGG {

    /**
     * Método para lanzar el algoritmo genético con solución inicial.
     *
     * @param fileName Nombre del archivo para realizar la carga de datos.
     * @param seed Semilla a utilizar para la generación de números aleatorios.
     * @param numberClusters Número de clusters a generar.
     * @param population Tamaño de la población.
     * @return Float con el coste de la solución.
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
        for (int i = 0; i < patterns.size(); i++) {
            populationClusters.add(Functions.setInitial(patterns, rand, numberClusters));
            populationCentroids.add(Functions.calculateCentroids(populationClusters.get(i)));
            populationDistances.add(Functions.objectiveFunction(populationClusters.get(i), populationCentroids.get(i)));
            populationChromosomes.add(Functions.setChromosome(populationClusters.get(i), patterns, rand));
        }

        /* Mejor solución inicial */
        float initialCost = Functions.bestDistance(populationDistances);

        /* Posición de la mejor solución */
        int positionBetter = run(populationClusters, populationCentroids, populationDistances, populationChromosomes, numberClusters, rand);

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
     * Método con el algoritmo genético sin utilizar ningún método de solución
     * inicial.
     *
     * @param populationClusters Población de distintas soluciones de clusters.
     * @param populationCentroids Población de centroides de cada cluster.
     * @param populationDistances Población de costes de cada solución.
     * @param populationChromosomes Población de cada cromosoma
     * @param numberClusters Número de clusters a generar.
     * @param rand Random con la semilla utilizada.
     * @return Integer con la posición de la mejor solución de la población.
     */
    public static Integer run(List<List<Cluster>> populationClusters, List<List<Pattern>> populationCentroids,
            List<Float> populationDistances, List<Chromosome> populationChromosomes, Integer numberClusters, Random rand) {

        float crossingProbability = (float) 0.7;
        float mutationProbability = (float) 0.01;
        int temporalPopulation = populationClusters.size();

        /* Repite hasta que supere el número de iteraciones */
        for (int i = 0; i < 10000; i++) {
            List<Chromosome> children = new ArrayList();
            List<Integer> fathers = new ArrayList();
            int positionBest = Functions.positionBest(populationDistances);
            /* Selecciona padres aleatorios */
            int numberCrossing = (int) (populationChromosomes.size() * crossingProbability / 2);
            for (int j = 0; j < numberCrossing; j++) {
                int father = Functions.selected(populationDistances, rand);
                int mother = Functions.selected(populationDistances, rand);
                while (father == mother) {
                    mother = Functions.selected(populationDistances, rand);
                }
                fathers.add(father);
                fathers.add(mother);
                /* Probabilidad de cruce es 0.7 por tanto siempre cruza */
                List<Chromosome> newChildren = Functions.crossing(populationChromosomes, father, mother, rand);
                for (Chromosome c : newChildren) {
                    children.add(c);
                }
                i++;
            }
            /* Probabilidad de mutación es 0.01 */
            int numberMutatitions = (int) (temporalPopulation * populationChromosomes.size() * mutationProbability);
            for (int j = 0; j < numberMutatitions; j++) {
                children = Functions.mutation(children, rand, numberClusters);
            }
            /* Sustituye cada hijo */
            for (int j = 0; j < populationChromosomes.size(); j++) {
                if (j != positionBest) {
                    int pos = fathers.indexOf(j);
                    if (pos != -1) {
                        Chromosome child = populationChromosomes.set(pos, children.get(pos));
                        List<Cluster> childrenClusters = Functions.getClusterChromosome(child, numberClusters);
                        List<Pattern> childrenCentroid = Functions.calculateCentroids(childrenClusters);
                        Float cost = Functions.objectiveFunction(childrenClusters, childrenCentroid);
                        populationCentroids.set(pos, childrenCentroid);
                        populationClusters.set(pos, childrenClusters);
                        populationDistances.set(pos, cost);
                    }
                }
            }
        }

        return Functions.positionBest(populationDistances);
    }
}
