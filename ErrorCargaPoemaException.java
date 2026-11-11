package excepciones;

public class ErrorCargaPoemaException extends Exception {
    public ErrorCargaPoemaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}