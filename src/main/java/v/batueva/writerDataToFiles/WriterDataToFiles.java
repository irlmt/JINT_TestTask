package v.batueva.writerDataToFiles;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import v.batueva.dataParser.DataParser;

public class WriterDataToFiles {
    private JCommander jCommander;
    private DataParser dataParser;
    public WriterDataToFiles() {
        dataParser = new DataParser();
        jCommander = JCommander.newBuilder().addObject(dataParser).build();
    }

    public void run(String[] args) {
        try {
            jCommander.parse(args);
            dataParser.parse();
        } catch (ParameterException e) {
            System.out.println("Wrong arguments");
        }
    }
}