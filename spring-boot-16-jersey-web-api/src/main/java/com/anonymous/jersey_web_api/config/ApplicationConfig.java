package com.anonymous.jersey_web_api.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

// xai app : localhost:6969/jersey-web-api
// xai api : localhost:6969/jersey-web-api/api

@ApplicationPath("api")
public class ApplicationConfig extends ResourceConfig {

    // Cac class mapping request chua duong dan '/api' se do ResourceConfig phu trach

    public ApplicationConfig() {
        packages("com/anonymous/jersey_web_api/recourse");
    }
}
