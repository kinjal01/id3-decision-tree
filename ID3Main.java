package ID3Algo;

import java.util.Scanner;

public class ID3Main {
	public static void main(String[] args) {
		
		//value of total instances
		int n;
		Scanner in1 = new Scanner(System.in);
		System.out.println("Enter total number of instances:");
	    n = in1.nextInt();
	    
		//value of k for k-fold cross validation
		int k;
		Scanner in2 = new Scanner(System.in);
		System.out.println("Enter an integer k to do k-fold cross validation:(Preferred 6 or 9)");
	    k = in2.nextInt();

	    String fileNameLearning = "learn.txt"; //dynamically created learn.txt
		String fileNameTesting = "test.txt";   //dynamically created test.txt
		String fileNameMonks = "monks.txt";	   //initial dataset
		
		int percent = 100;					//how many lines to be read from input in percentage
		
		double averageAccuracytest[]= new double[k];	//array to store test accuracy's over k iterations
		double averageAccuracytrain[]= new double[k];	//array to store train accuracy's over k iterations 
	    
	    for(int i=0;i<k;i++)
	    {		
	    
		ID3FileHandling fileI = new ID3FileHandling(fileNameLearning, fileNameTesting, fileNameMonks, k, i);
		if(i==0)
	    { 
			fileI.fileShuffle(fileNameMonks);
			fileI.fileTrim(fileNameMonks);
			fileI.fileShift(fileNameMonks);
	    }
	    
	    fileI.fileCopy(fileNameLearning, fileNameMonks);
	    fileI.fileCopy(fileNameTesting, fileNameMonks);
	    fileI.deleteinner(fileNameLearning, ((n/k)*i)+1, n/k);
	    fileI.deleteouter(fileNameTesting, ((n/k)*i)+1, n/k);
	    

		// ID3Maker
		// parameter 1: Learning File Name
		// parameter 2: % of Training file use to learn from
	      
		ID3Maker learn = new ID3Maker(fileNameLearning, percent);

		ID3TreeNode rootNode = learn.startMaking();
		// This function starts learning and creates decision tree, which is returned
		
		if (rootNode != null) {
			rootNode.printTree(rootNode, 0);// print to stdout the tree

			/*{// Calculating the accuracy on Training Set
				ID3FormMatrix matrixTests = new ID3FormMatrix();
				matrixTests.createMatrix(fileNameLearning, 100);
				double accuracytrain = matrixTests.getAccuracy(rootNode, "Training Set", i+1);
				averageAccuracytrain[i]=accuracytrain;

			}*/
			{// Calculating the accuracy on Test Set
				ID3FormMatrix matrixTests2 = new ID3FormMatrix();
				matrixTests2.createMatrix(fileNameTesting, 100);
				double accuracytest = matrixTests2.getAccuracy(rootNode, "Test Set", i+1);
				averageAccuracytest[i]=accuracytest;
			}

		} else {
			System.out.println("The algorithm was not able to create a decision Tree.");

		}
	    }
	    double sumtrain=0.0;
    	double sumtest=0.0;
    	
	    for(int j=0;j<k;j++)
	    {
	    	sumtrain=sumtrain+averageAccuracytrain[j];
	    	sumtest=sumtest+averageAccuracytest[j];
	    }
	    //System.out.println("\n\nThe average accuracy of the training data is:"+ sumtrain/k+" % ");
	    System.out.println("The average accuracy of the testing data is:"+ sumtest/k+" % ");
	    System.out.println("\nID3 Algorithm implemented! :)");
	    
	    
	}
}
