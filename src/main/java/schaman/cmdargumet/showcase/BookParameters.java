package schaman.cmdargumet.showcase;

import schaman.cmdargumet.annotations.Command;
import schaman.cmdargumet.annotations.Parameter;

@Command(description = "Esta descripcion es opcional")
public class BookParameters {

    @Parameter(name = "type", required = true, help = "type action")
    private ActionType type;

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }
}
