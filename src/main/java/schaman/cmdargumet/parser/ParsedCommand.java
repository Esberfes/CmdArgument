package schaman.cmdargumet.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ParsedCommand<T> {
    private T commandObject;
    private String prefixParameter;
    private String description;
    private Map<String, String> rawParameters;
    private Map<String, Object> parsedParameters;
    private Map<String, String> helpParameters;

    ParsedCommand(T commandObject, String prefixParameter, String description) {
        this.commandObject = commandObject;
        this.prefixParameter = prefixParameter;
        this.description = description;
        this.rawParameters = new HashMap<>();
        this.parsedParameters = new HashMap<>();
        this.helpParameters = new HashMap<>();
    }

    public String getHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(description)) {
            stringBuilder.append("[[ DESCRIPTION ]]").append("\n");
            stringBuilder.append(description).append("\n").append("\n");
        }

        if(!helpParameters.isEmpty()) {
            stringBuilder.append("[[ HELP ]]").append("\n");
            for(Map.Entry<String, String> entryHelp : helpParameters.entrySet())
                stringBuilder.append(prefixParameter).append(entryHelp.getKey()).append(" >>> ").append(entryHelp.getValue()).append("\n");
        }

        return stringBuilder.toString();
    }

    public T getCommandObject() {
        return commandObject;
    }

    public String getPrefixParameter() {
        return prefixParameter;
    }

    public Map<String, String> getRawParameters() {
        return rawParameters;
    }

    public Map<String, Object> getParsedParameters() {
        return parsedParameters;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getHelpParameters() {
        return helpParameters;
    }

    public void putRawParameter(String parameter, String value) {
        this.rawParameters.put(parameter, value);
    }

    public void putParsedParameter(String parameter, Object value) {
        this.parsedParameters.put(parameter, value);
    }

    public void putHelpParameter(String parameter, String value) {
        this.helpParameters.put(parameter, value);
    }
}
