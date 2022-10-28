package com.sketchbrain.gateway;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class FileServerFilter extends AbstractGatewayFilterFactory<FileServerFilter.Config> {
    //Constructor
    public FileServerFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("FileServerFilter baseMessage>>>>>>" + config.getBaseMessage());
            if (config.isPrelogger()) {
                log.info("FileServerFilter Start>>>>>>" + exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostlogger()) {
                    log.info("FileServerFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        });
    }


    @Data
    public static class Config{
        private String baseMessage;
        private boolean prelogger;
        private boolean postlogger;
    }
}