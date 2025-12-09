package modelo;
import java.io.Serializable;

public class Gerente extends Empleado implements Serializable {

    public Gerente(String nombre, Cedula cedula, int idGerente) {
        super(nombre, cedula, idGerente);
    }
}