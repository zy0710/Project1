package edu.auburn;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TXTDataAdapter {//implements IDataAccess {

    Map<Integer, ProductModel> products = new HashMap<>();
    Map<Integer, CustomerModel> customers = new HashMap<>();

    public void connect(String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(new File(path)));
            ProductModel product = new ProductModel();
            CustomerModel customer = new CustomerModel();

            while (scanner.hasNext()) {
                // read the product info
                product.mProductID = scanner.nextInt(); scanner.nextLine();
                product.mName = scanner.nextLine();
                product.mPrice = scanner.nextDouble();
                product.mQuantity = scanner.nextDouble();

                // read the customer info
                customer.mCustomerID = scanner.nextInt(); scanner.nextLine();
                customer.mName = scanner.nextLine();
                customer.mAddress = scanner.nextLine();
                customer.mPhone = scanner.nextLine();


//                scanner.next(); // empty line
                System.out.println(product);
                products.put(product.mProductID, product);
                customers.put(customer.mCustomerID, customer);
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public ProductModel loadProduct(int id) {
        if (products.containsKey(id))
            return products.get(id);
        else
            return null;
    }


    public boolean saveProduct(ProductModel product) {

        return false;
    }

    public CustomerModel loadCustomer(int id){
        if (customers.containsKey(id))
            return customers.get(id);
        else
            return null;
    }

    public boolean saveCustomer(CustomerModel product) {

        return false;
    }


}
