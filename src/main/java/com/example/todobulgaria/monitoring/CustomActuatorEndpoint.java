package com.example.todobulgaria.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Endpoint(id = "custom")
@Component
public class CustomActuatorEndpoint {

    // Dependency injection is available -> allow to receive db information, inject services
    // Can do different operations with the date -> read, write etc..

    @ReadOperation
    public  Map<String, String> customEndpoint(String username) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "Value");
        map.put("Username", username);
        return map;
    }
}
