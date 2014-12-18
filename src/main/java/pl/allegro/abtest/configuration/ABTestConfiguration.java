package pl.allegro.abtest.configuration;

import io.dropwizard.Configuration;

import java.util.Collection;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;

public class ABTestConfiguration extends Configuration {

	@NotEmpty
	private Collection<GroupConfiguration> groups;
	
	@JsonProperty
	public void setGroups(Collection<GroupConfiguration> groups) {
		this.groups = groups;
	}
	
	public Map<String, Integer> groupsToMap() {
		Map<String,Integer> mapping = Maps.newHashMap();
		for (GroupConfiguration groupConfig: groups) {
			mapping.put(groupConfig.getName(), groupConfig.getWeight());
		}
		return mapping;
	}
}
