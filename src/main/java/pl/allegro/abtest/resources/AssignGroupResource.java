package pl.allegro.abtest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Preconditions;

import pl.allegro.abtest.router.Router;


@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
public class AssignGroupResource {

	private Router router;

	public AssignGroupResource(Router router) {
		Preconditions.checkNotNull(router);
		this.router = router;
	}

	@GET
	public Group route(@QueryParam("id") String id) {
		Group group = new Group();
		group.setId(router.route(id));
		return group;
	}
}
