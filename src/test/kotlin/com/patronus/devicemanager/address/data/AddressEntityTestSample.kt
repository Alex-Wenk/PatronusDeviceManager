package com.patronus.devicemanager.address.data

import java.util.*


fun getAddressEntitySample(): AddressEntity {
    return AddressEntity(
            UUID.randomUUID(),
            "address line 1",
            "address line 2",
            "postcode",
            "city",
            "zone code",
            "country"
    )
}
