package com.packtpub.eunice.tictactoe


data class Student(var name: String, var classRoomNo: Int, var studentId: Int)

var anna = Student("Anna", 5, 1)
var joseph = anna.copy("Joseph", studentId = 2)

