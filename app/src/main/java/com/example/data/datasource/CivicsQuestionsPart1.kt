package com.example.data.datasource

import com.example.data.model.CivicsQuestion
import com.example.data.model.QuestionCategory

object CivicsQuestionsPart1 {
    val questions: List<CivicsQuestion> = listOf(
        CivicsQuestion(
            id = 1,
            questionEn = "What is the supreme law of the land?",
            questionEs = "¿Cuál es la ley suprema de la nación?",
            acceptableAnswersEn = listOf("The Constitution"),
            acceptableAnswersEs = listOf("La Constitución"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            isSenior6520 = true,
            explanationEn = "The Constitution sets up the government, defines its powers, and protects the basic rights of Americans.",
            explanationEs = "La Constitución establece el gobierno, define sus poderes y protege los derechos fundamentales de los estadounidenses."
        ),
        CivicsQuestion(
            id = 2,
            questionEn = "What does the Constitution do?",
            questionEs = "¿Qué hace la Constitución?",
            acceptableAnswersEn = listOf(
                "Sets up the government",
                "Defines the government",
                "Protects basic rights of Americans"
            ),
            acceptableAnswersEs = listOf(
                "Establece el gobierno",
                "Define el gobierno",
                "Protege los derechos básicos de los estadounidenses"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "The Constitution acts as the framework for the federal government.",
            explanationEs = "La Constitución actúa como el marco del gobierno federal."
        ),
        CivicsQuestion(
            id = 3,
            questionEn = "The idea of self-government is in the first three words of the Constitution. What are these words?",
            questionEs = "La idea de autogobierno está en las primeras tres palabras de la Constitución. ¿Cuáles son estas palabras?",
            acceptableAnswersEn = listOf("We the People"),
            acceptableAnswersEs = listOf("Nosotros, el pueblo (We the People)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "These famous three words signify that power comes from the citizens.",
            explanationEs = "Estas tres famosas palabras significan que el poder proviene de los ciudadanos."
        ),
        CivicsQuestion(
            id = 4,
            questionEn = "What is an amendment?",
            questionEs = "¿Qué es una enmienda?",
            acceptableAnswersEn = listOf(
                "A change (to the Constitution)",
                "An addition (to the Constitution)"
            ),
            acceptableAnswersEs = listOf(
                "Un cambio (a la Constitución)",
                "Una adición (a la Constitución)"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "An amendment alters or adds to the United States Constitution.",
            explanationEs = "Una enmienda modifica o agrega a la Constitución de los Estados Unidos."
        ),
        CivicsQuestion(
            id = 5,
            questionEn = "What do we call the first ten amendments to the Constitution?",
            questionEs = "¿Cómo se llaman las primeras diez enmiendas a la Constitución?",
            acceptableAnswersEn = listOf("The Bill of Rights"),
            acceptableAnswersEs = listOf("La Carta de Derechos (The Bill of Rights)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            isSenior6520 = true,
            explanationEn = "The Bill of Rights guarantees individual liberties such as freedom of speech, religion, and the press.",
            explanationEs = "La Carta de Derechos garantiza libertades individuales como la libertad de expresión, religión y prensa."
        ),
        CivicsQuestion(
            id = 6,
            questionEn = "What is one right or freedom from the First Amendment?",
            questionEs = "¿Cuál es un derecho o libertad de la Primera Enmienda?",
            acceptableAnswersEn = listOf(
                "Speech",
                "Religion",
                "Assembly",
                "Press",
                "Petition the government"
            ),
            acceptableAnswersEs = listOf(
                "Libertad de expresión",
                "Libertad de religión",
                "Libertad de reunión",
                "Libertad de prensa",
                "Derecho a peticionar al gobierno"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            isSenior6520 = true,
            explanationEn = "The First Amendment protects five fundamental personal freedoms.",
            explanationEs = "La Primera Enmienda protege cinco libertades personales fundamentales."
        ),
        CivicsQuestion(
            id = 7,
            questionEn = "How many amendments does the Constitution have?",
            questionEs = "¿Cuántas enmiendas tiene la Constitución?",
            acceptableAnswersEn = listOf("Twenty-seven (27)"),
            acceptableAnswersEs = listOf("Veintisiete (27)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "The 27th Amendment was ratified in 1992, over 200 years after it was proposed.",
            explanationEs = "La Enmienda 27 fue ratificada en 1992, más de 200 años después de ser propuesta."
        ),
        CivicsQuestion(
            id = 8,
            questionEn = "What did the Declaration of Independence do?",
            questionEs = "¿Qué hizo la Declaración de Independencia?",
            acceptableAnswersEn = listOf(
                "Announced our independence (from Great Britain)",
                "Declared our independence (from Great Britain)",
                "Said that the United States is free (from Great Britain)"
            ),
            acceptableAnswersEs = listOf(
                "Anunció nuestra independencia (de Gran Bretaña)",
                "Declaró nuestra independencia (de Gran Bretaña)",
                "Dijo que Estados Unidos es libre (de Gran Bretaña)"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "Adopted on July 4, 1776, it officially cut ties with King George III.",
            explanationEs = "Adoptada el 4 de julio de 1776, rompió oficialmente los lazos con el rey Jorge III."
        ),
        CivicsQuestion(
            id = 9,
            questionEn = "What are two rights in the Declaration of Independence?",
            questionEs = "¿Cuáles son dos derechos en la Declaración de Independencia?",
            acceptableAnswersEn = listOf(
                "Life",
                "Liberty",
                "Pursuit of happiness"
            ),
            acceptableAnswersEs = listOf(
                "La vida",
                "La libertad",
                "La búsqueda de la felicidad"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "Thomas Jefferson penned these unalienable rights in 1776.",
            explanationEs = "Thomas Jefferson escribió estos derechos inalienables en 1776."
        ),
        CivicsQuestion(
            id = 10,
            questionEn = "What is freedom of religion?",
            questionEs = "¿Qué es la libertad de religión?",
            acceptableAnswersEn = listOf(
                "You can practice any religion, or not practice a religion."
            ),
            acceptableAnswersEs = listOf(
                "Se puede practicar cualquier religión o no practicar ninguna."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "Congress cannot establish a national religion or prohibit the free exercise thereof.",
            explanationEs = "El Congreso no puede establecer una religión nacional ni prohibir su libre ejercicio."
        ),
        CivicsQuestion(
            id = 11,
            questionEn = "What is the economic system in the United States?",
            questionEs = "¿Cuál es el sistema económico de los Estados Unidos?",
            acceptableAnswersEn = listOf(
                "Capitalist economy",
                "Market economy"
            ),
            acceptableAnswersEs = listOf(
                "Economía capitalista",
                "Economía de mercado"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            isSenior6520 = true,
            explanationEn = "Private businesses and consumers determine goods, services, and pricing.",
            explanationEs = "Las empresas privadas y los consumidores determinan bienes, servicios y precios."
        ),
        CivicsQuestion(
            id = 12,
            questionEn = "What is the \"rule of law\"?",
            questionEs = "¿En qué consiste el \"imperio de la ley\" (rule of law)?",
            acceptableAnswersEn = listOf(
                "Everyone must follow the law.",
                "Leaders must obey the law.",
                "Government must obey the law.",
                "No one is above the law."
            ),
            acceptableAnswersEs = listOf(
                "Todos deben obedecer la ley.",
                "Los líderes deben obedecer la ley.",
                "El gobierno debe obedecer la ley.",
                "Nadie está por encima de la ley."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Principles of American Democracy",
            explanationEn = "Equal justice under law applies to everyone including the President.",
            explanationEs = "La justicia igualitaria ante la ley se aplica a todos, incluido el Presidente."
        ),
        CivicsQuestion(
            id = 13,
            questionEn = "Name one branch or part of the government.",
            questionEs = "Mencione una rama o parte del gobierno.",
            acceptableAnswersEn = listOf(
                "Congress",
                "Legislative",
                "President",
                "Executive",
                "The courts",
                "Judicial"
            ),
            acceptableAnswersEs = listOf(
                "Congreso",
                "Poder legislativo",
                "Presidente",
                "Poder ejecutivo",
                "Los tribunales",
                "Poder judicial"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            explanationEn = "The US government is divided into three co-equal branches to prevent tyranny.",
            explanationEs = "El gobierno de EE. UU. se divide en tres ramas coiguales para evitar la tiranía."
        ),
        CivicsQuestion(
            id = 14,
            questionEn = "What stops one branch of government from becoming too powerful?",
            questionEs = "¿Qué evita que una rama del gobierno se vuelva demasiado poderosa?",
            acceptableAnswersEn = listOf(
                "Checks and balances",
                "Separation of powers"
            ),
            acceptableAnswersEs = listOf(
                "Contrapesos y equilibrios (checks and balances)",
                "Separación de poderes"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Each branch has specific powers that balance and check the other branches.",
            explanationEs = "Cada rama tiene poderes específicos que equilibran y controlan a las demás."
        ),
        CivicsQuestion(
            id = 15,
            questionEn = "Who is in charge of the executive branch?",
            questionEs = "¿Quién está a cargo de la rama ejecutiva?",
            acceptableAnswersEn = listOf("The President"),
            acceptableAnswersEs = listOf("El Presidente"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "The President is the head of state, head of government, and Commander in Chief.",
            explanationEs = "El Presidente es el jefe de estado, jefe de gobierno y Comandante en Jefe."
        ),
        CivicsQuestion(
            id = 16,
            questionEn = "Who makes federal laws?",
            questionEs = "¿Quién crea las leyes federales?",
            acceptableAnswersEn = listOf(
                "Congress",
                "Senate and House (of Representatives)",
                "(U.S. or national) legislature"
            ),
            acceptableAnswersEs = listOf(
                "El Congreso",
                "El Senado y la Cámara (de Representantes)",
                "La legislatura (nacional o de EE. UU.)"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Article I of the Constitution grants all legislative powers to Congress.",
            explanationEs = "El Artículo I de la Constitución otorga todos los poderes legislativos al Congreso."
        ),
        CivicsQuestion(
            id = 17,
            questionEn = "What are the two parts of the U.S. Congress?",
            questionEs = "¿Cuáles son las dos partes que componen el Congreso de los Estados Unidos?",
            acceptableAnswersEn = listOf(
                "The Senate and House (of Representatives)"
            ),
            acceptableAnswersEs = listOf(
                "El Senado y la Cámara (de Representantes)"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            explanationEn = "Congress is a bicameral legislature created by the Great Compromise of 1787.",
            explanationEs = "El Congreso es una legislatura bicameral creada por el Gran Compromiso de 1787."
        ),
        CivicsQuestion(
            id = 18,
            questionEn = "How many U.S. Senators are there?",
            questionEs = "¿Cuántos Senadores de los Estados Unidos hay?",
            acceptableAnswersEn = listOf("One hundred (100)"),
            acceptableAnswersEs = listOf("Cien (100)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Every state has exactly 2 senators, ensuring equal state representation.",
            explanationEs = "Cada estado tiene exactamente 2 senadores, garantizando una representación estatal igual."
        ),
        CivicsQuestion(
            id = 19,
            questionEn = "We elect a U.S. Senator for how many years?",
            questionEs = "¿De cuántos años es el término de elección de un Senador de los Estados Unidos?",
            acceptableAnswersEn = listOf("Six (6) years"),
            acceptableAnswersEs = listOf("Seis (6) años"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "One-third of the Senate is elected every two years.",
            explanationEs = "Un tercio del Senado es elegido cada dos años."
        ),
        CivicsQuestion(
            id = 20,
            questionEn = "Who is one of your state's U.S. Senators now?",
            questionEs = "¿Quién es uno de los Senadores de su estado en este momento?",
            acceptableAnswersEn = listOf(
                "Answers will vary by state. District of Columbia residents and US territories should answer that D.C. (or territory) has no U.S. Senators."
            ),
            acceptableAnswersEs = listOf(
                "Las respuestas varían según el estado. Los residentes de D.C. o territorios deben responder que no tienen senadores."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            isDynamicOfficial = true,
            explanationEn = "You can select your state in the 'My State Officials' section to see your two senators.",
            explanationEs = "Puede seleccionar su estado en la sección 'Funcionarios de Mi Estado' para ver sus dos senadores."
        ),
        CivicsQuestion(
            id = 21,
            questionEn = "The House of Representatives has how many voting members?",
            questionEs = "¿Cuántos miembros votantes tiene la Cámara de Representantes?",
            acceptableAnswersEn = listOf("Four hundred thirty-five (435)"),
            acceptableAnswersEs = listOf("Cuatrocientos treinta y cinco (435)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "The number of representatives is based on state population from the decennial census.",
            explanationEs = "El número de representantes se basa en la población de cada estado del censo decenal."
        ),
        CivicsQuestion(
            id = 22,
            questionEn = "We elect a U.S. Representative for how many years?",
            questionEs = "¿De cuántos años es el término de elección de un Representante de los Estados Unidos?",
            acceptableAnswersEn = listOf("Two (2) years"),
            acceptableAnswersEs = listOf("Dos (2) años"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "All 435 members are up for election every even-numbered year.",
            explanationEs = "Los 435 miembros se postulan a elección cada año de número par."
        ),
        CivicsQuestion(
            id = 23,
            questionEn = "Name your U.S. Representative.",
            questionEs = "Mencione el nombre de su Representante de los Estados Unidos.",
            acceptableAnswersEn = listOf(
                "Answers will vary by congressional district. Residents of territories or D.C. may provide their non-voting delegate or resident commissioner."
            ),
            acceptableAnswersEs = listOf(
                "Las respuestas varían según el distrito congresional."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isDynamicOfficial = true,
            explanationEn = "Find your Representative by entering your zip code on house.gov.",
            explanationEs = "Encuentre su representante ingresando su código postal en house.gov."
        ),
        CivicsQuestion(
            id = 24,
            questionEn = "Who does a U.S. Senator represent?",
            questionEs = "¿A quiénes representa un Senador de los Estados Unidos?",
            acceptableAnswersEn = listOf("All people of the state"),
            acceptableAnswersEs = listOf("A todas las personas del estado"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Senators represent the entire population of their state, not just their party.",
            explanationEs = "Los senadores representan a toda la población de su estado, no solo a su partido."
        ),
        CivicsQuestion(
            id = 25,
            questionEn = "Why do some states have more Representatives than other states?",
            questionEs = "¿Por qué tienen algunos estados más Representantes que otros?",
            acceptableAnswersEn = listOf(
                "(Because of) the state's population",
                "(Because) they have more people",
                "(Because) some states have more people"
            ),
            acceptableAnswersEs = listOf(
                "(Debido a) la población del estado",
                "(Porque) tienen más gente",
                "(Porque) algunos estados tienen más población"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Proportional representation ensures larger populations have a proportionate voice.",
            explanationEs = "La representación proporcional asegura que las poblaciones más grandes tengan voz proporcional."
        ),
        CivicsQuestion(
            id = 26,
            questionEn = "We elect a President for how many years?",
            questionEs = "¿De cuántos años es el término de elección de un Presidente?",
            acceptableAnswersEn = listOf("Four (4) years"),
            acceptableAnswersEs = listOf("Cuatro (4) años"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Under the 22nd Amendment, a President is limited to two four-year terms.",
            explanationEs = "Bajo la Enmienda 22, un Presidente está limitado a dos mandatos de cuatro años."
        ),
        CivicsQuestion(
            id = 27,
            questionEn = "In what month do we vote for President?",
            questionEs = "¿En qué mes votamos por un nuevo Presidente?",
            acceptableAnswersEn = listOf("November"),
            acceptableAnswersEs = listOf("Noviembre"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            explanationEn = "Election Day is the Tuesday following the first Monday in November.",
            explanationEs = "El día de elecciones es el martes siguiente al primer lunes de noviembre."
        ),
        CivicsQuestion(
            id = 28,
            questionEn = "What is the name of the President of the United States now?",
            questionEs = "¿Cómo se llama el actual Presidente de los Estados Unidos?",
            acceptableAnswersEn = listOf("Joe Biden", "Biden"),
            acceptableAnswersEs = listOf("Joe Biden"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            isDynamicOfficial = true,
            explanationEn = "Joe Biden was inaugurated as the 46th President on January 20, 2021.",
            explanationEs = "Joe Biden asumió como el presidente número 46 el 20 de enero de 2021."
        ),
        CivicsQuestion(
            id = 29,
            questionEn = "What is the name of the Vice President of the United States now?",
            questionEs = "¿Cómo se llama la actual Vicepresidenta de los Estados Unidos?",
            acceptableAnswersEn = listOf("Kamala Harris", "Harris"),
            acceptableAnswersEs = listOf("Kamala Harris"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isDynamicOfficial = true,
            explanationEn = "Kamala Harris is the 49th Vice President and President of the Senate.",
            explanationEs = "Kamala Harris es la vicepresidenta número 49 y presidenta del Senado."
        ),
        CivicsQuestion(
            id = 30,
            questionEn = "If the President can no longer serve, who becomes President?",
            questionEs = "Si el Presidente ya no puede cumplir sus funciones, ¿quién se convierte en Presidente?",
            acceptableAnswersEn = listOf("The Vice President"),
            acceptableAnswersEs = listOf("El Vicepresidente"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "The 25th Amendment defines presidential succession.",
            explanationEs = "La Enmienda 25 define la línea de sucesión presidencial."
        ),
        CivicsQuestion(
            id = 31,
            questionEn = "If both the President and the Vice President can no longer serve, who becomes President?",
            questionEs = "Si tanto el Presidente como el Vicepresidente ya no pueden cumplir sus funciones, ¿quién se convierte en Presidente?",
            acceptableAnswersEn = listOf("The Speaker of the House"),
            acceptableAnswersEs = listOf("El Presidente de la Cámara de Representantes (The Speaker of the House)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "The Presidential Succession Act of 1947 places the Speaker third in line.",
            explanationEs = "La Ley de Sucesión Presidencial de 1947 sitúa al Portavoz en tercer lugar de la línea."
        ),
        CivicsQuestion(
            id = 32,
            questionEn = "Who is the Commander in Chief of the military?",
            questionEs = "¿Quién es el Comandante en Jefe de las Fuerzas Armadas?",
            acceptableAnswersEn = listOf("The President"),
            acceptableAnswersEs = listOf("El Presidente"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Civilians hold ultimate control of the military under the US Constitution.",
            explanationEs = "Los civiles tienen el control supremo del ejército bajo la Constitución."
        ),
        CivicsQuestion(
            id = 33,
            questionEn = "Who signs bills to become laws?",
            questionEs = "¿Quién firma los proyectos de ley para convertirlos en leyes?",
            acceptableAnswersEn = listOf("The President"),
            acceptableAnswersEs = listOf("El Presidente"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "When passed by both houses of Congress, the President can sign or veto a bill.",
            explanationEs = "Cuando es aprobado por ambas cámaras del Congreso, el Presidente puede firmar o vetar un proyecto de ley."
        ),
        CivicsQuestion(
            id = 34,
            questionEn = "Who vetoes bills?",
            questionEs = "¿Quién veta los proyectos de ley?",
            acceptableAnswersEn = listOf("The President"),
            acceptableAnswersEs = listOf("El Presidente"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Congress can override a presidential veto with a two-thirds majority in both chambers.",
            explanationEs = "El Congreso puede anular un veto presidencial con una mayoría de dos tercios en ambas cámaras."
        ),
        CivicsQuestion(
            id = 35,
            questionEn = "What does the President's Cabinet do?",
            questionEs = "¿Qué hace el Gabinete del Presidente?",
            acceptableAnswersEn = listOf("Advises the President"),
            acceptableAnswersEs = listOf("Asesora al Presidente"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            explanationEn = "The Cabinet includes the Vice President and the heads of 15 executive departments.",
            explanationEs = "El Gabinete incluye al Vicepresidente y a los jefes de 15 departamentos ejecutivos."
        )
    )
}
