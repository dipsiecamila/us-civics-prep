package com.example.data.datasource

import com.example.data.model.EnglishPracticeItem
import com.example.data.model.EnglishTestType

object EnglishTestDataSource {
    val readingSentences: List<EnglishPracticeItem> = listOf(
        EnglishPracticeItem(
            id = 1,
            type = EnglishTestType.READING,
            textEn = "George Washington was the first President.",
            textEs = "George Washington fue el primer Presidente.",
            category = "Presidents",
            phoneticOrTip = "Focus on: 'George Washington' and 'first President'"
        ),
        EnglishPracticeItem(
            id = 2,
            type = EnglishTestType.READING,
            textEn = "The United States has fifty states.",
            textEs = "Estados Unidos tiene cincuenta estados.",
            category = "Geography",
            phoneticOrTip = "Focus on: 'United States' and 'fifty'"
        ),
        EnglishPracticeItem(
            id = 3,
            type = EnglishTestType.READING,
            textEn = "Washington is the capital of the United States.",
            textEs = "Washington es la capital de Estados Unidos.",
            category = "Geography",
            phoneticOrTip = "Clear pronunciation of 'capital'"
        ),
        EnglishPracticeItem(
            id = 4,
            type = EnglishTestType.READING,
            textEn = "Citizens have the right to vote.",
            textEs = "Los ciudadanos tienen el derecho de votar.",
            category = "Civics",
            phoneticOrTip = "Pronounce 'Citizens' (SI-ti-zunz) and 'vote'"
        ),
        EnglishPracticeItem(
            id = 5,
            type = EnglishTestType.READING,
            textEn = "Abraham Lincoln was the sixteenth President.",
            textEs = "Abraham Lincoln fue el decimosexto Presidente.",
            category = "Presidents",
            phoneticOrTip = "Pronounce 'Lincoln' (LINK-un) and 'sixteenth'"
        ),
        EnglishPracticeItem(
            id = 6,
            type = EnglishTestType.READING,
            textEn = "Flag Day is in June.",
            textEs = "El Día de la Bandera es en junio.",
            category = "Holidays",
            phoneticOrTip = "Clear pronunciation of 'June'"
        ),
        EnglishPracticeItem(
            id = 7,
            type = EnglishTestType.READING,
            textEn = "Independence Day is in July.",
            textEs = "El Día de la Independencia es en julio.",
            category = "Holidays",
            phoneticOrTip = "Pronounce 'Independence' (in-duh-PEN-duns)"
        ),
        EnglishPracticeItem(
            id = 8,
            type = EnglishTestType.READING,
            textEn = "The American flag has red, white, and blue colors.",
            textEs = "La bandera estadounidense tiene colores rojo, blanco y azul.",
            category = "Symbols",
            phoneticOrTip = "Notice the comma pauses between colors"
        ),
        EnglishPracticeItem(
            id = 9,
            type = EnglishTestType.READING,
            textEn = "Congress meets in Washington, D.C.",
            textEs = "El Congreso se reúne en Washington, D.C.",
            category = "Government",
            phoneticOrTip = "Pronounce 'Congress' (KAHN-grus)"
        ),
        EnglishPracticeItem(
            id = 10,
            type = EnglishTestType.READING,
            textEn = "People want to live in freedom.",
            textEs = "La gente quiere vivir en libertad.",
            category = "Civics",
            phoneticOrTip = "Pronounce 'freedom' (FREE-dum)"
        )
    )

    val writingSentences: List<EnglishPracticeItem> = listOf(
        EnglishPracticeItem(
            id = 101,
            type = EnglishTestType.WRITING,
            textEn = "Washington is the Father of Our Country.",
            textEs = "Washington es el Padre de Nuestra Patria.",
            category = "History",
            phoneticOrTip = "Capitalize 'Washington', 'Father', 'Country'"
        ),
        EnglishPracticeItem(
            id = 102,
            type = EnglishTestType.WRITING,
            textEn = "Columbus Day is in October.",
            textEs = "El Día de la Raza es en octubre.",
            category = "Holidays",
            phoneticOrTip = "Capitalize 'Columbus Day' and 'October', don't forget the period"
        ),
        EnglishPracticeItem(
            id = 103,
            type = EnglishTestType.WRITING,
            textEn = "The White House is in Washington, D.C.",
            textEs = "La Casa Blanca está en Washington, D.C.",
            category = "Places",
            phoneticOrTip = "Capitalize 'White House' and 'Washington, D.C.'"
        ),
        EnglishPracticeItem(
            id = 104,
            type = EnglishTestType.WRITING,
            textEn = "Citizens vote for the President.",
            textEs = "Los ciudadanos votan por el Presidente.",
            category = "Civics",
            phoneticOrTip = "Check spelling: C-i-t-i-z-e-n-s"
        ),
        EnglishPracticeItem(
            id = 105,
            type = EnglishTestType.WRITING,
            textEn = "Thanksgiving is in November.",
            textEs = "El Día de Acción de Gracias es en noviembre.",
            category = "Holidays",
            phoneticOrTip = "Check spelling: T-h-a-n-k-s-g-i-v-i-n-g"
        ),
        EnglishPracticeItem(
            id = 106,
            type = EnglishTestType.WRITING,
            textEn = "We have fifty states in the United States.",
            textEs = "Tenemos cincuenta estados en los Estados Unidos.",
            category = "Geography",
            phoneticOrTip = "Spell 'fifty' and 'United States' correctly"
        ),
        EnglishPracticeItem(
            id = 107,
            type = EnglishTestType.WRITING,
            textEn = "President Washington was the first President.",
            textEs = "El Presidente Washington fue el primer Presidente.",
            category = "Presidents",
            phoneticOrTip = "Double check 'first' and 'President'"
        ),
        EnglishPracticeItem(
            id = 108,
            type = EnglishTestType.WRITING,
            textEn = "People come to America for freedom.",
            textEs = "La gente viene a América en busca de libertad.",
            category = "Civics",
            phoneticOrTip = "Capitalize 'America'"
        )
    )
}
