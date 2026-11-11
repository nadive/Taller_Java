package excepciones;

public class PalabraNoEncontradaException extends Exception {
    public PalabraNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}