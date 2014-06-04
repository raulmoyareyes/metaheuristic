/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr1.clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar Clustesrs. Son listas de {@link Pattern} que representa
 * un conjunto con características similares.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 * @see Pattern
 */
public class Cluster {

    /* Lista de patrones de un cluster */
    private final List<Pattern> patterns;

    /**
     * Constructor por defecto. Inicializa los atributos.
     */
    public Cluster() {
        this.patterns = new ArrayList();
    }

    /**
     * Métod para añadir un nuevo {@link Pattern} a la lista del cluster.
     *
     * @param p {@link Pattern} a añadir a la lista del cluster.
     * @return True si se añade correctamente.
     */
    public boolean add(Pattern p) {
        return this.patterns.add(p);
    }

    /**
     * Métod para comprobar el tamaño de la lista de {@link Pattern} del
     * cluster.
     *
     * @return Entero con el tamaño de la lista de {@link Pattern} del cluster.
     */
    public int size() {
        return this.patterns.size();
    }

}
