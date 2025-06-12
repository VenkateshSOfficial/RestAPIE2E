package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
	private String token;
	private String userId;
	private String message;
}
