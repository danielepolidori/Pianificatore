package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

    protected List<ElemDataset> products;

    public Dataset() {
        products = new ArrayList<ElemDataset>();
    }

    public void add(ElemDataset product){
        products.add(product);
    }

    public void delete(ElemDataset product){
        products.remove(product);
    }

    public int getNumberOfProducts(){
        return products.size();
    }

    public List<ElemDataset> getProducts() {
        return products;
    }
}