SQLKing
======================

SQLKing encapsulate all Android SQLite behaviour into an ORM that exposes a set of generic methods for CRUD operations. The provider allows database tables to be defined in models classes, the model objects can then be manipulated with ease through utility methods. 

####Define the SQLite database model####
The database model is defined using pojos (pure java objects) that implement the IModel interface. The getter and setter methods must match the class members, e.g; private String name; must be accompanied by getName() / setName(String newVal) methods.

```java
public class Message implements IModel {
	private String name;
	private String body;
	private long timestamp;
	private byte[] profilePicture;
	
	public String getName() {
		return name;
	}
	
	public void setName(String newVal) {
		name = newVal;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String newVal) {
		body = newVal;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long newVal) {
		timestamp = newVal;
	}
	
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(byte[] newVal) {
		profilePicture = newVal;
	}
}
```

####Initialise the database####
SQLKing will create the database representation of your IModel classes automatically, when these models are changed or new ones are added the version number argument should be incremented. NOTE: Incrementing the version number will destroy any existing database with the same name

```java
IModel[] modelArray = new IModel[] {
	new Message()
};
	
String databaseName = "SQLKing";
int versionNumber = 1;

SQLInitialise sql = new SQLInitialise(
	databaseName,
	versionNumber,
	getApplicationContext(),
	modelArray
);
SQLProvider sqlProvider = new SQLProvider(sql.getDatabase());
```

####Insert data####

```java
Message message = new Message();
message.setName("Angie");
message.setBody("Hello world!");
message.setTimestamp(System.currentTimeMillis());

long pid = sqlProvider.insert(message);
```

```java
Message message1 = new Message();
message1.setName("Angie");
message1.setBody("Hello world!");
message1.setTimestamp(System.currentTimeMillis());

Message message2 = new Message();
message2.setName("Sam");
message2.setBody("Hello!");
message2.setTimestamp(System.currentTimeMillis());

Message[] messageArray = new Message[] { message1, message2 };
long[] pidArray = sqlProvider.insertArray(messageArray);
```

####Selecting data####

```java
String order = "name ASC";
String limit = "0,10";

Message[] messageArray = sqlProvider.selectAll(
	Message.class, 
	order, 
	limit
);
```

####TODO:####
- Add a @nonpersitable annotation to the model class that will stop the property being treated as persistable
- Add methods that allow groupBy and having clauses to the SQLProvider
- Implement unit tests for SQLProvider and SQLDatabaseHelper methods
- Design a way to handle inner joins gracefully
- Use the builder pattern to create SQL queries instead of method arguments
- Update the database gracefully rather than purging and recreating it
- Inserting an array should use a UNION ALL INSERT instead of running multiple insert queries
- SELECT should evaluate whether there any object arrays, if there are they should be treated as a JOIN
