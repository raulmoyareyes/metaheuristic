/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr2a.clustering;

import es.ujaen.metaheuristicas.pr2a.utils.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Chromosome {
    
    List<Pair<Pattern, Cluster>> chromosomes;
    
    public Chromosome(){
        this.chromosomes = new ArrayList();
    }
    
    public boolean add() {
        return true;
    }
}
