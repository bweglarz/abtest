package pl.allegro.abtest.configuration;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupConfiguration {

	private String name;

	private int weight;

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	@NotEmpty
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public int getWeight() {
		return weight;
	}

	@JsonProperty
	@Min(value = 1)
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
