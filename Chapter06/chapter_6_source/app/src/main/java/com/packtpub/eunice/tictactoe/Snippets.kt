package com.packtpub.eunice.tictactoe

import android.util.Log


data class Student(var name: String, var classRoomNo: Int, var studentId: Int, var age: Int)




fun addStudent(name: String, age:Int, classRoomNo: Int = 1, studentId: Int) = Student(name, classRoomNo, studentId, age)


var anna = addStudent("Anna", 18, 2, 1)
var joseph = addStudent(name = "Joseph", age = 19, studentId = 2)


fun logStudent(name: String, age:Int, createStudent:(String, Int) -> Student) {
    Log.d("student creation", "About to create student with name $name")
    val student = createStudent(name, age)
    Log.d("student creation", "Student created with name ${student.name} and age ${student.age}")
}







fun main(args: Array<String>) {

    logStudent("Anna", 20, { name: String, age: Int -> Student(name, 1, 3, age)})

    var name: String = "Anna" // non-nullable String
    var gender: String? = "Female" //nullable String

    print("Length of name is ${name.length}") // will compile


    if (gender != null) {
        print("Length of gender is ${gender.length}")
    }


    val len = gender!!.length
    print("Length of gender is $len")

    if (gender is String) {
        println("Length of gender is ${gender.length}") // gender is automatically cast to a String
    }

    var fullname: String = name as String

    var gen: String? = gender as? String

    println("Anna is in classroom no. ${anna.classRoomNo}")
    println("Joseph is in classroom no. ${joseph.classRoomNo}")


}