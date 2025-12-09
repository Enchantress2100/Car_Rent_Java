package controlador;

import modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class GestorCarRent {
    // colecciones
    private List<Cliente> lstClientes;
    private List<Vehiculo> lstVehiculos;
    private List<ArriendoCuota> lstArriendosConCuotas;

    public GestorCarRent() {
        //carga de datos
        this.lstClientes = Conexion.cargarClientes();
        this.lstVehiculos = Conexion.cargarVehiculos();
        this.lstArriendosConCuotas = Conexion.cargarArriendos();

        if (this.lstClientes.isEmpty() || this.lstVehiculos.isEmpty()) {
            cargarDatosSimulados();
            Conexion.guardarClientes(lstClientes);
            Conexion.guardarVehiculos(lstVehiculos);
        }
    }

    private void cargarDatosSimulados() {
        if (lstClientes == null) this.lstClientes = new ArrayList<>();
        if (lstVehiculos == null) this.lstVehiculos = new ArrayList<>();
        if (lstArriendosConCuotas == null) this.lstArriendosConCuotas = new ArrayList<>();

        lstClientes.add(new Cliente("Juan Pérez", new Cedula("1111111", '1'), true));
        lstClientes.add(new Cliente("Ana Gómez", new Cedula("2222222", '2'), false)); // Cliente No Vigente

        lstVehiculos.add(new Vehiculo("ABCD12", "TOYOTA", "YARIS", 2020, CondicionVehiculo.DISPONIBLE));
        lstVehiculos.add(new Vehiculo("WXYZ98", "NISSAN", "VERSA", 2021, CondicionVehiculo.ARRENDADO));
        lstVehiculos.add(new Vehiculo("QWER76", "FORD", "FIESTA", 2022, CondicionVehiculo.DISPONIBLE));
    }

    //getters y accesos

    public List<Cliente> getClientes() { return lstClientes; }
    public List<ArriendoCuota> getArriendosConCuotas() { return lstArriendosConCuotas; }

    public List<Vehiculo> getVehiculosDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : lstVehiculos) {
            if (v.getCondicion() == CondicionVehiculo.DISPONIBLE) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }

    public void agregarCliente(Cliente cliente) {
        lstClientes.add(cliente);
        Conexion.guardarClientes(lstClientes); // Guardar
    }

    public int getNextArriendoID() {
        return lstArriendosConCuotas.size() + 1;
    }

    //logica negocio
    public boolean ingresarArriendoConCuotas(ArriendoCuota arriendo) {
        if (!arriendo.evaluarArriendo()) {
            ArriendoCuota.mostrarMensaje("Fallo: Evaluación previa fallida.", "ERROR");
            return false;
        }

        //cambiar la condicion
        arriendo.getVehiculo().setCondicion(CondicionVehiculo.ARRENDADO);
        //guardar cambio de estado del vehiculo
        Conexion.guardarVehiculos(lstVehiculos);

        //crear y asignar cuotas
        List<CuotaArriendo> cuotas = arriendo.generarCuotasDelArriendo();
        arriendo.setLstCuotas(cuotas);

        //agregar nuevo arriendo a la lista
        lstArriendosConCuotas.add(arriendo);
        //guardar la lista
        Conexion.guardarArriendos(lstArriendosConCuotas);

        ArriendoCuota.mostrarMensaje("Arriendo N°" + arriendo.getNumArriendo() + " guardado.", "ÉXITO");
        return true;
    }

    //pago
    public boolean realizarPagoDeCuota(ArriendoCuota arriendo, CuotaArriendo cuota) {
        if (arriendo.pagarCuota(cuota)) {
            Conexion.guardarArriendos(lstArriendosConCuotas); // Guardar el cambio de estado de la cuota
            return true;
        }
        return false;
    }
}