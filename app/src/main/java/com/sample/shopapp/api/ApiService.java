package com.sample.shopapp.api;

import com.sample.shopapp.api.models.CardWrapper;
import com.sample.shopapp.api.models.HomeResponse;
import com.sample.shopapp.api.models.LineItemWrapper;
import com.sample.shopapp.api.models.OrderWrapper;
import com.sample.shopapp.api.models.OrdersResponse;
import com.sample.shopapp.api.models.PasswordResetResponse;
import com.sample.shopapp.api.models.ProductWrapper;
import com.sample.shopapp.api.models.ProductsByTaxonsResponse;
import com.sample.shopapp.api.models.ProductsResponse;
import com.sample.shopapp.api.models.ReviewWrapper;
import com.sample.shopapp.api.models.TaxonomiesResponse;
import com.sample.shopapp.models.Address;
import com.sample.shopapp.models.Card;
import com.sample.shopapp.models.Config;
import com.sample.shopapp.models.Country;
import com.sample.shopapp.models.EmailWrapper;
import com.sample.shopapp.models.Order;
import com.sample.shopapp.models.PasswordChange;
import com.sample.shopapp.models.Review;
import com.sample.shopapp.models.Reviews;
import com.sample.shopapp.models.User;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;


public interface ApiService {

	//Get the list of all the products for the first page
	@GET("products")
	Call<ProductsResponse> getAllProducts();

	//get products in a particular category of the next pages in the trip list.
	@GET("search")
	Call<ProductsByTaxonsResponse> getProductsByTaxons(@Query("taxons[]") List<String> taxonNames);

	// Get the list of all the taxonomies
	@GET("taxonomies")
	Call<TaxonomiesResponse> getAllTaxonomies();

	// Get data for home page
	@GET("home")
	Call<HomeResponse> getHome();

	// User login
	@POST("users/sign_in")
	Call<User> login(@Body User user);

	// User Sign up
	@POST("users")
	Call<User> signup(@Body User user);

	// Password Reset
	@POST("password/reset")
	Call<PasswordResetResponse> resetPwd(@Body EmailWrapper emailWrapper);

	// Product detail
	@GET("products/{id}")
	Call<ProductWrapper> getProductById(@Path("id") int productId);

    // Product reviews
    @GET("product/reviews")
    Call<Reviews> getProductReviews(@Query("product_id") int product_id);

	// add a review
	@POST("product/reviews")
	Call<Review> addReviewForProduct(@Query("product_id") int product_id, @Query("token") String token, @Body ReviewWrapper reviewWrapper);

	//TODO : to be used when retrofit 2.1 is out
	// Product List with filters
	@GET("products")
	Call<ProductsResponse> getProductsByFilter(@QueryMap(encoded = true) Map<String, String> map);


	@PATCH("user/profiles/{token}")
	Call<User> editUser(@Path("token") String token,@Body User user);

	@PATCH("password/change")
	Call<User> changePwd(@Body PasswordChange passwordChange);

	@GET("user/addresses")
	Call<List<Address>> getUserAddresses(@Query("token") String token);

	@POST("user/addresses")
	Call<Address> createAddress(@Query("token") String token, @Body Address address);

	@PUT("user/addresses/{id}")
	Call<Address> updateAddress(@Path("id") int addressId, @Query("token") String token, @Body Address address);

	@DELETE("user/addresses/{id}")
	Call<Address> removeAddress(@Path("id") int addressId, @Query("token") String token);

	// --------------- Checkout flow ----------------
	// get current order if any
	@GET("orders/current")
	Call<Order> getCurrentOrder(@Query("token") String token);

	// create order in case current order does not exist
	@POST("orders")
	Call<Order> createOrder(@Query("token") String token, @Body OrderWrapper orderWrapper);

	// get order by id
	@GET("orders/{id}")
	Call<Order> getOrderById(@Path("id") String orderId, @Query("token") String token);

	// on add to cart if product already in cart then edit quantity of a product in cart
	@PATCH("orders/{orderId}/line_items/{id}")
	Call<Order> editQuantity(@Path("orderId") String orderId, @Path("id") int id, @Query("token") String token, @Body LineItemWrapper lineItemWrapper);

	// remove a product from cart
	@DELETE("orders/{orderId}/line_items/{id}")
	Call<Order> removeProduct(@Path("orderId") String orderId, @Path("id") int id, @Query("token") String token);

	// add to cart if not already in cart
	@POST("orders/{orderId}/line_items")
	Call<Order> addToCart(@Path("orderId") String orderId, @Query("token") String token, @Body LineItemWrapper lineItemWrapper);

	// add address for an order
	@PUT("checkouts/{orderId}")
	Call<Order> addAddressForOrder(@Path("orderId") String orderId, @Query("token") String token, @Body OrderWrapper orderWrapper);

	// add payment method for an order
	@POST("checkouts/{orderId}/payments")
	Call<Order> addPaymentForOrder(@Path("orderId") String orderId, @Query("token") String token/*, @Body PaymentWrapper paymentWrapper*/);

	// move to next state
	@PUT("checkouts/{orderId}/next")
	Call<Order> moveToNextState(@Path("orderId") String orderId, @Query("token") String token);

	// move to next state
	@PUT("checkouts/{orderId}")
	Call<Order> moveToConfirmState(@Path("orderId") String orderId, @Query("token") String token, @Body OrderWrapper orderWrapper);

	// move back a state
	@PUT("checkouts/{orderId}/back")
	Call<Order> moveBackAState(@Path("orderId") String orderId, @Query("token") String token);

	// ---------------------------------------------------

	@GET("users/credit_cards")
	Call<List<Card>> getUserCards(@Query("token") String token);

	@POST("users/credit_cards")
	Call<Card> createCard(@Query("token") String token, @Body CardWrapper cardWrapper);

	@DELETE("users/credit_cards/{id}")
	Call<Card> removeCard(@Path("id") int cardId, @Query("token") String token);

	// get my completed orders
	@GET("orders/mine")
	Call<OrdersResponse> getMyOrders(@Query("token") String token);

	// get config
	@GET("config")
	Call<Config> getConfig();

    // get states
    @GET("config/states")
    Call<Country> getStates();
}
