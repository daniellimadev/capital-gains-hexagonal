package br.com.github.daniellimadev.stock.domain.service

import spock.lang.Specification

// Especificações do leitor de ações
class StockReaderSpec extends Specification {

    def "Entrada do processo quando String é uma linha única com uma matriz"() {
        given:
        def reader = new StockReader()

        and:
        def input = '[{"operation":"buy", "unit-cost":10.00, "quantity": 100},{"operation":"sell", "unit-cost":15.00, "quantity": 50},{"operation":"sell", "unit-cost":15.00, "quantity": 50}]'

        and:
        def stream = new ByteArrayInputStream(input.getBytes())

        when:
        def result = reader.process(stream)

        then:
        result
        !result.empty
        result.size() == 1
    }

    def "Entrada do processo quando String é multilinha com uma matriz"() {
        given:
        def reader = new StockReader()

        and:
        def input = '''[
            {"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}
        ]'''

        and:
        def stream = new ByteArrayInputStream(input.getBytes())

        when:
        def result = reader.process(stream)

        then:
        result
        !result.empty
        result.size() == 1
    }

    def "Entrada do processo quando String é multilinha com duas matrizes"() {
        given:
        def reader = new StockReader()

        and:
        def input = '''[
            {"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}
        ]
        [
            {"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}
        ]
        
        
        '''

        and:
        def stream = new ByteArrayInputStream(input.getBytes())

        when:
        def result = reader.process(stream)

        then:
        result
        !result.empty
        result.size() == 2
    }

}