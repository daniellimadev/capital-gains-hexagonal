package br.com.github.daniellimadev.stock.domain.service;

import br.com.github.daniellimadev.stock.domain.entity.Stock;
import br.com.github.daniellimadev.stock.domain.service.shared.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.control.Try;

import java.util.List;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;

// Analisador de ações
public class StockParser implements Service<List<String>, List<List<Stock>>> {

    @Override
    public List<List<Stock>> process(List<String> lines) {
        return lines.stream()
            .map(this::toList)
            .toList();
    }

    private List<Stock> toList(String stocks) {
        return Try.of(() -> JsonMapper.builder()
                .enable(ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build()
                .readValue(stocks, new TypeReference<List<Stock>>() {}))
            .getOrElse(List.of());
    }

}
