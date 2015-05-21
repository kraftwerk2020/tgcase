package com.target.tgcase;

import com.target.tgcase.entity.Price;
import com.target.tgcase.json.Product;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {"classpath:/META-INF/spring/root-context.xml"})
public class ITMainTest extends AbstractTestNGSpringContextTests {


	@Autowired
	@Qualifier("cassandraTemplate")
	private CassandraOperations cassandraOperations;
	
	@Test(testName = "Rest and Parse Json")
	public void testRestJsonIT() {

		final String uri = "https://api.target.com/products/v3/{id}?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";

		Map<String, String> params = new HashMap<String, String>();
		params.put( "id", "13860428" );

		RestTemplate rt = new RestTemplate();

		rt.getMessageConverters().add( new MappingJackson2HttpMessageConverter() );

		Product result = rt.getForObject( uri, Product.class, params );

		Assert.assertEquals( result.getDescription(), "BIG LEBOWSKI, THE Blu-ray" );

		//System.out.println( result.getDescription() );
	}

	@Test
	public void productStoreIT() throws UnknownHostException {

		// Connect to cassandra store something change a value and pull it back

		cassandraOperations.insert( new Price("13860428", new BigDecimal( "123.23" ) ) );
		
		Price t = cassandraOperations.selectOneById(Price.class, "13860428");
		
		Assert.assertEquals( t.getPrice(), new BigDecimal( "123.23" ) );

	}
}
