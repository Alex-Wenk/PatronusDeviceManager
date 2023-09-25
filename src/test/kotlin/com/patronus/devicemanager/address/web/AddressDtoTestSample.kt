package com.patronus.devicemanager.address.web


fun getAddressDtoTestSample(): AddressDTO {
    return AddressDTO(
            "address line 1",
            "address line 2",
            "postcode",
            "city",
            "zone code",
            "country"
    )
}
