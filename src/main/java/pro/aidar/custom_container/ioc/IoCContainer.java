package pro.aidar.custom_container.ioc;

import pro.aidar.custom_container.ioc.exceptions.BeanDefinitionException;
import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;

public interface IoCContainer {

    void addScanningPackage(String packageName);

    void addBean(Object bean) throws BeanInitializationException;

    void addBean(Class<?> bean) throws BeanInitializationException;

    void addBean(String qualifier, Object bean) throws BeanInitializationException;

    void addBean(String qualifier, Class<?> bean) throws BeanInitializationException;

    <T> T getBean(String name) throws BeanDefinitionException;

    <T> T getBean(String name, Class<T> type) throws BeanDefinitionException;

    <T> T getBean(Class<T> type) throws BeanDefinitionException;

    void startScanning() throws BeanInitializationException, BeanDefinitionException;

}
