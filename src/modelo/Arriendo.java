package modelo;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Arriendo implements Serializable {
    private int numeroArriendo;
    private LocalDate fechaArriendo;
    private int numeroDiasArriendo;
    private float montoArriendoDiario;
    private String estado;
    private Cliente cliente;
    private Vehiculo vehiculo;

    public Arriendo(int numeroArriendo, LocalDate fechaArriendo, int numeroDiasArriendo,
                    float montoArriendoDiario, String estado, Cliente cliente, Vehiculo vehiculo) {
        this.numeroArriendo = numeroArriendo;
        this.fechaArriendo = fechaArriendo;
        this.numeroDiasArriendo = numeroDiasArriendo;
        this.montoArriendoDiario = montoArriendoDiario;
        this.estado = estado;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
    }

    public int getNumeroArriendo() {
        return numeroArriendo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public float generarCobro() {
        return this.numeroDiasArriendo * this.montoArriendoDiario;
    }

    public Ticket generarTicket() {
        float total = generarCobro();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFormateada = this.fechaArriendo.format(formatter);

        StringBuilder detalle = new StringBuilder();
        detalle.append("TICKET ARRIENDO DE VEHICULO\n");
        detalle.append("NÚMERO ARRIENDO : ").append(this.numeroArriendo).append("\n");
        detalle.append("VEHÍCULO : ").append(vehiculo.getPatente()).append(" ")
                .append(vehiculo.getMarca()).append(" ").append(vehiculo.getModelo()).append("\n");
        detalle.append(String.format("PRECIO ARRIENDO DIARIO: $%.0f\n", this.montoArriendoDiario));
        detalle.append("TOTAL DE DIAS DE ARRIENDO: ").append(this.numeroDiasArriendo).append("\n");
        detalle.append(String.format("PRECIO TOTAL: %.0f\n", total));
        detalle.append("FECHA CLIENTE DÍAS A PAGAR: \n");
        detalle.append("--------------------------------------------------------------------------------------\n");
        detalle.append(String.format("%s - %s %d días $%.0f",
                fechaFormateada,
                this.cliente.getNombre(),
                this.numeroDiasArriendo,
                total));

        return new Ticket(this.numeroArriendo, detalle.toString());
    }
}