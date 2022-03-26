package com.example.pds.model.packages;

import com.example.pds.model.user.userDTO.UserReceivePackageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PackageGetMyPackagesDTO {
    private int id;
    private Boolean isFragile;
    private String trackingNumber;
    private String description;
    private Boolean isSigned;
    private Double weight;
}
