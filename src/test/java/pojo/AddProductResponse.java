package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddProductResponse {
	private String productId;
	private String message;
}
