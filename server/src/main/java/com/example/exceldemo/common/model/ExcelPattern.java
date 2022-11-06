package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelPattern {

    CIVILITY_PATTERN("^Mr|Mrs|Ms|Miss$"),
    FIRSTNAME_PATTERN("^([a-zA-Z '-]+)$"),
    LASTNAME_PATTERN("^([a-zA-Z '-]+)$"),
    BIRTHDATE_PATTERN("^([0-9]{0,2})\\/([0-9]{0,2})\\/([0-9]{0,4})$"),
    STREET_ADDRESS_PATTERN("^\\d+?[A-Za-z]*\\s\\w*\\s?\\w+?\\s\\w{2}\\w*\\s*\\w*$"),
    CITY_PATTERN("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$"),
    STATE_PATTERN("^((A[LKSZR])|(C[AOT])|(D[EC])|(F[ML])|(G[AU])|(HI)|(I[DLNA])|(K[SY])|(LA)|(M[EHDAINSOT])|(N[EVHJMYCD])|(MP)|(O[HKR])|(P[WAR])|(RI)|(S[CD])|(T[NX])|(UT)|(V[TIA])|(W[AVIY]))$"),
    ZIP_CODE_PATTERN("^\\d{5}(-\\d{4})?$"),
    MOBILE_NUMBER_PATTERN("^[2-9]\\d{2}-\\d{3}-\\d{4}$"),
    EMAIL_ADDRESS_PATTERN("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,3}$");

    private final String value;

}
