package scripts.support

import org.json.JSONObject
import static scripts.support.PathConstants.*

/**
 * Created by lthatch1 on 5/8/2015.
 * @author Lawrence Thatcher
 */
class JsonMethods
{
    //--JSON Methods--
    public static void addElementToJSON(String outputName, String label)
    {
        def json = jsonFromFile
        def nodes = json.getJSONArray("nodes")
        def newEntry = createObject(outputName,label)
        nodes.put(newEntry)
        writeToJSONFile(json)
    }

    public static void writeToJSONFile(JSONObject json)
    {
        def out = new PrintWriter(new File(JSON_DIR))
        out.print(json.toString(2))
        out.close()
    }

    public static JSONObject getJsonFromFile()
    {
        def stream = new FileInputStream(new File(JSON_DIR))
        String text = stream.text
        stream.close()
        return new JSONObject(text)
    }

    public static JSONObject createObject(String outputName, String label)
    {
        JSONObject node = new JSONObject()
        node.put("name","Trial " + outputName)
        node.put("id",outputName)
        node.put("group",label)
        return node
    }

    public static JSONIterableArray getNodes(JSONObject indexJSON)
    {
        def array = indexJSON.getJSONArray("nodes")
        return new JSONIterableArray(array)
    }
}
