package pro.aidar.custom_container.beans;

import lombok.Getter;
import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.ioc.annotations.Inject;
import pro.aidar.custom_container.ioc.annotations.Named;

@Bean
@Named("mockBean")
@Getter
public class MockBean {

    @Inject
    private MockBean2 injectedBean;

}
