package pro.aidar.custom_container.error;

import lombok.Getter;
import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.ioc.annotations.Inject;

@Bean
@Getter
public class DemoBean {

    @Inject
    Integer num;

}
