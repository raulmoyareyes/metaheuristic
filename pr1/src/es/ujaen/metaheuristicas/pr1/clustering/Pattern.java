/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr1.clustering;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar los patrones. Son listas de datos que representan
 * característica (dimensiones).
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pattern {

    /* Lista de datos de un patrón */
    private final List<Float> datas;

    /**
     * Constructor por defecto. Inicializa los atributos.
     */
    public Pattern() {
        this.datas = new ArrayList();
    }

    /**
     * Métod para añadir un nuevo dato a la lista del patrón.
     *
     * @param data Dato a añadir a la lista del patrón.
     * @return True si se añade correctamente.
     */
    public boolean add(Float data) {
        return this.datas.add(data);
    }

    /**
     * Métod para comprobar el tamaño de la lista de datos del patrón.
     *
     * @return Entero con el tamaño de la lista de datos del patrón.
     */
    public int size() {
        return this.datas.size();
    }

}
