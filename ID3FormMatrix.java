package ID3Algo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ID3FormMatrix {
	int columns;//number of columns in matrix
	ArrayList<String> columnType;//Values in headers of matrix
	ArrayList<int[]> rows;//rows in matrix
	int numberOfRows;//number of rows

	//Default constructor
	public ID3FormMatrix() {
		rows = new ArrayList<int[]>();
		columnType = new ArrayList<String>();
	}
	
	public ArrayList<String> getColumnType() {
		return columnType;
	}

	public void setColumnType(ArrayList<String> columntype) {
		columnType = columntype;
	}

	public ArrayList<int[]> getRows() {
		return rows;
	}

	public void setRows(ArrayList<int[]> rows) {
		this.rows = rows;
	}

	//This function prints the matrix read from the file
	
	public void printMatrixUsed() {
		// print heading
		for (String temp : columnType) {
			System.out.print("\t" + temp);
		}
		for (int[] temp : rows) {
			System.out.println("");
			for (int i = 0; i < columns; i++) {
				System.out.print("\t" + temp[i]);
			}
		}
		System.out.println("");
	}

	//Fills the array with values for which index is matched to the index given
	public void fillArray(int[] arrayToFill, int indexToFetch) {
		int arrIndex = 0;
		for (int[] temp : rows) {
			arrayToFill[arrIndex++] = temp[indexToFetch];
		}
	}

	//Prepares the matrix
	public void createMatrix(String FileNameToRead,
			int PercentageOfDataToLEarnFrom) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(FileNameToRead));
			int NumberOfColoumns = 0;
			// Reading Header
			{
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);

				boolean notNum = true;
				while (st.hasMoreElements()) {
					if (notNum) {
						columnType.add((String) st.nextElement());
						notNum = false;
					} else {
						st.nextElement();
						notNum = true;
					}

				}

			}

			columnType.add("Class");
			NumberOfColoumns = columnType.size();
			columns = NumberOfColoumns;
			{//read columns
				String line = br.readLine();
				while (line != null) {
					StringTokenizer st = new StringTokenizer(line);
					int[] tempCol = new int[NumberOfColoumns];
					int tempIndex = 0;
					while (st.hasMoreElements()) {
						tempCol[tempIndex++] = Integer.parseInt((String) st.nextElement());
					}
					rows.add(tempCol);
					line = br.readLine();
				}
			}

			// Truncate
			int rowsAfterTrunc = (int) ((PercentageOfDataToLEarnFrom * (rows.size())) / 100);
			if (rowsAfterTrunc != rows.size()) {
				for (int i = rows.size() - 1; i > rowsAfterTrunc; i--)
				{
					rows.remove(i);
				}
			}

			numberOfRows = rows.size();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	/*
	 * Function :: divideMatrix, 
	 * This function splits the matrix
	 * based on the given attribute name, and its value.,
	 * rest other rows are removed from the row.
	 */
	public ID3FormMatrix divideMatrix(String attributeName, int value) {
		
		ID3FormMatrix matrixReturn = new ID3FormMatrix();
		ArrayList<int[]> rowsMatrixReturn = new ArrayList<int[]>();
		ArrayList<String> HeadersMatrixReturn = new ArrayList<String>();
		int attributeIndex = 0;

		// First set into headers all headers except the Selected Attribute
		for (String tempHeadValue : columnType) {
			if (!tempHeadValue.equals(attributeName))
				HeadersMatrixReturn.add(tempHeadValue);
		}
		matrixReturn.columns = HeadersMatrixReturn.size();
		matrixReturn.setColumnType(HeadersMatrixReturn);

		for (attributeIndex = 0; attributeIndex < columnType.size(); attributeIndex++) {
			if (attributeName.equals(columnType.get(attributeIndex))) {
				break;
			}
		}
		
		for (int[] temp : rows) {
			if (temp[attributeIndex] == value) {
				int[] tempRow = new int[HeadersMatrixReturn.size()];
				int indexTempRow = 0;
				for (int i = 0; i < columns; i++) {
					if (i == attributeIndex) {

					} else {
						tempRow[indexTempRow] = temp[i];
						indexTempRow++;
					}
				}

				rowsMatrixReturn.add(tempRow);
			}

		}
		matrixReturn.setRows(rowsMatrixReturn);
		matrixReturn.numberOfRows = rowsMatrixReturn.size();
		return matrixReturn;
	}

	/*
	 * Function :: getAccuracy, 
	 * This fucntion forms the Hashmap of attributes and its values for each Row in matrix,
	 * and then calls the ClassifyTest, and verifies the results returned with the result expected
	 * to calculate the accuracy.
	 */
	public double getAccuracy(ID3TreeNode treeBegin, String typeOfData, int j) {
		int iteration=j;
		double accuracy = 0.0;
		int countPositives = 0;
		for (int[] temp : rows) {
			HashMap<String, Integer> testValues = new HashMap<String, Integer>();
			int finalValue = -1;
			for (int i = 0; i < columns; i++) {
				if (i == (columns - 1)) {
					finalValue = temp[i];
				} else {
					testValues.put((String) columnType.get(i), temp[i]);
				}

			}
			if (finalValue == treeBegin.ClassifyTest(testValues, treeBegin)) {
				countPositives++;
			}
		}
		accuracy = ((double) countPositives / (rows.size())) * 100;

		System.out.print("Accuracy on "+iteration+"th iteration of "+ typeOfData + " (" + rows.size()
				+ " instances )");
		System.out.print(" = " + accuracy + " %\n");
		return accuracy;
	}
}
