package edu.auburn;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    Connection connection = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            connection = DriverManager.getConnection(url);

            //System.out.println("Connection to SQLite has been established.");
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED: return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED: return "Cannot load the product!";
        };
        return "OK";
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductID, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }

    }
    public boolean saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Products (ProductID, Name, Price, Quantity)" + "VALUES(" + product.mProductID + ",'" + product.mName + "'," + product.mPrice + ',' + product.mQuantity + ")";
            System.out.println(sql);
            Statement state = connection.createStatement();
            state.executeQuery(sql);

            state.close();
            connection.close();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public CustomerModel loadCustomer(int CustomerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerID, Name, Phone, Address FROM Customers WHERE CustomerId = " + CustomerID;
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerID");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getString("Phone");
            return customer;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }

    }

    public boolean saveCustomer(CustomerModel customer) {
        try {
            String sql = "INSERT INTO Customers (CustomerID, Name, Phone, Address)" + "VALUES(" + customer.mCustomerID + ",'" + customer.mName + "','" + customer.mPhone + "','" + customer.mAddress + "')";
            System.out.println(sql);
            Statement state = connection.createStatement();
            state.executeQuery(sql);

            state.close();
            connection.close();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = new PurchaseModel();

        try {
            String sql = "SELECT PurchaseID, CustomerID, ProductID, Quantity, Cost, Tax, TotalCost FROM Products WHERE PurchaseId = " + purchaseID;
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery(sql);
            purchase.mPurchaseID= rs.getInt("PurchaseID");
            purchase.mCustomerID = rs.getInt("CustomerID");
            purchase.mProductID = rs.getInt("ProductID");
            purchase.mQuantity = rs.getDouble("Quantity");
            purchase.mCost = rs.getDouble("Cost");
            purchase.mTax = rs.getDouble("Tax");
            purchase.mTotalCost = rs.getDouble("TotalCost");
            return purchase;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PURCHASE_LOAD_FAILED;
            return null;
        }

    }
    public boolean savePurchase(PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Purchase (purchaseID, CustomerID, ProductID, Quantity, Cost, Tax, TotalCost)"
                    + "VALUES(" + purchase.mPurchaseID + ",'" + purchase.mCustomerID + "'," + purchase.mProductID + ','
                    + purchase.mQuantity + ',' + purchase.mCost + ',' + purchase.mTax + ',' + purchase.mTotalCost + ")";
            System.out.println(sql);
            Statement state = connection.createStatement();
            state.executeQuery(sql);

            state.close();
            connection.close();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
