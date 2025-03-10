# Simple IoC Container

A lightweight Inversion of Control (IoC) container for Java that supports manual bean registration and automatic package scanning for annotated components.

## Features

- Manual bean registration by instance or class.
- Automatic scanning of packages to discover annotated beans.
- Dependency injection using `@Inject` annotation.
- Named bean qualification using `@Named` annotation.
- Simple and lightweight design.

## Usage

### Define Beans

Annotate your classes with `@Bean` to mark them as IoC-managed components:

```java
@Bean
public class MyService {
    public void doSomething() {
        System.out.println("Hello from MyService!");
    }
}
```

### Inject Dependencies

Use `@Inject` to automatically wire dependencies:

```java
@Bean
public class MyController {
    @Inject
    private MyService myService;

    public void execute() {
        myService.doSomething();
    }
}
```

### Qualify Beans with `@Named`

If multiple beans of the same type exist, use `@Named` to specify a unique qualifier:

```java
@Bean
@Named("specialService")
public class SpecialService extends MyService {
    @Override
    public void doSomething() {
        System.out.println("Hello from SpecialService!");
    }
}
```

### Initialize IoC Container

Create an instance of `IoCContainer`, register beans manually or scan packages:

```java
public class Main {
    public static void main(String[] args) throws BeanInitializationException, BeanDefinitionException {
        IoCContainer container = new SimpleIoCContainer();
        container.addScanningPackage("com.example.app");
        try {
            container.startScanning();
        } catch (BeanInitializationException | BeanDefinitionException e) {
            // TODO handle exception
        }
        MyController controller = container.getBean(MyController.class);
        controller.execute();
    }
}
```

### Manual Bean Registration

You can manually add beans to the container:

```java
IoCContainer container = new SimpleIoCContainer();
container.addBean(new MyService());
container.addBean("customService", new SpecialService());
```

Retrieve beans from the container:

```java
MyService myService = container.getBean(MyService.class);
MyService customService = container.getBean("customService", MyService.class);
```

### Annotations

#### `@Bean`

Marks a class as an IoC-managed component.

#### `@Named(String value)`

Assigns a unique name to a bean, useful for distinguishing between multiple beans of the same type.

#### `@Inject`

Marks a field for dependency injection.

## Exception Handling

- `BeanInitializationException`: Thrown when a bean fails to initialize.
- `BeanDefinitionException`: Thrown when a requested bean is not found or has conflicting definitions.