package com.example.Service;

import com.example.entity.Asset;
import com.example.Exception.AssetManagementException;
import com.example.entity.Category;
import com.example.entity.Status;
import com.example.repo.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AssetService {
    @Autowired
    private final AssetRepository assetRepository;
    public AssetService(AssetRepository assetRepository)
    {
        this.assetRepository = assetRepository;
    }

    // q4 : asset management
    public ResponseEntity<String> addAsset(List<Asset> assets)
    {
        try
        {
            assetRepository.saveAll(assets);
            return ResponseEntity.ok("Assets added successfully");
        }
        catch(DataIntegrityViolationException ex)
        {
            return ResponseEntity.badRequest().body("Error : A record with same batch Id already exists");
        }
        catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : Failed to add asset");
        }

    }

    // q5 : list all assets
    public List<Asset> fetchAllAssets(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Asset> assetPage = assetRepository.findAll(pageable);
        return assetPage.getContent();
    }

    // q6 : search asset
    public Asset fetchAssetById(Long assetId)
    {
        Optional<Asset> assetOptional = assetRepository.findById(assetId);
        return assetOptional.orElseThrow(() -> new AssetManagementException("Asset not found with ID: " + assetId));
    }

    public Asset fetchAssetByBatchId(String batchId)
    {
        Optional<Asset> assetOptional = assetRepository.findByBatchId(batchId);
        return assetOptional.orElseThrow(() -> new AssetManagementException("Asset not found with batchId: " + batchId));

    }


    public List<Asset> fetchAssetByName(String name)
    {
        //Optional<Asset> assetOptional = assetRepository.findByName(name);
        //return assetOptional.orElseThrow(() -> new AssetManagementException("Asset not found with Name: " + name));
        return assetRepository.findByName(name);
    }

    public List<Asset> fetchAssetByCategory(Category category)
    {
//        Optional<Asset> assetOptional = assetRepository.findByCategory(category);
//        return assetOptional.orElseThrow(() -> new AssetManagementException("Asset not found with Name: " + category));
        return assetRepository.findByCategory(category);
    }


    // q7 : update asset
    public Asset updateAsset(Long assetId, Asset updatedAsset)
    {
        if (assetId == null) {
            throw new IllegalArgumentException("Asset ID cannot be null");
        }
        Optional<Asset> existingAsset = assetRepository.findById(assetId);
        if(existingAsset.isPresent())
        {
            Asset asset = existingAsset.get();
            asset.setName(updatedAsset.getName());
            asset.setPurchaseDate(updatedAsset.getPurchaseDate());
            asset.setConditionNote(updatedAsset.getConditionNote());
            asset.setCategory(updatedAsset.getCategory());
            asset.setStatus(updatedAsset.getStatus());
            return assetRepository.save(asset);
        }
        else
        {
            throw new AssetManagementException("Asset not found with ID : " + assetId);
        }

    }

    // q8 : assign asset
    public void assignAsset(Long assetId, Status status)
    {
        if (assetId == null) {
            throw new IllegalArgumentException("Asset ID cannot be null");
        }
        Optional<Asset> existingAsset = assetRepository.findById(assetId);
        if(existingAsset.isPresent())
        {
            Asset asset = existingAsset.get();
            if(asset.getStatus() == Status.AVAILABLE || asset.getStatus() == Status.RECOVERED)
            {
                asset.setStatus(status);
                assetRepository.save(asset);
            }
            else {
                throw new AssetManagementException("Failed to assign the asset with Id : " + assetId);
            }
        }
        else {
            throw new AssetManagementException("Asset not found with ID: " + assetId);
        }
    }

    // q9 : recover asset
    public void recoverAsset(Long assetId)
    {
        if(assetId == null)
        {
            throw new IllegalArgumentException("Asset ID cannot be null");
        }
        Optional<Asset> existingAsset = assetRepository.findById(assetId);
        if(existingAsset.isPresent())
        {
            Asset asset = existingAsset.get();
            if(asset.getStatus() == Status.AVAILABLE)
            {
                throw new AssetManagementException("Asset with ID " + assetId + " is already set to AVAILABLE");
            }
            else if(asset.getStatus() == Status.RECOVERED)
            {
                throw new AssetManagementException("Asset with ID " + assetId + " is already set to RECOVERED");
            }
            else
            {
                asset.setStatus(Status.RECOVERED);
                assetRepository.save(asset);
            }
        }
        else
        {
            throw new AssetManagementException("Asset not found with ID: " + assetId);
        }

    }


    // q10 : delete asset
    public void deleteAsset(Long assetId)
    {
        if(assetId == null) {
            throw new IllegalArgumentException("Asset ID cannot be null");
        }
        Optional<Asset> existingAsset = assetRepository.findById(assetId);
        if(existingAsset.isPresent())
        {
            Asset asset = existingAsset.get();
            if(asset.getStatus() != Status.ASSIGNED) {
                assetRepository.deleteById(assetId);
            }
            else
            {
                throw new AssetManagementException("Asset with ID : " + assetId + " can't be deleted because it is in Assigned state");
            }
        }
        else
        {
            throw new AssetManagementException("Asset not found with ID: " + assetId);
        }
    }
}
