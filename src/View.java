import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.Date;
import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;


/*
TODO:
*/
class View extends JPanel {
	private static HotelAPI api;
	private static JPanel main_panel;
	private static JPanel search_panel;
	private static JPanel input_panel;
	private static JPanel button_panel;
	private static JTextField city_field;
	private static JComboBox min_rating_combo;
	private static JComboBox max_rating_combo;
	private static JButton search_button;
	private static JButton reset_button;
	private static JTable result_table;
	private static DefaultTableModel result_table_model;
	private static int result_table_index = -1;

	public static void main(String[] args) throws SQLException {
		api = new HotelAPI();

		JFrame frame = new JFrame();
		frame.setSize(500, 500);

		/*
		|   input   |
		|___________|
		|           |
		|search res |
		|___________|
		|  takkar   |
		*/
		main_panel = new JPanel();
		main_panel.setLayout(new BorderLayout());

		search_panel =  new JPanel();
		search_panel.setLayout(new BorderLayout());

		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(0, 1));

		input_panel =  new JPanel();
		input_panel.setLayout(new GridLayout(2, 3));

		/*
		JPanel bottom_panel =  new JPanel();
		bottom_panel.setLayout(new GridLayout());
		*/

		city_field = new JTextField();
		String[] rating_string = {"---", "5", "4", "3", "2", "1"};
		min_rating_combo = new JComboBox(rating_string);
		max_rating_combo = new JComboBox(rating_string);
		search_button = new JButton("Search");
		reset_button = new JButton("Reset");


		String[] label_names = new String[] {"Nafn borgar", "Lágmark stjarna", "Hámark stjarna"};
		for(String s : label_names) {
			JLabel label = new JLabel(s);
			label.setBorder(new EmptyBorder(0, 5, 0, 0));
			input_panel.add(label);
		}
		
		input_panel.add(city_field);
		input_panel.add(min_rating_combo);
		input_panel.add(max_rating_combo);

		button_panel.add(search_button);
		button_panel.add(reset_button);

		result_table_model = new DefaultTableModel(new String[]{"nafn", "borg", "stjörnur", "herbergi"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		result_table = new JTable(result_table_model);
		result_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		result_table.setShowGrid(false);
    	result_table.setShowVerticalLines(true);
    	result_table.setGridColor(Color.BLACK);

		search_panel.add(input_panel, BorderLayout.CENTER);
		search_panel.add(button_panel, BorderLayout.EAST);
		search_panel.setBorder(new EmptyBorder(0, 0, 5, 0));

		//for(int i = 0; i < 4; i++) bottom_panel.add(new JButton("Ok"));
		main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		main_panel.add(search_panel, BorderLayout.NORTH);
		main_panel.add(new JScrollPane(result_table), BorderLayout.CENTER);
		//main_panel.add(bottom_panel, BorderLayout.SOUTH);


		frame.add(main_panel);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event){
				System.out.println("exiting...");
				System.exit(0);
			}
		});

		reset_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				city_field.setText("");
				min_rating_combo.setSelectedIndex(0);
				max_rating_combo.setSelectedIndex(0);
			}
		});

		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int row_count = result_table_model.getRowCount();

				for(int i = 0; i < row_count; i++) {
					result_table_model.removeRow(0);
				}

				String city_name = city_field.getText();
				String string_rating_min = (String)min_rating_combo.getItemAt(min_rating_combo.getSelectedIndex());
				String string_rating_max = (String)max_rating_combo.getItemAt(max_rating_combo.getSelectedIndex());
				int min_rating = (string_rating_min.equals("---") ? -1 : Integer.parseInt(string_rating_min));
				int max_rating = (string_rating_max.equals("---") ? -1 : Integer.parseInt(string_rating_max));

				try {
					ArrayList<Hotel> hotels = api.hotelSearch(city_name, min_rating, max_rating);
	
					if(hotels.size() == 0) {
						JOptionPane.showMessageDialog(null, "ekkert fannst!");
						return;
					}

					for(Hotel h : hotels) {
						result_table_model.addRow(new Object[] {h.name, h.city, h.rating, h.rooms.size()});
					}

				}catch(SQLException sql_e) {
					System.out.println(sql_e.getMessage());
				}
			}
		});

		result_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(result_table.getSelectedRow());
			}
		});
	}
}
