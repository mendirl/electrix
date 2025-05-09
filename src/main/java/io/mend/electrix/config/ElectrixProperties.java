package io.mend.electrix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "electrix")
public record ElectrixProperties(Path filepath) {

}
