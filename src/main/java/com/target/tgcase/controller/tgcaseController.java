/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.target.tgcase.controller;

import com.target.tgcase.entity.Price;
import com.target.tgcase.json.CompositeProduct;
import com.target.tgcase.json.Product;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author cservin
 */
@Controller
public class tgcaseController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("cassandraTemplate")
	private CassandraOperations cassandraOperations;
	
	final String uri = "https://api.target.com/products/v3/{id}?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public @ResponseBody	CompositeProduct productLookup(@PathVariable String id) {

		// lookup product from rest service by id
		Map<String, String> params = new HashMap<>();
		params.put( "id", id );
		Product product = restTemplate.getForObject( uri, Product.class, params );
		
		// lookup price
		Price p = cassandraOperations.selectOneById(Price.class, id );
		
		BigDecimal amount = (p == null ) ? null : p.getPrice();

		return new CompositeProduct(id, product.getDescription(), amount );
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public @ResponseBody	CompositeProduct setPrice(@PathVariable String id, @RequestParam(value = "price", required = true) String price) {
		
		Price ourPrice  = cassandraOperations.selectOneById(Price.class, price);
		
		if ( ourPrice == null ) {
			
			ourPrice = new Price(id, new BigDecimal( price ) );
			cassandraOperations.insert( ourPrice );
		} else {
			cassandraOperations.update( ourPrice );
	   }	
		
		return new CompositeProduct(id, "priceUpdate", ourPrice.getPrice() );
	}
}
