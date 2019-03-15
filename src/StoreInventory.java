import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StoreInventory
{
public static Connection connection;

//Manager

// change price of item
public static void priceChange(){	
	try{
		Statement statement = connection.createStatement();
		Scanner input = new Scanner(System.in);
			System.out.print("Product Code:");
				String productCode = input.next();
			System.out.print("New Price: ");
				String newPrice = input.next();
			statement.executeUpdate("update item " + " set i_itemprice = " + newPrice + " where i_productcode = " + productCode);
		}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Price is updated");
} 
//update order status
public static void updateOrder(){	
	try{
		Statement statement = connection.createStatement();
		Scanner input = new Scanner(System.in);
			System.out.print("Order Key ('Order Key'): ");
				String orderKey = input.next();
			
			System.out.print("Order Status EX.('ALL CAP INPUT'):");
				String orderStatus = input.next();
			
			statement.executeUpdate("update orders set o_orderstatus = " + orderStatus + " where o_orderkey = " + orderKey );
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Order Status is Updated");
} 
//add new supplier
public static void addSupplier(){	

			Scanner input = new Scanner(System.in);
			System.out.print("New supplier name: ");
				String supplierName = input.next();
							System.out.print("Supplier Number: ");
				String supplierKey = input.next();
							System.out.print("Supplier Quantity: ");
				String suppQuant = input.next();
						System.out.print("Order Key: ");
				String orderKey = input.next();
							System.out.print("Supplier State Key: ");	
				String stateKey = input.next();
		try{
		Statement statement = connection.createStatement();
			statement.executeUpdate("insert into supplier values ('"+supplierName+"', '"+supplierKey+"','"+suppQuant+"','"+orderKey+"','"+stateKey+"')");	
			
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		System.out.println("New Supplier is added");
} 
// select query, Urgent priority and number // employee too?
public static void seeExpire(){	
		try{
		//Scanner input = new Scanner(System.in);
		Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("select i_productname, s_quantity, sto_quantity, (s_quantity + sto_quantity) as total from item, stock, store where i_salepriority = 'URGENT' and s_productcode = i_productcode and sto_itemname = i_productname group by i_productname ");
						System.out.println("Near Expiration items:");
					while(rs.next()){
						System.out.println("	" + rs.getString("i_productname"));
								System.out.println("		In Stock:" + rs.getString("s_quantity") + "	In Store:" +rs.getString("sto_quantity") + "	Total:" + (rs.getString("total")));
			}
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} 
//Check low inventory //add aisle number?? //employee too?
public static void lowSupplied(){	
	Scanner input = new Scanner(System.in);
	System.out.println("In order to find which items are low on supply");
		System.out.print("	Please enter minimum quantity: ");
		String quantity = input.next();
	try{
		Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT i_productname, i_productcode,(s_quantity + sto_quantity) as total FROM item,stock, store WHERE i_productcode = s_productcode AND total <" + quantity + " group by i_productname");
					while(rs.next()){
			System.out.println( rs.getString("i_productname") + " [" + rs.getString("i_productcode")+"]");
			System.out.println("			Total:" + (rs.getString("total")));
			}
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} 

//Employee

//check inventory //manager too?
public static void checkInventory(){	
	try{
		//Scanner input = new Scanner(System.in);
		Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select i_productname, s_quantity, sto_quantity from item, stock, store where i_productcode = s_productcode and sto_itemname = i_productname group by i_productname order by s_quantity asc");
			System.out.println("List of Inventory:");	
			while(rs.next()){
			System.out.println(rs.getString("i_productname"));
			System.out.println("		In Stock: " + rs.getString("s_quantity") + "	In Store: " + rs.getString("sto_quantity"));
			}
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} 
//update stock
//edit
public static void updateStore(){	
	try{
		Statement statement = connection.createStatement();
		Scanner input = new Scanner(System.in);
			System.out.print("Item Name EX.('INPUT'): ");
				String itemName = input.next();
			System.out.print("Product Code: ");
				String productCode = input.next();
			System.out.print("Quantity: ");
				String numQuantity = input.next();
				
			statement.executeUpdate("update store set sto_quantity = sto_quantity + " + numQuantity + " where sto_itemname = " + itemName);
			statement.executeUpdate("update stock set s_quantity = s_quantity - " + numQuantity + " where s_productcode = " + productCode);
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("New inventory added to store from stock");
} 
//update sale priority
public static void updatePriority(){	 
	try{
		Statement statement = connection.createStatement();
		Scanner input = new Scanner(System.in);
			System.out.print("Product Code: ");
				String productCode = input.next();
			System.out.print("New sale priority EX.('MEDIUM'): ");
				String priority = input.next();
				
				statement.executeUpdate("update item set i_salepriority = " + priority + " where i_productcode = " + productCode);
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		System.out.println("Sale priority updated");
} 

public static void makeOrder(){	

	Scanner input = new Scanner(System.in);
	System.out.print("Order Key Ex.(#name&num): ");
		String orderKey = input.next();
	System.out.print("Supplier Key: ");
		String supplierKey = input.next();
	System.out.print("Order Status (PLACED): ");	
			String orderStatus = input.next();
	System.out.print("Order Price: ");
		String orderPrice = input.next();
	System.out.print("Order Priority Ex(MEDIUM): ");	
		String orderPriority = input.next();
	System.out.print("Order Quantity: ");
		String orderQuantity = input.next();
	System.out.print("Product Code: ");
		String productCode = input.next();
try{
Statement statement = connection.createStatement();
	statement.executeUpdate("insert into orders values ('"+orderKey+"', '"+supplierKey+"','"+orderStatus+"','"+orderPrice+"','"+orderPriority+"','"+orderQuantity+"','"+productCode+"')");	
	
}catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
System.out.println("Order is Placed");
} 

//Buyer Information

public static void itemInfo(){	
	try{
		Scanner input = new Scanner(System.in);
		Statement statement = connection.createStatement();
		System.out.println("Which Categories of items would you like to see?: ");
				System.out.println("	BEVERAGES");
				System.out.println("	CANNED&PACKAGED-FOODS ");
				System.out.println("	BAKERY");
				System.out.println("	BREAKFAST");
				System.out.println("	FROZEN-FOODS");
				System.out.println("	PRODUCE-MEAT");
				System.out.println("	REFRIDGERATED-FOODS");
				System.out.println("	MISCELLANEOUS&KITCHEN-ITEMS");
				System.out.println("	PRODUCE-FRUITS");
				System.out.println("	PRODUCE-VEGETABLES");
				System.out.println("	DAIRY");
			System.out.print("Categories: ");	
				String catName = input.next();
		
			ResultSet rs = statement.executeQuery("select i_productname, i_itemprice, st_statename,supp_suppname from item, state, categories, supplier where st_statekey = supp_statekey and supp_suppkey = i_suppkey and i_catID = c_catID and c_catname = " + catName + "group by i_productname");
			System.out.println("Under Category: " + catName + " are the following items:");
					while(rs.next()){
			System.out.println("	" + rs.getString("i_productname") +" : $" + rs.getString("i_itemprice") +" : " +rs.getString("supp_suppname") + " : " + rs.getString("st_statename"));
			}
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} 

public static void iteminStore(){	
	try{
		Scanner input = new Scanner(System.in);
		Statement statement = connection.createStatement();
		System.out.println("What item are you looking for?: ");
			String itemName = input.next();
		
			ResultSet rs = statement.executeQuery("select sto_itemname, sto_quantity,  sto_aisle, s_quantity from store, stock, item where s_productcode = i_productcode and i_productname = sto_itemname and sto_itemname = " + itemName);
			
			System.out.println("Here's what the system found: ");
					while(rs.next()){
			System.out.println("	 " + rs.getString("sto_itemname") + " : " + rs.getString("sto_quantity") + " in store at aisle " +rs.getString("sto_aisle"));
				System.out.println("			" + rs.getString("s_quantity") + " in stock. Ask an employee for assistance.");
			}			
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
} 


public static void disconnect(){
    try
    {
      if(connection != null)
        connection.close();
      	System.out.println("Connection Disconnected");
    }
    catch(SQLException e)
    {
      // connection close failed.
      System.err.println(e);
    }
}     

  public static void main(String[] args)
  {
    try
    {
      // create a database connection
    	connection = DriverManager.getConnection("jdbc:sqlite:/eclipse/workspace/Cse111 Project/storeinventory.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      Scanner input = new Scanner(System.in);
      	System.out.println("Welcome to Store Inventory Database System choose an option to get started:");
      		System.out.println("	[0] for Customer");
      		System.out.println("	[1] for Employee");
      		System.out.println("	[2] for Manager");
      		System.out.print("User Type: ");
      			int account = input.nextInt();
      		System.out.println("");	
      		if (account == 0 ){
      			int reStart = 0;
    			System.out.println("Welcome to store navigation");
      				do{
      					System.out.println("What would you like to assistance on?:");
      					System.out.println("	[0] List of items in each categories");
      					System.out.println("	[1] Item information");
      					System.out.print("Command: ");
      						int cusAcc = input.nextInt();
      					if (cusAcc == 0){
      						itemInfo();
      							System.out.println("Would you like more assistance? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (cusAcc == 1){
      						iteminStore();
      							System.out.println("Would you like more assistance? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}
      				}while( reStart == 0);
      						disconnect();
//Employee      						
      		}else if(account == 1){
      			int reStart = 0;
      				System.out.println("Employee access: ...APPROVED!");
      			do{
      			System.out.println("Please choose appropriate course of action:");
      				System.out.println("	[0] Inventory Check");
      				System.out.println("	[1] Update Item Count To Store");
      				System.out.println("	[2] Update Item Sale Priority");
      				System.out.println("	[3] List Near Expiration Items");
      				System.out.println("	[4] List Low Supplied Items");
      				System.out.println("	[5] Place An Order");
      			System.out.print("Action: ");
      				int eAction = input.nextInt();
      					if (eAction == 0){
      						checkInventory();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (eAction == 1){
      						updateStore();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (eAction == 2){
      						updatePriority();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
									reStart = input.nextInt();
      					}else if (eAction == 3){
      						seeExpire();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (eAction == 4){
      						lowSupplied();
 								System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
 									reStart = input.nextInt();
      					}else if (eAction == 5){
      						makeOrder();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}
      			} while(reStart == 0);
      				disconnect();
//Manager      			
      		}else if(account == 2){
      			int reStart = 0;
      				System.out.println("Manager account access: .. APPROVED!");
      			do{
      			System.out.println("Please choose appropriate administrative course of action:");
      				System.out.println("	[0] Change Price Of An Item");
      				System.out.println("	[1] Update Order To Processed");
      				System.out.println("	[2] Add New Supplier");
      				System.out.println("	[3] Inventory Check");
      			System.out.print("Action: ");
      				int mAction = input.nextInt();
      					if (mAction == 0){
      						priceChange();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (mAction == 1){
      						updateOrder();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (mAction == 2){
      						addSupplier();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}else if (mAction == 3){
      						checkInventory();
      							System.out.println("Would you like to execute another course of action? [0] Continue [1] Disconnect");
      								reStart = input.nextInt();
      					}
         			} while (reStart == 0);
      					disconnect();
      		}
      		}
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }    
    
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
    }
  }