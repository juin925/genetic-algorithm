import java.util.ArrayList;
import java.util.List;
public class GA {
	static int generation =1;
	public static void main(String args[]) {
		List generationList = new ArrayList();
		int[] dielectrics = {1,5,6,8,3,7,3,5,9,0};
		Gene[] gene = initGene(dielectrics);		
		nextGeneration(gene,5);		
	}
	static Gene[] initGene(int[] dielectrics){
		Gene[] gene = new Gene[5];
		for(int i = 0 ; i < 5;i++){
			int[] dielectric = new int[3];
			for(int j = 0 ; j <3;j++){
				dielectric[j] = dielectrics[(int)Math.floor((Math.random() *10))];
			}
			gene[i] = new Gene(dielectric);
		}
		printGene(gene);
		return gene;
	}
	static void BubbleSort(Gene[] gene){
		for(int i = gene.length -1 ; i >0 ; i--){
			for(int j = 0 ; j > gene[j+1].getFitness(); j++){
					Gene tmp = gene[j];
					gene[j] = gene[j+1];
					gene[j+1] = tmp;
			}
		}
	}

	static Gene[] nextGeneration(Gene[] gene,int loop) {
		Gene[] newGene = new Gene[5];
		BubbleSort(gene);
		try{
			for(int i= 0 ; i < 5; i++)
				newGene[i] = (Gene) gene[i%2].clone();
		}catch(CloneNotSupportedException cnse){
			System.out.println("객체 복사 에러" +cnse.getMessage());
		}
		newGene[0].changeGene(0, gene[1].getGene()[0]);
		newGene[1].changeGene(0, gene[0].getGene()[0]);
		newGene[2].changeGene(1, gene[1].getGene()[1]);
		newGene[3].changeGene(1, gene[0].getGene()[1]);
		newGene[4].changeGene(2, gene[1].getGene()[2]);		
		generation++;
		printGene(newGene);
		loop--;
		if(loop !=0){
			nextGeneration(newGene, loop);
		}
		return newGene;
	}
	static void printGene(Gene[] gene){
		System.out.println("-----------------"+generation+"세대------------------");
		for(int i=0 ; i < 5 ;i++){
			System.out.print((i+1)+"번 유전자");
			for(int j=0;j<3;j++){
				System.out.print(":"+gene[i].getGene()[j]);
			}
			System.out.println("  적합도 :"+gene[i].getFitness());
		}
	}
}