package example.example.exception;

public class ShipmentConfirmationException extends AppException {

    public ShipmentConfirmationException(String message) {
        super(message);
    }

    public ShipmentConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }
}
