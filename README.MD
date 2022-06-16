## 유전 알고리즘(GNENTIC ALGORITHM)
**유전 알고리즘은 풀고자 하는 문제에 대한 가능한 해들을 정해진 형태의 자료구조로 표현한 다음, 이들을 점차적으로 변형함으로써 점점 더 좋은 해들을 만들어낸다.**    

*유전 알고리즘 구성*  
1)요구조건:   
* 유전 알고리즘을 어떤 문제에 적용하기 위해서는 해를 유전자형식으로 표현이 가능해야한다.

2)흐름:  
* 초기집단은 랜덤을 이용하요 구성할 수 있다.  
* 적합도가 높은 해를 이용하여 다음세대로 적합도가 높은 유전자 특성을 물려받게 하여 최적해에 가까워지도록 한다.
* 교배 이외에도 변이를 통하여 최적해에 가까워 질 수 있다.

*유전 알고리즘 연산*  
1)선택:  
* 한 세대에서 다음 세대로 전해지는 해의 후보가 되는 해들을 선택한다. 선택 방법에는 균등 비례, 룰렛 휠 선택, 토너먼트 선택, 순위 기반 선택 등이 있다.  

2)교차:  
* 실제 생명체의 염색체가 재조합되는 과정에서 부모 염색체의 일부분이 특정 위치를 기준으로 서로 바뀌어 결합되는 경우가 있는데, 이 현상을 교차라고 한다.

3)변이:  
* 일반 생명체에서, 유전자의 교배 뿐 아니라, 하나의 유전자가 직접적으로 변이를 일으켜서 주어진 환경에서 살아남을 확률 역시 존재한다.

4)대치:  
* 교차 변이 등을 거쳐서 만들어진 새로운 해를 해집단에 추가하고, 기존 해 중 열등한 해를 가려내서 제외시키는 연산이다.


>>**내가 이번에 구현해 볼 식**  

Y = X + Z + K 에 X, Z, K 에 랜덤 변수를 넣어 Y가 20이 나올 수 있게 구현해 볼 것이다.


**유전 알고리즘 코드**  
GA.JAVA
```
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
```  
Gene.java
```
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
```  
1. {1,5,6,8,3,7,3,5,9,0} 중에 3개의 랜덤위치에서 숫자를 선택해 3개의 유전체를 가진 5개의 유전자를 후보에 두었다.
2. 세대변화 할때에는 최적의 해를 가진 2개의 유전자를 5개로 복사한다.
3. 복사한 1,2번 유전자는 1번째 유전체를 서로 교환하고 복사한 3,4번 유전자는 2번째 유전체를 서로 교환하고 마지막 5번 유전자는 3번쨰 유전체를 원본 2번 유전자의 유전체와 교환하였다.

>>결과

![](https://user-images.githubusercontent.com/101376856/174060305-1f51186e-ae5a-4ac3-8ca1-0169aedb55f6.PNG)  
위처럼 3세대만에 합이 3+9+8 20으로 동일하게 나오는 것을 볼 수 있다.  
적합도는 20과 거리를 뜻한다.

>>그래프로 표현  

![](https://user-images.githubusercontent.com/101376856/174060327-f07991ec-be79-4b18-b995-67bdb2441bfc.PNG)

과 같이 표현되는데 3세대부터는 20과 동일한 선을 그리는 것을 볼 수 있다.

>>나의 한계점 그리고 느낀점

교수님이 요구하신 것중에 모수 값 추정, 최적화되는 과정에서 에러가 감소하는 경향 표현이 있는데 제 실력으론 아직 유전 알고리즘이 너무 어려워 그거까지 구현을 할 수 없었습니다. 그래서 교수님이 요구하신 유전 알고리즘이 아닌 그나마 쉬운 유전 알고리즘을 구현했습니다. 컴퓨터 알고리즘이란 과목을 들으면서 제 공부량이 아직은 많이 부족하다는 것을 명확히 느낄 수 있었고 다른 학우들이 코딩을 하는 모습에 저에 대한 자책을 조금 많이 한 것 같습니다. 방학 때 조금 더 발전한 모습으로 변하겠습니다. 2022년 1학기 수고 많으셨습니다.
