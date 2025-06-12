package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {
	private String country;
	private String productOrderedId;
}
