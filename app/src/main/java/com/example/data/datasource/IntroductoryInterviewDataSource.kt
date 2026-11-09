package com.example.data.datasource

import com.example.data.model.IntroductoryQuestion

object IntroductoryInterviewDataSource {
    val questions: List<IntroductoryQuestion> = listOf(
        IntroductoryQuestion(
            id = 1,
            category = "Oath & Warm-Up",
            questionEn = "Please raise your right hand. Do you swear or affirm that the statements you will give today will be the truth, the whole truth, and nothing but the truth?",
            questionEs = "Por favor levante su mano derecha. ¿Jura o afirma que las declaraciones que dará hoy serán la verdad, toda la verdad y nada más que la verdad?",
            sampleAnswersEn = listOf("I do.", "Yes, I do, officer.", "Yes, I swear."),
            sampleAnswersEs = listOf("Sí, lo juro.", "Sí, oficial.", "Sí, juro."),
            officerTip = "Answer clearly with 'I do' or 'Yes, I do'. Stand tall with your right hand raised.",
            keywordsToListenFor = listOf("swear", "truth", "right hand")
        ),
        IntroductoryQuestion(
            id = 2,
            category = "Oath & Warm-Up",
            questionEn = "Why are you here today?",
            questionEs = "¿Por qué está aquí hoy?",
            sampleAnswersEn = listOf(
                "To have my citizenship interview.",
                "To become an American citizen.",
                "For my naturalization interview."
            ),
            sampleAnswersEs = listOf(
                "Para tener mi entrevista de ciudadanía.",
                "Para convertirme en ciudadano estadounidense.",
                "Para mi entrevista de naturalización."
            ),
            officerTip = "Keep it simple and direct. Say 'To become a U.S. citizen'.",
            keywordsToListenFor = listOf("why", "here", "today")
        ),
        IntroductoryQuestion(
            id = 3,
            category = "Personal Information",
            questionEn = "What is your full legal name?",
            questionEs = "¿Cuál es su nombre legal completo?",
            sampleAnswersEn = listOf("My full legal name is [Your Full Name]."),
            sampleAnswersEs = listOf("Mi nombre legal completo es [Su Nombre Completo]."),
            officerTip = "State your first name, middle name (if any), and last name exactly as on your Green Card.",
            keywordsToListenFor = listOf("full", "legal", "name")
        ),
        IntroductoryQuestion(
            id = 4,
            category = "Personal Information",
            questionEn = "Have you used any other names since birth or on official documents?",
            questionEs = "¿Ha utilizado otros nombres desde su nacimiento o en documentos oficiales?",
            sampleAnswersEn = listOf("No, officer.", "No, only my maiden name [Maiden Name]."),
            sampleAnswersEs = listOf("No, oficial.", "No, únicamente mi apellido de soltera."),
            officerTip = "Mention your maiden name if applicable, or state clearly 'No, never'.",
            keywordsToListenFor = listOf("other names", "maiden name", "alias")
        ),
        IntroductoryQuestion(
            id = 5,
            category = "Personal Information",
            questionEn = "Would you like to legally change your name today?",
            questionEs = "¿Le gustaría cambiar legalmente su nombre hoy?",
            sampleAnswersEn = listOf("No, thank you.", "Yes, I would like to change it to [New Name]."),
            sampleAnswersEs = listOf("No, gracias.", "Sí, me gustaría cambiarlo a [Nuevo Nombre]."),
            officerTip = "USCIS allows you to request a legal name change during naturalization at no extra cost.",
            keywordsToListenFor = listOf("legally", "change", "name")
        ),
        IntroductoryQuestion(
            id = 6,
            category = "Age & Eligibility",
            questionEn = "What is your date of birth?",
            questionEs = "¿Cuál es su fecha de nacimiento?",
            sampleAnswersEn = listOf("My date of birth is [Month] [Day], [Year]."),
            sampleAnswersEs = listOf("Mi fecha de nacimiento es [Fecha de Nacimiento]."),
            officerTip = "In English, dates are spoken as Month-Day-Year (e.g., 'May 20th, 1985').",
            keywordsToListenFor = listOf("date of birth", "born")
        ),
        IntroductoryQuestion(
            id = 7,
            category = "Age & Eligibility",
            questionEn = "How old are you?",
            questionEs = "¿Cuántos años tiene?",
            sampleAnswersEn = listOf("I am [Age] years old."),
            sampleAnswersEs = listOf("Tengo [Edad] años."),
            officerTip = "Make sure your age matches your stated date of birth.",
            keywordsToListenFor = listOf("how old", "age")
        ),
        IntroductoryQuestion(
            id = 8,
            category = "Country of Origin",
            questionEn = "What country were you born in?",
            questionEs = "¿En qué país nació?",
            sampleAnswersEn = listOf("I was born in [Your Country of Birth]."),
            sampleAnswersEs = listOf("Nací en [Su País de Nacimiento]."),
            officerTip = "Say 'I was born in Mexico / Colombia / Dominican Republic / etc.'",
            keywordsToListenFor = listOf("country", "born in", "where born")
        ),
        IntroductoryQuestion(
            id = 9,
            category = "Residence & Address",
            questionEn = "What is your current home address?",
            questionEs = "¿Cuál es su dirección actual de residencia?",
            sampleAnswersEn = listOf("My address is [Street Number, Street Name, City, State, Zip Code]."),
            sampleAnswersEs = listOf("Mi dirección es [Número, Calle, Ciudad, Estado, Código Postal]."),
            officerTip = "Practice reciting your address smoothly, including city, state, and zip code.",
            keywordsToListenFor = listOf("current", "home address", "where live")
        ),
        IntroductoryQuestion(
            id = 10,
            category = "Residence & Address",
            questionEn = "How long have you lived at your current address?",
            questionEs = "¿Cuánto tiempo ha vivido en su dirección actual?",
            sampleAnswersEn = listOf("I have lived there for [Number] years.", "For about three years."),
            sampleAnswersEs = listOf("He vivido allí durante [Número] años.", "Aproximadamente tres años."),
            officerTip = "State the number of months or years.",
            keywordsToListenFor = listOf("how long", "lived", "current address")
        ),
        IntroductoryQuestion(
            id = 11,
            category = "Parents & Heritage",
            questionEn = "What are your parents' names?",
            questionEs = "¿Cuáles son los nombres de sus padres?",
            sampleAnswersEn = listOf("My father's name is [Name] and my mother's name is [Name]."),
            sampleAnswersEs = listOf("El nombre de mi padre es [Nombre] y el de mi madre es [Nombre]."),
            officerTip = "State both your father's and mother's full names clearly.",
            keywordsToListenFor = listOf("parents' names", "father", "mother")
        ),
        IntroductoryQuestion(
            id = 12,
            category = "Parents & Heritage",
            questionEn = "Were either of your parents U.S. citizens before your 18th birthday?",
            questionEs = "¿Alguno de sus padres era ciudadano estadounidense antes de que usted cumpliera 18 años?",
            sampleAnswersEn = listOf("No, officer, neither of them.", "No, they were not."),
            sampleAnswersEs = listOf("No, oficial, ninguno de ellos.", "No, no lo eran."),
            officerTip = "This checks if you might already be a derived U.S. citizen. Most applicants answer 'No'.",
            keywordsToListenFor = listOf("parents", "citizens", "before 18")
        ),
        IntroductoryQuestion(
            id = 13,
            category = "Marital Status",
            questionEn = "What is your current marital status? Are you single, married, divorced, or widowed?",
            questionEs = "¿Cuál es su estado civil actual? ¿Es soltero, casado, divorciado o viudo?",
            sampleAnswersEn = listOf("I am married.", "I am single.", "I am divorced."),
            sampleAnswersEs = listOf("Estoy casado/a.", "Soy soltero/a.", "Estoy divorciado/a."),
            officerTip = "Answer with the single word matching your Form N-400.",
            keywordsToListenFor = listOf("marital status", "married", "single")
        ),
        IntroductoryQuestion(
            id = 14,
            category = "Marital Status",
            questionEn = "What is the full name of your spouse, and is your spouse a U.S. citizen?",
            questionEs = "¿Cuál es el nombre completo de su cónyuge y es su cónyuge ciudadano estadounidense?",
            sampleAnswersEn = listOf(
                "My spouse's name is [Name], and yes, he/she is a U.S. citizen.",
                "My spouse is [Name], and no, he/she is a permanent resident."
            ),
            sampleAnswersEs = listOf(
                "El nombre de mi cónyuge es [Nombre] y sí, es ciudadano/a estadounidense."
            ),
            officerTip = "Be ready with your spouse's date of birth and wedding date if asked.",
            keywordsToListenFor = listOf("spouse", "citizen")
        ),
        IntroductoryQuestion(
            id = 15,
            category = "Children",
            questionEn = "Do you have any children?",
            questionEs = "¿Tiene hijos?",
            sampleAnswersEn = listOf("Yes, I have [Number] children.", "No, officer, I do not have children."),
            sampleAnswersEs = listOf("Sí, tengo [Número] hijos.", "No, oficial, no tengo hijos."),
            officerTip = "Includes all biological, adopted, and stepchildren.",
            keywordsToListenFor = listOf("children", "kids", "sons", "daughters")
        ),
        IntroductoryQuestion(
            id = 16,
            category = "Children",
            questionEn = "How many children do you have, and what are their names?",
            questionEs = "¿Cuántos hijos tiene y cuáles son sus nombres?",
            sampleAnswersEn = listOf(
                "I have two children. Their names are [Name] and [Name].",
                "I have one child, his/her name is [Name]."
            ),
            sampleAnswersEs = listOf(
                "Tengo dos hijos. Sus nombres son [Nombre] y [Nombre]."
            ),
            officerTip = "Practice saying each child's name, age, and country of birth.",
            keywordsToListenFor = listOf("how many children", "names")
        ),
        IntroductoryQuestion(
            id = 17,
            category = "Employment & Travel",
            questionEn = "Are you currently working? What is your occupation?",
            questionEs = "¿Está trabajando actualmente? ¿Cuál es su ocupación?",
            sampleAnswersEn = listOf(
                "Yes, I work as a [Job Title] at [Company Name].",
                "Yes, I am a manager / driver / nurse / technician.",
                "No, I am currently a homemaker / student / retired."
            ),
            sampleAnswersEs = listOf(
                "Sí, trabajo como [Puesto] en [Empresa].",
                "No, actualmente me dedico al hogar / soy estudiante."
            ),
            officerTip = "State your job title and employer name simply.",
            keywordsToListenFor = listOf("working", "employed", "occupation", "job")
        ),
        IntroductoryQuestion(
            id = 18,
            category = "Employment & Travel",
            questionEn = "Have you taken any trips outside of the United States in the last five years?",
            questionEs = "¿Ha realizado viajes fuera de los Estados Unidos en los últimos cinco años?",
            sampleAnswersEn = listOf(
                "Yes, I took two short vacations to visit family.",
                "No, I have not left the United States in the last five years."
            ),
            sampleAnswersEs = listOf(
                "Sí, hice dos viajes cortos de vacaciones para visitar a mi familia.",
                "No, no he salido de los Estados Unidos en los últimos cinco años."
            ),
            officerTip = "Confirm that no trip lasted more than 6 months to preserve continuous residence.",
            keywordsToListenFor = listOf("trips", "outside", "travel", "five years")
        ),
        IntroductoryQuestion(
            id = 19,
            category = "Moral Character & N-400 Part 12",
            questionEn = "Have you ever claimed to be a U.S. citizen in writing or any other way?",
            questionEs = "¿Alguna vez ha afirmado ser ciudadano estadounidense por escrito o de cualquier otra forma?",
            sampleAnswersEn = listOf("No, officer, never.", "No, never."),
            sampleAnswersEs = listOf("No, oficial, nunca."),
            officerTip = "Always answer 'No, never'. Claiming false citizenship is a serious ground of inadmissibility.",
            keywordsToListenFor = listOf("claimed", "citizen", "false")
        ),
        IntroductoryQuestion(
            id = 20,
            category = "Moral Character & N-400 Part 12",
            questionEn = "Have you ever registered to vote or voted in any federal, state, or local election in the United States?",
            questionEs = "¿Alguna vez se ha registrado para votar o ha votado en alguna elección en los Estados Unidos?",
            sampleAnswersEn = listOf("No, officer, never.", "No, I know only citizens can vote."),
            sampleAnswersEs = listOf("No, oficial, nunca.", "No, sé que solo los ciudadanos pueden votar."),
            officerTip = "Clear answer: 'No, never'. Only U.S. citizens may vote.",
            keywordsToListenFor = listOf("vote", "registered", "election")
        ),
        IntroductoryQuestion(
            id = 21,
            category = "Moral Character & N-400 Part 12",
            questionEn = "Do you owe any overdue federal, state, or local taxes?",
            questionEs = "¿Debe impuestos federales, estatales o locales vencidos?",
            sampleAnswersEn = listOf("No, officer. I pay my taxes every year.", "No, never."),
            sampleAnswersEs = listOf("No, oficial. Pago mis impuestos cada año."),
            officerTip = "Answer 'No, officer'. If you have a payment plan with the IRS, bring your payment proof.",
            keywordsToListenFor = listOf("owe", "overdue", "taxes")
        ),
        IntroductoryQuestion(
            id = 22,
            category = "Allegiance & Oath",
            questionEn = "Do you support the Constitution and form of government of the United States?",
            questionEs = "¿Apoya la Constitución y la forma de gobierno de los Estados Unidos?",
            sampleAnswersEn = listOf("Yes, I do, officer.", "Yes, absolutely."),
            sampleAnswersEs = listOf("Sí, oficial, por supuesto.", "Sí, absolutamente."),
            officerTip = "Answer firmly: 'Yes, I do'.",
            keywordsToListenFor = listOf("support", "constitution", "government")
        ),
        IntroductoryQuestion(
            id = 23,
            category = "Allegiance & Oath",
            questionEn = "Do you understand the full Oath of Allegiance to the United States, and are you willing to take it?",
            questionEs = "¿Entiende el Juramento de Lealtad completo a los Estados Unidos y está dispuesto a prestarlo?",
            sampleAnswersEn = listOf("Yes, officer, I understand and I am willing to take it."),
            sampleAnswersEs = listOf("Sí, oficial, lo entiendo y estoy dispuesto a prestarlo."),
            officerTip = "Answer with conviction: 'Yes, I do and I am willing'.",
            keywordsToListenFor = listOf("oath of allegiance", "willing", "take")
        )
    )

    fun resolvePersonalSampleAnswersEn(questionId: Int, profile: com.example.data.model.N400PersonalProfile): List<String> {
        val q = questions.firstOrNull { it.id == questionId } ?: return emptyList()
        val nameVal = profile.fullName.ifBlank { "Carlos Eduardo Rodriguez" }
        val ageVal = profile.calculatedAge.toString()
        val dobVal = profile.dateOfBirth.ifBlank { "March 15, 1985" }
        val cityVal = profile.cityOfBirth.ifBlank { "Bogota" }
        val countryVal = profile.countryOfBirth.ifBlank { "Colombia" }
        val statusVal = profile.maritalStatus.ifBlank { "Married" }
        val spouseVal = profile.spouseName.ifBlank { "Elena Gomez" }
        val fatherVal = profile.fatherName.ifBlank { "Juan Carlos Rodriguez" }
        val motherVal = profile.motherName.ifBlank { "Maria Elena Rodriguez" }
        val childrenCount = profile.numberOfChildren.toIntOrNull() ?: 0
        val childrenVal = profile.childrenNames.ifBlank { "David and Sofia" }
        val addressVal = profile.currentAddress.ifBlank { "123 Main Street, Los Angeles, CA" }
        val addressYears = profile.yearsAtCurrentAddress.ifBlank { "5" }
        val jobVal = profile.occupation.ifBlank { "technician" }
        val employerVal = profile.employer.ifBlank { "City Services" }
        val tripsVal = profile.tripsOutsideUS.ifBlank { "none" }

        return when (questionId) {
            3 -> listOf("My full legal name is $nameVal.")
            4 -> listOf("No, officer. I have only used my legal name.")
            5 -> listOf("No, thank you. I want to keep my current legal name.")
            6 -> listOf("My date of birth is $dobVal.")
            7 -> listOf("I am $ageVal years old.")
            8 -> listOf("I was born in $cityVal, $countryVal.")
            9 -> listOf("My address is $addressVal.")
            10 -> listOf("I have lived there for $addressYears years.")
            11 -> listOf("My father's name is $fatherVal and my mother's name is $motherVal.")
            12 -> if (profile.isFatherUSCitizen || profile.isMotherUSCitizen) {
                listOf("Yes, my parents are U.S. citizens.")
            } else {
                listOf("No, officer, neither of them.")
            }
            13 -> listOf("I am currently $statusVal.")
            14 -> if (statusVal.equals("Married", ignoreCase = true)) {
                val citizenNotice = if (profile.isSpouseUSCitizen) "and yes, my spouse is a U.S. citizen." else "and my spouse is a permanent resident."
                listOf("My spouse's name is $spouseVal, $citizenNotice")
            } else {
                listOf("I am not married, officer.")
            }
            15 -> if (childrenCount == 0) {
                listOf("No, officer, I do not have children.")
            } else {
                listOf("Yes, I have $childrenCount children.")
            }
            16 -> if (childrenCount == 0) {
                listOf("I do not have any children, officer.")
            } else {
                listOf("I have $childrenCount children. Their names are $childrenVal.")
            }
            17 -> listOf("Yes, I work as an $jobVal at $employerVal.")
            18 -> if (tripsVal.equals("none", true) || tripsVal == "0") {
                listOf("No, I have not left the United States in the last five years.")
            } else {
                listOf("Yes, I took trips outside the United States: $tripsVal.")
            }
            else -> q.sampleAnswersEn
        }
    }

    fun resolvePersonalSampleAnswersEs(questionId: Int, profile: com.example.data.model.N400PersonalProfile): List<String> {
        val q = questions.firstOrNull { it.id == questionId } ?: return emptyList()
        val nameVal = profile.fullName.ifBlank { "Carlos Eduardo Rodriguez" }
        val ageVal = profile.calculatedAge.toString()
        val dobVal = profile.dateOfBirth.ifBlank { "15 de marzo de 1985" }
        val cityVal = profile.cityOfBirth.ifBlank { "Bogotá" }
        val countryVal = profile.countryOfBirth.ifBlank { "Colombia" }
        val statusVal = profile.maritalStatus.ifBlank { "Casado" }
        val spouseVal = profile.spouseName.ifBlank { "Elena Gómez" }
        val fatherVal = profile.fatherName.ifBlank { "Juan Carlos Rodríguez" }
        val motherVal = profile.motherName.ifBlank { "María Elena Rodríguez" }
        val childrenCount = profile.numberOfChildren.toIntOrNull() ?: 0
        val childrenVal = profile.childrenNames.ifBlank { "David y Sofia" }
        val addressVal = profile.currentAddress.ifBlank { "123 Main Street" }
        val addressYears = profile.yearsAtCurrentAddress.ifBlank { "5" }
        val jobVal = profile.occupation.ifBlank { "técnico" }
        val employerVal = profile.employer.ifBlank { "la empresa" }
        val tripsVal = profile.tripsOutsideUS.ifBlank { "ninguno" }

        return when (questionId) {
            3 -> listOf("Mi nombre legal completo es $nameVal.")
            4 -> listOf("No, oficial. Únicamente mi nombre legal.")
            5 -> listOf("No, gracias. Deseo mantener mi nombre actual.")
            6 -> listOf("Mi fecha de nacimiento es $dobVal.")
            7 -> listOf("Tengo $ageVal años.")
            8 -> listOf("Nací en $cityVal, $countryVal.")
            9 -> listOf("Mi dirección es $addressVal.")
            10 -> listOf("He vivido allí durante $addressYears años.")
            11 -> listOf("El nombre de mi padre es $fatherVal y el de mi madre es $motherVal.")
            12 -> if (profile.isFatherUSCitizen || profile.isMotherUSCitizen) {
                listOf("Sí, mis padres son ciudadanos estadounidenses.")
            } else {
                listOf("No, oficial, ninguno de ellos.")
            }
            13 -> listOf("Actualmente estoy $statusVal.")
            14 -> if (statusVal.equals("Married", ignoreCase = true) || statusVal.equals("Casado", ignoreCase = true)) {
                val citizenNotice = if (profile.isSpouseUSCitizen) "y sí, es ciudadano/a estadounidense." else "y es residente permanente legal."
                listOf("El nombre de mi cónyuge es $spouseVal $citizenNotice")
            } else {
                listOf("No estoy casado/a, oficial.")
            }
            15 -> if (childrenCount == 0) {
                listOf("No, oficial, no tengo hijos.")
            } else {
                listOf("Sí, tengo $childrenCount hijos.")
            }
            16 -> if (childrenCount == 0) {
                listOf("No tengo hijos, oficial.")
            } else {
                listOf("Tengo $childrenCount hijos. Sus nombres son $childrenVal.")
            }
            17 -> listOf("Sí, trabajo como $jobVal en $employerVal.")
            18 -> if (tripsVal.equals("none", true) || tripsVal.equals("ninguno", true) || tripsVal == "0") {
                listOf("No, no he salido de los Estados Unidos en los últimos cinco años.")
            } else {
                listOf("Sí, realicé viajes fuera de EE. UU.: $tripsVal.")
            }
            else -> q.sampleAnswersEs
        }
    }
}
