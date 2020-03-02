# The solution for currency list

## Demo

![Demo](weather.gif "Demo")

## Structure.
There are two modules in this solution: servicelibrary and app.
The app is based MVP framework. 

### servicelibrary
This module handle the api calls and mapping the api response to the data that the app can consume.

### app
This module handle the weather list and recent search.
1. Weather list is in the mainui package and the UI is separated to several classes like BindListener, DbHelper and etc as well as the Activity class and RecyclerViewAdapter class.
1. Recent search package just include the Activity and RecyclerViewAdapter classes.

The logic of main ui is complex, here I choose extension functions to separate logic according to what they are doing.

## Test
Threre three types of tests.
1. Integrate tests which is in WeatherRepositoryTest which test the api service and transforming data.
1. Db tests which is in WeatherDatabaseTest will test db reading and writing, but the tests are instrument test and need device or simulator.
1. Other tests are unit test with mock object.


