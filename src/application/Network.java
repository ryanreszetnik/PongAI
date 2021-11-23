package application;

import java.util.ArrayList;

public class Network {
	public  ArrayList<ArrayList<Node>> net = new ArrayList<>();
	
	
	public  void setUpRandom(int size[]){
		for(int i =0; i <size.length; i++){
			net.add(new ArrayList<Node>());
			for(int p = 0; p < size[i]; p++){
				net.get(i).add(new Node(Math.random()*-4));
				if(i>0){
					for(int q = 0; q<net.get(i-1).size();q++){
//						System.out.println("i: "+ i + " p: "+ p + " q: "+q +"  " + net.get(i-1).get(q));
						net.get(i).get(p).addInput(net.get(i-1).get(q), Math.random());
					}
				}
			}
		}
	}
	
	public static Network merge(Network a, Network b){
		Network output= new Network();
		for(int i =0; i <a.net.size(); i++){
			output.net.add(new ArrayList<Node>());
			for(int p = 0; p < a.net.get(i).size(); p++){
				double v =0;
				if(Math.random()>0.5){
					v = a.net.get(i).get(p).biasweight;
				}else{
					v = b.net.get(i).get(p).biasweight;
				}
				output.net.get(i).add(new Node(v));
				if(i>0){
					for(int q = 0; q<output.net.get(i-1).size();q++){
						double val = 0;
						if(Math.random()>0.5){
							val = a.net.get(i).get(p).weights.get(q);
						}else{
							val = b.net.get(i).get(p).weights.get(q);
						}
						output.net.get(i).get(p).addInput(output.net.get(i-1).get(q), val);
					}
				}
			}
		}
		return output;
	}

	public double run(ArrayList<Double> inputs){
		for(int i = 0; i < net.get(0).size(); i++){
			net.get(0).get(i).setOutput(inputs.get(i));
		}
		for(int i = 1; i < net.size();i++){
			for(int p = 0; p < net.get(i).size();p++){
//System.out.println(net.size() +"  " + net.get(i).size());
				net.get(i).get(p).run();
			}
		}
		double output = net.get(net.size()-1).get(0).output;
		return output*2-1;
	}
	
}
