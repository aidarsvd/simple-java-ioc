package pro.aidar.custom_container.ioc.exceptions;

import lombok.Getter;

@Getter
public class BeanInitializationException extends Throwable {

    private final String message;

    public BeanInitializationException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public BeanInitializationException(String message) {
        super(message);
        this.message = message;
    }

}
