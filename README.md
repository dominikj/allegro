# allegro
## Przygotowanie
Przed urochomieniem aplikacji należy:
- zainstalować klienta GIT
- zainstalować narzędzie do budowania projektów Maven
- upewnić się że port 8080 jest wolny
## Uruchomienie
1) Pobrać repozytorium
2) W pliku `application.properties` można umieścić prywatny token github by zwiększyć limit zapytań w stosunku do użytkownika anonimowego, jednakże nie jest to konieczne. ( szczegóły: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
3) W katalogu repozytorium wykonać polecenie  `mvn clean install spring-boot:run`
4) Aplikacja dostępna jest pod adresem `localhost:8080`
## Użytkowanie
Aplikacja udostępnia jeden adres API <br />
`http://localhost:8080/repos/[nazwa_użytkownika_github]?pageNumber=[numer_strony]&pageSize=[liczba_rekordów_na_stronie]` <br />
Nazwa użytkownika jest obowiązkowa, natomiast parametry stronicowania opcjonalne. Dane są zwracane w formacie JSON.
## Uwagi i spostrzeżenia
* W trakcie testów API Github odpowiadało zaskakująco wolno. Nie sprawdzałem, czy problem też leży po mojej stronie. 
* Nie zauważyłem w API Github pola zwracającego sumę wszystkich gwiazdek we wszystkich repozytoriach danego użytkownika, może coś przeoczyłem. Dlatego też zliczam je ręcznie przechodząc po wszystkich repozytoriach danego użytkownika. Często jest to kilka stron wyników, bo github ogranicza liczę rekordów na jednej stronie do 100, więc uważam to za rozwiązanie mało wydajne. 
* API Github rozróżnia użytkowników prywatnych i organizacje. I tak przykładowo zapytania: `api.github.com/orgs/elastic/repos` i `api.github.com/users/elastic/repos` zwracają różne wyniki. 
* "Gwiazdki" są reprezentowane przez pola `stargazers_count` i `watchers_count`. Na ten moment wydaje się że zawsze mają identyczną wartość. 

