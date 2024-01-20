package br.com.github.daniellimadev.stock.domain.service;

import br.com.github.daniellimadev.stock.domain.entity.Tax;
import br.com.github.daniellimadev.stock.domain.service.shared.Service;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.control.Try;

import java.util.List;
import java.util.stream.Collectors;

// Analisador de impostos
public class TaxParser implements Service<List<List<Tax>>, String> {

    @Override
    public String process(List<List<Tax>> input) {
        return input.stream()
            .map(this::parseTax)
            .collect(Collectors.joining("\n"));
    }

    private String parseTax(List<Tax> taxes) {
        return Try.of(() -> JsonMapper.builder()
                .build()
                .writeValueAsString(taxes))
            .getOrElse("");
    }

}
