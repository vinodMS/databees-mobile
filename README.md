databees
========

INHolland Mobile Development Project 2013/2014

Installing
----------

In order to be able to run Google maps, a seperate key store file in required for authentication.

Custom key store file is found here under `Databees/additional_resource/debug.keystore`

Download the file to your desktop and change the path of keystore by going to 
`ADT --> Andrioid --> Build --> Change Custom debug keystore`

Dependencies
----------

There are two projects found under `Databees/additional_resource/`
The two libraries required by the application are `google-play-services_lib` and `library`

Both the folders should be imported to eclipse and marked as library by `Right click on project directory --> Properties --> Android`

Then `Go to the properties of Databees --> Android --> Library --> Add the above libraries from the list`

