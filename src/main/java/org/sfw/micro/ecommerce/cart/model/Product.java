package org.sfw.micro.ecommerce.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	private Integer productID;
	private String name;
	private String brand;
	private String category;
	private double price;

}
