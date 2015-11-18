
import static de.markiewb.netbeans.plugin.git.openinexternalviewer.ConfigurationMigratorAtStartup.getMD5;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author markiewb
 */
public class CreateMD5 {
    @Test
    public void testMe() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("D:\\ws\\nb-git-open-in-external-repoviewer\\src\\test\\java\\configuration1.4.0.2.unsorted.properties")));        
        System.out.println("content = " + content);
        System.out.println("md5(content) = " + getMD5(content.trim()));
    }
}
