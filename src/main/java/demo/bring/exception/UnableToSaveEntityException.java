package demo.bring.exception;

public class UnableToSaveEntityException extends RuntimeException {

    public UnableToSaveEntityException(String message) {
        super(message);
    }
}
