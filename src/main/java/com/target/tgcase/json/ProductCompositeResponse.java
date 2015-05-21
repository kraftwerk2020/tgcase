/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.target.tgcase.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author cservin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCompositeResponse {
	
	@JsonProperty("items")
	Item [] items;

	@Override
	public String toString() {
		return "ProductCompositeResponse{" + "items=" + Arrays.deepToString( items ) + '}';
	}
	
	
}
