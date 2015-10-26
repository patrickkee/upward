package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

public class ModelResourceTest extends BaseJerseyTest {

	@Test
	public void createModelTest() {
		String placeholder = "blah";
		String response = target("accounts").queryParam("accountName", "createModelTest").queryParam("firstName", "createModelTestFirstName")
				.queryParam("lastName", "createModelTestLastName").queryParam("email", "createModelTest@gmail.com")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(placeholder, MediaType.APPLICATION_JSON_TYPE), String.class);

		response = target("accounts/createModelTest@gmail.com/models")
										.queryParam("modelName", "createModelTestModelName")
										.queryParam("description", "createModelTestModelDesc")
										.queryParam("initialValue", 2000)
										.queryParam("targetValue", 20000)
										.queryParam("startDate", "01/01/2010")
										.queryParam("endDate", "01/01/2020")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(placeholder, MediaType.APPLICATION_JSON_TYPE), String.class);
		
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			startDate = formatter.parse("01/01/2010");
			endDate = formatter.parse("01/01/2020");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(startDate != null);
		Assert.assertTrue(endDate != null);
		Assert.assertTrue(response.contains("createModelTest"));
		Assert.assertTrue(response.contains("createModelTestFirstName"));
		Assert.assertTrue(response.contains("createModelTestLastName"));
		Assert.assertTrue(response.contains("createModelTest@gmail.com"));
		Assert.assertTrue(response.contains("createModelTestModelName"));
		Assert.assertTrue(response.contains("createModelTestModelDesc"));
		Assert.assertTrue(response.contains("2000"));
		Assert.assertTrue(response.contains("20000"));
		Assert.assertTrue(response.contains(Long.toString(startDate.getTime())));
		Assert.assertTrue(response.contains(Long.toString(endDate.getTime())));
	}
	
}
