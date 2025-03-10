package pro.aidar.custom_container.ioc.factory.impl;

import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;
import pro.aidar.custom_container.ioc.factory.BeanFactory;

public class SimpleBeanFactory implements BeanFactory {

    @Override
    public Object createBean(Class<?> beanClass) throws BeanInitializationException {
        try {
            return beanClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BeanInitializationException("Error while bean creation: " + beanClass.getName(), e);
        }
    }

}
