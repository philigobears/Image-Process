/* DList2.java */

/**
 *  A DList2 is a mutable doubly-linked list.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 */

public class DList {

	/**
	 *  head references the sentinel node.
	 *
	 *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
	 */

	protected DListNode head;
	protected int size;

	/* DList2 invariants:
	 *  1)  head != null.
	 *  2)  For any DListNode2 x in a DList2, x.next != null.
	 *  3)  For any DListNode2 x in a DList2, x.prev != null.
	 *  4)  For any DListNode2 x in a DList2, if x.next == y, then y.prev == x.
	 *  5)  For any DListNode2 x in a DList2, if x.prev == y, then y.next == x.
	 *  6)  size is the number of DListNode2s, NOT COUNTING the sentinel
	 *      (denoted by "head"), that can be accessed from the sentinel by
	 *      a sequence of "next" references.
	 */

	/**
	 *  DList2() constructor for an empty DList2.
	 */
	public DList() {

		head = new DListNode();
		head.next = head;
		head.prev = head;
		size = 0;
	}

	public int length() {
		return size;
	}


	/**
	 *  insertFront() inserts an item at the front of a DList2.
	 */
	public void insertFront( int runLengths,int red, int green,int blue) {
		if(head.next!=head){
			head.next.prev=new DListNode( runLengths,red, green,blue);
			head.next.prev.next=head.next;
			head.next=head.next.prev;
			head.next.prev=head;
		}else{
			head.next=new DListNode( runLengths,red, green,blue);
			head.prev=head.next;
			head.next.next=head;
			head.prev.prev=head;
		}
		size++;
	}

	public void insertBack( int runLengths,int red, int green,int blue) {
		if(head.next!=head){
			head.prev.next=new DListNode( runLengths,red, green,blue);
			head.prev.next.prev=head.prev;
			head.prev=head.prev.next;
			head.prev.next=head;
		}else{
			head.next=new DListNode( runLengths,red, green,blue);
			head.prev=head.next;
			head.next.next=head;
			head.prev.prev=head;
		}
		size++;
	}


	public void insert(int runLengths, int red, int green, int blue,DListNode node){
		node.next.prev=new DListNode(runLengths,red,green,blue);
		node.next.prev.next=node.next;
		node.next=node.next.prev;
		node.next.prev=node;
		size++;
	}
	/**
	 *  removeFront() removes the first item (and first non-sentinel node) from
	 *  a DList2.  If the list is empty, do nothing.
	 */
	public void removeFront() {
		if(head.next == head){
			return;
		}else{
			head.next=head.next.next;
			head.next.prev=head;
		}
		size--;
	}


	public void remove(DListNode a){
		if(a!=null){
			a.prev.next=a.next;
			a.next.prev=a.prev;
			size--;
		}
	}


	public void compact(){
		DListNode cur=this.head.next;
		while (cur.next != head) {
			if (cur.runLengths == 0) {
				this.remove(cur);
				cur = cur.next;
			} else if((cur.color[0]==cur.next.color[0])&&(cur.color[1]==cur.next.color[1])&&(cur.color[2]==cur.next.color[2])) {
				cur.runLengths+=cur.next.runLengths;
				this.remove(cur.next);
			}else{
				cur=cur.next;
			}
		}
	}


	/**
	 *  toString() returns a String representation of this DList.
	 *
	 *  DO NOT CHANGE THIS METHOD.
	 *
	 *  @return a String representation of this DList.
     */



	//  public String toString() {
	//    String result = "[  ";
	//    DListNode current = head.next;
	//    while (current != head) {
	//      result = result + current.item + "  ";
	//      current = current.next;
	//    }
	//    return result + "]";
	//
	//  }

}


