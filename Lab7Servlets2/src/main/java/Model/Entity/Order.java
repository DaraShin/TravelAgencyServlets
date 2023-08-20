package Model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class that represents order entity from database.
 */
@Entity
@Table(name = "ORDERS")
@NamedQueries({
        @NamedQuery(name = "numberOfPaidOrders", query = "SELECT COUNT(o) FROM Order o WHERE o.isPaid = 1 AND o.client.clientId = :pClientId")
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_sequence")
    @Column(name = "order_id")
    @Getter
    @Setter
    private int orderId;

    @ManyToOne(
            cascade = {CascadeType.MERGE})
    @JoinColumn(name = "trip_id")
    @Getter
    @Setter
    private Trip trip;

    @ManyToOne(
            cascade = {CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    @Getter
    @Setter
    private Client client;

    @Column(name = "is_paid", columnDefinition = "number(1,0)")
    @Getter
    @Setter
    private boolean isPaid;

    /**
     * Creates String with information about order. Contains ID of client and trip.
     * @return String representation of order.
     */
    @Override
    public String toString() {
        String orderString = "ID: " + orderId
                + ". Client ID: " + client.getClientId()
                + ". Trip ID: " + trip.getTripId()
                + ".\nIs paid: " + isPaid;
        return orderString;
    }

    /**
     * Creates String with detailed information about order.
     * Includes information about client and trip in order.
     * @return String representation of order.
     */
    public String getDetailedInfo() {
        String orderString = "ID: " + orderId
                + "\nClient info: " + client
                + "\nTrip info: " + trip
                + "\nIs paid: " + isPaid;
        return orderString;
    }
}
