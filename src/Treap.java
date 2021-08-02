//@author Jessica Kamman


import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
		// create inner class Node
	
		private static class Node<E>{
		//key for the search 
		public E data; 
		//random heap priority
		public int priority; 
		
		public Node<E > left;
		public Node<E > right;
		
		

		//Constructors for Node<E> class
		
		public Node(E data, int priority){
			if(data==null)
				throw new IllegalArgumentException("Pointer of Data is null");
			else {
				this.left = null;
				this.right =null;
				this.data = data;
				this.priority = priority;
		}
			}
		
		
		//Methods
		//node method rotate Right
		
		public Node<E > rotateRight(){
			Node<E> rootNode = new Node<E>(data, priority);
			if(this.left!=null){
				rootNode.left = this.left.right;
				rootNode.right = this.right;
				this.priority = this.left.priority;
				this.data = this.left.data;
				this.left = this.left.left;
				this.right = rootNode;
		}
		return this;
			}
		
		
		//rotate Left- will rotate Left
		public Node<E > rotateLeft(){
			Node<E> rootNode = new Node<E>(data, priority);
			if(this.right!=null){
				
				rootNode.left = this.left;
				rootNode.right = this.right.left;
				this.priority = this.right.priority;
				this.data = this.right.data;
				this.right = this.right.right;
				this.left = rootNode;
			}
			return this;
		}
			}
		
		//String to String Method
		//public String toString() {
			//return this.data.toString();
			
		
		//Data Fields given
		
		private Node<E> root;
		private Random priorityGenerator;
		
		//create empty treap
		
		public Treap(){
			priorityGenerator = new Random();
			root = null;
			}
		
		//Treap constructor that intitializes priority Generator
		public Treap(long seed){
			priorityGenerator = new Random(seed);
			root = null;
			}
		
		
		//add Boolean to create priority and insert into the tree
		boolean add(E key){
			return add(key, priorityGenerator.nextInt());
			}
		
		//boolean that takes key and priority
		boolean add(E key, int priority){
			
			Node<E > newNode = new Node<E>(key, priority);
			Node<E > tempRoot = root;
			Stack<Node> stack = new Stack<Node>();
			if(root==null)
		{
				root = new Node(key, priority);
				return true;
		}
			if(find(key)){
				return false;
		}
			
			while(tempRoot!=null){
				stack.push(tempRoot);
				if((key.compareTo(tempRoot.data)) < 0)
					tempRoot = tempRoot.left;
				else
					tempRoot = tempRoot.right;
		}
			if((key.compareTo((E)stack.peek().data)) < 0)
				stack.peek().left = newNode;
			else{
				stack.peek().right = newNode;
		}
			
			stack.push(newNode);
			reheap(stack);
			return true;
	
		
		}
		//Reheap function which will arange tree per max heap
		private void reheap(Stack<Node> stack){
		
			Node<E> child = stack.pop();
			Node<E> parent = stack.pop();
			while(parent!= null && child.priority > parent.priority){
				if((child.data.compareTo(parent.data)) > 0)
		
					parent.rotateLeft();
				
				else
					parent.rotateRight();
				
				if(!stack.isEmpty())
					parent = stack.pop();
				
				else
					parent = null;
		}
		}
		 
		//code needed to delete the node with a specified key from the treap if returned true
		//otherwise return false
		public boolean delete(E key){
			Node<E> nodeFound = null ;
			
			Node<E> parentnode = null;
			
			Node<E> localroot = null;
			
			if (find(key) == false || root==null) {
				return false;
			}
			
			else {
			}
		
				while(root!= null){
		
					if(key.compareTo(root.data) < 0){
						localroot = root;
						root = root.left;
		}
					else if((key.compareTo(root.data)) > 0){
						localroot = root;
						root = root.right;
		}
					else{
						nodeFound = root;
						break;
					}
					
				}
				while((nodeFound.right!=null)||(nodeFound.left!=null)) {
					
					if(nodeFound.right==null ){
						
						parentnode = nodeFound.rotateRight();
						nodeFound = parentnode.right;
		}
					
					else if(nodeFound.left == null){
						
						parentnode = nodeFound.rotateLeft();
						nodeFound = parentnode.left;
		}
					else if (nodeFound.left.priority <nodeFound.right.priority){
						
						parentnode = nodeFound.rotateLeft();
						nodeFound = parentnode.left;
		}
					else if(nodeFound.left.priority > nodeFound.right.priority){
						parentnode = nodeFound.rotateRight();
						nodeFound = parentnode.right;
		}
		}
				
			if(parentnode == null)
				parentnode = root;
			if((parentnode.left!=null)&&(parentnode.left.data.compareTo(key))==0)parentnode.left = null;
			else{
				parentnode.right = null;
		}
		return true;
			}
		//find boolean that finds a Treap
		private boolean find(Node<E> root,E key){
			if(root==null)
				return false;
			else if((key.compareTo(root.data))>0)
				return find(root.right, key);
			
			else if((key.compareTo(root.data))<0)
				return find(root.left, key);
		
			else
				return true;
		}
		
		//find boolean boolean return for find
		
		public boolean find(E key){
			if(key==null) {
				throw new NullPointerException("Key is null");
		}
			return find(root, key);
			}
		
		//Public String Method returns string value of tree
		public String toString(){
			StringBuilder strBuilder = new StringBuilder();
			return getPreOrderTraverse(root, 1, strBuilder);
			}
		
		
		//Get the string preorder
		
		private String getPreOrderTraverse(Node<E>node, int depth, StringBuilder strBuilder){
		
			for(int i=1; i< depth; i++){
				strBuilder.append(" ");
		}
			if(node==null)
				strBuilder.append("null\n");
			else{
		strBuilder.append("(key="+node.data+",priority="+node.priority+")\n\n");
			getPreOrderTraverse(node.left, depth + 1, strBuilder);
			getPreOrderTraverse(node.right, depth + 1,strBuilder);
		}
			return strBuilder.toString();
			}
		 
		public static void main(String[] args){
		
		System.out.println("Test Case");
		Treap<Integer> testTree = new Treap < Integer >();
		
		//TEST Cases given:
		testTree.add(4,19);
		testTree.add(2,31);
		testTree.add(6,70);
		testTree.add(1,84);
		testTree.add(3,12);
		testTree.add(5,83);
		testTree.add(7,26);
		
	
		System.out.println("Was Node 3 found ? "+ testTree.find(3));
		System.out.println("Was Node 6 deleted: "+ testTree.delete(6));
		System.out.println("Delete when key '3' exists, boolean result is: "+ testTree.delete(3));
		System.out.println("Was node 6 found after deletion? "+ testTree.find(3));
		
		
		System.out.println(testTree.toString());
		}
			}