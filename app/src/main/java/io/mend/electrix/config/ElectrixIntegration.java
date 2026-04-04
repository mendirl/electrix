package io.mend.electrix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.aggregator.FileAggregator;
import org.springframework.integration.file.dsl.Files;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableIntegration
public class ElectrixIntegration {

    private final ElectrixProperties properties;

    public ElectrixIntegration(ElectrixProperties properties) {
        this.properties = properties;
    }

    //    polling from a path, split each line, aggregate the lines by a combined key with name and type,
    // create a new line based on the aggregation and then persist it into database
    // use spring-integration file support and dsl for that
    // https://docs.spring.io/spring-integration/reference/html/file.html
    // https://docs.spring.io/spring-integration/reference/html/dsl.html#java-dsl
    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlow
            .from(Files.inboundAdapter(properties.filepath().toFile()))
            .split()
            .channel(MessageChannels.executor(Executors.newSingleThreadExecutor()))
            .aggregate(new FileAggregator())
            .handle((payload, headers) -> {
                System.out.println(payload);
                return "Void";
            })
            .get();
    }
}
