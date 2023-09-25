# Patronus Device Manager

### Intro

This is a Spring boot / Kotlin / Gradle service responsible for the management of 
the devices issued by Patronus and the users of those devices.

The service serves the User domain via ```/user``` and the Device domain via ```/device```.

A full API specification for the service can be found under ```/src/main/resources/static/apiSpec.yml```.

When the service is running, a swagger UI is also provided for easy access to the API. This can be found at
```/swagger-ui/index.html#/```.

For now the service uses an in-memory h2 database that is not persisted. As such changes do not survive a reboot.

### Dependencies
This service depends on Java 17.

### Development environment

To launch the service run ```./gradlew bootRun```. It will then be hosted on ```http://localhost:8080/```.

### Assumptions
The following assumptions were made when building the service from the initial requirements:

* The service will be expanded to provide a larger set of capabilities in the future - for example there
will be requirements to provide more functionality around get User - perhaps to get all users in a certain 
country, or search for users by name.
* When registering a device the service consumer will provide the UUID identifier for the device - the service 
is not responsible for creating the UUID.

### Areas for future improvement
 * Currently, no validation is done on provided inputs we may want to add validations in the future
   * User not already registered by name / address
   * User address is a valid address
   * Device phone number
   * Device model
 * Device UUID is used as the internal ID for the Device entities - perhaps separate out to have our own internal identifier
 * A user interface