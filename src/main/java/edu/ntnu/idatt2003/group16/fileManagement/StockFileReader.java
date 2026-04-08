package edu.ntnu.idatt2003.group16.fileManagement;

import edu.ntnu.idatt2003.group16.investment.Stock;

import java.io.BufferedReader;
import java.io.IOException;
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
}
