package excepciones;

public class PalabraMuyCortaException extends Exception {
    public PalabraMuyCortaException(String mensaje) {
        super(mensaje);
    }
}