package com.socket.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
	private static JTextArea textLogDHCPServer;

	private static JTextArea textLogDHCPClient;
	
	private static JTextArea textLogServerTCP;
	
	private static JTextArea textLogServerUDP;

	private static JTextArea textLogClientTCP;
	
	private static JTextArea textLogClientUDP;
	
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
	public Index() {
		this.setTitle("Troca de Mesagens DHCP");
		this.setSize(1040, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setLocationRelativeTo(null);
		this.setResizable(false);
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
						subirServidores(tpSockets.getSelectedItem().toString().trim().equals("TCP"));
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
		
		createLabel("LOG DHCP SERVER", 10, 130, 22, 200);
		textLogDHCPServer = addJtextArea(textLogDHCPServer, true, 10, 150, 500, 150);
		
		createLabel("LOG DHCP CLIENT", 520, 130, 22, 200);
		textLogDHCPClient = addJtextArea(textLogDHCPClient, true, 520, 150, 500, 150);

		createLabel("LOG SERVER TCP", 10, 300, 22, 200);
		textLogServerTCP = addJtextArea(textLogServerTCP, true, 10, 320, 500, 150);

		createLabel("LOG CLIENT TCP", 520, 300, 22, 200);
		textLogClientTCP = addJtextArea(textLogClientTCP, true, 520, 320, 500, 150);
		
		createLabel("LOG SERVER UDP", 10, 470, 22, 200);
		textLogServerUDP = addJtextArea(textLogServerUDP, true, 10, 490, 500, 150);

		createLabel("LOG CLIENT UDP", 520, 470, 22, 200);
		textLogClientUDP = addJtextArea(textLogClientUDP, true, 520, 490, 500, 150);
		
		this.add(panel);
	}
	
	private JTextArea addJtextArea(JTextArea jArea, boolean editable, int x, int y, int width, int height) {
		jArea = new JTextArea("");
		jArea.setLineWrap(true);
		jArea.setEditable(editable);
		JScrollPane scroll = new JScrollPane(jArea);
		scroll.setBounds(x, y, width, height);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll);
		return jArea;
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
		int port = isTcp() ? 5000 : 12345;
		for (int i = 0; i < qtd; i++) {
			DHCPClient client = new DHCPClient("127.0.0.1", port, isTcp(),  getClasse());
		}
		System.out.println("Número de requisições: ".concat(Integer.toString(qtd)));
	}
	
	public static synchronized void addLogDHCPServer(String text) {
		textLogDHCPServer.setText(textLogDHCPServer.getText().concat(getCurrentDateHour().concat(" DHCP SERVER: ").concat(text)).concat("\n"));
	}
	public static synchronized void addLogDHCPClient(String text) {
		textLogDHCPClient.setText(textLogDHCPClient.getText().concat(getCurrentDateHour().concat(" DHCP CLIENT: ").concat(text)).concat("\n"));
	}
	public static synchronized void addLogServerTCP(String text) {
		textLogServerTCP.setText(textLogServerTCP.getText().concat(getCurrentDateHour().concat(" TCP SERVER: ").concat(text)).concat("\n"));
	}
	public static synchronized void addLogClientTCP(String text) {
		textLogClientTCP.setText(textLogClientTCP.getText().concat(getCurrentDateHour().concat(" TCP CLIENT: ").concat(text)).concat("\n"));
	}
	public static synchronized void addLogServerUDP(String text) {
		textLogServerUDP.setText(textLogServerUDP.getText().concat(getCurrentDateHour().concat(" UDP SERVER: ").concat(text)).concat("\n"));
	}
	public static synchronized void addLogClientUDP(String text) {
		textLogClientUDP.setText(textLogClientUDP.getText().concat(getCurrentDateHour().concat(" UDP CLIENT: ").concat(text)).concat("\n"));
	}
	
	private void subirServidores(boolean tcp) {
		if (tcp) {
			subirServidorTCP();
		} else {
			subirServidorUDP();
		}
	}
	
	private void subirServidorTCP() {
		if (!DHCPServer.isRodando()) {
			DHCPServer.setRodando(true);
			DHCPServer.start(5000, isTcp());
		}
	}

	private void subirServidorUDP() {
		if (!DHCPServer.isServerUdpRodando()) {
			DHCPServer.setServerUdpRodando(true);
			DHCPServer.start(12345, isTcp());
		}
	}
	
	private static String getCurrentDateHour() {
		return format.format(Calendar.getInstance().getTime());
	}
}
