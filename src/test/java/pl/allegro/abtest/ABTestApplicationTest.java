package pl.allegro.abtest;

import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.IOException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import pl.allegro.abtest.configuration.ABTestConfiguration;
import pl.allegro.abtest.resources.Group;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class ABTestApplicationTest {

	private static final String TEST = "test";
	private static final String GROUP = "testgroup";

	@ClassRule
	public static final DropwizardAppRule<ABTestConfiguration> RULE = new DropwizardAppRule<ABTestConfiguration>(
			ABTestApplication.class, "src//test//resources//configuration.yml");

	@Test
	public void testRouteHttpCall() throws IOException {
		Client client = new Client();

		ClientResponse response = client.resource(
				String.format("http://localhost:%d/route?id=%s",
						RULE.getLocalPort(), TEST)).get(ClientResponse.class);

		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(response.getEntity(Group.class).getId(), GROUP);
	}

}
