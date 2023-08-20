package Model.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents trip entity from database.
 */
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "trip_sequence")
    @Column(name = "trip_id")
    @Getter
    @Setter
    private int tripId;
    
    @Getter
    @Setter
    private String destination;

    @Column(name = "trip_type")
    @Getter
    @Setter
    private String tripType;

    @Getter
    @Setter
    private double price;

    @Column(name = "is_hot_tour", columnDefinition = "number(1,0)")
    @Getter
    @Setter
    private boolean isHotTour;

    @OneToMany(
            cascade = {CascadeType.MERGE,CascadeType.REMOVE},
            mappedBy = "trip")
    @Getter
    @Setter
    private List<Order> orders = new ArrayList<>();

    /**
     * Creates String with information about trip.
     * @return String representation of trip.
     */
    @Override
    public String toString() {
        String tripString = "ID: " + tripId
                +". Type: " + tripType
                +". Price: "+String.format("%.2f", price);
        if(isHotTour){
            tripString += " !!! hot tour";
        }
        return tripString;
    }
}
