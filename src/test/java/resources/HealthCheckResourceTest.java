package resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.patrickkee.resources.HealthCheckResource;

public class HealthCheckResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(HealthCheckResource.class);
	}

	@Test
	public void missingAccountTest() {
		final String resp = target("health").request().get(String.class);
		Assert.assertTrue(resp.contains("Financial Models Application is healthy"));
	}
}
