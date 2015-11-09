 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keytyperserver;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Window {

    private JFrame frame;
    private Canvas canvas;
    private String title;
    private int width, height;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private String status;
    private JLabel statusInfo;
    private static InetAddress ipAddr;
    public final JTable table;

    public Window(String pTitle) throws UnknownHostException, SocketException {
        title = pTitle;
        width = 400;
        height = 300;

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setSize(width, height);

        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            checkInterfaceInformation(netint);
        }

        JLabel ipInfo = new JLabel("Host IP:   " + ipAddr.toString().split("/")[1]);
        ipInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().add(ipInfo);

        frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));

        status = "waiting for client...";
        statusInfo = new JLabel("Status: " + status);
        statusInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().add(statusInfo);

        frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));

        String[] columnNames = {"Time Connected", "Socket", "IP Address"};

        Object[][] data = {};

        table = new JTable(new DefaultTableModel(data, columnNames));
        // table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().add(scrollPane);

        frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));

        JButton powerButton = new JButton("Stop");
        powerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        powerButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        frame.getContentPane().add(powerButton);
        powerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));

        //frame.pack();
        frame.setVisible(true);
    }

    public void changeStatus(String s) {
        status = s;
        statusInfo.setText("Status: " + status);
    }

    public void addClient(String socket, String ip) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{hours + ":" + minutes, socket, ip});
    }

    static void checkInterfaceInformation(NetworkInterface netint) throws SocketException {
        if ((netint.getDisplayName().toLowerCase().contains("wifi")
                || netint.getDisplayName().toLowerCase().contains("wireless")
                || netint.getDisplayName().toLowerCase().contains("wlan")
                || netint.getName().toLowerCase().contains("wifi")
                || netint.getName().toLowerCase().contains("wireless")
                || netint.getName().toLowerCase().contains("wlan"))
                && (Collections.list(netint.getInetAddresses()).size() > 1)) {
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            int i = 0;
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                if (inetAddress.toString().contains(".")) {
                    ipAddr = inetAddress;
                }
            }
            System.out.printf("\n");
        }
    }

    public void removeClient(Socket sockt) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String socket = sockt.getRemoteSocketAddress().toString().substring(17);

        int row = -1;
        for (int i = 0; i < model.getRowCount(); ++i) {
            if (model.getValueAt(i, 1).equals(socket)) {
                row = i;
                break;
            }
        }

        if (row > -1) {
            model.removeRow(row);
        }

        if (model.getRowCount() == 0) {
            changeStatus("no clients connected");
        }
    }
}
