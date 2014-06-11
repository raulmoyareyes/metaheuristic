/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.metaheuristicas.pr1.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 * @param <A>
 * @deprecated no comentada
 */
public class Matrix<A> {

    private List<List<A>> cell;

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

    public A get(int row, int column) {
        return this.cell.get(row).get(column);
    }

    public void add(int row, int column, A data) {
        this.cell.get(row).set(column, data);
    }
}
