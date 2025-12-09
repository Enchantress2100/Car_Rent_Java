package vista;

import controlador.GestorCarRent;
import controlador.*;
import javax.swing.*;
import java.awt.*;

public class PrincipalUI extends JFrame {
    private GestorCarRent gestor;

    public PrincipalUI() {
        this.gestor = new GestorCarRent(); // Inicializa el controlador y carga datos
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Car-REnt - Sistema de Arriendo de Vehículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //barra menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOperaciones = new JMenu("Operaciones");

        JMenuItem itemArrendarCuotas = new JMenuItem("Arrendar con Cuotas");
        JMenuItem itemPagarCuota = new JMenuItem("Pagar Cuota");

        //conexion de eventos
        itemArrendarCuotas.addActionListener(e -> new ArriendoCuotasUI(this, gestor).setVisible(true));
        itemPagarCuota.addActionListener(e -> new PagarCuotasUI(this, gestor).setVisible(true));

        menuOperaciones.add(itemArrendarCuotas);
        menuOperaciones.add(itemPagarCuota);
        menuBar.add(menuOperaciones);
        setJMenuBar(menuBar);

        //contenido: titulo
        JPanel panelCentral = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("BIENVENIDO A CAR-RENT", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelCentral.add(lblTitulo, BorderLayout.CENTER);

        add(panelCentral);
    }
}