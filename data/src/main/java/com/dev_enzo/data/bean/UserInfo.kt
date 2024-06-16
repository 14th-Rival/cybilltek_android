package com.dev_enzo.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject

/**
 * This class will serve as the data holder
 * for the parsed parameters from the Query Result Data Class
 */
@Entity(tableName = "user_info")
data class UserInfo(
    val gender: String,
    val name: String,
    val location: String,
    val email: String,
    val login: String,
    val dob: String,
    val registered: String,
    val phone: String,
    val cell: String,
    val id: String,
    val picture: String,
    val nat: String,
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null
) {
    /**
     * This function will return the user's
     * name title
     */
    val getNameTitle: String
        get() {return JSONObject(name).optString("title")}

    /**
     * This function will return the user's
     * first name
     */
    val getFirstName: String
        get() {return JSONObject(name).optString("first")}

    /**
     * This function will return the user's
     * last name
     */
    val getLastName: String
        get() {return JSONObject(name).optString("last")}

    /**
     * This function will return the user's
     * detail in regards with their street details
     */
    private val getStreet: JSONObject
        get() {return JSONObject(location).optJSONObject("street")}

    /**
     * This function will return the user's
     * full detailed address
     */
    val getFullAddress: String
        get() {return "#${getStreetNo} ${getStreetName}, ${getCity}, ${getState}, ${getCountry}, ${getPostCode}"}

    /**
     * This function will return the user's
     * street number
     */
    val getStreetNo: Int
        get() {return JSONObject(getStreet.toString()).optInt("number")}

    /**
     * This function will return the user's
     * street name
     */
    val getStreetName: String
        get() {return JSONObject(getStreet.toString()).optString("name")}

    /**
     * This function will return the user's
     * city
     */
    val getCity: String
        get() {return JSONObject(location).optString("city")}

    /**
     * This function will return the user's
     * state
     */
    val getState: String
        get() {return JSONObject(location).optString("state")}

    /**
     * This function will return the user's
     * country
     */
    val getCountry: String
        get() {return JSONObject(location).optString("country")}

    /**
     * This function will return the user's
     * postal code
     */
    val getPostCode: Int
        get() {return JSONObject(location).optInt("postcode")}

    /**
     * This function will return the user's
     * coordinates details
     */
    private val getCoordinates: JSONObject
        get() {return JSONObject(location).optJSONObject("coordinates")}

    /**
     * This function will return the user's
     * latitude
     */
    val getLatitude: String
        get() {return JSONObject(getCoordinates.toString()).optString("latitude")}

    /**
     * This function will return the user's
     * longitude
     */
    val getLongitude: String
        get() {return JSONObject(getCoordinates.toString()).optString("longitude")}

    /**
     * This function will return the user's
     * timezome details
     */
    private val getTimeZone: JSONObject
        get() {return JSONObject(location).optJSONObject("timezone")}

    /**
     * This function will return the user's
     * timezone offset
     */
    val getOffset: String
        get() {return JSONObject(getTimeZone.toString()).getString("offset")}

    /**
     * This function will return the user's
     * timezone description
     */
    val getDescription: String
        get() {return JSONObject(getTimeZone.toString()).getString("description")}

    /**
     * This function will return the user's
     * date of birth
     */
    val getUserBirth: String
        get() {return JSONObject(dob).optString("date")}

    /**
     * This function will return the user's
     * birth age
     */
    val getUserAge: Int
        get() {return JSONObject(dob).optInt("age")}

    /**
     * This function will return the user's
     * registration date
     */
    val getUserRegistrationDate: String
        get() {return JSONObject(registered).optString("date")}

    /**
     * This function will return the user's
     * registration age
     */
    val getUserRegitrationAge: Int
        get() {return JSONObject(registered).optInt("age")}

    /**
     * This function will return the user's
     * profile image large
     */
    val getUserLargeImg: String
        get() {return JSONObject(picture).optString("large")}

    /**
     * This function will return the user's
     * profile image medium
     */
    val getUserMediumImg: String
        get() {return JSONObject(picture).optString("medium")}

    /**
     * This function will return the user's
     * profile thumb image
     */
    val getUserThumbImg: String
        get() {return JSONObject(picture).optString("thumbnail")}
}
