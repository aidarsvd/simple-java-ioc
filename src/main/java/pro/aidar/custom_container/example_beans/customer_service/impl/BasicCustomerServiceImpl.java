package pro.aidar.custom_container.example_beans.customer_service.impl;

import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.ioc.annotations.Named;
import pro.aidar.custom_container.example_beans.customer_service.CustomerService;

import java.util.List;

@Bean
@Named("basic-customer-service")
public class BasicCustomerServiceImpl implements CustomerService {

    @Override
    public List<String> getUsers() {
        return List.of("user_1", "user_2");
    }

}
