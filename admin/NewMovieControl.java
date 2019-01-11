package admin;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class NewMovieControl extends AdminControl implements Initializable   {
	private String rating = "";
	@FXML
	public TextField title;
	@FXML
	public ComboBox<String> cb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
		
	}
	
	public void uploadMovie() {
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
		if(title.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Enter a title", "Something Wrong", 1);
			return;
		}
		// case 4
		if(title.getText().length() > 99) {
			JOptionPane.showMessageDialog(null, "Movie tile is too long", "Something Wrong", 1);
			return;
		}
		// case 5      if movie title already existed in database
		
		try {
			db.insertMovie(title.getText(), rating);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,  "Movie Title Already Existed", "Something Wrong", 1);
			return;
		}
	
		JOptionPane.showMessageDialog(null, "Congratulations! Movie is uploaded", "Congratulations!", 2);
		title.setText("");

	}

}
