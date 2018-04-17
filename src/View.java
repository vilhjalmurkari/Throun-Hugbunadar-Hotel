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
	- reset á enter
	- esc clear
	- non case sensitive, nema þegar fyrsti stafur er upper 
*/
class View extends JPanel {
	private static HotelAPI api;
	private static User test_user;
	private static ArrayList<Hotel> hotels; 

	private static JFrame main_frame;
	private static JFrame hotel_frame;
	private static JPanel hotel_main_panel;
	private static JPanel hotel_info_panel;
	private static JPanel room_info_panel;

	private static JPanel main_panel;
	private static JPanel search_panel;
	private static JPanel input_panel;
	private static JPanel button_panel;
	private static JPanel bottom_panel;
	private static JTextField city_field;
	private static JComboBox min_rating_combo;
	private static JComboBox max_rating_combo;
	private static JButton search_button;
	private static JButton reset_button;
	private static JTable result_table;
	private static DefaultTableModel result_table_model;
	private static int table_selected_index = -1;

	private static JButton room_button;
	private static JTextField hotel_name_field;
	private static JTextField hotel_city_field;
	private static JTextField hotel_rating_field;


	public static void createMainFrame() {
		main_frame = new JFrame();
		main_frame.setSize(550, 500);

		main_panel = new JPanel();
		main_panel.setLayout(new BorderLayout());

		search_panel =  new JPanel();
		search_panel.setLayout(new BorderLayout());

		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(0, 1));

		input_panel =  new JPanel();
		input_panel.setLayout(new GridLayout(2, 3));

		bottom_panel =  new JPanel();
		bottom_panel.setLayout(new BorderLayout());

		city_field = new JTextField();
		String[] rating_string = {"---", "5", "4", "3", "2", "1"};
		min_rating_combo = new JComboBox(rating_string);
		max_rating_combo = new JComboBox(rating_string);
		search_button = new JButton("Search");
		reset_button = new JButton("Reset");


		String[] label_names = new String[] {"Nafn borgar/hótels", "Lágmark stjarna", "Hámark stjarna"};
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

		room_button = new JButton("Skoða herbergi");

		bottom_panel.add(room_button, BorderLayout.EAST);

		main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		main_panel.add(search_panel, BorderLayout.NORTH);
		main_panel.add(new JScrollPane(result_table), BorderLayout.CENTER);
		main_panel.add(bottom_panel, BorderLayout.SOUTH);


		main_frame.add(main_panel);
		main_frame.setVisible(true);
	}

	public static JPanel createInfoPanel(String label, JTextField info_field) {
		JPanel temp_panel = new JPanel();
		temp_panel.setLayout(new BorderLayout());

		temp_panel.setBorder(new EmptyBorder(0, 0, 2, 0));
		temp_panel.add(new JLabel(label), BorderLayout.WEST);

		temp_panel.add(info_field, BorderLayout.CENTER);

		return temp_panel;
	}

	public static void createHotelFrame() {
		hotel_frame = new JFrame();
		hotel_frame.setSize(500, 500);

		hotel_main_panel = new JPanel();
		hotel_main_panel.setLayout(new GridLayout(1, 2));
		hotel_main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		hotel_info_panel = new JPanel();
		hotel_info_panel.setLayout(new BorderLayout());
		hotel_info_panel.setBorder(new EmptyBorder(0, 0, 0, 5));

		JPanel static_info_panel = new JPanel();
		static_info_panel.setLayout(new GridLayout(0, 1));
		static_info_panel.setSize(new Dimension(200, 100));

		hotel_name_field = new JTextField();
		hotel_city_field = new JTextField();
		hotel_rating_field = new JTextField();
		
		hotel_name_field.setEditable(false);
		hotel_city_field.setEditable(false);
		hotel_rating_field.setEditable(false);

		static_info_panel.add(createInfoPanel("Nafn: ", hotel_name_field));
		static_info_panel.add(createInfoPanel("Borg: ", hotel_city_field));
		static_info_panel.add(createInfoPanel("Stjörnur: ", hotel_rating_field));

		hotel_info_panel.add(static_info_panel, BorderLayout.NORTH);

		hotel_info_panel.add(new JTextArea());

		room_info_panel = new JPanel();
		room_info_panel.setLayout(new BorderLayout());

		room_info_panel.add(new JLabel("Herbergi:"), BorderLayout.NORTH);
		room_info_panel.add(new JList(), BorderLayout.CENTER);

		JPanel temp_panel = new JPanel();
		temp_panel.setLayout(new BorderLayout());
		temp_panel.add(new JButton("bóka"), BorderLayout.EAST);

		room_info_panel.add(temp_panel, BorderLayout.SOUTH);

		hotel_main_panel.add(hotel_info_panel);
		hotel_main_panel.add(room_info_panel);

		hotel_frame.add(hotel_main_panel);

		/*
		hotel_main_panel = new JPanel();
		hotel_main_panel.setLayout(new GridLayout(5, 5));
		hotel_main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		hotel_info_panel = new JPanel();
		hotel_info_panel.setLayout(new GridLayout(1, 0));

		room_info_panel = new JPanel();
		room_info_panel.setLayout(new GridLayout(1, 0));

		hotel_info_panel.add(new JTextArea());
		room_info_panel.add(new JTextArea());

		hotel_main_panel.add(new JTextArea());
		hotel_main_panel.add(new JTextArea());

		hotel_frame.add(hotel_main_panel);
		*/
	}


	public static void updateHotelFrame() {
		Hotel h = hotels.get(table_selected_index);

		hotel_name_field.setText(h.name);
		hotel_city_field.setText(h.city);
		hotel_rating_field.setText(Integer.toString(h.rating));
		/*

		String[][] label_names = new String[][] {{"Nafn: ", h.name},
											   	 {"Borg: ", h.city},
											   	 {"Stjörnur: ", Integer.toString(h.rating)},
											   	 {"Lýsing: ", h.description}};

		

		for(String[] s : label_names) {
			JPanel temp_panel = new JPanel();
			temp_panel.setLayout(new BorderLayout());

			JPanel label_panel = new JPanel(new BorderLayout());
			label_panel.setBorder(new EmptyBorder(0, 0, 2, 0));
			label_panel.add(new JLabel(s[0]), BorderLayout.WEST);

			temp_panel.add(label_panel, BorderLayout.NORTH);
			JTextArea temp_text_area = new JTextArea(s[1]);
			temp_text_area.setEditable(false);
			temp_panel.add(temp_text_area, BorderLayout.CENTER);

			temp_panel.setBorder(new EmptyBorder(5, 0, 0, 0));

			hotel_info_panel.add(temp_panel);
		}

		hotel_info_panel.add(new JList());

		hotel_main_panel.add(hotel_info_panel);

		DefaultTableModel room_table_model = new DefaultTableModel(new String[]{"Stærð", "rúm", "verð"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable room_table = new JTable(room_table_model);
		room_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		room_table.setShowGrid(false);
    	room_table.setShowVerticalLines(true);
    	room_table.setGridColor(Color.BLACK);

    	JPanel room_panel = new JPanel();
		room_panel.setBorder(new EmptyBorder(0, 5, 0, 0));

    	room_panel.add(new JScrollPane(room_table), BorderLayout.CENTER);
    	hotel_main_panel.add(room_panel);
    	*/
	}

	public static void main(String[] args) throws SQLException {
		api = new HotelAPI();
		//test_user = new User(1);

		createMainFrame();
		createHotelFrame();

		main_frame.addWindowListener(new WindowAdapter() {
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

		room_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(table_selected_index > -1) {
					//hotel_main_panel.removeAll();
					updateHotelFrame();
					hotel_frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "ekkert hótel valið!");
				}
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
					hotels = api.hotelSearch(city_name, min_rating, max_rating);
	
					if(hotels.size() == 0) {
						JOptionPane.showMessageDialog(null, "ekkert fannst!");
						table_selected_index = -1;
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
				table_selected_index = result_table.getSelectedRow();
				System.out.println(table_selected_index);
			}
		});		
	}
}
