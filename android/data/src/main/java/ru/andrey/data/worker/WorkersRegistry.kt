package ru.andrey.data.worker

object WorkersRegistry {

    private val idToTask = HashMap<String, () -> Unit>()

    fun registerTask(id: String, task: () -> Unit) {
        if (!idToTask.containsKey(id)) {
            idToTask[id] = task
        }
    }

    fun findTask(id: String): (() -> Unit)? {
        return idToTask[id]
    }
}