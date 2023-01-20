package org.sfw.micro.ecommerce.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.sfw.micro.ecommerce.cart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/cart-service")
public class CartController {

	@Autowired
	private RestTemplate restTemplate;
	
	private String BASE_URI = "http://PRODUCT-SERVICE/product-service/";

	List<Product> productList = new ArrayList<>();

	@GetMapping("/add-many-to-cart/{category}/{brand}")
	public List<Product> addManyToCart(@PathVariable("category") String category, @PathVariable("brand") String brand) {
System.out.println("Cart Controller add many to cart");
		String uri = BASE_URI + "products-by-category/" + category;
		System.out.println(uri);
		List<Product> tempList = new ArrayList<>();
		
		List<LinkedHashMap<String, Object>> response = restTemplate.getForObject(uri, List.class);
		
		for (LinkedHashMap<String, Object> productMap : response) {
			Integer pID = (Integer) productMap.get("productID");
			String name = (String) productMap.get("name");
			String brnd = (String) productMap.get("brand");
			String categry = (String) productMap.get("category");
			Double price = (Double) productMap.get("price");
			Product product = new Product(pID, name, brnd, categry, price);
			tempList.add(product);
		}
		productList = tempList.stream().filter((prod) -> prod.getBrand().equals(brand)).collect(Collectors.toList());
		return productList;
	}

	@GetMapping("/add-one-to-cart/{productID}")
	public Product addOneToCart(@PathVariable("productID") int id) {
		productList.clear();
		String url = BASE_URI + "product-by-id/" + id;
		Product product = restTemplate.getForObject(url, Product.class);
		productList.add(product);
		return product;
	}

	@GetMapping("/view-cart")
	public List<Product> viewCart() {
		return productList;
	}
}