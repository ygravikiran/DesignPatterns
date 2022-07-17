package singleton;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

//Java Program to write double checked locking


/************
 * Stops
 * 		Cloning
 * 		Serializable
 * 		Garbaged Collected -> Overriding finalize Method
 * 
 * Private Constructor
 * Private Static Variable
 * Static Method -> to be called to get Object
 *
 */

public class Singleton_DoubleLocking implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private volatile static Singleton_DoubleLocking singletonObj;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * this method invoked automatically during serialization process
	 *
	 * @param objectOutputStream
	 * @throws Exception
	 */
	private void writeObject(ObjectOutputStream objectOutputStream) throws Exception {

		// don't provide implementation details here
		throw new IOException("Serialization not allowed");
	}

	public void finalize()
	    {
			singletonObj = this; // Putting the reference id
	        // of the current object
	        // into the static variable y
	  
	        System.out.println("Object reference saved. The object won't be collected by the garbage collector");
	    }

	private Singleton_DoubleLocking() {
	}

	// 1st version: creates multiple singletonObjs if two thread
	// access this method simultaneously
	public static Singleton_DoubleLocking getsingletonObj1() {
		if (singletonObj == null) {
			singletonObj = new Singleton_DoubleLocking();
		}

		return singletonObj;
	}

	// 2nd version : this is thread-safe and only
	// creates one singletonObj of Singleton on concurrent
	// environment but it is unnecessarily expensive due to
	// cost of synchronization at every call.
	public static synchronized Singleton_DoubleLocking getsingletonObj2() {
		if (singletonObj == null) {
			singletonObj = new Singleton_DoubleLocking();
		}
		return singletonObj;
	}

	// 3rd version : An implementation of double checked
	// locking of Singleton. Intention is to reduce cost
	// of synchronization and improve performance, by only
	// locking critical section of code, the code which
	// creates singletonObj of Singleton class.
	public static Singleton_DoubleLocking getsingletonObj3() {
		if (singletonObj == null) {
			synchronized (Singleton_DoubleLocking.class) {
				if (singletonObj == null) {
					singletonObj = new Singleton_DoubleLocking();
				}
			}
		}
		return singletonObj;
	}
}
