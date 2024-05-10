# PPS-23-LambdaQuiz 🧩

![GitHub Release](https://img.shields.io/github/v/release/marcochiavaroliunibo/PPS-23-LambdaQuiz?display_name=release)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/marcochiavaroliunibo/PPS-23-LambdaQuiz/lambdaquiz-ci.yml?branch=main&label=code%20quality%20%2B%20tests)

Un semplice gioco di quiz multi giocatore realizzato in Scala 3 per l'esame di PPS.

## Documentazione 📑

Oltre alla verbosa Scaladoc presente nel codice, è possibile trovare la relazione del progetto a questo
[link](https://marcochiavaroliunibo.github.io/PPS-23-LambdaQuiz).

## Esecuzione dell'applicazione 🚀

Per poter avviare il gioco, è necessario avere installato sul proprio sistema il **JDK 17**.
In più, se si vuole compilare ed eseguire la build in locale, sono necessari anche i software **Scala** ed **SBT**.

### Avvio tramite eseguibile

I passi da seguire per lanciare l'applicazione tramite il file JAR sono i seguenti:

1. Scaricare il file `.jar` relativo al proprio sistema operativo presente nella sezione `Releases` del repository;
2. Aprire un terminale nella cartella in cui si trova il file scaricato;
3. Eseguire il comando `java -jar LambdaQuiz-${os}-x86.jar`.

### Avvio tramite SBT

Per avviare LambdaQuiz con SBT, seguire i seguenti passi:

1. Clonare questa repository sulla propria macchina;
2. Aprire un terminale nella cartella appena creata;
3. Eseguire i comandi

```bash
  sbt compile
  sbt run
```

### Creazione dell'eseguibile in locale

Se si vuole lanciare l'applicazione tramite un eseguibile generato direttamente dalla propria macchina, fare quanto
segue:

1. Eseguire i punti 1 e 2 della sezione precedente;
2. Eseguire i comandi

```bash
  sbt compile
  sbt assembly
  java -jar target/scala-3.4.0/LambdaQuiz.jar
```

