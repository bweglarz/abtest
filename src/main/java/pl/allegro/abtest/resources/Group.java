package pl.allegro.abtest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Group {

	private String id;
	
	public Group() {
	}
	
	@JsonProperty
    public void setId(String id) {
        this.id = id;
    }
	
	@JsonProperty
    public String getId() {
        return id;
    }
}
