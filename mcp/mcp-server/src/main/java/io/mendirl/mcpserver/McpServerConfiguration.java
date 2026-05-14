package io.mendirl.mcpserver;

import io.mendirl.mcpserver.config.ClickhouseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({ClickhouseProperties.class})
@Configuration
public class McpServerConfiguration {
}
