package schaman.cmdargumet;

import com.google.gson.Gson;
import schaman.cmdargumet.exception.ParseCommandParameterException;
import schaman.cmdargumet.parser.CommandParser;
import schaman.cmdargumet.test.MyCommandBase;
import schaman.cmdargumet.test.Person;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        MyCommandBase myCommandBase = new MyCommandBase();
        CommandParser<MyCommandBase> commandBaseCommandParser = new CommandParser<>(myCommandBase, args);
        CommandParser selected = null;

        try {
            commandBaseCommandParser.parse();
            try {
                switch (myCommandBase.getAction()) {
                    case insert:
                        Person person = new Person();
                        selected = new CommandParser<>(person, args);
                        selected.parse();
                        break;
                    case drop:
                        selected = null;
                        break;
                    case select:
                        selected = null;
                        break;
                    default:
                        selected = null;
                }
            } catch (ParseCommandParameterException e) {
                System.out.println(e.getMessage());
                System.out.println(selected.getHelp());
            }
        } catch (ParseCommandParameterException e) {
            System.out.println(e.getMessage());
            System.out.println(commandBaseCommandParser.getHelp());
        }

        System.out.println(new Gson().toJson(myCommandBase));
    }
}
