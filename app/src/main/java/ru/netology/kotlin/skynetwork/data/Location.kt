package ru.netology.kotlin.skynetwork.data

class Location(var lat: Double, var lng: Double)

infix fun Double.x(that: Double) = Location(this, that)
