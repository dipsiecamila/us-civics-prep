package com.example.data.model

data class StateInfo(
    val code: String,
    val name: String,
    val capital: String,
    val governor: String,
    val senators: List<String>,
    val note: String = ""
)

object StateDirectory {
    val states: List<StateInfo> = listOf(
        StateInfo("AL", "Alabama", "Montgomery", "Kay Ivey", listOf("Tommy Tuberville", "Katie Britt")),
        StateInfo("AK", "Alaska", "Juneau", "Mike Dunleavy", listOf("Lisa Murkowski", "Dan Sullivan")),
        StateInfo("AZ", "Arizona", "Phoenix", "Katie Hobbs", listOf("Mark Kelly", "Kyrsten Sinema")),
        StateInfo("AR", "Arkansas", "Little Rock", "Sarah Huckabee Sanders", listOf("John Boozman", "Tom Cotton")),
        StateInfo("CA", "California", "Sacramento", "Gavin Newsom", listOf("Alex Padilla", "Laphonza Butler")),
        StateInfo("CO", "Colorado", "Denver", "Jared Polis", listOf("Michael Bennet", "John Hickenlooper")),
        StateInfo("CT", "Connecticut", "Hartford", "Ned Lamont", listOf("Richard Blumenthal", "Chris Murphy")),
        StateInfo("DE", "Delaware", "Dover", "John Carney", listOf("Tom Carper", "Chris Coons")),
        StateInfo("FL", "Florida", "Tallahassee", "Ron DeSantis", listOf("Marco Rubio", "Rick Scott")),
        StateInfo("GA", "Georgia", "Atlanta", "Brian Kemp", listOf("Jon Ossoff", "Raphael Warnock")),
        StateInfo("HI", "Hawaii", "Honolulu", "Josh Green", listOf("Brian Schatz", "Mazie Hirono")),
        StateInfo("ID", "Idaho", "Boise", "Brad Little", listOf("Mike Crapo", "Jim Risch")),
        StateInfo("IL", "Illinois", "Springfield", "JB Pritzker", listOf("Dick Durbin", "Tammy Duckworth")),
        StateInfo("IN", "Indiana", "Indianapolis", "Eric Holcomb", listOf("Todd Young", "Mike Braun")),
        StateInfo("IA", "Iowa", "Des Moines", "Kim Reynolds", listOf("Chuck Grassley", "Joni Ernst")),
        StateInfo("KS", "Kansas", "Topeka", "Laura Kelly", listOf("Jerry Moran", "Roger Marshall")),
        StateInfo("KY", "Kentucky", "Frankfort", "Andy Beshear", listOf("Mitch McConnell", "Rand Paul")),
        StateInfo("LA", "Louisiana", "Baton Rouge", "Jeff Landry", listOf("Bill Cassidy", "John Kennedy")),
        StateInfo("ME", "Maine", "Augusta", "Janet Mills", listOf("Susan Collins", "Angus King")),
        StateInfo("MD", "Maryland", "Annapolis", "Wes Moore", listOf("Ben Cardin", "Chris Van Hollen")),
        StateInfo("MA", "Massachusetts", "Boston", "Maura Healey", listOf("Elizabeth Warren", "Ed Markey")),
        StateInfo("MI", "Michigan", "Lansing", "Gretchen Whitmer", listOf("Debbie Stabenow", "Gary Peters")),
        StateInfo("MN", "Minnesota", "St. Paul", "Tim Walz", listOf("Amy Klobuchar", "Tina Smith")),
        StateInfo("MS", "Mississippi", "Jackson", "Tate Reeves", listOf("Roger Wicker", "Cindy Hyde-Smith")),
        StateInfo("MO", "Missouri", "Jefferson City", "Mike Parson", listOf("Josh Hawley", "Eric Schmitt")),
        StateInfo("MT", "Montana", "Helena", "Greg Gianforte", listOf("Jon Tester", "Steve Daines")),
        StateInfo("NE", "Nebraska", "Lincoln", "Jim Pillen", listOf("Deb Fischer", "Pete Ricketts")),
        StateInfo("NV", "Nevada", "Carson City", "Joe Lombardo", listOf("Catherine Cortez Masto", "Jacky Rosen")),
        StateInfo("NH", "New Hampshire", "Concord", "Chris Sununu", listOf("Jeanne Shaheen", "Maggie Hassan")),
        StateInfo("NJ", "New Jersey", "Trenton", "Phil Murphy", listOf("Cory Booker", "George Helmy")),
        StateInfo("NM", "New Mexico", "Santa Fe", "Michelle Lujan Grisham", listOf("Martin Heinrich", "Ben Ray Luján")),
        StateInfo("NY", "New York", "Albany", "Kathy Hochul", listOf("Chuck Schumer", "Kirsten Gillibrand")),
        StateInfo("NC", "North Carolina", "Raleigh", "Roy Cooper", listOf("Thom Tillis", "Ted Budd")),
        StateInfo("ND", "North Dakota", "Bismarck", "Doug Burgum", listOf("John Hoeven", "Kevin Cramer")),
        StateInfo("OH", "Ohio", "Columbus", "Mike DeWine", listOf("Sherrod Brown", "JD Vance")),
        StateInfo("OK", "Oklahoma", "Oklahoma City", "Kevin Stitt", listOf("James Lankford", "Markwayne Mullin")),
        StateInfo("OR", "Oregon", "Salem", "Tina Kotek", listOf("Ron Wyden", "Jeff Merkley")),
        StateInfo("PA", "Pennsylvania", "Harrisburg", "Josh Shapiro", listOf("Bob Casey Jr.", "John Fetterman")),
        StateInfo("RI", "Rhode Island", "Providence", "Dan McKee", listOf("Jack Reed", "Sheldon Whitehouse")),
        StateInfo("SC", "South Carolina", "Columbia", "Henry McMaster", listOf("Lindsey Graham", "Tim Scott")),
        StateInfo("SD", "South Dakota", "Pierre", "Kristi Noem", listOf("John Thune", "Mike Rounds")),
        StateInfo("TN", "Tennessee", "Nashville", "Bill Lee", listOf("Marsha Blackburn", "Bill Hagerty")),
        StateInfo("TX", "Texas", "Austin", "Greg Abbott", listOf("John Cornyn", "Ted Cruz")),
        StateInfo("UT", "Utah", "Salt Lake City", "Spencer Cox", listOf("Mike Lee", "Mitt Romney")),
        StateInfo("VT", "Vermont", "Montpelier", "Phil Scott", listOf("Bernie Sanders", "Peter Welch")),
        StateInfo("VA", "Virginia", "Richmond", "Glenn Youngkin", listOf("Mark Warner", "Tim Kaine")),
        StateInfo("WA", "Washington", "Olympia", "Jay Inslee", listOf("Patty Murray", "Maria Cantwell")),
        StateInfo("WV", "West Virginia", "Charleston", "Jim Justice", listOf("Joe Manchin", "Shelley Moore Capito")),
        StateInfo("WI", "Wisconsin", "Madison", "Tony Evers", listOf("Ron Johnson", "Tammy Baldwin")),
        StateInfo("WY", "Wyoming", "Cheyenne", "Mark Gordon", listOf("John Barrasso", "Cynthia Lummis")),
        StateInfo("DC", "District of Columbia", "Washington, D.C.", "Muriel Bowser (Mayor)", listOf("D.C. has no voting U.S. Senators"), "Residents of D.C. should answer that D.C. has no U.S. Senators."),
        StateInfo("PR", "Puerto Rico", "San Juan", "Pedro Pierluisi", listOf("Puerto Rico has no voting U.S. Senators"), "Territories have no U.S. Senators.")
    )

    fun getState(code: String): StateInfo? = states.find { it.code.equals(code, ignoreCase = true) }
}
