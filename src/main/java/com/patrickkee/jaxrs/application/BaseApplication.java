package com.patrickkee.jaxrs.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class BaseApplication extends ResourceConfig {
    public BaseApplication() {
        packages("com.patrickkee.resources");
    }
    
}