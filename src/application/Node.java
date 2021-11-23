package application;

import java.util.ArrayList;

public class Node {
	ArrayList<Node> inputs = new ArrayList<>();
	ArrayList<Double> weights = new ArrayList<>();
	public double output;
	public double bias =1;
	public double biasweight;
	
	public Node(double biasweight){
		this.biasweight=biasweight;
	}
	public void run(){
		double sum =0;
		for(int i = 0;i < inputs.size(); i++){
			sum+=inputs.get(i).output*weights.get(i);
		}
		sum+=bias*biasweight;
		output = 1.0/(1.0+Math.pow(Math.E,-sum));
	}
	
	public void addInput(Node n, double w){
		inputs.add(n);
		weights.add(w);
	}
	
	public void setOutput(double val){
		output = val;
	}

	
}
