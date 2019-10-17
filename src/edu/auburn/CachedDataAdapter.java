package edu.auburn;

import java.util.HashMap;
import java.util.Map;

public class CachedDataAdapter {//implements IDataAccess {
    Map<Integer, ProductModel> cachedProducts = new HashMap<>();
    Map<Integer, CustomerModel> cachedCustomers = new HashMap<>();
    IDataAccess adapter;

    public CachedDataAdapter(IDataAccess adapter) {
        this.adapter = adapter;
    }


    //@Override
    public void connect(String path) {
        this.adapter.connect(path);
    }

    //@Override
    public boolean disconnect() {
        return false;
    }

   // @Override
    public int getErrorCode() {
        return 0;
    }

   // @Override
    public String getErrorMessage() {
        return null;
    }

    //@Override
    public ProductModel loadProduct(int id) {
        if (cachedProducts.containsKey(id))
            return cachedProducts.get(id);
        else {
            ProductModel product = adapter.loadProduct(id);
            cachedProducts.put(id, product);
            return product;
        }
    }

    //@Override
    public boolean saveProduct(ProductModel product) {
        adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;
    }

    public CustomerModel loadCustomer(int id) {
        if (cachedProducts.containsKey(id))
            return cachedCustomers.get(id);
        else {
            CustomerModel customer = adapter.loadCustomer(id);
            cachedCustomers.put(id, customer);
            return customer;
        }
    }

    public boolean saveCustomer(CustomerModel customer) {
        adapter.saveCustomer(customer);
        cachedCustomers.put(customer.mCustomerID, customer);
        return true;
    }

}
