public class Gene implements Cloneable{
	private int[] gene = new int[3];
	private int fitness = 0;
	public Gene(int[] gene){
		for(int i =0 ; i < gene.length ; i++ )
			this.gene[i] = gene[i];
		setFitness();
	}
	
	
	public int[] getGene() {
		return gene;
	}
	public int getFitness() {
		return fitness;
	}
	public void setFitness(){
		int sum =0;
		for(int i=0 ;i < 3; i++){
			sum += gene[i];
		}
		this.fitness=Math.abs(20-sum);
	}
	public void changeGene(int index,int value){
		this.gene[index] = value;
		this.setFitness();
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Gene gene =  (Gene) super.clone();
		gene.gene = (int[])this.gene.clone();
		return gene;
	}
	
}