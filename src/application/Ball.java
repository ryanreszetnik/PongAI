package application;

import javafx.scene.shape.Rectangle;

public class Ball {
	public Rectangle ball = new Rectangle(5,5);
	public double xvel = 2;
	public double yvel = 2;
	
	public Ball(int x, int y){
		ball.setTranslateX(x);
		ball.setTranslateY(y);
	}
	
	public void move(){
		ball.setTranslateX(ball.getTranslateX()+xvel);
		ball.setTranslateY(ball.getTranslateY()+yvel);
	}
	
}
