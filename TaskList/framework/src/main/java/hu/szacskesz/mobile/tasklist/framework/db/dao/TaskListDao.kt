package hu.szacskesz.mobile.tasklist.framework.db.dao

import androidx.room.*
import hu.szacskesz.mobile.tasklist.framework.db.entity.TaskListEntity
import hu.szacskesz.mobile.tasklist.framework.db.entity.TaskListWithTasksCountEntity
import java.util.*


@Dao
interface TaskListDao {

    @Query("""
        SELECT * FROM `task_list` WHERE
            (:id IS NUll OR `task_list`.id = :id)
    """)
    suspend fun read(id: Int?) : List<TaskListEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(taskList: TaskListEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(taskList: TaskListEntity)

    @Delete
    suspend fun delete(taskList: TaskListEntity)



    @Transaction
    @Query("""
        SELECT `task_list`.*, `taskCount`.count as `task_count`, `overdueTaskCount`.count as `overdue_task_count`
        FROM `task_list`
            LEFT JOIN (
                SELECT list_id, COUNT(*) as `count` FROM `task` GROUP BY `task`.list_id
            ) as `taskCount`
            ON `task_list`.id = `taskCount`.list_id
            LEFT JOIN (
                SELECT list_id, COUNT(*) as `count` FROM `task` WHERE NOT `task`.done AND `task`.deadline < :nowDate GROUP BY `task`.list_id
            ) as `overdueTaskCount`
            ON `task_list`.id = `overdueTaskCount`.list_id
        WHERE (:id IS NUll OR `task_list`.id = :id)
    """)
    suspend fun readWithTasksCount(id: Int?, nowDate: Date) : List<TaskListWithTasksCountEntity>
}
