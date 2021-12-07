/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;
import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import thread.Servicio;

public class UsingHT {
    int key = 1;
    Hashtable<Integer, Servicio> hS = new Hashtable<Integer, Servicio>();
    Servicio s;

    public UsingHT(int puerto) {
        removeAll();
        readServicesJSON("" + puerto);
    }

    public UsingHT() {
        removeAll();
        readServicesJSON("all");
    }

    private void removeAll() {
        hS.clear();
        key = 1;
    }

    private void addService(String _Sucursal, String _NoCuenta, String _CURP, String _Nombres, String _Apellidos, Double _Saldo, int nodo) {
        s = new Servicio(_Sucursal, _NoCuenta, _CURP, _Nombres, _Apellidos, _Saldo, nodo);
        hS.put(key, s);
        key++;
    }

    /* recibe el puerto desde el que se solicita y el servicio que le gustaría recibir*/
    public String requestSucursal(String sucursal, int puerto) {
        System.out.println("Verificando HT para " + sucursal + " en puerto " + puerto);
        boolean existe = false;
        int portReal = 0;
        String ret = "";

        if (hS.size() > 0) {
            /* Se verifica si el servicio está en ese puerto */
            int portAux = 0;
            String serv = "";
            for (int i = 1; i < key; i++) {
                portAux = hS.get(i).getNodo();
                serv = hS.get(i).getSucursal();

                /* se encuentra la sucursal */
                if (serv.equals(sucursal)) {
                    /* Es el mismo puerto */
                    if (portAux == puerto) {
                        existe = true;
                        break;
                    } else {
                        /* puerto no coincide, se guarda el puerto que debe ser */
                        portReal = portAux;
                    }
                }
            }
            if (existe) {
                for (int j = 1; j < key; j++) {
                    /* se encuentra la sucursal */
                    if (hS.get(j).getSucursal().equals(sucursal)) {
                        /* Es el mismo puerto */
                        if (hS.get(j).getNodo() == puerto) {
                            ret = ret + hS.get(j).getSucursal() + "||" + hS.get(j).getNoCuenta() + "||" + hS.get(j).getCURP() + "||" + hS.get(j).getNombres() + "||" +
                                    hS.get(j).getApellidos() + "||" + hS.get(j).getSaldo() + ",";
                        }
                    }
                }
            } else {
                ret = "" + portReal;
            }
        }
        return ret;
    }

    public String requestCuenta(String cuenta, int puerto) {
        System.out.println("Verificando HT para " + cuenta + " en puerto " + puerto);
        boolean existe = false;
        int portReal = 0;
        String ret = "";

        if (hS.size() > 0) {
            /* Se verifica si el servicio está en ese puerto */
            int portAux = 0;
            String serv = "";
            for (int i = 1; i < key; i++) {
                portAux = hS.get(i).getNodo();
                serv = hS.get(i).getNoCuenta();

                /* se encuentra la sucursal */
                if (serv.equals(cuenta)) {
                    /* Es el mismo puerto */
                    if (portAux == puerto) {
                        existe = true;
                        break;
                    } else {
                        /* puerto no coincide, se guarda el puerto que debe ser */
                        portReal = portAux;
                    }
                }
            }

            if (existe) {
                for (int j = 1; j < key; j++) {
                    /* se encuentra la sucursal */
                    if (hS.get(j).getNoCuenta().equals(cuenta)) {
                        /* Es el mismo puerto */
                        if (hS.get(j).getNodo() == puerto) {
                            ret = ret + hS.get(j).getSucursal() + "||" + hS.get(j).getNoCuenta() + "||" + hS.get(j).getCURP() + "||" + hS.get(j).getNombres() + "||" +
                                    hS.get(j).getApellidos() + "||" + hS.get(j).getSaldo() + ",";
                        }
                    }
                }
            } else {
                ret = "" + portReal;
            }
        }
        return ret;
    }

    private void readServicesJSON(String puerto) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("SocketServ/" + puerto + ".json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray services = (JSONArray) jsonObject.get("services");
            Iterator<JSONObject> iterator = services.iterator();

            while (iterator.hasNext()) {
                JSONObject aux = new JSONObject();
                aux = iterator.next();
                addService(aux.get("Sucursal").toString().toLowerCase(), aux.get("NoCuenta").toString().toLowerCase(),
                        aux.get("CURP").toString().toLowerCase(), aux.get("Nombres").toString().toLowerCase().replaceAll("\\s+",""), aux.get("Apellidos").toString().toLowerCase().replaceAll("\\s+",""),
                        Double.parseDouble(aux.get("Saldo").toString()), 1111 * Integer.parseInt(aux.get("Sucursal").toString().substring(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
