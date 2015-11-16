package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.patrickkee.model.application.ApplicationStatus;

public class HealthCheckResourceTest extends BaseJerseyTest {

	@Test
	public void missingAccountTest() {
		Response response = target("health").request(MediaType.APPLICATION_JSON).get();
		
		assertEquals(200, response.getStatus());
		
		String status = response.readEntity(String.class);
		Assert.assertTrue(status.contains("Application is healthy!"));
	}
}
