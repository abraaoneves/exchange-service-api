package br.com.abneves.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Payer
 *
 * @author Abraao Neves
 * @version 0.0.1 21-06-2020
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payers")
@Entity(name = "Payer")
public class Payer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payerId;

    @Version
    private Long version;

    @Column(name = "total_of_payments")
    private Integer totalOfPayments;

    @OneToMany(mappedBy = "payer")
    private List<Payment> payments;

    public Integer getTotalOfPayments() {
        if (Objects.isNull(this.totalOfPayments)) {
            return 0;
        }

        return this.totalOfPayments;
    }

    public void incrementTotalOfPayments() {
        this.totalOfPayments = this.getTotalOfPayments() + 1;
    }
}
