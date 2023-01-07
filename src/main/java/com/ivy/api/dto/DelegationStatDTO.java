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

    @NotNull
    private Double percentageChange24Hour;

    public static DelegationStatDTO of(DelegationStatProjection d) {
        return DelegationStatDTO.builder().address(d.getAddress()).count(d.getCount()).average(d.getAverage())
                .standardDeviation(d.getStandardDeviation()).build();
    }
}
