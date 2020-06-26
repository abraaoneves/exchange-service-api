package br.com.abneves.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Payment
 *
 * @author Abraao Neves
 * @version 0.0.1 21-06-2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payment")
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long paymentId;

    @Column(name = "products_value")
    private Integer productsValue;

    @Column(name = "total_received")
    private Integer totalReceived;

    @Column(name = "discount")
    private Integer discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_fk_id", nullable = false)
    private Payer payer;

    protected boolean isAllowedToExchange() {
        return (this.totalReceived > 0 && this.totalReceived >= this.productsValue);
    }
}
