package edu.auburn;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class AddPurchaseUI {
    public JFrame view;

    public JLabel labPurchaseDate = new JLabel("Purchase Date: ");
    public JLabel labPurchaseID = new JLabel("Purchase ID: ");
    public JLabel labCustomerID = new JLabel("Customer ID: ");
    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductID = new JLabel("Product ID: ");
    public JLabel labProductName = new JLabel("Product Name: ");
    public JLabel labProductPrice = new JLabel("Product Price: ");
    public JLabel labProductQuantity = new JLabel("Purchase Quantity: ");

    public JTextField txtProductID = new JTextField(10);
    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtPurchaseDate = new JTextField(10);
    public JTextField txtProductName = new JTextField(20);
    public JTextField txtCustomerName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);
    public JTextField txtCost = new JTextField(20);
    public JTextField txtTax = new JTextField(20);
    public JTextField txtTotalCost = new JTextField(20);

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    ProductModel product;
    CustomerModel customer;
    PurchaseModel purchase;

    public AddPurchaseUI() {
        view = new JFrame();
        view.setTitle("Add Purchase");
        view.setSize(500, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(11,2, 0,6));
        view.getContentPane().add(pane);

        pane.add(new JLabel("Purchase Date:")); pane.add(txtPurchaseDate);
        pane.add(labPurchaseID); pane.add(txtPurchaseID);
        pane.add(labCustomerID); pane.add(txtCustomerID);
        pane.add(labCustomerName); pane.add(txtCustomerName);
        pane.add(labProductID); pane.add(txtProductID);
        pane.add(labProductName); pane.add(txtProductName);
        pane.add(labProductPrice); pane.add(txtPrice);
        pane.add(labProductQuantity); pane.add(txtQuantity);
        pane.add(new JLabel("Cost")); pane.add(txtCost);
        pane.add(new JLabel("Tax")); pane.add(txtTax);
        pane.add(new JLabel("Total Cost")); pane.add(txtTotalCost);


        pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(btnAdd);
        pane.add(btnCancel);
        view.getContentPane().add(pane);

        txtProductID.addFocusListener(new ProductNameLoader());
        txtQuantity.addFocusListener(new CostCalculator());
        txtCustomerID.addFocusListener(new CustomerNameLoader());
        //btnAdd.addActionListener(new AddPurchaseListener());

        btnAdd.addActionListener(new AddPurchaseContorller());
        btnCancel.addActionListener(
                (actionEvent) -> view.dispose()
        );


        /*btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                purchase.mPurchaseID = Integer.parseInt(txtPurchaseID.getText());

                ReceiptUI ui = new ReceiptUI(purchase, txtProductName.getText(), txtPurchaseDate.getText(), txtCustomerName.getText(), txtPrice.getText());
                ui.run();
            }
        });*/

    }


    class CostCalculator implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtQuantity.getText();
            try {
                purchase.mQuantity = Double.parseDouble(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Quantity");
                return;
            }

            if (purchase.mQuantity > product.mQuantity) {
                JOptionPane.showMessageDialog(null, "Purchase Quantity > Available Quantity!");
                return;
            }

            purchase.mCost = purchase.mQuantity * product.mPrice;
            txtCost.setText(Double.toString(purchase.mCost));
            purchase.mTax = purchase.mCost * 0.09;
            txtTax.setText(Double.toString(purchase.mTax));
            purchase.mTotalCost = purchase.mCost + purchase.mTax;
            txtTotalCost.setText(Double.toString(purchase.mTotalCost));

        }
    }

    class ProductNameLoader implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtProductID.getText();
            try {
                purchase.mProductID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid ProductID");
                return;
            }
            if (product == null || product.mProductID != purchase.mProductID)
                product = StoreManager.getInstance().getDataAccess().loadProduct(purchase.mProductID);
            if (product == null) {
                JOptionPane.showMessageDialog(null, "No Product with ID = " + purchase.mProductID);
                txtProductName.setText("");
                txtPrice.setText("");
                return;
            }

            txtProductName.setText(product.mName);
            txtPrice.setText(Double.toString(product.mPrice));

        }
    }

    class CustomerNameLoader implements FocusListener  {

        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            String s = txtCustomerID.getText();
            try {
                purchase.mCustomerID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid CustomerID");
                return;
            }
            if (customer == null || customer.mCustomerID != purchase.mCustomerID)
                customer = StoreManager.getInstance().getDataAccess().loadCustomer(purchase.mCustomerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(null, "No Customer with ID = " + purchase.mCustomerID);
                txtCustomerName.setText("");
                //txtPrice.setText("");
                return;
            }

            txtCustomerName.setText(customer.mName);
            //txtPrice.setText(Double.toString(customer.mPrice));

        }
    }


    public void run() {
        purchase = new PurchaseModel();

        txtPurchaseDate.setText(Calendar.getInstance().getTime().toString());
        txtCustomerName.setEnabled(false);
        txtProductName.setEnabled(false);
        txtCost.setEnabled(false);
        txtTax.setEnabled(false);
        txtTotalCost.setEnabled(false);

        view.setVisible(true);
    }



    class AddPurchaseContorller implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            IReceiptBuilder tb = new HTMLReceiptBuilder();
            tb.appendHeader("STORE MANAGEMENT SYSTEM");
            tb.appendCustomer(customer);
            tb.appendProduct(product);
            tb.appendPurchase(purchase);
            tb.appendFooter("THANK YOU AND SEE YOU AGAIN!");
            System.out.println(tb.toString());


            PurchaseModel purchase = new PurchaseModel();

            String s = txtPurchaseID.getText();

            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "PurchaseID could not be EMPTY!!!");
                return;
            }
            try {
                purchase.mPurchaseID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "PurchaseID is INVALID (not a number)!!!");
                return;
            }

            s = txtProductID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "ProductID could not be EMPTY!!!");
                return;
            }
            try {
                purchase.mProductID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "ProductID is INVALID (not a number)!!!");
                return;
            }


            s = txtCustomerID.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID could not be EMPTY!!!");
                return;
            }
            try {
                purchase.mCustomerID = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "CustomerID is INVALID (not a number)!!!");
                return;
            }

            s = txtQuantity.getText();
            if (s.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Quantity could not be EMPTY!!!");
                return;
            }

            try {
                purchase.mQuantity = Double.parseDouble(s);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Quantity is INVALID (not a number)!!!");
                return;
            }

            s = txtCost.getText();
            purchase.mCost = Double.parseDouble(s);

            s = txtTax.getText();
            purchase.mTax = Double.parseDouble(s);

            s = txtTotalCost.getText();
            purchase.mTotalCost = Double.parseDouble(s);

            IDataAccess adapter = StoreManager.getInstance().getDataAccess();

            if (adapter.savePurchase(purchase))
                JOptionPane.showMessageDialog(null,
                        "Purchase is saved successfully!");
            else {
                System.out.println(adapter.getErrorMessage());
            }

        }
    }


    /* class AddPurchaseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            IReceiptBuilder tb = new HTMLReceiptBuilder();
            tb.appendHeader("STORE MANAGEMENT SYSTEM");
            tb.appendCustomer(customer);
            tb.appendProduct(product);
            tb.appendPurchase(purchase);
            tb.appendFooter("THANK YOU AND SEE YOU AGAIN!");
            System.out.println(tb.toString());
        }
    }*/

}
