package com.equiron;

import com.equiron.endpoints.DealsEndpoint;
import com.equiron.store.Deals;
import org.springframework.context.annotation.Bean;

/**
 * @author mg
 */
public class EquironTestConfig extends EquironConfig {

    @Bean
    public Deals delasRepository() {
        return new Deals();
    }

    @Bean
    public DealsEndpoint dealsEndpoint() {
        return new DealsEndpoint();
    }

}
