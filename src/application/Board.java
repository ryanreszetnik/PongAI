package application;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Board {
	Rectangle Top;
	Rectangle Left;
	Rectangle Right;
	Rectangle Bottom;
	Rectangle leftPaddle;
	Rectangle rightPaddle;
	Network leftPlayer;
	Network rightPlayer;
	Label scoreDisplay = new Label();
	
	Ball ball = new Ball(10,10);
	int sizex;
	int sizey;
	public static final double paddleSpeed = 4;
	public static final double maxXVel = 3;
	public static final double maxYVel = 3;
	public int score = 0;
	public int rallies = 0;

	public Board(int xsize, int ysize, Pane root) {
		Top = new Rectangle(xsize, 5);
		Bottom = new Rectangle(xsize, 5);
		Left = new Rectangle(5, ysize);
		Right = new Rectangle(5, ysize);
		leftPaddle=new Rectangle(5,50);
		rightPaddle=new Rectangle(5,50);
		
		Right.setTranslateX(xsize - 5);
		Bottom.setTranslateY(ysize - 5);
		
		leftPaddle.setTranslateX(10);
		rightPaddle.setTranslateX(xsize-15);
		leftPaddle.setTranslateY(ysize/2-leftPaddle.getHeight()/2);
		rightPaddle.setTranslateY(ysize/2-rightPaddle.getHeight()/2);
		
		sizex = xsize;
		sizey = ysize;
		root.getChildren().addAll(Top, Bottom, Left, Right, ball.ball, leftPaddle, rightPaddle, scoreDisplay);
		setTranslate(0,0);
//		System.out.println("starting networks");
		leftPlayer=new Network();
		rightPlayer=new Network();
		int[] size = {4,10,10,1};
//		System.out.println("starting to setup");
		leftPlayer.setUpRandom(size);
		rightPlayer.setUpRandom(size);
//		System.out.println("setup");
		ball.xvel=Math.random()*6-3;
		ball.yvel=Math.random()*6-3;
	}

	public void setTranslate(int x, int y) {
		Top.setTranslateX(x);
		Bottom.setTranslateX(x);
		Left.setTranslateX(x);
		Right.setTranslateX(x + sizex - 5);

		Top.setTranslateY(y);
		Bottom.setTranslateY(y + sizey - 5);
		Left.setTranslateY(y);
		Right.setTranslateY(y);
		
		leftPaddle.setTranslateY(y+sizey/2-leftPaddle.getHeight()/2);
		rightPaddle.setTranslateY(y+sizey/2-rightPaddle.getHeight()/2);
		leftPaddle.setTranslateX(x+10);
		rightPaddle.setTranslateX(x+sizex-15);
		
		ball.ball.setTranslateX(x+sizex/2);
		ball.ball.setTranslateY(y+sizey/2);
		
		scoreDisplay.setTranslateX(x+sizex/2);
		scoreDisplay.setTranslateY(y+sizey/2);
	}

	public void move() {
		if(Math.abs(score)>5 || rallies > 40){
			
			Main.newRound();
			scoreDisplay.setText(score+"");
		}
		ball.move();
		if (collides(ball.ball,Bottom)||collides(ball.ball,Top)) {
			ball.yvel*=-1;
		}
		if (collides(ball.ball,Left)){
			score--;
		}else if(collides(ball.ball,Right)){
			score++;
		}
		
		if (collides(ball.ball,Left)||collides(ball.ball,Right)) {
			setTranslate((int)Top.getTranslateX(),(int)Top.getTranslateY());
			rallies++;
			ball.xvel=Math.random()+1;
			if(score>0){
				ball.xvel*=-1;
			}
			scoreDisplay.setText(score+"");
			ball.yvel=Math.random()*6-3;
		}
		
		
		if(collides(ball.ball,leftPaddle)||collides(ball.ball,rightPaddle)){
			ball.xvel*=-1;
		}
		ArrayList<Double> LeftInputs = new ArrayList<>();		
		LeftInputs.add((ball.ball.getTranslateX()-Left.getTranslateX())/sizex-(leftPaddle.getTranslateX()-Left.getTranslateX())/sizex);//ball pos x
		LeftInputs.add((ball.ball.getTranslateY()-Left.getTranslateY())/sizey-(leftPaddle.getTranslateY()-Left.getTranslateY())/sizey);// ball pos y
//		LeftInputs.add((leftPaddle.getTranslateX()-Left.getTranslateX())/sizex);//paddle pos x
//		LeftInputs.add((leftPaddle.getTranslateY()-Left.getTranslateY())/sizey);// paddle pos y
		LeftInputs.add(ball.xvel/maxXVel);
		LeftInputs.add(ball.yvel/maxYVel);
		double leftSpeed = leftPlayer.run(LeftInputs);
		leftPaddle.setTranslateY(leftPaddle.getTranslateY()+leftSpeed);
		ArrayList<Double> RightInputs = new ArrayList<>();		
		RightInputs.add((ball.ball.getTranslateX()-Left.getTranslateX())/sizex-(rightPaddle.getTranslateX()-Left.getTranslateX())/sizex);//ball pos x
		RightInputs.add((ball.ball.getTranslateY()-Left.getTranslateY())/sizey-(rightPaddle.getTranslateY()-Left.getTranslateY())/sizey);// ball pos y
//		RightInputs.add((rightPaddle.getTranslateX()-Left.getTranslateX())/sizex);//paddle pos x
//		RightInputs.add((rightPaddle.getTranslateY()-Left.getTranslateY())/sizey);// paddle pos y
		RightInputs.add(ball.xvel/maxXVel);
		RightInputs.add(ball.yvel/maxYVel);
		double rightSpeed = rightPlayer.run(RightInputs);
		rightPaddle.setTranslateY(rightPaddle.getTranslateY()+rightSpeed);
//		System.out.println("Inputs");
//		for(int i = 0;i < LeftInputs.size(); i++){
//			System.out.println(LeftInputs.get(i));
//		}
//		System.out.println("Inputs done");
		
//		System.out.println(leftSpeed +"  " + rightSpeed);
		leftPaddle.setTranslateY(Math.min(leftPaddle.getTranslateY(), Bottom.getTranslateY()-leftPaddle.getHeight()-Bottom.getHeight()));
		rightPaddle.setTranslateY(Math.min(rightPaddle.getTranslateY(), Bottom.getTranslateY()-rightPaddle.getHeight()-Bottom.getHeight()));
		leftPaddle.setTranslateY(Math.max(leftPaddle.getTranslateY(), Top.getTranslateY()+Top.getHeight()));
		rightPaddle.setTranslateY(Math.max(rightPaddle.getTranslateY(), Top.getTranslateY()+Top.getHeight()));
	}

	public static boolean collides(Rectangle a, Rectangle b) {
		double ax1 = a.getTranslateX();
		double ax2 = a.getTranslateX() + a.getWidth();
		double ay1 = a.getTranslateY();
		double ay2 = a.getTranslateY() + a.getHeight();
		double bx1 = b.getTranslateX();
		double bx2 = b.getTranslateX() + b.getWidth();
		double by1 = b.getTranslateY();
		double by2 = b.getTranslateY() + b.getHeight();

		return (containsPoint(b, ax1, ay1) || containsPoint(b, ax1, ay2) || containsPoint(b, ax2, ay1)
				|| containsPoint(b, ax2, ay2) || containsPoint(a, bx1, by1) || containsPoint(a, bx1, by2)
				|| containsPoint(a, bx2, by1) || containsPoint(a, bx2, by2));
	}

	public static boolean containsPoint(Rectangle a, double x, double y) {
		double ax1 = a.getTranslateX();
		double ax2 = a.getTranslateX() + a.getWidth();
		double ay1 = a.getTranslateY();
		double ay2 = a.getTranslateY() + a.getHeight();
		return (x >= ax1 && x <= ax2 && y >= ay1 && y <= ay2);
	}

}
