package tn.superhich.covid19watcher.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CountryData (
    @JsonProperty("countrydata")
    val countrydata: List<CountryDataItem>
)

data class CountryDataItem (
    @JsonProperty("info")
    val info: Info,
    @JsonProperty("total_cases")
    val totalCases: Int,
    @JsonProperty("total_recovered")
    val totalRecovered: Int,
    @JsonProperty("total_unresolved")
    val totalUnresolved: Int,
    @JsonProperty("total_deaths")
    val totalDeaths: Int,
    @JsonProperty("total_new_cases_today")
    val totalNewCasesToday: Int,
    @JsonProperty("total_new_deaths_today")
    val totalNewDeathsToday: Int,
    @JsonProperty("total_serious_cases")
    val totalActiveCases: Int,
    @JsonProperty("total_active_cases")
    val totalSeriousCases: Int,
    @JsonProperty("total_danger_rank")
    val totalDangerRank: Int
)

data class Info (
    @JsonProperty("ourid")
    val ourid: Int,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("code")
    val code: String,
    @JsonProperty("source")
    val source: String
)