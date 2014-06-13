/**
 * En este paquete se encuentra los algoritmos para realizar el clustering.
 */
package es.ujaen.metaheuristicas.pr2a.algorithms;

/**
 * Algoritmo Genético (variante generacional elitista).
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class AGG {
    /*
     El tamaño de la población será de 50 cromosomas. La probabilidad de cruce será
     0,7 en el AGG y 1 en el AGE (siempre se cruzan los dos padres). La probabilidad de
     mutación (por gen) será de 0,01 en ambos casos. El criterio de parada en las dos
     versiones del AG consistirá en realizar 10000 evaluaciones de la función objetivo. Se
     realizarán cinco ejecuciones sobre cada caso del problema.
     */

    /**
     * 
     * @param fileName
     * @param seed
     * @return float
     * @deprecated 
     */
    public static Float init(String fileName, Integer seed, Integer numberClusters, Integer numberChromosomes) {
        return 0f;
    }
}
