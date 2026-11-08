package com.example.data.datasource

import com.example.data.model.CivicsQuestion
import com.example.data.model.QuestionCategory

object CivicsQuestionsPart3 {
    val questions: List<CivicsQuestion> = listOf(
        CivicsQuestion(
            id = 71,
            questionEn = "What territory did the United States buy from France in 1803?",
            questionEs = "¿Qué territorio compró Estados Unidos a Francia en 1803?",
            acceptableAnswersEn = listOf(
                "The Louisiana Territory",
                "Louisiana"
            ),
            acceptableAnswersEs = listOf(
                "El territorio de Luisiana",
                "Luisiana"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            explanationEn = "The Louisiana Purchase doubled the physical size of the United States for 15 million dollars.",
            explanationEs = "La Compra de Luisiana duplicó el tamaño físico de Estados Unidos por 15 millones de dólares."
        ),
        CivicsQuestion(
            id = 72,
            questionEn = "Name one war fought by the United States in the 1800s.",
            questionEs = "Mencione una guerra en la que peleó Estados Unidos durante los años 1800.",
            acceptableAnswersEn = listOf(
                "War of 1812",
                "Mexican-American War",
                "Civil War",
                "Spanish-American War"
            ),
            acceptableAnswersEs = listOf(
                "Guerra de 1812",
                "Guerra México-Estadounidense",
                "Guerra Civil",
                "Guerra Hispano-Estadounidense"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            explanationEn = "The 1800s was defined by territorial expansion and intense conflicts.",
            explanationEs = "El siglo XIX estuvo marcado por la expansión territorial e intensos conflictos."
        ),
        CivicsQuestion(
            id = 73,
            questionEn = "Name the U.S. war between the North and the South.",
            questionEs = "Nombre la guerra de Estados Unidos entre el Norte y el Sur.",
            acceptableAnswersEn = listOf(
                "The Civil War",
                "The War between the States"
            ),
            acceptableAnswersEs = listOf(
                "La Guerra Civil",
                "La Guerra entre los Estados"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            explanationEn = "Fought from 1861 to 1865, it cost over 600,000 American lives.",
            explanationEs = "Librada entre 1861 y 1865, costó más de 600,000 vidas estadounidenses."
        ),
        CivicsQuestion(
            id = 74,
            questionEn = "Name one problem that led to the Civil War.",
            questionEs = "Mencione un problema que condujo a la Guerra Civil.",
            acceptableAnswersEn = listOf(
                "Slavery",
                "Economic reasons",
                "States' rights"
            ),
            acceptableAnswersEs = listOf(
                "Esclavitud",
                "Razones económicas",
                "Derechos de los estados"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            explanationEn = "Disagreements over the expansion of chattel slavery into western territories ignited the war.",
            explanationEs = "Los desacuerdos sobre la expansión de la esclavitud en los territorios occidentales desataron la guerra."
        ),
        CivicsQuestion(
            id = 75,
            questionEn = "What was one important thing that Abraham Lincoln did?",
            questionEs = "¿Cuál fue una cosa importante que hizo Abraham Lincoln?",
            acceptableAnswersEn = listOf(
                "Freed the slaves (Emancipation Proclamation)",
                "Saved (or preserved) the Union",
                "Led the United States during the Civil War"
            ),
            acceptableAnswersEs = listOf(
                "Liberó a los esclavos (Proclamación de Emancipación)",
                "Salvó (o preservó) la Unión",
                "Lideró a los Estados Unidos durante la Guerra Civil"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            isSenior6520 = true,
            explanationEn = "Lincoln served as 16th President from 1861 until his assassination in April 1865.",
            explanationEs = "Lincoln fue el decimosexto presidente desde 1861 hasta su asesinato en abril de 1865."
        ),
        CivicsQuestion(
            id = 76,
            questionEn = "What did the Emancipation Proclamation do?",
            questionEs = "¿Qué hizo la Proclamación de la Emancipación?",
            acceptableAnswersEn = listOf(
                "Freed the slaves",
                "Freed slaves in the Confederacy",
                "Freed slaves in the Confederate states",
                "Freed slaves in most Southern states"
            ),
            acceptableAnswersEs = listOf(
                "Liberó a los esclavos",
                "Liberó a los esclavos en la Confederación",
                "Liberó a los esclavos en los estados confederados",
                "Liberó a los esclavos en la mayoría de los estados del Sur"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            explanationEn = "Issued by Lincoln on January 1, 1863 as a wartime measure.",
            explanationEs = "Emitida por Lincoln el 1 de enero de 1863 como medida de guerra."
        ),
        CivicsQuestion(
            id = 77,
            questionEn = "What did Susan B. Anthony do?",
            questionEs = "¿Qué hizo Susan B. Anthony?",
            acceptableAnswersEn = listOf(
                "Fought for women's rights",
                "Fought for civil rights"
            ),
            acceptableAnswersEs = listOf(
                "Luchó por los derechos de la mujer",
                "Luchó por los derechos civiles"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "1800s",
            explanationEn = "She was a pioneer in the women's suffrage movement that led to the 19th Amendment.",
            explanationEs = "Fue pionera en el movimiento por el sufragio femenino que llevó a la Enmienda 19."
        ),
        CivicsQuestion(
            id = 78,
            questionEn = "Name one war fought by the United States in the 1900s.",
            questionEs = "Mencione una guerra en la que peleó Estados Unidos durante los años 1900.",
            acceptableAnswersEn = listOf(
                "World War I",
                "World War II",
                "Korean War",
                "Vietnam War",
                "(Persian) Gulf War"
            ),
            acceptableAnswersEs = listOf(
                "Primera Guerra Mundial",
                "Segunda Guerra Mundial",
                "Guerra de Corea",
                "Guerra de Vietnam",
                "Guerra del Golfo (Pérsico)"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            isSenior6520 = true,
            explanationEn = "American armed forces intervened globally across conflicts in the 20th century.",
            explanationEs = "Las fuerzas armadas de EE. UU. intervinieron a nivel global en el siglo XX."
        ),
        CivicsQuestion(
            id = 79,
            questionEn = "Who was President during World War I?",
            questionEs = "¿Quién fue el Presidente durante la Primera Guerra Mundial?",
            acceptableAnswersEn = listOf("(Woodrow) Wilson"),
            acceptableAnswersEs = listOf("(Woodrow) Wilson"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "Wilson championed the Fourteen Points and the League of Nations.",
            explanationEs = "Wilson impulsó los Catorce Puntos y la Sociedad de las Naciones."
        ),
        CivicsQuestion(
            id = 80,
            questionEn = "Who was President during the Great Depression and World War II?",
            questionEs = "¿Quién fue el Presidente durante la Gran Depresión y la Segunda Guerra Mundial?",
            acceptableAnswersEn = listOf("(Franklin) Roosevelt"),
            acceptableAnswersEs = listOf("(Franklin) Roosevelt"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "FDR was the only US President elected to four consecutive terms.",
            explanationEs = "FDR fue el único presidente estadounidense elegido para cuatro mandatos consecutivos."
        ),
        CivicsQuestion(
            id = 81,
            questionEn = "Who did the United States fight in World War II?",
            questionEs = "¿Contra qué países peleó Estados Unidos en la Segunda Guerra Mundial?",
            acceptableAnswersEn = listOf("Japan, Germany, and Italy"),
            acceptableAnswersEs = listOf("Japón, Alemania e Italia"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "These nations formed the Axis Powers against the Allied Powers.",
            explanationEs = "Estas naciones formaron las Potencias del Eje contra los Aliados."
        ),
        CivicsQuestion(
            id = 82,
            questionEn = "Before he was President, Eisenhower was a general. What war was he in?",
            questionEs = "Antes de ser Presidente, Eisenhower era general. ¿En qué guerra peleó?",
            acceptableAnswersEn = listOf("World War II"),
            acceptableAnswersEs = listOf("Segunda Guerra Mundial"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "Dwight D. Eisenhower was Supreme Commander of Allied Expeditionary Forces in Europe.",
            explanationEs = "Dwight D. Eisenhower fue el Comandante Supremo de las Fuerzas Expedicionarias Aliadas en Europa."
        ),
        CivicsQuestion(
            id = 83,
            questionEn = "During the Cold War, what was the main concern of the United States?",
            questionEs = "Durante la Guerra Fría, ¿cuál era la principal preocupación de los Estados Unidos?",
            acceptableAnswersEn = listOf("Communism"),
            acceptableAnswersEs = listOf("El comunismo"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "The geopolitical rivalry with the Soviet Union lasted from 1947 to 1991.",
            explanationEs = "La rivalidad geopolítica con la Unión Soviética duró de 1947 a 1991."
        ),
        CivicsQuestion(
            id = 84,
            questionEn = "What movement tried to end racial discrimination?",
            questionEs = "¿Qué movimiento intentó poner fin a la discriminación racial?",
            acceptableAnswersEn = listOf("Civil rights (movement)"),
            acceptableAnswersEs = listOf("El movimiento de derechos civiles"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "Led to landmark legislation such as the Civil Rights Act of 1964 and Voting Rights Act of 1965.",
            explanationEs = "Logró leyes históricas como la Ley de Derechos Civiles de 1964 y la Ley de Derecho al Voto de 1965."
        ),
        CivicsQuestion(
            id = 85,
            questionEn = "What did Martin Luther King, Jr. do?",
            questionEs = "¿Qué hizo Martin Luther King, Jr.?",
            acceptableAnswersEn = listOf(
                "Fought for civil rights",
                "Worked for equality for all Americans"
            ),
            acceptableAnswersEs = listOf(
                "Luchó por los derechos civiles",
                "Trabajó por la igualdad de todos los estadounidenses"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            isSenior6520 = true,
            explanationEn = "He delivered his iconic 'I Have a Dream' speech during the 1963 March on Washington.",
            explanationEs = "Pronunció su emblemático discurso 'Tengo un sueño' en la Marcha sobre Washington de 1963."
        ),
        CivicsQuestion(
            id = 86,
            questionEn = "What major event happened on September 11, 2001, in the United States?",
            questionEs = "¿Qué suceso de gran magnitud ocurrió el 11 de septiembre de 2001 en los Estados Unidos?",
            acceptableAnswersEn = listOf("Terrorists attacked the United States."),
            acceptableAnswersEs = listOf("Terroristas atacaron a los Estados Unidos."),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "Hijacked airliners struck the World Trade Center towers, the Pentagon, and a field in Shanksville, PA.",
            explanationEs = "Aviones secuestrados impactaron contra las Torres Gemelas, el Pentágono y un campo en Shanksville, PA."
        ),
        CivicsQuestion(
            id = 87,
            questionEn = "Name one American Indian tribe in the United States.",
            questionEs = "Mencione una tribu de indios americanos en los Estados Unidos.",
            acceptableAnswersEn = listOf(
                "Cherokee",
                "Navajo",
                "Sioux",
                "Chippewa",
                "Choctaw",
                "Pueblo",
                "Apache",
                "Iroquois",
                "Creek",
                "Blackfeet",
                "Seminole",
                "Cheyenne",
                "Arawak",
                "Shawnee",
                "Mohegan",
                "Huron",
                "Oneida",
                "Lakota",
                "Crow",
                "Teton",
                "Hopi",
                "Inuit"
            ),
            acceptableAnswersEs = listOf(
                "Cherokee",
                "Navajo",
                "Sioux",
                "Chippewa",
                "Choctaw",
                "Pueblo",
                "Apache",
                "Iroquois",
                "Creek",
                "Blackfeet",
                "Seminole",
                "Cheyenne",
                "Arawak",
                "Shawnee",
                "Mohegan",
                "Huron",
                "Oneida",
                "Lakota",
                "Crow",
                "Teton",
                "Hopi",
                "Inuit"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Recent American History",
            explanationEn = "There are 574 federally recognized Indian tribes in the United States.",
            explanationEs = "Hay 574 tribus indígenas reconocidas por el gobierno federal en los Estados Unidos."
        ),
        CivicsQuestion(
            id = 88,
            questionEn = "Name one of the two longest rivers in the United States.",
            questionEs = "Mencione uno de los dos ríos más largos de los Estados Unidos.",
            acceptableAnswersEn = listOf(
                "Missouri (River)",
                "Mississippi (River)"
            ),
            acceptableAnswersEs = listOf(
                "Río Misuri (Missouri)",
                "Río Misisipi (Mississippi)"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            explanationEn = "The Missouri River is approximately 2,341 miles long; the Mississippi is 2,202 miles.",
            explanationEs = "El río Misuri mide aproximadamente 2,341 millas de largo; el Misisipi 2,202 millas."
        ),
        CivicsQuestion(
            id = 89,
            questionEn = "What ocean is on the West Coast of the United States?",
            questionEs = "¿Qué océano está en la costa oeste de los Estados Unidos?",
            acceptableAnswersEn = listOf("Pacific (Ocean)"),
            acceptableAnswersEs = listOf("Océano Pacífico"),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            explanationEn = "Borders California, Oregon, Washington, Alaska, and Hawaii.",
            explanationEs = "Baña las costas de California, Oregón, Washington, Alaska y Hawái."
        ),
        CivicsQuestion(
            id = 90,
            questionEn = "What ocean is on the East Coast of the United States?",
            questionEs = "¿Qué océano está en la costa este de los Estados Unidos?",
            acceptableAnswersEn = listOf("Atlantic (Ocean)"),
            acceptableAnswersEs = listOf("Océano Atlántico"),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            explanationEn = "Spans from Maine in the north to Florida in the south.",
            explanationEs = "Abarca desde Maine en el norte hasta Florida en el sur."
        ),
        CivicsQuestion(
            id = 91,
            questionEn = "Name one U.S. territory.",
            questionEs = "Nombre un territorio de los Estados Unidos.",
            acceptableAnswersEn = listOf(
                "Puerto Rico",
                "U.S. Virgin Islands",
                "American Samoa",
                "Northern Mariana Islands",
                "Guam"
            ),
            acceptableAnswersEs = listOf(
                "Puerto Rico",
                "Islas Vírgenes de EE. UU.",
                "Samoa Americana",
                "Islas Marianas del Norte",
                "Guam"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            explanationEn = "These 5 inhabited territories are under US sovereign jurisdiction.",
            explanationEs = "Estos 5 territorios habitados están bajo la soberanía de Estados Unidos."
        ),
        CivicsQuestion(
            id = 92,
            questionEn = "Name one state that borders Canada.",
            questionEs = "Mencione un estado que tiene frontera con Canadá.",
            acceptableAnswersEn = listOf(
                "Maine",
                "New Hampshire",
                "Vermont",
                "New York",
                "Pennsylvania",
                "Ohio",
                "Michigan",
                "Minnesota",
                "North Dakota",
                "Montana",
                "Idaho",
                "Washington",
                "Alaska"
            ),
            acceptableAnswersEs = listOf(
                "Maine",
                "Nueva Hampshire",
                "Vermont",
                "Nueva York",
                "Pensilvania",
                "Ohio",
                "Míchigan",
                "Minnesota",
                "Dakota del Norte",
                "Montana",
                "Idaho",
                "Washington",
                "Alaska"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            explanationEn = "Thirteen states share the world's longest non-militarized international border (5,525 miles).",
            explanationEs = "Trece estados comparten la frontera internacional no militarizada más larga del mundo."
        ),
        CivicsQuestion(
            id = 93,
            questionEn = "Name one state that borders Mexico.",
            questionEs = "Mencione un estado que tiene frontera con México.",
            acceptableAnswersEn = listOf(
                "California",
                "Arizona",
                "New Mexico",
                "Texas"
            ),
            acceptableAnswersEs = listOf(
                "California",
                "Arizona",
                "Nuevo México",
                "Texas"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            explanationEn = "Four states share a 1,954-mile southern border with Mexico.",
            explanationEs = "Cuatro estados comparten una frontera sur de 1,954 millas con México."
        ),
        CivicsQuestion(
            id = 94,
            questionEn = "What is the capital of the United States?",
            questionEs = "¿Cuál es la capital de los Estados Unidos?",
            acceptableAnswersEn = listOf("Washington, D.C."),
            acceptableAnswersEs = listOf("Washington, D.C."),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Geography",
            isSenior6520 = true,
            explanationEn = "Established as a federal district under Article I of the Constitution.",
            explanationEs = "Establecido como distrito federal bajo el Artículo I de la Constitución."
        ),
        CivicsQuestion(
            id = 95,
            questionEn = "Where is the Statue of Liberty?",
            questionEs = "¿Dónde está la Estatua de la Libertad?",
            acceptableAnswersEn = listOf(
                "New York (Harbor)",
                "Liberty Island",
                "Also acceptable are New Jersey, near New York City, and on the Hudson (River)"
            ),
            acceptableAnswersEs = listOf(
                "Puerto de Nueva York (New York Harbor)",
                "Isla de la Libertad (Liberty Island)"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Symbols",
            isSenior6520 = true,
            explanationEn = "A gift from France dedicated in 1886, welcoming immigrants to America.",
            explanationEs = "Un regalo de Francia inaugurado en 1886, dando la bienvenida a los inmigrantes."
        ),
        CivicsQuestion(
            id = 96,
            questionEn = "Why does the flag have 13 stripes?",
            questionEs = "¿Por qué la bandera tiene 13 franjas?",
            acceptableAnswersEn = listOf(
                "(Because there were) 13 original colonies",
                "(Because the stripes) represent the original colonies"
            ),
            acceptableAnswersEs = listOf(
                "(Porque representaban) las 13 colonias originales"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Symbols",
            explanationEn = "Seven red stripes and six white stripes represent the 13 founding colonies.",
            explanationEs = "Siete franjas rojas y seis blancas representan las 13 colonias fundadoras."
        ),
        CivicsQuestion(
            id = 97,
            questionEn = "Why does the flag have 50 stars?",
            questionEs = "¿Por qué la bandera tiene 50 estrellas?",
            acceptableAnswersEn = listOf(
                "(Because there is) one star for each state",
                "(Because each star) represents a state",
                "(Because there are) 50 states"
            ),
            acceptableAnswersEs = listOf(
                "(Porque hay) una estrella por cada estado",
                "(Porque cada estrella) representa un estado",
                "(Porque hay) 50 estados"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Symbols",
            isSenior6520 = true,
            explanationEn = "The 50th star was added on July 4, 1960, after Hawaii was admitted to the Union.",
            explanationEs = "La estrella número 50 se agregó el 4 de julio de 1960 tras la admisión de Hawái."
        ),
        CivicsQuestion(
            id = 98,
            questionEn = "What is the name of the national anthem?",
            questionEs = "¿Cuál es el nombre del himno nacional?",
            acceptableAnswersEn = listOf("The Star-Spangled Banner"),
            acceptableAnswersEs = listOf("The Star-Spangled Banner (La bandera de estrellas centelleantes)"),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Symbols",
            explanationEn = "Written by Francis Scott Key in 1814 during the Battle of Fort McHenry.",
            explanationEs = "Escrito por Francis Scott Key en 1814 durante la Batalla de Fort McHenry."
        ),
        CivicsQuestion(
            id = 99,
            questionEn = "When do we celebrate Independence Day?",
            questionEs = "¿Cuándo celebramos el Día de la Independencia?",
            acceptableAnswersEn = listOf("July 4"),
            acceptableAnswersEs = listOf("El 4 de julio"),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Holidays",
            isSenior6520 = true,
            explanationEn = "Commemorates the adoption of the Declaration of Independence in 1776.",
            explanationEs = "Conmemora la adopción de la Declaración de Independencia en 1776."
        ),
        CivicsQuestion(
            id = 100,
            questionEn = "Name two national U.S. holidays.",
            questionEs = "Mencione dos días feriados nacionales de los Estados Unidos.",
            acceptableAnswersEn = listOf(
                "New Year's Day",
                "Martin Luther King, Jr. Day",
                "Presidents' Day",
                "Memorial Day",
                "Juneteenth National Independence Day",
                "Independence Day",
                "Labor Day",
                "Columbus Day",
                "Veterans Day",
                "Thanksgiving",
                "Christmas"
            ),
            acceptableAnswersEs = listOf(
                "Día de Año Nuevo",
                "Día de Martin Luther King, Jr.",
                "Día de los Presidentes",
                "Día de la Recordación (Memorial Day)",
                "Día de Juneteenth",
                "Día de la Independencia",
                "Día del Trabajo (Labor Day)",
                "Día de la Raza (Columbus Day)",
                "Día de los Veteranos",
                "Día de Acción de Gracias (Thanksgiving)",
                "Navidad"
            ),
            category = QuestionCategory.INTEGRATED_CIVICS,
            subcategory = "Holidays",
            isSenior6520 = true,
            explanationEn = "Federal holidays are established by Congress for federal employees and banks.",
            explanationEs = "Los feriados federales son establecidos por el Congreso para empleados federales y bancos."
        )
    )
}
