import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // CÉDULAS
        // cedula cliente
        Cedula cedulaCliente = new Cedula("17678932", 'k');
        //Cedula cedulaCliente = new Cedula("17678657932", 'k'); (cliente no puede arrendar)

        // cedula empleado
       //Cedula cedulaEmpleado = new Cedula("17476678749", '8'); (empleado no autorizado)
        Cedula cedulaEmpleado = new Cedula("17477749", '8');

        Cedula cedulaGerente = new Cedula("15374648", '1');
        //Cedula cedulaGerente = new Cedula("15467677648", '1'); (gerente no autorizado

        // validaciones
        System.out.println("Validar cédulas");

        // validacion cliente
        if (cedulaCliente.esValida()) {
            System.out.println("Cédula Cliente (" + cedulaCliente + ") -> VÁLIDA.");
        } else {
            System.out.println("Cédula Cliente (" + cedulaCliente + ") -> NO VÁLIDA.");
        }

        // validacion empleado
        if (cedulaEmpleado.esValida()) {
            System.out.println("Cédula Empleado (" + cedulaEmpleado + ") -> VÁLIDA.");
        } else {
            System.out.println("Cédula Empleado (" + cedulaEmpleado + ") -> NO VÁLIDA.");
        }

        // validacion gerente
        if (cedulaGerente.esValida()) {
            System.out.println("Cédula Gerente (" + cedulaGerente + ") -> VÁLIDA.");
        } else {
            System.out.println("Cédula Gerente (" + cedulaGerente + ") -> NO VÁLIDA.");
        }

        // con ruts validos podemos ingresar el nombre (decia en la guia que no se necesitaba input sino datos en duro)
        Cliente cliente = new Cliente("Juan Pérez", cedulaCliente, true);
        Empleado empleado = new Empleado("Pepita Palotes", cedulaEmpleado, 101);
        Gerente gerente = new Gerente("Casimiro Buenavista", cedulaGerente, 100);

        Vehiculo vehiculo = new Vehiculo(
                "VM677455",
                "TOYOTA",
                "YARIS",
                2020,
                //CondicionVehiculo.DISPONIBLE //asumo que el vehiculo esta disponible para poder arrendarlo
                CondicionVehiculo.ARRENDADO
        );

        int numeroArriendo = 567;
        LocalDate fechaArriendo = LocalDate.of(2025, 10, 21);
        int diasArriendo = 3;
        float montoDiario = 35000.0f;
        String estado = "en curso";

        // --- si las validaciones son exitosas seguimos
        Arriendo arriendoGenerado = null;
        Ticket ticketFinal = null;

        System.out.println("\n VALIDANDO GERENCIA Y CLIENTE");

        if (cedulaGerente.esValida()) {
            System.out.println("Cédula gerente (" + cedulaGerente + ") -> VÁLIDA.");
            boolean clienteVigente = cedulaCliente.esValida();

            if (!clienteVigente) {
                System.out.println("\nPROCESO DETENIDO: El cliente no esta vigente");
                return;
            }
        } else {
            System.out.println("Cédula de Gerente NO VÁLIDA.");
            System.out.println("\nPROCESO DETENIDO: Gerente no autorizado.");
            return;
        }


        System.out.println("\n VALIDANDO PERSONAL Y VEHÍCULO");

        if (cedulaEmpleado.esValida()) {
            System.out.println("Cédula Empleado (" + cedulaEmpleado + ") -> VÁLIDA.");
            boolean autoDisponible = empleado.validarDisponibilidadVehiculo(vehiculo);

            if (!autoDisponible) {
                System.out.println("\nPROCESO DETENIDO: El vehículo no está disponible.");
                return;
            }
        } else {
            System.out.println("Cédula de Empleado NO VÁLIDA.");
            System.out.println("\nPROCESO DETENIDO: Empleado no autorizado.");
            return;
        }

        System.out.println("\n VALIDANDO CLIENTE Y SOLICITUD ---");

        if (cedulaCliente.esValida()) {
            System.out.println("Cédula Cliente (" + cedulaCliente + ") -> VÁLIDA.");
            arriendoGenerado = cliente.solicitarArriendoVehiculo(
                    vehiculo, numeroArriendo, fechaArriendo,
                    diasArriendo, montoDiario, estado
            );
            System.out.println("-> Solicitud de arriendo N°" + arriendoGenerado.getNumeroArriendo() + " creada.");
        } else {
            System.out.println("Cédula Cliente (" + cedulaCliente + ") -> NO VÁLIDA.");
            System.out.println("\nPROCESO DETENIDO: Cliente no puede arrendar.");
            return;
        }

        System.out.println("\n--- 3. PROCESANDO ARRIENDO Y TICKET ---");

        if (cedulaEmpleado.esValida() && arriendoGenerado != null) {
            ticketFinal = empleado.ingresarArriendo(arriendoGenerado);
            System.out.println("\n--- TICKET GENERADO ---");
            System.out.println(ticketFinal.getDetalle());
        } else {
            System.out.println("Error inesperado en el procesamiento final.");
        }
    }
}