# Poc Metallica

Ce premier atelier est destiné à appréhender les problèmes réseaux auquel sera confronter Metallica dans sa volonte d'orchestration

Il contient trois projets :
- poc-protools : l'orchestrateur
- poc-password-generator : permettant la generation d'un mot de passe pour un username donné
- poc-password-mail-sender : permettant la simulation d'envoi d'un mail en ecrivant dans un fichier

Ces trois projet ne communique que du sens protools vers les API des deux autres.

Le workflow étudier est :
- Envoi d'une commande de workflow creation de mot de passe à Protools
- Protools demande la generation du mot de passe à l'API de mot de passe
- A reception il solicite l'API d'envoi de mail
- puis il retourne Password généré

C'est un workflow très simple qui va nous permettre de voir et d'adresser les différents problèmes réseaux en utilisant les patterns/usages suivant :
- Transactional Outbox
- Event sourcing avec utilisation de protools en broker
- Command store pour pouvoir sécuriser les appels aux différents service

Pour l'instant :
- J'ai mis en place les trois services avec le workflow nominal qui fonctionne.
- Mis en place docker et docker-compose. On peut lancer l'ensemble des projets en faisant "docker-compose build" puis "docker-compose up" qui lance une base postgres en même temps
- J'ai fait des tests d'intégration qui fonctionne pour le cas nominal et cas d'erreur nominaux (deux demande de génération pour le même user et absence de username)
- J'ai fait des tests d'intégration pour des cas qui devront marcher à la fin de la mise en place des différents patterns mentionnés (les test se lance sur l'environement doker-compose)
  - En cas de timeout de la génération (mais succès après timeout) le workflow doit bien se passer avec trois sous cas
    - retry avant la persistence
    - retry pendant la persistence
    - retry après la persistence
  - En cas d'indisponibilité de la génération il n'est pas besoin de relancer la commande
  - Les deux cas doivent marcher même si on ajoute des réplicas dans le docker compose
  - Il reste aussi le cas de surcharge au niveau réseau

