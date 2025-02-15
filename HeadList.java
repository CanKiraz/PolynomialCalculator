import java.io.FileWriter;
/**
 * Methods for Linked lists that stores linked lists.
 */
public class HeadList extends LinkedList{
    /**
     * The transaction to make between linked lists stored.
     */
    protected String transaction;
    /**
     * Stored linked lists to be processed.
     */
    protected LinkedList[] linkedLists = new LinkedList[2];
    /**
     * Processes stored linked lists and creates a result linked list. Then calls the print method.
     * @param writer The FileWriter to be given to the print method.
     */
    public void secondProcess(FileWriter writer){
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
        tempList.print(writer);
    }
}
