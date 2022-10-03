package com.ivy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@SuperBuilder
public class FTSODataProviderDTO {
    @NotNull
    String address;

    @NotNull
    List<String> whitelistedSymbols;
}
