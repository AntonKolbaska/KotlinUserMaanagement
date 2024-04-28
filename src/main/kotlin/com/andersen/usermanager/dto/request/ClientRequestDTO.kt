package com.andersen.usermanager.dto.request

import com.andersen.usermanager.entity.Gender
import com.fasterxml.jackson.annotation.JsonProperty

class ClientRequestDTO(
    @JsonProperty("firstname")
    var firstName: String,
    @JsonProperty("lastname")
    var lastName: String,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("gender")
    var gender: Gender?,
    @JsonProperty("job")
    var job: String?,
    @JsonProperty("position")
    var position: String?
)