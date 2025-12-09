package vista;

import controlador.GestorCarRent;
import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PagarCuotasUI extends JDialog {
    public GestorCarRent gestor;

    //componentes UI
    public JComboBox<Cliente> cmbCliente;
    public JComboBox<Vehiculo> cmbVehiculo;
    public JTextField txtFechaArriendo, txtDias, txtPrecioDia, txtMontoAPagar, txtCantCuotas;
    public DefaultListModel<CuotaArriendo> cuotasModel;
    public JList<CuotaArriendo> listCuotas;
    public DefaultListModel<ArriendoCuota> arriendosModel;
    public JList<ArriendoCuota> listArriendos;

    public PagarCuotasUI(JFrame padre, GestorCarRent gestor) {
        super(padre, "PAGAR CUOTAS ARRIENDOS", true);
        this.gestor = gestor;
        inicializarComponentes();
        pack();
        setLocationRelativeTo(padre);
    }

    private void addComp(Container container, Component c, int x, int y, int w, int h, int fill, int anchor, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints(x, y, w, h, 0.0, 0.0, anchor, fill, insets, 0, 0);
        container.add(c, gbc);
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());

        //cliente (superior)
        JPanel pnlCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cmbCliente = new JComboBox<>();
        cmbVehiculo = new JComboBox<>();
        pnlCliente.add(new JLabel("Seleccione cliente:"));
        pnlCliente.add(cmbCliente);

        addComp(this, pnlCliente, 0, 0, 2, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, new Insets(10, 10, 10, 10));

        //arriendo (izquierda)
        JPanel pnlArriendos = new JPanel(new BorderLayout());
        arriendosModel = new DefaultListModel<>();
        listArriendos = new JList<>(arriendosModel);
        listArriendos.setBorder(BorderFactory.createTitledBorder("Seleccione arriendo:"));

        pnlArriendos.add(new JScrollPane(listArriendos), BorderLayout.CENTER);

        JButton btnMostrarPagos = new JButton("Mostrar pagos arriendo seleccionado >>>");
        btnMostrarPagos.addActionListener(e -> mostrarCuotasArriendoSelHandler()); // Evento Mostrar Cuotas
        pnlArriendos.add(btnMostrarPagos, BorderLayout.SOUTH);

        addComp(this, pnlArriendos, 0, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST, new Insets(0, 10, 10, 5));

        //pagos (derecha)
        JPanel pnlPagos = new JPanel(new BorderLayout());
        pnlPagos.setBorder(BorderFactory.createTitledBorder("PAGOS"));

        cuotasModel = new DefaultListModel<>();
        listCuotas = new JList<>(cuotasModel);

        JScrollPane scrollCuotas = new JScrollPane(listCuotas);
        pnlPagos.add(scrollCuotas, BorderLayout.CENTER);

        JButton btnRealizarPago = new JButton("Realizar Pago");
        btnRealizarPago.addActionListener(e -> ejecutarPagoCuotaHandler()); // Evento Pagar
        pnlPagos.add(btnRealizarPago, BorderLayout.SOUTH);

        addComp(this, pnlPagos, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST, new Insets(0, 5, 10, 10));

        //carga clientes
        cargarClientes();

        //conexion combobox
        cmbCliente.addActionListener(e -> seleccionarClienteHandler());
    }

    //logica de eventos
    public void cargarClientes() {
        cmbCliente.removeAllItems();

        //iterar sobre la lista de clientes obtenida del Controlador
        for (Cliente c : gestor.getClientes()) {
            cmbCliente.addItem(c);
        }
    }

    public void seleccionarClienteHandler() {
        Cliente clienteSel = (Cliente) cmbCliente.getSelectedItem();
        arriendosModel.clear(); //limpiar la lista de arriendos anteriores
        listCuotas.clearSelection(); //limpiar la selección de cuotas
        cuotasModel.clear(); //limpiar la lista de cuotas

        if (clienteSel == null) return;

        //filtrar los arriendos del Gestor que coincidan con el cliente seleccionado
        for (ArriendoCuota arriendo : gestor.getArriendosConCuotas()) {
            if (arriendo.getCliente().equals(clienteSel)) {
                arriendosModel.addElement(arriendo);
            }
        }
    }

    public void mostrarCuotasArriendoSelHandler() {
        ArriendoCuota arriendoSel = listArriendos.getSelectedValue();
        cuotasModel.clear();

        if (arriendoSel == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un arriendo de la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //mostrar la lista de cuotas del arriendo seleccionado
        for (CuotaArriendo cuota : arriendoSel.getLstCuotas()) {
            cuotasModel.addElement(cuota);
        }
    }

    public void ejecutarPagoCuotaHandler() {
            ArriendoCuota arriendoSel = listArriendos.getSelectedValue();
            CuotaArriendo cuotaSel = listCuotas.getSelectedValue();

            if (arriendoSel == null || cuotaSel == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un arriendo y una cuota.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //llama a la lógica de negocio para pagar
            if (gestor.realizarPagoDeCuota(arriendoSel, cuotaSel)) {
                JOptionPane.showMessageDialog(this, "Pago realizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listCuotas.repaint(); // Refrescar la vista para mostrar el cambio de estado
            } else {
                JOptionPane.showMessageDialog(this, "La cuota ya estaba pagada o ocurrió un error.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }