/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr1.clustering;

import es.ujaen.metaheuristicas.pr1.utils.ReadFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Functions {

    //calcuar coste desde cero
    public static Float objectiveFunction(List<Cluster> clusters, List<Pattern> centroids) {
        return 2.2f;
    }

    //calcular el coste factorizando sin tener que calcularlo desde cero
    public static Float factorize() {
        return 12.0f;
    }

    //calcular centroides desde cero
    public static List<Pattern> calculateCentroids(List<Cluster> clusters) {
        /* ArrayList para crear los centroides de todos los clusters */
        List<Pattern> centroids = new ArrayList<>();
        for (Cluster c : clusters) {
            Pattern centroid = new Pattern();
            for (int i = 0; i < c.get(0).size(); i++) {
                Float avg = 0f;
                for (Pattern p : c) {
                    avg += p.get(i);
                }
                centroid.add(avg);
            }
            centroids.add(centroid);
        }
        return centroids;
    }

    //calcular centoides a partir de los anteriores
    public static void calculateCentroids(int i) {

    }

    /**
     * Método para asignar los patrones a los clusters de forma aleatoria.
     *
     * @param patterns Todos los patrones para asignarlos a los clusters.
     * @param seed Semilla para generar los números aleatorios.
     * @param numberClusters Número de clusters que se van a crear.
     * @return List de {@link Cluster} con todos los patrones asignados.
     */
    public static List<Cluster> setRandom(List<Pattern> patterns, Long seed, int numberClusters) {
        /* Números aleatorios a partir de una semilla */
        Random rand = new Random(seed);
        /* ArrayList para asignar todos los patrones a los clusters */
        List<Cluster> clusters = new ArrayList();
        /* Inicialización del número de cluster */
        for (int i = 0; i < numberClusters; i++) {
            clusters.add(new Cluster());
        }
        /* Asignación de patrones a los clusters */
        for (Pattern p : patterns) {
            /* Número aleatorio entre 0 y número de clusters (no incluido) */
            int position = rand.nextInt(numberClusters);
            /* Asignación del patrón p al cluster en la posicion indicada */
            clusters.get(position).add(p);
        }

        return clusters;
    }

    /**
     * Método para cargar un dataset en un List de {@link Pattern}.
     *
     * @param fileName Ruta del archivo.
     * @return List de {@link Pattern} con todos los patrones del dataset
     * indicado en el fileName.
     */
    public static List<Pattern> readData(String fileName) {
        /* ArrayList con todos los patrones del dataset */
        List<Pattern> patterns = new ArrayList<>();
        try {
            /* Url relativa al paquete */
            fileName = URLDecoder.decode(Functions.class.getResource("../instances/" + fileName).getFile(), "UTF-8");
            /* Archivo con los datos */
            ReadFile file = new ReadFile(fileName);
            /* Líneas del archivo que corresponden con cada patrón */
            String line;
            while ((line = file.readLine()) != null) {
                /* División de la línea por espacios que corresponde con cada dato del patrón */
                String[] datas = line.split(" ");
                /* Parse de string a Pattern */
                Pattern p = new Pattern();
                for (String data : datas) {
                    p.add(Float.parseFloat(data));
                }
                /* Añadido el patrón al listado de patrones */
                patterns.add(p);
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("An error has ocurred with encoding URL. Error message: " + ex.toString());
        }

        return patterns;
    }

}
