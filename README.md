# fractalexplorer

# Harjoitustyön tehtänanto
Tehtävänanto sekä tarkempi dokumentaatio ilmestyy tämän päivän (perjantai 16.11.) aikana.

# Työkalujen asennus

Työn tekemistä varten, asenna seuraavat työkalut seuraavassa järjestyksessä:

## Java
https://www.oracle.com/technetwork/java/javase/downloads/index.html

## Git
https://git-scm.com/downloads

## SBT
https://www.scala-sbt.org/

## IDEA
https://www.jetbrains.com/idea/download/

### Työkalujen integrointi IDEAan
a. IDEA:n asetusten kautta (File -> Configure -> Plugins + Install Jetbrains plugin) Scala-lisäosa.

b. Valinnainen lisäosa: Gitlab projects

c. Säädä myös File -> Settings -> Build, execution, deployment -> Build tools -> sbt -> Project level settings -> [x] use auto-import

## JavaFX

- Java 10/11 (suositus)

- lataa käyttöjärjestelmäsi SDK-paketti (zip) osoitteesta https://gluonhq.com/products/javafx/

- tee kotikansioon hakemisto openjfx

- pura zip sinne

- tämä vaihe valinnainen, jos käytät SBT:tä

- JavaFX 8

- Windows/Mac: Oracle JDK (tulee mukana)

- Linux: OpenJFX (pitää asentaa erikseen paketinhallinnasta)

# Konfigurointi

## Ympäristömuuttujat

- Windows: Select Start -> Computer -> System Properties -> Advanced system settings -> Environment Variables -> System variables -> PATH. ...

- Mac: ohje vaihtelee valitettavasti versioittain

- Linux: editoi ~/.profile tai ~/.bashrc

- Asetukset päivittyvät kun sisäänkirjautuu uudestaan!

### Ympäristömuuttuja JAVAFX_HOME (ei välttämätön)

- viittaa yllä valittuun javafx-kansion juureen

- esim. /home/minä/openjfx

### Ympäristömuuttuja PATH

- Testaa että komentoriviltä voi suorittaa käskyt: git, sbt, java, javac (Oracle jostain syystä unohtanut jossain Java-versiossa sisällyttää JDK:n javac:n PATH:iin)

- Jos jokin työkalu ei toimi, lisää kyseisen työkalun binäärien hakemisto PATHiin ;-eroteltuna (Linux/Mac :-eroteltuna).

# Projektin haku

1. Lataa (kloonaa) git-komentorivityökalulla projekti osoitteesta https://gitlab.utu.fi/tech/education/distributed-systems/fractalexplorer.git
    - Voit myös forkata projektin itsellesi suoraan gitlabista ja kloonata tämän koneellesi, jolloin saat automaattisen tarkistuksen käyttöösi (Gitlabissa CI / CD > Run Pipeline)
    - Mikäli git tuottaa liiaksi hankaluuksia, voit ladata uusimman version suoraan osoitteesta https://gitlab.utu.fi/tech/education/distributed-systems/fractalexplorer/-/archive/master/fractalexplorer-master.zip 
2. tai IDEA:lla (Check out from Version Control -> Git)

- Erikseen ladatun projektin voi importata myöhemminkin

- SBT integroituna IDEA:n sisällä – ei tarvita komentoriviä

- Java-versio valitaan projektia luotaessa. Voi vaihtaa myöh. Suositus: 11

# Käyttö

- Komentoriviltä projektin pitäisi käynnistyä käskyllä:

$ sbt run

IDEA:sta käynnistä main() luokasta MandelbrotMain.
