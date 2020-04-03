package schaman.cmdargumet.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String prefix() default "-";

    String description() default "";
}
