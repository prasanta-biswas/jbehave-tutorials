Meta:
@author prasanta biswas
@theme UI Test
@app wallethub

Narrative:
As a wallethub user
I want to review a particual insurance policy
So that the review appears in my review list


Lifecycle:
Before:
Scope: SCENARIO
Given I initialize my chrome browser instance
After:
Scope: SCENARIO|STORY
Outcome: ANY
Given I cleanup my browser instance


Scenario: Create review for a particual walletHub insurance policy
Meta:
@type positive
@data valid

Given I have a WalletHub test account
And I open wallethub in the web browser using url <url>
And I login to my wallethub account with email <email> and password <password>
Then I should see my username <username> on my homepage
Given I navigate to review url <reviewUrl> after login
And I count the current review count in my review list
And I navigate to insurance company url <insuranceCompanyUrl>
When I click Write a Review button
Then I should see new review page
Given I select insurance policy <policy> from insurance list in new review page
And I apply rating <star> star
And I put my review text <text>
When I click submit button
Then I should see confirmation message <message>
And I should see a my new review text <text> in my review url <reviewUrl>

Examples:
|email|password|url|username|reviewUrl|insuranceCompanyUrl|policy|text|star|message|
|pthewizard@gmail.com|Abcd123*|https://wallethub.com/|pthewizard|https://wallethub.com/profile/pthewizard/reviews/|http://wallethub.com/profile/test_insurance_company/|Health|This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.This is my first reviewHealthPolicy.|5|Awesome!|