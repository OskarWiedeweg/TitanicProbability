package com.example.TitanicProbability;

import com.example.TitanicProbability.dtos.AuthenticationDTO;
import com.example.TitanicProbability.dtos.LoginDTO;
import com.example.TitanicProbability.dtos.PassengerDTO;
import com.example.TitanicProbability.models.User;
import com.example.TitanicProbability.repositorys.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TitanicProbabilityApplicationTests {

	@Autowired
	private UserRepository userRepository;

	private final TestRestTemplate testRestTemplate = new TestRestTemplate();

	private final String URI = "http://localhost:8080";
	private final HttpHeaders headers;

	public TitanicProbabilityApplicationTests() {
		headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void importWorks() throws Exception {
		ResponseEntity<?> importResponse = testRestTemplate.getForEntity(URI + "/api/import", String.class);

		assertThat(importResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		ResponseEntity<PassengerDTO[]> checkResponse = testRestTemplate.getForEntity(URI + "/api/passenger/all", PassengerDTO[].class);

		assertThat(checkResponse.hasBody()).isTrue();
		assertThat(checkResponse.getBody().length).isEqualTo(2);
	}

	@Test
	public void passengerListIsEmpty() {
		ResponseEntity<PassengerDTO[]> response = testRestTemplate.getForEntity( URI + "/api/passenger/all", PassengerDTO[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.hasBody()).isTrue();
		assertThat(response.getBody().length).isEqualTo(0);
	}

	@Test
	public void protectedEndpointWithoutJwt() {
		ResponseEntity<?> checkDelete = testRestTemplate.exchange(URI + "/api/passenger?id=1", HttpMethod.DELETE, null, String.class);

		assertThat(checkDelete.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void protectedEndpointWithInvalidJwt() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJPc2thciIsImV4cCI6MTY0MjcwMTcwNSwiaWF0IjoxNjQyNjgzNzA1fQ.MMX05cI_aH_LWDmd_PUCa-8s3RCmAMPOFordIF0CtrXtQUtzActj1yRVe5dANRBdvql9SueVTfwuG2zrMZ467q");

		ResponseEntity<?> checkDelete = testRestTemplate.exchange(URI + "/api/passenger?id=1", HttpMethod.DELETE, null, String.class);

		assertThat(checkDelete.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void protectedEndpointWithValidJwt() {
		userRepository.save(new User(null, "testProtectedEndpoint", new BCryptPasswordEncoder().encode("password123"), true, true));

		ResponseEntity<AuthenticationDTO> checkAuthWithUser = testRestTemplate.exchange(URI + "/api/auth/login", HttpMethod.POST, new HttpEntity<LoginDTO>(new LoginDTO("testProtectedEndpoint", "password123"), headers), AuthenticationDTO.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(checkAuthWithUser.getBody().getJwt());

		ResponseEntity<?> checkDelete = testRestTemplate.exchange(URI + "/api/passenger?id=1", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);

		assertThat(checkDelete.getStatusCode()).isNotEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void login() {
		userRepository.save(new User(null, "testLogin", new BCryptPasswordEncoder().encode("password123"), true, true));

		ResponseEntity<AuthenticationDTO> checkAuthWithNoUser = testRestTemplate.exchange(URI + "/api/auth/login", HttpMethod.POST, new HttpEntity<LoginDTO>(new LoginDTO("testLogin", "password123"), headers), AuthenticationDTO.class);

		assertThat(checkAuthWithNoUser.getStatusCode()).isNotEqualTo(HttpStatus.FORBIDDEN);
	}

}
