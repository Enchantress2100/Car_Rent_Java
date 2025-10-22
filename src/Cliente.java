import java.time.LocalDate;

public class Cliente extends Usuario {
    private boolean estaVigente;

    public Cliente(String nombre, Cedula cedula, boolean estaVigente) {
        super(nombre, cedula);
        this.estaVigente = estaVigente;
    }

    // El cliente crea una solicitud de arriendo.

    public Arriendo solicitarArriendoVehiculo(Vehiculo vehiculo, int numeroArriendo,
                                              LocalDate fechaArriendo, int diasArriendo,
                                              float montoDiario, String estado) {

        System.out.println("Cliente " + this.nombre + " solicita arriendo para " + vehiculo.getMarca() + " " + vehiculo.getModelo() + ".");

        // Crea la instancia de Arriendo
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