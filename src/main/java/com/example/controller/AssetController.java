package com.example.controller;

import com.example.Service.AssetService;
import com.example.entity.Asset;
import com.example.entity.Category;
import com.example.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping("/addAsset") // add new asset
    public ResponseEntity<String> addAsset(@RequestBody List<Asset> assets)
    {
        ResponseEntity<String> response = assetService.addAsset(assets);

        if(response.getStatusCode().is2xxSuccessful())
        {
            return ResponseEntity.ok(response.getBody());
        }
        else
        {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    @GetMapping("/getAsset/id/{assetId}")  // get info of a particular asset
    public ResponseEntity<Asset> fetchAssetById(@PathVariable("assetId") Long assetId)
    {
        Asset asset = assetService.fetchAssetById(assetId);
        if(asset != null)
        {
            return ResponseEntity.ok(asset);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAsset/batchId/{batchId}")
    public ResponseEntity<Asset> getAssetByBatchId(@PathVariable String batchId)
    {
        Asset asset = assetService.fetchAssetByBatchId(batchId);
        if(asset != null)
        {
            return ResponseEntity.ok(asset);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getAsset/name/{name}")
    public List<Asset> getAssetByName(@PathVariable("name") String name)
    {
        return assetService.fetchAssetByName(name);
    }

    @GetMapping("/getAsset/category")
    public List<Asset> fetchAssetByCategory(@RequestBody Category category)
    {
        return assetService.fetchAssetByCategory(category);
    }
    @GetMapping("/getAssets")   // get info of all assets
    public ResponseEntity<List<Asset>> fetchAllAssets(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size)
    {
        List<Asset> assets = assetService.fetchAllAssets(page, size);
        return ResponseEntity.ok(assets);    }

    @DeleteMapping("/{assetId}") // to delete asset
    public String deleteAsset(@PathVariable Long assetId)
    {
        assetService.deleteAsset(assetId);
        return "Asset with Id : " + assetId + " has been deleted successfully";
    }

    @PutMapping("/{assetId}") // to update asset
    public ResponseEntity<Asset> updateAsset(@PathVariable Long assetId, @RequestBody Asset updatedAsset)
    {
         Asset asset = assetService.updateAsset(assetId, updatedAsset);
         return (asset != null) ? ResponseEntity.ok(asset) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{assetId}/assign") // to assign asset
    public ResponseEntity<Void> assignAsset(@PathVariable Long assetId, @RequestBody Status status)
    {
        assetService.assignAsset(assetId, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{assetId}/recover") // to recover asset
    public ResponseEntity<Void> recoverAsset(@PathVariable("assetId") Long assetId)
    {
        assetService.recoverAsset(assetId);
        return ResponseEntity.noContent().build();
    }

}
