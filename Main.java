import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Takes an input.txt file and calculates its given transactions.
 * Then prints the output to the output.txt file.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        File input = new File(Main.class.getClassLoader().getResource("input.txt").toURI());
        Scanner in = new Scanner(input);
        File output = new File(Main.class.getClassLoader().getResource("output.txt").toURI());
        FileWriter writer = new FileWriter(output);

        int lineNumberOfFile = Integer.parseInt(in.nextLine());

        for(int i = 0; i < lineNumberOfFile; i++){
            HeadList secondList = new HeadList();
            String term = "";
            String tempLine = in.nextLine();
            secondList.transaction = String.valueOf(tempLine.charAt(0));
            for(int j = 2; j < tempLine.length(); j++){
                term += tempLine.charAt(j);
            }
            String[] firstLayerTerms = term.split(" ");
            if(firstLayerTerms[0].length() == 0 | firstLayerTerms.length == 1){
                System.out.println("Cannot be blank");
                System.exit(0);
            }
            for(int j = 0; j < firstLayerTerms.length; j++){
                LinkedList firstList = new LinkedList();
                String[] secondLayerTerms = firstLayerTerms[j].split("(?=[+*-])");
                for(int k = 0; k < secondLayerTerms.length; k++){
                    Node newNode = new Node(secondLayerTerms[k]);
                    firstList.insertLast(newNode);
                }
                secondList.linkedLists[j] = firstList.process();;
            }
            secondList.secondProcess(writer);
            if(i != lineNumberOfFile-1){
                writer.write("\n");
            }
        }
        writer.close();
        System.out.println("Writing Done.");
    }

}
