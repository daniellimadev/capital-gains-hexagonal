package br.com.github.daniellimadev.stock.domain.service


import spock.lang.Specification

// Especificações do analisador de impostos
class TaxParserSpec extends Specification {

    def "Processar lista de impostos para uma única lista de string json analisada"() {
        given:
        def parser = new TaxParser()

        and:
        def input = [
            [
                    new br.com.github.daniellimadev.stock.domain.entity.Tax(new BigDecimal("0.0")),
                    new br.com.github.daniellimadev.stock.domain.entity.Tax(new BigDecimal("0.0")),
                    new br.com.github.daniellimadev.stock.domain.entity.Tax(new BigDecimal("0.0")),
            ]
        ]

        when:
        def result = parser.process(input)
        def matchTax = result =~ /tax/

        then:
        result
        !result.empty
        matchTax.size() == 3
    }

}