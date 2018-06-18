package com.packtpub.eunice.notesapp.data



interface NotesRepository {

    interface LoadNotesCallback {

        fun onNotesLoaded(notes: List<Note>)
    }

//    interface GetNoteCallback {
//
//        fun onNoteLoaded(note: Note)
//    }

    fun getNotes(callback: LoadNotesCallback?)

//    fun getNote(noteId: String, callback: GetNoteCallback)
//
//    fun saveNote(note: Note)

    fun refreshData()

}