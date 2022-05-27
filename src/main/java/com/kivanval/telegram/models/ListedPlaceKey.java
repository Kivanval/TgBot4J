package com.kivanval.telegram.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ListedPlaceKey implements Serializable {


    @NotNull
    Long listId;

    @NotNull
    Long userId;



}
