package hu.szacskesz.mobile.tasklist.core.interactors

import hu.szacskesz.mobile.tasklist.core.data.TaskListRepository


class ReadTaskListWithTasksCounts(private val repository: TaskListRepository) {
    suspend operator fun invoke(id: Int?) = repository.readWithTasksCount(id)
}
