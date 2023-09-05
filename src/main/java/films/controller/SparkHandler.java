package films.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que almacena lso servicios creados a partir de funciones lambda
 * @author Isabella Manrique
 * @version 5/09/2023/A
 */
public class SparkHandler {

    // Variable de la clase usada para almacenar los servicios
    private static final Map<String, StringService> serviciosGET = new HashMap<>();
    private static final Map<String, StringService> serviciosPOST = new HashMap<>();

    /**
     * Metodo usado para buscar servicios creados
     * @param nombre Nombre del servicio a buscar
     * @return Interfaz funcional del servicio
     */
    public static StringService buscar(String nombre, String verbo) {
        if (verbo.equals("GET")){
            return serviciosGET.get(nombre);
        } else{
            return serviciosPOST.get(nombre);
        }
    } // Cierre del metodo

    /**
     * Metodo usado para crear servicios a partir de funciones lambda
     * @param str Nombre del servicio a crear
     * @param service Interfaz funcional del servicio
     */
    public static void get(String str, StringService service){
        serviciosGET.put(str, service);
    } // Cierre del metodo

    public static void post(String str, StringService service){
        serviciosPOST.put(str, service);
    }
} // Cierre de la clase
