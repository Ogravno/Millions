package edu.ntnu.idatt2003.group16.model.filemanagement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ntnu.idatt2003.group16.model.dto.GameSessionDto;
import java.io.File;
import java.io.IOException;

/**
 * Class used for reading game data from json files.
 *
 * @author Odin Grav
 */
public class SaveReader {
  /**
   * Loads game data from a json file.
   *
   * @param fileName the name of the file to load game data from
   * @throws RuntimeException if an IO error occurs while reading from file.
   */
  public static GameSessionDto loadGame(String fileName) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    try {
      return objectMapper.readValue(new File("target/save-files/" + fileName + ".json"),
          GameSessionDto.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
