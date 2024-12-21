package v.batueva.writerDataToFiles.validators.fileNameValidator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class FileNameValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        String regex = "[^ ]+\\.[^ ]+";
        if(!value.matches(regex)){
            throw new ParameterException("Wrong file name");
        }
    }
}