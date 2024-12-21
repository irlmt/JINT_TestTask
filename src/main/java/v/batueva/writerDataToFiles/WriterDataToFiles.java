package v.batueva.writerDataToFiles;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.Getter;
import v.batueva.dataParser.DataParser;
import v.batueva.statistics.statisticsFloat.StatisticsFloat;
import v.batueva.statistics.statisticsInteger.StatisticsInteger;
import v.batueva.statistics.statisticsString.StatisticsString;
import v.batueva.writerDataToFiles.validators.fileNameValidator.FileNameValidator;
import v.batueva.writerDataToFiles.validators.notCommandValidator.NotCommandValidator;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

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