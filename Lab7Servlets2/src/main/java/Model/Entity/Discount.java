package Model.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Discount {
	@Getter 
	@Setter
	@Column(name = "discount_value") 
	private Integer discountValue;
	
	@Getter 
	@Setter
	@Column(name = "discount_pk") 
	@Id
	private Integer diskountPK;
}
