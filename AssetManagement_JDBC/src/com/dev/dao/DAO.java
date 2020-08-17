package com.dev.dao;

import java.util.List;

import com.dev.beans.Asset;
import com.dev.beans.AssetAllocation;
import com.dev.beans.Employee;
import com.dev.beans.UserMaster;

public interface DAO {
	UserMaster login(Integer userid,String password);
	Asset addAsset(Asset asset);
	Asset removeAsset(Integer aid);
	Asset updateAssetName(Integer aid,String assetname);
	Asset updateAssetDes(Integer aid,String assetdes);
	Asset updateAssetQuantity(Integer aid,Integer assetquan);
	Asset updateAssetStatus(Integer aid,String assetstatus);
	List<Asset> getAllAsset();
	Employee addEmployee(Employee employee);
	AssetAllocation raiseAllocation(AssetAllocation assetallocation);
	List<AssetAllocation> getAllAssetAllocation();
	Boolean setStatus(Integer allocationid,String status);
	String viewStatus(Integer allocationid);
	
}
