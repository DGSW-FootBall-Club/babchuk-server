package com.rice.babchuk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class babchukApplication

fun main(args: Array<String>) {
	runApplication<babchukApplication>(*args)
}
