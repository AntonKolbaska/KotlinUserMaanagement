package com.andersen.usermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinUserManagerApplication

fun main(args: Array<String>) {
    runApplication<KotlinUserManagerApplication>(*args)
}
