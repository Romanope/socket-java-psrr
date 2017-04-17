package com.socket.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.socket.psrr.dhcp.DHCPClient;
import com.socket.psrr.dhcp.DHCPServer;

public class Index extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel panel;
	
	private JTextField textQuantidade;
	
	private JComboBox<String> tpSockets;

	private JComboBox<String> tpClass;
	
	private JButton btnInicar;
	
	private static JTextArea textLog;
	
	public Index() {
		this.setTitle("Troca de Mesagens DHCP");
		this.setSize(540, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setLocationRelativeTo(null);
		montaJanela();
	}
	
	private void montaJanela() {
		panel = new JPanel(null);
		
		createLabel("Quantidade: ", 10, 10, 22, 80);
		textQuantidade = new JTextField();
		textQuantidade.setBounds(85, 10, 100, 22);
		panel.add(textQuantidade);

		createLabel("Tipo Conn: ", 10, 40, 22, 80);
		createComboTpSocket();
		
		createLabel("Classe: ", 10, 70, 22, 80);
		createComboTpClasse();
		
		btnInicar = new JButton("Iniciar");
		btnInicar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						DHCPServer server = new DHCPServer(5000, isTcp());
					}
				}).start();
				
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				start();
			}
		});
		
		btnInicar.setBounds(10, 100, 100, 22);
		panel.add(btnInicar);
		
		createLabel("LOG", 10, 130, 22, 80);
		textLog = new JTextArea("");
		textLog.setLineWrap(true);
//		textLog.setEditable(false);
		JScrollPane scroll = new JScrollPane(textLog);
		scroll.setBounds(10, 160, 500, 150);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(scroll);
		
		this.add(panel);
	}
	
	public static void main(String[] args) {
		new Index().setVisible(true);
	}
	
	private void createLabel(String label, int x, int y, int height, int width) {
		JLabel jLabel = new JLabel(label);
		jLabel.setBounds(x, y, width, height);
		panel.add(jLabel);
	}
	
	private void createComboTpSocket() {
		tpSockets = new JComboBox<String>();
		tpSockets.addItem("TCP");
		tpSockets.addItem("UDP");
		tpSockets.setBounds(85, 40, 100, 22);
		panel.add(tpSockets);
	}
	
	private void createComboTpClasse() {
		tpClass = new JComboBox<String>();
		tpClass.addItem("A");
		tpClass.addItem("B");
		tpClass.addItem("C");
		tpClass.setBounds(85, 70, 100, 22);
		panel.add(tpClass);
	}
	
	private boolean isTcp() {
		if (tpSockets.getSelectedItem().toString().trim().equals("TCP")) {
			return true;
		}
		return false;
	}
	
	private char getClasse() {
		return tpClass.getSelectedItem().toString().toCharArray()[0];
	}
	
	
	private void start() {
		int qtd = Integer.valueOf(textQuantidade.getText().trim());
		for (int i = 0; i < qtd; i++) {
			DHCPClient client = new DHCPClient("127.0.0.1", 5000, isTcp(),  getClasse());
		}
		System.out.println("Número de requisições: ".concat(Integer.toString(qtd)));
	}
	
	public static synchronized void setText(String text) {
		textLog.setText(textLog.getText().concat(text));
	}
}
