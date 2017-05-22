package com.sample.shopapp.viewhelpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sample.shopapp.R;
import com.sample.shopapp.SpreeApplication;
import com.sample.shopapp.events.OpenProductDetailEvent;
import com.sample.shopapp.models.Product;
import com.sample.shopapp.utils.BusProvider;

/**
 * Created by vaibhav on 11/19/15.
 */
public class ProductsCardItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView img, wishlist;
    private View ratingStars;
    private TextView name, price;
    private RelativeLayout descContainer;

    public ProductsCardItemViewHolder(View itemView) {
        super(itemView);
        img        = (ImageView) itemView.findViewById(R.id.products_card_view_item_img);
        wishlist   = (ImageView) itemView.findViewById(R.id.products_card_view_item_wishlist_img);
        ratingStars=             itemView.findViewById(R.id.products_card_view_item_rating_container);
        name       = (TextView)  itemView.findViewById(R.id.products_card_view_item_name);
        price      = (TextView)  itemView.findViewById(R.id.products_card_view_item_price);
        descContainer = (RelativeLayout) itemView.findViewById(R.id.products_card_view_item_txt_container);
    }

    private void setClickListeners(final int productId) {
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : add or remove product from wishlist v2
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail(productId);
            }
        });
        descContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail(productId);
            }
        });
    }

    private void openProductDetail(int productId) {
        BusProvider.getInstance().post(new OpenProductDetailEvent(productId));
    }

    public void setup(Context context, Product product) {
        setClickListeners(product.getId());
        // rating
        new RatingStarsHelper(context, ratingStars).setRating(product.getAverageRating());
        name.setText(product.getName());
        price.setText(product.getDisplayPrice());
        if (product.getImages()!=null && !product.getImages().isEmpty()) {
            Picasso.with(SpreeApplication.getContext()).load(product.getImages().get(0).getProductUrl()).into(img);
        } else {
            img.setImageResource(R.drawable.placeholder);
        }
    }
}
