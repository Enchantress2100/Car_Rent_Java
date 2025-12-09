package vista;

import controlador.GestorCarRent;
import modelo.Cliente;
import modelo.Cedula;
import javax.swing.*;
import java.awt.*;

public class ClientesUI extends JDialog {
    public final GestorCarRent gestor;
    public JTextField txtCedula, txtNombre;
    public JCheckBox chkVigente;
    public ArriendoCuotasUI padre;

    //constante para el ancho de los campos de texto
    public static final int FIELD_WIDTH = 20;

    public ClientesUI(ArriendoCuotasUI padre, GestorCarRent gestor) {
        super(padre, "Ingresar nuevo Cliente", true);
        this.padre = padre;
        this.gestor = gestor;

        //visual
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        //cedula
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Cédula:"), gbc);

        txtCedula = new JTextField(FIELD_WIDTH);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtCedula, gbc);

        //nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Nombre:"), gbc);

        txtNombre = new JTextField(FIELD_WIDTH);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtNombre, gbc);

        chkVigente = new JCheckBox("Vigente", true);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(chkVigente, gbc);

        //agregar
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarClienteHandler());
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        add(btnAgregar, gbc);
        pack();
        setLocationRelativeTo(padre);
    }

    private void agregarClienteHandler() {
        String cedulaStr = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        boolean vigente = chkVigente.isSelected();

        modelo.Cedula cedulaObj;

        //nombre
        if (!modelo.Cliente.validarNombre(nombre)) {
            JOptionPane.showMessageDialog(this, "Nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //validacion de cedula
        //separa por guion.
        String[] partes = cedulaStr.split("-");

        if (partes.length == 2 && partes[1].length() == 1) {

            String numero = partes[0];
            char dv = partes[1].charAt(0);

            //intentar crear el objeto Cedula
            cedulaObj = new modelo.Cedula(numero, dv);

            //validar la cédula
            if (!cedulaObj.esValida()) {
                JOptionPane.showMessageDialog(this, "El número o dígito verificador es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } else {
            JOptionPane.showMessageDialog(this, "Cédula debe tener el formato NNNNN-DV (ej: 17980490-8).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //crear y guardar el cliente
        modelo.Cliente nuevoCliente = new modelo.Cliente(nombre, cedulaObj, vigente);
        gestor.agregarCliente(nuevoCliente);

        JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        //retornar a la interfaz de Arriendo con cuotas (POE)
        padre.actualizarClientes();
        dispose();
    }
}