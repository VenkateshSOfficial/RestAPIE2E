package pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderResponse {
	private List<String> orders;
	private List<String> productOrderId;
	private String message;
}
