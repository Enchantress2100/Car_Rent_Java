package modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class ArriendoCuota implements Serializable {
    private int numArriendo;
    private Date fecArr;
    private int diasArriendo;
    private int cantCuotas;
    private double precioPorDia;

    //relaciones
    private Cliente cliente;
    private Vehiculo vehiculo;
    private List<CuotaArriendo> lstCuotas;

    public ArriendoCuota(int numArriendo, Date fecArr, int diasArriendo, int cantCuotas, double precioPorDia, Cliente cliente, Vehiculo vehiculo) {
        this.numArriendo = numArriendo;
        this.fecArr = fecArr;
        this.diasArriendo = diasArriendo;
        this.cantCuotas = cantCuotas;
        this.precioPorDia = precioPorDia;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.lstCuotas = new ArrayList<>();
    }

    //obtener monto a pagar
    public double obtenerMontoAPagar() {
        return diasArriendo * precioPorDia;
    }

    //evaluar modelo para arrendar (si es posible o no)
    public boolean evaluarArriendo() {
        //validar que el cliente esté vigente
        if (cliente == null || !cliente.isVigente()) {
            mostrarMensaje("El arriendo no es posible: modelo.Cliente no vigente.", "ERROR");
            return false;
        }
        //validar que el vehículo tenga condición D (disponible)
        if (vehiculo == null || vehiculo.getCondicion() != CondicionVehiculo.DISPONIBLE){
            mostrarMensaje("El arriendo no es posible: Vehículo no disponible.", "ERROR");
            return false;
        }
        return true;
    }

    //generar cuotas
    public ArrayList<CuotaArriendo> generarCuotasDelArriendo() {
        double montoTotal = obtenerMontoAPagar();
        double valorCuota = montoTotal / cantCuotas;
        ArrayList<CuotaArriendo> nuevasCuotas = new ArrayList<>();

        for (int i = 1; i <= cantCuotas; i++) {
            CuotaArriendo cuota = new CuotaArriendo(i, valorCuota);
            nuevasCuotas.add(cuota);
        }
        return nuevasCuotas;
    }

    //pagar cuotas
    public boolean pagarCuota(CuotaArriendo cuotaAPagar) {
        if (lstCuotas.contains(cuotaAPagar)) {
            return cuotaAPagar.pagarCuota();
        }
        CuotaArriendo.mostrarMensaje("Cuota no encontrada en este arriendo.", "ERROR");
        return false;
    }

    //metodo mensajes
    public static void mostrarMensaje(String mensaje, String tipo) {
        System.out.println("[" + tipo.toUpperCase() + "] ARRIENDO: " + mensaje);
    }

    //getters y setters
    public Cliente getCliente() { return cliente; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public List<CuotaArriendo> getLstCuotas() { return lstCuotas; }
    public void setLstCuotas(List<CuotaArriendo> lstCuotas) { this.lstCuotas = lstCuotas; }
    public int getNumArriendo() { return numArriendo; }

    @Override
    public String toString() {
        return "N°: " + numArriendo + " | modelo.Cliente: " + cliente.getNombre() + " | Vehículo: " + vehiculo.getPatente();
    }
}