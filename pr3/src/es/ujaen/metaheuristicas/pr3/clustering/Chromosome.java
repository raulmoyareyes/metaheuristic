/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr3.clustering;

import es.ujaen.metaheuristicas.pr3.utils.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase para gestionar Chromosomas. Son listas de pares con patrones y el
 * cluster al que pertenecen.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Chromosome implements Iterable<Pair<Pattern, Integer>> {

    /* Cada Pair representa un gen con un patrón y la posición del cluster al que pertenece */
    List<Pair<Pattern, Integer>> gene;

    /**
     * Constructor por defecto. Inicializa los atributos.
     */
    public Chromosome() {
        this.gene = new ArrayList();
    }

    /**
     * Constructor de copia.
     *
     * @param orig Cromosoma para realizar la copia.
     */
    public Chromosome(Chromosome orig) {
        this.gene = new ArrayList();
        for (Pair<Pattern, Integer> p : orig) {
            this.gene.add(p);
        }
    }

    /**
     * Método para añadir un nuevo gen al cromosoma.
     *
     * @param pattern Patrón para asignar en esa posició.
     * @param cluster Cluster al que pertenece el patrón.
     * @return True si se añade correctamente.
     */
    public boolean add(Pattern pattern, Integer cluster) {
        return this.gene.add(new Pair(pattern, cluster));
    }

    /**
     * Método para añadir un nuevo gen en una posición concreta.
     *
     * @param position Posición en la que añadir el gen.
     * @param pair Gen a insertar en la posición indicada.
     */
    public void add(Integer position, Pair<Pattern, Integer> pair) {
        this.gene.add(position, pair);
    }

    /**
     * Método para añadir un nuevo gen al cromosoma.
     *
     * @param pair Pair que incluye el patrón y el cluster al que pertenece en
     * el cromosoma.
     * @return True si se añade correctamente.
     */
    public boolean add(Pair<Pattern, Integer> pair) {
        return this.gene.add(pair);
    }

    /**
     * Método para obtener el gen de una posición concreta del cromosoma.
     *
     * @param position Posición del gen en el cromosoma.
     * @return Pair con el patrón y la posición del cluster al que pertenece.
     */
    public Pair<Pattern, Integer> get(int position) {
        return this.gene.get(position);
    }

    /**
     * Método para eliminar el gen de un cromosoma.
     *
     * @param position Posición del gen en el cromosoma.
     * @return Pair con el patrón y la posición del cluster al que pertenece.
     */
    public Pair<Pattern, Integer> remove(int position) {
        return this.gene.remove(position);
    }

    /**
     * Métod para comprobar el tamaño de la lista de genes del cromosoma.
     *
     * @return Entero con el tamaño de la lista de genes del cromosoma.
     */
    public int size() {
        return this.gene.size();
    }

    /**
     * Método para iterar por el List.
     *
     * @return Iterador del List.
     */
    @Override
    public Iterator<Pair<Pattern, Integer>> iterator() {
        Iterator<Pair<Pattern, Integer>> ipatterns = this.gene.iterator();
        return ipatterns;
    }

    /**
     * Método para transformar el contenido de la clase en un String.
     *
     * @return String con el contenido de la clase.
     */
    @Override
    public String toString() {
        return this.gene.toString();
    }

    /**
     * Método para obtener una sublista entre dos posiciones.
     *
     * @param fromIndex Indice desde el que obtener la sublista.
     * @param toIndex Indice hasta el que obtener la sublista.
     * @return List con los genes entre los dos incides.
     */
    public List<Pair<Pattern, Integer>> subList(int fromIndex, int toIndex) {
        return this.gene.subList(fromIndex, toIndex);
    }

    /**
     * Método para eliminar los genes contenidos en la sublista.
     *
     * @param sublits Lista de genes para eliminarlos.
     */
    public void removeAll(List<Pair<Pattern, Integer>> sublits) {
        this.gene.removeAll(sublits);
    }
}
