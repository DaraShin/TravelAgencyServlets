package Model.Entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Order.class)
public class Order_ {
    public static volatile SingularAttribute<Order, Integer> orderId;
    public static volatile SingularAttribute<Order, Trip> trip;
    public static volatile SingularAttribute<Order, Client> client;
    public static volatile SingularAttribute<Order, Boolean> isPaid;
}
