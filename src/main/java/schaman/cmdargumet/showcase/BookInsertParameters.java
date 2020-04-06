package schaman.cmdargumet.showcase;

import schaman.cmdargumet.annotations.Parameter;
import schaman.cmdargumet.parser.ListStringCommaParser;

import java.util.List;


public class BookInsertParameters extends BookParameters {

    @Parameter(name = "name", required = true)
    private String name;

    @Parameter(name = "cool")
    private Boolean cool;

    // TODO revisar tipado, revisar si no es del tipo
    @Parameter(name = "list")
    private List<Integer> list;

    @Parameter(name = "listcomma", parser = ListStringCommaParser.class)
    private List<String> list2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCool() {
        return cool;
    }

    public void setCool(Boolean cool) {
        this.cool = cool;
    }

    public Boolean getCool() {
        return cool;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
