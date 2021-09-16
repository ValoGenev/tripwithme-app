package app.exception.declarations.car;

import org.aspectj.apache.bcel.classfile.annotation.RuntimeTypeAnnos;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message) {
        super(message);
    }
}
