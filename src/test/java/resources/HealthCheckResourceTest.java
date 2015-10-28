package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class HealthCheckResourceTest extends BaseJerseyTest {

	@Test
	public void missingAccountTest() {
		Response response = target("health").request(MediaType.TEXT_HTML_TYPE).get();
		
		assertEquals(200, response.getStatus());
		Assert.assertTrue(response.readEntity(String.class).contains("Financial Models Application is healthy"));
	}
}
