import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Smart Printer.
 *
 * The class which build the table on the console with data from the file "fileName.csv"
 * The program working in 2 stages. first checking the size of the future table, next - writing data to the table;
 *
 * @author Mikhailov Vladimir
 */
public class Printer {
//url for our file;
    private static String fileName = "/home/abonent63/test2.csv";
//some text for test;
    private static String testText = "Id;Type;Description;IsApproved;Amount;Currency\n" +
                                     "100017508;BBK;Gift for the boss's lover;true;8750,35;USD\n" +
                                     "100017603;OE;Detergents;false;1813,12;EUR\n" +
                                     "100017508;BBK;Gift for the boss's lover;true;8750,35;USD\n" +
                                     "100017603;OE;Detergents;false;1813,12;EUR\n" +
                                     "100017508;BBK;Gift for the boss's lover;true;8750,35;USD\n" +
                                     "100017603;OE;Detergents;false;1813,12;EUR\n" +
                                     "100017508;BBK;Gift for the boss's lover;true;8750,35;USD\n" +
                                     "100017603;OE;Detergents;false;1813,12;EUR\n" +
                                     "100017610;;;true;1000;USD";
//default number of columns in the table;
    private static int numbOfCollumns = 0;
//flag for calculate number of columns in the table;
    private static boolean flagForColumns = false;
//size or rows in the table;
    private static int[] sizeOfRow;
//List of the clean rows which consist of the spaces;
    private static ArrayList<char[]> defaultRows;
//width of the table;
    private static int widthOfTable = 0;

    public static void main(String[] args) throws FileNotFoundException {
//write test text to default test file;
     //write(fileName, testText);
//method which reads file, creates table and print it to console;
        read(fileName);
    }
//method read;
    public static void read(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
        exists(fileName);
        try {
//Objects for reading file to buffer;
//in - for reading and count number and size of the roads;out - for read and write file to the table;
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            BufferedReader out = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                String tmp;
//Cyrcle for identity parametrs of the table (number of columns, size of the rows);
                while ((tmp = in.readLine()) != null) {
                    char [] str = tmp.toCharArray();
//if "flagForColumns" - false, the number of column still not known. Start method count column(only 1 time)
//because all lines in the file has the same structure;
                    if (!flagForColumns) {
                        countColumns(str);
                    }
//count size of the rows;
                    countSizeOfRows(str);
                }
//create rows. Filling them of the spaces " ";
                makeRows();
//create horizontal line for the head of the table and close the table in the down;
                String headLine = horizontalLine();
                System.out.println(headLine);
//Cycle for filling the table data and writing the table to console;
                boolean headFlag = false;
                while ((tmp = out.readLine()) != null) {
                    char [] line = tmp.toCharArray();

                    System.out.println(buildLine(line));
//devide head from the other part of the table;
                    if (!headFlag){
                        System.out.println(headLine);
                        headFlag = true;
                        continue;
                    }
                    System.out.println(collectLine(defaultRows));
                }
                System.out.println(headLine);
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
//method whice represent count of the number of columns in future table. And set default size of the row = "0";
    private static void countColumns(char [] str){
        for(char i : str){
            if (i == ';'){
                numbOfCollumns++;
            }
        }
        numbOfCollumns++;
        sizeOfRow = new int[numbOfCollumns];
        Arrays.fill(sizeOfRow, 0);
        flagForColumns = true;
    }
//method wich represent set data to rows;
    private static String buildLine (char[] line){
        ArrayList<char[]> ch = copy();
        int numbOfRows = 0;
        int count = 0;

        for (int i = 0; i < line.length; i++){
            if (line[i] != ';'){
                ch.get(numbOfRows)[count] = line[i];
                count++;
            }else{
                numbOfRows++;
                count = 0;
            }
        }
        return collectLine(ch);
    }
//The method which represents the building lines(with data and without) from the rows adn adding dividing
//columns with the vertical lines;
    private static String collectLine(ArrayList<char[]> rows){
        StringBuilder sb =  new StringBuilder();
        sb.append('|');
        for (int i = 0; i < numbOfCollumns; i++){
            sb.append(new String(rows.get(i)));
            sb.append('|');
        }
        widthOfTable = sb.length();
        return sb.toString();
    }
//count the size of the future rows. If someone need to be old and new size with comparing is different,
//sets bigger size for the future row;
    private static void countSizeOfRows(char[] line){
        int tmp = 0;
        int count = 0;
        int length = line.length;

        for (int j = 0; j < length; j++){
            if (line[j] != ';'){
                tmp++;
            }
            if (line[j] == ';' || j == length-1){
                if (sizeOfRow[count] < tmp){
                    sizeOfRow[count] =  tmp;
                }
                count++;
                tmp = 0;
            }
        }
    }
//The method which represent for filling rows spaces;
    private static void makeRows (){
        defaultRows = new ArrayList<char[]>(numbOfCollumns);
        for (int i = 0; i < numbOfCollumns; i++){
            char [] r = new char[sizeOfRow[i]];
            Arrays.fill(r, ' ');
            defaultRows.add(r);
        }
    }
//The method which created clone of the clean line "defaultRows" and this method counts the size of the table
//which will be used for create of the "horizontalLine";
    private static ArrayList<char []> copy (){
        ArrayList<char []> cl = new ArrayList<char[]>(numbOfCollumns);
        for (int i = 0; i < numbOfCollumns; i++){
            cl.add(defaultRows.get(i).clone());
            widthOfTable +=cl.get(i).length;
        }
        widthOfTable = widthOfTable + numbOfCollumns + 1;
        return cl;
    }
//write test text to the file which exist, or create new, if dosn't exist;
    public static void write(String fileName, String testText){
        File file = new File(fileName);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                //Записываем текст у файл
                out.print(testText);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
//check the file to exist. If not throws "FileNotFoundException;
    private static void exists(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
        if (!file.exists()){
            System.out.println("File doesn't exist or wrong fileName!!!!"+"\n"+
                    "You can create default test File. For this uncomment method \"write\"");
            throw new FileNotFoundException(file.getName());
        }
    }
//creating of the horizontal line for the head and closing the table in the end;
    private static String horizontalLine(){
        for (int i = 0; i < numbOfCollumns; i++){
            widthOfTable += defaultRows.get(i).length;
        }
        char [] ch = new char[widthOfTable+numbOfCollumns+1];
        Arrays.fill(ch, '-');
        return new String(ch);
    }
}