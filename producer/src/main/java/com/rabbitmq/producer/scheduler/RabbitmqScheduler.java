package com.rabbitmq.producer.scheduler;

import com.rabbitmq.producer.client.RabbitmqClient;
import com.rabbitmq.producer.entity.RabbitmqQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RabbitmqScheduler {

    private final RabbitmqClient client;
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqScheduler.class);

    public RabbitmqScheduler(RabbitmqClient client) {
        this.client = client;
    }

    @Scheduled(fixedDelay = 90000)
    public void sweepDirtyQueue() {
        try {
            List<RabbitmqQueue> dirtyQueues = client.getAllQueue().stream().filter(RabbitmqQueue::isDirty).collect(Collectors.toList());
            dirtyQueues.forEach(dq -> LOG.info("Queue name={} has {} unprocessed messages", dq.getName(), dq.getMessages()));
        } catch (Exception e) {
            LOG.warn("failed to sweep dirty queue. message: {} ", e.getMessage());
        }
    }
}
