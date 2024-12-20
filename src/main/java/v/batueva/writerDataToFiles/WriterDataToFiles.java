package v.batueva.writerDataToFiles;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.Getter;
import v.batueva.statistics.StatisticsFloat;
import v.batueva.statistics.StatisticsInteger;
import v.batueva.statistics.StatisticsString;
import v.batueva.writerDataToFiles.validators.FileNameValidator;
import v.batueva.writerDataToFiles.validators.NotCommandValidator;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class WriterDataToFiles {
    @Parameter(names = "-o", description = "path for result files names", arity = 1, validateWith = NotCommandValidator.class)
    @Getter
    private String filesPath = "";

    @Parameter(names = "-p", description = "prefix for result files names", arity = 1, validateWith = NotCommandValidator.class)
    @Getter
    private String filesPrefix = "";

    @Parameter(names = "-a", description = "add to existing results")
    @Getter
    private boolean isAddToExisting = false;

    @Parameter(names = "-s", description = "show short statistics")
    @Getter
    private boolean isShortStatistics = false;

    @Parameter(names = "-f", description = "show full statistics")
    @Getter
    private boolean isFullStatistics = false;
    @Parameter(validateWith = FileNameValidator.class)
    @Getter
    private List<String> filesNames = new ArrayList<>();
    private JCommander jCommander;
    private String regexInteger = "^\\-?\\d+$";
    private String regexFloat = "^\\-?\\d+\\.\\d+|\\-?\\d+\\.\\d+E\\-\\d+$";
    private final String INTEGER_FILE_NAME = "integers.txt";
    private final String FLOAT_FILE_NAME = "floats.txt";
    private final String STRING_FILE_NAME = "strings.txt";

    public WriterDataToFiles() {
        jCommander = JCommander.newBuilder().addObject(this).build();
    }

    public void run(String[] args) {
        try {
            jCommander.parse(args);
            writeDataToFile(readDataFromFile());
            printStatistics();
        } catch (ParameterException e) {
            System.out.println("Wrong arguments");
        }
    }

    private void writeDataToFile(List<String> dataToWrite) {
        if (isAddToExisting) {
            writeListToFile(dataToWrite);
        } else {
            File fileIntegers = new File(filesPrefix + INTEGER_FILE_NAME);
            fileIntegers.delete();
            File fileFloats = new File(filesPrefix + FLOAT_FILE_NAME);
            fileFloats.delete();
            File fileStrings = new File(filesPrefix + STRING_FILE_NAME);
            fileStrings.delete();
            writeListToFile(dataToWrite);
        }
    }

    private void writeListToFile(List<String> dataToWrite) {
        for (String data : dataToWrite) {
            if (data.matches(regexInteger)) {
                StatisticsInteger.addValue(BigInteger.valueOf(Long.parseLong(data)));
                writeStringToFile(filesPrefix + INTEGER_FILE_NAME, data);
            } else if (data.matches(regexFloat)) {
                StatisticsFloat.addValue(BigDecimal.valueOf(Double.parseDouble(data)));
                writeStringToFile(filesPrefix + FLOAT_FILE_NAME, data);
            } else {
                StatisticsString.addValue(data);
                writeStringToFile(filesPrefix + STRING_FILE_NAME, data);
            }
        }
    }

    private void writeStringToFile(String fileName, String data) {
        try (BufferedWriter writerDataToFile = new BufferedWriter(new FileWriter(fileName, true))) {
            writerDataToFile.write(data + '\n');
            writerDataToFile.flush();
        } catch (IOException e) {
            System.out.println("File Not Found");
        }
    }

    private List<String> readDataFromFile() {
        List<String> dataFromFiles = new ArrayList<>();
        for (String fileName : filesNames) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filesPath + '\\' + fileName)))) {
                String line;
                while ((line = in.readLine()) != null) {
                    dataFromFiles.add(line);
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file: " + fileName);
            }
        }
        return dataFromFiles;
    }

    private void printStatistics() {
        if (isFullStatistics) {
            StatisticsInteger.printFullStatistics();
            StatisticsFloat.printFullStatistics();
            StatisticsString.printFullStatistics();
        }
        if (isShortStatistics) {
            StatisticsInteger.printShortStatistics();
            StatisticsFloat.printShortStatistics();
            StatisticsString.printShortStatistics();
        }
    }
}