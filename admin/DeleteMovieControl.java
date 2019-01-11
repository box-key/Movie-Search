package admin;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DeleteMovieControl extends AdminControl implements Initializable    {
	private String current = "";
	private Vector<String> movieList = new Vector<>();
	@FXML
	public ComboBox<String> cb;
	@FXML
	public Button delete;
	
	/*
	@FXML
	public TextField movieName;
	@FXML
	public Button change;
	@FXML
	public VBox container;
	private CheckBox selected = null;
	private Vector<String> ratingList = new Vector<>();
	private String rating = "";
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		update();
		/*
		// Initialize ComboBox for rating
		cb.getItems().addAll(
				"G",
				"PG",
				"PG13",
				"R",
				"NC17"
				);
		cb.setOnAction((event) -> {
			rating = cb.getSelectionModel().getSelectedItem();
		});
		
		// Initialize list of movies
		update();
		*/
	}
	
	private void update() {
		cb.getItems().clear();
		movieList = db.getMovieList();
		for(int i = 0; i < movieList.size(); i++) {
			cb.getItems().add(movieList.elementAt(i));
		}
		cb.setOnAction((event) -> {
			current = cb.getSelectionModel().getSelectedItem();
		});
		
	}
	
	public void delete() {
		if(current.equals("")) {
			JOptionPane.showMessageDialog(null, "Please Select a Movie", "Something Wrong", 1);
			return;
		}
		
		db.deleteMovie(current);
		JOptionPane.showMessageDialog(null, "Congratulations! Movie is deleted", "Congratulations!", 2);
		update();
	}
	
	
	/*
	public void changeProperties() {
		// case 0
		if(selected == null) {
			JOptionPane.showMessageDialog(null, "Please select a movie", "Something Wrong", 1);
			return;
		}
		// case 1
		if(rating.equals("") &&  title.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter a Title and Select rating", "Something Wrong", 1);
			return;
		}
		// case 2
		if(rating.equals("")) {
			JOptionPane.showMessageDialog(null,  "Please Select rating", "Something Wrong", 1);
			return;
		}
		// case 3
		if(movieName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter a title", "Something Wrong", 1);
			return;
		}
		// case 4
		if(selected == null) {
			JOptionPane.showMessageDialog(null, "Please Select a Movie", "Something Wrong", 1);
			return;
		}
		if(title.getText().length() > 99) {
			JOptionPane.showMessageDialog(null, "Movie tile is too long", "Something Wrong", 1);
			return;
		}
		// case 5
		try {
			db.updateMovie(selected.getText(), movieName.getText(), rating);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Cinema Name already existed", "Something Wrong", 1);
			return;
		}
		JOptionPane.showMessageDialog(null, "Congratulations! Properties changed sucessfully", "Congratulations!", 2);
		update();
		clearField();
		selected = null;
	}
	*/
	/*
	public void delete() {
		if(selected == null) {
			JOptionPane.showMessageDialog(null, "Please Select a Movie", "Something Wrong", 1);
			return;
		}
		
		db.deleteMovie(selected.getText());
		JOptionPane.showMessageDialog(null, "Congratulations! Movie is deleted", "Congratulations!", 2);
		//update();
		clearField();
		selected = null;
	}
	/*
	private void update() {
		container.getChildren().clear();
		movieList = db.getMovieList();
		ratingList = db.getRating();
		for(int i = 0; i < movieList.size(); i++) {
			CheckBox temp = new CheckBox(movieList.elementAt(i));
			temp.setSelected(false);
			temp.setId(Integer.toString(i));
			temp.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					if(temp.isSelected()) {
						selected = temp;
						for(int i = 0; i < container.getChildren().size(); i++) {
							if(Integer.parseInt(temp.getId())!=i) {
								container.getChildren().get(i).setDisable(true);
								movieName.setText(movieList.elementAt(Integer.parseInt(temp.getId())));
								cb.getSelectionModel().select(ratingList.elementAt(Integer.parseInt(temp.getId())));
							}
						}
					}
					if(!temp.isSelected()) {
						for(int i = 0; i < container.getChildren().size(); i++) {
							container.getChildren().get(i).setDisable(false);
							clearField();
						}
					}
				}
			});
		container.getChildren().add(temp);
		}
	}
	
	private void clearField() {
		movieName.setText("");
		cb.getSelectionModel().clearSelection();
		selected = null;
	}
	*/
}
