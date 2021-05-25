package project.RUCRateCalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * RUC rate calculator for diesel vehicle road use charges in New Zealand.
 * Standard rate is as follows: 
 * <3.5 tons = $72 p/1000km 
 * 3.5 - 6 tons = $78 p/1000km 
 * 6 - 9 tons = $159 p/1000km 
 * >9 tons = $334 p/1000km
 * 
 * Applies for powered vehicles with two axles.
 * 
 * @author Fabian Fagan
 */
public class Calculator extends JFrame {
	public static void main(String[] args) {
		new Calculator();
	}

	public Calculator() {
		super("RUC Fees");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		String chosenWeight = "";
		String chosenKm = "";
		JTextField weight = new JTextField("");
		JTextField km = new JTextField("");

		// input panel
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Weight (tons e.g. 2.5):"));
		panel.add(weight);
		panel.add(new JLabel("KM:"));
		panel.add(km);
		int result = JOptionPane.showConfirmDialog(null, panel, "Enter weight and kilometres driven:",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		// assign values 
		if (result == JOptionPane.OK_OPTION) {
			if (!weight.getText().equals("")) {
				chosenWeight = weight.getText();
			}

			if (!weight.getText().equals("")) {
				chosenKm = km.getText();
			}
		}

		// results panel
		setBounds(600, 400, 250, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(true);
		setMinimumSize(this.getSize());
		setVisible(true);
		JPanel display = new JPanel();
		this.add(display);
		// calculate result
		String calculatedPrice = calculatePrice(chosenWeight, chosenKm);
		JLabel text = new JLabel("Cost:");
		text.setFont(text.getFont().deriveFont(66.0f));
		text.setForeground(Color.black);
		JLabel num = new JLabel("$" + calculatedPrice);
		num.setFont(num.getFont().deriveFont(44.0f));
		num.setForeground(Color.green);
		display.add(text);
		display.add(num);
		display.setBackground(Color.LIGHT_GRAY);

	}

	private String calculatePrice(String chosenWeight, String chosenKm) {
		double weight = Double.parseDouble(chosenWeight);
		double km = Double.parseDouble(chosenKm);
		int weightClass = calculateWeightClass(chosenWeight, chosenKm);
		double fee = 0;

		if (weightClass == 1) {
			fee = (km / 1000) * 72; // $72 per 1000km
		}
		if (weightClass == 2) {
			fee = (km / 1000) * 78; // $78 per 1000km
		}
		if (weightClass == 3) {
			fee = (km / 1000) * 159; // $159 per 1000km
		}
		if (weightClass == 4) {
			fee = (km / 1000) * 334; // $334 per 1000km
		}
		fee = Math.round(fee * 100.0) / 100.0; // round to 2dp
		String toReturn = Double.toString(fee);
		return toReturn;
	}

	private int calculateWeightClass(String chosenWeight, String chosenKm) {
		double weight = Double.parseDouble(chosenWeight);
		int weightClass = 1; // default to class 1 if weight is null
		if (weight < 3.5) {
			weightClass = 1;
		}
		if ((weight > 3.5) && (weight < 6)) {
			weightClass = 2;
		}
		if ((weight > 6) && (weight < 9)) {
			weightClass = 3;
		}
		if ((weight > 6) && (weight < 9)) {
			weightClass = 3;
		}
		if (weight > 9) {
			weightClass = 4;
		}
		return weightClass;
	}
}
