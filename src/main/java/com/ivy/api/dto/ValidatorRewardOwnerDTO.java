package com.ivy.api.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidatorRewardOwnerDTO {
    @NotNull
    private List<String> addresses;

    @NotNull
    private List<String> checksumAddresses;

}
