package resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import com.patrickkee.resources.AccountResource;
import com.patrickkee.resources.HealthCheckResource;
import com.patrickkee.resources.ModelResource;

public class BaseJerseyTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(AccountResource.class, HealthCheckResource.class, ModelResource.class);
	}
	
}
