# REQUISITI TECNICI
 * Versione java: 21
 * Database: My SQL

# SETUP
Il progetto richiede tre variabili d'ambiente per fare andare il progetto. Nello specifico sono le variabili d'ambiente
usate per il collegamento al database e sono le seguenti:
 * JDBC_URL
 * JDBC_USER
 * JDBC_PASS

è presente anche un sistema di login con spring security e JWT ci sarà anche da impsotare la seguente variabile
d'ambiente per gestire il jwt
 * JWT_SECRET

Senza queste variabili d'ambiente impostare il progetto non potrà partire

C'è anche un sistema di log su file che di default vengono salvati in
```
/cine-mille/log
```

I fil excel per la programmazione invece vengono gestisti tutti i lunedì e sono da mettere nella cartella
```
/cine-mille/excel
```

Queste ultime configurazioni si posso ovviamente cambiare sul file
```
application.yml
```
# INFO
Nel progetto è configurato swagger per la documentazione delle API. Sono consultabili all'URL:
```
<SERVER-URL:PORT>/swagger-ui.html
```
Il file all'avvio fa scattare un paio di automatismi
 * Genera l'utente di base se non presente: credenziali: admin, admin
 * Prova a vedere se c'è un file excel da processare e se non sono presenti ancora dei film, viene subito processatto