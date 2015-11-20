package com.patrickkee.jaxrs.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("")
public class BaseApplication extends ResourceConfig {
    public BaseApplication() {
        packages("com.patrickkee.resources");
    }
    
}