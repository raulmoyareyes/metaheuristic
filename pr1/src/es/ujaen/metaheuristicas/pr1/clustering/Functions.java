/**
 * En este paquete se encuentra las clases necesarias para gestionar problemas
 * de clustering.
 */
package es.ujaen.metaheuristicas.pr1.clustering;

import es.ujaen.metaheuristicas.pr1.utils.Pair;
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
        /* Recorre los clusters */
        for (int i = 0; i < clusters.size(); i++) {
            /* Recorre los patrones de cada cluster */
            Cluster cluster = clusters.get(i);
            for (int j = 0; j < cluster.size(); j++) {
                cost += distance(centroids.get(i), cluster.get(j));
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
     * Método para calcular la distancia entre dos patrones.
     *
     * @param first Un patrón con el que comparar (corresponde al centroide).
     * @param second Un patrón con el que comparar (corresponde al patrón).
     */
    private static Float distance(Pattern first, Pattern second) {
        /* Distancia entre el patrón y el centroide */
        float add = 0;
        for (int k = 0; k < second.size(); k++) {
            /* Resta de la distancia de cada componente entre el patrón y el centroide */
            float sub = second.get(k) - first.get(k);
            add += sub * sub;
        }
        return add;
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
            centroids.add(calculateCentroid(c.get()));
        }
        return centroids;
    }

    /**
     * Método que calcula el centroide de un cluster.
     *
     * @param patterns List de {@link Pattern} para calcular un centroide.
     * @return {@link Pattern} que representan el centroide de un cluster.
     */
    public static Pattern calculateCentroid(List<Pattern> patterns) {

        Pattern centroid = new Pattern();
        /* Recorre cada patrón sumando cada dimension y realizando la media */
        for (int i = 0; patterns.size() > 0 && i < patterns.get(0).size(); i++) {
            float avg = 0;
            for (Pattern p : patterns) {
                avg += p.get(i);
            }
            centroid.add(avg / patterns.size());
        }

        return centroid;
    }

    /**
     * Método para asignar los patrones a los clusters de forma aleatoria.
     *
     * @param patterns Todos los patrones para asignarlos a los clusters.
     * @param rand Números aleatorios a partir de una semilla.
     * @param numberClusters Número de clusters que se van a crear.
     * @return List de {@link Cluster} con todos los patrones asignados.
     */
    public static List<Cluster> setRandom(List<Pattern> patterns, Random rand, Integer numberClusters) {
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
     * Método para asignar los patrones a los clusters utilizando un algoritmo
     * greedy.
     *
     * @param patterns Todos los patrones para asignarlos a los clusters.
     * @param rand Números aleatorios a partir de una semilla.
     * @param numberClusters Número de clusters a generar.
     * @param threshold Umbral de los candidatos de la lista restringida para la
     * construcción de la solución greedy.
     * @return List de {@link Cluster} con todos los patrones asignados.
     */
    public static List<Cluster> setGreedy(List<Pattern> patterns, Random rand, Integer numberClusters, Double threshold) {
        /* Crea una copia de la lista de patrones */
        List<Pattern> restPattern = new ArrayList(patterns);
        /* Inicializa los clusters y los centroides */
        List<Cluster> clusters = new ArrayList<>();
        List<Pattern> centroids = new ArrayList<>();

        /* Calcula el centroide de cada cluster */
        for (int i = 0; i < numberClusters; i++) {
            /* Crea la lista restringida con las posiciones de los patrones en restPattern */
            List<Integer> restrictedList = createRestrictedList(restPattern, threshold);
            /* Selecciona una posición aleatoria de la lista restringida */
            int positionCandidate = restrictedList.remove(rand.nextInt(restrictedList.size()));
            /* Asigna el patrón a un cluster */
            Pattern candidate = restPattern.remove(positionCandidate);
            clusters.add(new Cluster());
            clusters.get(i).add(candidate);
            centroids.add(i, candidate);
        }

        /* Asigna el resto de patrones al cluster que menos distancia tenga */
        for (int j = 0; j < restPattern.size(); j++) {
            float distance = 0;
            int assigned = 0;
            for (int i = 0; i < centroids.size(); i++) {
                float actualDistance = distance(centroids.get(i), restPattern.get(j));
                if (actualDistance < distance || distance == 0) {
                    distance = actualDistance;
                    assigned = i;
                }
            }
            clusters.get(assigned).add(restPattern.remove(j));
            j--;
        }

        return clusters;
    }

    /**
     * Método para crear la lista restringida de la construcción de la solución
     * inicial greedy.
     *
     * @param patterns Lista de patrones con los que crear la lista restringida.
     * @param threshold Umbral de los candidatos de la lista restringida.
     * @return List de {@link Pattern} que representa la lista restringida.
     */
    private static List<Integer> createRestrictedList(List<Pattern> patterns, Double threshold) {
        /* Busca el patrón más cercano al centroide */
        Pattern centroid = calculateCentroid(patterns);
        List<Float> distances = distances(patterns, centroid);
        Pattern nearest = nearest(patterns, distances);
        /* Busca los cercanos a nearest que estén por debajo del umbral */
        List<Integer> near = near(patterns, nearest, threshold);

        return near;
    }

    /**
     * Método para buscar el patrón con menos distancia.
     *
     * @param patterns Lista de patrones para obtener el de menor distancia.
     * @param distances Distancia de cada patrón.
     * @return El patrón con menos distancia.
     */
    private static Pattern nearest(List<Pattern> patterns, List<Float> distances) {

        Pattern nearest = null;
        float distance = distances.get(0);
        for (int i = 0; i < patterns.size(); i++) {
            if (distances.get(i) < distance) {
                distance = distances.get(i);
                nearest = patterns.get(i);
            }
        }
        return nearest;
    }

    /**
     * Método para buscar una lista de posiciones de patrones cercanos al
     * centroide.
     *
     * @param patterns Lista de patrones con los que crear la lista de
     * posiciones de patrones cercanos.
     * @param centroid Centroide para calcular la distancia.
     * @param threshold Factor para calcular el umbral.
     * @return Lista de posiciones de los patrones que entran en la lista de
     * cercanos.
     */
    private static List<Integer> near(List<Pattern> patterns, Pattern centroid, Double threshold) {
        List<Float> distances = distances(patterns, centroid);
        double tolerance = calculateTolerance(distances, threshold);
        List<Integer> near = new ArrayList();
        for (int i = 0; i < distances.size(); i++) {
            if (tolerance > distances.get(i)) {
                near.add(i);
            }
        }
        return near;
    }

    /**
     * Método para calcular el umbral de patrones aceptados en la lista
     * restringida.
     *
     * @param distances Distancia de cada patrón.
     * @param threshold Factor para calcular el umbral.
     * @return Double que representa el umbral para aceptar patrones en la lista
     * restringida.
     */
    private static Double calculateTolerance(List<Float> distances, Double threshold) {
        float best = 0;
        float worst = 0;
        for (Float distance : distances) {
            if (distance > best || best == 0) {
                best = distance;
            } else if (distance < worst || worst == 0) {
                worst = distance;
            }
        }
        return best - threshold * worst;
    }

    /**
     * Método para calcular la distancia entre los patrones y el centroide.
     *
     * @param patterns Lista de patrones.
     * @param centroid Centroide con el que calcular las distancias.
     * @return List de float con la distancia de cada patrón en el mismo orden.
     */
    private static List<Float> distances(List<Pattern> patterns, Pattern centroid) {
        List<Float> distances = new ArrayList();

        for (Pattern p : patterns) {
            distances.add(distance(centroid, p));
        }
        return distances;
    }

    /**
     * @deprecated no implementada
     */
    public static List<Pair<Integer, Integer>> createListTabu() {
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

    /**
     * Método para imprimir en consola el resultado de una ejecución.
     *
     * @param fileName Nombre del dataset utilizado.
     * @param patterns List de patrones utilizados.
     * @param clusters list de clusters utilizados con los patrones asignados a
     * cada uno.
     * @param centroids List de patrones que representa los centroides de cada
     * cluster.
     * @param seed Semilla utilizada.
     * @param initialCost Coste de la solución inicial.
     * @param finalCost Coste de la solución tras aplicar el algoritmo.
     * @param time Tiempo de ejecución del algoritmo.
     */
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
        System.out.println("Coste inicial: " + initialCost);
        System.out.println("===================== Coste final: " + finalCost + " ======================");
        System.out.println("\n");

    }
}
