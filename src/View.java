import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;

class View extends JPanel {
	private static HotelAPI api;
	private static DefaultListModel result_list_model;

	public static void main(String[] args) throws SQLException {
		api = new HotelAPI();

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

		JTextField search_field_city = new JTextField("Sladu inn nafn a borg");
		JTextField search_field_rating = new JTextField("Sladu inn lagmarksfjolda stjarna");
		JButton search_button = new JButton("Search");

		result_list_model = new DefaultListModel();

		JList result_list = new JList(result_list_model);
		result_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		result_list.setLayoutOrientation(JList.VERTICAL);

		JScrollPane result_pane = new JScrollPane(result_list);

		input_panel.add(search_field_city, BorderLayout.LINE_START);
		input_panel.add(search_field_rating, BorderLayout.CENTER);
		input_panel.add(search_button, BorderLayout.EAST);

		for(int i = 0; i < 4; i++) bottom_panel.add(new JButton("Ok"));

		main_panel.add(input_panel, BorderLayout.NORTH);
		main_panel.add(result_pane, BorderLayout.CENTER);
		main_panel.add(bottom_panel, BorderLayout.SOUTH);

		frame.add(main_panel);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.out.println("exiting...");
				System.exit(0);
			}
		});

		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result_list_model.removeAllElements();

				String city = search_field_city.getText();
				String rating = search_field_rating.getText();


				try {
					//ArrayList<Hotel> hotels = api.getHotelsByName(text);
					ArrayList<Hotel> hotels = api.getHotelsByCityAndRating(city, Integer.parseInt(rating));

					for(Hotel h : hotels) {
						result_list_model.addElement(h.name + h.rating);
					}


				}catch(SQLException sql_e) {
					System.out.println(sql_e.getMessage());
				}
			}
		});
	}
}
