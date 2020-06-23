package br.com.abneves.exchange.controllers.decorators;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public class PaymentPageDecorator<T> {

    private final Page<T> page;

    public PaymentPageDecorator(Page<T> page) {
        this.page = page;
    }

    @JsonProperty("payments")
    public List<T> getContent() {
        return this.page.getContent();
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

}
