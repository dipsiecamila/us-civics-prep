package com.example.data.datasource

import com.example.data.model.CivicsQuestion
import com.example.data.model.QuestionCategory

object CivicsQuestionsPart2 {
    val questions: List<CivicsQuestion> = listOf(
        CivicsQuestion(
            id = 36,
            questionEn = "What are two Cabinet-level positions?",
            questionEs = "¿Cuáles son dos puestos a nivel de gabinete?",
            acceptableAnswersEn = listOf(
                "Secretary of Agriculture",
                "Secretary of Commerce",
                "Secretary of Defense",
                "Secretary of Education",
                "Secretary of Energy",
                "Secretary of Health and Human Services",
                "Secretary of Homeland Security",
                "Secretary of Housing and Urban Development",
                "Secretary of the Interior",
                "Secretary of Labor",
                "Secretary of State",
                "Secretary of Transportation",
                "Secretary of the Treasury",
                "Secretary of Veterans Affairs",
                "Attorney General",
                "Vice President"
            ),
            acceptableAnswersEs = listOf(
                "Secretario de Agricultura",
                "Secretario de Comercio",
                "Secretario de Defensa",
                "Secretario de Educación",
                "Secretario de Energía",
                "Secretario de Salud y Servicios Humanos",
                "Secretario de Seguridad Nacional",
                "Secretario de Vivienda y Desarrollo Urbano",
                "Secretario del Interior",
                "Secretario del Trabajo",
                "Secretario de Estado",
                "Secretario de Transporte",
                "Secretario del Tesoro",
                "Secretario de Asuntos de los Veteranos",
                "Procurador General",
                "Vicepresidente"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Cabinet members advise the President and head major government agencies.",
            explanationEs = "Los miembros del gabinete asesoran al presidente y dirigen agencias gubernamentales clave."
        ),
        CivicsQuestion(
            id = 37,
            questionEn = "What does the judicial branch do?",
            questionEs = "¿Qué hace la rama judicial?",
            acceptableAnswersEn = listOf(
                "Reviews laws",
                "Explains laws",
                "Resolves disputes (disagreements)",
                "Decides if a law goes against the Constitution"
            ),
            acceptableAnswersEs = listOf(
                "Revisa las leyes",
                "Explica las leyes",
                "Resuelve disputas (desacuerdos)",
                "Decide si una ley va en contra de la Constitución"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Federal courts interpret the meaning of laws and apply them to individual cases.",
            explanationEs = "Los tribunales federales interpretan el significado de las leyes y las aplican a casos individuales."
        ),
        CivicsQuestion(
            id = 38,
            questionEn = "What is the highest court in the United States?",
            questionEs = "¿Cuál es el tribunal más alto de los Estados Unidos?",
            acceptableAnswersEn = listOf("The Supreme Court"),
            acceptableAnswersEs = listOf("La Corte Suprema"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            explanationEn = "The Supreme Court has the final word on constitutional questions.",
            explanationEs = "La Corte Suprema tiene la última palabra sobre cuestiones constitucionales."
        ),
        CivicsQuestion(
            id = 39,
            questionEn = "How many justices are on the Supreme Court?",
            questionEs = "¿Cuántos jueces o magistrados hay en la Corte Suprema?",
            acceptableAnswersEn = listOf("Nine (9)"),
            acceptableAnswersEs = listOf("Nueve (9)"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "The Supreme Court consists of one Chief Justice and eight Associate Justices.",
            explanationEs = "La Corte Suprema se compone de un Presidente del Tribunal Supremo y ocho jueces asociados."
        ),
        CivicsQuestion(
            id = 40,
            questionEn = "Who is the Chief Justice of the United States now?",
            questionEs = "¿Quién es el Presidente del Tribunal Supremo de los Estados Unidos en la actualidad?",
            acceptableAnswersEn = listOf("John Roberts", "John G. Roberts, Jr."),
            acceptableAnswersEs = listOf("John Roberts"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isDynamicOfficial = true,
            explanationEn = "Chief Justice John Roberts has led the Supreme Court since 2005.",
            explanationEs = "El Presidente de la Corte John Roberts lidera la Corte Suprema desde 2005."
        ),
        CivicsQuestion(
            id = 41,
            questionEn = "Under our Constitution, some powers belong to the federal government. What is one power of the federal government?",
            questionEs = "Según nuestra Constitución, algunos poderes pertenecen al gobierno federal. ¿Cuál es un poder del gobierno federal?",
            acceptableAnswersEn = listOf(
                "To print money",
                "To declare war",
                "To create an army",
                "To make treaties"
            ),
            acceptableAnswersEs = listOf(
                "Imprimir dinero",
                "Declarar la guerra",
                "Crear un ejército",
                "Hacer tratados"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "These delegated or enumerated powers are listed in Article I, Section 8.",
            explanationEs = "Estos poderes delegados o enumerados están en el Artículo I, Sección 8 de la Constitución."
        ),
        CivicsQuestion(
            id = 42,
            questionEn = "Under our Constitution, some powers belong to the states. What is one power of the states?",
            questionEs = "Según nuestra Constitución, algunos poderes pertenecen a los estados. ¿Cuál es un poder de los estados?",
            acceptableAnswersEn = listOf(
                "Provide schooling and education",
                "Provide protection (police)",
                "Provide safety (fire departments)",
                "Give a driver's license",
                "Approve zoning and land use"
            ),
            acceptableAnswersEs = listOf(
                "Proveer educación y escuelas",
                "Proveer protección (policía)",
                "Proveer seguridad (bomberos)",
                "Otorgar licencias de conducir",
                "Aprobar la zonificación y el uso de tierras"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            explanationEn = "Under the 10th Amendment, powers not delegated to the federal government are reserved to the states.",
            explanationEs = "Bajo la Enmienda 10, los poderes no delegados al gobierno federal quedan reservados para los estados."
        ),
        CivicsQuestion(
            id = 43,
            questionEn = "Who is the Governor of your state now?",
            questionEs = "¿Quién es el Gobernador de su estado en este momento?",
            acceptableAnswersEn = listOf(
                "Answers will vary by state. District of Columbia residents should answer that D.C. does not have a Governor (Muriel Bowser is Mayor)."
            ),
            acceptableAnswersEs = listOf(
                "Las respuestas varían según el estado. Los residentes de D.C. deben responder que D.C. no tiene gobernador."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            isDynamicOfficial = true,
            explanationEn = "Select your state in 'My State Officials' to check your current governor.",
            explanationEs = "Seleccione su estado en 'Funcionarios de Mi Estado' para ver su gobernador actual."
        ),
        CivicsQuestion(
            id = 44,
            questionEn = "What is the capital of your state?",
            questionEs = "¿Cuál es la capital de su estado?",
            acceptableAnswersEn = listOf(
                "Answers will vary by state. D.C. residents should answer that D.C. is not a state and does not have a capital."
            ),
            acceptableAnswersEs = listOf(
                "Las respuestas varían según el estado. Los residentes de D.C. deben responder que D.C. no es un estado."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            isDynamicOfficial = true,
            explanationEn = "Check 'My State Officials' for your state capital.",
            explanationEs = "Consulte 'Funcionarios de Mi Estado' para la capital de su estado."
        ),
        CivicsQuestion(
            id = 45,
            questionEn = "What are the two major political parties in the United States?",
            questionEs = "¿Cuáles son los dos principales partidos políticos de los Estados Unidos?",
            acceptableAnswersEn = listOf("Democratic and Republican"),
            acceptableAnswersEs = listOf("Demócrata y Republicano"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isSenior6520 = true,
            explanationEn = "The US has operated predominantly as a two-party political system since the 1850s.",
            explanationEs = "Estados Unidos ha operado principalmente como un sistema bipartidista desde la década de 1850."
        ),
        CivicsQuestion(
            id = 46,
            questionEn = "What is the political party of the President now?",
            questionEs = "¿Cuál es el partido político del Presidente actual?",
            acceptableAnswersEn = listOf("Democratic (Party)", "Democrat"),
            acceptableAnswersEs = listOf("Partido Demócrata"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isDynamicOfficial = true,
            explanationEn = "President Joe Biden is a member of the Democratic Party.",
            explanationEs = "El presidente Joe Biden es miembro del Partido Demócrata."
        ),
        CivicsQuestion(
            id = 47,
            questionEn = "What is the name of the Speaker of the House of Representatives now?",
            questionEs = "¿Cómo se llama el Presidente de la Cámara de Representantes en la actualidad?",
            acceptableAnswersEn = listOf("Mike Johnson"),
            acceptableAnswersEs = listOf("Mike Johnson"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "System of Government",
            isDynamicOfficial = true,
            explanationEn = "Mike Johnson was elected Speaker in October 2023.",
            explanationEs = "Mike Johnson fue elegido Presidente de la Cámara en octubre de 2023."
        ),
        CivicsQuestion(
            id = 48,
            questionEn = "There are four amendments to the Constitution about who can vote. Describe one of them.",
            questionEs = "Hay cuatro enmiendas a la Constitución sobre quién puede votar. Describa una de ellas.",
            acceptableAnswersEn = listOf(
                "Citizens eighteen (18) and older (can vote).",
                "You don't have to pay (a poll tax) to vote.",
                "Any citizen can vote. (Women and men can vote.)",
                "A male citizen of any race (can vote)."
            ),
            acceptableAnswersEs = listOf(
                "Ciudadanos de 18 años o más pueden votar.",
                "No hay que pagar un impuesto para votar (sin impuesto de capitación).",
                "Cualquier ciudadano puede votar (mujeres y hombres pueden votar).",
                "Un ciudadano varón de cualquier raza puede votar."
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "Amendments 15, 19, 24, and 26 expanded suffrage over American history.",
            explanationEs = "Las Enmiendas 15, 19, 24 y 26 ampliaron el derecho al voto en la historia estadounidense."
        ),
        CivicsQuestion(
            id = 49,
            questionEn = "What is one responsibility that is only for United States citizens?",
            questionEs = "¿Cuál es una responsabilidad que corresponde sólo a los ciudadanos de los Estados Unidos?",
            acceptableAnswersEn = listOf(
                "Serve on a jury",
                "Vote in a federal election"
            ),
            acceptableAnswersEs = listOf(
                "Prestar servicio en un jurado",
                "Votar en una elección federal"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            isSenior6520 = true,
            explanationEn = "Serving on a jury ensures trial by peers, a fundamental constitutional right.",
            explanationEs = "Servir en un jurado garantiza el juicio por pares, un derecho constitucional fundamental."
        ),
        CivicsQuestion(
            id = 50,
            questionEn = "Name one right only for United States citizens.",
            questionEs = "¿Cuál es un derecho que pueden ejercer sólo los ciudadanos de los Estados Unidos?",
            acceptableAnswersEn = listOf(
                "Vote in a federal election",
                "Run for federal office"
            ),
            acceptableAnswersEs = listOf(
                "Votar en una elección federal",
                "Postularse a un cargo federal"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "Only US citizens can vote for President, Senators, and Representatives.",
            explanationEs = "Solo los ciudadanos estadounidenses pueden votar por Presidente, Senadores y Representantes."
        ),
        CivicsQuestion(
            id = 51,
            questionEn = "What are two rights of everyone living in the United States?",
            questionEs = "¿Cuáles son dos derechos que pueden ejercer todas las personas que viven en los Estados Unidos?",
            acceptableAnswersEn = listOf(
                "Freedom of expression",
                "Freedom of speech",
                "Freedom of assembly",
                "Freedom to petition the government",
                "Freedom of religion",
                "The right to bear arms"
            ),
            acceptableAnswersEs = listOf(
                "Libertad de expresión",
                "Libertad de la palabra",
                "Libertad de reunión",
                "Libertad para peticionar al gobierno",
                "Libertad de religión",
                "El derecho a portar armas"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "Constitutional protections apply to all persons within the jurisdiction of the United States.",
            explanationEs = "Las protecciones constitucionales se aplican a todas las personas bajo la jurisdicción de EE. UU."
        ),
        CivicsQuestion(
            id = 52,
            questionEn = "What do we show loyalty to when we say the Pledge of Allegiance?",
            questionEs = "¿A qué demostramos lealtad cuando decimos el Juramento a la Bandera (Pledge of Allegiance)?",
            acceptableAnswersEn = listOf(
                "The United States",
                "The flag"
            ),
            acceptableAnswersEs = listOf(
                "A los Estados Unidos",
                "A la bandera"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "The pledge was first composed in 1892 by Francis Bellamy.",
            explanationEs = "El juramento fue redactado por primera vez en 1892 por Francis Bellamy."
        ),
        CivicsQuestion(
            id = 53,
            questionEn = "What is one promise you make when you become a United States citizen?",
            questionEs = "¿Cuál es una promesa que usted hace cuando se convierte en ciudadano de los Estados Unidos?",
            acceptableAnswersEn = listOf(
                "Give up loyalty to other countries",
                "Defend the Constitution and laws of the United States",
                "Obey the laws of the United States",
                "Serve in the U.S. military (if needed)",
                "Serve (do important work for) the nation (if needed)",
                "Be loyal to the United States"
            ),
            acceptableAnswersEs = listOf(
                "Renunciar a la lealtad a otros países",
                "Defender la Constitución y las leyes de los Estados Unidos",
                "Obedecer las leyes de los Estados Unidos",
                "Prestar servicio en el ejército de EE. UU. (si es necesario)",
                "Prestar servicio a la nación (si es necesario)",
                "Ser leal a los Estados Unidos"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "New citizens take the Oath of Allegiance during their Naturalization Ceremony.",
            explanationEs = "Los nuevos ciudadanos prestan el Juramento de Lealtad durante su Ceremonia de Naturalización."
        ),
        CivicsQuestion(
            id = 54,
            questionEn = "How old do citizens have to be to vote for President?",
            questionEs = "¿Cuántos años tienen que tener los ciudadanos para votar por el Presidente?",
            acceptableAnswersEn = listOf("Eighteen (18) and older"),
            acceptableAnswersEs = listOf("Dieciocho (18) años o más"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            isSenior6520 = true,
            explanationEn = "The 26th Amendment lowered the voting age to 18 in 1971.",
            explanationEs = "La Enmienda 26 redujo la edad de votación a 18 años en 1971."
        ),
        CivicsQuestion(
            id = 55,
            questionEn = "What are two ways that Americans can participate in their democracy?",
            questionEs = "¿Cuáles son dos maneras mediante las cuales los estadounidenses pueden participar en su democracia?",
            acceptableAnswersEn = listOf(
                "Vote",
                "Join a political party",
                "Help with a campaign",
                "Join a civic group",
                "Join a community group",
                "Give an elected official your opinion on an issue",
                "Call Senators and Representatives",
                "Publicly support or oppose an issue or policy",
                "Run for office",
                "Write to a newspaper"
            ),
            acceptableAnswersEs = listOf(
                "Votar",
                "Afiliarse a un partido político",
                "Ayudar en una campaña",
                "Unirse a un grupo cívico",
                "Unirse a un grupo comunitario",
                "Dar su opinión sobre un asunto a un funcionario electo",
                "Llamar a senadores y representantes",
                "Apoyar u oponerse públicamente a una ley o política",
                "Postularse a un cargo público",
                "Escribir a un periódico"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "Active civic engagement is the bedrock of democratic self-governance.",
            explanationEs = "La participación cívica activa es la base del autogobierno democrático."
        ),
        CivicsQuestion(
            id = 56,
            questionEn = "When is the last day you can send in federal income tax forms?",
            questionEs = "¿Cuál es la fecha límite para enviar las declaraciones federales de impuestos sobre ingresos?",
            acceptableAnswersEn = listOf("April 15"),
            acceptableAnswersEs = listOf("El 15 de abril"),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "Federal tax returns for the prior year are due every April 15 (or next business day).",
            explanationEs = "Las declaraciones de impuestos federales vencen cada 15 de abril (o siguiente día hábil)."
        ),
        CivicsQuestion(
            id = 57,
            questionEn = "When must all men register for the Selective Service?",
            questionEs = "¿Cuándo deben inscribirse todos los hombres en el Servicio Selectivo?",
            acceptableAnswersEn = listOf(
                "At age eighteen (18)",
                "Between eighteen (18) and twenty-six (26)"
            ),
            acceptableAnswersEs = listOf(
                "A la edad de dieciocho (18) años",
                "Entre los dieciocho (18) y veintiséis (26) años"
            ),
            category = QuestionCategory.AMERICAN_GOVERNMENT,
            subcategory = "Rights and Responsibilities",
            explanationEn = "Federal law requires almost all male US citizens and immigrants ages 18-25 to register.",
            explanationEs = "La ley federal exige que casi todos los hombres ciudadanos e inmigrantes de 18 a 25 años se inscriban."
        ),
        CivicsQuestion(
            id = 58,
            questionEn = "What is one reason colonists came to America?",
            questionEs = "¿Cuál es una razón por la que los colonos vinieron a América?",
            acceptableAnswersEn = listOf(
                "Freedom",
                "Political liberty",
                "Religious freedom",
                "Economic opportunity",
                "Practice their religion",
                "Escape persecution"
            ),
            acceptableAnswersEs = listOf(
                "Libertad",
                "Libertad política",
                "Libertad religiosa",
                "Oportunidad económica",
                "Practicar su religión",
                "Escapar de la persecución"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "Colonies like Plymouth and Massachusetts Bay were founded for religious freedom.",
            explanationEs = "Colonias como Plymouth y la Bahía de Massachusetts se fundaron por libertad religiosa."
        ),
        CivicsQuestion(
            id = 59,
            questionEn = "Who lived in America before the Europeans arrived?",
            questionEs = "¿Quiénes vivían en lo que hoy es Estados Unidos antes de la llegada de los europeos?",
            acceptableAnswersEn = listOf(
                "American Indians",
                "Native Americans"
            ),
            acceptableAnswersEs = listOf(
                "Indios americanos",
                "Nativos americanos"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            isSenior6520 = true,
            explanationEn = "Hundreds of diverse Indigenous nations lived across the continent for thousands of years.",
            explanationEs = "Cientos de diversas naciones indígenas vivieron en el continente durante miles de años."
        ),
        CivicsQuestion(
            id = 60,
            questionEn = "What group of people was taken to America and sold as slaves?",
            questionEs = "¿Qué grupo de personas fue traído a los Estados Unidos y vendido como esclavos?",
            acceptableAnswersEn = listOf(
                "Africans",
                "People from Africa"
            ),
            acceptableAnswersEs = listOf(
                "Africanos",
                "Gente de África"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "The transatlantic slave trade forcibly brought millions of Africans to the Americas.",
            explanationEs = "El comercio transatlántico de esclavos trajo por la fuerza a millones de africanos a América."
        ),
        CivicsQuestion(
            id = 61,
            questionEn = "Why did the colonists fight the British?",
            questionEs = "¿Por qué lucharon los colonos contra los británicos?",
            acceptableAnswersEn = listOf(
                "Because of high taxes (taxation without representation)",
                "Because the British army stayed in their houses (boarding, quartering)",
                "Because they didn't have self-government"
            ),
            acceptableAnswersEs = listOf(
                "Debido a los altos impuestos (impuestos sin representación)",
                "Porque el ejército británico se alojaba en sus casas (acuartelamiento)",
                "Porque no tenían autogobierno"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "\"No taxation without representation\" was the primary rally cry leading to the revolution.",
            explanationEs = "\"No hay tributación sin representación\" fue el lema principal que condujo a la revolución."
        ),
        CivicsQuestion(
            id = 62,
            questionEn = "Who wrote the Declaration of Independence?",
            questionEs = "¿Quién escribió la Declaración de Independencia?",
            acceptableAnswersEn = listOf("(Thomas) Jefferson"),
            acceptableAnswersEs = listOf("(Thomas) Jefferson"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "Thomas Jefferson was the principal author on the Committee of Five.",
            explanationEs = "Thomas Jefferson fue el autor principal en el Comité de los Cinco."
        ),
        CivicsQuestion(
            id = 63,
            questionEn = "When was the Declaration of Independence adopted?",
            questionEs = "¿Cuándo fue adoptada la Declaración de Independencia?",
            acceptableAnswersEn = listOf("July 4, 1776"),
            acceptableAnswersEs = listOf("El 4 de julio de 1776"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            isSenior6520 = true,
            explanationEn = "Celebrated every year on July 4th as Independence Day.",
            explanationEs = "Celebrado cada año el 4 de julio como el Día de la Independencia."
        ),
        CivicsQuestion(
            id = 64,
            questionEn = "There were 13 original states. Name three.",
            questionEs = "Había 13 estados originales. Nombre tres.",
            acceptableAnswersEn = listOf(
                "New Hampshire",
                "Massachusetts",
                "Rhode Island",
                "Connecticut",
                "New York",
                "New Jersey",
                "Pennsylvania",
                "Delaware",
                "Maryland",
                "Virginia",
                "North Carolina",
                "South Carolina",
                "Georgia"
            ),
            acceptableAnswersEs = listOf(
                "Nueva Hampshire",
                "Massachusetts",
                "Rhode Island",
                "Connecticut",
                "Nueva York",
                "Nueva Jersey",
                "Pensilvania",
                "Delaware",
                "Maryland",
                "Virginia",
                "Carolina del Norte",
                "Carolina del Sur",
                "Georgia"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "These 13 British colonies declared independence in 1776.",
            explanationEs = "Estas 13 colonias británicas declararon su independencia en 1776."
        ),
        CivicsQuestion(
            id = 65,
            questionEn = "What happened at the Constitutional Convention?",
            questionEs = "¿Qué ocurrió en la Convención Constitucional?",
            acceptableAnswersEn = listOf(
                "The Constitution was written.",
                "The Founding Fathers wrote the Constitution."
            ),
            acceptableAnswersEs = listOf(
                "Se redactó la Constitución.",
                "Los Padres Fundadores redactaron la Constitución."
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "Fifty-five delegates met in Philadelphia in 1787 to design a new national government.",
            explanationEs = "Cincuenta y cinco delegados se reunieron en Filadelfia en 1787 para diseñar un nuevo gobierno nacional."
        ),
        CivicsQuestion(
            id = 66,
            questionEn = "When was the Constitution written?",
            questionEs = "¿Cuándo fue redactada la Constitución?",
            acceptableAnswersEn = listOf("1787"),
            acceptableAnswersEs = listOf("1787"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "Signed on September 17, 1787, now celebrated as Constitution Day.",
            explanationEs = "Firmada el 17 de septiembre de 1787, hoy celebrado como el Día de la Constitución."
        ),
        CivicsQuestion(
            id = 67,
            questionEn = "The Federalist Papers supported the passage of the U.S. Constitution. Name one of the writers.",
            questionEs = "Los Ensayos Federalistas (The Federalist Papers) respaldaron la aprobación de la Constitución. Nombre uno de sus autores.",
            acceptableAnswersEn = listOf(
                "(James) Madison",
                "(Alexander) Hamilton",
                "(John) Jay",
                "Publius"
            ),
            acceptableAnswersEs = listOf(
                "(James) Madison",
                "(Alexander) Hamilton",
                "(John) Jay",
                "Publius"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "85 essays were published under the pseudonym 'Publius' to urge ratification in New York.",
            explanationEs = "Se publicaron 85 ensayos bajo el seudónimo 'Publius' para instar a la ratificación en Nueva York."
        ),
        CivicsQuestion(
            id = 68,
            questionEn = "What is one thing Benjamin Franklin is famous for?",
            questionEs = "¿Mencione una cosa por la que es famoso Benjamin Franklin?",
            acceptableAnswersEn = listOf(
                "U.S. diplomat",
                "Oldest member of the Constitutional Convention",
                "First Postmaster General of the United States",
                "Writer of 'Poor Richard's Almanac'",
                "Started the first free libraries"
            ),
            acceptableAnswersEs = listOf(
                "Diplomático de EE. UU.",
                "Miembro más anciano de la Convención Constitucional",
                "Primer Director General de Correos",
                "Autor del 'Almanaque del Pobre Richard'",
                "Fundó las primeras bibliotecas públicas gratuitas"
            ),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            explanationEn = "Franklin secured crucial French financial and military support during the Revolution.",
            explanationEs = "Franklin aseguró el crucial apoyo financiero y militar francés durante la Revolución."
        ),
        CivicsQuestion(
            id = 69,
            questionEn = "Who is the \"Father of Our Country\"?",
            questionEs = "¿Quién es el \"Padre de Nuestra Patria\"?",
            acceptableAnswersEn = listOf("(George) Washington"),
            acceptableAnswersEs = listOf("(George) Washington"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            isSenior6520 = true,
            explanationEn = "Washington commanded the Continental Army and presided over the Constitutional Convention.",
            explanationEs = "Washington comandó el Ejército Continental y presidió la Convención Constitucional."
        ),
        CivicsQuestion(
            id = 70,
            questionEn = "Who was the first President?",
            questionEs = "¿Quién fue el primer Presidente?",
            acceptableAnswersEn = listOf("(George) Washington"),
            acceptableAnswersEs = listOf("(George) Washington"),
            category = QuestionCategory.AMERICAN_HISTORY,
            subcategory = "Colonial Period and Independence",
            isSenior6520 = true,
            explanationEn = "George Washington served two terms from 1789 to 1797.",
            explanationEs = "George Washington cumplió dos mandatos de 1789 a 1797."
        )
    )
}
