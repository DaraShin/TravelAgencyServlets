package Model.Entity;


import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Trip.class)
public class Trip_ {
    public static volatile SingularAttribute<Trip, Integer> tripId;
    public static volatile SingularAttribute<Trip, String> destination;
    public static volatile SingularAttribute<Trip, String> tripType;
    public static volatile SingularAttribute<Trip, Double> price;
    public static volatile SingularAttribute<Trip, Boolean> isHotTour;

    public static volatile ListAttribute<Trip, Order> orders;
}
