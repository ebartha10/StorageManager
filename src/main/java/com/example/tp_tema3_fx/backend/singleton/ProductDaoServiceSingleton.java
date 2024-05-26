package com.example.tp_tema3_fx.backend.singleton;

import com.example.tp_tema3_fx.backend.service.ProductDaoService;

public class ProductDaoServiceSingleton {
    private static final ProductDaoService productDaoService = new ProductDaoService();
    public static ProductDaoService getProductDaoService(){
        return productDaoService;
    }
}
