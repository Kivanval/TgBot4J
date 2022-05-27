package com.kivanval.telegram.models;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
public class TelegramList implements Serializable {

    @NotNull
    protected Long id;

    @NotNull
    protected TelegramUser creator;


    @Size(
            min = 1,
            max = 255
    )
    protected String title;


    protected int maxSize = Integer.MAX_VALUE;


    @PastOrPresent
    @NotNull
    protected Timestamp startDate = Timestamp.valueOf(LocalDateTime.now());


    @Future
    protected Timestamp endDate;

    protected boolean isFreeze;

}
