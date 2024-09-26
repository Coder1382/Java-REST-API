package rest.dto;

public class SuppliersDto {
    long id;
    String name;

    public SuppliersDto() {
        id = 0;
        name = "";
    }

    public SuppliersDto(long id) {
        this.id = id;
    }

    public SuppliersDto(String company) {
        this.name = company;
    }

    public SuppliersDto(long id, String company) {
        this.id = id;
        this.name = company;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
