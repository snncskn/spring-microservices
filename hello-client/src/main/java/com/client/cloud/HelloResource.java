package com.client.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hello/client")
public class HelloResource {

	@Autowired	
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "fallback", groupKey = "Hello", commandKey = "hello", threadPoolKey = "helloThread")
	@GetMapping
	public String hello() {
		return restTemplate.getForObject("http://hello-server/hello/server", String.class);
	}

	public String fallback(Throwable hystrixCommand) {
		return "Fall Back Hello world";
	}

}
