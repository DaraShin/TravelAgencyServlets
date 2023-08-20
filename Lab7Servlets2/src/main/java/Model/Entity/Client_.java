package Model.Entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
public class Client_ {
    public static volatile SingularAttribute<Client, Integer> clientId;
    public static volatile SingularAttribute<Client, String> surname;
    public static volatile SingularAttribute<Client, String> firstname;
    public static volatile SingularAttribute<Client, String> middlename;
    public static volatile SingularAttribute<Client, Integer> discount;
    public static volatile SingularAttribute<Client, Boolean> hasDiscount;
    public static volatile SingularAttribute<Client, User> user;
    
    public static volatile ListAttribute<Client, Order> orders;
}
