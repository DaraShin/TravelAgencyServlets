package Model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Client of travel agency.
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "client_sequence")
    @Column(name = "client_id")
    @Getter
    @Setter
    private int clientId;
    @Getter
    @Setter
    private String surname;
    @Getter
    @Setter
    private String firstname;
    @Getter
    @Setter
    private String middlename;
    @Column(columnDefinition = "number default 0")
    @Getter
    @Setter
    private Integer discount;
    
    @Column(name = "HAS_DISCOUNT", columnDefinition = "number(1,0)")
    @Getter
    @Setter
    private Boolean hasDiscount;

    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            mappedBy = "client") // имя поля во втором классе связи
    @Getter
    @Setter
    private List<Order> orders = new ArrayList<>();
    
    @Getter
    @Setter
    @OneToOne(mappedBy = "client")
    private User user;

    /**
     * Creates String with information about client.
     * @return String representation of client.
     */
    @Override
    public String toString() {
        String clientString = "ID: " + clientId
                + ". Name: " + surname + " " + firstname + " " + middlename
                + ". Discount: " + discount;
        return clientString;
    }
}
