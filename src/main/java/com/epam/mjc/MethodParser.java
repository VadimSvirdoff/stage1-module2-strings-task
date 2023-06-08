package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringBuilder sb = new StringBuilder(signatureString);
        int startBrackets = sb.indexOf("(");
        int endBrackets = sb.indexOf(")");
        String[] partBeforeArguments = sb.substring(0, startBrackets).toString().split(" ");
        String[] partWithArguments = sb.substring(startBrackets + 1, endBrackets).toString().split(", ");

        boolean isEmptyElement = String.join("", partWithArguments).isEmpty();
        MethodSignature methodSignature;
        boolean hasAccessModifier = isAccessModifier(sb);
        String accessModifier = hasAccessModifier ? partBeforeArguments[0] : "";
        String returnType = hasAccessModifier ? partBeforeArguments[1] : partBeforeArguments[0];
        String methodName = hasAccessModifier ? partBeforeArguments[2] : partBeforeArguments[1];

        if(isEmptyElement == false){
            List<MethodSignature.Argument> arguments = fillArguments(partWithArguments);
            methodSignature = new MethodSignature(methodName,arguments);
        }else{
            methodSignature = new MethodSignature(methodName);
        }
        if(hasAccessModifier) {
            methodSignature.setAccessModifier(accessModifier);
        }
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }

    private List<MethodSignature.Argument> fillArguments(String[] partWithArguments) {
        List<MethodSignature.Argument> arguments = new ArrayList<>();

        for (String argumentsPart : partWithArguments) {
            String[] argumentsPartArray = argumentsPart.split(" ");
            String argumentType = argumentsPartArray[0].trim();
            String argumentName = argumentsPartArray[1].trim();
            arguments.add(new MethodSignature.Argument(argumentType,argumentName));
        }

        return arguments;
    }

    private boolean isAccessModifier(StringBuilder sb) {
        if (sb.indexOf("private") != -1 || sb.indexOf("public") != -1 || sb.indexOf("protected") != -1) {
            return true;
        }
        return false;
    }
}
