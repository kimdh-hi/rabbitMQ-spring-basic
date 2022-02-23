package com.lab2.consumer.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqScheduler {

    private final RabbitListenerEndpointRegistry registry;
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqScheduler.class);

    public RabbitmqScheduler(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(cron = "0 54 19 * * *")
    public void stopAll() {
        LOG.info("stopAll");
        registry.getListenerContainers().forEach(c -> {
                    LOG.info("stop listener container: {}", c);
                    c.stop();
                });
    }

    @Scheduled(cron = "0 55 19 * * *")
    public void startAll() {
        LOG.info("startAll");
        registry.getListenerContainers().forEach(c -> {
            LOG.info("start listener container: {}", c);
            c.start();
        });
    }
}
