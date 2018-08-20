Voici Mon impl�mentation de l'application. 
Cette impl�mentation est r�alis�e sur la base de Spring afin que celle-ci puisse �tre embarqu�e facilement au sein d'une autre application comme API ou sur un serveur.
Cette application et lanc�e par la m�thode main de la class Main pour laquelle on d�fini les arguments de lancement de l'application.


L'application se lance donc via Eclipse-NetBeans avec les arguments suivant : 
	- une chaine de caract�res num�rique qui d�fini la chaine de caract�re � traiter.
	- un s�parateur '-';
	- et un boul�en (true ou false) �crit en toute lettre qui d�fini le lancement de l'exemple par d�fault (valeur 'true') ou non (false)
	
	
Il existe plusieurs possibilit�s d'exemple de lancement:
	Exemples:
	1) "1235468-true" ou "-true" ces arguments vont lancer l'exemple de l'ennonc�.
	2) "12321654-false" ces arguments vont permettre de lancer l'application en r�alisant l'optimmisation pour la chaine de caract�re "12321654"
	3) "-false" ces arguments vont permettre de lancer l'application en r�alisant l'optimmisation pour une chaine de caract�re al�atoire g�n�r�e par un g�n�rateur de chaine de caract�re num�rique.
	4) tout autre type d'argument levera une Exception de type IllegalArgumentException.
	 
	 