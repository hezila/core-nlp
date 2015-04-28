package com.numb3r3.nlp.util.wordnet;

import com.numb3r3.common.ResourceUtils;
import edu.illinois.cs.cogcomp.wnsim.WNSim;
import net.didion.jwnl.JWNLException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fwang
 * Date: 9/24/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class IllinoisWNSimilarity {
    WNSim wnsim = null;

    public IllinoisWNSimilarity() {
        String pathWordNet = ResourceUtils.getFileURL("wordnet-3.0", this.getClass()).getPath();
        String pathWordNetConfigFile = ResourceUtils.getFileURL("jwnl_file_properties.xml", this.getClass()).getPath();

        try {
            wnsim = WNSim.getInstance(pathWordNet, pathWordNetConfigFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JWNLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public double getSimilarity(String word1, String word2) {
        try {
            return this.wnsim.getWupSimilarity(word1, word2);
        } catch (JWNLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return -1.0;
    }

    public static void main(String[] args) throws IOException, JWNLException {
        String word1 = "breakfast";
        String word2 = "service";
        IllinoisWNSimilarity sim = new IllinoisWNSimilarity();
        System.out.println("" + sim.getSimilarity(word1, word2));
    }

}
