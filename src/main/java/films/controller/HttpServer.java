package films.controller;

import films.service.AREMflixService;

import java.net.*;
import java.io.*;

/**
 * Clase que funciona como servidor HTTP
 * @author Isabella Manrique
 * @version 22/08/2023/A
 */

public class HttpServer {

    // Variables de la clase
    private AREMflixService as;

    /**
     * Metodo principal de la clase
     * @param args Arreglo de argumentos
     * @throws IOException Excepcion de entrada/salida
     */

    public static void main(String[] args) throws IOException {
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

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean firstLine = true;
            String path = null;

            String name = "";

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    path = inputLine.split(" ")[1];
                }
                if (inputLine.contains("GET") && inputLine.contains("=")){
                    name = inputLine.split("=")[1].split(" ")[0];
                    System.out.println("Nombre: "+name);
                }
                if (!in.ready()) {
                    break;
                }
            }

            outputLine = "HTTP/1.1 200 OK \r\n";


            if (path.startsWith("/"+name) && name != ""){
                outputLine += getHello("/"+name);
            } else {
                outputLine += getResponse();
            }


            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    } // Cierre del metodo

    /**
     * Metodo usado para devolver un mensaje
     * @param path Path de la pagina en donde se encuentra el mensaje
     * @return Mensaje "Hello World"
     */

    public static String getHello(String path){
        String response = "Content-Type: text/html \r\n"
                + "\r\n" + "{\"msg\": \"Hello World!\"}";
        return response;
    } // Cierre del metodo

    public static String sendName(String name){
        return name;
    }

    /**
     * Metodo usado para mostrar en formato http el contenido de la pagina acompañado de scripts para las
     * diferentes funcionalidades del mismo
     * @return Respuesta GET/POST
     */

    public static String getResponse(){
        String response = "Content-Type: text/html \r\n"
                + "\r\n <!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form with GET</h1>\n" +
                "        <form action=\"/movie\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/movie?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "        <h1>Form with POST</h1>\n" +
                "        <form action=\"/moviepost\">\n" +
                "            <label for=\"postname\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
                "        </form>\n" +
                "        \n" +
                "        <div id=\"postrespmsg\"></div>\n" +
                "        \n" +
                "        <script>\n" +
                "            function loadPostMsg(name){\n" +
                "                let url = \"/moviepost?name=\" + name.value;\n" +
                "\n" +
                "                fetch (url, {method: 'POST'})\n" +
                "                    .then(x => x.text())\n" +
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
        return response;
    } // Cierre del metodo
} // Cierre de la clase