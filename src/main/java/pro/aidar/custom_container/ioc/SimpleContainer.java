package pro.aidar.custom_container.ioc;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.ioc.annotations.Inject;
import pro.aidar.custom_container.ioc.annotations.Named;
import pro.aidar.custom_container.ioc.exceptions.BeanDefinitionException;
import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;
import pro.aidar.custom_container.ioc.factory.BeanFactory;
import pro.aidar.custom_container.ioc.factory.impl.SimpleBeanFactory;
import pro.aidar.custom_container.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

import static org.reflections.scanners.Scanners.*;

@Slf4j
public class SimpleContainer implements IoCContainer {

    private final BeanFactory beanFactory = new SimpleBeanFactory();
    private final Map<String, Object> beans = new HashMap<>();
    private final List<String> packages = new ArrayList<>();

    @Override
    public void addScanningPackage(String packageName) {
        this.packages.add(packageName);
    }

    @Override
    public void addBean(Object bean) throws BeanInitializationException {
        String qualifier = bean.getClass().getName();
        this.addBean(qualifier, bean);
    }

    @Override
    public void addBean(Class<?> bean) throws BeanInitializationException {
        String qualifier = bean.getName();
        this.addBean(qualifier, bean);
    }

    @Override
    public void addBean(String qualifier, Object bean) throws BeanInitializationException {
        this.validateQualifier(qualifier);
        this.beans.put(qualifier, bean);
    }

    @Override
    public void addBean(String qualifier, Class<?> bean) throws BeanInitializationException {
        this.validateQualifier(qualifier);
        Object initializedBean = beanFactory.createBean(bean);
        this.beans.put(qualifier, initializedBean);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name) throws BeanDefinitionException {
        Object bean = beans.get(name);
        if (bean == null) {
            throw new BeanDefinitionException("Bean " + name + " is not found");
        }
        return (T) bean;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> type) throws BeanDefinitionException {
        Object o = beans.get(name);
        if (type.isInstance(o)) {
            return (T) o;
        } else {
            throw new BeanDefinitionException("Bean " + name + " is not found");
        }
    }

    // Ignores qualifier for avoiding confusion in Bean store querying
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) throws BeanDefinitionException {
        for (Object bean : this.beans.values()) {
            if (!bean.getClass().isAnnotationPresent(Named.class) && type.isInstance(bean)) {
                return (T) bean;
            }
        }
        throw new BeanDefinitionException("Bean " + type.getName() + " not found");
    }

    @Override
    public void startScanning() throws BeanInitializationException, BeanDefinitionException {
        if (!this.packages.isEmpty()) {
            for (String packageName : this.packages) {

                Reflections reflections = new Reflections(
                        new ConfigurationBuilder()
                                .setUrls(ClasspathHelper.forPackage(packageName))
                                .setScanners(FieldsAnnotated, SubTypes, TypesAnnotated)
                                .filterInputsBy(new FilterBuilder().includePackage(packageName))
                );

                // 1. Define beans in package, and put in container store
                Set<Class<?>> annotated = reflections.get(SubTypes.of(TypesAnnotated.with(Bean.class)).asClass());

                for (Class<?> annotatedClass : annotated) {
                    String qualifierName = annotatedClass.isAnnotationPresent(Named.class)
                            ? annotatedClass.getAnnotation(Named.class).value()
                            : annotatedClass.getName();

                    this.addBean(qualifierName, annotatedClass);
                }

                // 2. Define Injects in initialized beans
                Set<Field> injectFields = reflections.get(Scanners.FieldsAnnotated.with(Inject.class).as(Field.class));

                // 3. Process @Inject field
                for (Field field : injectFields) {
                    Class<?> declaringClass = field.getDeclaringClass();

                    if (!declaringClass.isAnnotationPresent(Bean.class)) {
                        throw new BeanDefinitionException("Injects are possible only for Beans");
                    }

                    // Define correct (field's declaring bean) target for the field
                    Object declaringObject;
                    if (declaringClass.isAnnotationPresent(Named.class)) {
                        declaringObject = this.getBean(declaringClass.getAnnotation(Named.class).value(), declaringClass);
                    } else {
                        declaringObject = this.getBean(declaringClass);
                    }

                    // Inject value to field
                    try {
                        ReflectionUtils.setFieldValue(
                                field,
                                declaringObject,
                                field.isAnnotationPresent(Named.class)
                                        ? this.getBean(field.getAnnotation(Named.class).value(), field.getType())
                                        : this.getBean(field.getType())
                        );
                    } catch (IllegalAccessException e) {
                        throw new BeanDefinitionException("Could not inject bean", e);
                    }
                }
            }

            log.info("{} packages were scanned, {} bean was founded", this.packages.size(), beans.size());

        } else {
            log.warn("Packages to scan is not defined!");
        }

    }

    private void validateQualifier(String qualifier) throws BeanInitializationException {
        if (this.beans.containsKey(qualifier)) {
            throw new BeanInitializationException("Bean with name " + qualifier + " is already exists, please use @Named annotation");
        }
    }

}
