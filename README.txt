Voici Mon implémentation de l'application. 
Cette implémentation est réalisée sur la base de Spring afin que celle-ci puisse être embarquée facilement au sein d'une autre application comme API ou sur un serveur.
Cette application et lancée par la méthode main de la class Main pour laquelle on défini les arguments de lancement de l'application.


L'application se lance donc via Eclipse-NetBeans avec les arguments suivant : 
	- une chaine de caractères numérique qui défini la chaine de caractère à traiter.
	- un séparateur '-';
	- et un bouléen (true ou false) écrit en toute lettre qui défini le lancement de l'exemple par défault (valeur 'true') ou non (false)
	
	
Il existe plusieurs possibilités d'exemple de lancement:
	Exemples:
	1) "1235468-true" ou "-true" ces arguments vont lancer l'exemple de l'ennoncé.
	2) "12321654-false" ces arguments vont permettre de lancer l'application en réalisant l'optimmisation pour la chaine de caractère "12321654"
	3) "-false" ces arguments vont permettre de lancer l'application en réalisant l'optimmisation pour une chaine de caractère aléatoire générée par un générateur de chaine de caractère numérique.
	4) tout autre type d'argument levera une Exception de type IllegalArgumentException.
	 
	 