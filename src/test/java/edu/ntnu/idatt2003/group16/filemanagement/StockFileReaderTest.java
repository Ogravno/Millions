package edu.ntnu.idatt2003.group16.filemanagement;

import edu.ntnu.idatt2003.group16.model.filemanagement.StockFileReader;
import edu.ntnu.idatt2003.group16.model.investment.Stock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockFileReaderTest {

  @Test
  void shouldReadStocksFromFile() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Files.write(path, List.of(
      "#Test data",
      " ",
      "AAPL,Apple,100.00",
      "MSFT,Microsoft,200.00"
    ));

    StockFileReader reader = new StockFileReader();

    List<Stock> stocks = reader.readStocks(path);

    assertNotNull(stocks);
    assertFalse(stocks.isEmpty());
    assertEquals(2, stocks.size());
  }

  @Test
  void shouldIgnoreCommentLines() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Files.write(path, List.of(
      "#Text",
      "AAPL,Apple,100.00"
    ));

    StockFileReader reader = new StockFileReader();

    List<Stock> stocks = reader.readStocks(path);

    assertEquals(1, stocks.size());
  }

  @Test
  void shouldIgnoreBlankLines() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Files.write(path, List.of(
      " ",
      "AAPL,Apple,100.00"
    ));

    StockFileReader reader = new StockFileReader();

    List<Stock> stocks = reader.readStocks(path);

    assertEquals(1, stocks.size());
  }

  @Test
  void shouldThrowExceptionWhenLinesHasInvalidFormat() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Files.write(path, List.of(
      " ",
      "AAPL,Apple,100.00, 3"
    ));

    StockFileReader reader = new StockFileReader();

    assertThrows(IllegalArgumentException.class, () -> reader.readStocks(path));
  }

  @Test
  void shouldReturnEmptyListIfFileContainsOnlyCommentAndBlankLines() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Files.write(path, List.of(
      " ",
      "#AAPL,Apple,100,1"
    ));

    StockFileReader reader = new StockFileReader();
    List<Stock> stocks = reader.readStocks(path);
    List<Stock> empty = new ArrayList<>();

    assertEquals(empty, stocks);
  }
}