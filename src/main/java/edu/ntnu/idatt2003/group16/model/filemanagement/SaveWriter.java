package edu.ntnu.idatt2003.group16.model.filemanagement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ntnu.idatt2003.group16.model.dto.GameSessionDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveWriter {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void saveGame(GameSessionDto gameSession) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    try {
      Files.createDirectories(Paths.get( "target/save-files"));
      objectMapper.writeValue(new File("target/save-files/save.json"),
          gameSession);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
