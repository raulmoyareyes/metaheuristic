/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.metaheuristicas.pr1.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para simular una matriz.
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 *
 * @param <A> Tipo de objeto que almacena la matriz.
 * @deprecated Puede no funcionar con cualquier tipo de objeto. Revisar el
 * constructor.
 */
public class Matrix<A> {

    private final List<List<A>> cell;

    /**
     * Constructor para inicializar la matriz a un número de filas y columnas.
     *
     * @param rows Filas de la matriz.
     * @param columns Columnas de la matriz.
     */
    public Matrix(int rows, int columns) {
        this.cell = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<A> row = new ArrayList<>(columns);
            for (int j = 0; j < columns; j++) {
                row.add((A) new Integer(0));
            }
            this.cell.add(row);
        }
    }

    /**
     * Método para obtener el contenido de una posición concreta dentro de la
     * matriz
     *
     * @param row Posición de la fila.
     * @param column Posición de la columna.
     * @return Objeto almacenado en la posición indicada con la fila y columna.
     */
    public A get(int row, int column) {
        return this.cell.get(row).get(column);
    }

    /**
     * Método para añadir un objeto en una fila y columna concreta.
     * @param row Posición de la fila.
     * @param column Posición de la columna.
     * @param data Objeto a insertar en esa posición.
     */
    public void add(int row, int column, A data) {
        this.cell.get(row).set(column, data);
    }
}
