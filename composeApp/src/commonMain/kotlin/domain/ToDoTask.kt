package domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ToDoTask : RealmObject {
    @PrimaryKey
    val _id: ObjectId = ObjectId()
    var title: String = ""
    var description: String = ""
    val isFavorite: Boolean = false
    val isCompleted: Boolean = false
}