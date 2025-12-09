package modelo;
import java.io.Serializable;

public class CuotaArriendo implements Serializable {
    private int numCuota;
    private double valorCuota;
    private boolean pagada;

    //constructor que inicializa pagada = false
    public CuotaArriendo(int numCuota, double valorCuota) {
        this.numCuota = numCuota;
        this.valorCuota = valorCuota;
        this.pagada = false;
    }

    //operación pagar cuota
    public boolean pagarCuota() {
        if (!this.pagada) {
            this.pagada = true;
            mostrarMensaje("Cuota N° " + numCuota + " pagada exitosamente.", "INFO");
            return true;
        }
        mostrarMensaje("La cuota N° " + numCuota + " ya estaba pagada.", "ADVERTENCIA");
        return false;
    }

    //getters y setters
    public int getNumCuota() { return numCuota; }
    public double getValorCuota() { return valorCuota; }
    public boolean isPagada() { return pagada; }
    public void setPagada(boolean pagada) { this.pagada = pagada; }

    //metodo de mensajes
    public static void mostrarMensaje(String mensaje, String tipo) {
        System.out.println("[" + tipo.toUpperCase() + "] CUOTA: " + mensaje);
    }

    @Override
    public String toString() {
        return "N° " + numCuota + " | Valor: $" + String.format("%.2f", valorCuota) + " | Pagada: " + (pagada ? "Sí" : "No");
    }
}