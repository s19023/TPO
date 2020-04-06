/**
 *
 *  @author Pazur Michał S19023
 *
 */

package PASSTIME1;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Tools
{
    static Options createOptionsFromYaml(String fileName)
    {
        try
        {
            Yaml yaml = new Yaml();
            InputStream fileInputStream = new FileInputStream(fileName);
            OptionsTemplate options = yaml.loadAs(fileInputStream, OptionsTemplate.class);
            return options.createOptionsFromTemplate();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
