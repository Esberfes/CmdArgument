package schaman.cmdargumet.test;

import schaman.cmdargumet.annotations.Command;
import schaman.cmdargumet.annotations.Parameter;
import schaman.cmdargumet.parser.ListStringCommaParser;

import java.util.List;

@Command(prefix = "--", description = "Esto es un troncho de descripción que no lee ni dios, que pun que pan que te vi que te bang!")
public class Person {

    @Parameter(name = "name", help = "name of person")
    private String name;

    @Parameter(name = "lastname", help = "last name of person")
    private String lastName;

    @Parameter(name = "age", required = true, help = "must be a number, age of person")
    private int age;

    @Parameter(name = "addresses", parser = ListStringCommaParser.class, help = "list of addresses separated with comma: elemnet1, element2")
    private List<String> addresses;

    @Parameter(name = "active", help = "implicit boolean value")
    private boolean isActive;

    @Parameter(name = "gender", required = true, help = "muste be male or female")
    private GenderEnum genderEnum;

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public void setGenderEnum(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }
}
