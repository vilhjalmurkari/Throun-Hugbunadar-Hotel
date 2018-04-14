import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Date;
import java.util.ArrayList;
import java.sql.*;
import hotelAPI.*;


/*TODO: - scroll virkar ekki
		- combo box fyrir rating 
		- combo box fyrir borg
*/
class View extends JPanel {
	private static HotelAPI api;
	private static JPanel main_panel;
	private static JPanel input_panel;
	private static JPanel result_panel;
	private static JPanel result_label_panel;
	private static JPanel bottom_panel;
	private static JTextField search_field;
	private static JButton search_button;
	private static JTable result_table;
	private static JScrollPane result_scroll;
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

		input_panel =  new JPanel();
		input_panel.setLayout(new BorderLayout());

		JPanel result_panel =  new JPanel();
		result_panel.setLayout(new BorderLayout());
		JPanel result_label_panel =  new JPanel();
		result_label_panel.setLayout(new GridLayout());

		JPanel bottom_panel =  new JPanel();
		bottom_panel.setLayout(new GridLayout());

		search_field = new JTextField("Sláðu inn nafn á hóteli...");
		search_button = new JButton("Search");

		result_table_model = new DefaultTableModel(0, 4) {
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

		result_scroll = new JScrollPane(result_table);

		input_panel.add(search_field, BorderLayout.CENTER);
		input_panel.add(search_button, BorderLayout.EAST);

		String[] label_names = {"nafn", "borg", "stjörnur", "herbergi"};
		for(String s : label_names) {
			JLabel label = new JLabel(s, JLabel.CENTER);
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			result_label_panel.add(label);	
		}

		result_panel.add(result_label_panel, BorderLayout.NORTH);
		result_panel.add(result_table, BorderLayout.CENTER);

		for(int i = 0; i < 4; i++) bottom_panel.add(new JButton("Ok"));

		main_panel.add(input_panel, BorderLayout.NORTH);
		main_panel.add(result_panel, BorderLayout.CENTER);
		main_panel.add(result_scroll, BorderLayout.EAST);
		main_panel.add(bottom_panel, BorderLayout.SOUTH);

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

				//System.out.println("before: " + row_count);
				for(int i = 0; i < row_count; i++) {
					result_table_model.removeRow(0);
				}
				//System.out.println("after: " + row_count);

				String text = search_field.getText();

				try {
					ArrayList<Hotel> hotels = api.getHotelsByName(text);

					for(Hotel h : hotels) {
						result_table_model.addRow(new Object[] {h.name, h.city, h.rating, h.rooms.size()});
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
