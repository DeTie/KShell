import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class KShell {
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
            //System.out.println(location.get(tar).size());//show the original degree of each spot
        }
    }

    public static void readValue(HashMap<String,Integer> result){
        String tar;
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            tar = scanner.nextLine();

            if (tar.equals("q")){
                break;
            }
            System.out.println(result.get(tar));
        }
    }

    public static void clearSpot(HashMap<String,HashSet<String>> location, HashMap<String,String> result){
        for(String key:result.keySet()){
            if(!location.containsKey(key)){
                continue;
            }
            for(String subKey:location.get(key)){
                location.get(subKey).remove(key);
            }
            location.remove(key);
        }
    }

    public static void iniPreDeleted(HashMap<String,Integer> preDeleted){
        for (String key:preDeleted.keySet()){
            preDeleted.put(key,0);
        }
    }
    //initialize PreDeleted to 0
    public static void updatePreDeleted(HashMap<String,Integer> preDeleted, HashMap<String,HashSet<String>> location, HashSet<String> tempSpot){
        for(String key:tempSpot){
            for(String subkey:location.get(key)){
                preDeleted.put(subkey,preDeleted.get(subkey)+1);
            }
        }
    }

    public static HashMap<String,String> calValue(HashMap<String,HashSet<String>> location, double weight){
        HashMap<String,String> result = new HashMap<>();
        HashMap<String,Integer> preDeleted = new HashMap<>();
        for (String key:location.keySet()){
            preDeleted.put(key,0);
        }//add all the spot keys to preDeleted and initialize them as 0

        HashSet<String> tempSpot = new HashSet<>();
        double tempDegree;
        double tempValue;
        int locSize=0;
        DecimalFormat outcome = new DecimalFormat("#.0");//regulate all the over-counted decimals caused by the class "Double"

        while(!location.isEmpty()){
            tempDegree=location.entrySet().iterator().next().getValue().size()+
                       preDeleted.get(location.entrySet().iterator().next().getKey())*weight;
            tempSpot.add(location.entrySet().iterator().next().getKey());
            //initialize the first spot
            // iterator output the latest-input <key,value>
            for(String key:location.keySet()){
                locSize=location.get(key).size();
                tempValue=locSize+weight*preDeleted.get(key);
                //store the recent value of k-shell of this certain spot
                if(tempValue > tempDegree){
                    continue;
                }
                else if(tempValue == tempDegree){
                    tempSpot.add(key);
                }
                else if(tempValue < tempDegree){
                    tempDegree=tempValue;
                    tempSpot.clear();
                    tempSpot.add(key);
                }
            }
            updatePreDeleted(preDeleted,location,tempSpot);
            //add 1 to the root spot of the to-be-deleted spots

            for(String key:tempSpot){
                result.put(key,outcome.format(tempDegree));
            }//add the deleted the spots and the value to the result

            System.out.println(result);
            tempSpot.clear();
            clearSpot(location,result);
        }

        return result;
}

    public static void main(String[] args){
        HashMap linkedLoc = new HashMap();
        try {
            linkedLoc = linkLoc(".\\src\\RawLoc.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String,Integer> test = new HashMap<>();

        readLoc(linkedLoc);

        HashMap a = calValue(linkedLoc,0.7);

        readValue(a);

    }
}