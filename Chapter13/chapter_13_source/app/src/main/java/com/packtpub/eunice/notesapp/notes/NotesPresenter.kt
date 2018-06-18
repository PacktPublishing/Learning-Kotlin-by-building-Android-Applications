package com.packtpub.eunice.notesapp.notes

import com.packtpub.eunice.notesapp.data.Note
import com.packtpub.eunice.notesapp.data.NotesRepository
import com.packtpub.eunice.notesapp.util.EspressoIdlingResource


class NotesPresenter(notesView: NotesContract.View, notesRepository: NotesRepository) :
        NotesContract.UserActionsListener {
    private var notesView: NotesContract.View = checkNotNull(notesView) {
        "notesView cannot be null!"
    }
    private var notesRepository: NotesRepository = checkNotNull(notesRepository) {
        "notesRepository cannot be null!"
    }

    override fun loadNotes(forceUpdate: Boolean) {
        notesView.setProgressIndicator(true)
        if (forceUpdate) {
            notesRepository.refreshData()
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment() // App is busy until further notice

        notesRepository.getNotes(object : NotesRepository.LoadNotesCallback {
            override fun onNotesLoaded(notes: List<Note>) {
                EspressoIdlingResource.decrement() // Set app as idle.
                notesView.setProgressIndicator(false)
                notesView.showNotes(notes)
            }
        })
    }

    override fun addNewNote() = notesView.showAddNote()

    override fun openNoteDetails(requestedNote: Note) {

    }

}