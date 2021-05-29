package hu.szacskesz.mobile.tasklist.framework.db.datasource

import android.content.Context
import hu.szacskesz.mobile.tasklist.core.converters.toTaskWithTaskListName
import hu.szacskesz.mobile.tasklist.core.data.TaskDataSource
import hu.szacskesz.mobile.tasklist.core.domain.Task
import hu.szacskesz.mobile.tasklist.core.domain.TaskWithTaskList
import hu.szacskesz.mobile.tasklist.core.domain.TaskWithTaskListName
import hu.szacskesz.mobile.tasklist.framework.db.AppDatabase
import hu.szacskesz.mobile.tasklist.framework.db.converters.toDto
import hu.szacskesz.mobile.tasklist.framework.db.converters.toEntity


class RoomTaskDataSource(val context: Context) : TaskDataSource {

    private val taskDao = AppDatabase.getInstance(context).taskDao()

    override suspend fun create(task: Task) {
        return taskDao.create( task.toEntity() )
    }

    override suspend fun read(): List<Task> {
        return taskDao.read().map { it.toDto() }
    }

    override suspend fun readWithTaskList(): List<TaskWithTaskList> {
        return taskDao.readWithTaskList().map { it.toDto() }
    }

    override suspend fun readWithTaskListName(): List<TaskWithTaskListName> {
        return taskDao.readWithTaskList().map { it.toDto().toTaskWithTaskListName() }
    }

    override suspend fun update(task: Task)  {
        return taskDao.update( task.toEntity() )
    }

    override suspend fun delete(task: Task) {
        return taskDao.delete( task.toEntity() )
    }
}
