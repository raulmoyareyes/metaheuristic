/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr2a.clustering;

import es.ujaen.metaheuristicas.pr2a.utils.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Clase para gestionar Chromosomas. Son listas de pares con patrones y el
 * cluster al que pertenecen.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 *
 * @deprecated Grandes cambios, repasar entera.
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
     *
     * @param orig
     * @deprecated No terminado
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
     * @param cluster Cluster al que pertenece el patrón.
     * @return True si se añade correctamente.
     */
    public boolean add(Pattern pattern, Integer cluster) {
        return this.gene.add(new Pair(pattern, cluster));
    }

    /**
     * 
     * @param position
     * @param pair
     * @deprecated Noterminado
     */
    public void add(Integer position, Pair<Pattern, Integer> pair) {
        this.gene.add(position, pair);
    }

    /**
     *
     * @param pair
     * @return a
     * @deprecated No comentado
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
     *
     * @param position
     * @param cluster
     * @return a
     * @deprecated No terminado.
     */
//    public Integer set(Integer position, Pair<Pattern, Integer> pair) {
//        return this.gene.set(position, pair);
//    }
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
     * Método para
     *
     * @param fromIndex
     * @param toIndex
     * @return list
     * @deprecated No comentada
     */
    public List<Pair<Pattern, Integer>> subList(int fromIndex, int toIndex) {
        return this.gene.subList(fromIndex, toIndex);
    }

    /**
     *
     * @param sublits
     * @deprecated No comentada.
     */
    public void removeAll(List<Pair<Pattern, Integer>> sublits) {
//        for (Pair<Pattern, Integer> other : sublits) {
//            for (int i = 0; i < this.gene.size(); i++) {
//                if (this.gene.get(i).first==other.first) {
//                    this.gene.remove(i);
//                }
//            }
//        }
        this.gene.removeAll(sublits);
    }
}
