package com.sample.shopapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sample.shopapp.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav on 9/29/15.
 */
//@Parcel
public class ProductsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<>();

//    @SerializedName("filters")
//    @Expose
//    private List<Filter> filters = new ArrayList<>();

    public ProductsResponse(){}

    /**
     *
     * @return
     *     The products
     */
    public List<Product> getProducts() {
        return products;
    }

//    /**
//     *
//     * @return
//     *     The filters
//     */
//    public List<Filter> getFilters() {
//        return filters;
//    }
}
