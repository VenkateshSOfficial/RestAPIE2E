package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDetails {
	private String _id;
	private String orderById;
	private String orderBy;
	private String productOrderedId;
	private String productName;
	private String country;
	private String productDescription;
	private String productImage;
	private String orderPrice;
	private String __v;
}
