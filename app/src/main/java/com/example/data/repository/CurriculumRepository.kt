package com.example.data.repository

import com.example.R
import com.example.data.model.Achievement
import com.example.data.model.ClassTier
import com.example.data.model.CurriculumTrack
import com.example.data.model.DailyQuizQuestion
import com.example.data.model.SchoolClass
import com.example.data.model.Subject
import com.example.data.model.SubjectCategory

class CurriculumRepository {

    fun getSubjectsForClass(
        schoolClass: SchoolClass,
        track: CurriculumTrack
    ): List<Subject> = when (schoolClass.tier) {
        ClassTier.PREP -> prepSubjects(schoolClass)
        ClassTier.LOWER_PRIMARY -> lowerPrimarySubjects(schoolClass, track)
        ClassTier.MIDDLE_PRIMARY -> middlePrimarySubjects(schoolClass, track)
        ClassTier.UPPER_PRIMARY -> upperPrimarySubjects(schoolClass, track)
    }

    private fun prepSubjects(schoolClass: SchoolClass): List<Subject> {
        val isPrep2 = schoolClass == SchoolClass.PREP_2
        return listOf(
            Subject(
                id = "prep_eng_alpha",
                title = "English Alphabet",
                subtitle = if (isPrep2) "Tracing & letter pairs" else "A to Z big & small letters",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_sky_bg,
                accentColorRes = R.color.pastel_sky_fg,
                iconRes = R.drawable.ic_translate,
                chapterCount = 6,
                completedChapters = 4,
                sampleTopics = listOf("Letters A–E", "Letters F–J", "Vowels & Consonants")
            ),
            Subject(
                id = "prep_phonics",
                title = "Phonics",
                subtitle = "Letter sounds & blends",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_lavender_bg,
                accentColorRes = R.color.pastel_lavender_fg,
                iconRes = R.drawable.ic_volume_up,
                chapterCount = 5,
                completedChapters = 3,
                sampleTopics = listOf("Short Vowel Sounds", "CVC Words", "Rhyming Pairs")
            ),
            Subject(
                id = "prep_vocab",
                title = "Basic English Vocabulary",
                subtitle = "Everyday picture words",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_mint_bg,
                accentColorRes = R.color.pastel_mint_fg,
                iconRes = R.drawable.ic_nav_learn,
                chapterCount = 5,
                completedChapters = 2,
                sampleTopics = listOf("My Home", "Classroom Things", "Action Words")
            ),
            Subject(
                id = "prep_numbers",
                title = "Numbers",
                subtitle = if (isPrep2) "Numbers 1 to 100" else "Numbers 1 to 50",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_peach_bg,
                accentColorRes = R.color.pastel_peach_fg,
                iconRes = R.drawable.ic_calculate,
                chapterCount = 6,
                completedChapters = 4,
                sampleTopics = listOf("1 to 10", "11 to 30", "Missing Numbers")
            ),
            Subject(
                id = "prep_counting",
                title = "Counting",
                subtitle = "Count objects & match",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_lemon_bg,
                accentColorRes = R.color.pastel_lemon_fg,
                iconRes = R.drawable.ic_star,
                chapterCount = 4,
                completedChapters = 3,
                sampleTopics = listOf("Counting Toys", "Forward & Backward", "Skip Counting by 2")
            ),
            Subject(
                id = "prep_basic_math",
                title = "Basic Mathematics",
                subtitle = "Simple adding & taking away",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_sky_bg,
                accentColorRes = R.color.pastel_sky_fg,
                iconRes = R.drawable.ic_calculate,
                chapterCount = 5,
                completedChapters = 2,
                sampleTopics = listOf("More or Less", "Picture Addition", "Picture Subtraction")
            ),
            Subject(
                id = "prep_urdu_alpha",
                title = "Urdu Alphabet",
                subtitle = "حروفِ تہجی کی پہچان",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_teal_bg,
                accentColorRes = R.color.pastel_teal_fg,
                iconRes = R.drawable.ic_translate,
                chapterCount = 7,
                completedChapters = 3,
                sampleTopics = listOf("ا ب پ ت ٹ ث", "ج چ ح خ", "حروف کی آوازیں"),
                urduSample = "ا ب پ ت"
            ),
            Subject(
                id = "prep_urdu_vocab",
                title = "Urdu Vocabulary",
                subtitle = "آسان اردو الفاظ",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_mint_bg,
                accentColorRes = R.color.pastel_mint_fg,
                iconRes = R.drawable.ic_nav_learn,
                chapterCount = 5,
                completedChapters = 2,
                sampleTopics = listOf("گھر اور خاندان", "جانوروں کے نام", "رنگوں کے نام"),
                urduSample = "آم، بلی، تارا"
            ),
            Subject(
                id = "prep_colors",
                title = "Colors",
                subtitle = "Rainbow shades & matching",
                category = SubjectCategory.CREATIVE,
                bgColorRes = R.color.pastel_rose_bg,
                accentColorRes = R.color.pastel_rose_fg,
                iconRes = R.drawable.ic_palette,
                chapterCount = 3,
                completedChapters = 3,
                sampleTopics = listOf("Primary Colors", "Rainbow Order", "Color Mixing")
            ),
            Subject(
                id = "prep_shapes",
                title = "Shapes",
                subtitle = "Circle, square, triangle & star",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_lavender_bg,
                accentColorRes = R.color.pastel_lavender_fg,
                iconRes = R.drawable.ic_school,
                chapterCount = 4,
                completedChapters = 2,
                sampleTopics = listOf("2D Shapes", "Shapes Around Us", "Pattern Blocks")
            ),
            Subject(
                id = "prep_animals",
                title = "Animals",
                subtitle = "Wild, farm & water friends",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_peach_bg,
                accentColorRes = R.color.pastel_peach_fg,
                iconRes = R.drawable.ic_public,
                chapterCount = 4,
                completedChapters = 2,
                sampleTopics = listOf("Farm Animals", "Jungle Safari", "Animal Sounds")
            ),
            Subject(
                id = "prep_fruits_veg",
                title = "Fruits and Vegetables",
                subtitle = "Healthy & tasty food",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_lemon_bg,
                accentColorRes = R.color.pastel_lemon_fg,
                iconRes = R.drawable.ic_science,
                chapterCount = 4,
                completedChapters = 1,
                sampleTopics = listOf("Sweet Fruits", "Garden Vegetables", "Healthy Plate")
            ),
            Subject(
                id = "prep_gk",
                title = "General Knowledge",
                subtitle = "My world, seasons & manners",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_teal_bg,
                accentColorRes = R.color.pastel_teal_fg,
                iconRes = R.drawable.ic_public,
                chapterCount = 5,
                completedChapters = 2,
                sampleTopics = listOf("Parts of Body", "Four Seasons", "Good Habits")
            ),
            Subject(
                id = "prep_drawing",
                title = "Drawing and Coloring",
                subtitle = "Creative strokes & fun fills",
                category = SubjectCategory.CREATIVE,
                bgColorRes = R.color.pastel_rose_bg,
                accentColorRes = R.color.pastel_rose_fg,
                iconRes = R.drawable.ic_palette,
                chapterCount = 4,
                completedChapters = 2,
                sampleTopics = listOf("Trace & Color", "Draw a Sun", "Nature Sketch")
            )
        )
    }

    private fun lowerPrimarySubjects(schoolClass: SchoolClass, track: CurriculumTrack): List<Subject> {
        val isClass2 = schoolClass == SchoolClass.CLASS_2
        return listOf(
            Subject(
                id = "lp_english",
                title = "English",
                subtitle = if (isClass2) "Reading, grammar & short stories" else "Sight words, sentences & stories",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_sky_bg,
                accentColorRes = R.color.pastel_sky_fg,
                iconRes = R.drawable.ic_translate,
                chapterCount = 8,
                completedChapters = 5,
                sampleTopics = listOf("Nouns & Verbs", "Short Stories", "Sentence Builders")
            ),
            Subject(
                id = "lp_math",
                title = "Mathematics",
                subtitle = if (isClass2) "Tables, 3-digit add & subtract" else "Number bonds, addition & subtraction",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_peach_bg,
                accentColorRes = R.color.pastel_peach_fg,
                iconRes = R.drawable.ic_calculate,
                chapterCount = 9,
                completedChapters = 6,
                sampleTopics = listOf("Place Value", "Mental Addition", "Shapes & Time")
            ),
            Subject(
                id = "lp_urdu",
                title = "Urdu",
                subtitle = "اردو پڑھائی، الفاظ اور جملے",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_mint_bg,
                accentColorRes = R.color.pastel_mint_fg,
                iconRes = R.drawable.ic_nav_learn,
                chapterCount = 8,
                completedChapters = 4,
                sampleTopics = listOf("دو حرفی الفاظ", "آسان جملے", "کہانیاں"),
                urduSample = "میرا پیارا وطن"
            ),
            Subject(
                id = "lp_science",
                title = "General Science",
                subtitle = "Living things, plants & weather",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_teal_bg,
                accentColorRes = R.color.pastel_teal_fg,
                iconRes = R.drawable.ic_science,
                chapterCount = 7,
                completedChapters = 3,
                sampleTopics = listOf("Parts of a Plant", "Senses & Body", "Weather & Sky")
            ),
            Subject(
                id = "lp_gk",
                title = "General Knowledge",
                subtitle = "Community helpers & our Earth",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_lemon_bg,
                accentColorRes = R.color.pastel_lemon_fg,
                iconRes = R.drawable.ic_public,
                chapterCount = 6,
                completedChapters = 4,
                sampleTopics = listOf("Traffic Safety", "Our Neighbourhood", "World Landmarks")
            ),
            Subject(
                id = "lp_computer",
                title = "Computer Basics",
                subtitle = "Parts of a computer & typing",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_lavender_bg,
                accentColorRes = R.color.pastel_lavender_fg,
                iconRes = R.drawable.ic_computer,
                chapterCount = 5,
                completedChapters = 2,
                sampleTopics = listOf("Monitor & Keyboard", "Mouse Skills", "Safe Screen Habits")
            ),
            Subject(
                id = "lp_values",
                title = track.label,
                subtitle = "Kindness, honesty & good manners",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_rose_bg,
                accentColorRes = R.color.pastel_rose_fg,
                iconRes = R.drawable.ic_school,
                chapterCount = 6,
                completedChapters = 3,
                sampleTopics = listOf("Speaking the Truth", "Respecting Elders", "Sharing & Caring")
            )
        )
    }

    private fun middlePrimarySubjects(schoolClass: SchoolClass, track: CurriculumTrack): List<Subject> {
        val isClass4 = schoolClass == SchoolClass.CLASS_4
        return listOf(
            Subject(
                id = "mp_english",
                title = "English",
                subtitle = if (isClass4) "Tenses, paragraphs & comprehension" else "Parts of speech & reading skills",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_sky_bg,
                accentColorRes = R.color.pastel_sky_fg,
                iconRes = R.drawable.ic_translate,
                chapterCount = 10,
                completedChapters = 5,
                sampleTopics = listOf("Parts of Speech", "Past & Present Tenses", "Story Writing")
            ),
            Subject(
                id = "mp_math",
                title = "Mathematics",
                subtitle = if (isClass4) "Fractions, factors & area" else "Multiplication, division & fractions",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_peach_bg,
                accentColorRes = R.color.pastel_peach_fg,
                iconRes = R.drawable.ic_calculate,
                chapterCount = 10,
                completedChapters = 6,
                sampleTopics = listOf("Times Tables", "Long Division", "Fractions Basics")
            ),
            Subject(
                id = "mp_urdu",
                title = "Urdu",
                subtitle = "اردو قواعد، مضمون اور تفہیم",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_mint_bg,
                accentColorRes = R.color.pastel_mint_fg,
                iconRes = R.drawable.ic_nav_learn,
                chapterCount = 9,
                completedChapters = 4,
                sampleTopics = listOf("اسم اور فعل", "واحد جمع", "تفہیم عبارت"),
                urduSample = "علم بڑی دولت ہے"
            ),
            Subject(
                id = "mp_science",
                title = "Science",
                subtitle = "Matter, energy, ecosystems & space",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_teal_bg,
                accentColorRes = R.color.pastel_teal_fg,
                iconRes = R.drawable.ic_science,
                chapterCount = 8,
                completedChapters = 4,
                sampleTopics = listOf("Water Cycle", "States of Matter", "Solar System")
            ),
            Subject(
                id = "mp_sst",
                title = "Social Studies",
                subtitle = "Maps, culture & citizenship",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_lemon_bg,
                accentColorRes = R.color.pastel_lemon_fg,
                iconRes = R.drawable.ic_public,
                chapterCount = 7,
                completedChapters = 3,
                sampleTopics = listOf("Continents & Oceans", "Civic Duties", "Local Heritage")
            ),
            Subject(
                id = "mp_computer",
                title = "Computer Studies",
                subtitle = "Files, logic & safe browsing",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_lavender_bg,
                accentColorRes = R.color.pastel_lavender_fg,
                iconRes = R.drawable.ic_computer,
                chapterCount = 6,
                completedChapters = 3,
                sampleTopics = listOf("Hardware & Software", "Word Processing", "Digital Citizenship")
            ),
            Subject(
                id = "mp_gk",
                title = "General Knowledge",
                subtitle = "Inventions, wildlife & world facts",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_sky_bg,
                accentColorRes = R.color.pastel_sky_fg,
                iconRes = R.drawable.ic_star,
                chapterCount = 6,
                completedChapters = 2,
                sampleTopics = listOf("Great Inventors", "Famous Mountains", "Science Wonders")
            ),
            Subject(
                id = "mp_values",
                title = track.label,
                subtitle = "Character building & values",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_rose_bg,
                accentColorRes = R.color.pastel_rose_fg,
                iconRes = R.drawable.ic_school,
                chapterCount = 6,
                completedChapters = 3,
                sampleTopics = listOf("Gratitude", "Teamwork & Empathy", "Helping Community")
            )
        )
    }

    private fun upperPrimarySubjects(schoolClass: SchoolClass, track: CurriculumTrack): List<Subject> {
        val isClass6 = schoolClass == SchoolClass.CLASS_6
        return listOf(
            Subject(
                id = "up_english",
                title = "English",
                subtitle = if (isClass6) "Essays, clauses & literature" else "Comprehension, grammar & writing",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_sky_bg,
                accentColorRes = R.color.pastel_sky_fg,
                iconRes = R.drawable.ic_translate,
                chapterCount = 12,
                completedChapters = 6,
                sampleTopics = listOf("Active & Passive Voice", "Essay Structure", "Critical Reading")
            ),
            Subject(
                id = "up_math",
                title = "Mathematics",
                subtitle = if (isClass6) "Ratios, algebra & geometry" else "Decimals, percentages & geometry",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_peach_bg,
                accentColorRes = R.color.pastel_peach_fg,
                iconRes = R.drawable.ic_calculate,
                chapterCount = 12,
                completedChapters = 7,
                sampleTopics = listOf("Decimals & Percents", "Angles & Triangles", "Word Problems")
            ),
            Subject(
                id = "up_urdu",
                title = "Urdu",
                subtitle = "ادب، قواعد اور مضمون نویسی",
                category = SubjectCategory.LANGUAGES,
                bgColorRes = R.color.pastel_mint_bg,
                accentColorRes = R.color.pastel_mint_fg,
                iconRes = R.drawable.ic_nav_learn,
                chapterCount = 10,
                completedChapters = 5,
                sampleTopics = listOf("مترادف و متضاد", "خطوط نویسی", "نظم و نثر"),
                urduSample = "محنت میں عظمت ہے"
            ),
            Subject(
                id = "up_science",
                title = "General Science",
                subtitle = "Cells, electricity, light & chemistry",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_teal_bg,
                accentColorRes = R.color.pastel_teal_fg,
                iconRes = R.drawable.ic_science,
                chapterCount = 10,
                completedChapters = 5,
                sampleTopics = listOf("Electric Circuits", "Forces & Motion", "Acids & Mixtures")
            ),
            Subject(
                id = "up_cs",
                title = "Computer Science",
                subtitle = "Algorithms, spreadsheets & coding",
                category = SubjectCategory.STEM,
                bgColorRes = R.color.pastel_lavender_bg,
                accentColorRes = R.color.pastel_lavender_fg,
                iconRes = R.drawable.ic_computer,
                chapterCount = 8,
                completedChapters = 4,
                sampleTopics = listOf("Flowcharts & Logic", "Binary Numbers", "Spreadsheets")
            ),
            Subject(
                id = "up_sst",
                title = "Social Studies",
                subtitle = "Democracy, trade & global society",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_lemon_bg,
                accentColorRes = R.color.pastel_lemon_fg,
                iconRes = R.drawable.ic_public,
                chapterCount = 8,
                completedChapters = 3,
                sampleTopics = listOf("Rights & Responsibilities", "Resources & Trade", "Global Connections")
            ),
            Subject(
                id = "up_geography",
                title = "Geography",
                subtitle = "Climate zones, landforms & maps",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_mint_bg,
                accentColorRes = R.color.pastel_mint_fg,
                iconRes = R.drawable.ic_public,
                chapterCount = 7,
                completedChapters = 3,
                sampleTopics = listOf("Latitude & Longitude", "Rivers & Mountains", "Climate Regions")
            ),
            Subject(
                id = "up_history",
                title = "History",
                subtitle = "Ancient civilizations & milestones",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_peach_bg,
                accentColorRes = R.color.pastel_peach_fg,
                iconRes = R.drawable.ic_school,
                chapterCount = 7,
                completedChapters = 2,
                sampleTopics = listOf("Indus Valley", "Golden Age of Science", "Modern Discoveries")
            ),
            Subject(
                id = "up_values",
                title = track.label,
                subtitle = "Ethics, leadership & service",
                category = SubjectCategory.GK_VALUES,
                bgColorRes = R.color.pastel_rose_bg,
                accentColorRes = R.color.pastel_rose_fg,
                iconRes = R.drawable.ic_star,
                chapterCount = 6,
                completedChapters = 4,
                sampleTopics = listOf("Integrity & Fairness", "Respecting Diversity", "Community Service")
            )
        )
    }

    fun getAchievements(stars: Int, streakDays: Int, points: Int): List<Achievement> = listOf(
        Achievement(
            id = "ach_first_star",
            title = "Rising Star",
            description = "Earned your first 10 stars",
            isUnlocked = stars >= 10,
            iconRes = R.drawable.ic_star,
            pointsReward = 50
        ),
        Achievement(
            id = "ach_streak_5",
            title = "5-Day Streak",
            description = "Learned 5 days in a row",
            isUnlocked = streakDays >= 5,
            iconRes = R.drawable.ic_flame,
            pointsReward = 100
        ),
        Achievement(
            id = "ach_quiz_whiz",
            title = "Quiz Explorer",
            description = "Reached 350 genius points",
            isUnlocked = points >= 350,
            iconRes = R.drawable.ic_trophy,
            pointsReward = 75
        ),
        Achievement(
            id = "ach_super_scholar",
            title = "Super Scholar",
            description = "Collect 30 stars across subjects",
            isUnlocked = stars >= 30,
            iconRes = R.drawable.ic_school,
            pointsReward = 150
        )
    )

    fun getDailyQuizForClass(schoolClass: SchoolClass): DailyQuizQuestion = when (schoolClass.tier) {
        ClassTier.PREP -> DailyQuizQuestion(
            id = "dq_prep",
            subjectLabel = "Counting & Shapes",
            question = "How many sides does a triangle have?",
            options = listOf("2 sides", "3 sides", "4 sides", "5 sides"),
            correctIndex = 1,
            explanation = "A triangle always has 3 straight sides and 3 corners!"
        )
        ClassTier.LOWER_PRIMARY -> DailyQuizQuestion(
            id = "dq_lp",
            subjectLabel = "Mathematics",
            question = "What is 8 + 7?",
            options = listOf("14", "15", "16", "17"),
            correctIndex = 1,
            explanation = "8 + 2 makes 10, and 10 + 5 equals 15!"
        )
        ClassTier.MIDDLE_PRIMARY -> DailyQuizQuestion(
            id = "dq_mp",
            subjectLabel = "Science",
            question = "Which planet is known as the Red Planet?",
            options = listOf("Venus", "Jupiter", "Mars", "Saturn"),
            correctIndex = 2,
            explanation = "Mars looks reddish because of iron-rich dust on its surface!"
        )
        ClassTier.UPPER_PRIMARY -> DailyQuizQuestion(
            id = "dq_up",
            subjectLabel = "Mathematics",
            question = "What is 25% of 80?",
            options = listOf("15", "20", "25", "40"),
            correctIndex = 1,
            explanation = "25% is one-fourth, and 80 divided by 4 equals 20!"
        )
    }
}
