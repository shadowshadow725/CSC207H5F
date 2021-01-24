package ca.utoronto.utm.othello.model;
import java.util.Random;

public class RandomVSRandom {

	public static void main(String[] args) {
		// Below are the probabilities I got from OthelloControllerRandomVSRandom.java
		// Note: Since we are not going to simulate ties, we should re-normalize
		// the probabilities...
		double p1wins=0.4496, p2wins=0.5056; 
		double totalProbability = p1wins+p2wins;
		p1wins = p1wins/totalProbability;
		p2wins = p2wins/totalProbability;

		// H0: The probability of P1 or P2 winning using the Random Strategy is equal, so .5 and .5
		// Ha: When both P1 and P2 are playing the RandomStrategy, P2 has a real advantage
		// and so is more likely to win.

		// We are interested in how likely it is that H0 is true and yet we arrive at 
		// experimental probabilities that are as extreme or more than the ones we measured.
		double distanceFromHalf = Math.abs(.5-p1wins);
		System.out.println("From 10000 plays of random vs tandom P(p1wins)="+p1wins);
		System.out.println("From 10000 plays of random vs random P(p2wins)="+p2wins);

		System.out.println(distanceFromHalf);
		// How likely it is to get, in 10,000 flips of a fair coin,
		//  a difference like the one we are seeing here.
		
		int numRuns = 1000000;
		int numMoreThanExtreme = 0;
		double maxDistanceFromHalf = 0.0;
		for(int i=0;i<numRuns;i++) {
			FlipProbabilities f = experiment(10000);
			double eDistanceFromHalf = Math.abs(f.pHeads-.5);
			if(eDistanceFromHalf>maxDistanceFromHalf) {
				maxDistanceFromHalf=eDistanceFromHalf;
			}
			if(eDistanceFromHalf>=distanceFromHalf) {
				System.out.println("Got one!!");
				numMoreThanExtreme++;
			}
			// System.out.println(eDistanceFromHalf);
		}
		System.out.println("Probability of H0<"+(double)numMoreThanExtreme/numRuns);
		System.out.println("Largest distance from half is="+maxDistanceFromHalf);

	}
		

	/**
	 * Run an experiment, flipping a coin num times. 
	 * Record the experimental probability of heads and of tails.		
	 * @param num
	 */
	public static FlipProbabilities experiment(int num) {
		Random r = new Random();
		int numTrue = 0, numFalse=0;
		for(int i=0;i<num;i++) {
			if(r.nextBoolean())numTrue++;
			else numFalse++;
		}
		return new FlipProbabilities((float)numTrue/num, (float)numFalse/num);
	}
	
	private static class FlipProbabilities {
		double pHeads, pTails;
		FlipProbabilities(double pHeads, double pTails){
			this.pHeads=pHeads; this.pTails=pTails;
		}
		public String toString() {
			return "("+this.pHeads+","+this.pTails+")";
		}
	}
}
