package pl.allegro.abtest.resources;

import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import pl.allegro.abtest.router.Router;

public class AssignGroupResourceTest {

	private static final String TEST = "test";
	private static final String GROUP = "group0";

	private final static Router router = Mockito.mock(Router.class);

	@ClassRule
	public final static ResourceTestRule resources = ResourceTestRule.builder()
			.addResource(new AssignGroupResource(router)).build();

	@Before
	public void setUp() {
		Mockito.when(router.route(TEST)).thenReturn(GROUP);
	}

	@Test
	public void testRoute() {
		Group group = resources.client().resource("/route?id=" + TEST)
				.get(Group.class);
		Mockito.verify(router).route(TEST);
		Assert.assertEquals(group.getId(), GROUP);
	}
}
