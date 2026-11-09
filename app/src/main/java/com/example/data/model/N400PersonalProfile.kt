package com.example.data.model

data class N400PersonalProfile(
    val gender: String = "Male", // "Male" or "Female"
    val fullName: String = "",
    val age: String = "",
    val dateOfBirth: String = "",
    val countryOfBirth: String = "",
    val cityOfBirth: String = "",
    val fatherName: String = "",
    val isFatherUSCitizen: Boolean = false,
    val motherName: String = "",
    val isMotherUSCitizen: Boolean = false,
    val maritalStatus: String = "Married", // "Married", "Single", "Divorced", "Widowed"
    val spouseName: String = "",
    val isSpouseUSCitizen: Boolean = false,
    val numberOfChildren: String = "",
    val childrenNames: String = "",
    val currentAddress: String = "",
    val yearsAtCurrentAddress: String = "",
    val occupation: String = "",
    val employer: String = "",
    val tripsOutsideUS: String = "",
    val yearsAsPermanentResident: String = "",
    val residenceStateCode: String = "CA"
) {
    val isMale: Boolean
        get() = gender.equals("Male", ignoreCase = true) || gender.equals("Masculino", ignoreCase = true)

    val isComplete: Boolean
        get() = fullName.isNotBlank() || age.isNotBlank() || countryOfBirth.isNotBlank()

    /**
     * Accurately calculates age based on dateOfBirth (e.g. "March 15, 1985" -> age in 2026).
     * If dateOfBirth is not set or parseable, falls back to age string or default.
     */
    val calculatedAge: Int
        get() {
            val yearRegex = Regex("""\b(19\d\d|20\d\d)\b""")
            val yearMatch = yearRegex.find(dateOfBirth)?.value?.toIntOrNull()
            if (yearMatch != null) {
                val currentYear = 2026
                return (currentYear - yearMatch).coerceIn(18, 120)
            }
            val parsedAge = age.filter { it.isDigit() }.toIntOrNull()
            if (parsedAge != null && parsedAge > 0) return parsedAge
            return 38
        }
}

data class PersonalDialogueItem(
    val id: String,
    val category: String,
    val questionEn: String,
    val questionInUserLang: String,
    val answerEn: String,
    val spokenTip: String
)

object N400DialogueGenerator {
    fun generate(profile: N400PersonalProfile, lang: AppLanguage): List<PersonalDialogueItem> {
        val nameVal = profile.fullName.ifBlank { "[Your Name]" }
        val ageVal = profile.calculatedAge.toString()
        val dobVal = profile.dateOfBirth.ifBlank { "[Your Birth Date]" }
        val cityVal = profile.cityOfBirth.ifBlank { "[Your Birth City]" }
        val countryVal = profile.countryOfBirth.ifBlank { "[Your Birth Country]" }
        val statusVal = profile.maritalStatus.ifBlank { "Married" }
        val spouseVal = profile.spouseName.ifBlank { "[Spouse Name]" }
        val fatherVal = profile.fatherName.ifBlank { "[Father's Name]" }
        val motherVal = profile.motherName.ifBlank { "[Mother's Name]" }
        val childrenCount = profile.numberOfChildren.ifBlank { "0" }
        val childrenVal = profile.childrenNames.ifBlank { "[Children Names]" }
        val addressVal = profile.currentAddress.ifBlank { "[Your Address]" }
        val addressYears = profile.yearsAtCurrentAddress.ifBlank { "several" }
        val jobVal = profile.occupation.ifBlank { "[Your Occupation]" }
        val employerVal = profile.employer.ifBlank { "[Your Employer]" }
        val tripsVal = profile.tripsOutsideUS.ifBlank { "none" }
        val lprYears = profile.yearsAsPermanentResident.ifBlank { "5" }

        val fatherCitizenText = if (profile.isFatherUSCitizen) "Yes, my father is a U.S. citizen." else "No, my father is not a U.S. citizen."
        val motherCitizenText = if (profile.isMotherUSCitizen) "Yes, my mother is a U.S. citizen." else "No, my mother is not a U.S. citizen."

        return listOf(
            PersonalDialogueItem(
                id = "1",
                category = if (lang == AppLanguage.SPANISH) "Nombre Legal" else "Legal Name",
                questionEn = "What is your full legal name?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuál es su nombre legal completo?"
                    AppLanguage.CHINESE -> "您的法定全名是什么？"
                    AppLanguage.VIETNAMESE -> "Tên đầy đủ hợp pháp của bạn là gì?"
                    AppLanguage.TAGALOG -> "Ano ang iyong buong legal na pangalan?"
                    AppLanguage.FRENCH -> "Quel est votre nom légal complet ?"
                    else -> "What is your full legal name?"
                },
                answerEn = "My full legal name is $nameVal.",
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Pronuncia con calma: 'My full legal name is...'"
                    else -> "Say clearly: 'My full legal name is...'"
                }
            ),
            PersonalDialogueItem(
                id = "2",
                category = if (lang == AppLanguage.SPANISH) "Edad y Nacimiento" else "Age & Birthday",
                questionEn = "How old are you and what is your date of birth?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuántos años tiene y cuál es su fecha de nacimiento?"
                    AppLanguage.CHINESE -> "您多大年纪？您的出生日期是哪天？"
                    AppLanguage.VIETNAMESE -> "Bạn bao nhiêu tuổi và ngày sinh của bạn là gì?"
                    else -> "How old are you and what is your date of birth?"
                },
                answerEn = "I am $ageVal years old, and I was born on $dobVal.",
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Di: 'I am $ageVal years old, and I was born on...'"
                    else -> "Respond: 'I am $ageVal years old, and I was born on...'"
                }
            ),
            PersonalDialogueItem(
                id = "3",
                category = if (lang == AppLanguage.SPANISH) "País de Origen" else "Birthplace",
                questionEn = "What is your country of birth and where were you born?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿En qué país nació y en qué ciudad?"
                    AppLanguage.CHINESE -> "您出生在哪个国家和城市？"
                    AppLanguage.VIETNAMESE -> "Bạn sinh ra ở quốc gia và thành phố nào?"
                    else -> "Where were you born?"
                },
                answerEn = "I was born in $cityVal, $countryVal.",
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Pronuncia: 'I was born in [Ciudad], [País]'"
                    else -> "Pronounce: 'I was born in [City], [Country]'"
                }
            ),
            PersonalDialogueItem(
                id = "4",
                category = if (lang == AppLanguage.SPANISH) "Padres y Familiares" else "Parents & Family",
                questionEn = "What are your parents' names, and are either of them U.S. citizens?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuáles son los nombres de sus padres y es alguno ciudadano de EE. UU.?"
                    AppLanguage.CHINESE -> "您父母的名字是什么？他们中有美国公民吗？"
                    AppLanguage.VIETNAMESE -> "Tên cha mẹ bạn là gì và có ai là công dân Hoa Kỳ không?"
                    else -> "What are your parents' names and are they U.S. citizens?"
                },
                answerEn = if (profile.fatherName.isNotBlank() || profile.motherName.isNotBlank()) {
                    "My father's name is $fatherVal and my mother's name is $motherVal. " +
                    if (!profile.isFatherUSCitizen && !profile.isMotherUSCitizen) {
                        "Neither of my parents is a U.S. citizen."
                    } else {
                        "$fatherCitizenText $motherCitizenText"
                    }
                } else if (!profile.isFatherUSCitizen && !profile.isMotherUSCitizen) {
                    "No, officer. Neither of my parents is a United States citizen."
                } else {
                    "$fatherCitizenText $motherCitizenText"
                },
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Menciona los nombres de tus padres con claridad."
                    else -> "State your father and mother's names clearly."
                }
            ),
            PersonalDialogueItem(
                id = "5",
                category = if (lang == AppLanguage.SPANISH) "Estado Civil" else "Marital Status",
                questionEn = "What is your current marital status?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuál es su estado civil actual?"
                    AppLanguage.CHINESE -> "您目前的婚姻状况是什么？"
                    AppLanguage.VIETNAMESE -> "Tình trạng hôn nhân hiện tại của bạn là gì?"
                    else -> "What is your marital status?"
                },
                answerEn = if (statusVal.equals("Married", ignoreCase = true)) {
                    val spouseCitizenNotice = if (profile.isSpouseUSCitizen) "My spouse is a U.S. citizen." else "My spouse is a lawful permanent resident."
                    "I am married, my spouse's name is $spouseVal, and $spouseCitizenNotice"
                } else {
                    "I am currently $statusVal."
                },
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Opciones comunes: 'I am married', 'I am single', 'I am divorced'."
                    else -> "Common answers: 'I am married', 'I am single', 'I am divorced'."
                }
            ),
            PersonalDialogueItem(
                id = "6",
                category = if (lang == AppLanguage.SPANISH) "Hijos" else "Children",
                questionEn = "How many children do you have?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuántos hijos tiene y cómo se llaman?"
                    AppLanguage.CHINESE -> "您有几个孩子？他们叫什么名字？"
                    AppLanguage.VIETNAMESE -> "Bạn có bao nhiêu người con?"
                    else -> "How many children do you have?"
                },
                answerEn = if (childrenCount == "0" || childrenCount.equals("none", ignoreCase = true)) {
                    "I do not have any children, officer."
                } else {
                    "I have $childrenCount children. Their names are $childrenVal."
                },
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Di claramente el número: 'I have [número] children.'"
                    else -> "State clearly: 'I have [number] children.'"
                }
            ),
            PersonalDialogueItem(
                id = "7",
                category = if (lang == AppLanguage.SPANISH) "Dirección y Residencia" else "Address & Residence",
                questionEn = "What is your current physical address?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuál es su dirección física actual en EE. UU.?"
                    AppLanguage.CHINESE -> "您目前的居住地址是什么？"
                    AppLanguage.VIETNAMESE -> "Địa chỉ nơi ở hiện tại của bạn là gì?"
                    else -> "What is your current physical address?"
                },
                answerEn = "My current address is $addressVal. I have lived there for $addressYears years.",
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Di tu dirección completa: calle, ciudad y estado."
                    else -> "State street number, street name, city and state."
                }
            ),
            PersonalDialogueItem(
                id = "8",
                category = if (lang == AppLanguage.SPANISH) "Empleo y Trabajo" else "Employment",
                questionEn = "What do you do for a living and who is your employer?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿En qué trabaja actualmente y para qué empresa?"
                    AppLanguage.CHINESE -> "您目前从事什么工作？雇主是谁？"
                    AppLanguage.VIETNAMESE -> "Công việc hiện tại của bạn là gì?"
                    else -> "What do you do for work?"
                },
                answerEn = "I work as an $jobVal for $employerVal.",
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Di: 'I work as a [ocupación] at [empleador]' o 'I am self-employed'."
                    else -> "Say: 'I work as a [job] at [company]' or 'I am self-employed'."
                }
            ),
            PersonalDialogueItem(
                id = "9",
                category = if (lang == AppLanguage.SPANISH) "Viajes Fuera de EE. UU." else "Travel History",
                questionEn = "Have you taken any trips outside of the United States in the last 5 years?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Ha realizado algún viaje fuera de EE. UU. en los últimos 5 años?"
                    AppLanguage.CHINESE -> "在过去5年里，您离开过美国旅行吗？"
                    AppLanguage.VIETNAMESE -> "Bạn có chuyến đi nào ra ngoài Hoa Kỳ trong 5 năm qua không?"
                    else -> "Have you traveled outside the US?"
                },
                answerEn = if (tripsVal.equals("none", ignoreCase = true) || tripsVal == "0") {
                    "No, officer. I have not taken any trips outside the United States."
                } else {
                    "Yes, officer. I took trips outside the United States: $tripsVal."
                },
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Ten presente la duración de tus viajes para no exceder los límites de residencia continua."
                    else -> "Be accurate with dates to verify continuous residence."
                }
            ),
            PersonalDialogueItem(
                id = "10",
                category = if (lang == AppLanguage.SPANISH) "Años de Residencia" else "Permanent Residency",
                questionEn = "How many years have you been a lawful permanent resident of the United States?",
                questionInUserLang = when (lang) {
                    AppLanguage.SPANISH -> "¿Cuántos años lleva siendo residente permanente legal (Green Card)?"
                    AppLanguage.CHINESE -> "您成为美国合法永久居民有多少年了？"
                    AppLanguage.VIETNAMESE -> "Bạn đã là thường trú nhân hợp pháp được bao nhiêu năm?"
                    else -> "How many years as a permanent resident?"
                },
                answerEn = "I have been a lawful permanent resident for $lprYears years.",
                spokenTip = when (lang) {
                    AppLanguage.SPANISH -> "Responde con seguridad: 'I have been a permanent resident for [X] years.'"
                    else -> "State: 'I have been a permanent resident for [X] years.'"
                }
            )
        )
    }
}
