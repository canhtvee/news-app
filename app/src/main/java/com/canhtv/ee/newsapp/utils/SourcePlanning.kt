package com.canhtv.ee.newsapp.utils

data class SourcePlanning(

    val tagList: List<String> = listOf<String>(
        "apple", "android", "huawei", "tesla", "bitcoin", "facebook", "twitter"
    ),

    val techSources: List<String> = listOf<String>(
        "engadget",
//        "hacker-news",
//        "recode",
        "techcrunch",
        "techradar",
//        "the-next-web",
//        "the-verge",
//        "wired",
        ),

    val businessSources: List<String> = listOf<String>(
//        "business-insider",
        //"australian-financial-review",
        //"business-insider-uk",
        "fortune",
        "the-wall-street-journal",
    ),

    val startup: List<String> = listOf<String>(
        "startup",
    ),

    val scienceSources: List<String> = listOf<String>(
        "new-scientist",
//        "next-big-future",
//        "physics",
//        "blackhole",
    ),

    val lifeSources: List<String> = listOf(
//        "the-lad-bible",
//        "talksport",
        "bbc-sport",
//        "bleacher-report",
        "espn",
//        "espn-cric-info",
        "fox-sports",
//        "buzzfeed",
//        "entertainment-weekly",
//        "mtv-news",
    ),
)
