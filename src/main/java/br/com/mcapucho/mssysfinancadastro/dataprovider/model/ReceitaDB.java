package br.com.mcapucho.mssysfinancadastro.dataprovider.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_receita")
public class ReceitaDB {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String transactionId;

    @Column(name = "description", nullable = false, unique = true, length = 200)
    private String description;

    @Column(name = "status")
    private Boolean status;
}
