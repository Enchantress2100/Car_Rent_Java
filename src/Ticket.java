// Representa el ticket
public class Ticket {
    private int idTicket;
    private String detalle;

    public Ticket(int idTicket, String detalle) {
        this.idTicket = idTicket;
        this.detalle = detalle;
    }

    public String getDetalle() {
        return detalle;
    }
}
