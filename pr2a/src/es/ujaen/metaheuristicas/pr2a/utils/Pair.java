/**
 * En este paquete se encuentra las clases útiles para realizar distintas tareas
 * como lectura de ficheros, gestión de datos, etc.
 */
package es.ujaen.metaheuristicas.pr2a.utils;

/**
 * Clase para gestionar dos item (A y B).
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 *
 * @param <A> Primer parametro.
 * @param <B> Segundo parametro.
 */
public class Pair<A, B> {

    public A first;
    public B second;

    /**
     * Constructor con parametros. Inicializa los atributos con los parametros
     * indicados.
     *
     * @param first Primer item del par.
     * @param second Segundo item del par.
     */
    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * Método para crear un código hash de los items del par.
     *
     * @return Hash de los items del par.
     */
    @Override
    public int hashCode() {
        int hashFirst = this.first != null ? this.first.hashCode() : 0;
        int hashSecond = this.second != null ? this.second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    /**
     * Método para comparar este par con otro par.
     *
     * @param other Par con el que comparar este.
     * @return True si este par es igual al otro par, false en otro caso.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return ((this.first == otherPair.first
                    || (this.first != null && otherPair.first != null
                    && this.first.equals(otherPair.first)))
                    && (this.second == otherPair.second
                    || (this.second != null && otherPair.second != null
                    && this.second.equals(otherPair.second))));
        }

        return false;
    }

    /**
     * Método para transformar el par en un string.
     *
     * @return Representación en string del par.
     */
    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

}
