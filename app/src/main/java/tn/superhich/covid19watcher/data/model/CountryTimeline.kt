package tn.superhich.covid19watcher.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CountryTimeline(
    @JsonProperty("new_daily_cases")
    val newDailyCases: Int,
    @JsonProperty("new_daily_deaths")
    val newDailyDeaths: Int,
    @JsonProperty("total_cases")
    val totalCases: Int,
    @JsonProperty("total_deaths")
    val totalDeaths: Int,
    @JsonProperty("total_recoveries")
    val totalRecoveries: Int
)
