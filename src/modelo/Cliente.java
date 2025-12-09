package modelo;
import java.io.Serializable;
import java.time.LocalDate;

public class Cliente extends Usuario implements Serializable {
    private boolean estaVigente;

    public Cliente(String nombre, Cedula cedula, boolean estaVigente) {
        super(nombre, cedula);
        this.estaVigente = estaVigente;
    }

    //getter vigencia
    public boolean isVigente() {
        return this.estaVigente;
    }

    //metodo mensajes
    public static void mostrarMensaje(String mensaje, String tipo) {
        System.out.println("[" + tipo.toUpperCase() + "] CLIENTE: " + mensaje);
    }

    //setters y validacion

    public static boolean validarCedula(String cedula) {
        // Solo valida que el String no sea nulo/vacío
        return cedula != null && !cedula.trim().isEmpty();
    }
    @Override
    public String toString() {
        // Muestra el nombre y la cédula para que sea legible en el desplegable
        return this.getNombre() + " (" + this.cedula.getNumero() + "-" + this.cedula.getDigitoVerificador() + ")";
    }

    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    //setter que recibe un objeto Cedula y lo valida
    public void setCedula(Cedula nuevaCedula) {
        if (!validarCedula(nuevaCedula.getNumero())) {
            mostrarMensaje("Cédula inválida. No se actualizó.", "ADVERTENCIA");
            return;
        }
        this.cedula = nuevaCedula; // Asigna el objeto Cedula
    }

    public void setEstaVigente(boolean estaVigente) {
        this.estaVigente = estaVigente;
    }

    public Arriendo solicitarArriendoVehiculo(Vehiculo vehiculo, int numeroArriendo,
                                              LocalDate fechaArriendo, int diasArriendo,
                                              float montoDiario, String estado) {

        mostrarMensaje("solicita arriendo para " + vehiculo.getMarca() + " " + vehiculo.getModelo() + ".", "INFO");

        //crea instancia arriendo
        Arriendo nuevoArriendo = new Arriendo(
                numeroArriendo,
                fechaArriendo,
                diasArriendo,
                montoDiario,
                estado,
                this,     // Pasa este mismo objeto Cliente
                vehiculo  // Pasa el objeto Vehiculo
        );

        return nuevoArriendo;
    }
}