# POST-Request zum Erstellen des Films "Avengers: Infinity War"

## Mit curl ausführen
Führen Sie den folgenden Befehl in der Kommandozeile aus, um den Post Request zu testen:

```
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d @marvel_movies/thor.json
```

## Mit PowerShell ausführen
Alternativ können Sie in PowerShell folgenden Befehl verwenden:

```powershell
Invoke-RestMethod -Method POST -Uri "http://localhost:8080/api/movies" -ContentType "application/json" -InFile "marvel_movies/thor.json"
```

## Mit Postman
1. Öffne Postman
2. Erstelle einen neuen Request
3. Wähle die Methode "POST"
4. Gib die URL ein: http://localhost:8080/api/movies
5. Gehe zum Tab "Body"
6. Wähle "raw" und "JSON"
7. Kopiere den Inhalt der Datei "thor.json" in das Textfeld
8. Klicke auf "Send"

Der Server sollte mit einem Status 201 (Created) und dem erstellten Film-Objekt antworten.