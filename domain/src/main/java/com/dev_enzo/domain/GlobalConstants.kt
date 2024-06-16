package com.dev_enzo.domain

class GlobalConstants {
    companion object {
        val BASE_URL: String = "https://randomuser.me/api/"
        val GC_PAGE_SIZE: Int = 10
        // TAGS
        val TAG_MAIN_ACTIVITY: String = "TAG_MAIN_ACTIVITY"
        val TAG_USER_INFO: String = "TAG_USER_INFO"
        val TAG_HTTP_CLIENT: String = "TAG_HTTP_CLIENT"
        // DATA
        val USER_INFO_GENDER = "gender"
        val USER_INFO_NAME = "name"
        val USER_INFO_LOC = "location"
        val USER_INFO_EMAIL = "email"
        val USER_INFO_LOGIN = "login"
        val USER_INFO_DOB = "dob"
        val USER_INFO_REG = "registered"
        val USER_INFO_PHONE = "phone"
        val USER_INFO_CELL = "cell"
        val USER_INFO_ID = "id"
        val USER_INFO_PIC = "picture"
        val USER_INFO_NAT = "nat"
    }
}