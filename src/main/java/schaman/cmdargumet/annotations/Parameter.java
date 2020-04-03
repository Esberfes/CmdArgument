package schaman.cmdargumet.annotations;

import schaman.cmdargumet.parser.DefaultParser;
import schaman.cmdargumet.parser.ParameterParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Parameter {
    String name();

    Class<? extends ParameterParser> parser() default DefaultParser.class;

    boolean required() default false;

    String help() default "";
}
