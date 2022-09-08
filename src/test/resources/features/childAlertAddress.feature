#Author: oliviermorel.oc1@gmail.com
#language: fr

@http://localhost:8080/childAlert?address=<address>
Fonctionnalité: 2-childAlert?address
Cette url doit retourner une liste d'enfants (âge <= 18 ans) habitant à cette adresse.
S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide

	En tant qu’utilisateur, je souhaite obtenir la liste des enfants (prénom, nom, âge, liste des adultes)
	habitant à une adresse donnée

	Contexte:
		Étant donné les personnes contexte 2:
		|firstName|lastName|age |address         |city    |zip    |phone         |email            |
		|"John"   |"Boyd"  |"30"|"1509 Culver St"|"Culver"|"97451"|"841-874-6512"|"jboyd@email.com"|
		|"Elsa"   |"Boyd"  |"33"|"1509 Culver St"|"Culver"|"97451"|"841-874-6512"|"elsad@email.com"|
		|"Tenley" |"Boyd"  |"18"|"1509 Culver St"|"Culver"|"97451"|"841-874-6513"|"tboyd@email.com"|
		|"Tessa"  |"Carman"|"28"|"1509 Culver St"|"Culver"|"97451"|"841-874-6514"|"tenz@email.com" |
		|"Tony"   |"Boyd"  |"12"|"1509 Culver St"|"Culver"|"97451"|"841-874-6512"|"jboyd@email.com"|
		|"Eric"   |"Kadiga"|"25"|"844 Binoc Ave" |"Culver"|"97451"|"841-874-6515"|"kadi@email.com" |
		|"Brad"   |"Voisin"|" 2"|"1509 Culver St"|"Culver"|"97451"|"841-874-6515"|"kadi@email.com" |
		|"Eric"   |"Voisin"|"24"|"1509 Culver St"|"Culver"|"97451"|"841-874-6515"|"kadi@email.com" |
	Scénario: 2A : des enfants habitent à l’adresse
		Quand utilisateur 2A requête l’addresse "1509 Culver St"
		Alors la liste A des enfants est:
		|firstName|lastName|age |adults                                                                                                                              |
		|"Tenley" |"Boyd"  |"18"|"[{\"firstName\":\"John\", \"lastName\":\"Boyd\", \"age\":\"30\"}, {\"firstName\":\"Elsa\", \"lastName\":\"Boyd\", \"age\":\"33\"}]"|
		|"Tony"   |"Boyd"  |"12"|"[{\"firstName\":\"John\", \"lastName\":\"Boyd\", \"age\":\"30\"}, {\"firstName\":\"Elsa\", \"lastName\":\"Boyd\", \"age\":\"33\"}]"|
		|"Brad"   |"Voisin"|" 2"|"[{\"firstName\":\"Eric\", \"lastName\":\"Voisin\", \"age\":\"24\"}]"                                                               |
	
	Scénario: 2B : il n’y a pas d’enfant habitant à l’adresse
		Quand utilisateur 2B requête l’addresse "844 Binoc Ave"
		Alors la liste B des enfants est:
		|firstName|lastName|age |others                                |
		
	Scénario: 2C : l’adresse n’existe pas
		Quand utilisateur 2C requête l’addresse "112 Steppes Pl"
		Alors la liste C des enfants est:
