# INF4410
labo du cours de infonuagique 
Comment partir le serveur de fciicher et interagir avec le client 

#test du serveur  
pour partir le serveur 
Le serveur est contenu dans le repertoire suivant:/home/ubuntu/INF4410/tp1/partie2/FileSystem
partir le serveur en faisant ant  et ./server
Le message server ready  devrait afficher 

#test du client 
Le client est dans le repertoire suivant /home/ubuntu/INF4410/tp1/partie2/FileSystem
Compilez avec ant 
et executerz la suite de commande d'interaction 
par exemple ./client 132.207.12.236 list  
pour avoir la liste des fichiers sur le serveur 

NB: Les fichiers locaux sont enregitres dans le repertoire du projet dans notre cas dans FileSystem
NB:un fichier cacher .client_id est generer lors de la premiere interaction du client avec 
le serveur (ce fichier nest jamasi supprimer) 

