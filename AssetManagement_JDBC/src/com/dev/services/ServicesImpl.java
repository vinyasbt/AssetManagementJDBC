package com.dev.services;

import java.util.List;

import com.dev.beans.Asset;
import com.dev.beans.AssetAllocation;
import com.dev.beans.Employee;
import com.dev.beans.UserMaster;
import com.dev.dao.DAO;
import com.dev.dao.DAOImpl;

public class ServicesImpl implements Services {
	DAO dao = new DAOImpl();

	public UserMaster loginService(Integer userid, String password) {

		return dao.login(userid, password);
	}

	public Asset addAssetService(Asset asset) {
		// TODO Auto-generated method stub
		return dao.addAsset(asset);
	}

	public Asset removeAssetService(Integer aid) {

		return dao.removeAsset(aid);
	}

	// public Asset updateAssetService(Integer aid) {
	//
	// return d.updateAsset(aid);
	// }

	public List<Asset> getAllAssetService() {
		// TODO Auto-generated method stub
		return dao.getAllAsset();
	}

	public Employee addEmployeeService(Employee employee) {

		return dao.addEmployee(employee);
	}

	public AssetAllocation raiseAllocationService(AssetAllocation assetallocation) {
		return dao.raiseAllocation(assetallocation);
	}

	public List<AssetAllocation> getAllAssetAllocationService() {

		return dao.getAllAssetAllocation();
	}

	public String viewStatusService(Integer allocationid) {
		// TODO Auto-generated method stub
		return dao.viewStatus(allocationid);
	}

	public Asset updateAssetNameService(Integer aid, String assetname) {

		return dao.updateAssetName(aid, assetname);
	}

	public Asset updateAssetDesService(Integer aid, String assetdes) {

		return dao.updateAssetDes(aid, assetdes);
	}

	public Asset updateAssetQuantityService(Integer aid, Integer assetquan) {

		return dao.updateAssetQuantity(aid, assetquan);
	}

	public Asset updateAssetStatusService(Integer aid, String assetstatus) {

		return dao.updateAssetStatus(aid, assetstatus);
	}

	public Boolean setStatusService(Integer allocationid, String status) {

		return dao.setStatus(allocationid, status);
	}

}
