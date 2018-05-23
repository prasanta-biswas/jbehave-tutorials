Meta:
@author prasanta biswas
@theme UI Test
@app facebook

Narrative:
As a Facebook User
In order to update my status
I want to login to my Facebook account
So that I can update my status message

Lifecycle:
Before:
Scope: SCENARIO
Given I initialize my chrome browser instance
After:
Scope: SCENARIO|STORY
Outcome: ANY
Given I cleanup my browser instance

Scenario: Update Facebook status message
Meta:
@type positive
@data valid

Given I have a Facebook account
And I open Facebook in my web browser with the url <url>
And I login to Facebook with username <username> and password <password>
Then I check if status message box is present in the homepage after successful login
When I put my status <message> in the status box
And I click post button
Then I should see my new status <message> in my news feeds

Examples:
|username|password|url|message|
|VALID_USERNAME|VALID_PASSWORD|https://www.facebook.com|Hi Facebook|

!-- Use valid facebook username and password

Scenario: Check validation while trying to update facebook status with wrong username/password
Meta:
@type nagative
@data invalid
Given I have a Facebook account
And I open Facebook in my web browser with the url <url>
And I login to Facebook with username <username> and password <password>
Then I check if status message box is present in the homepage after unsuccessful login

Examples:
|username|password|url|
|1234567|abcdf|https://www.facebook.com|