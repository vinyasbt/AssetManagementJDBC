package com.dev.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.dev.beans.Asset;
import com.dev.beans.AssetAllocation;
import com.dev.beans.Employee;
import com.dev.beans.UserMaster;
import com.dev.exceptions.AddAssetException;
import com.dev.exceptions.AddEmployeeException;
import com.dev.exceptions.AssetAllocationException;
import com.dev.exceptions.GetAssetException;
import com.dev.exceptions.LoginException;
import com.dev.exceptions.RaiseAllocationException;
import com.dev.exceptions.RemoveAssetException;
import com.dev.exceptions.StatusException;
import com.dev.exceptions.UpdateAssetException;
import com.dev.exceptions.ValidationException;
import com.util.validations.Validate;

public class DAOImpl implements DAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	Statement stmt = null;
	ResultSet rs = null;
	Integer result = null;

	void jdbcConnection() {

		try {
			Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			String url = "jdbc:mysql://localhost:3307/asset_management?user=root&password=root";
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserMaster login(Integer userid, String password) {
		UserMaster um = new UserMaster();
		jdbcConnection();
		try {

			String query = "select UserType from user_master where UserId=? and UserPassword=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userid);
			pstmt.setString(2, password);
			Boolean b = pstmt.execute();
			if (b) {
				rs = pstmt.getResultSet();
				String s = null;

				while (rs.next()) {
					s = rs.getString(1);
				}
				um.setUsertype(s);
				return um;

			} else {
				throw new LoginException();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return um;
	}

	@Override
	public Asset addAsset(Asset asset) {
		jdbcConnection();
		try {
			String check = "select * from asset";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(check);
			while (rs.next()) {
				if (rs.getInt(1) == asset.getAssetid()) {
					throw new AddAssetException();
				}
			}

			String query = "insert into asset values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, asset.getAssetid());
			pstmt.setString(2, asset.getAssetname());
			pstmt.setString(3, asset.getAssetdes());
			pstmt.setInt(4, asset.getQuantity());
			pstmt.setString(5, asset.getStatus());
			pstmt.setString(6,"null");

			result = pstmt.executeUpdate();

			if (result > 0) {

				return asset;

			} else {
				throw new AddAssetException();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@Override
	public Asset removeAsset(Integer aid) {
		Asset a = new Asset();
		try {
			jdbcConnection();

			String query = "select * from asset where AssetId=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, aid);

			Boolean result = pstmt.execute();

			if (result) {
				rs = pstmt.getResultSet();
				while (rs.next()) {
					a.setAssetid(rs.getInt(1));
					a.setAssetname(rs.getString(2));
					a.setAssetdes(rs.getString(3));
					a.setQuantity(rs.getInt(4));
					a.setStatus(rs.getString(5));

					String query1 = "delete from asset where AssetId=?";
					pstmt = conn.prepareStatement(query1);
					pstmt.setInt(1, aid);
					Integer count = pstmt.executeUpdate();
					if (count > 0) {

						return a;
					} else {
						throw new RemoveAssetException();
					}

				}
			}

			throw new RemoveAssetException();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@Override
	public List<Asset> getAllAsset() {
		jdbcConnection();
		try {
			List<Asset> list = new ArrayList<Asset>();
			String query = "select * from asset";
			pstmt = conn.prepareStatement(query);

			Boolean b = pstmt.execute();
			if (b) {
				rs = pstmt.getResultSet();

				while (rs.next()) {

					Asset asset = new Asset();
					asset.setAssetid(rs.getInt(1));
					asset.setAssetname(rs.getString(2));
					asset.setAssetdes(rs.getString(3));
					asset.setQuantity(rs.getInt(4));
					asset.setStatus(rs.getString(5));
					list.add(asset);
				}
				return list;

			} else {
				throw new GetAssetException();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@Override
	public Employee addEmployee(Employee employee) {
		try {
			jdbcConnection();
			String check = "select * from employee";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(check);
			while (rs.next()) {
				if (rs.getInt(1) == employee.getEmpno()) {
					throw new AddEmployeeException();
				}
			}
			String q = "select dept_id from department ";
			pstmt = conn.prepareStatement(q);
			Boolean b = pstmt.execute();
			if (b) {
				int temp = 0;
				rs = pstmt.getResultSet();
				Integer s = null;
				while (rs.next()) {
					s = rs.getInt(1);

					if (employee.getDeptid() == s) {
						temp = 1;
					}

				}
				if (temp != 1) {
					System.out.println();
					throw new AddEmployeeException();
				}
			} else {
				throw new AddEmployeeException();
			}

			String query = "insert into employee values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, employee.getEmpno());
			pstmt.setString(2, employee.getEname());
			pstmt.setString(3, employee.getJob());
			pstmt.setInt(4, employee.getMgrno());
			pstmt.setString(5, employee.getHiredate());
			pstmt.setInt(6, employee.getDeptid());

			result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("employee added successfully");
				return employee;

			} else {
				throw new AddEmployeeException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public AssetAllocation raiseAllocation(AssetAllocation assetallocation) {
		jdbcConnection();
		try {
			String check = "select * from asset_allocation";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(check);
			while (rs.next()) {
				if (rs.getInt(1) == assetallocation.getAllocationid()) {
					throw new RaiseAllocationException();
				}
			}
			String query = "insert into asset_allocation values(?, ?, ?, ?, ?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, assetallocation.getAllocationid());
			pstmt.setInt(2, assetallocation.getAssetid());
			pstmt.setInt(3, assetallocation.getEmpno());
			pstmt.setString(4, assetallocation.getAllocationdate());
			pstmt.setString(5, assetallocation.getReleasedate());
			pstmt.setInt(6, assetallocation.getQuantity());
			result = pstmt.executeUpdate();
			String query1 = "insert into allocation_status values(?, ?)";
			pstmt1 = conn.prepareStatement(query1);
			pstmt1.setInt(1, assetallocation.getAllocationid());
			pstmt1.setString(2, "null");
			Integer result1 = pstmt1.executeUpdate();

			if (result > 0 && result1 > 0) {

				return assetallocation;
			} else {
				throw new RaiseAllocationException();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;

	}

	@Override
	public List<AssetAllocation> getAllAssetAllocation() {
		jdbcConnection();
		try {
			List<AssetAllocation> list = new ArrayList<AssetAllocation>();
			String query = "select * from asset_allocation";
			pstmt = conn.prepareStatement(query);
			Boolean b = pstmt.execute();
			if (b) {
				rs = pstmt.getResultSet();

				while (rs.next()) {

					AssetAllocation assetallocation = new AssetAllocation();
					assetallocation.setAllocationid(rs.getInt(1));
					assetallocation.setAssetid(rs.getInt(2));
					assetallocation.setEmpno(rs.getInt(3));
					assetallocation.setAllocationdate(rs.getString(4));
					assetallocation.setReleasedate(rs.getString(5));
					assetallocation.setQuantity(rs.getInt(6));
					list.add(assetallocation);

				}
				return list;
			} else {
				throw new GetAssetException();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;

	}

	@Override
	public Boolean setStatus(Integer allocationid, String a) {
		jdbcConnection();
		try {
			String query = "update allocation_status set status=? where AllocationId=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, a);
			pstmt.setInt(2, allocationid);

			result = pstmt.executeUpdate();
			if (result > 0) {
				return true;

			}
			throw new StatusException();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	@Override
	public String viewStatus(Integer allocationid) {
		jdbcConnection();
		try {

			String query = "select * from allocation_status where AllocationId=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, allocationid);

			Boolean b = pstmt.execute();
			if (b) {
				rs = pstmt.getResultSet();
				while (rs.next()) {
					System.out.println("Allocation ID:" + rs.getInt(1));

					if (rs.getString(2).equals("null")) {
						return "status not updated till now";
					}
					System.out.println("Status:" + rs.getString(2));
					return "";
				}
			}
			throw new StatusException();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return "enter valid allocation id";
	}

	@Override
	public Asset updateAssetName(Integer aid, String assetname) {
		Asset a1 = new Asset();
		try {
			jdbcConnection();

			String query = "select * from asset where AssetId=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, aid);

			Boolean result = pstmt.execute();

			if (result) {
				rs = pstmt.getResultSet();
				while (rs.next()) {
					a1.setAssetid(rs.getInt(1));
					a1.setAssetname(rs.getString(2));
					a1.setAssetdes(rs.getString(3));
					a1.setQuantity(rs.getInt(4));
					a1.setStatus(rs.getString(5));

					String query1 = "update asset set AssetName=? where AssetId=?";
					pstmt = conn.prepareStatement(query1);

					// String name=sc.next();
					pstmt.setString(1, assetname);
					pstmt.setInt(2, aid);
					Integer count = pstmt.executeUpdate();
					if (count > 0) {
						System.out.println("Asset name updated successfully");
						a1.setAssetname(assetname);
						return a1;
					}
				}
			}

			throw new UpdateAssetException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	@Override
	public Asset updateAssetDes(Integer aid, String assetdes) {
		Asset a2 = new Asset();
		try {
			jdbcConnection();

			String query = "select * from asset where AssetId=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, aid);

			Boolean result = pstmt.execute();

			if (result) {
				rs = pstmt.getResultSet();
				while (rs.next()) {
					a2.setAssetid(rs.getInt(1));
					a2.setAssetname(rs.getString(2));
					a2.setAssetdes(rs.getString(3));
					a2.setQuantity(rs.getInt(4));
					a2.setStatus(rs.getString(5));

					String query1 = "update asset set AssetDes=? where AssetId=?";
					pstmt = conn.prepareStatement(query1);

					pstmt.setString(1, assetdes);
					pstmt.setInt(2, aid);
					Integer count = pstmt.executeUpdate();
					if (count > 0) {
						System.out.println("AssetDes updated successfully");
						a2.setAssetdes(assetdes);
						return a2;
					}
				}
			}

			throw new UpdateAssetException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// throw new UpdateAssetException();
		return null;
	}

	@Override
	public Asset updateAssetQuantity(Integer aid, Integer assetquantity) {
		Asset a3 = new Asset();
		try {
			jdbcConnection();

			String query = "select * from asset where AssetId=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, aid);

			Boolean result = pstmt.execute();

			if (result) {
				rs = pstmt.getResultSet();
				while (rs.next()) {
					a3.setAssetid(rs.getInt(1));
					a3.setAssetname(rs.getString(2));
					a3.setAssetdes(rs.getString(3));
					a3.setQuantity(rs.getInt(4));
					a3.setStatus(rs.getString(5));

					String query1 = "update asset set Quantity=? where AssetId=?";
					pstmt = conn.prepareStatement(query1);
					pstmt.setInt(1, assetquantity);
					pstmt.setInt(2, aid);
					Integer count = pstmt.executeUpdate();
					if (count > 0) {
						System.out.println("Quantity updated successfully");
						a3.setQuantity(assetquantity);
						return a3;
					}
				}
			}

			throw new UpdateAssetException();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Asset updateAssetStatus(Integer aid, String assetstatus) {
		Asset a4 = new Asset();
		try {
			jdbcConnection();

			String query = "select * from asset where AssetId=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, aid);

			Boolean result = pstmt.execute();

			if (result) {
				rs = pstmt.getResultSet();
				while (rs.next()) {
					a4.setAssetid(rs.getInt(1));
					a4.setAssetname(rs.getString(2));
					a4.setAssetdes(rs.getString(3));
					a4.setQuantity(rs.getInt(4));
					a4.setStatus(rs.getString(5));

					String query1 = "update asset set status=? where AssetId=?";
					pstmt = conn.prepareStatement(query1);
					pstmt.setString(1, assetstatus);
					pstmt.setInt(2, aid);
					Integer count = pstmt.executeUpdate();
					if (count > 0) {
						System.out.println("Status updated successfully");
						a4.setStatus(assetstatus);
						return a4;
					}

				}
			}

			throw new UpdateAssetException();

		} catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
