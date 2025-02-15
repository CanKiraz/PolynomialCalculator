public class Node {
    protected Node next;
    protected String term;
    protected double coefficient = 1;
    protected double powerOfX = 0;
    protected double powerOfY = 0;
    protected double powerOfZ = 0;
    public Node(String term){
        this.next = null;
        this.term = term;
        this.setArithmeticValue(term);
    }
    private void setArithmeticValue(String term){
        if(term.charAt(0) == '+' | term.charAt(0) == '-' | term.charAt(0) == '*'){
            String tempTerm = "";
            for(int i = 1; i < term.length(); i++){
                tempTerm += term.charAt(i);
            }
            term = tempTerm;
        }
        String[] nodeParts = term.split("(?=[xyz])");
        for(int i = 0; i < nodeParts.length; i++){
            if(nodeParts[i].charAt(0) == 'x'){
                String valueString = "";
                for(int j = 1; j < nodeParts[i].length(); j++){
                    valueString += nodeParts[i].charAt(j);
                }
                if(!valueString.isEmpty()){
                    this.powerOfX = Double.parseDouble(valueString);
                }else{
                    this.powerOfX = 1;
                }
            }
            else if(nodeParts[i].charAt(0) == 'y'){
                String valueString = "";
                for(int j = 1; j < nodeParts[i].length(); j++){
                    valueString += nodeParts[i].charAt(j);
                }
                if(!valueString.isEmpty()){
                    this.powerOfY = Double.parseDouble(valueString);
                }else{
                    this.powerOfY = 1;
                }
            }
            else if(nodeParts[i].charAt(0) == 'z'){
                String valueString = "";
                for(int j = 1; j < nodeParts[i].length(); j++){
                    valueString += nodeParts[i].charAt(j);
                }
                if(!valueString.isEmpty()){
                    this.powerOfZ = Double.parseDouble(valueString);
                }else{
                    this.powerOfZ = 1;
                }
            }
            else{
                this.coefficient = Double.parseDouble(nodeParts[i]);
            }
        }
        if(this.term.charAt(0)=='-'){
            this.coefficient *= -1;
        }
    }
    public void setNext(Node next){
        this.next = next;
    }
    public Node getNext(){
        return next;
    }
    public static String termToPrint(Node node){
        String term = "";
        if(node.coefficient > 0){
            term += "+";
        }
        if(node.coefficient != 1){
            term += (int) node.coefficient;
        }

        if(node.powerOfX != 0){
            if(node.powerOfX == 1){
                term += "x";
            }else{
                term += "x" + (int) node.powerOfX;
            }
        }

        if(node.powerOfY != 0){
            if(node.powerOfY == 1){
                term += "y";
            }else{
                term += "y" + (int) node.powerOfY;
            }
        }

        if(node.powerOfZ != 0){
            if(node.powerOfZ == 1){
                term += "z";
            }else{
                term += "z" + (int) node.powerOfZ;
            }
        }



        return term;
    }
}
