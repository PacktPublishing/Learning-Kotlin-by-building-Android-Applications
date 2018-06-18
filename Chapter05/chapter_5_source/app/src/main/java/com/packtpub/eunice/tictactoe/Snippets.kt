package com.packtpub.eunice.tictactoe


data class Student(var name: String, var classRoomNo: Int, var studentId: Int)

var anna = Student("Anna", 5, 1)
var joseph = anna.copy(name = "Joseph", studentId = 2)

fun main(args: Array<String>) {

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


}