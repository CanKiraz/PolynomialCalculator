import java.io.FileWriter;

public class LinkedList {
    protected Node head;
    protected Node tail;
    protected String transaction;
    protected LinkedList[] linkedLists = new LinkedList[2];
    public LinkedList() {
        head = null;
        tail = null;
    }
    private boolean isEmpty(){
        return head == null;
    }
    public void insertLast(Node newNode) {
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }
    public int numberOfElements(){ //düzelt bunu
        Node temp1 = this.head;
        Node temp2;
        int count = 0;
        while(temp1 != null){
            count++;
            temp2 = temp1;
            temp1 = temp2.next;
        }
        return count;
    }
    private void deleteFirst(){
        head = head.getNext();
        if (isEmpty()){
            tail = null;
        }
    }
    private Node getPrevious(Node node){
        Node tmp = head;
        Node previous = null;
        while (tmp != node) {
            previous = tmp;
            tmp = tmp.getNext();
        }
        return previous;
    }
    private void deleteLast(){
        tail = getPrevious(tail);
        if (tail != null){
            tail.setNext(null);
        } else {
            head = null;
        }
    }
    private void deleteMiddle(Node node){
        Node previous;
        previous = getPrevious(node);
        previous.setNext(node.getNext());
    }
    private void delete(Node node){
        if(this.head == node){
            this.deleteFirst();
        }else if(this.tail == node){
            this.deleteLast();
        }else{
            this.deleteMiddle(node);
        }
    }
    public void print(FileWriter writer){
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
    public LinkedList secondProcess(){
        LinkedList tempList = new LinkedList();
        Node temp1 = this.linkedLists[0].head;
        Node temp2 = this.linkedLists[1].head;
        if(transaction.equals("*")){
            while (temp1 != null){
                while (temp2 != null){
                    Node temp3 = this.times(temp1,temp2);
                    tempList.insertLast(temp3);
                    temp2 = temp2.next;
                }
                temp1 = temp1.next;
            }
        }else{
            if(transaction.equals("-")){
                while (temp2 != null){
                    temp2.coefficient *= -1;
                    temp2 = temp2.next;
                }
            }
            temp2 = this.linkedLists[1].head;

            while (temp1 != null){
                tempList.insertLast(temp1);
                temp1 = temp1.next;
            }
            while (temp2 != null){
                tempList.insertLast(temp2);
                temp2 = temp2.next;
            }
        }
        tempList = tempList.process();
        temp1 = tempList.head;
        while (temp1 != null){
            temp1 = temp1.next;
        }
        return tempList;
    }
    private boolean isEqual(Node temp1, Node temp2){
        if(temp1.powerOfX == temp2.powerOfX & temp1.powerOfY == temp2.powerOfY & temp1.powerOfZ == temp2.powerOfZ){
            return true;
        }
        return false;
    }
    private Node sum(Node temp1, Node temp2){
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
    private Node times(Node temp1, Node temp2){
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
