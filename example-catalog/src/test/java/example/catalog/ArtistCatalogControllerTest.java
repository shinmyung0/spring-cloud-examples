package example.catalog;

import example.catalog.datamodel.ArtistEntity;
import example.catalog.datamodel.ArtistRespository;
import example.catalog.exception.ResourceConflictException;
import example.catalog.exception.ResourceNotFoundException;
import example.catalog.web.Artist;
import example.catalog.web.ArtistCatalogController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static example.catalog.ControllerTestValues.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shiyoon on 4/24/17.
 */

public class ArtistCatalogControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ArtistRespository mockArtistRepository;

    @Mock
    private EntityToDtoConverter mockConverter;

    private ArtistCatalogController artistCatalogController;

    private Artist validArtist = getValidArtist();
    private Artist nonExistantArtist = getNonExistantArtist();
    private ArtistEntity validArtistEntity = getValidArtistEntity();
    private Artist newArtist = getNewArtistWithoutId();



    @Before
    public void setUp() {
        artistCatalogController = new ArtistCatalogController(mockArtistRepository, mockConverter);

        when(mockArtistRepository.findOne(VALID_ARTIST_ID)).thenReturn(validArtistEntity);
        when(mockArtistRepository.findOne(NONEXISTANT_ARTIST_ID)).thenReturn(null);

        when(mockArtistRepository.exists(VALID_ARTIST_ID)).thenReturn(true);
        when(mockArtistRepository.exists(NONEXISTANT_ARTIST_ID)).thenReturn(false);

        when(mockArtistRepository.findAll()).thenReturn(new ArrayList<>());

    }

    @Test
    public void gettingAllShouldCallFindAllFromRepository() {
        List<Artist> artists = artistCatalogController.getAllArtists();
        verify(mockArtistRepository).findAll();

    }

    @Test
    public void gettingArtistWillCallFromRepository() {
        Artist artist = artistCatalogController.getArtist(VALID_ARTIST_ID);
        verify(mockArtistRepository).findOne(VALID_ARTIST_ID);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void gettingNonExistantArtistWillThrowException() {
        Artist artist = artistCatalogController.getArtist(NONEXISTANT_ARTIST_ID);

    }

    @Test
    public void attemptingRegistrationWithoutIDShouldJustCallSave() {

        Artist result =  artistCatalogController.registerArtist(newArtist);
        verify(mockArtistRepository).save(Mockito.any(ArtistEntity.class));


    }

    @Test(expected = ResourceConflictException.class)
    public void attemptingRegistrationOnExistingIdShouldThrowConflictException() {
        Artist result = artistCatalogController.registerArtist(validArtist);

    }

    @Test
    public void updatingValidNewArtistShouldJustCallSave() {
        Artist result = artistCatalogController.updateArtist(validArtist);
        verify(mockArtistRepository).save(Mockito.any(ArtistEntity.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatingNonExistantShouldThrowException() {
        Artist result = artistCatalogController.updateArtist(nonExistantArtist);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void deletingNonExistantArtistShouldThrowException() {
        artistCatalogController.deleteArtist(NONEXISTANT_ARTIST_ID);

    }






}
