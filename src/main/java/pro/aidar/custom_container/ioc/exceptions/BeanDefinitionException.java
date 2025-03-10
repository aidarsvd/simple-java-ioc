package pro.aidar.custom_container.ioc.exceptions;

import lombok.Getter;

@Getter
public class BeanDefinitionException extends Throwable {

    private final String message;

    public BeanDefinitionException(String message) {
        super(message);
        this.message = message;
    }

    public BeanDefinitionException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }
}
