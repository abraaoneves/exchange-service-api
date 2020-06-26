package br.com.abneves.exchange.controllers.decorators;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
public class PaymentPageDecorator<T> {

    private final Page<T> page;

    @JsonProperty("payments")
    public List<T> getContent() {
        return page.getContent();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

}
