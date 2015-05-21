/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.target.tgcase.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author cservin
 */
@JsonIgnoreProperties(ignoreUnknown = true )
public class Item {
	
	@JsonProperty("relation")
	String relation;
	
	@JsonProperty("general_description")
	String description;

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	@Override
	public String toString() {
		return "Item{" + "relation=" + relation + ", description=" + description + '}';
	}
	
	
}
