package com.logicbig.example.ui;


import com.logicbig.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

public class SwingCustomerUi implements CustomerUi {

    @Autowired
    private CustomerService customerService;

    @Override
    public void showUi() {
        JFrame frame = new JFrame();
        frame.setTitle("Swing Customer Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300, 300, 600, 600);

        JList list = new JList();
        list.setListData(customerService.getAllCustomers()
                .toArray());
        frame.add(list);
        frame.setVisible(true);
    }
}