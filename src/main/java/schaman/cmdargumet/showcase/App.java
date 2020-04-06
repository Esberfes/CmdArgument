package schaman.cmdargumet.showcase;

import com.google.gson.Gson;
import schaman.cmdargumet.parser.CommandParser;

public class App {

    public static void main(String[] args) {
        BookParameters bookParameters = new BookParameters();
        CommandParser<BookParameters> bookParametersCommandParser = new CommandParser<>(bookParameters, args);

        try {
            bookParametersCommandParser.parse();
            switch (bookParameters.getType()) {
                case select:
                    BookSelectParameters bookSelectParameters = new BookSelectParameters();
                    CommandParser<BookSelectParameters> bookSelectParametersCommandParser = new CommandParser<>(bookSelectParameters, args);
                    try {
                        bookSelectParametersCommandParser.parse();
                        System.out.println(new Gson().toJson(bookSelectParameters));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(bookSelectParametersCommandParser.getHelp());
                    }
                    break;
                case insert:
                    BookInsertParameters bookInsertParameters = new BookInsertParameters();
                    CommandParser<BookInsertParameters> bookInsertParametersCommandParser = new CommandParser<>(bookInsertParameters, args);
                    try {
                        bookInsertParametersCommandParser.parse();
                        System.out.println(new Gson().toJson(bookInsertParameters));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(bookInsertParametersCommandParser.getHelp());
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(bookParametersCommandParser.getHelp());
        }

        System.out.println(new Gson().toJson(bookParameters));
    }
}
