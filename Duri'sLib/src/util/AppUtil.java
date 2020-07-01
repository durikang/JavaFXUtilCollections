package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AppUtil {
	
	
	
	public AppUtil() {
	}
	public static AppUtil getInstance() {
		return LazyHolder.ALERT;
	}
	
	private static class LazyHolder{
		private static final AppUtil ALERT= new AppUtil();
	}
	

	public void alert(String msg, String header) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		
		
		alert.show();
	}
	
}
