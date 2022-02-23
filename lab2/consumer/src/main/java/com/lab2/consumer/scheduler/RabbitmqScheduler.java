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
    private final String containerId = "q_dummy";

    public RabbitmqScheduler(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(cron = "0 40 21 * * *")
    public void stopAll() {
        LOG.info("stopAll");
        registry.getListenerContainer(containerId).stop();
//        registry.getListenerContainers().forEach(c -> {
//                    LOG.info("stop listener container: {}", c);
//                    c.stop();
//                });
    }

    @Scheduled(cron = "0 41 21 * * *")
    public void startAll() {
        LOG.info("startAll");
        registry.getListenerContainer(containerId).start();
//        registry.getListenerContainers().forEach(c -> {
//            LOG.info("start listener container: {}", c);
//            c.start();
//        });
    }
}
