// Representa la cédula de identidad, separando el número y el dígito verificador (maximo 10 caracteres)
public class Cedula {
    private String numero;
    private char digitoVerificador;

    public Cedula(String numero, char digitoVerificador) {
        this.numero = numero;
        this.digitoVerificador = digitoVerificador;
    }

    /**
     * Valida la cédula.
     * 1. El número debe tener entre 1 y 10 dígitos).
     * 2. El número debe contener solo dígitos ('0'-'9').
     * 3. El DV debe ser '0'-'9', 'k' o 'K'.
     */
    public boolean esValida() {

        // 1. Validar el largo (máximo 10 caracteres como pediste)
        if (this.numero == null || this.numero.isEmpty() || this.numero.length() > 10) {
            return false;
        }

        // 2. Validar que el string contenga solo números
        for (char c : this.numero.toCharArray()) {
            if (c < '0' || c > '9') {
                return false; // No es un numero
            }
        }

        // 3. Validar el dígito verificador (lógica anterior)
        char dv = Character.toLowerCase(this.digitoVerificador);
        boolean dvValido = (dv >= '0' && dv <= '9') || dv == 'k';

        return dvValido; // Ya validamos el número en los pasos 1 y 2
    }

    @Override
    public String toString() {
        return numero + "-" + digitoVerificador;
    }
}