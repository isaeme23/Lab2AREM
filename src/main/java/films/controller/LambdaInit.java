package films.controller;

import films.inter.MovieResponse;
import java.io.IOException;
import static films.controller.SparkHandler.*;
import static films.controller.HttpServer.*;

/**
 * Clase que inicia las funciones lambda utilizadas
 * @author Isabella Manrique
 * @version 5/09/2023/A
 */
public class LambdaInit {

    /**
     * Metodo principal de la clase
     * @param args Arreglo de String
     * @throws IOException Excepcion de Entrada/Salida
     */
    public static void main(String[] args) throws IOException {
        get("/movie", (str, cli) -> {
            MovieResponse.getMovie(cli, str);
        });

        post("/movie", ((str, client) -> {

        }));

        start();
    } // Cierre del metodo
} // Cierre de la clase
