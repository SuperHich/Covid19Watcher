package tn.superhich.covid19watcher.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GlobalInfo (
    @JsonProperty("results")
    val results: List<TotalInfo>
)

data class TotalInfo (
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
    val totalSeriousCases: Int,
    @JsonProperty("total_affected_countries")
    val totalAffectedCountries: Int
)
