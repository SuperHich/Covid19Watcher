package tn.superhich.covid19watcher.data.model

/*data class GlobalInfo (
    val count: Int,
    val date: Date,
    val result: Result
)

data class Result (
    val confirmed: Int,
    val deaths: Int,
    val recovered: Int
)
 */
data class GlobalInfo (
    val results: List<TotalInfo>
)

data class TotalInfo (
    val total_cases: Int,
    val total_recovered: Int,
    val total_unresolved: Int,
    val total_deaths: Int,
    val total_new_cases_today: Int,
    val total_new_deaths_today: Int,
    val total_serious_cases: Int,
    val total_affected_countries: Int
)
