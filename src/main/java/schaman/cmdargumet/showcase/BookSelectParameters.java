package schaman.cmdargumet.showcase;

import schaman.cmdargumet.annotations.Parameter;


public class BookSelectParameters extends BookParameters {

    @Parameter(name = "id", required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
