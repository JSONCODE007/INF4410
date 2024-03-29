package ca.polymtl.inf4410.tp1.shared;

public class Constant {
	/***
	 * all constants strings used in the application
	 */
    public static String EMPTY_LIST = "0 fichiers(s)";
    
    public static String DUPLICATED_FILENAME= "Erreur! un fichier du meme nom existe deja";
    public static String FILE_ADDED(String fileName) {return  fileName+" ajoutÃ©. sur le serveur" ;}
    public static String FILE_CREATED(String fileName) {return  fileName+" creer. sur le serveur" ;}

    public static String NOT_LOCKED(String fileName) {return "* "+ fileName+" non verrouilleÌ�";}
    public static String LOCKED(String fileName,int clientId) {return "* "+fileName+" verrouilleÌ� par client "+clientId;}
    public static String LOCK_REQUIRED = "opeÌ�ration refuseÌ�e : vous devez verrouiller d'abord verrouiller le fichier.";
    
    public static String FILE_DOESNT_EXIST="Erreur! Le fichier n'existe pas sur le server"; 
    public static String LOCKED_BY_OTHER="Erreur! Le fichier est deja verouille pas un autre utilisateur"; 
    public static String FILE_SAME="Votre fichier est deja a jour"; 
    
    public static String FILE_IN_EDIT_STATE (String fileName){return fileName+" en cours de modification";}
    
    public static String FILE_UPLOADED = "Le fichier demandeer a ete ajouter dans le repertoire courant ";
    
    //non du ficheir de configuration du client 
    public static String CLIENT_ID_FILE_NAME = ".client_id";
    
}
