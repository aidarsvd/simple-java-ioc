package pro.aidar.custom_container;

import org.junit.jupiter.api.Test;
import pro.aidar.custom_container.ioc.IoCContainer;
import pro.aidar.custom_container.ioc.SimpleContainer;
import pro.aidar.custom_container.ioc.exceptions.BeanDefinitionException;
import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerTest {

    @Test
    public void testBean() throws BeanInitializationException, BeanDefinitionException {
        IoCContainer container = new SimpleContainer();

        Integer bean = 1;
        container.addBean(bean);

        Number getBean = container.getBean(Number.class);
        assertEquals(bean, getBean);
    }

    @Test
    public void testNamedBean() throws BeanInitializationException, BeanDefinitionException {

        IoCContainer container = new SimpleContainer();

        Object bean1 = new Object();
        Object bean2 = new Object();

        container.addBean("bean1", bean1);
        container.addBean("bean2", bean2);

        assertEquals(bean1, container.getBean("bean1"));
        assertEquals(bean2, container.getBean("bean2"));

    }

}
