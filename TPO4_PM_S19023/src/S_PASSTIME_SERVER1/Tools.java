/**
 *
 *  @author Pazur Michał S19023
 *
 */

package S_PASSTIME_SERVER1;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Tools
{
    static Options createOptionsFromYaml(String fileName)
    {
        try
        {
            Yaml yaml = new Yaml();
            InputStream fileInputStream = new FileInputStream(fileName);
            Map<String, Object> yamlContent = yaml.load(fileInputStream);
            String host = (String) yamlContent.get("host");
            int port = (int) yamlContent.get("port");
            boolean concurMode = (boolean) yamlContent.get("concurMode");
            boolean showSendRes = (boolean) yamlContent.get("showSendRes");
            Map<String, List<String>> clientsMap = (Map<String, List<String>>) yamlContent.get("clientsMap");
            Options options = new Options(host, port, concurMode, showSendRes, clientsMap);
            return options;
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}