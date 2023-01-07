package com.ivy.api.dto;

import javax.validation.constraints.NotNull;

import com.ivy.api.dto.projection.DelegationStatProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class DelegationStatDTO {

    @NotNull
    private String address;

    @NotNull
    private Integer count;

    @NotNull
    private Double average;

    @NotNull
    private Double standardDeviation;

    public static DelegationStatDTO of(DelegationStatProjection d) {
        return new DelegationStatDTO(d.getAddress(), d.getCount(), d.getAverage(), d.getStandardDeviation());
    }
}
