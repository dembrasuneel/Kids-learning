package com.example.data.model

enum class ClassTier {
    PREP,
    LOWER_PRIMARY,
    MIDDLE_PRIMARY,
    UPPER_PRIMARY
}

enum class SchoolClass(
    val id: String,
    val displayName: String,
    val ageRange: String,
    val tier: ClassTier,
    val tagline: String
) {
    PREP_1("prep_1", "Prep 1", "Ages 3-4", ClassTier.PREP, "First steps with ABCs, numbers & colors"),
    PREP_2("prep_2", "Prep 2", "Ages 4-5", ClassTier.PREP, "Phonics, counting & playful discovery"),
    CLASS_1("class_1", "Class 1", "Ages 5-6", ClassTier.LOWER_PRIMARY, "Early reading, addition & curiosity"),
    CLASS_2("class_2", "Class 2", "Ages 6-7", ClassTier.LOWER_PRIMARY, "Stories, tables & everyday science"),
    CLASS_3("class_3", "Class 3", "Ages 7-8", ClassTier.MIDDLE_PRIMARY, "Grammar, multiplication & world wonders"),
    CLASS_4("class_4", "Class 4", "Ages 8-9", ClassTier.MIDDLE_PRIMARY, "Fractions, habitats & digital basics"),
    CLASS_5("class_5", "Class 5", "Ages 9-10", ClassTier.UPPER_PRIMARY, "Decimals, earth science & history"),
    CLASS_6("class_6", "Class 6", "Ages 10-11", ClassTier.UPPER_PRIMARY, "Geometry, coding logic & geography");

    companion object {
        fun fromId(id: String?): SchoolClass =
            entries.firstOrNull { it.id == id } ?: CLASS_1
    }
}
