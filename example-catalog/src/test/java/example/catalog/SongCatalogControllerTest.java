package example.catalog;

import example.catalog.datamodel.SongEntity;
import example.catalog.datamodel.SongRepository;
import example.catalog.exception.ResourceConflictException;
import example.catalog.exception.ResourceNotFoundException;
import example.catalog.web.Artist;
import example.catalog.web.ArtistCatalogController;
import example.catalog.web.Song;
import example.catalog.web.SongCatalogController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static example.catalog.ControllerTestValues.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Created by shiyoon on 4/24/17.
 */
public class SongCatalogControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private SongRepository mockSongRepository;

    @Mock
    private EntityToDtoConverter mockConverter;

    @Mock
    private ArtistCatalogController mockArtistController;

    private SongCatalogController songCatalogController;

    @Before
    public void setUp() {
        songCatalogController = new SongCatalogController(mockSongRepository, mockArtistController, mockConverter);

        when(mockSongRepository.findAll()).thenReturn(new ArrayList<>());
        when(mockSongRepository.findOne(VALID_SONG_ID)).thenReturn(new SongEntity());
        when(mockSongRepository.exists(VALID_SONG_ID)).thenReturn(true);

        when(mockSongRepository.save(any(SongEntity.class))).thenReturn(new SongEntity());
        when(mockArtistController.getArtist(anyLong())).thenReturn(new Artist());
    }

    @Test
    public void gettingAllSongsWithId0ShouldCallRepositoryFindAll() {
        List<Song> allSongs = songCatalogController.getAllSongsForArtist(0);
        verify(mockSongRepository).findAll();

    }

    @Test
    public void gettingAllSongsWithIdShouldCallRepositoryFindAllByArtistId() {
        List<Song> allSongs = songCatalogController.getAllSongsForArtist(VALID_ARTIST_ID);
        verify(mockSongRepository).findAllByArtistId(VALID_ARTIST_ID);

    }

    @Test
    public void gettingASongShouldCallRepositoryFindOne() {
        Song song = songCatalogController.getSong(VALID_SONG_ID);
        verify(mockSongRepository).findOne(VALID_SONG_ID);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void gettingNonExistantSongShouldThrowException() {
        Song song = songCatalogController.getSong(NONEXISTANT_SONG_ID);

    }

    @Test
    public void registeringASongWithoutIdShouldCallRepositorySave() {
        Song newSong = new Song();
        assertEquals(0L, newSong.getId());

        Song registeredSong = songCatalogController.registerSong(newSong);
        verify(mockSongRepository).save(any(SongEntity.class));
    }

    @Test
    public void registeringASongWithIdShouldSaveIfItNonExistant() {
        Song newSong = new Song();
        newSong.setId(NONEXISTANT_SONG_ID);

        Song registeredSong = songCatalogController.registerSong(newSong);

        verify(mockSongRepository).save(any(SongEntity.class));
    }

    @Test(expected = ResourceConflictException.class)
    public void registeringASongWithConflictingIdShouldThrowException() {
        Song newSong = new Song();
        newSong.setId(VALID_SONG_ID);

        Song registeredSong = songCatalogController.registerSong(newSong);
    }

    @Test
    public void updatingAValidSongShouldCallSave() {
        Song validSong = new Song();
        validSong.setId(VALID_SONG_ID);

        Song updatedSong = songCatalogController.updateSong(validSong);

        verify(mockSongRepository).exists(VALID_SONG_ID);
        verify(mockSongRepository).save(any(SongEntity.class));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatingNonExistantSongShouldThrowException() {
        Song nonExistantSong = new Song();
        nonExistantSong.setId(NONEXISTANT_SONG_ID);

        Song updatedSong = songCatalogController.updateSong(nonExistantSong);
    }

    @Test
    public void deletingValidSongShouldCallDeleteOnRepository() {
        songCatalogController.deleteSong(VALID_SONG_ID);
        verify(mockSongRepository).delete(VALID_SONG_ID);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deletingNonExistantSongShouldThrowException() {
        songCatalogController.deleteSong(NONEXISTANT_SONG_ID);
    }






}
