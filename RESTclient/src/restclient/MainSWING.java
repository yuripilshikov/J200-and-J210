/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import restclient.objects.Address;
import restclient.objects.MyClient;

/**
 *
 * @author YuriPilshikov
 */
public class MainSWING extends JFrame {

    RESTclient restclient;
    
    JLabel selectedItemLabel;
    JTextField clientNumber, addressNumber;
    JButton clientFind, addressFind, clientDelete, addressDelete;
    JTextArea clientFound, addressFound;
    
    DefaultListModel<MyClient> clientList;
    JList list;
    
    List<MyClient> clients;

    public MainSWING() {
        super("REST Client");
        setBounds(300, 200, 800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLayout(new FlowLayout());

        restclient = new RESTclient();        
        clients = restclient.readXML();
        selectedItemLabel = new JLabel();
        
        clientNumber = new JTextField();
        clientFind = new JButton("Find client");
        clientFound = new JTextArea();
        
        addressNumber = new JTextField();
        addressFind = new JButton("Find address");
        addressFound = new JTextArea();
        
        clientDelete = new JButton("Delete client");
        addressDelete = new JButton("Delete address");

        clientList = new DefaultListModel<>();
        for(MyClient c : clients) {
            clientList.addElement(c);
        }
        
        list = new JList(clientList);
        add(list, BorderLayout.WEST);
        
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedItemLabel.setText("Selected: " + list.getSelectedIndex() + " " + list.getSelectedValue());
            }
        });
        
        //JLabel label = new JLabel("" + list.getSelectedIndex());
        add(selectedItemLabel, BorderLayout.NORTH);
        
        
        // Find item by ID
        JPanel findIdPanel = new JPanel();
        findIdPanel.setLayout(new FlowLayout());
        findIdPanel.add(new JLabel("Find client by ID"));
        findIdPanel.add(clientNumber);
        clientNumber.setColumns(5);
        findIdPanel.add(clientFind);
        findIdPanel.add(clientDelete);
        findIdPanel.add(clientFound);
        clientFound.setLineWrap(true);
        clientFound.setColumns(35);
        
        
        clientFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(clientNumber.getText());
                } catch(NumberFormatException ee) {
                    id = -1;
                }
                MyClient foundClient = restclient.findClient(id);
                StringBuilder sb = new StringBuilder("Found: ");
                sb.append(foundClient.toString()).append(".\n");
                for(Address a : foundClient.getAddressList()) {
                    sb.append("Address: ").append(a.toString());
                }
                clientFound.setText(sb.toString());
            }
        });        
        
        clientDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(clientNumber.getText());
                } catch(NumberFormatException ee) {
                    id = -1;
                }
                restclient.deleteClient(id);
                updateList();
            }
        });

          
        
        // find address
        findIdPanel.add(new JLabel("Find address by ID"));
        findIdPanel.add(addressNumber);
        addressNumber.setColumns(5);
        findIdPanel.add(addressFind);
        findIdPanel.add(addressDelete);
        findIdPanel.add(addressFound);
        addressFound.setLineWrap(true);
        addressFound.setColumns(35);
        
        
        addressFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(addressNumber.getText());
                } catch(NumberFormatException ee) {
                    id = -1;
                }
                Address foundAddress = restclient.findAddress(id);
                addressFound.setText(foundAddress.toString());
            }
        });
        
        addressDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(addressNumber.getText());
                } catch(NumberFormatException ee) {
                    id = -1;
                }
                restclient.deleteAddress(id);
            }
        });
        
        // update list
        
        add(findIdPanel);      
    }
    
    private void updateList() {
        clients = restclient.readXML();
        clientList.clear();
        for(MyClient c : clients) {
            clientList.addElement(c);
        }        
    }   

    public static void main(String[] args) {
        MainSWING main = new MainSWING();
        main.setVisible(true);        
    }
}
