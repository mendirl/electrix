package io.mendirl.mcpserver.handlers;

import io.mendirl.mcpserver.infrastructure.ClickHouseDatabase;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToolsHandler {

  private final ClickHouseDatabase clickHouseDatabase;

  public ToolsHandler(ClickHouseDatabase clickHouseDatabase) {
    this.clickHouseDatabase = clickHouseDatabase;
  }

  @McpTool
  public List<String> listTables(String databaseName) {
    return clickHouseDatabase.listTables(databaseName);
  }
}
