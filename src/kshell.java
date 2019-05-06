import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class kshell {
    public static HashMap<String, HashSet<String>> linkLoc(String fileName) throws IOException {

        File loc = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(loc));

        HashMap<String,HashSet<String>> result = new HashMap();
        String tempLine = null;
        String first=null;
        String second=null;
        String[] splited=null;

        while ((tempLine=reader.readLine())!=null){

            splited=tempLine.split("\\,");
            first = splited[0];
            second = splited[1];

            if(result.containsKey(first)){
                result.get(first).add(second);
            }
            else{
                HashSet<String> tempPoint= new HashSet();
                tempPoint.add(second);
                result.put(first,tempPoint);
            }


            if(result.containsKey(second)){
                result.get(second).add(first);
            }
            else{
                HashSet<String> tempPoint= new HashSet();
                tempPoint.add(first);
                result.put(second,tempPoint);
            }
        }


        return result;

    }

    public static void readLoc(HashMap<String,HashSet<String>> location){
        String tar = null;
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            tar = scanner.nextLine();

            if (tar.equals("q")){
                break;
            }
            System.out.println(location.get(tar));
        }
    }

    public static HashMap<String,String> calKValue(HashMap<String,HashSet<String>> location){

        HashMap<String,String> kShellValue = new HashMap();
        int degree = 0;
        for (String key:location.keySet()){
            degree = location.get(key).size();
            
        }

//////////此处遍历HashMap未完

        return kShellValue;
    }

    public static void main(String[] args){

        HashMap linkedLoc = new HashMap();
        try {
            linkedLoc = linkLoc(".\\src\\RawLoc.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        readLoc(linkedLoc);




    }
}

