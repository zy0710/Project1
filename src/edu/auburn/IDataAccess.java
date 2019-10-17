package edu.auburn;

public interface IDataAccess {
    public static final int CONNECTION_OPEN_OK = 1;
    public static final int CONNECTION_OPEN_FAILED = 2;

    public static final int PRODUCT_SAVE_OK = 101;
    public static final int PRODUCT_SAVE_FAILED = 102;
    public static final int PRODUCT_SAVE_DUPLICATE = 103;

    public static final int PRODUCT_LOAD_OK = 101;
    public static final int PRODUCT_LOAD_FAILED = 102;
    public static final int PRODUCT_LOAD_ID_NOT_FOUND = 103;

    public static final int CUSTOMER_SAVE_OK = 101;
    public static final int CUSTOMER_SAVE_FAILED = 102;
    public static final int CUSTOMER_SAVE_DUPLICATE = 103;

    public static final int CUSTOMER_LOAD_OK = 101;
    public static final int CUSTOMER_LOAD_FAILED = 102;
    public static final int CUSTOMER_LOAD_ID_NOT_FOUND = 103;

    public static final int PURCHASE_SAVE_OK = 101;
    public static final int PURCHASE_SAVE_FAILED = 102;
    public static final int PURCHASE_SAVE_DUPLICATE = 103;

    public static final int PURCHASE_LOAD_OK = 101;
    public static final int PURCHASE_LOAD_FAILED = 102;
    public static final int PURCHASE_LOAD_ID_NOT_FOUND = 103;

    public boolean connect(String path);
    public boolean disconnect();
    public int getErrorCode();
    public String getErrorMessage();


    public ProductModel loadProduct(int id);
    public boolean saveProduct(ProductModel product);

    public CustomerModel loadCustomer(int id);
    public boolean saveCustomer(CustomerModel customer);

    public PurchaseModel loadPurchase(int id);
    public boolean savePurchase(PurchaseModel purchase);


}
