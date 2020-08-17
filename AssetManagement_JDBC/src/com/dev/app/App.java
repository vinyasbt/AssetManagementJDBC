package com.dev.app;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.dev.beans.Asset;
import com.dev.beans.AssetAllocation;
import com.dev.beans.Employee;
import com.dev.beans.UserMaster;
import com.dev.exceptions.ValidationException;
import com.dev.services.Services;
import com.dev.services.ServicesImpl;
import com.util.validations.Validate;

public class App {

	public static void main(String[] args) {

		Services service = new ServicesImpl();
		Scanner sc = new Scanner(System.in);
		Validate validate = new Validate();

		jump2: while (true) {
			System.out.println("*******************************");
			System.out.println("enter your choice ");
			System.out.println("1. Admin");
			System.out.println("2. manager");
			System.out.println("3. exit program");
			System.out.println("*******************************");
			Integer id = sc.nextInt();

			if (id == 2)

			{
				System.out.println("enter the user id");
				Integer userid = sc.nextInt();
				System.out.println("enter password");
				String password = sc.next();

				UserMaster um = service.loginService(userid, password);
				if (um.getUsertype().equalsIgnoreCase("manager"))
					System.out.println("manager");

				jump1: while (true) {
					System.out.println("*******************************");
					System.out.println("enter your choice");
					System.out.println("1.Add employee");
					System.out.println("2.Raise Allocation ");
					System.out.println("3.View Status");
					System.out.println("4. Exit");
					System.out.println("*******************************");
					Integer choice1 = sc.nextInt();
					switch (choice1) {
					case 1:
						Employee employee = new Employee();
						System.out.println("enter employee id");
						String empid = sc.next();

						Boolean b = validate.numberValidation(empid);
						jump: while (!b) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								empid = sc.next();
								if (validate.numberValidation(empid)) {
									break jump;
								}
							}
						}
						employee.setEmpno(Integer.parseInt(empid));
						System.out.println("enter employee name");

						String name = sc.next();
						Boolean b1 = validate.numberValidation(name);
						jumpvalidate: while (b1) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in string format");
								System.out.println("enter again");
								name = sc.next();
								if (!validate.numberValidation(name)) {
									break jumpvalidate;
								}
							}
						}
						employee.setEname(name);
						System.out.println("enter deptid of the employee");

						String deptid = sc.next();

						Boolean b2 = validate.numberValidation(deptid);
						jump: while (!b2) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								deptid = sc.next();
								if (validate.numberValidation(deptid)) {
									break jump;
								}
							}
						}
						employee.setDeptid(Integer.parseInt(deptid));
						System.out.println("enter hiredate of the employee");
						String hiredate = sc.next();
						Boolean b3 = validate.dateValidation(hiredate);
						jump: while (!b3) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in YYYY/MM/DD format");
								System.out.println("enter again");
								hiredate = sc.next();
								if (validate.dateValidation(hiredate)) {
									break jump;
								}
							}
						}
						employee.setHiredate(hiredate);

						System.out.println("enter job of employee");

						String job = sc.next();
						Boolean b4 = validate.numberValidation(job);
						jumpvalidate: while (b4) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in string format");
								System.out.println("enter again");
								job = sc.next();
								if (!validate.numberValidation(job)) {
									break jumpvalidate;
								}
							}
						}
						employee.setJob(job);
						System.out.println("enter mgr number");

						String mgr = sc.next();

						Boolean b5 = validate.numberValidation(mgr);
						jump: while (!b5) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								mgr = sc.next();
								if (validate.numberValidation(mgr)) {
									break jump;
								}
							}
						}
						employee.setMgrno(Integer.parseInt(mgr));
						System.out.println("Added Employee :" + service.addEmployeeService(employee));
						break;
					case 2:
						AssetAllocation assetallocation = new AssetAllocation();
						Integer min = 1;
						Integer max = 300;
						Integer rand = (int) ((Math.random() * ((max - min) + 1)) + min);

						assetallocation.setAllocationid(rand);

						System.out.println("Enter Asset id ");

						String assetid = sc.next();

						Boolean b6 = validate.numberValidation(assetid);
						jump: while (!b6) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								assetid = sc.next();
								if (validate.numberValidation(assetid)) {
									break jump;
								}
							}
						}
						assetallocation.setAssetid(Integer.parseInt(assetid));

						System.out.println("Enter employee number");
						String empno = sc.next();

						Boolean b7 = validate.numberValidation(empno);
						jump: while (!b7) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								empno = sc.next();
								if (validate.numberValidation(empno)) {
									break jump;
								}
							}
						}
						assetallocation.setEmpno(Integer.parseInt(empno));
						System.out.println("Enter allocation date");

						String date = sc.next();
						Boolean b8 = validate.dateValidation(date);
						jump: while (!b8) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in YYYY/MM/DD format");
								System.out.println("enter again");
								date = sc.next();
								if (validate.dateValidation(date)) {
									break jump;
								}
							}
						}
						assetallocation.setAllocationdate(date);
						System.out.println("Enter release date ");

						String releasedate = sc.next();
						Boolean b9 = validate.dateValidation(releasedate);
						jump: while (!b9) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in YYYY/MM/DD format");
								System.out.println("enter again");
								releasedate = sc.next();
								if (validate.dateValidation(releasedate)) {
									break jump;
								}
							}
						}
						assetallocation.setReleasedate(releasedate);
						System.out.println("Enter quantity");
						String quantity = sc.next();

						Boolean b10 = validate.numberValidation(quantity);
						jump: while (!b10) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								quantity = sc.next();
								if (validate.numberValidation(quantity)) {
									break jump;
								}
							}
						}
						assetallocation.setQuantity(Integer.parseInt(quantity));
						System.out.println(
								"Raised allocation request :" + service.raiseAllocationService(assetallocation));
						System.out.println("Randomly generated allocation id :" + assetallocation.getAllocationid());

						break;
					case 3:
						System.out.println("enter the allocation id");
						String allocationid = sc.next();

						Boolean b11 = validate.numberValidation(allocationid);
						jump: while (!b11) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								allocationid = sc.next();
								if (validate.numberValidation(allocationid)) {
									break jump;
								}
							}
						}
						System.out.println(service.viewStatusService(Integer.parseInt(allocationid)));
						break;
					case 4:
						System.out.println("manager logged out successfully");
						break jump1;
					default:
						System.out.println("enter valid number");
						break;

					}

				}
			}

			else if (id == 1) {
				System.out.println("enter the user id");
				Integer userid = sc.nextInt();
				System.out.println("enter password");
				String password = sc.next();

				UserMaster um = service.loginService(userid, password);
				if (um.getUsertype().equalsIgnoreCase("admin"))

					System.out.println("admin");
				jump: while (true) {
					System.out.println("*******************************");
					System.out.println("enter your choice");
					System.out.println("1.add asset");
					System.out.println("2.Remove asset");
					System.out.println("3. update asset");
					System.out.println("4. view all asset");
					System.out.println("5. view all allocation request");
					System.out.println("6. set allocation status");
					System.out.println("7. exit");
					System.out.println("*******************************");
					Integer choice = sc.nextInt();
					switch (choice) {
					case 1:
						Asset a = new Asset();
						System.out.println("enter asset id");
						String assetid = sc.next();

						Boolean b = validate.numberValidation(assetid);
						jumpadmin: while (!b) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								assetid = sc.next();
								if (validate.numberValidation(assetid)) {
									break jumpadmin;
								}
							}
						}
						a.setAssetid(Integer.parseInt(assetid));
						System.out.println(" enter asset name ");
						String assetname = sc.next();
						Boolean b1 = validate.numberValidation(assetname);
						jumpadmin: while (b1) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in string format");
								System.out.println("enter again");
								assetname = sc.next();
								if (!validate.numberValidation(assetname)) {
									break jumpadmin;
								}
							}
						}

						a.setAssetname(assetname);
						System.out.println("enter asset description");
						a.setAssetdes(sc.next());
						System.out.println("enter asset quantity");
						String assetquantity = sc.next();

						Boolean b2 = validate.numberValidation(assetquantity);
						jumpadmin: while (!b2) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								assetquantity = sc.next();
								if (validate.numberValidation(assetquantity)) {
									break jumpadmin;
								}
							}
						}
						a.setQuantity(Integer.parseInt(assetquantity));
						System.out.println(" enter asset status ");
						String status = sc.next();
						Boolean b3 = validate.numberValidation(status);
						jumpadmin: while (b3) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in string format");
								System.out.println("enter again");
								status = sc.next();
								if (!validate.numberValidation(status)) {
									break jumpadmin;
								}
							}
						}
						a.setStatus(status);

						System.out.println("Added asset :" + service.addAssetService(a));
						break;
					case 2:
						System.out.println("enter the asset id you want to remove");
						Asset a1 = new Asset();
						String assetid1 = sc.next();

						Boolean b4 = validate.numberValidation(assetid1);
						jumpadmin: while (!b4) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								assetid1 = sc.next();
								if (validate.numberValidation(assetid1)) {
									break jumpadmin;
								}
							}
						}
						a1 = service.removeAssetService(Integer.parseInt(assetid1));
						System.out.println("removed asset is :" + a1);
						break;

					case 3:
						System.out.println("enter the asset id you want to update");
						String assetid2 = sc.next();

						Boolean b5 = validate.numberValidation(assetid2);
						jumpadmin: while (!b5) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								assetid2 = sc.next();
								if (validate.numberValidation(assetid2)) {
									break jumpadmin;
								}
							}
						}
						System.out.println("to update asset");
						System.out.println("enter the choice");
						System.out.println("1.update asset name");
						System.out.println("2.update asset des ");
						System.out.println("3. update asset quantity");
						System.out.println("4.update asset status");
						String assetchoice = sc.next();

						Boolean b10 = validate.numberValidation(assetchoice);
						jumpadmin: while (!b10) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								assetchoice = sc.next();
								if (validate.numberValidation(assetchoice)) {
									break jumpadmin;
								}
							}
						}
						switch (Integer.parseInt(assetchoice)) {
						case 1:
							System.out.println("enter updated asset name");
							String updatedassetname = sc.next();
							System.out.println("updated asset name :"
									+ service.updateAssetNameService(Integer.parseInt(assetid2), updatedassetname));
							break;

						case 2:
							System.out.println("enter updated asset des");
							String updatedassetdes = sc.next();
							System.out.println("updated asset des :"
									+ service.updateAssetDesService(Integer.parseInt(assetid2), updatedassetdes));
							break;

						case 3:
							System.out.println("enter updated asset quantity");
							Integer updatedassetquantity = sc.nextInt();
							System.out.println("updated asset quantity :" + service
									.updateAssetQuantityService(Integer.parseInt(assetid2), updatedassetquantity));
							break;

						case 4:
							System.out.println("enter updated asset status");
							String updatedassetstatus = sc.next();
							System.out.println("updated asset status :"
									+ service.updateAssetStatusService(Integer.parseInt(assetid2), updatedassetstatus));
							break;

						}
						break;
					case 4:
						System.out.println("assets are");
						List list=service.getAllAssetService();
						Iterator it=list.iterator();
						while(it.hasNext())
						{
							System.out.println(it.next());
						}
						break;
					case 5:
						List list1=service.getAllAssetAllocationService();
						Iterator it1=list1.iterator();
						while(it1.hasNext())
						{
							System.out.println(it1.next());
						}
						break;
					case 6:
						System.out.println("enter allocation id to set status");

						String allocationid = sc.next();

						Boolean b6 = validate.numberValidation(allocationid);
						jumpadmin: while (!b6) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter number");
								System.out.println("enter again");
								allocationid = sc.next();
								if (validate.numberValidation(allocationid)) {
									break jumpadmin;
								}
							}
						}
						Integer allocation_id = Integer.parseInt(allocationid);
						System.out.println("enter status");
						String assetstatus = sc.next();
						Boolean b11 = validate.numberValidation(assetstatus);
						jumpadmin: while (b11) {
							try {
								throw new ValidationException();
							} catch (ValidationException e1) {
								System.out.println("please enter in string format");
								System.out.println("enter again");
								assetstatus = sc.next();
								if (!validate.numberValidation(assetstatus)) {
									break jumpadmin;
								}
							}
						}

						if (service.setStatusService(allocation_id, assetstatus)) {
							System.out.println("status changed");
						} else {
							System.out.println("status not changed");
						}
						break;

					case 7:
						System.out.println("admin logged out successfully");
						break jump;
					default:
						System.out.println("enter valid number");
						break;
					}
				}
			} else if (id == 3) {
				System.out.println("Thank you...visit again");
				sc.close();
				break jump2;
			}

		}

	}

}
