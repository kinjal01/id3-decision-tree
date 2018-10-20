/**
 * 
 */
package ID3Algo;

import java.util.ArrayList;
import java.util.HashMap;
/*
 *		   Data structure for Tree. Each node
 *         of a tree has following attribute int attributeValue ->This is the
 *         attribute value String attributeName ->This is the attribute Name
 *         ArrayList<TreeNode> branches -> This is the arraylist of other
 *         branches int fClass ->This is the final class(0/1).
 */
public class ID3TreeNode {

	int attributeValue;// attribute value
					
	String attributeName;// This is the attribute Name
							
	ArrayList<ID3TreeNode> branches;// All other Child branches
	
	int fClass; // 0 -> this leaf belongs to Class 0
				// 1 -> this leaf belongs to Class 1
				// -1 -> this is not a leaf node

	double informationGain;// Information Gain for each attribute

	//Constructor for TreeNode
	public ID3TreeNode(int attributeValue, String attributeName, int fClass, double informationGain) {
		super();
		this.attributeValue = attributeValue;
		this.attributeName = attributeName;
		this.fClass = fClass;
		this.informationGain = informationGain;
	}

	// Default Constructor
	public ID3TreeNode() {
		branches = new ArrayList<ID3TreeNode>();
	}

	// Getter & Setter methods
	public int getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(int attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public ArrayList<ID3TreeNode> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<ID3TreeNode> branches) {
		this.branches = branches;
	}

	public int getfClass() {
		return fClass;
	}

	public void setfClass(int fClass) {
		this.fClass = fClass;
	}

	public double getGain() {
		return informationGain;
	}

	public void setGain(double informationGain) {
		this.informationGain = informationGain;
	}
	/*Function ::  printTree, Recursive Function
	 * Parameters :: TreeNode Reference = The root of the tree we want to print
	 * Parameters :: int treeLevel = The treeLevel, as this function is always called from root, so it should be 0.
	 * Output:: Prints the build Decision Tree in following form
	 * attr1 = 0 : 
	 * | attr2 = 0 : 
	 * | | attr3 = 0 : 1 
	 * | | attr3 = 1 : 0 
	 * attr1 = 1 : 
	 * | attr2 = 1 : 1 
	 */
	public void printTree(ID3TreeNode t, int treeLevel) {
		if (t.fClass == -1) {// not a leaf node
			for (ID3TreeNode temp : t.branches) {// For all branches of this node
				int i = 0;
				while (i < treeLevel) {// According to treeLevel value we print |
					i++;
					System.out.print("| ");
				}
				System.out.print(t.attributeName);
				System.out.print("=");
				if (temp.fClass == -1)
					System.out.print(temp.attributeValue + " :\n");
				else
					System.out.print(temp.attributeValue + " : ");
				printTree(temp, treeLevel + 1);
				// recursively call same function for child node, with treeLevel+1
			}

		} else {// it is a leaf node , hence terminate it
			System.out.println(t.fClass);
		}

	}

	/*
	 * Function :: ClassifyTest, Iterative Function Parameters ::
	 * HashMap<Key,Value> testValues Key-> attribute Name, Value -> The value of
	 * that attribute e.g some values in this maps are
	 * <attr1,0><attr2,1><attr3,1> Thus for the given test case, values of
	 * attributes are attr1=0, attr2=1, attr3=1 Parameters :: TreeNode
	 * treeBegin, which is the root node of the tree Output:: Final class for
	 * this given Test: 0, 1 or -1 : if the tree was not able to predict the
	 * output
	 */
	public int ClassifyTest(HashMap<String, Integer> testValues,ID3TreeNode treeBegin) {
		int returnValue = -1;
		ID3TreeNode treeTraverse = treeBegin;
		if (treeTraverse.attributeName == null|| treeTraverse.attributeName.length() == 0) {
			return returnValue;// if the root node is null then return -1;
		}
		while (treeTraverse != null) {
			if (treeTraverse.fClass != -1) {// if current node is leaf node
				returnValue = treeTraverse.fClass;// then return its class
				treeTraverse = null;
				break;
			} 
			else {// if current node is not leaf node, then based upon the
					// values of test case provided, choose the node to go down.
				String atributeKey = treeTraverse.attributeName;

				if (testValues.containsKey(atributeKey)) {
					int atributeValue = testValues.get(atributeKey);// will
																	// traverse
																	// to this
																	// node now
					if (treeTraverse.branches.size() == 0) {
						break;
					}
					boolean valueFound = false;
					for (ID3TreeNode tempChild : treeTraverse.branches) {
						if (tempChild.attributeValue == atributeValue) {
							// this is the correct direction of movement
							valueFound = true;
							if (tempChild.fClass != -1) {// if child node is
															// leaf node
								returnValue = tempChild.fClass;// then return
																// its class
								treeTraverse = null;
								break;

							} else {
								treeTraverse = tempChild;// if current node is
															// not leaf
															// node,then
															// traverse this
															// childNode
								continue;
							}
						}
					}
					if (!valueFound)// if the child nodes of the traversing node
									// didn't matched any attribute inside map
						break;

				} else {
					System.out.println("Attribute name not found in test case");
					break;
				}

			}
		}

		return returnValue;
	}
}
