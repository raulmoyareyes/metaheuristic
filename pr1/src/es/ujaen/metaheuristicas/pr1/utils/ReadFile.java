/**
 * En este paquete se encuentra las clases utiles para realizar distintas tareas
 * como lectura de ficheros, gestión de datos, etc.
 */
package es.ujaen.metaheuristicas.pr1.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase para leer archivos.
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 */
public class ReadFile {

    /* File */
    private File file = null;
    /* File readers */
    private FileReader fr = null;
    /* Buffered Reader */
    private BufferedReader br = null;

    /**
     * Constructor con parametros. Inicializa los atributos y crea los objetos
     * necesarios para leer el archivo que se le indica.
     *
     * @param fileName Ruta del archivo
     */
    public ReadFile(String fileName) {

        try {
            this.file = new File(fileName);
            this.fr = new FileReader(file);
            this.br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("An error has ocurred during file opening. Error message: " + e.toString());
            this.closeFile();
        }

    }

    /**
     * Método para cerrar el archivo.
     */
    private void closeFile() {
        try {
            if (null != fr) {
                this.fr.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred during the file closing. Error message: " + e.toString());
        }

    }

    /**
     * Método para leer una nueva línea. Cada vez que es llamado lee la
     * siguiente línea del archivo.
     *
     * @return String con siguiente línea del archivo.
     */
    public String readLine() {

        String line = null;
        try {
            if ((line = this.br.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            System.out.println("An error has occurred during the line reading. Error message: " + e.toString());
            this.closeFile();
        }
        return line;
    }
}
