# fractalexplorer

## Harjoitustyön tehtävänanto
Harjoitustyön tehtävänanto sekä lisätiedot löytyvät tämän repositoryn doc/ -hakemistosta nimellä exercise.pdf

## Työkalujen asennus
Työn tekemistä varten, asenna seuraavat työkalut seuraavassa järjestyksessä:

### 1. Java Development Kit (JDK)
https://www.oracle.com/technetwork/java/javase/downloads/index.html

### 2. Git-versionhallinta
https://git-scm.com/downloads

### 3. SBT käännöksen automatisointityökalu (build tool)
https://www.scala-sbt.org/

### 4. IDEA-ohjelmointiympäristö
https://www.jetbrains.com/idea/download/

#### Työkalujen integrointi IDEAan
1. IDEA:n asetusten kautta (File -> Settings... -> Plugins > Install Jetbrains plugin...) Etsi "Scala" ja asenna
2. Valinnainen lisäosa: Gitlab projects
3. Säädä myös File -> Settings... -> Build, execution, deployment -> Build tools -> sbt -> Project level settings -> [x] Use Auto-import
    - Tämä optio on valittavissa vasta kun projekti on tuotu IDEAan. Eli siis kohdan "Projektin aloittaminen" jälkeen

### JavaFX
JavaFX vastaa käyttöliittymästä esimerkkiprojektissamme. Riippuu Javan versiosta, onko JavaFX mukana vai ei.

- Java 10/11 (suositus)
    - JavaFX ei ole enää JDK:n mukana, joten se joudutaan asentamaan
    - Mikäli käytät SBT:tä, on tämä vaihe valinnainen, sillä sbt osaa ladata JavaFX 11:n automaattisesti!
    - Lataa käyttöjärjestelmällesi sopiva JavaFX SDK-paketti (zip) osoitteesta https://gluonhq.com/products/javafx/
    - Tee hakemisto openjfx (Sijainti: Windows:esim. Documents, Linux:esim. kotikansioosi)
    - Pura zip sinne

- Java 8 ja JavaFX 8
    - Windows/Mac: JavaFX osana Oracle JDK 8:aa
    - Linux: OpenJFX (pitää asentaa erikseen paketinhallinnasta)

## Konfigurointi

### Ympäristömuuttujat

- Windows: Select Start -> Computer -> System Properties -> Advanced system settings -> Environment Variables -> System variables -> PATH. ...
- MacOS: ohje vaihtelee valitettavasti versioittain
- Linux: editoi ~/.profile tai ~/.bashrc
- Asetukset päivittyvät kun sisäänkirjautuu uudestaan!

#### Ympäristömuuttuja JAVAFX_HOME (ei välttämätön)
- Mikäli käytät SBT:tä tai Java 8:aa, ei tätä tarvitse tehdä
- Aseta ympäristömuuttuja JAVAFX_HOME osoittamaan javafx-kansion juureen
    - Eli siis kansioon, johon purit JavaFX-kohdassa JavaFX-kirjaston
- Esimerkiksi: /home/minä/openjfx

#### Ympäristömuuttuja PATH
Ympäristömuuttuja PATH sisältää kaikki ne polut, josta etsitään suoritettavia binäärejä työkansiosta riippumatta
- Testaa että komentoriviltä voi suorittaa käskyt: git, sbt, java, javac (Oracle jostain syystä unohtanut jossain Java-versiossa sisällyttää JDK:n javac:n PATH:iin)
- Jos jokin työkalu ei toimi, lisää kyseisen työkalun binäärien hakemisto PATHiin ;-eroteltuna (Linux/Mac :-eroteltuna).

## Projektin aloittaminen ja projektirungon lataus

### Forkkaamalla projekti (suositeltu)
1. Ryhmän perustaja forkkaa (eli tekee itsellensä kopion projektista gitlabiin) osoitteessa https://gitlab.utu.fi/tech/education/distributed-systems/fractalexplorer/forks/new
    - Suositeltavaa pitää näkyvyys forkilla Privatena, etteivät muut pääse kopioimaan koodia
2. Ryhmäläisille tulee antaa tämän jälkeen tarvittavat oikeudet forkattuun projektiin kohdasta Settings > Members
3. Antakaa myös vähintään Reporter-tason oikeudet distributed-systems -ryhmälle projektisivunne Settings > Members > Invite group -kohdassa
4. Ryhmän jäsenet voivat nyt kloonata forkkisi harjoitustyöstä joko komentorivillä (git clone) tai IDEA:lla (Check out from Version Control -> Git)
5. Joka kerta kun joku lähettää (push) commitit gitlabiin, ajetaan CI/CD-pipeline, joka tarkistaa, kääntyykö harjoitustyö ja onko se ratkaistu oikein. Ei siis kannata pelästyä ilmoitusta siitä, että testaus epäonnistui.

### Manuaalisesti
1. Lataa (kloonaa) git-komentorivityökalulla projekti osoitteesta https://gitlab.utu.fi/tech/education/distributed-systems/fractalexplorer.git
    - Huom. Et pysty tällöin lähettämään muutoksia gitlabiin ilman lisäkonfiguraatiota, sillä sinulla ei ole kirjoitusoikeuksia projektipohjaan
    - Mikäli git tuottaa liiaksi hankaluuksia tälläkin asteella, voit ladata uusimman version harjoitustyöpohjasta suoraan osoitteesta https://gitlab.utu.fi/tech/education/distributed-systems/fractalexplorer/-/archive/master/fractalexplorer-master.zip
2. Voit aloittaa työskentelyn. Huomaa, että tällä tavoin et pysty hyödyntämään gitin tarjoamia etuja ryhmätyöskentelyssä, etkä saa myöskään automaattitarkastajaa käyttöön.
3. Lopuksi kuitenkin automaattitarkastajaa käytetään toimivuuden tarkistamiseksi. Tällöin voit luoda gitlabiin uuden puhtaan repositoryn ja lähettää koodit suoraan sinne.


## Käyttö
Komentoriviltä projekti käynnistyy komennolla:

```shell
$ sbt run
```

IDEA:sta käynnistä `main()` luokasta `MandelbrotMain`.

### Huomioita

- Erikseen ladatun projektin voi importata myöhemminkin IDEAan

- SBT integroituna IDEA:n sisällä – ei tarvita komentoriviä

- Java-versio valitaan projektia luotaessa. Voi vaihtaa myöhemmin. Suositus: 11
