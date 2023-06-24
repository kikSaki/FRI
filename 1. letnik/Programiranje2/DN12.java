import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DN12 { 

    public static void main(String[] args) {

        JFrame okno = new JFrame();
        okno.setSize(700, 400);
        okno.setLocation(400, 400);
        okno.setTitle("VELIKE ČRKE");

        JTextArea vpis = new JTextArea();
        // teli dodatki so zato da se TextArea ne spreminja v velikosti ko notri pišeš
        // razen če je to pač dovoljeno v nalog, ampak nikje ne piše
        vpis.setColumns(5);
        vpis.setRows(1);
        vpis.setLineWrap(true);
        vpis.setWrapStyleWord(true);

        JTextArea izpis = new JTextArea();
        // teli dodatki so zato da se TextArea ne spreminja v velikosti ko notri pišeš
        // razen če je to pač dovoljeno v nalog, ampak nikje ne piše
        izpis.setColumns(5);
        izpis.setRows(1);
        izpis.setLineWrap(true);
        izpis.setWrapStyleWord(true);
        // za izpis sem dal tudi non editable, ker nočemo da se notri piše
        izpis.setEditable(false);

        JButton pretvori = new JButton("--> Pretvori");

        okno.setLayout(new GridBagLayout());

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        okno.add(vpis, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        okno.add(pretvori, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        okno.add(izpis, gbc);

        pretvori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                izpis.setText(vpis.getText().toUpperCase());
            }
        });

        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);

    }
}