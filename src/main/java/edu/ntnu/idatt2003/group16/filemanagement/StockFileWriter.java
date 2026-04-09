package edu.ntnu.idatt2003.group16.filemanagement;

import edu.ntnu.idatt2003.group16.investment.Stock;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Extracts a list of stocks to file.
 *
 * @author Robin Strand Prestmo
 */
public class StockFileWriter {

  /**
   * Writes stock data to a CSV file.
   *
   * @param path the path to CSV file to save stock data.
   * @param stocks the stocks to be saved in file
   * @throws IOException if an IO error occurs writing to file.
   * @throws IllegalArgumentException if path or stocks is null.
   *     Or if a stock in list of stocks is null.
   */
  public void writeStocks(Path path, List<Stock> stocks) throws IOException {

    if (path == null || stocks == null) {
      throw new IllegalArgumentException("Path and Stocks cannot be null.");
    }

    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      for (Stock stock : stocks) {
        if (stock == null) {
          throw new IllegalArgumentException("Stock cannot be null");
        }

        String stockAsString = stock.getSymbol()
            + "," + stock.getCompany()
            + "," + stock.getCurrentPrice();

        writer.write(stockAsString);
        writer.newLine();
      }
    }
  }
}
