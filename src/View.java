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
	//private static JPanel bottom_panel;
	private static JTextField hotel_field;
	private static JTextField city_field;
	private static JComboBox rating_combo;
	private static JButton search_button;
	private static JTable result_table;
	private static DefaultTableModel result_table_model;
	private static int result_table_index = -1;

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
		main_panel = new JPanel();
		main_panel.setLayout(new BorderLayout());

		search_panel =  new JPanel();
		search_panel.setLayout(new BorderLayout());

		input_panel =  new JPanel();
		input_panel.setLayout(new GridLayout(2, 3));

		/*
		JPanel bottom_panel =  new JPanel();
		bottom_panel.setLayout(new GridLayout());
		*/

		hotel_field = new JTextField();
		city_field = new JTextField();
		rating_combo = new JComboBox(new String[] {"---", "5", "4", "3", "2", "1"});
		search_button = new JButton("Search");


		String[] label_names = new String[] {"hótel nafn", "borgar nafn", "stjörnu fjöldi"};
		for(String s : label_names) {
			JLabel label = new JLabel(s);
			label.setBorder(new EmptyBorder(0, 5, 0, 0));
			input_panel.add(label);
		}
		
		input_panel.add(hotel_field);
		input_panel.add(city_field);
		input_panel.add(rating_combo);

		result_table_model = new DefaultTableModel(new String[]{"nafn", "borg", "stjörnur", "herbergi"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		result_table = new JTable(result_table_model);
		result_table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		result_table.setShowGrid(false);
    	result_table.setShowVerticalLines(true);
    	result_table.setGridColor(Color.BLACK);

		search_panel.add(input_panel, BorderLayout.CENTER);
		search_panel.add(search_button, BorderLayout.EAST);
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

		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int row_count = result_table_model.getRowCount();

				for(int i = 0; i < row_count; i++) {
					result_table_model.removeRow(0);
				}

				String hotel = hotel_field.getText();
				String city = city_field.getText();
				String rating_string = (String)rating_combo.getItemAt(rating_combo.getSelectedIndex());
				int rating = (rating_string.equals("---") ? -1 : Integer.parseInt(rating_string));

				try {
					ArrayList<Hotel> hotels = api.getHotelsByName(hotel);
					//ArrayList<Hotel> hotels = api.getHotelsByCityAndRating(city, Integer.parseInt(rating));
					if(hotels.size() == 0) {
						JOptionPane.showMessageDialog(null, "ekkert fannst!");
						return;
					}

					for(Hotel h : hotels) {
						result_table_model.addRow(new Object[] {h.name, h.city, h.rating, h.rooms.size()});
					}

					for(int i = 0; i < 30; i++) {
						result_table_model.addRow(new Object[] {"bla", "bla", "bla", "bla"});
					}

				}catch(SQLException sql_e) {
					System.out.println(sql_e.getMessage());
				}
			}
		});

		/*
		result_table.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event) {
				int index = result_table.locationToIndex(new Point(event.getX(),
																  event.getY())
														);

				if (index != result_table_index) {
					result_table_index = index;
					System.out.println(index);
					result_table.repaint();
				}
			}
		});
		*/
	}
}
