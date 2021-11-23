package application;
	
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	static ArrayList<Board> boards = new ArrayList<>();
	static ArrayList<Network> winners = new ArrayList<>();
	@Override
	
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();
			Scene scene = new Scene(root,1500,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			for(int i = 0; i < 20; i++){
				Board a = new Board(300,200,root);
				boards.add(a);
				a.setTranslate(i%5*300, (int)(i/5)*200);
			}
			
			
			
			AnimationTimer timer = new AnimationTimer(){

				@Override
				public void handle(long now) {
					// TODO Auto-generated method stub
					for(int i = 0; i < boards.size(); i++){
						boards.get(i).move();
					}
				}
				
			};
			
			
			
			timer.start();
			scene.setOnKeyPressed(e -> {
				switch (e.getCode()) {
				
				case N:
					newRound();
				
				case R:
					timer.start();
				}
			});
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public static void newRound(){
		System.out.println("new round");
		winners.clear();
		for(Board b: boards){
			b.score =0;
			b.rallies = 0;
			Network winner = b.score>0?b.leftPlayer:b.rightPlayer;
			winners.add(winner);
		}
		for(Board b: boards){
			b.leftPlayer = Network.merge(winners.get((int) (Math.random()*winners.size())), winners.get((int) (Math.random()*winners.size())));
			b.rightPlayer = Network.merge(winners.get((int) (Math.random()*winners.size())), winners.get((int) (Math.random()*winners.size())));
		}
		System.out.println("new round started");
	}
}
