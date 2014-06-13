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
    /*
     El tamaño de la población será de 50 cromosomas. La probabilidad de cruce será
     0,7 en el AGG y 1 en el AGE (siempre se cruzan los dos padres). La probabilidad de
     mutación (por gen) será de 0,01 en ambos casos. El criterio de parada en las dos
     versiones del AG consistirá en realizar 10000 evaluaciones de la función objetivo. Se
     realizarán cinco ejecuciones sobre cada caso del problema.
     */

    /**
     * Método para
     *
     * @param fileName
     * @param seed
     * @param numberClusters
     * @param numberChromosomes
     * @return Float
     * @deprecated
     */
    public static Float init(String fileName, Integer seed, Integer numberClusters, Integer numberChromosomes) {
        /* Conjunto de todos los patrones */
        List<Pattern> patterns = Functions.readData(fileName);
        /* Números aleatorios a partir de una semilla */
        Random rand = new Random(seed);
        /* Inicio de la cuenta del tiempo de ejecución */
        Long time = System.currentTimeMillis();
        /**/
        List<Chromosome> chromosomes = Functions.setRandomChromosomes(numberChromosomes);

        /* Coste de la solución final */
        float cost = run();

        /* Fin de la cuenta del tiempo de ejecución */
        time = System.currentTimeMillis() - time;
        /* Imprimir contenido en consola */
        Functions.print(fileName, patterns, clusters, centroids, seed,
                initialCost, cost, time);

        return cost;
    }

    public static Float run() {//List<Cluster> clusters, List<Pattern> centroids, float initialCost) {
        return 3f;
    }
}
