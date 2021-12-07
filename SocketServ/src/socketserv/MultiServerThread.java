package socketserv;

import java.net.*;
import java.io.*;
import java.util.Date;

import thread.UsingHT;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    Socket clienteX = null;
    UsingHT HT;
    int port;
    int puertoReal = 0; /* por si se debe conectar desde otro puerto */
    boolean verificado = false; /* para esperar respuesta válida del serv */
    String respuesta = "-1";

    public MultiServerThread(Socket socket, int puerto) {
        super("MultiServerThread");
        this.socket = socket;
        this.port = puerto;
        ServerMultiClient.NoClients++;
        System.out.println("MultiThread running & listenning, port: " + puerto);
        HT = new UsingHT();
    }

    public void run() {

        try {

            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String lineIn;

            ObjectOutputStream outputStream = new ObjectOutputStream(
                    socket.getOutputStream());

            while ((lineIn = entrada.readLine()) != null) {
                /* Se verifica si se pide 1 o más datos */
                String[] arrSplit = lineIn.split(",");

                /* Pide varias cosas */
                //1111
                //SUC001,SUC002
                for (int i = 0; i < arrSplit.length; i++) {

                    /* Se valida si pide 2 servidores, o 1 serv y 1 cliente*/

                    /* pide servidor */
                    if (arrSplit[i].substring(0, 1).equals("s")) {
                        respuesta = HT.requestSucursal(arrSplit[i], port);
                    } else {
                        respuesta = HT.requestCuenta(arrSplit[i], port);
                    }

                    try {
                        puertoReal = Integer.parseInt(respuesta);
                        if (puertoReal != 0) {
                            /* significa que se debe hacer conexión desde otro puerto */
                            try {
                                /* se hace nueva verificación con el puerto correcto */
                                clienteX = new Socket(socket.getInetAddress(), puertoReal);
                                PrintWriter escritorX = new PrintWriter(clienteX.getOutputStream(), true);
                                BufferedReader entradaX = new BufferedReader(new InputStreamReader(clienteX.getInputStream()));
                                /* se hace la petición escribriendo en ese puerto */
                                escritorX.println(arrSplit[i]);

                                /* se recupera la respuesta en ese puerto */
                                String newResp = entradaX.readLine();

                                /* se imprimen las respuestas */
                                //System.out.println(newResp);                        

                                escritor.println(newResp);
                                escritor.flush();

                                /* se cierran conexiones alternas */
                                clienteX.close();
                                escritorX.close();
                                entradaX.close();
                            } catch (Exception e) {
                                System.out.println("Fallo : " + e.toString());
                                System.exit(0);
                            }
                        } else {
                            escritor.println("No se encontró el servicio en ningún puerto");
                        }
                    } catch (Exception e) {
                        /* no es el puerto, es una respuesta del sv*/
                        escritor.println(respuesta);
                        escritor.flush();
                        verificado = true;
                    }
                }
            }
            try {
                entrada.close();
                escritor.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("Error : " + e.toString());
                socket.close();
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("Error---");
            e.printStackTrace();
        }
    }
}
