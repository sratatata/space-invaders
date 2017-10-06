# Wstęp
## Tło
### Android
Android – system operacyjny z jądrem Linux dla urządzeń mobilnych takich jak telefony komórkowe, smartfony, tablety i netbooki oraz imteligentne zegarki. W 2013 roku był najpopularniejszym systemem mobilnym na świecie. Wspomniane jądro oraz niektóre inne komponenty, które zaadaptowano do Androida opublikowane są na licencji GNU GPL. Android nie zawiera natomiast kodu pochodzącego z projektu GNU. Cecha ta odróżnia Androida od wielu innych istniejących obecnie dystrybucji Linuksa (określanych zbiorczo mianem GNU/Linux). Początkowo był rozwijany przez firmę Android Inc. (kupioną później przez Google), następnie przeszedł pod skrzydła Open Handset Alliance.

Android zrzesza przy sobie dużą społeczność deweloperów piszących aplikacje, które poszerzają funkcjonalność urządzeń. W sierpniu 2014 było dla tego systemu dostępnych ponad 1,3 miliona aplikacji w Google Play (wcześniej Android Market)[5].

Od kwietnia 2017 roku Android ma największe udziały na rynku systemów operacyjnych.

Android w odróżnieniu od konkurencji w postaci iOS(Apple), Windows Phone (Microsoft) boryka się ze znaczną fragmentacją wersji systemów zainstalowanych na urządzeniach. Zjawisko fragmentacji wpływa na sposób rozwoju oprogramowania, gdyż należy uwzględnić nie tylko najnowsze i najpotężniejsze urządzenia ale też urządzenia starsze i te z nieaktualnym systemem operacyjnym.

### libGDX

(Tu krótki opis czym jest libGDX i dlaczego wykorzystujemy)

### Space Invaders (Najeźdźcy z Kosmosu)

(Tu opis plus screeny space invaders)

## Organizacja Tutoriala

Niniejszy kurs podzielony został na lekcje. Każda z lekcji pomoże Ci zrozumieć kolejne aspekty programowania.

* Lekcja 1 - Szablon i struktura projektu
* Lekcja 2 - ...
...

Każda z lekcji ma przypisany odpowiedni TAG w repozytorium, po to żebyś mógł przywrócić wersję programu do stanu początkowego dla danej wersji. Dzięki temu nie będziesz się martwić, gdy coś zajdzie zadaleko w niewłaściwym kierunku.

W celu ustawienia stanu zerowego dla danej lekcji wprowadź w linii komend:

'''
#> git checkout tags/Lekcja-1
#> git reset --hard origin/master
#> git clean -xf
'''

Ale jeżeli linia komend jest dla Ciebie przerażająca przygotowaliśmy specjalnie skrypty w katalogu '''githelp''', które pomogą Ci w przechodzeniu pomiędzy lekcjami:

''' .githelp/lekcja.bat Lekcja-1 '''

## Lekcja 1 - Szablon projektu

### Co robimy
Najpierw tytułem wstępu wyjaśnimy Ci co chcemy osiągnąć naszym kursem.
Będziemy ćwiczyć programowanie, poprzez implementacje klasyki gier komputerowych: Space Invaders.

Reguły jakie chcemy spełnić:
1. Wrogowie znajdują się w trzech rzędach na środku ekranu
2. Statek znajduje się na dole ekranu
3. Statek porusza się w lewo i prawo w płaszyźnie ziemi ( pojawia się w miejscu dotknięcia, lub można go przeciągać)
4. Statek może strzelać do kosmitów (przez dotknięcie powyżej 1/3 ekranu)
5. Kosmici strzelają w stronę ziemi

### Struktura projektu:

```
|_ core
|_ android
|_ desktop
```

android - moduł zawiera małą aplikacje, która uruchamia naszą grę napisaną w libGDX

desktop - moduł zawiera równie małą aplikację, która uruchamia naszą gre napisaną w libGDX

core - to jest najważniejszy moduł wszystkie zmiany będziemy wprowadzać w tym module, jest to serce naszej implementacji


## Lekcja 2 - Animacja postaci

Przez postać rozumiemy rakiety, obcych ale także pociski.

W naszym przykładzie wykorzystamy technike stosowaną przez naszych dziadków ;) zwaną Sprite animation. Podejście to polega na przechowywaniu grafiki w plikach typy mapa bitowa, w tym wypadku png. Kolejne klatki są zapisane w tym samym pliku w postaci kolumn i wierszy. Technika ta polega na indeksowaniu i wybieraniu kolejnych sekwencji z pliku.

![Sprite z Rakieta](/space-invaders/graphics/rakieta.png)

//todo wczytywanie sprite, przyklad

## Lekcja 3 - Poruszanie postacią

## Lekcja 4 - Strzelanie

## Lekcja 5 - Wykrywanie kolizji

## Lekcja 6 -

# Słownik
* Repozytorium - odnosi się do systemów kontroli wersji, w tym wypadku GIT. Repozytorium przypomina bibliotekę z wszystkimi wersjami napisanego programu.
* TAG - znacznik wyróżniający konkretną wersję programu w repozytorium.

# Bibliografia
[1] https://pl.wikipedia.org/wiki/Android_(system_operacyjny)
[2] Android Podręcznik Hackera: Joshua J. Drake, Pau Oliva Fora, Zach Lanier, Collin Mulliner, Stephen A. Ridley, Georg Wicherski
