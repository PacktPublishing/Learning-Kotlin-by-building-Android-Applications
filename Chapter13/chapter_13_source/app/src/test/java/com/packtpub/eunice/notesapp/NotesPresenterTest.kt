package com.packtpub.eunice.notesapp

import com.packtpub.eunice.notesapp.data.Note
import com.packtpub.eunice.notesapp.data.NotesRepository
import com.packtpub.eunice.notesapp.notes.NotesContract
import com.packtpub.eunice.notesapp.notes.NotesPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class NotesPresenterTest {


    @Mock
    private lateinit var notesView: NotesContract.View

    @Mock
    private lateinit var notesRepository: NotesRepository

    private lateinit var notesPresenter: NotesPresenter

    private val NOTES = arrayListOf(Note("Title1", "Description1"),
            Note("Title2", "Description2"))

//    private val EMPTY_NOTES = ArrayList(0)

    /**
     * [ArgumentCaptor] is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private lateinit var loadNotesCallbackCaptor: ArgumentCaptor<NotesRepository.LoadNotesCallback>

    @Before
    fun setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        notesPresenter = NotesPresenter(notesView, notesRepository)
    }

    @Test
    fun `should display note view on button click`() {
        // When adding a new note
        notesPresenter.addNewNote()

        // Then add note UI is shown
        verify(notesView)?.showAddNote()
    }

    @Test
    fun `should load notes from repository into view`() {
        // When loading of Notes is requested
        notesPresenter.loadNotes(true)

        // Then capture callback and invoked with stubbed notes
        verify(notesRepository)?.getNotes(loadNotesCallbackCaptor.capture())
        loadNotesCallbackCaptor.value.onNotesLoaded(NOTES)

        // Then hide progress indicator and display notes
        verify(notesView).setProgressIndicator(false)
        verify(notesView).showNotes(NOTES)
    }
}