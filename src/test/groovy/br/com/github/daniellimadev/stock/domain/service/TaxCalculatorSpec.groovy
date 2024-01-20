package br.com.github.daniellimadev.stock.domain.service


import spock.lang.Specification

import static br.com.github.daniellimadev.stock.domain.entity.Operation.BUY
import static br.com.github.daniellimadev.stock.domain.entity.Operation.SELL

// Calculadora de ImpostoSpec
class TaxCalculatorSpec extends Specification {

    def "Processa uma única lista de estoque, calcula impostos e responde a uma única lista de impostos"() {
        given:
        def calculator = new TaxCalculator()

        and:
        def input = [
            [
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(BUY, new BigDecimal("10.00"), 100L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("15.00"), 50L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("15.00"), 50L),
            ]
        ]

        when:
        def result = calculator.process(input)

        then:
        result
        !result.empty
        result.size() == 1
        result[0].size() == 3
    }

    def "Processar Lista de Lista de Ações, calcular imposto e responder Lista de Lista de Impostos"() {
        given:
        def calculator = new TaxCalculator()

        and:
        def input = [
            [
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(BUY, new BigDecimal("10.00"), 100L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("15.00"), 50L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("15.00"), 50L),
            ],
            [
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(BUY, new BigDecimal("10.00"), 10_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("2.00"), 5_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("20.00"), 2_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("20.00"), 2_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("25.00"), 1_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(BUY, new BigDecimal("20.00"), 10_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("15.00"), 5_000L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("30.00"), 4_350L),
                    new br.com.github.daniellimadev.stock.domain.entity.Stock(SELL, new BigDecimal("30.00"), 650L),
            ]
        ]

        when:
        def result = calculator.process(input)

        then:
        result
        !result.empty
        result.size() == 2
        result[0].size() == 3
        result[1].size() == 9
        result[1][4] == new br.com.github.daniellimadev.stock.domain.entity.Tax(new BigDecimal("3000.00"))
        result[1][7] == new br.com.github.daniellimadev.stock.domain.entity.Tax(new BigDecimal("3700.00"))
    }

}