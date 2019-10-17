package edu.auburn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceiptUI {

    public JFrame view;

    PurchaseModel purchase;

    public ReceiptUI(PurchaseModel purchase, String ProductName, String Date, String CustomerName, String Price) {
        view = new JFrame();
        view.setTitle("Receipt");
        view.setSize(500, 400);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(11, 2, 0, 6));
        view.getContentPane().add(pane);

        pane.add(new JLabel("Purchase Date:" + Date));
        pane.add(new JLabel("PurchaseID:" + purchase.mPurchaseID));
        pane.add(new JLabel("CustomerID:" + purchase.mCustomerID));
        pane.add(new JLabel("Customer Name:" + CustomerName));
        pane.add(new JLabel("ProductID:" + purchase.mProductID));
        pane.add(new JLabel("Product Name:" + ProductName));
        pane.add(new JLabel("Product price:" + Price));
        pane.add(new JLabel("Quantity:" + purchase.mQuantity));
        pane.add(new JLabel("Cost" + purchase.mCost));
        pane.add(new JLabel("Tax" + purchase.mTax));
        pane.add(new JLabel("Total Cost" + purchase.mTotalCost));

    }

    public void run() {
        view.setVisible(true);
    }

}
