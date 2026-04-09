package edu.ntnu.idatt2003.group16.filemanagement;

import edu.ntnu.idatt2003.group16.investment.Stock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockFileWriterTest {

  @Test
  void shouldWriteToFile() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Stock apple = new Stock("AAPL","Apple",new BigDecimal("100.00"));
    Stock microsoft = new Stock("MSFT","Microsoft",new BigDecimal("200.00"));

    List<Stock> stocks = new ArrayList<>();
    stocks.add(apple);
    stocks.add(microsoft);

    StockFileWriter writer = new StockFileWriter();

    writer.writeStocks(path, stocks);

    List<String> lines = Files.readAllLines(path);

    assertEquals(2, lines.size());
    assertEquals("AAPL,Apple,100.00", lines.get(0));
    assertEquals("MSFT,Microsoft,200.00", lines.get(1));
  }

  @Test
  void shouldThrowIfPathIsNull() {
    Path path = null;
    Stock apple = new Stock("AAPL","Apple",new BigDecimal("100.00"));
    Stock microsoft = new Stock("MSFT","Microsoft",new BigDecimal("200.00"));

    List<Stock> stocks = new ArrayList<>();
    stocks.add(apple);
    stocks.add(microsoft);

    StockFileWriter writer = new StockFileWriter();

    assertThrows(IllegalArgumentException.class, () ->
      writer.writeStocks(path,stocks));
  }

  @Test
  void shouldThrowIfStocksIsNull() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");

    List<Stock> stocks = null;

    StockFileWriter writer = new StockFileWriter();

    assertThrows(IllegalArgumentException.class, () ->
      writer.writeStocks(path,stocks));
  }

  @Test
  void shouldThrowIfStockIsNull() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");
    Stock apple = new Stock("AAPL","Apple",new BigDecimal("100.00"));
    Stock microsoft = null;

    List<Stock> stocks = new ArrayList<>();
    stocks.add(apple);
    stocks.add(microsoft);

    StockFileWriter writer = new StockFileWriter();

    assertThrows(IllegalArgumentException.class, () ->
      writer.writeStocks(path,stocks));
  }

  @Test
  void shouldWriteEmptyFileWhenStockListIsEmpty() throws IOException {
    Path path = Files.createTempFile("stocks", ".csv");

    List<Stock> stocks = new ArrayList<>();

    StockFileWriter writer = new StockFileWriter();
    writer.writeStocks(path, stocks);

    List<String> lines = Files.readAllLines(path);

    assertTrue(lines.isEmpty());
  }
}