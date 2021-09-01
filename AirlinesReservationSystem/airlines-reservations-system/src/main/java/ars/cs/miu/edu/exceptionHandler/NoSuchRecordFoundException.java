package ars.cs.miu.edu.exceptionHandler;

public class NoSuchRecordFoundException extends RuntimeException {
    public NoSuchRecordFoundException(String message) {
        super(message);
    }

    public NoSuchRecordFoundException() {
        this("No record available :(");
    }
}
