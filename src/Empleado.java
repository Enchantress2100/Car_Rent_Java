public class Empleado extends Usuario {
    protected int idEmpleado;
    public Empleado(String nombre, Cedula cedula, int idEmpleado) {
        super(nombre, cedula);
        this.idEmpleado = idEmpleado;
    }

    // El empleado comprueba que el auto se puede arrendar.

    public boolean validarDisponibilidadVehiculo(Vehiculo vehiculo) {
        System.out.println("Empleado " + this.nombre + " validando vehículo: " + vehiculo.getPatente());

        if (vehiculo.getCondicion() == CondicionVehiculo.DISPONIBLE) {
            System.out.println("-> Vehículo DISPONIBLE para arrendar.");
            return true;
        } else {
            System.out.println("-> Vehículo NO DISPONIBLE (Condición: " + vehiculo.getCondicion() + ").");
            return false;
        }
    }

    //El empleado guarda el arriendo y genera el ticket.

    public Ticket ingresarArriendo(Arriendo arriendo) {
        System.out.println("Empleado " + this.nombre + " ingresando arriendo N° " + arriendo.getNumeroArriendo() + " al sistema.");
        Ticket ticket = arriendo.generarTicket();
        arriendo.getVehiculo().setCondicion(CondicionVehiculo.ARRENDADO);
        System.out.println("-> Estado del vehículo " + arriendo.getVehiculo().getPatente() + " actualizado a ARRENDADO.");

        return ticket;
    }
}