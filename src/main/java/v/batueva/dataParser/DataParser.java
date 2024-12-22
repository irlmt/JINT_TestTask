package v.batueva.dataParser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
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

public class DataParser {
    @Parameter(names = "-o", description = "path for result files", arity = 1, validateWith = NotCommandValidator.class)
    private String filesPath = "";

    @Parameter(names = "-p", description = "prefix for result files names", arity = 1, validateWith = NotCommandValidator.class)
    private String filesPrefix = "";

    @Parameter(names = "-a", description = "add to existing results")
    private boolean isAddToExisting = false;

    @Parameter(names = "-s", description = "show short statistics")
    private boolean isShortStatistics = false;

    @Parameter(names = "-f", description = "show full statistics")
    private boolean isFullStatistics = false;
    @Parameter(validateWith = FileNameValidator.class)
    private List<String> filesNames = new ArrayList<>();
    private JCommander jCommander;
    private String regexInteger = "^\\-?\\d+$";
    private String regexFloat = "^\\-?\\d+\\.\\d+|\\-?\\d+\\.\\d+E\\-\\d+$";
    private final String INTEGER_FILE_NAME = "integers.txt";
    private final String FLOAT_FILE_NAME = "floats.txt";
    private final String STRING_FILE_NAME = "strings.txt";

    public void parse() {
        try {
            writeDataToFiles();
        } catch (IOException e) {
            System.out.println("Couldnt close file");
        }
        printStatistics();
    }

    private void writeDataToFiles() throws IOException {
        checkWriteToExisting();
        List<BufferedReader> readers = new ArrayList<>();
        try {
            for (String fileName : filesNames) {
                readers.add(new BufferedReader(new InputStreamReader(new FileInputStream(fileName))));
            }
            boolean notEmpty = true;
            while (notEmpty) {
                notEmpty = false;
                for (BufferedReader in : readers) {
                    String line;
                    if ((line = in.readLine()) != null) {
                        matchDataType(line);
                        notEmpty = true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        } catch (NullPointerException e) {
            System.out.println("Empty string");
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format");
        } catch (PatternSyntaxException e) {
            System.out.println("Wrong pattern");
        } finally {
            for (BufferedReader in : readers) {
                if (in != null) {
                    in.close();
                }
            }
        }
    }

    private void checkWriteToExisting() {
        if(!filesPath.equals("")){
            filesPath = filesPath + '\\';
        }
        if (!isAddToExisting) {
            try {
                File fileIntegers = new File(filesPath + filesPrefix + INTEGER_FILE_NAME);
                fileIntegers.delete();
                File fileFloats = new File(filesPath + filesPrefix + FLOAT_FILE_NAME);
                fileFloats.delete();
                File fileStrings = new File(filesPath + filesPrefix + STRING_FILE_NAME);
                fileStrings.delete();
            } catch (SecurityException e) {
                System.out.println("Couldn't delete file");
            } catch (NullPointerException e) {
                System.out.println("Couldn't open file");
            } catch (PatternSyntaxException e) {
                System.out.println("Wrong pattern");
            }
        }
    }

    private void matchDataType(String line) throws PatternSyntaxException {
        if (line.matches(regexInteger)) {
            StatisticsInteger.addValue(BigInteger.valueOf(Long.parseLong(line)));
            writeStringToFile(filesPath + filesPrefix + INTEGER_FILE_NAME, line);
        } else if (line.matches(regexFloat)) {
            StatisticsFloat.addValue(BigDecimal.valueOf(Double.parseDouble(line)));
            writeStringToFile(filesPath + filesPrefix + FLOAT_FILE_NAME, line);
        } else {
            StatisticsString.addValue(line);
            writeStringToFile(filesPath + filesPrefix + STRING_FILE_NAME, line);
        }
    }

    private void writeStringToFile(String fileName, String data) {
        try (BufferedWriter writerDataToFile = new BufferedWriter(new FileWriter(fileName, true))) {
            writerDataToFile.write(data + '\n');
            writerDataToFile.flush();
        } catch (IOException e) {
            System.out.println("I/O error occured");
        }
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
