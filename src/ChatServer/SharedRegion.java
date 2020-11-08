package ChatServer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
/**
 * Classe per la gestione delle sezioni critiche
 * @author Patrissi Mathilde
 */
public class SharedRegion {
		private String dest="all";
		private java.util.Vector <String> list=new java.util.Vector <String>(1,1);
//		private boolean dispDest= false,dispList = false;
		
		Queue<String> status=new LinkedList<>();;
		
		Semaphore sem= new Semaphore(1,true);
		/**
	     * Metodo per inserire in una coda i messaggi provenienti dal server che saranno prelevati dalla GUI
	     *
	     * @param msg String
	     */
		public  void pushStatusMSG(String msg) {
			status.add(msg);
		}
		/**
	     * Metodo per prelevare dalla GUI i messaggi del server presenti nella coda
	     *
	     * @return il messaggio meno recente
	     */
		public String popStatusMSG() {
			if (status.isEmpty())
				return "Empty";
			else
				return status.poll();
		}
		
		/**
	     * Metodo che restituisce il nome dell'attuale destinatario
	     *
	     * @return il nome del destinatario
	     */
		public String getDest() {
			String d="";
			try {
				sem.acquire();
				d= dest;
			}
			catch(InterruptedException e) {
				System.out.println("Errore Semaforo");
			}
			sem.release();
			return d;
		}
		/**
	     * Metodo per memorizzare il nome dell'attuale destinatario
	     *
	     * @param d String
	     */
		public void putDest(String d) {			
			
			try {
				sem.acquire();
				dest = d;
			}
			catch(InterruptedException e) {
				System.out.println("Errore Semaforo");
			}
			sem.release();
		}
		/**
	     * Metodo che restituisce la lista degli utenti connessi
	     *
	     * @return la lista degli utenti connessi
	     */
		public java.util.Vector <String> getList() {
			java.util.Vector l=new java.util.Vector(1,1);
			try {
				sem.acquire();
				l=(java.util.Vector <String> ) list.clone();
			}
			catch(InterruptedException e) {
				System.out.println("Errore Semaforo");
			}	
			sem.release();	
			return l;
		}
		/**
	     * Metodo per memorizzare la lista degli utenti connessi
	     *
	     * @param l java.util.Vector <String>
	     */
		public void putList(java.util.Vector <String> l) {
			
			try {
				sem.acquire();
				list = (java.util.Vector <String> )l.clone();
			}
			catch(InterruptedException e) {
				System.out.println("Errore Semaforo");
			}	
			sem.release();		
		}		
}
