package rest.dto;

import java.util.ArrayList;
import java.util.List;

public class SuppliersDto {
    private long id = 0;
    private String name = "";
    private List<SellersDto> sellers = new ArrayList<>();
    private String seller = "";

    public SuppliersDto() {
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

    public SuppliersDto(long id, String company, List<SellersDto> sellers) {
        this.id = id;
        this.name = company;
        this.sellers = new ArrayList<>();
        sellers.forEach(e -> {
            this.sellers.add(e);
        });
    }

    public SuppliersDto(String company, String seller) {
        this.name = company;
        this.seller = seller;
    }

    public String getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
