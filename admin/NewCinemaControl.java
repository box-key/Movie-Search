package admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import database.CinemaType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class NewCinemaControl extends AdminControl implements Initializable {
	@FXML
	public TextField cinemaName;
	@FXML
	public TextField xCord;
	@FXML
	public TextField yCord;
	@FXML
	public CheckBox g;
	@FXML
	public CheckBox pg;
	@FXML
	public CheckBox pg13;
	@FXML
	public CheckBox r;
	@FXML
	public CheckBox nc17;
	@FXML
	public CheckBox selectAll;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialized all CheckBox
		unSelectAll();
		
		// set event for select all button
		selectAll.setOnAction((event) -> {
			boolean selected = selectAll.isSelected();
			// check all boxes
			if(selected)  selectAll();
			if(!selected) unSelectAll();
		});
		
	}
	
	public void uploadCinema()  {
		// case 1
		if(cinemaName.getText().equals("") && xCord.getText().equals("") && yCord.getText().equals("") ) {
			JOptionPane.showMessageDialog(null, "Please Enter Name of Cinema and Address of Cinema", "Something Wrong", 1);
			return;
		}
		// case 2
		if(cinemaName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Name of Cinema", "Something Wrong", 1);
			return;
		}
		// case 3
		if(xCord.getText().equals("") && yCord.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Address of Cinema", "Something Wrong", 1);
			return;
		}
		// case 4
		if(xCord.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter X Coordinate of Cinema", "Something Wrong", 1);
			return;
		}
		// case 5
		if(yCord.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Y Coordinate of Cinema", "Something Wrong", 1);
			return;
		}
		if(cinemaName.getText().length() > 99) {
			JOptionPane.showMessageDialog(null, "Cinema name is too long", "Something Wrong", 1);
			return;
		}
		// make sure x coordinate is integer
        try { 
            Integer.parseInt(xCord.getText()); 
        }  
        catch (NumberFormatException e){ 
			JOptionPane.showMessageDialog(null, "Please Enter a valid x Coordinate of Cinema", "Something Wrong", 1);
			return;
        } 
        // make sure y coordinate is integer
        try { 
            Integer.parseInt(yCord.getText()); 
        }  
        catch (NumberFormatException e){ 
			JOptionPane.showMessageDialog(null, "Please Enter a valid y Coordinate of Cinema", "Something Wrong", 1);
			return;
        } 
       
        if(Integer.parseInt(xCord.getText()) > 100 || Integer.parseInt(yCord.getText()) > 100 ||
        		Integer.parseInt(xCord.getText()) < 0 ||Integer.parseInt(yCord.getText()) < 0 ) {
			JOptionPane.showMessageDialog(null, "Address x coordinate and y coordinate should be between 0-100", "Something Wrong", 1);
        	return;
        }
        
		CinemaType type = new CinemaType(g.isSelected(),pg.isSelected(),pg13.isSelected(),r.isSelected(),nc17.isSelected());
		
		try {
			db.insertCinema(cinemaName.getText(), Integer.parseInt(xCord.getText()), Integer.parseInt(yCord.getText()), type);
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Name/Address already existed in the database", "Something Wrong", 1);
			return;
		}
		
		JOptionPane.showMessageDialog(null, "Congratulations! Cinema Uploaded", "Congratualtions!", 2);
		
		cinemaName.setText("");
		xCord.setText("");
		yCord.setText("");
		
		unSelectAll();
	}
	protected void clearField() {
		cinemaName.setText("");
		xCord.setText("");
		yCord.setText("");
	}
	protected void selectAll() {
		g.setSelected(true);
		pg.setSelected(true);
		pg13.setSelected(true);
		r.setSelected(true);
		nc17.setSelected(true);
	}
	protected void unSelectAll() {
		g.setSelected(false);
		pg.setSelected(false);
		pg13.setSelected(false);
		r.setSelected(false);
		nc17.setSelected(false);
	}
	
}
