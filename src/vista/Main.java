package vista;

import javax.swing.SwingUtilities; //GUI

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PrincipalUI().setVisible(true);
        });
    }
}
