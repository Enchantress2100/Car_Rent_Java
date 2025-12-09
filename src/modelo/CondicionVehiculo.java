package modelo;
import java.io.Serializable;

//estados del vehiculo
public enum CondicionVehiculo implements Serializable {
    DISPONIBLE,
    MANTENCION,
    ARRENDADO
}
