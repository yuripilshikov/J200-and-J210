/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
    JButton clientFind, addressFind, clientDelete, addressDelete, refresh;
    JTextArea clientFound, addressFound;
    
    DefaultListModel<MyClient> clientListModel;
    JList listOfClients;
    
    DefaultListModel<Address> addressListModel;
    JList listOfAddresses;
    
    List<MyClient> clients;
    List<Address> addresses;

    public MainSWING() {
        super("REST Client");
        setBounds(300, 200, 1200, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLayout(new FlowLayout());

        restclient = new RESTclient();        
        //restclient.runSomeTests();
        clients = restclient.readXML();
        addresses = restclient.getAllAddresses();
        selectedItemLabel = new JLabel("...");
        
        clientNumber = new JTextField();
        clientFind = new JButton("Find client");
        clientFound = new JTextArea();
        
        addressNumber = new JTextField();
        addressFind = new JButton("Find address");
        addressFound = new JTextArea();
        
        clientDelete = new JButton("Delete client");
        addressDelete = new JButton("Delete address");

        clientListModel = new DefaultListModel<>();
        for(MyClient c : clients) {
            clientListModel.addElement(c);
        }
        
        addressListModel = new DefaultListModel<>();
        for(Address a : addresses) {
            addressListModel.addElement(a);
        }       
        
        
        listOfClients = new JList(clientListModel);
        listOfAddresses = new JList(addressListModel);
        
        JPanel listsPanel = new JPanel();
        listsPanel.setLayout(new BoxLayout(listsPanel, BoxLayout.Y_AXIS));
        listsPanel.add(new JLabel("Clients"));
        listsPanel.add(listOfClients);
        listsPanel.add(new JLabel("Addresses"));
        listsPanel.add(listOfAddresses);      
        
        
        refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLists();
            }
        });
        listsPanel.add(refresh);
        listsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        add(listsPanel, BorderLayout.WEST);
        
        listOfClients.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedItemLabel.setText("Selected: " + listOfClients.getSelectedIndex() + " " + listOfClients.getSelectedValue());
            }
        });
        
        add(selectedItemLabel, BorderLayout.NORTH);
        
        
        // Find item by ID
        JPanel findIdPanel = new JPanel();
        findIdPanel.setLayout(new BoxLayout(findIdPanel, BoxLayout.Y_AXIS));
        
        JPanel findClientPanel = new JPanel();        
        findClientPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        findClientPanel.setLayout(new FlowLayout());
        findClientPanel.add(new JLabel("Find client by ID"));
        findClientPanel.add(clientNumber);
        clientNumber.setColumns(5);
        findClientPanel.add(clientFind);
        findClientPanel.add(clientDelete);
        findClientPanel.add(clientFound);
        clientFound.setLineWrap(true);
        clientFound.setColumns(35);
        findIdPanel.add(findClientPanel);
        
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
                if(foundClient == null) {
                    clientFound.setText("NOT FOUND");
                    return;
                }
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
                updateLists();
            }
        });

          
        
        // find address
        JPanel findAddressPanel = new JPanel();
        findAddressPanel.setLayout(new FlowLayout());
        findAddressPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        findAddressPanel.add(new JLabel("Find address by ID"));
        findAddressPanel.add(addressNumber);
        addressNumber.setColumns(5);
        findAddressPanel.add(addressFind);
        findAddressPanel.add(addressDelete);
        findAddressPanel.add(addressFound);
        addressFound.setLineWrap(true);
        addressFound.setColumns(35);
        
        findIdPanel.add(findAddressPanel);
        
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
                if(foundAddress == null) {
                    addressFound.setText("NOT FOUND");
                    return;
                }
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
                updateLists();
            }
        });
        
        add(findIdPanel);      
    }
    
    private void updateLists() {
        clients = restclient.readXML();
        clientListModel.clear();
        for(MyClient c : clients) {
            clientListModel.addElement(c);
        }        
        
        addresses = restclient.getAllAddresses();
        addressListModel.clear();
        for(Address a : addresses) {
            addressListModel.addElement(a);
        }  
    }   

    public static void main(String[] args) {
        MainSWING main = new MainSWING();
        main.setVisible(true);        
    }
}
