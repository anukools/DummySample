package com.sample.shopapp.events;

import com.sample.shopapp.models.Filter;

import java.util.ArrayList;

/**
 * Created by vaibhav on 12/17/15.
 */
public class ApplyFiltersClickEvent {
    public ArrayList<Filter> selectedFilters;
    public String queryParams;

    public ApplyFiltersClickEvent(ArrayList<Filter> selectedFilters, String queryParams) {
        this.selectedFilters = selectedFilters;
        this.queryParams = queryParams;
    }
}
