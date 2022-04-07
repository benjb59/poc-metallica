package fr.insee.metallica.pocprotools.command.processor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.insee.metallica.pocprotools.command.domain.Command;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionAbortException;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionException;
import fr.insee.metallica.pocprotools.command.exception.CommandExecutionRetryException;
import fr.insee.metallica.pocprotools.command.processor.payload.HttpPayload;

@Service
public class HttpCommandProcessor extends TypedAbstractCommandProcessor<HttpPayload> {
	public HttpCommandProcessor() {
		super(Processors.Http, HttpPayload.class);
	}
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public Object process(Command command, HttpPayload payload) throws CommandExecutionException {
		try {
			HttpClient client = HttpClient.newHttpClient();
			Builder builder = HttpRequest
				.newBuilder()
				.uri(URI.create(payload.getUrl()))
				.POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload.getBody())))
				.header("COMMAND", command.getId().toString())
				.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.timeout(Duration.ofSeconds(20));
			
			switch (payload.getMethod()) {
			case DELETE:
				builder.DELETE();
				break;
			case GET:
				builder.GET();
				break;
			case POST:
				builder.POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload.getBody())));
				break;
			case PUT:
				builder.PUT(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload.getBody())));
				break;
			default:
				throw new CommandExecutionAbortException("Unknown http method " + payload.getMethod());
			}
			
			if (payload.getHeaders() != null) {
				for (var entry : payload.getHeaders().entrySet()) {
					builder.header(entry.getKey(), entry.getValue());
				}
			}
			
			var request = builder.build();

			var result = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			if (result.statusCode() == HttpStatus.SERVICE_UNAVAILABLE.value() || result.statusCode() == HttpStatus.BAD_GATEWAY.value()) {
				throw new CommandExecutionRetryException("Error " + result.statusCode() + " " + (result.body() != null ? result.body() : "")); 
			}
			if (result.statusCode()/100 >= 4) {
				throw new CommandExecutionAbortException("Error " + result.statusCode() + " " + (result.body() != null ? result.body() : "")); 
			}
			if (result.statusCode()/100 >= 3) {
				throw new CommandExecutionAbortException("Error " + result.statusCode()); 
			}
			if (result.statusCode() == 204) {
				return null;
			}
			return result.body();
		} catch (JsonProcessingException e) {
			throw new CommandExecutionAbortException("Could not serialize body", e);
		} catch (HttpTimeoutException e) {
			throw new CommandExecutionRetryException("Timeout calling service", e);
		} catch (IOException | InterruptedException e) {
			throw new CommandExecutionAbortException("Could not execute post request", e);
		}
	}

}
