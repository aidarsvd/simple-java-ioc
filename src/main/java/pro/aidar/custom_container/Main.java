package pro.aidar.custom_container;

import pro.aidar.custom_container.example_beans.customer_notification.NotificationService;
import pro.aidar.custom_container.example_beans.customer_service.CustomerService;
import pro.aidar.custom_container.ioc.exceptions.BeanDefinitionException;
import pro.aidar.custom_container.ioc.exceptions.BeanInitializationException;
import pro.aidar.custom_container.ioc.IoCContainer;
import pro.aidar.custom_container.ioc.SimpleContainer;

public class Main {
    public static void main(String[] args) throws BeanDefinitionException {

        // Example of usage

        IoCContainer container = new SimpleContainer();
        container.addScanningPackage("pro.aidar.custom_container.example_beans");

        try {
            container.startScanning();
        } catch (BeanInitializationException | BeanDefinitionException e) {
            throw new RuntimeException(e);
        }

        // Get list of VIP customers
        CustomerService vipCustomerService = container.getBean(CustomerService.class);
        System.out.println("VIP customers: " + vipCustomerService.getUsers());

        // Get list of Basic customers
        CustomerService basicCustomerService = container.getBean("basic-customer-service", CustomerService.class);
        System.out.println("Basic customers: " + basicCustomerService.getUsers());

        // Send notifications to VIP customers
        NotificationService vipOfferService = container.getBean("vip-offer-service", NotificationService.class);
        System.out.println("VIP offer sending to customers: " + vipOfferService.getWhiteList());

        // Send notifications to Basic customers
        NotificationService basicOfferService = container.getBean("basic-offer-service", NotificationService.class);
        System.out.println("Basic offer sending to customers: " + basicOfferService.getWhiteList());

        // Send update of AGB (for all users regardless on status)
        NotificationService termsUpdateService = container.getBean(NotificationService.class);
        System.out.println("AGB update sending to customers: " + termsUpdateService.getWhiteList());
    }
}