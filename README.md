databees
========

INHolland Mobile Development Project 2013/2014

Run
----------

Use the Databees.apk found under the root directory to install and run the application on your mobile device.

Installing
----------

In order to be able to run Google maps, a seperate key store file in required for authentication.

Custom key store file is found here under `Databees/additional_resource/debug.keystore`

Download the file to your desktop and change the path of keystore by going to 
`ADT --> Andrioid --> Build --> Change Custom debug keystore`

The remote database for the android application could be found in the root folder `databees.sql`
Import this file through phpmyadmin to create the database.

Dependencies
----------

There are two projects found under `Databees/additional_resource/`
The two libraries required by the application are `google-play-services_lib` and `library`

Both the folders should be imported to eclipse and marked as library by `Right click on project directory --> Properties --> Android --> Is Library`

Then `Go to the properties of Databees --> Android --> Library --> Add the above libraries from the list`

Troubleshooting
----------

If you continue to have errors then check the `Project Build Target` and make sure Google APIs - API level 17 is choosen.
Clean project and test again
