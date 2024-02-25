package com.example.controllerTest;

import com.example.controller.AssetController;
import com.example.entity.Asset;
import com.example.entity.Status;
import com.example.Service.AssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AssetControllerTest {

    @Mock
    private AssetService assetService;

    @InjectMocks
    private AssetController assetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addAsset() {
        Asset asset = new Asset();
        asset.setName("Test Asset");

        when(assetService.addAsset(asset)).thenReturn(asset);

        ResponseEntity<Asset> responseEntity = assetController.addAsset(asset);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(asset, responseEntity.getBody());
    }

    @Test
    void fetchAssetById() {
        Long assetId = 1L;
        Asset asset = new Asset();

        when(assetService.fetchAssetById(assetId)).thenReturn(asset);

        ResponseEntity<Asset> responseEntity = assetController.fetchAssetById(assetId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(asset, responseEntity.getBody());
    }

    @Test
    void getAssetByName() {
        String assetName = "Test Asset";
        Asset asset = new Asset();

        when(assetService.fetchAssetByName(assetName)).thenReturn(asset);

        ResponseEntity<List<Asset> responseEntity = assetController.getAssetByName(assetName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(asset, responseEntity.getBody());
    }

    @Test
    void fetchAllAssets() {
        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset());
        assets.add(new Asset());

        when(assetService.fetchAllAssets()).thenReturn(assets);

        List<Asset> fetchedAssets = assetController.fetchAllAssets();

        assertEquals(2, fetchedAssets.size());
    }

    @Test
    void deleteAsset() {
        Long assetId = 1L;

        String expectedMessage = "Asset with Id : " + assetId + " has been deleted successfully";

        String resultMessage = assetController.deleteAsset(assetId);

        assertEquals(expectedMessage, resultMessage);
        verify(assetService, times(1)).deleteAsset(assetId);
    }

    @Test
    void updateAsset() {
        Long assetId = 1L;
        Asset updatedAsset = new Asset();
        updatedAsset.setName("Updated Asset");

        when(assetService.updateAsset(assetId, updatedAsset)).thenReturn(updatedAsset);

        ResponseEntity<Asset> responseEntity = assetController.updateAsset(assetId, updatedAsset);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAsset, responseEntity.getBody());
    }

    @Test
    void assignAsset() {
        Long assetId = 1L;
        Status status = Status.ASSIGNED;

        ResponseEntity<Void> responseEntity = assetController.assignAsset(assetId, status);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(assetService, times(1)).assignAsset(assetId, status);
    }

    @Test
    void recoverAsset() {
        Long assetId = 1L;

        ResponseEntity<Void> responseEntity = assetController.recoverAsset(assetId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(assetService, times(1)).recoverAsset(assetId);
    }
}
