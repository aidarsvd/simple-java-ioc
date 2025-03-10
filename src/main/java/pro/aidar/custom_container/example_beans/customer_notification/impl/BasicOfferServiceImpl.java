package pro.aidar.custom_container.example_beans.customer_notification.impl;

import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.ioc.annotations.Inject;
import pro.aidar.custom_container.ioc.annotations.Named;
import pro.aidar.custom_container.example_beans.customer_notification.NotificationService;
import pro.aidar.custom_container.example_beans.customer_service.CustomerService;

import java.util.List;

@Bean
@Named("basic-offer-service")
public class BasicOfferServiceImpl implements NotificationService {

    @Inject
    @Named("basic-customer-service")
    private CustomerService basicCustomerService;

    @Override
    public List<String> getWhiteList() {
        return this.basicCustomerService.getUsers();
    }
}
