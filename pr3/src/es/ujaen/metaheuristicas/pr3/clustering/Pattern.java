/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr3.clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase para gestionar los patrones. Son listas de datos que representan
 * característica (dimensiones).
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Pattern implements Iterable<Float> {

    /* Lista de datos de un patrón */
    private final List<Float> datas;

    /**
     * Constructor por defecto. Inicializa los atributos.
     */
    public Pattern() {
        this.datas = new ArrayList();
    }

    /**
     * Método para añadir un nuevo dato a la lista del patrón.
     *
     * @param data Dato a añadir a la lista del patrón.
     * @return True si se añade correctamente.
     */
    public boolean add(Float data) {
        return this.datas.add(data);
    }

    /**
     * Método para obtener el dato de una posición concreta del patrón.
     *
     * @param position Posicion del dato en el listado del patrón.
     * @return Dato en la posición indicada en el listado del patrón.
     */
    public Float get(int position) {
        return this.datas.get(position);
    }

    /**
     * Método para obtener un List con todos los datos del patrón.
     *
     * @return List con todos los datos del patrón.
     */
    public List<Float> get() {
        return this.datas;
    }

    /**
     * Método para eliminar un dato del patrón.
     *
     * @param index Posición del dato a eliminar en el patrón.
     * @return Dato eliminado del patrón.
     */
    public Float remove(int index) {
        return this.datas.remove(index);
    }

    /**
     * Método para comprobar el tamaño de la lista de datos del patrón.
     *
     * @return Entero con el tamaño de la lista de datos del patrón.
     */
    public int size() {
        return this.datas.size();
    }

    /**
     * Método para iterar por el List.
     *
     * @return Iterador del List.
     */
    @Override
    public Iterator<Float> iterator() {
        Iterator<Float> ipatterns = this.datas.iterator();
        return ipatterns;
    }

    /**
     * Método para transformar el contenido de la clase en un String.
     *
     * @return String con el contenido de la clase.
     */
    @Override
    public String toString() {
        return this.datas.toString();
    }

}
