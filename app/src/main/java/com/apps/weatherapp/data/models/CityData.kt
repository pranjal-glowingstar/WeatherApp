package com.apps.weatherapp.data.models

import com.google.gson.annotations.SerializedName

data class CityData(

    @SerializedName("Headline") var Headline: Headline? = Headline(),
    @SerializedName("DailyForecasts") var DailyForecasts: ArrayList<DailyForecasts> = arrayListOf()

)

data class Headline(

    @SerializedName("EffectiveDate") var EffectiveDate: String? = null,
    @SerializedName("EffectiveEpochDate") var EffectiveEpochDate: Int? = null,
    @SerializedName("Severity") var Severity: Int? = null,
    @SerializedName("Text") var Text: String? = null,
    @SerializedName("Category") var Category: String? = null,
    @SerializedName("EndDate") var EndDate: String? = null,
    @SerializedName("EndEpochDate") var EndEpochDate: Int? = null,
    @SerializedName("MobileLink") var MobileLink: String? = null,
    @SerializedName("Link") var Link: String? = null

)

data class Minimum(

    @SerializedName("Value") var Value: Int? = null,
    @SerializedName("Unit") var Unit: String? = null,
    @SerializedName("UnitType") var UnitType: Int? = null

)

data class Maximum(

    @SerializedName("Value") var Value: Int? = null,
    @SerializedName("Unit") var Unit: String? = null,
    @SerializedName("UnitType") var UnitType: Int? = null

)

data class Temperature(

    @SerializedName("Minimum") var Minimum: Minimum? = Minimum(),
    @SerializedName("Maximum") var Maximum: Maximum? = Maximum()

)

data class Day(

    @SerializedName("Icon") var Icon: Int? = null,
    @SerializedName("IconPhrase") var IconPhrase: String? = null,
    @SerializedName("HasPrecipitation") var HasPrecipitation: Boolean? = null

)

data class Night(

    @SerializedName("Icon") var Icon: Int? = null,
    @SerializedName("IconPhrase") var IconPhrase: String? = null,
    @SerializedName("HasPrecipitation") var HasPrecipitation: Boolean? = null,
    @SerializedName("PrecipitationType") var PrecipitationType: String? = null,
    @SerializedName("PrecipitationIntensity") var PrecipitationIntensity: String? = null

)

data class DailyForecasts(

    @SerializedName("Date") var Date: String? = null,
    @SerializedName("EpochDate") var EpochDate: Int? = null,
    @SerializedName("Temperature") var Temperature: Temperature? = Temperature(),
    @SerializedName("Day") var Day: Day? = Day(),
    @SerializedName("Night") var Night: Night? = Night(),
    @SerializedName("Sources") var Sources: ArrayList<String> = arrayListOf(),
    @SerializedName("MobileLink") var MobileLink: String? = null,
    @SerializedName("Link") var Link: String? = null

)