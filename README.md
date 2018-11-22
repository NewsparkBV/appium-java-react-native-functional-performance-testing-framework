# ADR Kubus test framework
Dit project is bedoeld om voor de ADR newspark app te verifieren of reeds bestaande functionaliteit nog correct werkt.

Dit project runt via Maven 3.3.1 of hoger en JDK 1.8.

Om de tests te draaien op bitrise tegen devices of emulators in de cloud is verder geen setup nodig.

Om testen te draaien tegen een lokale emulator dient men het volgende te doen:

###Appium
* Instaleer appium zoals beschreven op [http://appium.io/getting-started.html](http://appium.io/getting-started.html)
* Voeg in AVD manager een AVD toe waarvan de device properties overeenkomen met met het betreffende device (of pas een reeds bestaande aan). Om de test te kunnen draaien zijn alleen de 
correcte android versie en AVD naam van belang. Om de test zo real life mogelijk te doen kunnen kunnen in de AVD manager ook andere properties zoals 
schermgroote RAM etc in te stellen.
* Voeg in AVD manager een AVD toe waarvan de device properties overeenkomen met met het betreffende device (of pas een reeds bestaande aan). Om de test te kunnen draaien zijn alleen de 
correcte android versie en AVD naam van belang. 
* Voeg in Xcode devices & simulators een simulator toe waarvan de device properties overeenkomen met met het betreffende device (of pas een reeds bestaande aan). Om de test te kunnen draaien zijn alleen de 
correcte iOS versie en naam van belang. 

###Systeem variabelen
Om het mogelijk te maken de tests lokaal of tegen de cloud te draaien maken we gebruik de systeemvariabele:

* deviceName
* buildNr
* spring.profiles.active

Buildnr wordt gebruikt om de juiste app die naar browserstack geupload is te testen. Voor het uploaden naar browserstack zie: "builds naar browserstack uploaden"

De testlocation eigenschap kan doorgegeven worden of via een maven profiel, of in je IDE (Intellij) als Environment Variable.

De testlocations (profielen) die mogelijk zijn:

* cloud
* local

De deviceName kan middels een systeemvariabele op de command line doorgegeven worden. Om lokaal tegen een emulator/simulator te testen kan een deviceproperties bestand worden toegevoegd. Voor voorbeelden zie:
* properties/deviceprops/Nexus_5X_API_26.properties
* properties/deviceprops/Pixel_API_23.properties
* properties/deviceprops/iPhone_7.properties

De mogelijkheden zijn voor echte devices in de browserstack cloud zijn:

* bs_galaxys8Plus
* bs_galaxys5
* bs_galaxys6
* bs_galaxys7
* bs_galaxys8
* bs_galaxynote4
* bs_nexus9
* bs_nexus6
* bs_pixel7
* bs_pixel8
* bs_pixel2
* bs_iPhone8
* bs_iPhone8Plus
* bs_iPhoneX

###Maven profiel
Via een spring profile kan tevens worden doorgegeven wat het OS is waartegen getest wordt, de mogelijkheden zijn:

* android 
* ios

###Cucumber tags
Geef met een cucumber tags aan welke scenarios gerund moeten worden:

* -Dcucumber.options="--tags @nieuws --tags @smoke"

Voor meer info over het gebruik van cucumber tags:
[https://github.com/cucumber/cucumber/wiki/Tags](https://github.com/cucumber/cucumber/wiki/Tags)

###Mvn comando
Gebruik de volgende instructie om in de cloud de smoketests van de nieuwswereld tegen buildNR 2255 een Google pixel 8 te draaien :
```
mvn clean verify -Pcloud -Dspring.profiles.active=android -DdeviceName=bs_pixel8 -Dcucumber.options="--tags @nieuws --tags @smoke" -DbuildNr=2255
```

###Performance 
Gebruik om de performancetest te draaien altijd de Samsung galaxy A5. Download via de hockeyapp app de te testen release 
build of vervang het bestand "app-develop-bitrise-signed.apk" in de map /resources/ handmatig. Sluit deze met USB aan om het apparaat en draai 
de performncetest met de volgende instructie (waar 1234 = build nummer):
```
mvn clean verify -Pperformance -DbuildNr=1234
```

Zorg dat je ingelogd bent op google docs met adrautotest@gmail.com. Soms moet het access token ververst worden. In dat geval opent de browser 
een pop-up met de vraag voor authorisatie. Selecteer hier het adrautotest@gmail.com account.
In de folder "target/performance" worden vervolgens per testscenario gfxinfo dumpsys log files aangemaakt welke info 
bevatten over de performance van de build.


Testen van performance op een ander fysiek android apparaat

* Stel het device id vast (enable usb debugging, sluit aan op laptop, run in de terminal ```adb devices``` vanuit folder android/platform-tools)
* Maak kopie van properties/deviceprops/galaxya5.properties en plaats deze in de deviceprops folder. Vervang de bestandsnaam door de naam van het device
* Wijzig de properties udid, device.name en platform version naar de juiste waarde voor het device.
* Pas in het maven profile "performance" de property  "deviceName" aan naar de bestandsnaam van het in een eerdere stap aangemaakte properties bestand
* Run de test met: 

```
  mvn clean verify -Pperformance
```
  
###Builds naar browserstack uploaden
Voeg de volgende stap toe in bitrise voor ios:

```
#!/usr/bin/env bash
# fail if any commands fails
set -e
# debug log
set -x
curl -u "$BROWSERSTACK_CREDENTIALS" -X POST https://api.browserstack.com/app-automate/upload -F "file=@$BITRISE_IPA_PATH" -F 'data={"custom_id": "'"$BITRISE_BUILD_NUMBER"'-ios"}'
```

Voeg de volgende stap toe in bitrise voor android

```
#!/usr/bin/env bash
# fail if any commands fails
set -e
# debug log
set -x

curl -u "$BROWSERSTACK_CREDENTIALS" -X POST https://api.browserstack.com/app-automate/upload -F "file=@$BITRISE_APK_PATH" -F 'data={"custom_id": "'"$BITRISE_BUILD_NUMBER"'-android"}'```
```

Voor beide stappen:
* Execute with / runner binary: /bin/bash
* Working directory: $BITRISE_SOURCE_DIR

###Test vanuit bitrise aanroepen

Voeg de volgende workflow toe:

```
workflows:
  _run_from_repo:
    steps:
    - activate-ssh-key:
        run_if: '{{getenv "SSH_RSA_PRIVATE_KEY" | ne ""}}'
    - git-clone:
        inputs:
        - tag: ''
        - commit: ''
        - branch: master
        - branch_dest: ''
        - pull_request_id: ''
        - pull_request_repository_url: ''
        - pull_request_merge_branch: ''
        - repository_url: ssh://bitbucket.org/persgroep/adr-bitrise-config.git
    - script:
        title: continue from repo
        inputs:
        - content: |-
            #!/bin/bash
            set -ex
            bitrise run $REPO_WORKFLOW
```

Voeg de volgende stap aan een bestaande ci build toe met de gewenste cucumber options:

```
  after_run:
  - _run_from_repo
  envs:
  - REPO_WORKFLOW: appium-android
  - BUILD_NR: "$BITRISE_BUILD_NUMBER"
  - CUCUMBER_OPTIONS: <cucumber.options>
```

Met deze stap wordt de test gedraaid tegen een galaxys8. Omdat de tests op android aanzienlijk sneller zijn is het advies de testen in de CI workflow op android te doen. Snachts zullen scheduled de testen op verschillende ios en android devices gedraaid worden.