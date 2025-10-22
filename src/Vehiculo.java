public class Vehiculo {
    private String patente;
    private String marca;
    private String modelo;
    private int añoFabricacion;
    private CondicionVehiculo condicion;

    public Vehiculo(String patente, String marca, String modelo, int añoFabricacion, CondicionVehiculo condicion) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.añoFabricacion = añoFabricacion;
        this.condicion = condicion;
    }

    public CondicionVehiculo getCondicion() {
        return condicion;
    }

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
}