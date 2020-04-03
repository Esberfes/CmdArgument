package schaman.cmdargumet.test;

import schaman.cmdargumet.annotations.Command;
import schaman.cmdargumet.annotations.Parameter;

@Command(prefix = "--", description = "Esto es un troncho de descripción que no lee ni dios, que pun que pan que te vi que te bang!")
public class MyCommandBase {

    @Parameter(name = "action", required = true, help = "action")
    private ActionType action;

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }
}
