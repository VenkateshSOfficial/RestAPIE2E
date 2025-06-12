package ecommerceAPI;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.*;


public class ECommerceAPI {
	RequestSpecification requestSpecification;
	AddProductResponse productResponse;
	CreateOrderResponse createOrderResponse;
	DeleteProductResponse deleteProductResponse;
	private String userId;
	private String token;
	String productId;
	List<String> orderIds;

	public String getUserId(){
		return userId;
	}

	public String getToken(){
		return token;
	}

	public String getProductId(){
		return productId;
	}

	public String getOrderId(){
		for (String orderId : orderIds) {
			return orderId;
		}
		return null;
	}


	@BeforeTest
	public void preRequisite() {
		requestSpecification = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.build();
	}
	@Test
	public void authenticationProcess(){
		Login login=new Login();
		login.setUserEmail("venkatesh230691@gmail.com");
		login.setUserPassword("Abcd1234");
		LoginResponse loginResponse = given().spec(requestSpecification).body(login).when().post(
				"/api/ecom/auth/login").then().assertThat().statusCode(200).extract().response().as(LoginResponse.class);

		userId = loginResponse.getUserId();
		token = loginResponse.getToken();

		System.out.println("user id is : " + getUserId());
		System.out.println("The token is ; " + getToken());
	}

	@Test(dependsOnMethods = "authenticationProcess")
	public void createProduct(){
		Map<String,String> formParameters=new HashMap<>();
		formParameters.put("productName","Addidas T shirt");
		formParameters.put("productAddedBy",getUserId());
		formParameters.put("productCategory","Fashion");
		formParameters.put("productSubCategory","Shirts");
		formParameters.put("productPrice","2000");
		formParameters.put("productDescription","Addidas originals");
		formParameters.put("productFor","Men");

		productResponse = given().spec(requestSpecification.contentType("multipart/form-data")).params(formParameters).multiPart(
				"productImage", new File("pics/shopping.png")).header("Authorization",token).when().post(
				"/api/ecom/product/add-product").then().assertThat().statusCode(201).extract().response().as(
				AddProductResponse.class);

		productId = productResponse.getProductId();
		System.out.println("The created product id is : " + getProductId());
	}
	@Test(dependsOnMethods = "createProduct")
	public void createOrderRequest(){
		Orders orders=new Orders();
		orders.setCountry("India");
		orders.setProductOrderedId(getProductId());

		CreateOrder createOrder=new CreateOrder();
		List<Orders> ordersList=new ArrayList();
		ordersList.add(orders);
		createOrder.setOrders(ordersList);
		createOrderResponse = given().spec(requestSpecification.contentType("application/json")).body(createOrder).header("Authorization",
				getToken()).when().post("/api/ecom/order/create-order").then().assertThat().statusCode(
				201).extract().response().as(CreateOrderResponse.class);
		orderIds = createOrderResponse.getOrders();
		System.out.println("The order id obtained is : " + getOrderId());
	}
	@Test(dependsOnMethods = "createOrderRequest")
	public void deleteOrders(){
		deleteProductResponse = given().spec(
				requestSpecification.contentType("application/json")).header("Authorization", getToken()).pathParam(
				"productId", getProductId()).when().delete(
				"/api/ecom/product/delete-product/{productId}").then().assertThat().statusCode(200).extract().response().as(
				DeleteProductResponse.class);

		System.out.println("The delete message is : " + deleteProductResponse.getMessage());
	}
}
