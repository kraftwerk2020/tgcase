/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.target.tgcase.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;



/**
 *
 * @author cservin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
	
	@JsonProperty("product_composite_response")
   ProductCompositeResponse cr;
	
	public String getDescription() {
		
		return (cr.items[0] != null ) ? cr.items[0].getDescription() : null;
	}

	@Override
	public String toString() {
		return "Product{" + "product_composite_response=" + cr + '}';
	}
}
