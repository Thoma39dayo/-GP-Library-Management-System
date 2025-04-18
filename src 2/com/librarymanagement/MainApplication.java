package com.librarymanagement;

import com.librarymanagement.gui.PanelFactory;
import com.librarymanagement.service.InventoryService;
import com.librarymanagement.service.TransactionService;
import com.librarymanagement.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplication extends JFrame {
    private JPanel currentPanel;
    private final UserService userService;
    private final InventoryService inventoryService;
    private final TransactionService transactionService;
    private final PanelFactory panelFactory;

    public MainApplication() {
        userService = new UserService();
        inventoryService = new InventoryService();
        transactionService = new TransactionService(inventoryService);
        panelFactory = new PanelFactory(userService, inventoryService, transactionService);

        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        switchToPanel(panelFactory.createLoginPanel(this));
    }

    public void switchToPanel(JPanel panel) {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = panel;
        add(currentPanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApplication app = new MainApplication();
            app.setVisible(true);
        });
    }
}    