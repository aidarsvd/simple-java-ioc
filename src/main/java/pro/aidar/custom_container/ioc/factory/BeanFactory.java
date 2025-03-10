package pro.aidar.custom_container.ioc.factory;

import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;

public interface BeanFactory {
    Object createBean(Class<?> beanClass) throws BeanInitializationException;
}
