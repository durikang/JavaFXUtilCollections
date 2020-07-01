package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager2 {
	private Stage stage;
	private Scene scene;

	public SceneManager2() {
		this.stage = new Stage();
	}
	
	public static SceneManager2 getInstance() {
		
		return LazyHolder.sceneManager;
	}
	private static class LazyHolder {
		//Ŭ���� �ε� �������� ����
		private static final SceneManager2 sceneManager = new SceneManager2();
	}

	

	public Stage getStage() {
		return this.stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setTitle(String title) {
		stage.setTitle(title);
	}

	public void moveScene(String location, String cssLocal) {
		
		String replaceLocation=location.replace(".", "/");
		location="/"+replaceLocation.concat(".fxml");
		
		String replaceCss=cssLocal.replace(".", "/");
		cssLocal="/"+replaceCss.concat(".css");
		
		System.out.println("SceneManager2::moveScene() Field value");
		System.out.println("FXML 위치 : "+location);
		
		System.out.println("SceneManager2::moveScene() Field value");
		System.out.println("css 위치 : "+cssLocal);
		
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(location));
			System.out.println("SceneManager2::moveScene()");
			System.out.println("root : " + root);

			scene = new Scene(root);
			System.out.println("scene : " + scene);

			scene.getStylesheets().add(getClass().getResource(cssLocal).toExternalForm());

			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveScene(String location) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(location));
			scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
