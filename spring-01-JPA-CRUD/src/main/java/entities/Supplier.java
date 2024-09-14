package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

//    Using a Foreign Key

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
//    private Address supplierAddress;

//    Using a Shared Primary Key

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Address supplierAddress;

}
