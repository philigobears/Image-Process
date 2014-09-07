
/* DListNode2.java */

/**
 *  A DListNode2 is a node in a DList2 (doubly-linked list).
 */

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   * 
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public int[] color;
  public DListNode prev;
  public DListNode next;
  public int runLengths;

  /**
   *  DListNode2() constructor.
   */
 public DListNode() {
	color=new int[3];
	 runLengths=0;
    prev = null;
    next = null;
  }


public DListNode( int runLengths,int red, int green,int blue){
	color=new int[3];
	 this.color[0]=red;
	 this.color[1]=green;
	 this.color[2]=blue;
	 this.runLengths=runLengths;
   prev = null;
   next = null;
}
}




