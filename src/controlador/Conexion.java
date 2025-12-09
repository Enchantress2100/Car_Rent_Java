package controlador;

import modelo.*; // Para usar Cliente, Vehiculo, ArriendoCuota, etc.
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private static final String FILE_CLIENTES = "clientes.dat";
    private static final String FILE_VEHICULOS = "vehiculos.dat";
    private static final String FILE_ARRIENDOS = "arriendos.dat";

    //escritura

    public static void guardarClientes(List<Cliente> lstClientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_CLIENTES))) {
            oos.writeObject(lstClientes);
            System.out.println("DEBUG: Clientes guardados en " + FILE_CLIENTES);
        } catch (IOException e) {
            System.err.println("ERROR al guardar clientes: " + e.getMessage());
        }
    }

    public static void guardarArriendos(List<ArriendoCuota> lstArriendos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_ARRIENDOS))) {
            oos.writeObject(lstArriendos);
            System.out.println("DEBUG: Arriendos guardados en " + FILE_ARRIENDOS);
        } catch (IOException e) {
            System.err.println("ERROR al guardar arriendos: " + e.getMessage());
        }
        }

        public static void guardarVehiculos(List<Vehiculo> lstVehiculos) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_VEHICULOS))) {
                oos.writeObject(lstVehiculos);
                System.out.println("DEBUG: Vehiculos guardados en " + FILE_VEHICULOS);
            } catch (IOException e) {
                System.err.println("ERROR al guardar vehiculos: " + e.getMessage());
            }
    }

    //lectura
    @SuppressWarnings("unchecked")
    public static List<Cliente> cargarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_CLIENTES))) {
            System.out.println("DEBUG: Clientes cargados desde " + FILE_CLIENTES);
            return (List<Cliente>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("INFO: Archivo de clientes no encontrado. Iniciando lista vacía.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR al cargar clientes: " + e.getMessage());
        }
        return new ArrayList<>(); // Retorna lista vacía si falla o no existe
    }

    @SuppressWarnings("unchecked")
    public static List<Vehiculo> cargarVehiculos() { // CORREGIDO: Devuelve List<Vehiculo>
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_VEHICULOS))) {
            System.out.println("DEBUG: Vehiculos cargados desde " + FILE_VEHICULOS);
            return (List<Vehiculo>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("INFO: Archivo de vehiculos no encontrado. Iniciando lista vacía.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR al cargar vehiculos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static List<ArriendoCuota> cargarArriendos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_ARRIENDOS))) {
            System.out.println("DEBUG: Arriendos cargados desde " + FILE_ARRIENDOS);
            return (List<ArriendoCuota>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("INFO: Archivo de arriendos no encontrado. Iniciando lista vacía.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR al cargar arriendos: " + e.getMessage());
        }
        return new ArrayList<>(); //retorna lista vacía si falla o no existe
    }
}