package ca.polymtl.inf4410.tp1.shared;

public class Constant {
	/***
	 * all constants strings used in the application
	 */
    public static String EMPTY_LIST = "0 fichiers(s)";
    
    public static String DUPLICATED_FILENAME= "Erreur! un fichier du meme nom existe deja";
    public static String FILE_ADDED(String fileName) {return  fileName+" ajouté." ;}
    
    public static String NOT_LOCKED(String fileName) {return "* "+ fileName+" non verrouillé";}
    public static String LOCKED(String fileName,int clientId) {return "* "+fileName+" verrouillé par client "+clientId;}
    public static String LOCK_REQUIRED = "opération refusée : vous devez verrouiller d'abord verrouiller le fichier.";
    
}
