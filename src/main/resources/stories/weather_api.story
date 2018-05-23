Meta:
@author prasanta biswas
@theme API Test
@app WeatherApp

Narrative: Testing story

In order to test Weather API
As a API consumer
I want to get weather report of a particluar City

Scenario: Fetch weather report of particula city
Meta:
@type positive
@data valid

Given I have the API url <url> and city name <city>
When I send an HTTP GET request to the API
Then I shall get the weather report of city <city>

Examples:
|url|city|
|http://restapi.demoqa.com/utilities/weather/city/|Kolkata|

Scenario: Check validation on fetching weather details with wrong city name
Meta:
@type negative
@data invalid

Given I have the API url <url> and city name <city>
When I send an HTTP GET request to the API
Then I shall get the validation error message <message>

Examples:
|url|city|message|
|http://restapi.demoqa.com/utilities/weather/city/|ABC|An internal error occured while servicing the request|