package es.ujaen.metaheuristicas.pr3.clustering;

import java.util.Random;

/**
 *
 * @author Raúl Moya Reyes <www.raulmoya.es>
 */
public class Functions {

    /**
     * Calcula la distancia entre un patrón y un cluster.
     *
     * @param pattern
     * @param chromosome
     * @param cluster
     * @return null
     * @deprecated
     */
    public static float distance(Pattern pattern, Chromosome chromosome, int cluster) {
        float distance = 0;
        for (int i = 0; i < pattern.dimensions.length; i++) {
            float pow = -pattern.dimensions[i] + chromosome.clusters[cluster].centroid[i];
            distance += (pow * pow);
        }
        return distance;
    }

    /**
     *
     * @param patterns
     * @param chromosome
     * @deprecated
     */
    public static void objectiveFunction(Pattern[] patterns, Chromosome chromosome) {
        chromosome.objective = 0;
        for (Pair pair : chromosome.pairs) {
            float aux = distance(patterns[pair.pattern], chromosome, pair.cluster);
            chromosome.objective += aux;
        }
    }

    /**
     *
     * @param chromosomes
     * @param patterns
     * @param rand
     * @deprecated
     */
    public static void mutation(Chromosome[] chromosomes, Pattern[] patterns, Random rand) {
        int randC = rand.nextInt(chromosomes.length);

        int gen1 = rand.nextInt(chromosomes[randC].pairs.length);
        int gen2 = rand.nextInt(chromosomes[randC].pairs.length);

        while (gen1 == gen2 || chromosomes[randC].pairs[gen1].cluster == chromosomes[randC].pairs[gen2].cluster) {
            gen1 = rand.nextInt(chromosomes[randC].pairs.length);
            gen2 = rand.nextInt(chromosomes[randC].pairs.length);
        }

        Pair aux1 = chromosomes[randC].pairs[gen1];
        Pair aux2 = chromosomes[randC].pairs[gen2];
        if (chromosomes[randC].clusters[aux1.cluster].size > 1 && chromosomes[randC].clusters[aux2.cluster].size > 1) {
            //Se intercambian los cluster.
            int aux = aux1.cluster;
            chromosomes[randC].pairs[gen1].cluster = chromosomes[randC].pairs[gen2].cluster;
            chromosomes[randC].pairs[gen2].cluster = aux;

            //Se calculan los centroides de los cluster al añadir los patrones.           
            float clus1[] = Functions.calculateCentroids(patterns, chromosomes, randC, aux1.cluster);
            float clus2[] = Functions.calculateCentroids(patterns, chromosomes, randC, aux2.cluster);
            chromosomes[randC].clusters[aux1.cluster].centroid = clus1;
            chromosomes[randC].clusters[aux2.cluster].centroid = clus2;
        }
    }

    /**
     *
     * @param patterns
     * @param numClus
     * @param population
     * @param rand
     * @return null
     * @deprecated
     */
    public static Chromosome[] setInitial(Pattern[] patterns, int numClus, int population, Random rand) {
        Chromosome[] chromosomes = new Chromosome[population];

        for (int i = 0; i < chromosomes.length; i++) {
            chromosomes[i] = new Chromosome(patterns.length, numClus);
        }

        for (Chromosome chromosome : chromosomes) {
            int pospar = 0;
            boolean assigned[] = new boolean[patterns.length];
            //Asigna los n patrones desde el aleatorio como centros de los cluster.
            for (int c = 0; c < numClus; c++) {
                int p = rand.nextInt(patterns.length);
                while (assigned[p]) {
                    p = rand.nextInt(patterns.length);
                }
                float centroid[] = new float[patterns[p].size];
                System.arraycopy(patterns[p % patterns.length].dimensions, 0, centroid, 0, patterns[p].dimensions.length);
                chromosome.clusters[c] = new Cluster();
                chromosome.clusters[c].centroid = centroid;
                chromosome.clusters[c].size++;
                Pair aux = new Pair();
                aux.pattern = p;
                aux.cluster = c;
                chromosome.pairs[pospar] = aux;
                assigned[p] = true;
                pospar++;
            }
            //Busca el más cercano.
            for (int i = 0; i < patterns.length; i++) {
                int pattern = i;
                if (!assigned[i]) {
                    float dist = Float.MAX_VALUE;
                    int cluster = -1;
                    for (int c = 0; c < numClus; c++) {
                        float distance = 0;
                        distance = distance(patterns[pattern], chromosome, c);
                        if (distance < dist && cluster != -1) {
                            cluster = c;
                            dist = distance;
                        }
                        if (cluster == -1) {
                            cluster = c;
                            dist = distance;
                        }
                    }
                    //Asignación del patrón al cluster mas cercano.
                    Pair aux = new Pair();
                    aux.pattern = pattern;
                    aux.cluster = cluster;
                    chromosome.pairs[pospar] = aux;
                    pospar += 1 % patterns.length;
                    assigned[i] = true;
                    //Recalculo del centroide.
                    for (int d = 0; d < patterns[0].size; d++) {
                        //Sin factorización.
                        float centroid = 0;
                        int size = 0;
                        for (int pat = 0; pat < pospar; pat++) {
                            if (chromosome.pairs[pat].cluster == cluster) {
                                centroid += patterns[chromosome.pairs[pat].pattern].dimensions[d];
                                size++;
                            }
                        }
                        chromosome.clusters[cluster].centroid[d] = centroid / size;
                        chromosome.clusters[cluster].size = size;
                    }
                }
            }
            objectiveFunction(patterns, chromosome);
        }
        return chromosomes;
    }

    /**
     *
     * @param chromosomes
     * @param rand
     * @return null
     * @deprecated
     */
    public static int[] selection(Chromosome chromosomes[], int size, Random rand) {
        int fathers[] = new int[size];
        for (int i = 0; i < size; i++) {
            int f1 = rand.nextInt(chromosomes.length);
            int f2 = rand.nextInt(chromosomes.length);
            if (f1 == f2) {
                while (f1 == f2) {
                    f2 = rand.nextInt(chromosomes.length);
                }
            }
            if (chromosomes[f1].objective < chromosomes[f2].objective) {
                fathers[i] = f1;
            } else {
                fathers[i] = f2;
            }
        }
        return fathers;
    }
    
    public static float factorize(Pattern pattern, float[] centroid, float[] newCentroid){
        float distance = 0;
        float newDistance = 0;
        for (int i = 0; i < pattern.dimensions.length; i++) {
            float pow = pattern.dimensions[i] - centroid[i];
            distance += (pow * pow);
            pow = pattern.dimensions[i] - newCentroid[i];
            newDistance += (pow * pow);
        }
        return ((-1*centroid.length/centroid.length-1)*distance)+((newCentroid.length/newCentroid.length)*newDistance);
    }

    /**
     *
     * @param rand
     * @param chromosomes
     * @param f1
     * @param f2
     * @param patterns
     * @return null
     * @deprecated
     */
    public static Chromosome[] crossing(Pattern[] patterns, Chromosome[] chromosomes, Random rand, int f1, int f2) {
        int lowerCut = rand.nextInt(chromosomes[f1].pairs.length);
        int upperCut = rand.nextInt(chromosomes[f1].pairs.length);
        Chromosome children[] = new Chromosome[2];

        for (int h = 0; h < 2; h++) {
            children[h] = new Chromosome(chromosomes[f1].patternsSize, chromosomes[f1].clustersSize);
        }

        if (lowerCut > upperCut) {
            int aux = lowerCut;
            lowerCut = upperCut;
            upperCut = aux;
        }
        
        boolean son[] = new boolean[chromosomes[f1].patternsSize];
        boolean daughter[] = new boolean[chromosomes[f2].patternsSize];
        
        // Añade el corte al son        
        for (int i = lowerCut; i <= upperCut; i++) {
            son[chromosomes[f1].pairs[i].pattern] = true;
            children[0].pairs[i] = new Pair(chromosomes[f1].pairs[i]);

        }
        // Añade el resto del f2
        int pospar = 0;
        for (int i = 0; i < children[0].pairs.length; i++) {
            Pair aux = chromosomes[f2].pairs[i];
            if (!son[aux.pattern]) {
                if (pospar == lowerCut) {
                    pospar = upperCut + 1;
                }

                children[0].pairs[pospar] = new Pair(aux);
                pospar++;
            }
        }
        
        // Añade el corte al daughter        
        for (int i = lowerCut; i <= upperCut; i++) {
            daughter[chromosomes[f2].pairs[i].pattern] = true;
            children[1].pairs[i] = new Pair(chromosomes[f2].pairs[i]);

        }
        // Añade el resto del f1
        pospar = 0;
        for (int i = 0; i < children[1].pairs.length; i++) {
            Pair aux = chromosomes[f1].pairs[i];
            if (!daughter[aux.pattern]) {
                if (pospar == lowerCut) {
                    pospar = upperCut + 1;
                }

                children[1].pairs[pospar] = new Pair(aux);
                pospar++;
            }
        }

        //Crea los cluster.        
        for (int i = 0; i < 2; i++) {
            for (int c = 0; c < children[i].clusters.length; c++) {
                children[i].clusters[c] = new Cluster();
                children[i].clusters[c].centroid = new float[patterns[0].size];
                for (int d = 0; d < patterns[0].dimensions.length; d++) {
                    float centroid = 0;
                    int size = 0;
                    for (int pair = 0; pair < patterns.length; pair++) {

                        if (children[i].pairs[pair].cluster == c) {
                            centroid += patterns[children[i].pairs[pair].pattern].dimensions[d];
                            size++;
                        }
                    }
                    children[i].clusters[c].centroid[d] = centroid / (float) size;
                    children[i].clusters[c].size = size;
                }
            }
        }
        return children;
    }

    /**
     * Recalcula el centroide de un cluster concreto.
     *
     * @param patterns
     * @param chromosomes
     * @param pob
     * @param cluster
     * @return null
     * @deprecated
     */
    public static float[] calculateCentroids(Pattern[] patterns, Chromosome[] chromosomes, int pob, int cluster) {
        float centroids[] = new float[patterns[0].dimensions.length];

        for (int d = 0; d < centroids.length; d++) {
            int size = 0;
            float centroid = 0;
            for (int i = 0; i < patterns.length; i++) {
                if (chromosomes[pob].pairs[i].cluster == cluster) {
                    centroid += patterns[chromosomes[pob].pairs[i].pattern].dimensions[d];
                    size++;
                }
            }
            centroids[d] = centroid / (float) size;
        }
        return centroids;
    }
    
    /**
     * 
     * @param chromosomes
     * @return null
     * @deprecated 
     */
    public static float bestSolution(Chromosome[] chromosomes){
        
        float best = Float.MAX_VALUE;
        for (Chromosome chromosome : chromosomes) {
            if (chromosome.objective < best) {
                best = chromosome.objective;
            }
        }
        return best;
    }
    
    /**
     * 
     * @param chromosomes
     * @return null
     * @deprecated 
     */
    public static int bestPosition(Chromosome[] chromosomes){
        int position = 0;
        float best = Float.MAX_VALUE;
        for (int i = 0; i<chromosomes.length; i++) {
            if (chromosomes[i].objective < best) {
                best = chromosomes[i].objective;
                position = i;
            }
        }
        return position;
    }
    
    public static int worstPosition(Chromosome[] chromosomes){
        int position = 0;
        float worst = Float.MAX_VALUE;
        for (int i = 0; i<chromosomes.length; i++) {
            if (chromosomes[i].objective > worst) {
                worst = chromosomes[i].objective;
                position = i;
            }
        }
        return position;
    }

    /**
     * Método para cargar un dataset en un List de {@link Pattern}.
     *
     * @param fileName Ruta del archivo.
     * @param size
     * @return List de {@link Pattern} con todos los patrones del dataset
     * indicado en el fileName.
     */
    public static Pattern[] readData(String fileName, int size) {
        /* ArrayList con todos los patrones del dataset */
        Pattern[] patterns = new Pattern[size];
        ReadFile file = new ReadFile("src/es/ujaen/metaheuristicas/pr3/instances/" + fileName);
        String line;
        int pos = 0;
        while ((line = file.readLine()) != null) {
            /* División de la línea por espacios que corresponde con cada dato del patrón */
            String[] datas = line.split(" ");
            /* Parse de string a Pattern */
            Pattern p = new Pattern(datas.length);
            for (int j = 0; j < datas.length; j++) {
                p.dimensions[j] = Float.parseFloat(datas[j]);
            }
            /* Añadido el patrón al listado de patrones */
            patterns[pos] = p;
            pos++;
        }

        return patterns;
    }

    /**
     * Método para imprimir en consola el resultado de una ejecución.
     *
     * @param fileName Nombre del dataset utilizado.
     * @param patterns List de patrones utilizados.
     * @param chromosomes 
     * @param seed Semilla utilizada.
     * @param initialCost Coste de la solución inicial.
     * @param finalCost Coste de la solución tras aplicar el algoritmo.
     * @param generation
     * @param time Tiempo de ejecución del algoritmo.
     */
    public static void print(String fileName, Pattern[] patterns, Chromosome[] chromosomes,
            Integer seed, Float initialCost, Float finalCost, Integer generation, Long time) {
        System.out.println("======================================================================\n"
                + "    " + fileName + "\n"
                + "======================================================================");
        System.out.println("Lectura del dataset " + fileName);
        System.out.println("Número de patrones: " + patterns.length);
        System.out.println("Dimensión de los patrones: " + patterns[0].size);
//        System.out.println("Número de clusters: " + clusters.size());
        System.out.println("Semilla: " + seed);
        System.out.println("Tiempo de ejecución: " + time + " milisegundos");
//        System.out.println("Número de centroides: " + centroids.size());
//        System.out.println("    Centroide inicial 1: " + centroids.get(0).toString());
//        System.out.println("    Centroide inicial 2: " + centroids.get(1).toString());
        System.out.println("Coste inicial: " + initialCost);
        System.out.println("Número de generaciones: " + generation);
        System.out.println("===================== Coste final: " + finalCost + " ======================");
        System.out.println("\n");

    }
}
