package scripts

import org.json.JSONObject
import scripts.support.JSONIterableArray

import static scripts.support.PathConstants.*
import static scripts.support.JsonMethods.*

/**
 * Created by lthatch1 on 5/8/2015.
 * @author Lawrence Thatcher
 */
class ArffFormatter
{
    //--Types to include--
    public static List<String> getAcceptedTypes()
    {
        def result = []
        result.add("Flipping")
        result.add("Sitting_Still")
        return result
    }

    //--Main Method--
    public static void main(String[] args)
    {
        String outputDir = args[0]

        println "getting JSON data"
        def json = jsonFromFile
        def nodes = getNodes(json)
        def valids = getValidInstances(nodes)

        println "formatting data to .arff format"
        List<String> data = []
        for (JSONObject instance : valids)  //for every file
        {
            String bag = ""

            String id = instance.getString("id")
            String path = getTsvPath(id)
            List<String> lines = getLines(path)

            //get data values
            for (int i = 1; i < lines.size(); i++)  //for every line
            {
                String[] parts = lines[i].split("\t")
                if (parts.length == 7)
                {
                    String line =
                            parts[1] + "," + parts[2] + "," + parts[3] + "," +  // accelerometer x,y,z
                            parts[4] + "," + parts[5] + "," + parts[6];         // gyro x,y,z
                    bag += line + "\\n"
                }
            }

            //create data line for arff file
            String label = instance.getString("group")
            data.add("\"" + bag + "\"," + label)
        }

        println "outputting file to: " + outputDir
        outputFile(data,outputDir)
        println "output complete"
    }

    //--Helper Methods--
    private static void outputFile(List<String> output, String outputDir)
    {
        def out = new PrintWriter(ARFF_OUTPUT_DIR + outputDir + ".arff")
        out.print(ARFF_HEADER)
        for (String instance : output)
        {
            out.println(instance)
            out.flush()
        }
        out.close()
    }

    private static List<String> getLines(String path)
    {
        File file = new File(path)
        def inputStream =  new FileInputStream(file)
        def lines = inputStream.readLines()
        inputStream.close()
        return lines
    }

    private static List<JSONObject> getValidInstances(JSONIterableArray nodes)
    {
        def valids = []
        for (Object node : nodes)
        {
            JSONObject instance = (JSONObject)node
            if (shouldAccept(instance))
                valids.add(instance)
        }
        return valids
    }

    private static boolean shouldAccept(JSONObject instance)
    {
        return acceptedTypes.contains(instance.getString("group"))
    }

    private static final String ARFF_HEADER =
            "@relation SensorData\n" +
            "\n" +
            "@attribute bag relational\n" +
            "\t@attribute accel-x numeric\n" +
            "\t@attribute accel-y numeric\n" +
            "\t@attribute accel-z numeric\n" +
            "\t@attribute gyro-x numeric\n" +
            "\t@attribute gyro-y numeric\n" +
            "\t@attribute gyro-z numeric\n" +
            "@end bag\n" +
            "@attribute class {\"Flipping\",\"Sitting_Still\"}\n" +
            "\n" +
            "@data\n"
}
