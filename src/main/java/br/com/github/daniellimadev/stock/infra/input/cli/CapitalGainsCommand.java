package br.com.github.daniellimadev.stock.infra.input.cli;

import br.com.github.daniellimadev.stock.application.port.input.CapitalGainsInputPort;

public class CapitalGainsCommand {

    public static void main(String[] args) {
        var useCase = new CapitalGainsInputPort();
        System.out.println(useCase.calculateTaxes());
    }

}