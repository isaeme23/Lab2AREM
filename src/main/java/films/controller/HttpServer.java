package films.controller;

import films.inter.Mediatory;
import films.inter.MovieResponse;
import films.inter.impl.ImageMediatory;
import films.inter.impl.TextMediatory;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

import static films.controller.SparkHandler.buscar;

/**
 * Clase que funciona como servidor HTTP
 * @author Isabella Manrique
 * @version 5/09/2023/A
 */

public class HttpServer {

    static Map<String, StringService> servicios = new HashMap<>();

    private static Mediatory mediatory;

    /**
     * Metodo principal de la clase
     * @throws IOException Excepcion de entrada/salida
     */

    public static void start() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;

            boolean firstLine = true;
            String path = null;


            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    path = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }


            if(path.contains("?")) {
                buscar("/movie", "GET").response(path.split("=")[1], clientSocket);
            }else if (path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js")) {
                mediatory = new TextMediatory(path, clientSocket);
                mediatory.reply();
            } else if (path.endsWith(".jpeg") || (path.endsWith(".jpg")) || path.endsWith(".gif")){
                mediatory = new ImageMediatory(path, clientSocket);
                mediatory.reply();
            } else {
                System.out.println("Otra extension xd");
            }
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    } // Cierre del metodo


} // Cierre de la clase