package com.packtpub.eunice.notesapp.notes

import com.packtpub.eunice.notesapp.data.Note


interface NotesContract {
    interface View {
        fun setProgressIndicator(active: Boolean)

        fun showNotes(notes: List<Note>)

        fun showAddNote()

        fun showNoteDetailUi(noteId: String)
    }

    interface UserActionsListener {

        fun loadNotes(forceUpdate: Boolean)

        fun addNewNote()

        fun openNoteDetails(requestedNote: Note)
    }
}