import java.io.FileWriter;

/**
 * Methods for linked lists that store the terms and do transactions on them.
 */
public class LinkedList {
    /**
     * The first node of the linked list.
     */
    protected Node head;
    /**
     * The last node of the linked list.
     */
    protected Node tail;
    /**
     * Constructor for linked lists.
     */
    public LinkedList() {
        head = null;
        tail = null;
    }
    /**
     * Checks the emptiness of the linked list.
     * @return Returns whether the linked list is empty or not.
     */
    private boolean isEmpty(){
        return head == null;
    }
    /**
     * Adds the given node to the end of the linked list.
     * @param newNode The node to be added.
     */
    public void insertLast(Node newNode) {
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }
    /**
     * Calculates the number of nodes of the linked list.
     * @return Returns the number of nodes.
     */
    public int numberOfElements(){
        Node temp1 = this.head;
        int count = 0;
        while(temp1 != null){
            count++;
            temp1 = temp1.next;
        }
        return count;
    }
    /**
     * Deletes the first node of the linked list.
     */
    private void deleteFirst(){
        head = head.next;
        if (isEmpty()){
            tail = null;
        }
    }
    /**
     * Finds the previous node of the given node.
     * @param node The node to find its previous node.
     * @return Returns the previous node.
     */
    private Node getPrevious(Node node){
        Node tmp = head;
        Node previous = null;
        while (tmp != node) {
            previous = tmp;
            tmp = tmp.next;
        }
        return previous;
    }
    /**
     * Deletes the last node of the linked list.
     */
    private void deleteLast(){
        tail = getPrevious(tail);
        if (tail != null){
            tail.next = null;
        } else {
            head = null;
        }
    }
    /**
     * Deletes the given node which is not at the end or the beginning of the linked list.
     * @param node The node to be deleted.
     */
    private void deleteMiddle(Node node){
        Node previous;
        previous = getPrevious(node);
        previous.next = node.next;
    }
    /**
     * Calls the appropriate deletion function for the given node.
     * @param node The node to be deleted.
     */
    private void delete(Node node){
        if(this.head == node){
            this.deleteFirst();
        }else if(this.tail == node){
            this.deleteLast();
        }else{
            this.deleteMiddle(node);
        }
    }
    /**
     * Prints the output to the output file.
     * The output is ordered with respect to the power of x, then the power of y, and lastly, the power of z.
     * @param writer The FileWriter which writes output to the output file.
     */
    protected void print(FileWriter writer){
        Node tempNode = this.head;
        Node tempNode2;
        String printMessage = "";
        while (tempNode != null){
            tempNode2 = tempNode.next;

            while (tempNode2 != null){
                if(tempNode2.powerOfX > tempNode.powerOfX){
                    tempNode = tempNode2;
                }
                else if(tempNode2.powerOfX == tempNode.powerOfX){
                    if(tempNode2.powerOfY > tempNode.powerOfY){
                        tempNode = tempNode2;
                    }
                    else if(tempNode2.powerOfY == tempNode.powerOfY){
                        if(tempNode2.powerOfZ > tempNode.powerOfZ){
                            tempNode = tempNode2;
                        }
                        else if(tempNode2.powerOfZ == tempNode.powerOfZ){
                            if(tempNode2.coefficient > tempNode.coefficient){
                                tempNode = tempNode2;
                            }
                        }
                    }
                }
                tempNode2 = tempNode2.next;
            }
            printMessage += Node.termToPrint(tempNode);
            this.delete(tempNode);
            tempNode = this.head;
        }

        if(printMessage.charAt(0) == '+'){
            StringBuilder sb = new StringBuilder(printMessage);
            sb.deleteCharAt(0);
            printMessage = sb.toString();
        }
        try{
            writer.write(printMessage);
        }catch (Exception e){
            System.out.println("Error during writing to file.");
        }
    }
    /**
     * Makes suitable transactions like summing, subtracting, and multiplying on nodes of the linked list.
     * @return Returns the processed linked list.
     */
    public LinkedList process(){
        LinkedList tempList = new LinkedList();
        if(this.numberOfElements() > 1){
            Node temp1 = this.head;
            while (this.numberOfElements() != 0){
                boolean anyTransaction = false;
                Node temp2 = temp1.next;
                while (temp2 != null){
                    if(temp2.term.charAt(0) == '*'){
                        if(temp1.next == temp2){
                            Node temp3 = this.times(temp1,temp2);
                            Node temp4 = temp2.next;
                            this.delete(temp1);
                            this.delete(temp2);
                            this.insertLast(temp3);
                            if(temp4 != null){
                                this.delete(temp4);
                                String newTerm = temp4.term;
                                Node newNode = new Node(newTerm);
                                this.insertLast(newNode);
                            }
                            anyTransaction = true;
                            break;
                        }
                    }else{
                        if(this.isEqual(temp1,temp2)){
                            Node temp3 = this.sum(temp1,temp2);
                            this.delete(temp1);
                            this.delete(temp2);
                            this.insertLast(temp3);
                            anyTransaction = true;
                            break;
                        }
                    }
                    temp2 = temp2.next;
                }
                if(!anyTransaction){
                    this.delete(temp1);
                    tempList.insertLast(temp1);
                }
                temp1 = this.head;
            }
        }
        Node temp1 = tempList.head;

        while (temp1 != null){
            if(temp1.coefficient == 0){
                tempList.delete(temp1);
            }
            temp1 = temp1.next;
        }
        temp1 = tempList.head;
        if(temp1 == null){
            if(this.head != null){
                tempList.insertLast(this.head);
            }
        }
        return tempList;
    }
    /**
     * Calculates the equivalence of given nodes' powers with respect to x, y, and z.
     * @param temp1 The first node to be considered.
     * @param temp2 The second node to be considered.
     * @return Returns whether given nodes are power-equal or not.
     */
    protected boolean isEqual(Node temp1, Node temp2){
        if(temp1.powerOfX == temp2.powerOfX & temp1.powerOfY == temp2.powerOfY & temp1.powerOfZ == temp2.powerOfZ){
            return true;
        }
        return false;
    }
    /**
     * Sums given power-equal two nodes and returns a result node.
     * @param temp1 The first power-equal node to be summed.
     * @param temp2 The second power-equal node to be summed.
     * @return Returns a result node created by adding up given nodes.
     */
    protected Node sum(Node temp1, Node temp2){
        String newTerm = "";
        double coefficientSum = temp1.coefficient + temp2.coefficient;
        if(coefficientSum != 1){
            newTerm += (int) coefficientSum;
        }
        if(temp1.powerOfX != 0){
            newTerm += "x";
            if(temp1.powerOfX != 1){
                newTerm += (int) temp1.powerOfX;
            }
        }
        if(temp1.powerOfY != 0){
            newTerm += "y";
            if(temp1.powerOfY != 1){
                newTerm += (int) temp1.powerOfY;
            }
        }
        if(temp1.powerOfZ != 0){
            newTerm += "z";
            if(temp1.powerOfZ != 1){
                newTerm += (int) temp1.powerOfZ;
            }
        };
        Node newNode = new Node(newTerm);
        return newNode;
    }
    /**
     * Multiplies given two nodes and returns a result node.
     * @param temp1 The first node to be multiplied.
     * @param temp2 The second node to be multiplied.
     * @return Returns a result node created by multiplying given nodes.
     */
    protected Node times(Node temp1, Node temp2){
        String newTerm = "";

        int sumOfPowers;

        if(temp1.coefficient * temp2.coefficient != 1){
            newTerm += (int) (temp1.coefficient * temp2.coefficient);
        }

        if(temp1.powerOfX * temp2.powerOfX != 0){
            newTerm += "x";
            sumOfPowers = (int) (temp1.powerOfX + temp2.powerOfX);
            if(sumOfPowers != 1){
                newTerm += sumOfPowers;
            }
        }else{
            if(temp1.powerOfX != 0 | temp2.powerOfX != 0){
                newTerm += "x";
                if(temp1.powerOfX == 0){
                    sumOfPowers = (int) (temp2.powerOfX);
                }else{
                    sumOfPowers = (int) (temp1.powerOfX);
                }
                if(sumOfPowers != 1){
                    newTerm += sumOfPowers;
                }
            }
        }

        if(temp1.powerOfY * temp2.powerOfY != 0){
            newTerm += "y";
            sumOfPowers = (int) (temp1.powerOfY + temp2.powerOfY);
            if(sumOfPowers != 1){
                newTerm += sumOfPowers;
            }
        }else{
            if(temp1.powerOfY != 0 | temp2.powerOfY != 0){
                newTerm += "y";
                if(temp1.powerOfY == 0){
                    sumOfPowers = (int) (temp2.powerOfY);
                }else{
                    sumOfPowers = (int) (temp1.powerOfY);
                }
                if(sumOfPowers != 1){
                    newTerm += sumOfPowers;
                }
            }
        }

        if(temp1.powerOfZ * temp2.powerOfZ != 0){
            newTerm += "z";
            sumOfPowers = (int) (temp1.powerOfZ + temp2.powerOfZ);
            if(sumOfPowers != 1){
                newTerm += sumOfPowers;
            }
        }else{
            if(temp1.powerOfZ != 0 | temp2.powerOfZ != 0){
                newTerm += "z";
                if(temp1.powerOfZ == 0){
                    sumOfPowers = (int) (temp2.powerOfZ);
                }else{
                    sumOfPowers = (int) (temp1.powerOfZ);
                }
                if(sumOfPowers != 1){
                    newTerm += sumOfPowers;
                }
            }
        }

        return new Node(newTerm);
    }
}
