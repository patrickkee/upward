package resources;

import org.junit.Assert;
import org.junit.Test;

public class HealthCheckResourceTest extends BaseJerseyTest {

	@Test
	public void missingAccountTest() {
		final String resp = target("health").request().get(String.class);
		Assert.assertTrue(resp.contains("Financial Models Application is healthy"));
	}
}
