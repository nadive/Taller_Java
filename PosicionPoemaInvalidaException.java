package excepciones;

public class PosicionPoemaInvalidaException extends Exception {
    public PosicionPoemaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}