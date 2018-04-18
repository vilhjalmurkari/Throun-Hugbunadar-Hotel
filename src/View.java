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
	- ætti sennilega að láta room gluggann uppfærast þegar þú velur eitthvað annað hótel
	- reset á enter
	- esc clear
*/
class View extends JPanel {
	private static HotelAPI api;
	private static User test_user;
	private static ArrayList<Hotel> hotels;
	private static ArrayList<Room> rooms; 

	private static JFrame main_frame;
	private static JFrame room_frame;
	private static JPanel room_main_panel;

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
	private static JButton book_button;
	private static JTable room_table;
	private static DefaultTableModel room_table_model;
	private static int room_selected_index = -1;


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

		result_table = new JTable(result_table_model) {

			public String getToolTipText(MouseEvent event) {
				String result = "";
				Point p = event.getPoint();

				int rowIndex = rowAtPoint(p);
				Hotel hotel = hotels.get(rowIndex);

				result += "<html>";
				result += "Lýsing:";
				result += "<br>";
				result += hotel.description;
				result += "<br>";
				result += "--";
				result += "<br>";
				result += "Tög:"; 
				result += "<br>";

				int i = 2;
				for(String s : hotel.tags) {
					if(i % 3 == 0) result += "<br>";
					result += s + ", ";
					i--;
				}

				result += "</html>";

				return result;
			}
		};

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

		JPanel temp_panel = new JPanel();
		temp_panel.setLayout(new GridLayout());
		temp_panel.add(new JScrollPane(result_table));
		temp_panel.setBackground(Color.WHITE);

		main_panel.add(temp_panel, BorderLayout.CENTER);
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

	public static void createRoomsFrame() {
		room_frame = new JFrame();
		room_frame.setSize(500, 500);

		room_main_panel = new JPanel();
		room_main_panel.setLayout(new BorderLayout());
		room_main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel temp_panel = new JPanel();
		temp_panel.setLayout(new BorderLayout());
		temp_panel.setBorder(new EmptyBorder(0, 0, 5, 0));

		temp_panel.add(new JLabel("Herbergi:"), BorderLayout.WEST);

		room_main_panel.add(temp_panel, BorderLayout.NORTH);

		room_table_model = new DefaultTableModel(new String[]{"Stærð", "Fjöldi rúma", "verð"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		room_table = new JTable(room_table_model) {

			public String getToolTipText(MouseEvent event) {
				String result = "";
				Point p = event.getPoint();

				int rowIndex = rowAtPoint(p);
				Hotel hotel = hotels.get(rowIndex);

				result += "<html>";
				result += "Tög:"; 
				result += "<br>";

				int i = 2;
				for(String s : hotel.tags) {
					if(i % 3 == 0) result += "<br>";
					result += s + ", ";
					i--;
				}

				result += "</html>";

				return result;
			}
		};

		room_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		room_table.setShowGrid(false);
    	room_table.setShowVerticalLines(true);
    	room_table.setGridColor(Color.BLACK);

    	temp_panel = new JPanel();
		temp_panel.setLayout(new GridLayout());
		temp_panel.add(new JScrollPane(room_table));
		temp_panel.setBackground(Color.WHITE);

		room_main_panel.add(temp_panel, BorderLayout.CENTER);

		temp_panel = new JPanel();
		temp_panel.setLayout(new BorderLayout());
		book_button = new JButton("bóka");
		temp_panel.add(book_button, BorderLayout.EAST);

		room_main_panel.add(temp_panel, BorderLayout.SOUTH);

		room_frame.add(room_main_panel);
	}

	public static void main(String[] args) throws SQLException {
		api = new HotelAPI();
		//test_user = new User(1);

		createMainFrame();
		createRoomsFrame();

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
					int row_count = room_table_model.getRowCount();

					for(int i = 0; i < row_count; i++) {
						room_table_model.removeRow(0);
					}

					rooms = hotels.get(table_selected_index).rooms;

					for(Room r : rooms) {
						room_table_model.addRow(new Object[] {r.size, r.bed_count, r.price});
					}

					room_frame.setVisible(true);
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

				String hotel_city_or_name = city_field.getText();
				String string_rating_min = (String)min_rating_combo.getItemAt(min_rating_combo.getSelectedIndex());
				String string_rating_max = (String)max_rating_combo.getItemAt(max_rating_combo.getSelectedIndex());
				int min_rating = (string_rating_min.equals("---") ? -1 : Integer.parseInt(string_rating_min));
				int max_rating = (string_rating_max.equals("---") ? -1 : Integer.parseInt(string_rating_max));

				try {
					hotels = api.hotelSearch(hotel_city_or_name, min_rating, max_rating);
	
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

		book_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

			}
		});

		result_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				table_selected_index = result_table.getSelectedRow();
				System.out.println("selected hotel: " + table_selected_index);
			}
		});

		room_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				room_selected_index = room_table.getSelectedRow();
				System.out.println("selected room: " + room_selected_index);
			}
		});
	}
}
