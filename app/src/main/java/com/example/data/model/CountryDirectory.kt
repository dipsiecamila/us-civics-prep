package com.example.data.model

data class CountryCityInfo(
    val countryEn: String,
    val countryEs: String,
    val capital: String,
    val majorCities: List<String>,
    val localizedNames: Map<String, String> = emptyMap()
)

object CountryDirectory {
    val countries: List<CountryCityInfo> = listOf(
        // The Americas & Caribbean
        CountryCityInfo("Colombia", "Colombia", "Bogotá", listOf("Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena", "Bucaramanga", "Pereira", "Santa Marta"), mapOf("zh" to "哥倫比亞", "fr" to "Colombie", "vi" to "Colombia", "ar" to "كولومبيا", "ru" to "Колумбия", "ko" to "콜롬비아", "pt" to "Colômbia", "tl" to "Kolombya")),
        CountryCityInfo("Mexico", "México", "Ciudad de México", listOf("Ciudad de México", "Guadalajara", "Monterrey", "Puebla", "Tijuana", "León", "Juárez", "Cancún"), mapOf("zh" to "墨西哥", "fr" to "Mexique", "vi" to "Mexico", "ar" to "المكسيك", "ru" to "Мексика", "ko" to "멕시코", "pt" to "México", "tl" to "Mehiko")),
        CountryCityInfo("Cuba", "Cuba", "La Habana", listOf("La Habana", "Santiago de Cuba", "Camagüey", "Holguín", "Santa Clara", "Guantánamo", "Cienfuegos"), mapOf("zh" to "古巴", "fr" to "Cuba", "vi" to "Cuba", "ar" to "كوبا", "ru" to "Куба", "ko" to "쿠바", "pt" to "Cuba", "tl" to "Kuba")),
        CountryCityInfo("Dominican Republic", "República Dominicana", "Santo Domingo", listOf("Santo Domingo", "Santiago de los Caballeros", "La Romana", "San Pedro de Macorís", "Puerto Plata", "San Cristóbal"), mapOf("zh" to "多明尼加共和國", "fr" to "République dominicaine", "vi" to "Cộng hòa Dominica", "ar" to "جمهورية الدومينيكان", "pt" to "República Dominicana")),
        CountryCityInfo("El Salvador", "El Salvador", "San Salvador", listOf("San Salvador", "Santa Ana", "San Miguel", "Soyapango", "Apopa", "Delgado"), mapOf("zh" to "薩爾瓦多", "fr" to "Salvador", "vi" to "El Salvador", "ar" to "السلفادور", "pt" to "El Salvador")),
        CountryCityInfo("Guatemala", "Guatemala", "Ciudad de Guatemala", listOf("Ciudad de Guatemala", "Quetzaltenango", "Escuintla", "Mixco", "Villa Nueva", "Cobán"), mapOf("zh" to "瓜地馬拉", "fr" to "Guatemala", "vi" to "Guatemala", "ar" to "غواتيمالا", "pt" to "Guatemala")),
        CountryCityInfo("Honduras", "Honduras", "Tegucigalpa", listOf("Tegucigalpa", "San Pedro Sula", "La Ceiba", "Choloma", "El Progreso", "Comayagua"), mapOf("zh" to "宏都拉斯", "fr" to "Honduras", "vi" to "Honduras", "ar" to "هندوراس", "pt" to "Honduras")),
        CountryCityInfo("Venezuela", "Venezuela", "Caracas", listOf("Caracas", "Maracaibo", "Valencia", "Barquisimeto", "Maracay", "Ciudad Guayana", "San Cristóbal"), mapOf("zh" to "委內瑞拉", "fr" to "Venezuela", "vi" to "Venezuela", "ar" to "فنزويلا", "pt" to "Venezuela")),
        CountryCityInfo("Peru", "Perú", "Lima", listOf("Lima", "Arequipa", "Trujillo", "Chiclayo", "Cusco", "Piura", "Iquitos", "Huancayo"), mapOf("zh" to "秘魯", "fr" to "Pérou", "vi" to "Peru", "ar" to "بيرو", "pt" to "Peru")),
        CountryCityInfo("Ecuador", "Ecuador", "Quito", listOf("Quito", "Guayaquil", "Cuenca", "Santo Domingo", "Machala", "Manta", "Portoviejo"), mapOf("zh" to "厄瓜多", "fr" to "Équateur", "vi" to "Ecuador", "ar" to "الإكوادور", "pt" to "Equador")),
        CountryCityInfo("Argentina", "Argentina", "Buenos Aires", listOf("Buenos Aires", "Córdoba", "Rosario", "Mendoza", "La Plata", "San Miguel de Tucumán", "Mar del Plata"), mapOf("zh" to "阿根廷", "fr" to "Argentine", "vi" to "Argentina", "ar" to "الأرجنتين", "pt" to "Argentina")),
        CountryCityInfo("Chile", "Chile", "Santiago", listOf("Santiago", "Valparaíso", "Concepción", "La Serena", "Antofagasta", "Temuco", "Rancagua"), mapOf("zh" to "智利", "fr" to "Chili", "vi" to "Chile", "ar" to "تشيلي", "pt" to "Chile")),
        CountryCityInfo("Nicaragua", "Nicaragua", "Managua", listOf("Managua", "León", "Granada", "Masaya", "Chinandega", "Estelí"), mapOf("zh" to "尼加拉瓜", "fr" to "Nicaragua", "vi" to "Nicaragua", "ar" to "نيكاراغوا", "pt" to "Nicarágua")),
        CountryCityInfo("Costa Rica", "Costa Rica", "San José", listOf("San José", "Alajuela", "Heredia", "Cartago", "Puntarenas", "Limón", "Liberia"), mapOf("zh" to "哥斯大黎加", "fr" to "Costa Rica", "vi" to "Costa Rica", "ar" to "كوستاريكا", "pt" to "Costa Rica")),
        CountryCityInfo("Panama", "Panamá", "Ciudad de Panamá", listOf("Ciudad de Panamá", "San Miguelito", "David", "Colón", "La Chorrera", "Santiago"), mapOf("zh" to "巴拿馬", "fr" to "Panama", "vi" to "Panama", "ar" to "بنما", "pt" to "Panamá")),
        CountryCityInfo("Bolivia", "Bolivia", "La Paz", listOf("La Paz", "Santa Cruz de la Sierra", "Cochabamba", "Sucre", "Oruro", "Tarija"), mapOf("zh" to "玻利維亞", "fr" to "Bolivie", "vi" to "Bolivia", "ar" to "بوليفيا", "pt" to "Bolívia")),
        CountryCityInfo("Paraguay", "Paraguay", "Asunción", listOf("Asunción", "Ciudad del Este", "San Lorenzo", "Luque", "Capiatá", "Lambaré"), mapOf("zh" to "巴拉圭", "fr" to "Paraguay", "vi" to "Paraguay", "ar" to "باراغواي", "pt" to "Paraguai")),
        CountryCityInfo("Uruguay", "Uruguay", "Montevideo", listOf("Montevideo", "Salto", "Ciudad de la Costa", "Paysandú", "Las Piedras", "Maldonado"), mapOf("zh" to "烏拉圭", "fr" to "Uruguay", "vi" to "Uruguay", "ar" to "أوروغواي", "pt" to "Uruguai")),
        CountryCityInfo("Brazil", "Brasil", "Brasília", listOf("Brasília", "São Paulo", "Rio de Janeiro", "Salvador", "Fortaleza", "Belo Horizonte", "Manaus", "Curitiba"), mapOf("zh" to "巴西", "fr" to "Brésil", "vi" to "Brazil", "ar" to "البرازيل", "ru" to "Бразилия", "ko" to "브라질", "pt" to "Brasil")),
        CountryCityInfo("Haiti", "Haití", "Port-au-Prince", listOf("Port-au-Prince", "Cap-Haïtien", "Gonaïves", "Les Cayes", "Saint-Marc", "Port-de-Paix"), mapOf("zh" to "海地", "fr" to "Haïti", "vi" to "Haiti", "ar" to "هايتي", "ht" to "Ayiti", "pt" to "Haiti")),
        CountryCityInfo("Jamaica", "Jamaica", "Kingston", listOf("Kingston", "Montego Bay", "Spanish Town", "Portmore", "May Pen", "Mandeville"), mapOf("zh" to "牙買加", "fr" to "Jamaïque", "vi" to "Jamaica", "ar" to "جامايكا", "pt" to "Jamaica")),
        CountryCityInfo("Trinidad and Tobago", "Trinidad y Tobago", "Port of Spain", listOf("Port of Spain", "Chaguanas", "San Fernando", "Arima", "Point Fortin"), mapOf("zh" to "千里達及托巴哥", "fr" to "Trinité-et-Tobago", "es" to "Trinidad y Tobago")),
        CountryCityInfo("Guyana", "Guyana", "Georgetown", listOf("Georgetown", "Linden", "New Amsterdam", "Anna Regina", "Bartica"), mapOf("zh" to "蓋亞那", "fr" to "Guyana", "es" to "Guyana")),
        CountryCityInfo("Suriname", "Surinam", "Paramaribo", listOf("Paramaribo", "Lelydorp", "Nieuw Nickerie", "Moengo"), mapOf("zh" to "蘇利南", "fr" to "Suriname", "es" to "Surinam")),
        CountryCityInfo("Belize", "Belice", "Belmopan", listOf("Belmopan", "Belize City", "San Ignacio", "Orange Walk Town", "San Pedro"), mapOf("zh" to "貝里斯", "fr" to "Belize", "es" to "Belice")),
        CountryCityInfo("Bahamas", "Bahamas", "Nassau", listOf("Nassau", "Freeport", "West End", "Coopers Town"), mapOf("zh" to "巴哈馬", "fr" to "Bahamas", "es" to "Bahamas")),
        CountryCityInfo("Barbados", "Barbados", "Bridgetown", listOf("Bridgetown", "Speightstown", "Oistins", "Bathsheba"), mapOf("zh" to "巴貝多", "fr" to "Barbade", "es" to "Barbados")),
        CountryCityInfo("Canada", "Canadá", "Ottawa", listOf("Ottawa", "Toronto", "Montreal", "Vancouver", "Calgary", "Edmonton", "Quebec City", "Winnipeg"), mapOf("zh" to "加拿大", "fr" to "Canada", "vi" to "Canada", "ar" to "كندا", "ru" to "Канада", "ko" to "캐나다", "pt" to "Canadá")),
        CountryCityInfo("United States", "Estados Unidos", "Washington D.C.", listOf("Washington D.C.", "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Miami", "San Francisco"), mapOf("zh" to "美國", "fr" to "États-Unis", "vi" to "Hoa Kỳ", "ar" to "الولايات المتحدة", "ru" to "США", "ko" to "미국", "pt" to "Estados Unidos", "tl" to "Estados Unidos", "ht" to "Etazini")),

        // Asia & Oceania
        CountryCityInfo("China", "China", "Beijing", listOf("Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Chengdu", "Wuhan", "Tianjin", "Chongqing"), mapOf("zh" to "中國", "fr" to "Chine", "vi" to "Trung Quốc", "ar" to "الصين", "ru" to "Китай", "ko" to "중국", "pt" to "China")),
        CountryCityInfo("Taiwan", "Taiwán", "Taipei", listOf("Taipei", "Kaohsiung", "Taichung", "Tainan", "Hsinchu", "Taoyuan", "Keelung"), mapOf("zh" to "台灣", "fr" to "Taïwan", "vi" to "Đài Loan", "ar" to "تايوان", "ru" to "Тайвань", "ko" to "대만", "pt" to "Taiwan")),
        CountryCityInfo("Philippines", "Filipinas", "Manila", listOf("Manila", "Quezon City", "Davao City", "Cebu City", "Zamboanga City", "Taguig", "Pasig", "Cagayan de Oro"), mapOf("zh" to "菲律賓", "fr" to "Philippines", "vi" to "Philippines", "ar" to "الفلبين", "ru" to "Филиппины", "ko" to "필리핀", "tl" to "Pilipinas", "pt" to "Filipinas")),
        CountryCityInfo("Vietnam", "Vietnam", "Hanoi", listOf("Hanoi", "Ho Chi Minh City", "Da Nang", "Hai Phong", "Can Tho", "Bien Hoa", "Nha Trang", "Hue"), mapOf("zh" to "越南", "fr" to "Viêt Nam", "vi" to "Việt Nam", "ar" to "فيتنام", "ru" to "Вьетнам", "ko" to "베트남", "pt" to "Vietnã")),
        CountryCityInfo("India", "India", "New Delhi", listOf("New Delhi", "Mumbai", "Bengaluru", "Hyderabad", "Chennai", "Kolkata", "Ahmedabad", "Pune"), mapOf("zh" to "印度", "fr" to "Inde", "vi" to "Ấn Độ", "ar" to "الهند", "ru" to "Индия", "ko" to "인도", "hi" to "भारत", "gu" to "ભારત", "te" to "భారతదేశం", "bn" to "ভারত")),
        CountryCityInfo("South Korea", "Corea del Sur", "Seoul", listOf("Seoul", "Busan", "Incheon", "Daegu", "Daejeon", "Gwangju", "Suwon", "Ulsan"), mapOf("zh" to "韓國", "fr" to "Corée du Sud", "vi" to "Hàn Quốc", "ar" to "كوريا الجنوبية", "ru" to "Южная Корея", "ko" to "대한민국", "pt" to "Coreia do Sul")),
        CountryCityInfo("Japan", "Japón", "Tokyo", listOf("Tokyo", "Yokohama", "Osaka", "Nagoya", "Sapporo", "Fukuoka", "Kobe", "Kyoto"), mapOf("zh" to "日本", "fr" to "Japon", "vi" to "Nhật Bản", "ar" to "اليابان", "ru" to "Япония", "ko" to "일본", "ja" to "日本", "pt" to "Japão")),
        CountryCityInfo("Pakistan", "Pakistán", "Islamabad", listOf("Islamabad", "Karachi", "Lahore", "Faisalabad", "Rawalpindi", "Multan", "Peshawar"), mapOf("zh" to "巴基斯坦", "fr" to "Pakistan", "vi" to "Pakistan", "ar" to "باكستان", "pt" to "Paquistão")),
        CountryCityInfo("Bangladesh", "Bangladés", "Dhaka", listOf("Dhaka", "Chittagong", "Khulna", "Rajshahi", "Sylhet", "Barisal"), mapOf("zh" to "孟加拉", "fr" to "Bangladesh", "vi" to "Bangladesh", "ar" to "بنغلاديش", "bn" to "বাংলাদেশ", "pt" to "Bangladesh")),
        CountryCityInfo("Iran", "Irán", "Tehran", listOf("Tehran", "Mashhad", "Isfahan", "Karaj", "Shiraz", "Tabriz", "Qom"), mapOf("zh" to "伊朗", "fr" to "Iran", "vi" to "Iran", "ar" to "إيران", "fa" to "ایران", "ru" to "Иран", "pt" to "Irã")),
        CountryCityInfo("Iraq", "Irak", "Baghdad", listOf("Baghdad", "Basra", "Mosul", "Erbil", "Najaf", "Kirkuk", "Sulaymaniyah"), mapOf("zh" to "伊拉克", "fr" to "Irak", "vi" to "Iraq", "ar" to "العراق", "pt" to "Iraque")),
        CountryCityInfo("Syria", "Siria", "Damascus", listOf("Damascus", "Aleppo", "Homs", "Latakia", "Hama", "Raqqa"), mapOf("zh" to "敘利亞", "fr" to "Syrie", "vi" to "Syria", "ar" to "سوريا", "pt" to "Síria")),
        CountryCityInfo("Lebanon", "Líbano", "Beirut", listOf("Beirut", "Tripoli", "Sidon", "Tyre", "Jounieh", "Zahlé"), mapOf("zh" to "黎巴嫩", "fr" to "Liban", "vi" to "Li-băng", "ar" to "لبنان", "pt" to "Líbano")),
        CountryCityInfo("Jordan", "Jordania", "Amman", listOf("Amman", "Zarqa", "Irbid", "Russeifa", "Aqaba", "Madaba"), mapOf("zh" to "約旦", "fr" to "Jordanie", "vi" to "Jordan", "ar" to "الأردن", "pt" to "Jordânia")),
        CountryCityInfo("Israel", "Israel", "Jerusalem", listOf("Jerusalem", "Tel Aviv", "Haifa", "Rishon LeZion", "Petah Tikva", "Ashdod"), mapOf("zh" to "以色列", "fr" to "Israël", "vi" to "Israel", "ar" to "إسرائيل", "pt" to "Israel")),
        CountryCityInfo("Palestine", "Palestina", "Ramallah", listOf("Ramallah", "Gaza City", "Hebron", "Nablus", "Bethlehem", "Jericho"), mapOf("zh" to "巴勒斯坦", "fr" to "Palestine", "vi" to "Palestine", "ar" to "فلسطين", "pt" to "Palestina")),
        CountryCityInfo("Saudi Arabia", "Arabia Saudita", "Riyadh", listOf("Riyadh", "Jeddah", "Mecca", "Medina", "Dammam", "Khobar"), mapOf("zh" to "沙烏地阿拉伯", "fr" to "Arabie saoudite", "vi" to "Ả Rập Xê Út", "ar" to "المملكة العربية السعودية", "pt" to "Arábia Saudita")),
        CountryCityInfo("United Arab Emirates", "Emiratos Árabes Unidos", "Abu Dhabi", listOf("Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Al Ain", "Ras Al Khaimah"), mapOf("zh" to "阿拉伯聯合大公國", "fr" to "Émirats arabes unis", "vi" to "UAE", "ar" to "الإمارات العربية المتحدة", "pt" to "Emirados Árabes Unidos")),
        CountryCityInfo("Kuwait", "Kuwait", "Kuwait City", listOf("Kuwait City", "Hawalli", "Salmiya", "Al Ahmadi"), mapOf("zh" to "科威特", "fr" to "Koweït", "ar" to "الكويت", "pt" to "Kuwait")),
        CountryCityInfo("Qatar", "Catar", "Doha", listOf("Doha", "Al Rayyan", "Al Wakrah", "Al Khor"), mapOf("zh" to "卡達", "fr" to "Qatar", "ar" to "قطر", "pt" to "Catar")),
        CountryCityInfo("Yemen", "Yemen", "Sana'a", listOf("Sana'a", "Aden", "Taiz", "Al Hudaydah", "Ibb"), mapOf("zh" to "葉門", "fr" to "Yémen", "ar" to "اليمن", "pt" to "Iêmen")),
        CountryCityInfo("Afghanistan", "Afganistán", "Kabul", listOf("Kabul", "Kandahar", "Herat", "Mazar-i-Sharif", "Jalalabad"), mapOf("zh" to "阿富汗", "fr" to "Afghanistan", "ar" to "أفغانستان", "fa" to "افغانستان", "pt" to "Afeganistão")),
        CountryCityInfo("Thailand", "Tailandia", "Bangkok", listOf("Bangkok", "Nonthaburi", "Chiang Mai", "Chonburi", "Pattaya", "Phuket"), mapOf("zh" to "泰國", "fr" to "Thaïlande", "vi" to "Thái Lan", "ar" to "تايلاند", "pt" to "Tailândia")),
        CountryCityInfo("Indonesia", "Indonesia", "Jakarta", listOf("Jakarta", "Surabaya", "Bandung", "Medan", "Semarang", "Makassar", "Palembang"), mapOf("zh" to "印尼", "fr" to "Indonésie", "vi" to "Indonesia", "ar" to "إندونيسيا", "pt" to "Indonésia")),
        CountryCityInfo("Malaysia", "Malasia", "Kuala Lumpur", listOf("Kuala Lumpur", "George Town", "Johor Bahru", "Ipoh", "Shah Alam"), mapOf("zh" to "馬來西亞", "fr" to "Malaisie", "vi" to "Malaysia", "ar" to "ماليزيا", "pt" to "Malásia")),
        CountryCityInfo("Singapore", "Singapur", "Singapore", listOf("Singapore"), mapOf("zh" to "新加坡", "fr" to "Singapour", "vi" to "Singapore", "ar" to "سنغافورة", "pt" to "Singapura")),
        CountryCityInfo("Myanmar", "Myanmar", "Naypyidaw", listOf("Naypyidaw", "Yangon", "Mandalay", "Mawlamyine"), mapOf("zh" to "緬甸", "fr" to "Birmanie", "vi" to "Myanmar", "ar" to "ميانمار", "pt" to "Mianmar")),
        CountryCityInfo("Cambodia", "Camboya", "Phnom Penh", listOf("Phnom Penh", "Siem Reap", "Battambang", "Sihanoukville"), mapOf("zh" to "柬埔寨", "fr" to "Cambodge", "vi" to "Campuchia", "ar" to "كمبوديا", "pt" to "Camboja")),
        CountryCityInfo("Laos", "Laos", "Vientiane", listOf("Vientiane", "Pakse", "Savannakhet", "Luang Prabang"), mapOf("zh" to "寮國", "fr" to "Laos", "vi" to "Lào", "ar" to "لاوس", "pt" to "Laos")),
        CountryCityInfo("Nepal", "Nepal", "Kathmandu", listOf("Kathmandu", "Pokhara", "Lalitpur", "Bharatpur", "Biratnagar"), mapOf("zh" to "尼泊爾", "fr" to "Népal", "vi" to "Nepal", "ar" to "نيبال", "hi" to "नेपाल", "pt" to "Nepal")),
        CountryCityInfo("Sri Lanka", "Sri Lanka", "Colombo", listOf("Colombo", "Kandy", "Galle", "Jaffna", "Negombo"), mapOf("zh" to "斯里蘭卡", "fr" to "Sri Lanka", "vi" to "Sri Lanka", "ar" to "سريلانكا", "pt" to "Sri Lanka")),
        CountryCityInfo("Australia", "Australia", "Canberra", listOf("Canberra", "Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide", "Gold Coast"), mapOf("zh" to "澳大利亞", "fr" to "Australie", "vi" to "Úc", "ar" to "أستراليا", "ru" to "Австралия", "pt" to "Austrália")),
        CountryCityInfo("New Zealand", "Nueva Zelanda", "Wellington", listOf("Wellington", "Auckland", "Christchurch", "Hamilton", "Tauranga"), mapOf("zh" to "紐西蘭", "fr" to "Nouvelle-Zélande", "vi" to "New Zealand", "ar" to "نيوزيلندا", "pt" to "Nova Zelândia")),

        // Europe
        CountryCityInfo("Spain", "España", "Madrid", listOf("Madrid", "Barcelona", "Valencia", "Sevilla", "Zaragoza", "Málaga", "Murcia", "Palma", "Bilbao"), mapOf("zh" to "西班牙", "fr" to "Espagne", "vi" to "Tây Ban Nha", "ar" to "إسبانيا", "ru" to "Испания", "ko" to "스페인", "pt" to "Espanha")),
        CountryCityInfo("France", "Francia", "Paris", listOf("Paris", "Marseille", "Lyon", "Toulouse", "Nice", "Nantes", "Strasbourg", "Montpellier", "Bordeaux"), mapOf("zh" to "法國", "fr" to "France", "vi" to "Pháp", "ar" to "فرنسا", "ru" to "Франция", "ko" to "프랑스", "pt" to "França")),
        CountryCityInfo("Germany", "Alemania", "Berlin", listOf("Berlin", "Munich", "Frankfurt", "Hamburg", "Cologne", "Stuttgart", "Düsseldorf", "Dortmund"), mapOf("zh" to "德國", "fr" to "Allemagne", "vi" to "Đức", "ar" to "ألمانيا", "ru" to "Германия", "ko" to "독일", "de" to "Deutschland", "pt" to "Alemanha")),
        CountryCityInfo("Italy", "Italia", "Rome", listOf("Rome", "Milan", "Naples", "Turin", "Palermo", "Genoa", "Bologna", "Florence", "Venice"), mapOf("zh" to "義大利", "fr" to "Italie", "vi" to "Ý", "ar" to "إيطاليا", "ru" to "Италия", "ko" to "이탈리아", "it" to "Italia", "pt" to "Itália")),
        CountryCityInfo("United Kingdom", "Reino Unido", "London", listOf("London", "Manchester", "Birmingham", "Edinburgh", "Glasgow", "Liverpool", "Bristol", "Leeds"), mapOf("zh" to "英國", "fr" to "Royaume-Uni", "vi" to "Vương quốc Anh", "ar" to "المملكة المتحدة", "ru" to "Великобритания", "pt" to "Reino Unido")),
        CountryCityInfo("Portugal", "Portugal", "Lisbon", listOf("Lisbon", "Porto", "Vila Nova de Gaia", "Amadora", "Braga", "Funchal", "Coimbra"), mapOf("zh" to "葡萄牙", "fr" to "Portugal", "vi" to "Bồ Đào Nha", "ar" to "البرتغال", "ru" to "Португалия", "pt" to "Portugal")),
        CountryCityInfo("Russia", "Rusia", "Moscow", listOf("Moscow", "Saint Petersburg", "Novosibirsk", "Yekaterinburg", "Kazan", "Nizhny Novgorod", "Chelyabinsk", "Samara"), mapOf("zh" to "俄羅斯", "fr" to "Russie", "vi" to "Nga", "ar" to "روسيا", "ru" to "Россия", "ko" to "러시아", "pt" to "Rússia")),
        CountryCityInfo("Ukraine", "Ucrania", "Kyiv", listOf("Kyiv", "Kharkiv", "Odesa", "Dnipro", "Lviv", "Zaporizhzhia", "Vinnytsia"), mapOf("zh" to "烏克蘭", "fr" to "Ukraine", "vi" to "Ukraina", "ar" to "أوكرانيا", "ru" to "Украина", "pt" to "Ucrânia")),
        CountryCityInfo("Poland", "Polonia", "Warsaw", listOf("Warsaw", "Kraków", "Łódź", "Wrocław", "Poznań", "Gdańsk", "Szczecin"), mapOf("zh" to "波蘭", "fr" to "Pologne", "vi" to "Ba Lan", "ar" to "بولندا", "pl" to "Polska", "ru" to "Польша", "pt" to "Polônia")),
        CountryCityInfo("Netherlands", "Países Bajos", "Amsterdam", listOf("Amsterdam", "Rotterdam", "The Hague", "Utrecht", "Eindhoven", "Groningen"), mapOf("zh" to "荷蘭", "fr" to "Pays-Bas", "vi" to "Hà Lan", "ar" to "هولندا", "pt" to "Países Baixos")),
        CountryCityInfo("Belgium", "Bélgica", "Brussels", listOf("Brussels", "Antwerp", "Ghent", "Charleroi", "Liège", "Bruges"), mapOf("zh" to "比利時", "fr" to "Belgique", "vi" to "Bỉ", "ar" to "بلجيكا", "pt" to "Bélgica")),
        CountryCityInfo("Switzerland", "Suiza", "Bern", listOf("Bern", "Zurich", "Geneva", "Basel", "Lausanne", "Lucerne"), mapOf("zh" to "瑞士", "fr" to "Suisse", "vi" to "Thụy Sĩ", "ar" to "سويسرا", "pt" to "Suíça")),
        CountryCityInfo("Sweden", "Suecia", "Stockholm", listOf("Stockholm", "Gothenburg", "Malmö", "Uppsala", "Västerås"), mapOf("zh" to "瑞典", "fr" to "Suède", "vi" to "Thụy Điển", "ar" to "السويد", "pt" to "Suécia")),
        CountryCityInfo("Norway", "Noruega", "Oslo", listOf("Oslo", "Bergen", "Trondheim", "Stavanger", "Drammen"), mapOf("zh" to "挪威", "fr" to "Norvège", "vi" to "Na Uy", "ar" to "النرويج", "pt" to "Noruega")),
        CountryCityInfo("Denmark", "Dinamarca", "Copenhagen", listOf("Copenhagen", "Aarhus", "Odense", "Aalborg", "Esbjerg"), mapOf("zh" to "丹麥", "fr" to "Danemark", "vi" to "Đan Mạch", "ar" to "الدنمارك", "pt" to "Dinamarca")),
        CountryCityInfo("Finland", "Finlandia", "Helsinki", listOf("Helsinki", "Espoo", "Tampere", "Vantaa", "Oulu", "Turku"), mapOf("zh" to "芬蘭", "fr" to "Finlande", "vi" to "Phần Lan", "ar" to "فنلندا", "pt" to "Finlândia")),
        CountryCityInfo("Ireland", "Irlanda", "Dublin", listOf("Dublin", "Cork", "Limerick", "Galway", "Waterford"), mapOf("zh" to "愛爾蘭", "fr" to "Irlande", "vi" to "Ireland", "ar" to "أيرلندا", "pt" to "Irlanda")),
        CountryCityInfo("Greece", "Grecia", "Athens", listOf("Athens", "Thessaloniki", "Patras", "Heraklion", "Larissa"), mapOf("zh" to "希臘", "fr" to "Grèce", "vi" to "Hy Lạp", "ar" to "اليونان", "pt" to "Grécia")),
        CountryCityInfo("Turkey", "Turquía", "Ankara", listOf("Ankara", "Istanbul", "Izmir", "Bursa", "Antalya", "Adana", "Gaziantep"), mapOf("zh" to "土耳其", "fr" to "Turquie", "vi" to "Thổ Nhĩ Kỳ", "ar" to "تركيا", "pt" to "Turquia")),
        CountryCityInfo("Romania", "Rumania", "Bucharest", listOf("Bucharest", "Cluj-Napoca", "Timișoara", "Iași", "Constanța", "Craiova"), mapOf("zh" to "羅馬尼亞", "fr" to "Roumanie", "vi" to "România", "ar" to "رومانيا", "pt" to "Romênia")),
        CountryCityInfo("Czech Republic", "República Checa", "Prague", listOf("Prague", "Brno", "Ostrava", "Plzeň", "Liberec"), mapOf("zh" to "捷克", "fr" to "République tchèque", "ar" to "التشيك", "pt" to "República Tcheca")),
        CountryCityInfo("Hungary", "Hungría", "Budapest", listOf("Budapest", "Debrecen", "Szeged", "Miskolc", "Pécs"), mapOf("zh" to "匈牙利", "fr" to "Hongrie", "ar" to "المجر", "pt" to "Hungria")),
        CountryCityInfo("Austria", "Austria", "Vienna", listOf("Vienna", "Graz", "Linz", "Salzburg", "Innsbruck"), mapOf("zh" to "奧地利", "fr" to "Autriche", "ar" to "النمسا", "pt" to "Áustria")),

        // Africa
        CountryCityInfo("Nigeria", "Nigeria", "Abuja", listOf("Abuja", "Lagos", "Kano", "Ibadan", "Port Harcourt", "Benin City", "Kaduna"), mapOf("zh" to "奈及利亞", "fr" to "Nigéria", "ar" to "نيجيريا", "pt" to "Nigéria")),
        CountryCityInfo("Egypt", "Egipto", "Cairo", listOf("Cairo", "Alexandria", "Giza", "Shubra El Kheima", "Port Said", "Suez", "Luxor"), mapOf("zh" to "埃及", "fr" to "Égypte", "ar" to "مصر", "pt" to "Egito")),
        CountryCityInfo("Ethiopia", "Etiopía", "Addis Ababa", listOf("Addis Ababa", "Dire Dawa", "Mekelle", "Gondar", "Hawassa", "Bahir Dar"), mapOf("zh" to "衣索比亞", "fr" to "Éthiopie", "ar" to "إثيوبيا", "pt" to "Etiópia")),
        CountryCityInfo("Ghana", "Ghana", "Accra", listOf("Accra", "Kumasi", "Tamale", "Sekondi-Takoradi", "Cape Coast"), mapOf("zh" to "迦納", "fr" to "Ghana", "ar" to "غانا", "pt" to "Gana")),
        CountryCityInfo("Kenya", "Kenia", "Nairobi", listOf("Nairobi", "Mombasa", "Kisumu", "Nakuru", "Eldoret", "Malindi"), mapOf("zh" to "肯亞", "fr" to "Kenya", "ar" to "كينيا", "pt" to "Quênia")),
        CountryCityInfo("South Africa", "Sudáfrica", "Pretoria", listOf("Pretoria", "Johannesburg", "Cape Town", "Durban", "Gqeberha", "Bloemfontein"), mapOf("zh" to "南非", "fr" to "Afrique du Sud", "ar" to "جنوب إفريقيا", "pt" to "África do Sul")),
        CountryCityInfo("Morocco", "Marruecos", "Rabat", listOf("Rabat", "Casablanca", "Fez", "Tangier", "Marrakesh", "Agadir", "Meknes"), mapOf("zh" to "摩洛哥", "fr" to "Maroc", "ar" to "المغرب", "pt" to "Marrocos")),
        CountryCityInfo("Algeria", "Argelia", "Algiers", listOf("Algiers", "Oran", "Constantine", "Annaba", "Blida", "Batna"), mapOf("zh" to "阿爾及利亞", "fr" to "Algérie", "ar" to "الجزائر", "pt" to "Argélia")),
        CountryCityInfo("Tunisia", "Túnez", "Tunis", listOf("Tunis", "Sfax", "Sousse", "Kairouan", "Bizerte", "Gabès"), mapOf("zh" to "突尼西亞", "fr" to "Tunisie", "ar" to "تونس", "pt" to "Tunísia")),
        CountryCityInfo("Cameroon", "Camerún", "Yaoundé", listOf("Yaoundé", "Douala", "Garoua", "Kousséri", "Bamenda", "Maroua"), mapOf("zh" to "喀麥隆", "fr" to "Cameroun", "ar" to "الكاميرون", "pt" to "Camarões")),
        CountryCityInfo("Ivory Coast", "Costa de Marfil", "Yamoussoukro", listOf("Yamoussoukro", "Abidjan", "Bouaké", "Daloa", "San-Pédro"), mapOf("zh" to "象牙海岸", "fr" to "Côte d'Ivoire", "ar" to "ساحل العاج", "pt" to "Costa do Marfim")),
        CountryCityInfo("Senegal", "Senegal", "Dakar", listOf("Dakar", "Touba", "Thiès", "Rufisque", "Kaolack", "Saint-Louis"), mapOf("zh" to "塞內加爾", "fr" to "Sénégal", "ar" to "السنغال", "pt" to "Senegal")),
        CountryCityInfo("Democratic Republic of the Congo", "República Democrática del Congo", "Kinshasa", listOf("Kinshasa", "Lubumbashi", "Mbuji-Mayi", "Kananga", "Kisangani"), mapOf("zh" to "剛果民主共和國", "fr" to "RDC", "ar" to "جمهورية الكونغو الديمقراطية", "pt" to "República Democrática do Congo")),
        CountryCityInfo("Somalia", "Somalia", "Mogadishu", listOf("Mogadishu", "Hargeisa", "Kismayo", "Berbera", "Merca"), mapOf("zh" to "索馬利亞", "fr" to "Somalie", "ar" to "الصومال", "pt" to "Somália")),
        CountryCityInfo("Sudan", "Sudán", "Khartoum", listOf("Khartoum", "Omdurman", "Khartoum North", "Nyala", "Port Sudan"), mapOf("zh" to "蘇丹", "fr" to "Soudan", "ar" to "السودان", "pt" to "Sudão"))
    )

    fun getCountryDisplayName(country: CountryCityInfo, lang: AppLanguage): String {
        return country.localizedNames[lang.code] ?: when (lang) {
            AppLanguage.SPANISH -> country.countryEs
            AppLanguage.ENGLISH -> country.countryEn
            else -> country.countryEs
        }
    }

    fun searchCountries(query: String, lang: AppLanguage = AppLanguage.SPANISH): List<CountryCityInfo> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return countries
        return countries.filter { c ->
            val local = getCountryDisplayName(c, lang).lowercase()
            c.countryEn.lowercase().contains(q) ||
            c.countryEs.lowercase().contains(q) ||
            local.contains(q) ||
            c.localizedNames.values.any { it.lowercase().contains(q) }
        }
    }

    fun getCitiesForCountry(countryName: String, cityQuery: String = ""): List<String> {
        val cleanCountry = countryName.trim().lowercase()
        val match = countries.firstOrNull { c ->
            c.countryEn.lowercase() == cleanCountry ||
            c.countryEs.lowercase() == cleanCountry ||
            c.localizedNames.values.any { it.lowercase() == cleanCountry } ||
            cleanCountry.contains(c.countryEn.lowercase()) ||
            cleanCountry.contains(c.countryEs.lowercase())
        }

        val baseCities = match?.majorCities ?: listOf(
            "Bogotá", "Ciudad de México", "La Habana", "Santo Domingo", "San Salvador",
            "Lima", "Caracas", "Buenos Aires", "Madrid", "Paris", "London", "Taipei",
            "Manila", "Hanoi", "Seoul", "Tokyo", "Cairo", "Port-au-Prince"
        )
        val q = cityQuery.trim().lowercase()
        return if (q.isBlank()) baseCities else baseCities.filter { it.lowercase().contains(q) }
    }
}
