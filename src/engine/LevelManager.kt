package engine

class LevelManager {
    private val levels: MutableList<String> = mutableListOf()
    private val maxLevels = 3

    init {
        for (i in 1 .. maxLevels) {
            levels.add("resources/levels/$i.level")
        }
    }

    //    var unlockedLevels = 1
    var unlockedLevels = maxLevels

    fun getLevelPath(levelIndex: Int): String {
        return levels[levelIndex]
    }

    fun getUnlockedLevels(): List<String> {
        return levels.take(unlockedLevels)
    }

    fun unlockNextLevel() {
        if (unlockedLevels < levels.size) {
            unlockedLevels++
        }
    }

    fun isLevelUnlocked(levelIndex: Int): Boolean {
        return levelIndex < unlockedLevels
    }
}
