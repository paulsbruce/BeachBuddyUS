package io.paulsbruce.dev.example.beachbuddyus.data

/**
 * Created by paul on 7/10/17.
 */
data class GeoLocation (val lat : Double, var long : Double) {


}

fun getDefaultLocation(): GeoLocation {
    return GeoLocation(43.2668002,-70.6441778);
}