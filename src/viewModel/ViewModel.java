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
	public StringProperty start ;
	public StringProperty stop ;
	public IntegerProperty usersConnected;
	
	public ViewModel (SokobanServer model){
		this.model=model;
		start= new SimpleStringProperty();
		stop= new SimpleStringProperty();
		usersConnected = new SimpleIntegerProperty();
	}
	
	public ViewModel() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void update(Observable o, Object arg) {
		if(o==model){ usersConnected.set(model.getUsersConnected());	
		
		}
	}

	public void start() {
		model.start();		
	}

	public void stop() {
     model.stopServer();
}
	
}
