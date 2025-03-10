package pro.aidar.custom_container.example_beans.customer_notification.impl;

import pro.aidar.custom_container.ioc.annotations.Bean;
import pro.aidar.custom_container.ioc.annotations.Inject;
import pro.aidar.custom_container.ioc.annotations.Named;
import pro.aidar.custom_container.example_beans.customer_notification.NotificationService;
import pro.aidar.custom_container.example_beans.customer_service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@Bean
public class TermsUpdateServiceImpl implements NotificationService {

    @Inject
    private CustomerService vipCustomerService;

    @Inject
    @Named("basic-customer-service")
    private CustomerService basicCustomerService;

    // Notify all type of users
    @Override
    public List<String> getWhiteList() {
        List<String> allUsers = new ArrayList<>();

        allUsers.addAll(vipCustomerService.getUsers());
        allUsers.addAll(basicCustomerService.getUsers());

        return allUsers;
    }
}
