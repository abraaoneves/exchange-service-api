package br.com.abneves.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @GeneratedValue
    private Long payerId;

    @OneToMany(mappedBy = "payer")
    private List<Payment> payments;
}
