package app.boot.events.com.aol.micro.server;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.aol.micro.server.boot.config.Microboot;
import com.aol.micro.server.boot.config.MicrobootApp;
import com.aol.micro.server.rest.client.nio.AsyncNonNIORestClient;
import com.aol.micro.server.testing.RestAgent;

@Microboot
public class EventRunnerTest {

	RestAgent rest = new RestAgent();
	private final AsyncNonNIORestClient<String> client = new AsyncNonNIORestClient<String>(1000,1000).withAccept("application/json");
	MicrobootApp server;
	
	
	@Before
	public void startServer(){
		
		server = new MicrobootApp(()-> "event-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/event-app/status/ping"),is("ok"));
		
		assertThat(client.get("http://localhost:8080/event-app/active/jobs").get(),
				containsString("startedAt"));
		assertThat(client.get("http://localhost:8080/event-app/active/requests").get(),
				containsString("startedAt"));
		assertThat(client.get("http://localhost:8080/event-app/manifest").get(),
				containsString("Manifest"));
		
	}
	
	
	
}
