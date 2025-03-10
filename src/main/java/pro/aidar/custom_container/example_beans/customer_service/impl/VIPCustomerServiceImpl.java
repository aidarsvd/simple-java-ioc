package pro.aidar.custom_container.example_beans.customer_service.impl;

import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.example_beans.customer_service.CustomerService;

import java.util.List;

@Bean
public class VIPCustomerServiceImpl implements CustomerService {
    @Override
    public List<String> getUsers() {
        return List.of("user_100", "user_200");
    }
}
