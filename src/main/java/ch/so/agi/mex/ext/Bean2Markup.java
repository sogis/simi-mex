package ch.so.agi.mex.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Iterator;

public class Bean2Markup {

    private static Logger log = LoggerFactory.getLogger(Bean2Markup.class);

    public static File runBean2Html(ThemePublication dataset){
        return null;
    }

    public static File runBeans2Xml(Path xmlFilePath, Iterator<ThemePublication> datasets, RegionsReader regReader){

        StringBuilder sb = new StringBuilder();

        int i=0;
        while(datasets.hasNext()){
            ThemePublication curr = datasets.next();

            String currString = MessageFormat.format("ThemePublication: foo {0}, bar {1}", curr.getFoo(), curr.getBar());
            log.info(currString);
            sb.append(currString);
            sb.append(System.lineSeparator());

            if(i % 10 == 0) { //dummy für jeden zehnten Aufruf
                String region = regReader.readRegionsForThemePub(curr);
                String regionLog = MessageFormat.format("Region: {0}", region);
                log.info(regionLog);
                sb.append(regionLog);
                sb.append(System.lineSeparator());
            }

            i++;
        }

        try {
            Files.writeString(xmlFilePath, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
