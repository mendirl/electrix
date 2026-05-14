package io.mend.electrix.ingestion.infrastructure;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class ResourceDownloader {

  private final RestClient restClient;

  public ResourceDownloader(RestClient restClient) {
    this.restClient = restClient;
  }

  public File download(Resource resource) throws IOException {
    File tempFile = File.createTempFile("ingestion-", "-" + resource.getFilename());
    tempFile.deleteOnExit();

    try {
      if (resource.getURL().getProtocol().startsWith("http")) {
        byte[] content = restClient.get()
          .uri(resource.getURI())
          .retrieve()
          .body(byte[].class);
        if (content != null) {
          Files.write(tempFile.toPath(), content);
          return tempFile;
        }
      }
    } catch (Exception e) {
      // Fallback to standard resource loading if URL is not HTTP or RestClient fails
    }

    try (var inputStream = resource.getInputStream()) {
      Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    return tempFile;
  }
}
