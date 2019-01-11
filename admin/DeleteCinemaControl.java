package admin;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JOptionPane;

import database.CinemaType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DeleteCinemaControl extends NewCinemaControl implements Initializable {
	/*
	private Vector<String> xCordList = new Vector<>();
	private Vector<String> yCordList = new Vector<>();
	private CheckBox selected = null;
	@FXML
	public VBox container;
	@FXML
	public Button change;
	*/
	private Vector<String> cinemaList = new Vector<>();
	private String current = "";
	@FXML
	public Button delete;
	@FXML
	public ComboBox<String> cb;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		update();
		/*
		unSelectAll();
		selectAll.setOnAction((event) -> {
			boolean selected = selectAll.isSelected();
			// check all boxes
			if(selected) selectAll();
			if(!selected) unSelectAll();
		});
		*/
	}
	
	private void update() {
		cb.getItems().clear();
		cinemaList = db.getCinema();
		for(int i = 0; i < cinemaList.size(); i++) {
			cb.getItems().add(cinemaList.elementAt(i));
		}
		cb.setOnAction((event) -> {
			current = cb.getSelectionModel().getSelectedItem();
		});
	}
	
	public void delete() {
		if(current.equals("")) {
			JOptionPane.showMessageDialog(null, "Please Select a Cinema", "Something Wrong", 1);
			return;
		}
		
		db.deleteCinema(current);
		JOptionPane.showMessageDialog(null, "Congratulations! Movie is deleted", "Congratulations!", 2);
		update();
	}
	/*
	public void changeProperties() {
		if(selected == null) {
			JOptionPane.showMessageDialog(null, "Please Select a Cinema", "Something Wrong", 1);
			return;
		}
		if(cinemaName.getText().equals("") && xCord.getText().equals("") && yCord.getText().equals("") ) {
			JOptionPane.showMessageDialog(null, "Please Enter Name of Cinema and Address of Cinema", "Something Wrong", 1);
			return;
		}
		if(cinemaName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Name of Cinema", "Something Wrong", 1);
			return;
		}
		if(xCord.getText().equals("") && yCord.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Address of Cinema", "Something Wrong", 1);
			return;
		}
		if(xCord.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter X Coordinate of Cinema", "Something Wrong", 1);
			return;
		}
		if(yCord.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter Y Coordinate of Cinema", "Something Wrong", 1);
			return;
		}
		if(cinemaName.getText().length() > 99) {
			JOptionPane.showMessageDialog(null, "Cinema name is too long", "Something Wrong", 1);
			return;
		}
        try { 
            Integer.parseInt(xCord.getText()); 
        }  
        catch (NumberFormatException e){ 
			JOptionPane.showMessageDialog(null, "Please Enter a valid x Coordinate of Cinema", "Something Wrong", 1);
			return;
        } 
        try { 
            Integer.parseInt(yCord.getText()); 
        }  
        catch (NumberFormatException e){ 
			JOptionPane.showMessageDialog(null, "Please Enter a valid y Coordinate of Cinema", "Something Wrong", 1);
			return;
        } 
		CinemaType type = new CinemaType(g.isSelected(),pg.isSelected(),pg13.isSelected(),r.isSelected(),nc17.isSelected());
		try {
			db.updateCinema(selected.getText(),cinemaName.getText(),Integer.parseInt(xCord.getText()), Integer.parseInt(yCord.getText()),type);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Name/Address already existed in the database", "Something Wrong", 1);
			return;
		}
		JOptionPane.showMessageDialog(null, "Congratulations, properties updated!", "Congratulations!", 2);
		update();
		clearField();
		selected = null;
		
	}
	public void delete() {
		if(selected == null) {
			JOptionPane.showMessageDialog(null, "Please Select a Cinema", "Something Wrong", 1);
			return;
		}
		db.deleteCinema(selected.getText());
		JOptionPane.showMessageDialog(null, "Congratulations! Cinema is deleted", "Congratulations!", 2);
		update();
		clearField();
		unSelectAll();
		selected = null;
	}
	
	private void update() {
		container.getChildren().clear();
		cinemaList = db.getCinema();
		xCordList = db.getXCord();
		yCordList = db.getYCord();
		for(int i = 0; i < cinemaList.size(); i++) {
			CheckBox temp = new CheckBox(cinemaList.elementAt(i));
			temp.setSelected(false);
			temp.setId(Integer.toString(i));
			temp.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					if(temp.isSelected()) {
						selected = temp;
						String id = Integer.toString(db.getCinemaId(temp.getText()));
						CinemaType type = db.getType(id);
						for(int i = 0; i < container.getChildren().size(); i++) {
							if(Integer.parseInt(temp.getId())!=i) {
								container.getChildren().get(i).setDisable(true);
								cinemaName.setText(cinemaList.elementAt(Integer.parseInt(temp.getId())));
								xCord.setText(xCordList.elementAt(Integer.parseInt(temp.getId())));
								yCord.setText(yCordList.elementAt(Integer.parseInt(temp.getId())));
								if(type.getG()) g.setSelected(true);
								else g.setSelected(false);
								if(type.getPG()) pg.setSelected(true);
								else pg.setSelected(false);
								if(type.getPG_13()) pg13.setSelected(true);
								else pg13.setSelected(false);
								if(type.getR()) r.setSelected(true);
								else r.setSelected(false);
								if(type.getNC17()) nc17.setSelected(true);
								else nc17.setSelected(false);
							}
						}
					}
					if(!temp.isSelected()) {
						for(int i = 0; i < container.getChildren().size(); i++) {
							container.getChildren().get(i).setDisable(false);
							clearField();
							selected = null;
							unSelectAll();
						}
					}
				}
			});
		container.getChildren().add(temp);
		}
	}

	*/
}
