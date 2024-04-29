package com.andersen.usermanager.service.impl

import com.andersen.usermanager.exception.GenderUndefinedException
import com.andersen.usermanager.exception.message.ExceptionMessage
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL

@Service
class GenderApiServiceImpl {
    fun defineClientGender(firstName: String): String {
        val apiUrl = "https://api.genderize.io/?name=$firstName"
        val response = URL(apiUrl).readText()
        val json = JSONObject(response)
        val probability = json.getDouble("probability")

        if (probability >= 0.8) {
            return json.getString("gender").uppercase()
        } else {
            throw GenderUndefinedException(
                ExceptionMessage.GENDER_NOT_DEFINED
                    .toString()
                    .format(firstName)
            )
        }
    }
}