package br.com.github.daniellimadev.stock.domain.service.shared;

public interface Service<I, O> {

    O process(I input);

}
