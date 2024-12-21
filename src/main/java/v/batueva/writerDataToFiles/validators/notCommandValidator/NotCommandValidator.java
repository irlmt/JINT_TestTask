package v.batueva.writerDataToFiles.validators.notCommandValidator;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class NotCommandValidator implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException {
        String regex = "\\-[a-z]";
        if(value.matches(regex)){
            throw new ParameterException("Parameter " + name + "should pass path to files, found " + value);
        }
    }
}