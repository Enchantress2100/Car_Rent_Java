package modelo;
import java.io.Serializable;

public class Vehiculo implements Serializable {
    private String patente;
    private String marca;
    private String modelo;
    private int añoFabricacion;

    // CORRECCIÓN 1: El atributo debe ser del tipo Enum
    private CondicionVehiculo condicion;

    // CORRECCIÓN 2: El constructor debe recibir el tipo Enum
    public Vehiculo(String patente, String marca, String modelo, int añoFabricacion, CondicionVehiculo condicion) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.añoFabricacion = añoFabricacion;
        this.condicion = condicion;
    }

    // CORRECCIÓN 3: El getter debe devolver el tipo Enum
    public CondicionVehiculo getCondicion() {
        return condicion;
    }

    // El setter está ahora CORRECTO, ya que ambos tipos (atributo y parámetro) son CondicionVehiculo
    public void setCondicion(CondicionVehiculo condicion) {
        this.condicion = condicion;
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    //metodo de mensajes
    public static void mostrarMensaje(String mensaje, String tipo) {
        System.out.println("[" + tipo.toUpperCase() + "] VEHICULO: " + mensaje);
    }

    @Override
    public String toString() {
        // Muestra la información clave del vehículo
        return this.getMarca() + " " + this.getModelo() + " [" + this.getPatente() + "]";
    }
}