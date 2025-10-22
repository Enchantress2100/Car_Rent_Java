// Clase base para todos los usuarios del sistema.
public abstract class Usuario {
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