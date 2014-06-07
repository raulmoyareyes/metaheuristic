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

    /**
     * Método para calcular el coste de una solución.
     *
     * @param clusters List de {@link Cluster} con los patrones asignados.
     * @param centroids List de {@link Pattern} representando los centroides de
     * los clusters.
     * @return Float con el coste de la solución.
     */
    public static Float objectiveFunction(List<Cluster> clusters, List<Pattern> centroids) {

        /* Coste de la solución */
        float cost = 0;
        /* Resta de la distancia de cada componente entre el patrón y el centroide */
        float sub = 0;
        /* Distancia entre el patrón y el centroide */
        float add = 0;
        /* Recorre los clusters */
        for (int i = 0; i < clusters.size(); i++) {
            /* Recorre los patrones de cada cluster */
            Cluster cluster = clusters.get(i);
            for (int j = 0; j < cluster.size(); j++) {
                /* Recorre los datos de cada patrón */
                Pattern pattern = cluster.get(j);
                for (int k = 0; k < pattern.size(); k++) {
                    sub = pattern.get(k) - centroids.get(i).get(k);
                    add += sub * sub;
                }
                cost += add;
                add = 0;
            }
        }

        return cost;
    }

    /**
     * Método para calcular la mejora moviendo un patrón.
     *
     * @param clusters List de {@link Cluster} con todos los patrones asignados.
     * @param centroids List de {@link Pattern} que representan los centroides
     * de los clusters.
     * @param assigned Posición del cluster en el listado de cluster al que se
     * va a asignar el patrón indicado.
     * @param i Posición del cluster en el listado de cluster del que se va a
     * eliminar el patrón indicado.
     * @param j Posición del patrón que se va a mover.
     * @return Float con la mejora que produce el cambio del patrón.
     */
    public static Float factorize(List<Cluster> clusters, List<Pattern> centroids,
            int assigned, int i, int j) {

        /* Patrón que se va a mover */
        Pattern pattern = clusters.get(i).get(j);
        /* Centroide del cluster de origen */
        Pattern centroid = centroids.get(i);
        /* Centroide del cluster al que se va a asignar el patrón */
        Pattern centroidAssigned = centroids.get(assigned);
        /* Cálculo de la distancia a los dos centroides */
        Float add = 0f;
        Float addAssigned = 0f;
        for (int k = 0; k < pattern.size(); k++) {
            Float sub = pattern.get(k) - centroid.get(k);
            add += sub * sub;
            sub = pattern.get(k) - centroidAssigned.get(k);
            addAssigned += sub * sub;
        }
        /* Mejora que se produce al mover el patrón de cluster */
        Float objective = -1 * ((clusters.get(i).size() * add) / clusters.get(i).size())
                + ((clusters.get(assigned).size() * addAssigned) / clusters.get(assigned).size());

        return objective;
    }

    /**
     * Método que calcula los centroides de los clusters.
     *
     * @param clusters List de {@link Cluster} con todos los patrones asignados.
     * @return List de {@link Pattern} que representan los centroides de los
     * clusters.
     */
    public static List<Pattern> calculateCentroids(List<Cluster> clusters) {
        /* ArrayList para crear los centroides de todos los clusters */
        List<Pattern> centroids = new ArrayList<>();
        /* Recorre los clusters */
        for (Cluster c : clusters) {
            Pattern centroid = new Pattern();
            /* Recorre cada patrón sumando cada dimension y realizando la media */
            for (int i = 0; c.size() > 0 && i < c.get(0).size(); i++) {
                float avg = 0;
                for (Pattern p : c) {
                    avg += p.get(i);
                }
                centroid.add(avg / c.size());
            }
            centroids.add(centroid);
        }
        return centroids;
    }

    /**
     *
     * @param clusters
     * @param seed
     * @return
     * @deprecated No implementada.
     */
    public static List<Pattern> calculateCentroids(List<Cluster> clusters, Integer seed) {
        /*
        Solucion ={}
        repetir mientras(no se haya construido){
            crear lista restringida
            s<- seleccion aleatoria de un elemento de la lista restringida
            Solucion <- Solucion+s
        devolver S
        
        alpha = 0.3 umbral
         */

        List<Pattern> restrictedList = new ArrayList();

        return new ArrayList<>();
    }

    /**
     * Método para asignar los patrones a los clusters de forma aleatoria.
     *
     * @param patterns Todos los patrones para asignarlos a los clusters.
     * @param seed Semilla para generar los números aleatorios.
     * @param numberClusters Número de clusters que se van a crear.
     * @return List de {@link Cluster} con todos los patrones asignados.
     */
    public static List<Cluster> setRandom(List<Pattern> patterns, Integer seed, int numberClusters) {
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
     *
     * @param patterns
     * @param numberClusters
     * @return
     * @deprecated No está implementada
     */
    public static List<Cluster> setGreedy(List<Pattern> patterns, int numberClusters) {
        return new ArrayList();
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

    
    public static void print(String fileName, List<Pattern> patterns,
            List<Cluster> clusters, List<Pattern> centroids, Integer seed,
            Float initialCost, Float finalCost, Long time) {
        System.out.println("======================================================================\n"
                + "    " + fileName + "\n"
                + "======================================================================");
        System.out.println("Lectura del dataset " + fileName);
        System.out.println("Número de patrones: " + patterns.size());
        System.out.println("Dimensión de los patrones: " + patterns.get(0).size());
        System.out.println("Número de clusters: " + clusters.size());
        System.out.println("Semilla: " + seed);
        System.out.println("Tiempo de ejecución: " + time + " milisegundos");
//        System.out.println("Número de centroides: " + centroids.size());
//        System.out.println("    Centroide inicial 1: " + centroids.get(0).toString());
//        System.out.println("    Centroide inicial 2: " + centroids.get(1).toString());
//        System.out.println("    Coste inicial: " + initialCost);
        System.out.println("===================== Coste final: " + finalCost + " ======================");
        System.out.println("\n");

    }
}
