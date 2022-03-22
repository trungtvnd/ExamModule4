package com.example.thi_th.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.concurrent.CountDownLatch;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Name not be null")
    private String name;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "Area must be greater than 0")
    private String area;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "Population must be greater than 0")
    private String population;
    @Pattern(regexp = "^[1-9]+[0-9]*$", message = "GDP must be greater than 0")
    private String gdp;

    private String description;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;


}
