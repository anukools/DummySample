package com.sample.shopapp.controllers.fragments.drawerFragment;

import com.sample.shopapp.models.Taxon;

/**
 * Created by Infernus on 23/12/15.
 */
public interface DrawerActionListener {
    public void onShowPage1(boolean isInstant);
    public void onShowPage2(boolean isInstant, Taxon taxon);
    public void onTaxonSelected(Taxon taxon);
}
