import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import HotelAPI.*;

class View extends JPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400, 400);

		/*
		|   input   |
		|___________|
		|           |
		|search res |
		|___________|
		|  takkar   |
		*/
		JPanel main_panel = new JPanel();
		main_panel.setLayout(new BorderLayout());

		JPanel input_panel =  new JPanel();
		input_panel.setLayout(new BorderLayout());

		JPanel bottom_panel =  new JPanel();
		bottom_panel.setLayout(new GridLayout());

		JTextField search_field = new JTextField("Sláðu inn leitar streng...");
		JButton search_button = new JButton("Search");
		
		DefaultListModel result_list_model = new DefaultListModel();
		for(int i = 0; i < 20; i++) result_list_model.addElement(""+(i+1));

		JList result_list = new JList(result_list_model);
		result_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		result_list.setLayoutOrientation(JList.VERTICAL);
		//result_list.setModel(result_list_model);

		JScrollPane result_pane = new JScrollPane(result_list);

		//result_pane.add(result_list);

		input_panel.add(search_field, BorderLayout.CENTER);
		input_panel.add(search_button, BorderLayout.EAST);
		
		for(int i = 0; i < 4; i++) bottom_panel.add(new JButton("Ok"));

		main_panel.add(input_panel, BorderLayout.NORTH);
		main_panel.add(result_pane, BorderLayout.CENTER);
		main_panel.add(bottom_panel, BorderLayout.SOUTH);

		frame.add(main_panel);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.out.println("what");
				System.exit(0);
			}
		});

		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = search_field.getText();
				System.out.println(text);
			}
		});
	}
}