package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.SokobanServer;

public class ViewModel extends  Observable implements Observer{
	
	SokobanServer model;
	public StringProperty ;
	public StringProperty ;
	public IntegerProperty usersConnected;
	
	public ViewModel (SokobanServer model){
		this.model=model;
		addUser= new SimpleStringProperty();
		removeUser = new SimpleStringProperty();
		usersConnected = new SimpleIntegerProperty();
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		if(o==model){ usersConnected.set(model.getResult());		
	}
	
	

}
