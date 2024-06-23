package de.htwg.server;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

import de.htwg.provider.CorsFilter;
import de.htwg.provider.ObjectMapperProvider;
import de.htwg.provider.ResponseLoggingFilter;

@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("de.htwg.controller");
        register(CorsFilter.class);
        register(ObjectMapperProvider.class);
        register(ResponseLoggingFilter.class);

        register(JacksonFeature.class);
    }
}
