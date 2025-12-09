package vista;

import controlador.GestorCarRent;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class ArriendoCuotasUI extends JDialog {
    public final GestorCarRent gestor;

    // formulario
    public JComboBox<Cliente> cmbCliente;
    public JComboBox<Vehiculo> cmbVehiculo;
    public JTextField txtFechaArriendo, txtDias, txtPrecioDia, txtMontoAPagar, txtCantCuotas;
    public DefaultListModel<CuotaArriendo> cuotasModel;
    public JList<CuotaArriendo> listCuotas;

    public ArriendoCuota arriendoEnProceso;

    //ancho para los JTextField
    private static final int FIELD_WIDTH = 15;

    public ArriendoCuotasUI(JFrame padre, GestorCarRent gestor) {
        super(padre, "ARRIENDOS CON CUOTAS", true);
        this.gestor = gestor;

        // iniciar componentes
        inicializarComponentes();

        // carga inicial
        actualizarClientes();
        actualizarVehiculos();

        pack();
        setLocationRelativeTo(padre);
    }

    //utilidades layout (Añadido weightx)
    public void addComp(Container container, Component c, int x, int y, int w, int h, int fill, int anchor, Insets insets, double weightx) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, w, h, weightx, 0.0, anchor, fill, insets, 0, 0);
        container.add(c, gbc);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());

        //zona superior
        JPanel pnlSeleccion = new JPanel(new GridLayout(1, 3, 10, 5));
        cmbCliente = new JComboBox<>();
        cmbVehiculo = new JComboBox<>();

        pnlSeleccion.add(new JLabel("Seleccione CLIENTE"));
        pnlSeleccion.add(new JLabel("Seleccione AUTOMOVIL"));
        pnlSeleccion.add(new JLabel(""));

        pnlSeleccion.add(cmbCliente);
        pnlSeleccion.add(cmbVehiculo);

        JButton btnIngresarNuevoCliente = new JButton("Ingresar nuevo Cliente");
        btnIngresarNuevoCliente.addActionListener(e -> new ClientesUI(this, gestor).setVisible(true));
        pnlSeleccion.add(btnIngresarNuevoCliente);

        addComp(this, pnlSeleccion, 0, 0, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, new Insets(10, 10, 10, 10), 1.0);

        //datos y boton guardar
        JPanel pnlDatosArriendo = new JPanel(new GridBagLayout());

        txtFechaArriendo = new JTextField(FIELD_WIDTH);
        txtDias = new JTextField(FIELD_WIDTH);
        txtPrecioDia = new JTextField(FIELD_WIDTH);
        txtCantCuotas = new JTextField(FIELD_WIDTH);
        txtMontoAPagar = new JTextField(FIELD_WIDTH);
        txtMontoAPagar.setEditable(false);

        int row = 0;
        //columnas
        addComp(pnlDatosArriendo, new JLabel("Fecha Arriendo:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 0.0);
        addComp(pnlDatosArriendo, txtFechaArriendo, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 1.0);

        addComp(pnlDatosArriendo, new JLabel("Días:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 0.0);
        addComp(pnlDatosArriendo, txtDias, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 1.0);

        addComp(pnlDatosArriendo, new JLabel("Precio por día:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 0.0);
        addComp(pnlDatosArriendo, txtPrecioDia, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 1.0);

        // Cantidad de cuotas está en las columnas 2 y 3
        addComp(pnlDatosArriendo, new JLabel("Cantidad de cuotas:"), 2, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 0.0);
        addComp(pnlDatosArriendo, txtCantCuotas, 3, 0, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, new Insets(5, 5, 5, 5), 1.0); // Peso 1.0

        JButton btnGuardarMostrarCuotas = new JButton("Guardar arriendo y mostrar cuotas >>");
        btnGuardarMostrarCuotas.addActionListener(e -> guardarArriendoYMostrarCuotasHandler()); // Evento Guardar
        addComp(pnlDatosArriendo, btnGuardarMostrarCuotas, 2, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(10, 5, 5, 5), 0.0);

        //monto
        addComp(pnlDatosArriendo, new JLabel("MONTO A PAGAR:"), 0, row, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, new Insets(10, 5, 5, 5), 0.0);
        addComp(pnlDatosArriendo, txtMontoAPagar, 1, row++, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, new Insets(10, 5, 5, 5), 1.0);

        // Agregar pnlDatosArriendo al diálogo principal (Ocupa columna 0, Peso 1.0)
        addComp(this, pnlDatosArriendo, 0, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, new Insets(0, 10, 10, 10), 1.0);


        //cuotas (derecha)
        JPanel pnlCuotas = new JPanel(new BorderLayout());
        pnlCuotas.setBorder(BorderFactory.createTitledBorder("CUOTAS A PAGAR"));

        cuotasModel = new DefaultListModel<>();
        listCuotas = new JList<>(cuotasModel);

        JScrollPane scrollCuotas = new JScrollPane(listCuotas);
        pnlCuotas.add(scrollCuotas, BorderLayout.CENTER);

        JButton btnPagarPrimeraCuota = new JButton("Pagar Primera Cuota");
        btnPagarPrimeraCuota.addActionListener(e -> pagarPrimeraCuotaHandler()); // Evento Pagar Primera Cuota
        pnlCuotas.add(btnPagarPrimeraCuota, BorderLayout.SOUTH);

        // Agregar pnlCuotas al diálogo principal (Ocupa columna 1, Peso 1.0)
        addComp(this, pnlCuotas, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHEAST, new Insets(0, 0, 10, 10), 1.0);
    }


    public void actualizarClientes() {
        cmbCliente.removeAllItems();
        for (modelo.Cliente c : gestor.getClientes()) {
            cmbCliente.addItem(c);
        }
    }

    public void actualizarVehiculos() {
        cmbVehiculo.removeAllItems();
        for (modelo.Vehiculo v : gestor.getVehiculosDisponibles()) {
            cmbVehiculo.addItem(v);
        }
    }

   //logica de eventos
    public void guardarArriendoYMostrarCuotasHandler() {
        try {
            Cliente clienteSel = (Cliente) cmbCliente.getSelectedItem();
            Vehiculo vehiculoSel = (Vehiculo) cmbVehiculo.getSelectedItem();

            int dias = Integer.parseInt(txtDias.getText());
            int cantCuotas = Integer.parseInt(txtCantCuotas.getText());
            double precioPorDia = Double.parseDouble(txtPrecioDia.getText());

            if (clienteSel == null || vehiculoSel == null || dias <= 0 || cantCuotas <= 0 || precioPorDia <= 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar Cliente/Vehículo e ingresar valores positivos.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numArriendo = gestor.getNextArriendoID();
            Date fechaActual = new Date();

            arriendoEnProceso = new ArriendoCuota(
                    numArriendo, fechaActual, dias, cantCuotas, precioPorDia, clienteSel, vehiculoSel
            );

            txtMontoAPagar.setText(String.format("%.2f", arriendoEnProceso.obtenerMontoAPagar()));

            if (gestor.ingresarArriendoConCuotas(arriendoEnProceso)) {
                JOptionPane.showMessageDialog(this, "Arriendo registrado y vehículo bloqueado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                cuotasModel.clear();
                for (CuotaArriendo c : arriendoEnProceso.getLstCuotas()) {
                    cuotasModel.addElement(c);
                }

                actualizarVehiculos();

            } else {
                JOptionPane.showMessageDialog(this, "El arriendo fue rechazado. Verifique la vigencia del cliente o la disponibilidad.", "Fallo de Evaluación", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Asegúrese de ingresar números válidos en Días, Precio y Cantidad de cuotas.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarPrimeraCuotaHandler() {
        if (arriendoEnProceso == null || arriendoEnProceso.getLstCuotas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe generar y guardar un arriendo con cuotas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CuotaArriendo primeraCuota = arriendoEnProceso.getLstCuotas().get(0);

        if (primeraCuota.isPagada()) {
            JOptionPane.showMessageDialog(this, "La primera cuota ya fue pagada.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (gestor.realizarPagoDeCuota(arriendoEnProceso, primeraCuota)) {
            JOptionPane.showMessageDialog(this, "Primera cuota pagada con éxito. Actualizando sistema.", "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
            listCuotas.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Error al procesar el pago.", "Fallo", JOptionPane.ERROR_MESSAGE);
        }
    }
}