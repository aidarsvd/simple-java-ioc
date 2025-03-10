package pro.aidar.custom_container;

import org.junit.jupiter.api.Test;
import pro.aidar.custom_container.beans.MockBean;
import pro.aidar.custom_container.beans.MockBean2;
import pro.aidar.custom_container.error.DemoBean;
import pro.aidar.custom_container.ioc.IoCContainer;
import pro.aidar.custom_container.ioc.SimpleContainer;
import pro.aidar.custom_container.ioc.exceptions.BeanDefinitionException;
import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContainerScanTest {

    @Test
    public void testScan() throws BeanInitializationException, BeanDefinitionException {
        IoCContainer container = new SimpleContainer();

        container.addScanningPackage("pro.aidar.custom_container.beans");

        container.startScanning();

        MockBean mockBean = container.getBean("mockBean", MockBean.class);
        MockBean2 mockBean2 = container.getBean(MockBean2.class);

        assertEquals(mockBean2, mockBean.getInjectedBean());

    }

    @Test
    public void testExceptionScan() {
        IoCContainer container = new SimpleContainer();

        container.addScanningPackage("pro.aidar.custom_container.error");

        assertThrows(BeanDefinitionException.class, () -> {
            container.startScanning();
        });

    }

    @Test
    public void testProgrammaticInjection() throws BeanInitializationException, BeanDefinitionException {
        IoCContainer container = new SimpleContainer();

        Number injectable = 0;

        container.addBean(injectable);
        container.addScanningPackage("pro.aidar.custom_container.error");
        container.startScanning();

        assertEquals(injectable, container.getBean(DemoBean.class).getNum());

    }

}
