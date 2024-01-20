package br.com.github.daniellimadev.stock.domain.service;

import br.com.github.daniellimadev.stock.domain.entity.Operation;
import br.com.github.daniellimadev.stock.domain.entity.Stock;
import br.com.github.daniellimadev.stock.domain.entity.Tax;
import br.com.github.daniellimadev.stock.domain.service.shared.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.FLOOR;

// Calculadora de impostos
public class TaxCalculator implements Service<List<List<Stock>>, List<List<Tax>>> {

    private static final BigDecimal PERCENTAGE_TAX = new BigDecimal("0.2");
    private static final BigDecimal TOTAL_AMOUNT = new BigDecimal("20000.00");

    private static final Tax ZERO_TAX = new Tax(new BigDecimal("0.00"));

    @Override
    public List<List<Tax>> process(List<List<Stock>> input) {
        return input.stream()
            .map(this::calculateTaxes)
            .toList();
    }

    private List<Tax> calculateTaxes(List<Stock> stocks) {
        var taxes = new ArrayList<Tax>();

        var loss = new BigDecimal("0.0");
        var totalStock = new BigDecimal("0.0");
        var weightedAveragePrice = new BigDecimal("0.0");
        var previousBuy = new Stock(Operation.BUY, new BigDecimal("0.0"), 0L);

        for (var stock : stocks) {
            switch (stock.operation()) {
                case BUY -> {
                    weightedAveragePrice = weightedAveragePrice(totalStock, weightedAveragePrice, stock);
                    totalStock = totalStock.add(stock.quantityDecimal());
                    previousBuy = getPreviousBuy(totalStock, previousBuy, stock);
                    taxes.add(ZERO_TAX);
                }
                case SELL -> {
                    totalStock = totalStock.subtract(stock.quantityDecimal());
                    var profit = calculateProfit(stock, weightedAveragePrice);
                    var tax = calculateTax(stock, weightedAveragePrice, profit, loss);
                    loss = calculateLoss(loss, profit);
                    previousBuy = getPreviousBuy(totalStock, previousBuy, stock);
                    taxes.add(tax);
                }
            }
        }

        return taxes;
    }

    private BigDecimal weightedAveragePrice(BigDecimal totalStockQuantity, BigDecimal weightedAveragePrice, Stock newBuy) {
        var currentCalculus = totalStockQuantity.multiply(weightedAveragePrice);
        var newCalculus = newBuy.quantityDecimal().multiply(newBuy.unitCost());
        var quantitySum = totalStockQuantity.add(newBuy.quantityDecimal());

        return currentCalculus.add(newCalculus).divide(quantitySum, 2, FLOOR);
    }

    private Stock getPreviousBuy(BigDecimal totalStock, Stock previousBuy, Stock newBuy) {
        if (totalStock.compareTo(new BigDecimal("0.0")) == 0) {
            return new Stock(Operation.BUY, new BigDecimal("0.0"), 0L);
        }
        if (Operation.SELL.equals(newBuy.operation())) {
            return previousBuy;
        }
        return newBuy;
    }

    private BigDecimal calculateProfit(Stock sell, BigDecimal weightedAveragePrice) {
        var totalAmount = sell.totalAmount();
        var currentGain = sell.quantityDecimal().multiply(weightedAveragePrice);
        return totalAmount.subtract(currentGain);
    }

    private BigDecimal calculateLoss(BigDecimal loss, BigDecimal profit) {
        if (isLoss(loss) || isLoss(profit)) {
            return loss.add(profit);
        }
        return loss;
    }

    private boolean isLoss(BigDecimal quantity) {
        return quantity.compareTo(new BigDecimal("0.0")) == -1;
    }

    private Tax calculateTax(Stock sell, BigDecimal weightedAveragePrice, BigDecimal profit, BigDecimal loss) {
        if (sell.isLowerEqualTotalAmount(TOTAL_AMOUNT)) {
            return ZERO_TAX;
        }

        if (sell.unitCost().compareTo(weightedAveragePrice) == -1) {
            return ZERO_TAX;
        }

        var calculatedProfit = profit.add(loss);

        if (isLoss(calculatedProfit)) {
            return ZERO_TAX;
        }

        return new Tax(calculatedProfit.multiply(PERCENTAGE_TAX).setScale(2, FLOOR));
    }

}
