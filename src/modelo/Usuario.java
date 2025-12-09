package modelo;
import java.io.Serializable;

//clase base para todos los usuarios del sistema.
public abstract class Usuario implements Serializable {
    protected String nombre;
    protected Cedula cedula;

    public Usuario(String nombre, Cedula cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }
}