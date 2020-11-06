package ChatServer;

public class SharedRegion {
		private String dest;
		private java.util.Vector <String> list=new java.util.Vector <String>(1,1);
		private boolean dispDest= false,dispList = false;

	public synchronized String get() {
		while (dispDest == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		dispDest = false;
		String d= dest;
		notifyAll();
		return d;
	}
		public synchronized void put(String d) {
			while (dispDest == true) {
				try { wait(); } catch(InterruptedException e){}
			}
			dest = d;
			dispDest = true;
			notifyAll();
			
		}
		public synchronized java.util.Vector <String> getList() {
			while (dispList == false) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			dispList = false;
			java.util.Vector l=(java.util.Vector <String> ) list.clone();
			notifyAll();
			return l;
		}
			public synchronized void put(java.util.Vector <String> l) {
				while (dispList == true) {
					try { wait(); } catch(InterruptedException e){}
				}
				list = (java.util.Vector <String> )l.clone();
				dispList = true;
				notifyAll();
				
			}
}
