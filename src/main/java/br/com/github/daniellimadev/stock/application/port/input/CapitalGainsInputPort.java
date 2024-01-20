package br.com.github.daniellimadev.stock.application.port.input;

import br.com.github.daniellimadev.stock.application.usecase.CapitalGainsUseCase;
import br.com.github.daniellimadev.stock.domain.entity.Stock;
import br.com.github.daniellimadev.stock.domain.entity.Tax;
import br.com.github.daniellimadev.stock.domain.service.StockParser;
import br.com.github.daniellimadev.stock.domain.service.StockReader;
import br.com.github.daniellimadev.stock.domain.service.TaxCalculator;
import br.com.github.daniellimadev.stock.domain.service.TaxParser;
import br.com.github.daniellimadev.stock.domain.service.shared.Service;

import java.io.InputStream;
import java.util.List;

public class CapitalGainsInputPort implements CapitalGainsUseCase {

    private Service<InputStream, List<String>> stockReader;
    private Service<List<String>, List<List<Stock>>> stockParser;
    private Service<List<List<Stock>>, List<List<Tax>>> taxCalculator;
    private Service<List<List<Tax>>, String> taxParser;

    public CapitalGainsInputPort() {
        this.stockReader = new StockReader();
        this.stockParser = new StockParser();
        this.taxCalculator = new TaxCalculator();
        this.taxParser = new TaxParser();
    }

    @Override
    public String calculateTaxes() {
        var stockLines = stockReader.process(System.in);
        var stocks = stockParser.process(stockLines);
        var taxes = taxCalculator.process(stocks);
        var taxLines = taxParser.process(taxes);
        return taxLines;
    }

}
