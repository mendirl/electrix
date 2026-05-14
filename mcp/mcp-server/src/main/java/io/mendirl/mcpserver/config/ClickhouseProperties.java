package io.mendirl.mcpserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mcp.server.clickhouse")
public record ClickhouseProperties(
  String url,
  String user,
  String password
) {
}
