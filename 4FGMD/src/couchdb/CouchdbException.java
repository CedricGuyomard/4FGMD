package couchdb;

public class CouchdbException extends Exception {
	
	public CouchdbException()
	{}
	
	public CouchdbException(String msg)
	{
		super(msg);
	}
	
	public CouchdbException(Throwable th)
	{
		super(th);
	}
	
	public CouchdbException(String msg, Throwable th)
	{
		super(msg, th);
	}
}
