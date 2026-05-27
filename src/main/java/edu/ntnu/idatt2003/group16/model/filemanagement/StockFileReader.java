package edu.ntnu.idatt2003.group16.model.filemanagement;

import edu.ntnu.idatt2003.group16.model.investment.Stock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads file with stock-data.
 *
 * @author Robin Strand Prestmo
 */
public class StockFileReader {

  /**
   * Read stock data from a CSV file and returns a list of Stock objects.
   *
   * <p>The stocks in the fil must follow the required format: symbol, company, price.
   * Lines that are blank or that begins with '#' are ignored. The price must
   * use '.' as decimal separator.
   * </p>
   *
   * @param path the path to CSV file containing stock data.
   * @return a list of Stock objectives created from file.
   * @throws IOException if an I/O error occurs while reading the file.
   * @throws IllegalArgumentException if the file contains invalid data.
   */
  public List<Stock> readStocks(Path path) throws IOException {
    List<Stock> stocks = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      String line;

      while ((line = reader.readLine()) != null) {
        String trimmedLine = line.trim();

        if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
          continue;
        }

        String[] parts = trimmedLine.split(",");

        if (parts.length != 3) {
          throw new IllegalArgumentException("Invalid file format: " + line);
        }

        String symbol = parts[0].trim();
        String name = parts[1].trim();
        String priceText = parts[2].trim();

        if (symbol.isBlank() || name.isBlank() || priceText.isBlank()) {
          throw new IllegalArgumentException("Missing value in line: " + line);
        }

        BigDecimal price;
        try {
          price = new BigDecimal(priceText);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid price in line: " + line, e);
        }

        stocks.add(new Stock(symbol, name, price));
      }
    }

    return stocks;
  }

  /**
   * Reads stock data from an input stream and returns a list of Stock objects.
   *
   * <p>The stock data must follow the required CSV format:
   * symbol, company, price.
   * Lines that are blank or begin with '#' are ignored.
   * The price must use '.' as decimal separator.
   * </p>
   *
   * @param inputStream the input stream containing stock data.
   * @return a list of Stock objects created from the input stream.
   * @throws IOException if an I/O error occurs while reading the stream.
   * @throws IllegalArgumentException if the stream contains invalid data.
   */
  public List<Stock> readStocks(InputStream inputStream) throws IOException {
    List<Stock> stocks = new ArrayList<>();

    try (BufferedReader reader =
           new BufferedReader(new InputStreamReader(inputStream))) {

      String line;

      while ((line = reader.readLine()) != null) {
        String trimmedLine = line.trim();

        if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
          continue;
        }

        String[] parts = trimmedLine.split(",");

        if (parts.length != 3) {
          throw new IllegalArgumentException("Invalid file format: " + line);
        }

        String symbol = parts[0].trim();
        String name = parts[1].trim();
        String priceText = parts[2].trim();

        if (symbol.isBlank() || name.isBlank() || priceText.isBlank()) {
          throw new IllegalArgumentException("Missing value in line: " + line);
        }

        BigDecimal price;

        try {
          price = new BigDecimal(priceText);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid price in line: " + line, e);
        }

        stocks.add(new Stock(symbol, name, price));
      }
    }

    return stocks;
  }

}
